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

    private Integer randGenerator(Integer bound){
        Random random = new Random();
        return random.nextInt(bound);
    }

    //生产随机标签
    private String randTag(Instance instance){
        List<String> tagLst = new ArrayList<>();
        String tagExpert = instance.getTagExpert();
        String tagModels = instance.getTagModel();

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
        return tagLst.get(randGenerator(tagLst.size()));
    }

    @GetMapping("/annotate")
    public String toAnnotate(Model model){
        Subject curSubj = SecurityUtils.getSubject();

//        if (curSubj.hasRole("admin")){
//            return "redirect:/bg";
//        }

        User curUser = (User) curSubj.getPrincipal();
        String username = curUser.getUsername();
        String role = curUser.getRole();
        String teamName = curUser.getTeamName();

        //判断当前用户是否加入小组（没有，则返回空页面）
        if (StringUtils.isBlank(teamName)){
            model.addAttribute("userVo", new UserVO(username, role));
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
//            System.out.println(pageData.isLast());
//            System.out.println(pageData.hasContent());
//            System.out.println(pageData.hasNext());
        // 判断当前用户是否已经完成小组任务
        if (pageData.isEmpty()){
            model.addAttribute("userVo", userVO);
            return "finished";
        }

        Instance firstInstance = pageData.get(0);
        InstanceVO instanceVO = new InstanceVO(firstInstance.getInstanceId(), task.getTaskname(), firstInstance.getItem(), randTag(firstInstance));
        model.addAttribute("userVo", userVO);
        model.addAttribute("taskVo", taskVo);
        model.addAttribute("instanceVo", instanceVO);
        return "annotate";
    }

    @PostMapping("/annotate")
    @ResponseBody
    public RespEntity annotate(UserVO userVO, InstanceVO instanceVO){
        System.out.println(userVO);
        System.out.println(instanceVO);

        //记录用户对此数据标注的标签
        String username = userVO.getUsername();
        String taskName = instanceVO.getTaskName();
        String role = userVO.getRole();
        Long instanceId = instanceVO.getInstanceId();
        String tag = instanceVO.getTag();

        Instance instance = instanceService.findById(instanceId);
        //如果当前数据未标满，则tagnum+1
        instance.setTagNum(instance.getTagNum()+1);
        if (instance.getTagNum() == 3) {
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
        instanceUser.setTag(tag);
        instanceUserService.addInstanceUser(instanceUser);


        // 取下一个数据
        int tagNum = userVO.getTagNum() + 1;
        UserVO curUser = new UserVO(username, role, tagNum);
        List<Instance> pageData = instanceService.findPageDataByTaskName(taskName, PageRequest.of(tagNum, 1));
        if (pageData.isEmpty()){
            Session session = SecurityUtils.getSubject().getSession();
            Team team = (Team) session.getAttribute("team");
            TaskVO taskVO = (TaskVO) session.getAttribute("taskVo");

            List<Instance>instanceList = instanceService.findByTaskName(taskName);
            boolean flag = true;
            for(Instance item: instanceList){
                if (item.getStatus() == 0){
                    flag = false;
                    break;
                }
            }
            if (flag){
                //任务完成将队伍和任务的状态都改变
                team.setStatus(true);
                taskVO.setStatus(true);
                teamService.updateByTeamName(team);
                taskService.updateByName(taskVO);
            }

            return new RespEntity<>(RespStatus.Over, curUser);
        }

        Instance nextInstance = pageData.get(0);
        InstanceVO nextInst = new InstanceVO(nextInstance.getInstanceId(), nextInstance.getTaskName(), nextInstance.getItem(), randTag(nextInstance));

        Map<String, Object> respMap = new HashMap<>();
        respMap.put("curUser", curUser);
        respMap.put("nextInst", nextInst);
        return new RespEntity<>(RespStatus.SUCCESS, respMap);
    }
}
