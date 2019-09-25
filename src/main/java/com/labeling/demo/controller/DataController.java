package com.labeling.demo.controller;

import com.alibaba.fastjson.*;
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
    private static final int BatchSize = 800;
    private static final String root = "bg/";

    private InstanceService instanceService;
    private TaskService taskService;
    private InstanceUserService instanceUserService;
    private DataTypeService dataTypeService;

    @Autowired
    public DataController(InstanceService instanceService, TaskService taskService, InstanceUserService instanceUserService, DataTypeService dataTypeService) {
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
    public void exportData(@RequestParam("taskId") Integer taskId,
                           HttpServletResponse response) throws IOException {
        /*
            原始数据
            专家标签
            用户标签
            模型标签（暂不需要）
         */
        Task task = taskService.findById(taskId);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);  //APPLICATION_OCTET_STREAM_VALUE
        response.setHeader("Content-Disposition", String.format("attachment; filename=%s.json", new String(task.getTaskname().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)));

//        BufferedOutputStream bufOs = new BufferedOutputStream(response.getOutputStream());
        //根据任务查团队(一个团队一个任务，一个任务可以多个团队)
        List<Instance> taskInsts = instanceService.findByTaskId(taskId);
//        Set<ExportVO> exportVOs = new HashSet<>();
//        for (Instance instance: taskInsts){ //导出相关任务的所有数据
//            //根据数据找到标注者的标注记录
//            List<InstanceUserVO> records = instanceUserService.findFullRecords(instance.getInstanceId());
//            ExportVO exportVO = new ExportVO(instance.getInstanceId(), instance.getItem(), instance.getDefaultTag(), instance.getExpertTag(), records);
//            exportVOs.add(exportVO);
//        }
//        bufOs.write(JSONArray.toJSONBytes(exportVOs));
//        bufOs.flush();
//        bufOs.close();

        JSONWriter jsonWriter = new JSONWriter(new BufferedWriter(new OutputStreamWriter(response.getOutputStream())));
        jsonWriter.startArray();
        for (Instance instance: taskInsts){ //导出相关任务的所有数据
            //根据数据找到标注者的标注记录
            List<InstanceUserVO> records = instanceUserService.findFullRecords(instance.getInstanceId());
            ExportVO exportVO = new ExportVO(instance.getInstanceId(), instance.getItem(), instance.getDefaultTag(), instance.getExpertTag(), records);
            jsonWriter.writeValue(exportVO);
        }
        jsonWriter.endArray();
        jsonWriter.close();
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

        Set<Instance> instSet = new HashSet<>(0);
        //任务名已存在且是同一个人创建的 -> 修改
        if (task != null && username.equals(task.getExpertname())){
            if(multiFile.getOriginalFilename().endsWith("json")){
                /*  读取效率低  */
//                String jsonStr = IOUtils.toString(multiFile.getInputStream());
//                JSONArray jsonArray = JSONArray.parseArray(jsonStr);
//                for (int i = 0, len=jsonArray.size(); i < len; i++) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    Long instanceId = jsonObject.getLong("instanceId");
////                    String tagDefault = jsonObject.getString("defaultTag");
//                    String tagExpert = jsonObject.getString("expertTag");
//                    JSONArray tagModelArr = jsonObject.getJSONArray("modelTag");
//                    String tagModel = "";
//                    if (tagModelArr != null){
//                        tagModel = StringUtils.join(tagModelArr, ";");
//                    }
//                    Instance instance = new Instance();
//                    instance.setInstanceId(instanceId);
//                    instance.setExpertTag(tagExpert);
//                    instance.setModelTag(tagModel);
//                    instSet.add(instance);
//                }

                JSONReader jsonReader = new JSONReader(new BufferedReader(new InputStreamReader(multiFile.getInputStream())));
                jsonReader.startArray();
                while(jsonReader.hasNext()){
                    Instance instance = jsonReader.readObject(Instance.class);
                    if (instance.getInstanceId() != null) {
                        instSet.add(instance);
                    }
                }
                jsonReader.endArray();
                jsonReader.close();

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

        if (StringUtils.isBlank(tags)) {
            return new RespEntity<>("标签值为空！", 405, Boolean.FALSE);
        }

        String[] tagArr = StringUtils.split(tags, "\n");
        // 清除每个标签值中的空白字符
        for (int i = 0; i < tagArr.length; i++) {
            tagArr[i] = StringUtils.trimToEmpty(tagArr[i]);
        }

        Integer taskId = taskService.taskCount() + 1;
        if (multiFile.getOriginalFilename().endsWith("json")) {  //用于上传数据带标签的情况
            /* 将数据全部读到内存，效率低 */
//            String jsonStr = IOUtils.toString(new BufferedInputStream(multiFile.getInputStream()));
//            JSONArray jsonArray = JSONArray.parseArray(jsonStr);
//            for (int i = 0, len=jsonArray.size(); i < len; i++) {
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                String item = jsonObject.getString("item");
//                String tagDefault = jsonObject.getString("defaultTag");
////                if(StringUtils.isBlank(tagDefault)){
////                    tagDefault = "";
////                }
//                Instance instance = new Instance();
//                instance.setTaskId(taskId);
//                instance.setItem(item);
//                instance.setDefaultTag(tagDefault);

            JSONReader jsonReader = new JSONReader(new BufferedReader(new InputStreamReader(multiFile.getInputStream())));
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                Instance instance = jsonReader.readObject(Instance.class);
                if (StringUtils.isNotBlank(instance.getItem())) {
                    instance.setTaskId(taskId);
                    instSet.add(instance);
                }
            }
            jsonReader.endArray();
            jsonReader.close();
        } else if (multiFile.getOriginalFilename().endsWith("zip")) {
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
                            String defaultTag = tagArr[RandomUtils.nextInt(0, tagArr.length)];
                            instSet.add(new Instance(taskId, defaultTag, "", "", 0, 0, line));
                        }
                    }
                } else {
                    return new RespEntity<>(RespStatus.InvalidFormat, Boolean.FALSE);
                }
            }
        } else if(multiFile.getOriginalFilename().endsWith("txt") || multiFile.getOriginalFilename().endsWith("csv")){
            // 读取普通的txt和csv文件
            BufferedReader br = new BufferedReader(new InputStreamReader(multiFile.getInputStream(), Charset.forName("gbk")));
            String line;
            while ((line=br.readLine()) != null){
                line = StringUtils.trimToEmpty(line);
                if (!StringUtils.isAllBlank(line)){
                    String defaultTag = tagArr[RandomUtils.nextInt(0, tagArr.length)];
                    instSet.add(new Instance(taskId, defaultTag, "", "", 0, 0, line));
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
            Task newTask = new Task(taskId, taskName, dataType, instSet.size(), StringUtils.join(tagArr, ";"), separator, username, false);
            taskService.save(newTask);
        } else{
            return new RespEntity<>(RespStatus.Error, Boolean.FALSE);
        }
        return new RespEntity<>(RespStatus.SUCCESS, Boolean.TRUE);
    }

}
