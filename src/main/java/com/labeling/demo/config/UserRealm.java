package com.labeling.demo.config;

import com.labeling.demo.entity.User;
import com.labeling.demo.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/*
 在认证、授权内部实现中都有提到，最终的处理都是交由Realm进行处理的，因为在Shiro中最终是通过Realm来获取应用程序的用户、角色以及权限信息的。
 Realm是专用于安全框架的DAO
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;


    //只有在需要权限认证时才会进去
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权验证。。。。。");
//        String username = (String)principalCollection.getPrimaryPrincipal();
//        System.out.println(username);
//        String username = (String) SecurityUtils.getSubject().getPrincipal();
//        System.out.println(username);

        User curUser = (User)principalCollection.getPrimaryPrincipal();
        User user = userService.findUser(curUser.getUsername());
        System.out.println(user);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<>();
        roles.add(user.getRole());
        simpleAuthorizationInfo.setRoles(roles); //设置该用户拥有的角色
        return simpleAuthorizationInfo;
    }

    //需要身份认证时（比如前面的 Subject.login() 方法）才会进入
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("身份验证。。。。。");
        String loginUserName = (String)authenticationToken.getPrincipal();
        String loginPwd = new String((char[]) authenticationToken.getCredentials());
        System.out.println(loginUserName+" "+loginPwd);
        User user = userService.findUser(loginUserName);

        if(user == null){
            throw new UnknownAccountException("用户不存在！！！");
        }
        if(!loginPwd.equals(user.getPassword())){
            throw new IncorrectCredentialsException("密码错误！！！");
        }

//        return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }
}
