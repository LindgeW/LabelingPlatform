package com.labeling.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.labeling.demo.entity.*;
import com.labeling.demo.entity.vo.ExportVO;
import com.labeling.demo.entity.vo.InstanceUserVO;
import com.labeling.demo.service.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
//import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Controller
public class DataController {
//    private static final String tempDir = "src/main/resources/temp";
    private static final int BatchSize = 500;
    private static final String root = "bg/";

    private UserService userService;
    private InstanceService instanceService;
    private TaskService taskService;
    private InstanceUserService instanceUserService;
    private DataTypeService dataTypeService;

    @Autowired
    public DataController(UserService userService, InstanceService instanceService, TaskService taskService, InstanceUserService instanceUserService, DataTypeService dataTypeService) {
        this.userService = userService;
        this.instanceService = instanceService;
        this.taskService = taskService;
        this.instanceUserService = instanceUserService;
        this.dataTypeService = dataTypeService;
    }

    @GetMapping("/upload")
    @RequiresRoles("admin")
    public String toUpload(Model model){
        User curUser = (User) SecurityUtils.getSubject().getPrincipal();
        List<Task> tasks = taskService.findByExpertName(curUser.getUsername());
        List<DataType> dataTypes = dataTypeService.findAll();

        model.addAttribute("builtTasks", tasks);
        model.addAttribute("dataTypes", dataTypes);
        return root+"upload";
    }

    @GetMapping("/export")
    @RequiresRoles("admin")
    public String toExport(Model model){
        List<Task> tasks = taskService.findAll();
        model.addAttribute("tasks", tasks);
        return root+"export";
    }

    @GetMapping("/exportData")
    @RequiresRoles("admin")
    public void exportData(@RequestParam("task") String taskName,
                           HttpServletResponse response) throws IOException {
        System.out.println(taskName);
        /*
            原始数据
            专家标签
            用户标签
            模型标签（暂不需要）
         */
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);  //APPLICATION_OCTET_STREAM_VALUE
        response.setHeader("Content-Disposition", String.format("attachment; filename=%s.json", new String(taskName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)));
        BufferedOutputStream bufOs = new BufferedOutputStream(response.getOutputStream());

        //根据任务查团队(一个团队一个任务，一个任务可以多个团队)
        List<Instance> taskInsts = instanceService.findByTaskName(taskName);
        Set<ExportVO> exportVOs = new HashSet<>();
        for (Instance instance: taskInsts){ //导出相关任务的所有数据
            //根据数据找到标注者的标注记录
//            List<InstanceUser> records = instanceUserService.findInstanceUserById(instance.getInstanceId());
            List<InstanceUserVO> records = instanceUserService.findFullRecords(instance.getInstanceId());

            ExportVO exportVO = new ExportVO(instance.getInstanceId(), instance.getItem(), instance.getTagDefault(), instance.getTagExpert(), records);
            exportVOs.add(exportVO);

//            if (!records.isEmpty()){
//                for (InstanceUser record : records) {
//                    String role = userService.findUser(record.getUsername()).getRole();
//                    ExportVO exportVO = new ExportVO(instance.getInstanceId(), instance.getItem(), instance.getTagDefault(), instance.getTagExpert(), record.getUsername(), record.getTag(), role, record.getTagTime(), record.getResponseTime());
//                    exportVOs.add(exportVO);
//                }
//            } else{ //没有标注记录直接导出
//                ExportVO exportVO = new ExportVO(instance.getInstanceId(), instance.getItem(), instance.getTagDefault(), instance.getTagExpert(), "", "", "", null, 0f);
//                exportVOs.add(exportVO);
//            }
        }

