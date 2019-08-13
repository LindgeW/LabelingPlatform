package com.labeling.demo.service.impl;

import com.labeling.demo.entity.InstanceUser;
import com.labeling.demo.repository.InstanceUserMapper;
import com.labeling.demo.service.InstanceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
    public List<InstanceUser> findByPage(String username, Pageable pageable) {
        return instanceuserMapper.findByPage(username, pageable);
    }

    @Override
    public List<InstanceUser> findByUserName(String username) {
        return instanceuserMapper.findByUserName(username);
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(InstanceUser instanceUser) {
        return this.instanceuserMapper.save(instanceUser) >= 1;
    }


    public Integer countByUsername(String username) {
        return  this.instanceuserMapper.countByUsername(username);
    }

    @Override
    public boolean saveBtach(List<InstanceUser> list) {
        return this.instanceuserMapper.saveBatch(list);
    }

    @Override
    public List<InstanceUser> findInstanceUserById(Long instanceid) {
        return this.instanceuserMapper.findInstanceUserById(instanceid);
    }

    @Override
    public List<InstanceUser> findInstanceUserByUsername(String username) {
        return this.instanceuserMapper.findByUserName(username);
    }


}
