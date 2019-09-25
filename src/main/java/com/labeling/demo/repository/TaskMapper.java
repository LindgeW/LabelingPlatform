package com.labeling.demo.repository;

import com.labeling.demo.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskMapper {
    int deleteByPrimaryKey(Integer taskId);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Integer taskId);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);

    @Select("select * from tb_task")
    List<Task> findAll();

    @Select("select * from tb_task where expertname = #{expertname}")
    List<Task> findByExpert(String expertName);

    @Select("select * from tb_task where taskname = #{taskname}")
    Task findByName(String taskName);

    @Select("select count(0) from tb_task")
    Integer count();
}