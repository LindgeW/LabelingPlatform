package com.labeling.demo.service;

import com.labeling.demo.entity.Instance;
import com.labeling.demo.entity.Pager;
import com.labeling.demo.entity.Task;
import com.labeling.demo.entity.vo.InstanceUserVO;
import com.labeling.demo.entity.vo.InstanceVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InstanceService {
    void save(Instance instance);

    void saveAll(Iterable<Instance> instances);

    void updateAll(Iterable<Instance> instances);

    List<Instance> findAll();

//    List<Instance> findPageDataByTaskName(String taskName, Pager pageable);
    List<Instance> findPageDataByTaskId(Integer taskId, Pager pageable);

    Instance findPageDataByTaskNameRand(String userName, String taskName, Pager pageable);

    List<Instance> findByTaskId(Integer taskId);

    long count();

    Instance findById(Long instanceId);

    InstanceVO fitTask(Instance firstInstance, Task task);
}
