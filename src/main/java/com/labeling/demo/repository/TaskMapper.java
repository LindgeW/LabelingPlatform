package com.labeling.demo.repository;

import com.labeling.demo.entity.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskMapper {
    int deleteByPrimaryKey(String taskname);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(String taskname);

    List<Task> findAll();

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);
}