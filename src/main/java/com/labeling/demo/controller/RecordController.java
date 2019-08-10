package com.labeling.demo.controller;

import com.labeling.demo.entity.InstanceUser;
import com.labeling.demo.entity.User;
import com.labeling.demo.service.InstanceUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecordController {

    private InstanceUserService instanceUserService;

    @Autowired
    public RecordController(InstanceUserService instanceUserService) {
        this.instanceUserService = instanceUserService;
    }

    @RequestMapping("/list")
    public String personalRecords(){
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();


        return "history";
    }

}
