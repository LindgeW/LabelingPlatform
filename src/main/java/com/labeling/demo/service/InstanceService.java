package com.labeling.demo.service;

import com.labeling.demo.entity.Instance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InstanceService {
    void save(Instance instance);

    void saveAll(Iterable<Instance> instances);

    List<Instance> findAll();

    Page<Instance> findPageData(Pageable instPage);

    List<Instance> findByTaskName(String taskName);
}
