package com.labeling.demo.service.impl;

import com.labeling.demo.entity.Instance;
import com.labeling.demo.repository.InstanceMapper;
import com.labeling.demo.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InstanceServiceImpl implements InstanceService {
    private InstanceMapper instanceMapper;

    @Autowired
    public InstanceServiceImpl(InstanceMapper instanceMapper) {
        this.instanceMapper = instanceMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Instance instance) {
        this.instanceMapper.save(instance);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAll(Iterable<Instance> instances) {
        for (Instance inst: instances) {
            System.out.println(inst);
        }
        this.instanceMapper.saveAll(instances);
    }

    @Override
    public List<Instance> findAll() {
        return instanceMapper.findAll();
    }

//    @Override
//    public Page<Instance> findPageData(Pageable instPage) {
//        return instanceMapper.findPageData(instPage);
//    }

    @Override
    public List<Instance> findPageDataByTaskName(String taskName, Pageable pageable) {
        return instanceMapper.findPageDataByTaskName(taskName, pageable);
    }

//    @Override
//    public Instance findInstById(Long id) {
//        return instanceMapper.findById(id);
//    }

    @Override
    public List<Instance> findByTaskName(String taskName) {
        return instanceMapper.findByTaskName(taskName);
    }

    @Override
    public long count() {
        return instanceMapper.count();
    }

    @Override
    public Instance findById(Long instanceId) {
        return instanceMapper.findById(instanceId);
    }
}
