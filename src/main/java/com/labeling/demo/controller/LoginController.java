package com.labeling.demo.controller;

import com.labeling.demo.entity.RespEntity;
import com.labeling.demo.entity.RespStatus;
import com.labeling.demo.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

//    @GetMapping(value = {"", "/", "/index"})
//    public String index(){
//        return "index";
//    }

    @GetMapping("/register")
    public String toRegister(){
        return "register";
    }


    @GetMapping("/login")
    public String toLogin(){
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public RespEntity login(User user){
        System.out.println(user);

        //创建Subject实例
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        String error = "";
        try {
            //将存有用户名和密码的token存进subject中
            subject.login(token);
        } catch (UnknownAccountException uae){
            error = "用户名或密码错误！";
        } catch (LockedAccountException lae){
            error = "用户已锁定或已删除！";
        } catch (AuthenticationException e){
            error = "未知错误！";
        }

        if(!subject.isAuthenticated()){ //未进行登录认证
            System.out.println("认证失败！");
            return new RespEntity(RespStatus.UNAUTHEN);
        }

        //跳转到标注页面
        return new RespEntity<>(RespStatus.SUCCESS, "/annotate");
    }

    @GetMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        System.out.println("已退出！");
        return "redirect:/login";
    }

}
