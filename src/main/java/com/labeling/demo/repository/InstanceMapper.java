package com.labeling.demo.repository;

import com.labeling.demo.entity.Instance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface InstanceMapper {
    int deleteByPrimaryKey(Long instanceId);

    int insert(Instance record);

    int insertSelective(Instance record);

    Instance selectByPrimaryKey(Long instanceId);

    int updateByPrimaryKeySelective(Instance record);

    int updateByPrimaryKeyWithBLOBs(Instance record);

    int updateByPrimaryKey(Instance record);

    @Select("select * from tb_instance")
    List<Instance> findAll();

    List<Instance> findPageData(Pageable instPage);

    List<Instance> findPageDataByTaskName(String taskName, @Param("pager") Pageable pageable);

    List<Instance> findPageDataByTaskNameRand(String taskName, @Param("pager") Pageable pageable);

    @Select("select * from tb_instance where taskName = #{taskName}")
    List<Instance> findByTaskName(String taskName);

    @Select("select count(0) from tb_instance")
    Long count();

    @Select("select * from tb_instance where instanceId = #{instanceId}")
    Instance findById(Long instanceId);

    void save(Instance instance);

    void saveAll(Iterable<Instance> instances);

    void updateAll(Iterable<Instance> instances);
}