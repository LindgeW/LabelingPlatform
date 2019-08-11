package com.labeling.demo.controller;

import com.labeling.demo.entity.*;
import com.labeling.demo.entity.vo.InstanceVO;
import com.labeling.demo.entity.vo.TaskVO;
import com.labeling.demo.entity.vo.UserVO;
import com.labeling.demo.service.InstanceService;
import com.labeling.demo.service.InstanceUserService;
import com.labeling.demo.service.TaskService;
import com.labeling.demo.service.TeamService;
import com.labeling.demo.util.FindMostElm;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AnnotateController {
//    private List<InstanceUser> instUserList = null;
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
                model.addAttribute("username", username);
                return "no_task";
            }

            // 判断当前用户是否已经完成小组任务
            Integer tagNum = instanceUserService.countByUsername(username);  //统计当前用户已标记的数量
            Page<Instance> pageData = instanceService.findPageData(PageRequest.of(tagNum, 1));  //当前页 pageNum, 每页大小 pageSize
//            System.out.println(pageData.isLast());
//            System.out.println(pageData.hasContent());
//            System.out.println(pageData.hasNext());
            if (!pageData.hasContent()){
                model.addAttribute("username", username);
                return "finished";
            }
            Instance firstInstance = pageData.getContent().get(0);
            InstanceVO instanceVO = new InstanceVO(firstInstance.getInstanceId(), firstInstance.getItem(), firstInstance.getTag());

            //找到该小组分配的任务
            Team team = teamService.findByName(teamName);
            //获取该任务对应的标注数据
            Task task = taskService.findByName(team.getTaskName());

            TaskVO taskVo = new TaskVO(task);
            UserVO userVO = new UserVO(username, tagNum);

            model.addAttribute("taskVo", taskVo);
            model.addAttribute("userVo", userVO);
            model.addAttribute("instanceVo", instanceVO);

            return "annotate";
        }
    }

    @PostMapping("/annotate")
    @ResponseBody
    public RespEntity annotate(UserVO userVO, InstanceVO instanceVO){
        System.out.println(userVO);
        System.out.println(instanceVO);
        ArrayList<String> taglist = new ArrayList<String>();
        //记录用户对此数据标注的标签
        InstanceUser instanceUser = new InstanceUser();
        instanceUser.setUsername(userVO.getUsername());
        instanceUser.setInstanceId(instanceVO.getInstanceId());
        instanceUser.setTag(instanceVO.getTag());
        instanceUserService.addInstanceUser(instanceUser);
        //批量插入(待解决)
       /* batch_list.add(instanceUser);
        if (batch_list.size() == batch_size)
        {
            instanceUserService.saveBtach(batch_list);

        }*/


        Instance instance = instanceService.findInstById(instanceVO.getInstanceId());

        //如果当前数据未标满，则tagnum+1
        instance.setTagNum(instance.getTagNum()+1);
        instanceService.save(instance);
        if (instance.getTagNum() == 3) {
            List<InstanceUser> instTaglist =  instanceUserService.findInstanceUserById(instanceVO.getInstanceId());
            //为数据打上最终标签且改变状态
            for(InstanceUser item :instTaglist){
                System.out.println(item.getTag());
                taglist.add(item.getTag());
            }

            FindMostElm findmostElm = new FindMostElm();
            //找到最终的标签
            String finaltag = findmostElm.findfinaltag(taglist);
            System.out.println(finaltag);
            //为此数据打上最终标签并修改它的状态
            instance.setTag(finaltag);
            //1为已标状态
            instance.setStatus(1);
            instanceService.save(instance);


        }

        // 取下一个数据
        int tagNum = userVO.getTagNum() + 1;
        UserVO curUser = new UserVO(userVO.getUsername(), tagNum);

        Page<Instance> pageData = instanceService.findPageData(PageRequest.of(tagNum, 1));
//        System.out.println(pageData.hasContent());
        if (!pageData.hasContent()){
            return new RespEntity<>(RespStatus.Over, curUser);
        }
        Instance nextInstance = pageData.getContent().get(0);
        InstanceVO nextInst = new InstanceVO(nextInstance.getInstanceId(), nextInstance.getItem(), nextInstance.getTag());

        Map<String, Object> respMap = new HashMap<>();
        respMap.put("curUser", curUser);
        respMap.put("nextInst", nextInst);

        return new RespEntity<>(RespStatus.SUCCESS, respMap);
    }
}
