package com.labeling.demo.service.impl;

import com.labeling.demo.entity.User;
import com.labeling.demo.repository.UserMapper;
import com.labeling.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    //构造器注入
    //尽量避免使用属性注入
    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User findUser(String username) {
        return this.userMapper.selectByPrimaryKey(username);
    }

    @Override
    public boolean addUser(User user) {
        return this.userMapper.insertSelective(user) >= 1;
    }

    @Override
    public boolean updateUser(User user) {
        return this.userMapper.updateByPrimaryKeySelective(user) >= 1;
    }

}
