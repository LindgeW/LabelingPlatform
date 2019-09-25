package com.labeling.demo.repository;

import com.labeling.demo.entity.Instance;
import com.labeling.demo.entity.Pager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    List<Instance> findPageDataByTaskId(Integer taskId, @Param("pager") Pager pageable);

    List<Instance> findPageDataByTaskNameRand(String taskName, @Param("pager") Pager pageable);

    @Select("select * from tb_instance where taskId = #{taskId}")
    List<Instance> findByTaskId(Integer taskId);

    @Select("select count(0) from tb_instance")
    Long count();

    @Select("select * from tb_instance where instanceId = #{instanceId}")
    Instance findById(Long instanceId);

    void save(Instance instance);

    void saveAll(Iterable<Instance> instances);

    void updateAll(Iterable<Instance> instances);
}