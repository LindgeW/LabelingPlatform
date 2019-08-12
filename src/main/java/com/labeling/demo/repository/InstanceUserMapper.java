package com.labeling.demo.repository;

import com.labeling.demo.entity.InstanceUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Pageable;

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

    List<InstanceUser> findByPage(String username, @Param("pager") Pageable pageable);

    int countByUsername(String username);

    int save(InstanceUser instanceUser);

    boolean saveBatch(List<InstanceUser> list);

    List<InstanceUser>findInstanceUserById(Long instanceid);

    @Select("select * from tb_instance_user where username = #{username}")
    List<InstanceUser> findByUserName(String username);
}