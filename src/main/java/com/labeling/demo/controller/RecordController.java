package com.labeling.demo.controller;

import com.labeling.demo.entity.*;
import com.labeling.demo.entity.vo.InstanceUserVO;
import com.labeling.demo.entity.vo.TaskVO;
import com.labeling.demo.service.InstanceService;
import com.labeling.demo.service.InstanceUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class RecordController {
    private final static String root = "history/";
    private InstanceService instanceService;
    private InstanceUserService instanceUserService;

    @Autowired
    public RecordController(InstanceService instanceService, InstanceUserService instanceUserService) {
        this.instanceService = instanceService;
        this.instanceUserService = instanceUserService;
    }

    @RequestMapping("/history")
    public String history(Model model){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        //获取当前用户的任务信息
        Session session = subject.getSession();
        TaskVO taskVO = (TaskVO) session.getAttribute("taskVo");
        model.addAttribute("taskVo", taskVO);
        model.addAttribute("userVo", user);

        String userName = user.getUsername();
        Integer taskId = taskVO.getTaskId();
        if (TagType.NER == TagType.getTypeByID(taskVO.getDatatype())){
            InstanceUser record = instanceUserService.findByPage(userName, taskId, Pager.of(0, 1)).get(0);
            Integer totalRows = instanceUserService.countByTask(userName, taskId);
            Instance instance = instanceService.findById(record.getInstanceId());
            String[] items = StringUtils.split(instance.getItem(), " ");
            model.addAttribute("instanceVo", new InstanceUserVO<>(record, items, null));
            model.addAttribute("pager", Pager.of(0, 1, totalRows));
            return root+"ner_history";
        }

        return root+"history";
    }

    @PostMapping("/list")
    @ResponseBody
    public Map<String, Object> personalRecords(@RequestParam("username") String username,
                                               @RequestParam("taskId") Integer taskId,
                                               @RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("limit") Integer limit,
                                               @RequestParam("search") String search){

        List<InstanceUserVO> instanceUserVOs = new ArrayList<>();
        Integer totalNum = instanceUserService.countByTask(username, taskId);

        if(StringUtils.isBlank(search)){
            List<InstanceUser> instanceUsers = instanceUserService.findByPage(username, taskId, Pager.of(pageNum, limit));
            for (InstanceUser instanceUser: instanceUsers) {
                String item = instanceService.findById(instanceUser.getInstanceId()).getItem();
                instanceUserVOs.add(new InstanceUserVO<>(instanceUser, item, null));
            }
        } else{  //关键字查询（模糊查询）
            List<InstanceUser> instanceUsers = instanceUserService.findByPage(username, taskId, Pager.of(0, totalNum));
            List<InstanceUserVO> instanceUserLst = new ArrayList<>();
            for (InstanceUser instanceUser: instanceUsers) {
                String item = instanceService.findById(instanceUser.getInstanceId()).getItem();
                if (StringUtils.contains(item, search) || StringUtils.contains(instanceUser.getTag(), search)){
                    instanceUserLst.add(new InstanceUserVO<>(instanceUser, item, null));
                }
            }

            totalNum = instanceUserLst.size();
            if (totalNum == 0){
                instanceUserVOs = instanceUserLst;
            } else if (totalNum < limit){
                instanceUserVOs = instanceUserLst.subList(0, totalNum);
            }else{
                int end = (pageNum + 1) * limit >= totalNum ? totalNum : (pageNum + 1) * limit;
                instanceUserVOs = instanceUserLst.subList(pageNum * limit, end);
            }
        }

        Map<String, Object> respMap = new HashMap<>();
        respMap.put("total", totalNum);   //总记录数
        respMap.put("rows", instanceUserVOs);   //当前页面记录
        return respMap;
    }

    @PostMapping("/update")
    @ResponseBody
    public RespEntity update(InstanceUser instanceUser){
        //修改数据
        System.out.println(instanceUser);
        instanceUser.setTagTime(new Date());  //修改标注时间
        instanceUserService.updateInstanceUser(instanceUser);
        return new RespEntity(RespStatus.SUCCESS);
    }

    @PostMapping("/new_page")
    @ResponseBody
    public InstanceUserVO pageBrowse(InstanceUser instanceUser,
                                     @RequestParam("offset") Integer offset){
        System.out.println(instanceUser);
        System.out.println(offset);

        //取出指定页数据
        String username = instanceUser.getUsername();
        Integer taskId = instanceUser.getTaskId();
        InstanceUser nextPage = instanceUserService.findByPage(username, taskId, Pager.of(offset, 1)).get(0);
        Instance instance = instanceService.findById(nextPage.getInstanceId());
        return new InstanceUserVO<>(nextPage, StringUtils.split(instance.getItem(), " "), null);
    }
}
