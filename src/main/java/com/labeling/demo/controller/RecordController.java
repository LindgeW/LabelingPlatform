package com.labeling.demo.controller;

import com.labeling.demo.entity.*;
import com.labeling.demo.entity.vo.InstanceUserVO;
import com.labeling.demo.entity.vo.TaskVO;
import com.labeling.demo.service.InstanceService;
import com.labeling.demo.service.InstanceUserService;
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
public class RecordController {
    private final static String root = "history/";
    private InstanceService instanceService;
    private InstanceUserService instanceUserService;

    @Autowired
    public RecordController(InstanceService instanceService, InstanceUserService instanceUserService) {
        this.instanceService = instanceService;
        this.instanceUserService = instanceUserService;
    }

//    private Set<String> colorGenerator(int n){
//        Set<String> colorSet = new HashSet<>();
//        Random random = new Random(25);
//        for(int i=0; i<n; i++) {
//            int r = random.nextInt(255);
//            int g = random.nextInt(255);
//            int b = random.nextInt(255);
//            colorSet.add(String.format("rgb(%d, %d, %d)", r, g, b));
//        }
//        return colorSet;
//    }

    @RequestMapping("/history")
    public String history(Model model){
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();

        //获取当前用户的任务信息
        Session session = subject.getSession();
        TaskVO taskVO = (TaskVO) session.getAttribute("taskVo");

        model.addAttribute("taskVo", taskVO);
        model.addAttribute("userVo", user);

        if (TagType.NER == TagType.getTypeByID(taskVO.getDatatype())){
            InstanceUser record = instanceUserService.findByPage(user.getUsername(), Pager.of(0, 1)).get(0);
            Integer totalRows = instanceUserService.countByUsername(user.getUsername());
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
                                               @RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("limit") Integer limit,
                                               @RequestParam("search") String search){

        Integer totalNum;
        List<InstanceUserVO> instanceUserVOs = new ArrayList<>();
        if(StringUtils.isBlank(search)){
            Pager pageable = Pager.of(pageNum, limit);
            totalNum = instanceUserService.countByUsername(username);
            List<InstanceUser> instanceUsers = instanceUserService.findByPage(username, pageable);
            for (InstanceUser instanceUser: instanceUsers) {
                String item = instanceService.findById(instanceUser.getInstanceId()).getItem();
                instanceUserVOs.add(new InstanceUserVO<>(instanceUser, item, null));
            }
        } else{  //关键字查询（模糊查询）
            List<InstanceUser> instanceUsers = instanceUserService.findByUserName(username);
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
        InstanceUser nextPage = instanceUserService.findByPage(username, Pager.of(offset, 1)).get(0);
        Instance instance = instanceService.findById(nextPage.getInstanceId());
        return new InstanceUserVO<>(nextPage, StringUtils.split(instance.getItem(), " "), null);
    }
}
