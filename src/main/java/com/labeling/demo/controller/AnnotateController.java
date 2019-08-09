package com.labeling.demo.controller;

import com.labeling.demo.entity.*;
import com.labeling.demo.service.InstanceService;
import com.labeling.demo.service.InstanceUserService;
import com.labeling.demo.service.TaskService;
import com.labeling.demo.service.TeamService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Random;

@Controller
public class AnnotateController {
    private List<Instance> instanceList = null;
    private Integer counter = 0;
    private InstanceService instanceService;
    private TaskService taskService;
    private TeamService teamService;
    private InstanceUserService instanceUserService;

    @Autowired
    public AnnotateController(InstanceService instanceService, TaskService taskService, TeamService teamService, InstanceUserService instanceUserService) {
        this.instanceService = instanceService;
        this.taskService = taskService;
        this.teamService = teamService;
        this.instanceUserService = instanceUserService;
        System.out.println("构造方法");
    }

    @GetMapping("/annotate")
    public String toAnnotate(Model model){
        Subject curSubj = SecurityUtils.getSubject();
        User curUser = (User) curSubj.getPrincipal();
        String username = curUser.getUsername();
        model.addAttribute("username", username);
        //统计用户工作量
        long work_count = instanceUserService.countByUsername(username);
        model.addAttribute("work_count", work_count);
        if (curSubj.hasRole("admin")){
            return "bg";
        } else {
            //判断当前用户是否加入小组（没有，则返回空页面）
            String teamName = curUser.getTeamName();
            if (StringUtils.isBlank(teamName)){
                return "no_anno";
            }
            //找到该小组分配的任务
            Team team = teamService.findByName(teamName);
            //获取该任务对应的标注数据
            String taskName = team.getTaskName();
            Task task = taskService.findByName(taskName);
            // 最好分页读取！
            instanceList = instanceService.findByTaskName(taskName);
//            Pageable instPage = PageRequest.of(0, 5);  //当前页 pageNum, 每页大小 pageSize
//            Page<Instance> pageData = instanceService.findPageData(instPage);
//            instanceList = pageData.getContent();

            model.addAttribute("taskName", taskName);
            model.addAttribute("dataType", DataType.getTypeByID(task.getDatatype()));
            model.addAttribute("corpusSize", task.getCorpussize());

            String[] tags = StringUtils.split(task.getTags(), ";");
            model.addAttribute("tags", tags);

            counter = 0;
            model.addAttribute("instance", instanceList.get(counter));
            return "annotate";
        }
    }

    @PostMapping("/annotate")
    @ResponseBody
    public RespEntity annotate(@RequestParam("tag") String tag,
                               @RequestParam("cmd") String cmd){
        System.out.println(tag);
        //记录用户对此数据标注的标签
        Subject curSubj = SecurityUtils.getSubject();
        User curUser = (User) curSubj.getPrincipal();
        String username = curUser.getUsername();
        //统计用户工作量


        InstanceUser instanceUser = new InstanceUser();
        Random rd = new Random();
        instanceUser.setId(rd.nextInt(100));
        instanceUser.setInstance_id(2);
        instanceUser.setUsername(username);
        instanceUser.setTag(tag);
        instanceUserService.addInstanceUser(instanceUser);
        long work_count = instanceUserService.countByUsername(username);
        int counter = (int) work_count;
        if(cmd.equalsIgnoreCase("prev")){
            counter --;
        }else if (cmd.equalsIgnoreCase("next")){
            counter ++;
        }

        if (counter <= 0){
            counter = 0;
        }

        if (counter >= instanceList.size()){
            return new RespEntity<>(RespStatus.Over);
        }
        return new RespEntity<>(RespStatus.SUCCESS, instanceList.get(counter),work_count);
    }
}