        bufOs.write(JSONArray.toJSONBytes(exportVOs));
        bufOs.flush();
        bufOs.close();
    }


    @PostMapping("/upload")
    @ResponseBody
    @RequiresRoles("admin")
    public RespEntity upload(@RequestParam("file") MultipartFile multiFile,
                             @RequestParam("taskName") String taskName,
                             @RequestParam("dataType") Short dataType,
                             @RequestParam("tags") String tags,
                             @RequestParam("separator") String separator) throws IOException {
        if(multiFile==null || multiFile.isEmpty()){
            return new RespEntity(RespStatus.Error);
        }

        System.out.println(multiFile.getOriginalFilename());
        System.out.println(multiFile.getName());
        System.out.println(multiFile.getContentType());
        System.out.println(multiFile.getSize());
        System.out.println(tags);
        System.out.println(separator);

//        String[] tagArr = StringUtils.split(tags, ";");
        String[] tagArr = StringUtils.split(tags, "\n");
        // 清除每个标签值中的空白字符
        for (int i = 0; i < tagArr.length; i++) {
            tagArr[i] = StringUtils.trimToEmpty(tagArr[i]);
        }

        /*
            插入：
                任务不存在
            修改：
                同一个人创建的任务同名
            警告：
                不同人创建的同名任务
         */
        // 发布任务的人
        User curUser = (User) SecurityUtils.getSubject().getPrincipal();
        String username = curUser.getUsername();

        Task task = taskService.findByName(taskName);
        if (task != null && !username.equals(task.getExpertname())){
            //任务名已存在且不是同一个人创建的 -> 警告
            return new RespEntity<>(RespStatus.DuplicateTask, Boolean.FALSE);
        }

//        Set<String> instSet = new HashSet<>(0);
        Set<Instance> instSet = new HashSet<>(0);

        //任务名已存在且是同一个人创建的 -> 修改
        if (task != null && username.equals(task.getExpertname())){
            if(multiFile.getOriginalFilename().endsWith("json")){
                String jsonStr = IOUtils.toString(multiFile.getInputStream());
                JSONArray jsonArray = JSON.parseArray(jsonStr);

                for (int i = 0, len=jsonArray.size(); i < len; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Long instanceId = jsonObject.getLong("instanceId");
//                    String tagDefault = jsonObject.getString("defaultTag");
//                    if(StringUtils.isBlank(tagDefault)){
//                        tagDefault = "";
//                    }
                    String tagExpert = jsonObject.getString("expertTag");
                    if(StringUtils.isBlank(tagExpert)){
                        tagExpert = "";
                    }
                    JSONArray tagModelArr = jsonObject.getJSONArray("modelTag");
                    String tagModel = "";
                    if (tagModelArr != null){
                        tagModel = StringUtils.join(tagModelArr, ";");
                    }
                    Instance instance = new Instance();
                    instance.setInstanceId(instanceId);
//                    instance.setTagDefault(tagDefault);
                    instance.setTagExpert(tagExpert);
                    instance.setTagModel(tagModel);
                    instSet.add(instance);
                }

                // 分批次修改数据（修改标签值）
                if (!instSet.isEmpty()) {
                    List<Instance> instBuf = new ArrayList<>(BatchSize);
                    for (Instance inst : instSet) {
                        instBuf.add(inst);
                        if (instBuf.size() == BatchSize) {
                            instanceService.updateAll(instBuf);
                            instBuf.clear();
                        }
                    }
                    if (!instBuf.isEmpty()) {
                        instanceService.updateAll(instBuf);
                    }
                }

                return new RespEntity<>(RespStatus.SUCCESS, Boolean.TRUE);
            }

            return new RespEntity<>(RespStatus.InvalidFormat, Boolean.FALSE);
        }

        if (multiFile.getOriginalFilename().endsWith("zip")) {
//            File tmpDir = new File(tempDir);
//            if (!tmpDir.exists()){
//                boolean isDone = tmpDir.mkdirs();
//                if (!isDone){
//                    return new RespEntity(RespStatus.Error);
//                }
//            }
//            FileUtils.cleanDirectory(tmpDir);
//            File tmpFile = new File(tmpDir.getAbsolutePath(), multiFile.getOriginalFilename());
//            multiFile.transferTo(tmpFile);
//            // 读取zip文件
//            ZipFile zf = new ZipFile(tmpFile);
//            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(tmpFile)));

            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(multiFile.getInputStream()));
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                if (ze.getName().endsWith("txt") || ze.getName().endsWith("csv")) {
                    System.out.println(String.format("name:%s size:%d", ze.getName(), ze.getSize()));
//                    BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze), Charset.forName("gbk")));
                    BufferedReader br = new BufferedReader(new InputStreamReader(zis, Charset.forName("gbk")));
                    String line;
                    while ((line = br.readLine()) != null) {
                        line = StringUtils.trimToEmpty(line);  //过滤两端空格
                        if (!StringUtils.isAllBlank(line)){
//                            instSet.add(line);
                            String defaultTag = tagArr[RandomUtils.nextInt(0, tagArr.length)];
                            instSet.add(new Instance(taskName, defaultTag, "", "", 0, 0, line));
                        }
                    }
                }
            }

        } else if(multiFile.getOriginalFilename().endsWith("txt") || multiFile.getOriginalFilename().endsWith("csv")){
            // 读取普通的txt和csv文件
            BufferedReader br = new BufferedReader(new InputStreamReader(multiFile.getInputStream(), Charset.forName("gbk")));
            String line;
            while ((line=br.readLine()) != null){
                line = StringUtils.trimToEmpty(line);
                if (!StringUtils.isAllBlank(line)){
//                    instSet.add(line);
                    String defaultTag = tagArr[RandomUtils.nextInt(0, tagArr.length)];
                    instSet.add(new Instance(taskName, defaultTag, "", "", 0, 0, line));
                }
            }

        }

        // 分批次将数据保存到DB中
        if (!instSet.isEmpty()){
            List<Instance> instBuf = new ArrayList<>(BatchSize);
            for(Instance inst: instSet){
                instBuf.add(inst);
                if (instBuf.size() == BatchSize){
                    instanceService.saveAll(instBuf);
                    instBuf.clear();
                }
            }
            if (!instBuf.isEmpty()) {
                instanceService.saveAll(instBuf);
            }

            // 保存新任务
            taskService.save(new Task(taskName, dataType, instSet.size(), StringUtils.join(tagArr, ";"), separator, username, false));
        }

//        if (!instSet.isEmpty()){
//            ArrayList<Instance> insts = new ArrayList<>(BatchSize);
//            for(String item: instSet){
//                insts.add(new Instance(counter, taskName, item, "", "", 0, 0));
//                counter ++;
//                if (insts.size() == BatchSize){
//                    instanceService.saveAll(insts);
//                    insts.clear();
//                }
//            }
//            if (!insts.isEmpty()) {
//                instanceService.saveAll(insts);
//            }
//
//            taskService.save(new Task(taskName, dataType, instSet.size(), tags, false));
//        }

        return new RespEntity<>(RespStatus.SUCCESS, Boolean.TRUE);
    }

}
