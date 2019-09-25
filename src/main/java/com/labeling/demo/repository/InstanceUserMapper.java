package com.labeling.demo.repository;

import com.labeling.demo.entity.InstanceUser;
import com.labeling.demo.entity.Pager;
import com.labeling.demo.entity.vo.InstanceUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    List<InstanceUser> findByPage(String username, Integer taskId, @Param("pager") Pager pageable);

    @Select("select * from tb_instance_user where instanceId = #{instanceId}")
    List<InstanceUser> findInstanceUserById(Long instanceid);

    @Select("select * from tb_instance_user where username = #{username}")
    List<InstanceUser> findByUserName(String username);

    @Select("select count(0) from tb_instance_user where username=#{username} and taskId=#{taskId}")
    Integer countByTask(String username, Integer taskId);

    boolean save(InstanceUser instanceUser);

    List<InstanceUserVO> findFullRecord(Long instanceId);
}