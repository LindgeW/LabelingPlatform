package com.labeling.demo.service;

import com.labeling.demo.entity.Task;

import java.util.List;

public interface TaskService {
    void save(Task task);

    List<Task> findAll();

    Task findByName(String taskName);

    int updateByname(Task task);
}
