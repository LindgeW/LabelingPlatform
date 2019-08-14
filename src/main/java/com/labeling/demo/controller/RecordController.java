package com.labeling.demo.controller;

import com.labeling.demo.entity.InstanceUser;
import com.labeling.demo.entity.RespEntity;
import com.labeling.demo.entity.RespStatus;
import com.labeling.demo.entity.User;
import com.labeling.demo.entity.vo.InstanceUserVO;
import com.labeling.demo.entity.vo.TaskVO;
import com.labeling.demo.entity.vo.TempoVO;
import com.labeling.demo.entity.vo.UserVO;
import com.labeling.demo.service.InstanceService;
import com.labeling.demo.service.InstanceUserService;
import com.labeling.demo.util.DataTransfer;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RecordController {

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
        TempoVO tempoVO = (TempoVO) session.getAttribute("tempoVO");

        //计算用户正确率
//        String username = user.getUsername();
//        Integer correcNum = 0;
//        List<InstanceUser>list = instanceUserService.findInstanceUserByUsername(username);
//        for (InstanceUser item : list)
//        {
//            String finalTag = instanceService.findById(item.getInstanceId()).getTag();
//            if (item.getTag().equals(finalTag))
//            {
//                correcNum++;
//            }
//        }

//        Integer totalNum = tempoVO.getCorpusSize();
//        DataTransfer dataTransfer = new DataTransfer();
//        String correctRate = dataTransfer.dataTrans(correcNum,totalNum);
//        model.addAttribute("correctRate",correctRate);
        model.addAttribute("userVo", new UserVO(user.getUsername(), user.getRole()));
        model.addAttribute("tempoVO",tempoVO);
        return "history";
    }

    @PostMapping("/fetchTags")
    @ResponseBody
    public RespEntity fetchTags(){
        Session session = SecurityUtils.getSubject().getSession();
        TaskVO myTask = (TaskVO) session.getAttribute("myTask");
        return new RespEntity<>(RespStatus.SUCCESS, myTask);
    }

    @PostMapping("/list")
    @ResponseBody
    public Map<String, Object> personalRecords(@RequestParam("username") String username,
                                               @RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("limit") Integer limit,
                                               @RequestParam("search") String search){

        Integer totalNum;
        List<InstanceUserVO> instanceUserVOs = new ArrayList<>();
        if(StringUtils.isBlank(search)){
            Pageable pageable = PageRequest.of(pageNum, limit);
            totalNum = instanceUserService.countByUsername(username);
            List<InstanceUser> instanceUsers = instanceUserService.findByPage(username, pageable);
            for (InstanceUser instanceUser: instanceUsers) {
                String item = instanceService.findById(instanceUser.getInstanceId()).getItem();
                instanceUserVOs.add(new InstanceUserVO(instanceUser, item));
            }
        } else{  //关键字查询（模糊查询）
            List<InstanceUser> instanceUsers = instanceUserService.findByUserName(username);
            List<InstanceUserVO> instanceUserLst = new ArrayList<>();
            for (InstanceUser instanceUser: instanceUsers) {
                String item = instanceService.findById(instanceUser.getInstanceId()).getItem();
                if (StringUtils.contains(item, search) || StringUtils.contains(instanceUser.getTag(), search)){
                    instanceUserLst.add(new InstanceUserVO(instanceUser, item));
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
    public RespEntity update(@RequestBody InstanceUser instanceUser){
        System.out.println(instanceUser);
        instanceUserService.updateInstanceUser(instanceUser);
        return new RespEntity(RespStatus.SUCCESS);
    }
}
