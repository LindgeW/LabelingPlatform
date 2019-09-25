package com.labeling.demo.service;

import com.labeling.demo.entity.Task;

import java.util.List;

public interface TaskService {
    Boolean save(Task task);

    List<Task> findAll();

    Task findById(Integer taskId);

    int updateTask(Task task);

    List<Task> findByExpertName(String expertName);

    String pageScheduler(Task task);

    Task findByName(String taskName);

    Integer taskCount();
}
