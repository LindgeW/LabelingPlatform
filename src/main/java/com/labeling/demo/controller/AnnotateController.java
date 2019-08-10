package com.labeling.demo.controller;

import com.labeling.demo.entity.*;
import com.labeling.demo.entity.vo.InstanceVO;
import com.labeling.demo.entity.vo.TaskVO;
import com.labeling.demo.entity.vo.UserVO;
import com.labeling.demo.service.InstanceService;
import com.labeling.demo.service.InstanceUserService;
import com.labeling.demo.service.TaskService;
import com.labeling.demo.service.TeamService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AnnotateController {
//    private List<Instance> instanceList = null;
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
    }

    @GetMapping("/annotate")
    public String toAnnotate(Model model){
        Subject curSubj = SecurityUtils.getSubject();

        if (curSubj.hasRole("admin")){
            return "redirect:/bg";
        } else {
            User curUser = (User) curSubj.getPrincipal();
            String username = curUser.getUsername();

            //判断当前用户是否加入小组（没有，则返回空页面）
            String teamName = curUser.getTeamName();
            if (StringUtils.isBlank(teamName)){
                return "no_anno";
            }
            //找到该小组分配的任务
            Team team = teamService.findByName(teamName);
            //获取该任务对应的标注数据
            Task task = taskService.findByName(team.getTaskName());
            // 分页读取！
//            instanceList = instanceService.findByTaskName(taskName);
            Integer tagNum = instanceUserService.countByUsername(username);  //统计用户工作量
            Page<Instance> pageData = instanceService.findPageData(PageRequest.of(tagNum, 1));  //当前页 pageNum, 每页大小 pageSize
            Instance firstInstance = pageData.getContent().get(0);

            TaskVO taskVo = new TaskVO(task);
            UserVO userVO = new UserVO(username, tagNum);
            InstanceVO instanceVO = new InstanceVO(firstInstance.getInstanceId(), firstInstance.getItem(), firstInstance.getTag());
            model.addAttribute("taskVo", taskVo);
            model.addAttribute("userVo", userVO);
            model.addAttribute("instanceVo", instanceVO);

            return "annotate";
        }
    }

    @PostMapping("/annotate")
    @ResponseBody
    public RespEntity annotate(@RequestParam("username") String username,
                               @RequestParam("instanceId") Long instId,
                               @RequestParam("tag") String tag,
                               @RequestParam("tagNum") Integer tagNum,
                               @RequestParam("cmd") String cmd){
        System.out.println(tag);

        //记录用户对此数据标注的标签
        InstanceUser instanceUser = new InstanceUser();
        instanceUser.setInstanceId(instId);
        instanceUser.setUsername(username);
        instanceUser.setTag(tag);
        instanceUserService.addInstanceUser(instanceUser);

        // 取下一个数据
        tagNum ++;
        Instance nextInstance = instanceService.findPageData(PageRequest.of(tagNum, 1)).getContent().get(0);
        if (nextInstance == null){
            return new RespEntity(RespStatus.Over);
        }

        Map<String, Object> respMap = new HashMap<>();
        respMap.put("instId", nextInstance.getInstanceId());
        respMap.put("item", nextInstance.getItem());
        respMap.put("tagNum", tagNum);
        return new RespEntity<>(RespStatus.SUCCESS, respMap);
    }
}
