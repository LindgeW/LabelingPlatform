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
import org.springframework.data.domain.PageRequest;
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

    //生产随机标签
    private String randTag(Instance instance){
        List<String> tagLst = new ArrayList<>();
        String tagDefault = instance.getTagDefault();
        String tagExpert = instance.getTagExpert();
        String tagModels = instance.getTagModel();

        if (StringUtils.isNotBlank(tagDefault)){
            tagLst.add(tagDefault);
        }

        if (StringUtils.isNotBlank(tagExpert)) {
            tagLst.add(tagExpert);
        }

        if (StringUtils.isNotBlank(tagModels)){
            // 模型预测的k-best结果
            String[] tagModel = StringUtils.split(tagModels, ";");
            Collections.addAll(tagLst, tagModel);
        }

        if (tagLst.isEmpty()) {
            return "";
        }

        System.out.println("gold标签：" + tagLst);
        return tagLst.get(RandomUtils.nextInt(0, tagLst.size()));
    }

//    private InstanceVO fitTask(Instance instance, Task task) {
//        InstanceVO<Object> instanceVO = null;
//        if (TagType.CLASSIFY.getId().equals(task.getDatatype())) {
//            instanceVO = new InstanceVO<>(instance.getInstanceId(), task.getTaskname(), instance.getItem(), randTag(instance));
//        } else if(TagType.NER.getId().equals(task.getDatatype())) {
//            instanceVO = new InstanceVO<>(instance.getInstanceId(), task.getTaskname(), StringUtils.split(instance.getItem(), " "), randTag(instance));
//        }
//
//        return instanceVO;
//    }

    private InstanceUserVO fitTask(Instance instance, Task task) {
        InstanceUserVO<Object> instanceUserVO = new InstanceUserVO<>();
        instanceUserVO.setInstanceId(instance.getInstanceId());
        instanceUserVO.setTaskname(task.getTaskname());
        if (TagType.CLASSIFY.getId().equals(task.getDatatype())) {
            instanceUserVO.setItem(instance.getItem());
            instanceUserVO.setTag(randTag(instance));
        } else if(TagType.NER.getId().equals(task.getDatatype())) {
            instanceUserVO.setItem(StringUtils.split(instance.getItem(), " "));
//            instanceUserVO.setTag(randTag(instance));
        }

        return instanceUserVO;
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
        Task task = taskService.findByName(team.getTaskName());
        TaskVO taskVo = new TaskVO(task);

        Integer tagNum = instanceUserService.countByTask(username, task.getTaskname());  //统计当前用户对当前任务已标注的数量

        //将小组信息存入session
        Session sess = curSubj.getSession();
        sess.setAttribute("team", team);
        sess.setAttribute("taskVo", taskVo);

        UserVO userVO = new UserVO(username, role, tagNum);
        //当前页 pageNum, 每页大小 pageSize
        List<Instance> pageData = instanceService.findPageDataByTaskName(task.getTaskname(), PageRequest.of(tagNum, 1));
        // 判断当前用户是否已经完成小组任务
        if (pageData.isEmpty()){
            model.addAttribute("userVo", userVO);
            return "finished";
        }

        Instance firstInstance = pageData.get(0);
//        InstanceVO instanceVO = fitTask(firstInstance, task);
        InstanceUserVO instanceUserVO = fitTask(firstInstance, task);

        model.addAttribute("userVo", userVO);
        model.addAttribute("taskVo", taskVo);
        model.addAttribute("instanceVo", instanceUserVO);
        return TagType.getTypeByID(task.getDatatype()).getUrl();
    }

    @PostMapping("/annotate")
    @ResponseBody
    public RespEntity annotate(UserVO userVO, InstanceUserVO<Object> instanceVO){
        System.out.println(userVO);
        System.out.println(instanceVO);

        //记录用户对此数据标注的标签
        String username = userVO.getUsername();
        String taskName = instanceVO.getTaskname();
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
//        ArrayList<String> taglist = new ArrayList<String>();
//            List<InstanceUser> instTaglist =  instanceUserService.findInstanceUserById(instanceVO.getInstanceId());
//            //为数据打上最终标签且改变状态
//            for(InstanceUser item :instTaglist){
//                System.out.println(item.getTag());
//                taglist.add(item.getTag());
//            }
//            FindMostElm findmostElm = new FindMostElm();
//            //找到最终的标签
//            String finaltag = findmostElm.findfinaltag(taglist);
//            System.out.println(finaltag);
//            //为此数据打上最终标签并修改它的状态
//            instance.setTag(finaltag);

            //1为已标状态
            instance.setStatus(1);
        }

        if ("admin".equalsIgnoreCase(role)){
            instance.setTagExpert(tag);
        }
        instanceService.save(instance);

        //保存标注记录
        InstanceUser instanceUser = new InstanceUser();
        instanceUser.setInstanceId(instanceId);
        instanceUser.setUsername(username);
        instanceUser.setTaskname(taskName);
        instanceUser.setResponseTime(respTime);
        instanceUser.setTag(tag);
        instanceUserService.addInstanceUser(instanceUser);

        // 取下一个数据
        int tagNum = userVO.getTagNum() + 1;
        UserVO curUser = new UserVO(username, role, tagNum);
        List<Instance> pageData = instanceService.findPageDataByTaskName(taskName, PageRequest.of(tagNum, 1));
//        Instance nextInstance = instanceService.findPageDataByTaskNameRand(username ,taskName, PageRequest.of(tagNum, 1));

        if (pageData.isEmpty()){
            List<Instance>instanceList = instanceService.findByTaskName(taskName);
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

        Instance nextInstance = pageData.get(0);
//        InstanceVO nextInstVo = fitTask(nextInstance, taskVO);
        InstanceUserVO nextInstVo = fitTask(nextInstance, taskVO);

        Map<String, Object> respMap = new HashMap<>();
        respMap.put("curUser", curUser);
        respMap.put("nextInst", nextInstVo);
        return new RespEntity<>(RespStatus.SUCCESS, respMap);
    }
}
