package com.labeling.demo.service.impl;

import com.labeling.demo.entity.User;
import com.labeling.demo.repository.UserMapper;
import com.labeling.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

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
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(User user) {
        return this.userMapper.insertSelective(user) >= 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(User user) {
        return this.userMapper.updateByPrimaryKeySelective(user) >= 1;
    }

    @Override
    public boolean updateBatchUser(List<User> users) {
        boolean isOk = false;
        for (User user: users) {
            isOk = this.updateUser(user);
        }
        return isOk;
    }

}
