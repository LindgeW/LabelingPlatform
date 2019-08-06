package com.labeling.demo.service.impl;

import com.labeling.demo.entity.Instance;
import com.labeling.demo.repository.InstanceRepository;
import com.labeling.demo.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstanceServiceImpl implements InstanceService {

    private InstanceRepository instanceRepository;

    @Autowired
    public InstanceServiceImpl(InstanceRepository instanceRepository) {
        this.instanceRepository = instanceRepository;
    }

    @Override
    public void save(Instance instance) {
        instanceRepository.save(instance);
    }

    @Override
    public void saveAll(Iterable<Instance> instances) {
        instanceRepository.saveAll(instances);
    }

    @Override
    public List<Instance> findAll() {
        return instanceRepository.findAll();
    }


}
