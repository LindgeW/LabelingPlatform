package com.labeling.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.labeling.demo.entity.*;
import com.labeling.demo.entity.vo.ExportVO;
import com.labeling.demo.service.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Controller
public class DataController {
    private static final String tempDir = "src/main/resources/temp";
    private static final int BatchSize = 500;

    private UserService userService;
    private InstanceService instanceService;
    private TaskService taskService;
    private InstanceUserService instanceUserService;

    @Autowired
    public DataController(UserService userService, InstanceService instanceService, TaskService taskService, InstanceUserService instanceUserService) {
        this.userService = userService;
        this.instanceService = instanceService;
        this.taskService = taskService;
        this.instanceUserService = instanceUserService;
    }

    @GetMapping("/upload")
    @RequiresRoles("admin")
    public String toUpload(){
//        counter = instanceService.count();
        return "upload";
    }

    @GetMapping("/export")
    @RequiresRoles("admin")
    public String toExport(Model model){
        List<Task> tasks = taskService.findAll();
        model.addAttribute("tasks", tasks);
        return "export";
    }

    @GetMapping("/exportData")
    @RequiresRoles("admin")
    public void exportData(@RequestParam("task") String taskName,
                           @RequestParam("type") String type,
                           HttpServletResponse response) throws IOException {
        System.out.println(taskName);
        System.out.println(type);
        /*
            原始数据
            专家标签
            用户标签
            模型标签（暂不需要）
         */
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);  //APPLICATION_OCTET_STREAM_VALUE
        response.setHeader("Content-Disposition", String.format("attachment; filename=%s.json", new String(taskName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)));
        BufferedOutputStream bufOs = new BufferedOutputStream(response.getOutputStream());

        //根据团队找成员
//        String[] userNames = StringUtils.split(team.getMembers(),";");
//        //根据成员找标注记录
//        Set<ExportVO> exportVOs = new HashSet<>();
//        for (String username: userNames) {
//            List<InstanceUser> records = instanceUserService.findByUserName(username);
//            for (InstanceUser record: records) {
//                Instance instance = instanceService.findById(record.getInstanceId());
//                ExportVO exportVO = new ExportVO(instance.getItem(), instance.getTagExpert(), record.getUsername(), record.getTag());
//                exportVOs.add(exportVO);
//            }
//        }

        //根据任务查团队(一个团队一个任务，一个任务可以多个团队)
        List<Instance> taskInsts = instanceService.findByTaskName(taskName);
        Set<ExportVO> exportVOs = new HashSet<>();
        for (Instance instance: taskInsts){
            List<InstanceUser> records = instanceUserService.findInstanceUserById(instance.getInstanceId());
            if (records.isEmpty()){
                ExportVO exportVO = new ExportVO(instance.getItem(), instance.getTagExpert(), "", "", null);
                exportVOs.add(exportVO);
            } else {
                for (InstanceUser record : records) {
                    if("admin".equalsIgnoreCase(type) || !"admin".equalsIgnoreCase(userService.findUser(record.getUsername()).getRole())) {
                        ExportVO exportVO = new ExportVO(instance.getItem(), instance.getTagExpert(), record.getUsername(), record.getTag(), record.getTagTime());
                        exportVOs.add(exportVO);
                    }
                }
            }
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
                             @RequestParam("tags") String tags) throws IOException {
        if(multiFile==null || multiFile.isEmpty()){
            return new RespEntity(RespStatus.Error);
        }

        System.out.println(multiFile.getOriginalFilename());
        System.out.println(multiFile.getName());
        System.out.println(multiFile.getContentType());
        System.out.println(multiFile.getSize());
        System.out.println(tags);

        Task task = taskService.findByName(taskName);
        if (task != null){  //任务名已存在
            return new RespEntity<>(RespStatus.Error, Boolean.FALSE);
        }

//        Set<String> instSet = new HashSet<>(0);
        Set<Instance> instSet = new HashSet<>(0);
        if (multiFile.getOriginalFilename().endsWith("zip")) {
            File tmpDir = new File(tempDir);
            if (!tmpDir.exists()){
                boolean isDone = tmpDir.mkdirs();
                if (!isDone){
                    return new RespEntity(RespStatus.Error);
                }
            }

            FileUtils.cleanDirectory(tmpDir);
            File tmpFile = new File(tmpDir.getAbsolutePath(), multiFile.getOriginalFilename());
            multiFile.transferTo(tmpFile);

            // 读取zip文件
            ZipFile zf = new ZipFile(tmpFile);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(tmpFile)));
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                if (ze.getName().endsWith("txt") || ze.getName().endsWith("csv")) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze), Charset.forName("gbk")));
                    String line;
                    while ((line = br.readLine()) != null) {
                        line = StringUtils.trimToEmpty(line);  //过滤两端空格
                        if (!StringUtils.isAllBlank(line)){
//                            instSet.add(line);
                            instSet.add(new Instance(taskName, line, "", "", 0, 0));
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
                    instSet.add(new Instance(taskName, line, "", "", 0, 0));
                }
            }

        } else if(multiFile.getOriginalFilename().endsWith("json")){
            String jsonStr = IOUtils.toString(multiFile.getInputStream());
            JSONArray jsonArray = JSON.parseArray(jsonStr);

            for (int i = 0, len=jsonArray.size(); i < len; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String item = jsonObject.getString("raw");
                String tagExpert = jsonObject.getString("expertTag");
                if(StringUtils.isBlank(tagExpert)){
                    tagExpert = "";
                }
                JSONArray tagModelArr = jsonObject.getJSONArray("modelTag");
                String tagModel = "";
                if (tagModelArr != null){
                    tagModel = StringUtils.join(tagModelArr, ";");
                }
                instSet.add(new Instance(taskName, item, tagExpert, tagModel, 0, 0));
            }
        }

        // 分批次将数据保存到MongoDB中
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

            taskService.save(new Task(taskName, dataType, instSet.size(), tags, false));
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
