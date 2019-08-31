package com.labeling.demo.service;

import com.labeling.demo.entity.Instance;
import com.labeling.demo.entity.Pager;
import com.labeling.demo.entity.vo.InstanceUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InstanceService {
    void save(Instance instance);

    void saveAll(Iterable<Instance> instances);

    void updateAll(Iterable<Instance> instances);

    List<Instance> findAll();

//    List<Instance> findPageData(Pageable instPage);
    List<Instance> findPageDataByTaskName(String taskName, Pager pageable);

    Instance findPageDataByTaskNameRand(String userName, String taskName, Pager pageable);

//    Instance findInstById(Long id);

    List<Instance> findByTaskName(String taskName);

    long count();

    Instance findById(Long instanceId);
}
