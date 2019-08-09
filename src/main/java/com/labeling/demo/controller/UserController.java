package com.labeling.demo.controller;

import com.labeling.demo.entity.RespEntity;
import com.labeling.demo.entity.RespStatus;
import com.labeling.demo.entity.User;
import com.labeling.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/bg_index")
    @RequiresRoles("admin")
    public  String bgIndex(){
        return "bg_index";
    }

    @GetMapping("/bg")
    @RequiresRoles("admin")
//    @RequiresPermissions("login:annotate:bg")
    public String background(){
        return "bg";
    }

    @GetMapping("/account")
    public String account(Model model){
        User user = (User)SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("username", user.getUsername());
        return "account";
    }


    @PostMapping("/register")
    @ResponseBody
    public RespEntity addUser(User user){
        System.out.println(user);
        //账户信息保存到数据库中
        boolean isOk = this.userService.addUser(user);
        if(isOk){
            return new RespEntity(RespStatus.SUCCESS);
        }else{
            return new RespEntity(RespStatus.Error);
        }
    }

    @PostMapping("/alter_pwd")
    @ResponseBody
    public RespEntity alterPwd(@RequestParam("oldPwd") String oldPwd,
                               @RequestParam("newPwd") String newPwd){
        User curUser = (User) SecurityUtils.getSubject().getPrincipal();
        String username = curUser.getUsername();
        String password = curUser.getPassword();
//        User real = userService.findUser(curUser.getUsername());
        System.out.println(curUser);
        if(!oldPwd.equals(password)){
            return new RespEntity(RespStatus.UNAUTHEN);
        } else if(!oldPwd.equals(newPwd)){
            User user = new User();
            user.setUsername(username);
            user.setPassword(newPwd);
            userService.updateUser(user);
        }

        return new RespEntity(RespStatus.SUCCESS);
    }

}
