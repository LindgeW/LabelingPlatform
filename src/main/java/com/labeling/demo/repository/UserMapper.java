package com.labeling.demo.repository;

import com.labeling.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(String username);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String username);

    List<User> findAll();

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

//    @Select("select role from tb_user where username = #{username}")
//    String findRoleByName(String username);
}