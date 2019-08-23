package com.labeling.demo.repository;

import com.labeling.demo.entity.InstanceUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface InstanceUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InstanceUser record);

    int insertSelective(InstanceUser record);

    InstanceUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InstanceUser record);

    int updateByPrimaryKey(InstanceUser record);


    @Select("select * from tb_instance_user")
    List<InstanceUser> findAll();

    @Select("select count(username) from tb_instance_user where username = #{username}")
    Integer countByUserName(String username);

    List<InstanceUser> findByPage(String username, @Param("pager") Pageable pageable);

    @Select("select * from tb_instance_user where instanceId = #{instanceId}")
    List<InstanceUser> findInstanceUserById(Long instanceid);

    @Select("select * from tb_instance_user where username = #{username}")
    List<InstanceUser> findByUserName(String username);

    @Select("select count(0) from tb_instance_user where username=#{username} and taskname=#{taskname}")
    Integer countByTask(String username, String taskname);

    boolean save(InstanceUser instanceUser);
}