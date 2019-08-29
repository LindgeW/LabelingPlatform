package com.labeling.demo.service;

import com.labeling.demo.entity.Instance;
import com.labeling.demo.entity.vo.InstanceUserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InstanceService {
    void save(Instance instance);

    void saveAll(Iterable<Instance> instances);

    void updateAll(Iterable<Instance> instances);

    List<Instance> findAll();

//    List<Instance> findPageData(Pageable instPage);
    List<Instance> findPageDataByTaskName(String taskName, Pageable pageable);

    Instance findPageDataByTaskNameRand(String userName, String taskName, Pageable pageable);

//    Instance findInstById(Long id);

    List<Instance> findByTaskName(String taskName);

    long count();

    Instance findById(Long instanceId);
}
