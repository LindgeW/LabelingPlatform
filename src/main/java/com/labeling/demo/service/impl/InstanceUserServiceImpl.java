package com.labeling.demo.service.impl;

import com.labeling.demo.entity.InstanceUser;
import com.labeling.demo.repository.InstanceUserMapper;
import com.labeling.demo.service.InstanceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InstanceUserServiceImpl implements InstanceUserService {

    private InstanceUserMapper instanceuserMapper;

    //构造器注入
    //尽量避免使用属性注入
    @Autowired
    public InstanceUserServiceImpl(InstanceUserMapper instanceuserMapper) {
        this.instanceuserMapper = instanceuserMapper;
    }

    @Override
    public InstanceUser findInstanceUser(int id) {
        return this.instanceuserMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<InstanceUser> findAll() {
        return instanceuserMapper.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addInstanceUser(InstanceUser instanceuser) {
        return this.instanceuserMapper.insertSelective(instanceuser) >= 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateInstanceUser(InstanceUser instanceuser) {
        return this.instanceuserMapper.updateByPrimaryKeySelective(instanceuser) >= 1;
    }


    public Integer countByUsername(String username) {
        return  this.instanceuserMapper.countByUsername(username);
    }


}
