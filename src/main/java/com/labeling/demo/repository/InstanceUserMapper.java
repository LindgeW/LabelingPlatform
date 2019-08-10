package com.labeling.demo.repository;

import com.labeling.demo.entity.InstanceUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InstanceUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InstanceUser record);

    int insertSelective(InstanceUser record);

    InstanceUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InstanceUser record);

    int updateByPrimaryKey(InstanceUser record);

    List<InstanceUser> findAll();

    int countByUsername(String username);
}