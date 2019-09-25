package com.labeling.demo.controller;

import com.labeling.demo.entity.*;
import com.labeling.demo.entity.vo.InstanceUserVO;
import com.labeling.demo.entity.vo.InstanceVO;
import com.labeling.demo.entity.vo.TaskVO;
import com.labeling.demo.entity.vo.UserVO;
import com.labeling.demo.service.InstanceService;
import com.labeling.demo.service.InstanceUserService;
import com.labeling.demo.service.TaskService;
import com.labeling.demo.service.TeamService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class AnnotateController {
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

        User curUser = (User) curSubj.getPrincipal();
        String username = curUser.getUsername();
        String role = curUser.getRole();
        String teamName = curUser.getTeamName();

        //判断当前用户是否加入小组（没有，则返回空页面）
        if (StringUtils.isBlank(teamName)){
            model.addAttribute("userVo", new UserVO(username, role, 0));
            return "no_task";
        }

        //找到该小组分配的任务
        Team team = teamService.findByName(teamName);
        //获取该任务对应的标注数据
        Integer taskId = team.getTaskId();
        Task task = taskService.findById(taskId);
        TaskVO taskVo = new TaskVO(task);

        Integer tagNum = instanceUserService.countByTask(username, taskId);  //统计当前用户对当前任务已标注的数量

        //将小组信息存入session
        Session sess = curSubj.getSession();
        sess.setAttribute("team", team);
        sess.setAttribute("taskVo", taskVo);

        UserVO userVO = new UserVO(username, role, tagNum);
        //当前页 pageNum, 每页大小 pageSize
        List<Instance> pageData = instanceService.findPageDataByTaskId(taskId, Pager.of(tagNum, 1));
        // 判断当前用户是否已经完成小组任务
        if (pageData.isEmpty()){
            model.addAttribute("userVo", userVO);
            return "finished";
        }

        InstanceVO firstInstance = instanceService.fitTask(pageData.get(0), task);
        model.addAttribute("userVo", userVO);
        model.addAttribute("taskVo", taskVo);
        model.addAttribute("instanceVo", firstInstance);
        return taskService.pageScheduler(task);
    }

    @PostMapping("/annotate")
    @ResponseBody
    public RespEntity annotate(UserVO userVO, InstanceUserVO<Object> instanceVO){
        //记录用户对此数据标注的标签
        String username = userVO.getUsername();
        Integer taskId = instanceVO.getTaskId();
        String role = userVO.getRole();
        Long instanceId = instanceVO.getInstanceId();
        String tag = instanceVO.getTag();
        Float respTime = instanceVO.getResponseTime();

        Instance instance = instanceService.findById(instanceId);
        //如果当前数据未标满，则tagnum+1
        instance.setTagNum(instance.getTagNum()+1);

        Session session = SecurityUtils.getSubject().getSession();
        Team team = (Team) session.getAttribute("team");
        TaskVO taskVO = (TaskVO) session.getAttribute("taskVo");
        String[] members = StringUtils.split(team.getMembers(), ";");
        //判断该数据是否完成标注，完成置成1
        if (instance.getTagNum() == members.length) {

            //1为已标状态
            instance.setStatus(1);
        }

        if (Role.ADMIN == Role.valueOf(role.toUpperCase())){
            instance.setExpertTag(tag);
        }
        instanceService.save(instance);

        //保存标注记录
        InstanceUser instanceUser = new InstanceUser();
        instanceUser.setInstanceId(instanceId);
        instanceUser.setUsername(username);
        instanceUser.setTaskId(taskId);
        instanceUser.setResponseTime(respTime);
        instanceUser.setTag(tag);
        instanceUserService.addInstanceUser(instanceUser);

        // 取下一个数据
        int tagNum = userVO.getTagNum() + 1;
        UserVO curUser = new UserVO(username, role, tagNum);
        List<Instance> pageData = instanceService.findPageDataByTaskId(taskId, Pager.of(tagNum, 1));

        if (pageData.isEmpty()){
            List<Instance>instanceList = instanceService.findByTaskId(taskId);
            boolean flag = true;
            for(Instance item: instanceList){  //所有的数据状态都为1，才算完成任务
                if (item.getStatus() == 0){
                    flag = false;
                    break;
                }
            }
            if (flag){
                //任务完成将队伍和任务的状态都改变，变成1
                team.setStatus(true);
                taskVO.setStatus(true);
                teamService.updateTeam(team);
                taskService.updateTask(taskVO);
            }

            return new RespEntity<>(RespStatus.Over, curUser);
        }

        InstanceVO nextInstVo = instanceService.fitTask(pageData.get(0), taskVO);

        Map<String, Object> respMap = new HashMap<>();
        respMap.put("curUser", curUser);
        respMap.put("nextInst", nextInstVo);
        return new RespEntity<>(RespStatus.SUCCESS, respMap);
    }
}
