package com.labeling.demo.service.impl;

import com.labeling.demo.entity.*;
import com.labeling.demo.entity.vo.InstanceVO;
import com.labeling.demo.repository.InstanceMapper;
import com.labeling.demo.service.InstanceService;
import com.labeling.demo.service.InstanceUserService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class InstanceServiceImpl implements InstanceService {
    private InstanceMapper instanceMapper;
    private InstanceUserService instanceUserService;

    @Autowired
    public InstanceServiceImpl(InstanceMapper instanceMapper, InstanceUserService instanceUserService) {
        this.instanceMapper = instanceMapper;
        this.instanceUserService = instanceUserService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Instance instance) {
        this.instanceMapper.save(instance);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAll(Iterable<Instance> instances) {
        this.instanceMapper.saveAll(instances);
    }

    @Override
    public void updateAll(Iterable<Instance> instances) {
       this.instanceMapper.updateAll(instances);
    }

    @Override
    public List<Instance> findAll() {
        return instanceMapper.findAll();
    }

    @Override
    public List<Instance> findPageDataByTaskId(Integer taskId, Pager pageable) {
        return instanceMapper.findPageDataByTaskId(taskId, pageable);
    }

    @Override
    public Instance findPageDataByTaskNameRand(String userName, String taskName, Pager pageable) {
        boolean isTagged = false;
        Instance instance = null;
        do {
            List<Instance> insts = instanceMapper.findPageDataByTaskNameRand(taskName, pageable);
            for (Instance inst : insts) {
                if(inst.getStatus() != 1) {
                    isTagged = false;
                    Long instanceId = inst.getInstanceId();
                    List<InstanceUser> records = instanceUserService.findInstanceUserById(instanceId);
                    for (InstanceUser rec : records) {
                        if (userName.equals(rec.getUsername())) {
                            isTagged = true;
                            break;
                        }
                    }

                    if (!isTagged) {
                        instance = inst;
                        break;
                    }
                }
            }
        }while (isTagged);

        return instance;
    }

    @Override
    public List<Instance> findByTaskId(Integer taskId) {
        return instanceMapper.findByTaskId(taskId);
    }

    @Override
    public long count() {
        return instanceMapper.count();
    }

    @Override
    public Instance findById(Long instanceId) {
        return instanceMapper.findById(instanceId);
    }

    //生产随机标签
    private String randTag(Instance instance){
        List<String> tagLst = new ArrayList<>();
        String tagDefault = instance.getDefaultTag();
        String tagExpert = instance.getExpertTag();
        String tagModels = instance.getModelTag();

        if (StringUtils.isNotBlank(tagDefault)){
            tagLst.add(tagDefault);
        }

        if (StringUtils.isNotBlank(tagExpert)) {
            tagLst.add(tagExpert);
        }

        if (StringUtils.isNotBlank(tagModels)){
            // 模型预测的k-best结果
            String[] tagModel = StringUtils.split(tagModels, ";");
            Collections.addAll(tagLst, tagModel);
        }

        if (tagLst.isEmpty()) {
            return "";
        }

        System.out.println("gold标签：" + tagLst);
        return tagLst.get(RandomUtils.nextInt(0, tagLst.size()));
    }

    @Override
    public InstanceVO fitTask(Instance instance, Task task) {
        InstanceVO<Object> instanceVO = new InstanceVO<>();
        instanceVO.setInstanceId(instance.getInstanceId());
        instanceVO.setTaskId(task.getTaskId());
        TagType tagType = TagType.getTypeByID(task.getDatatype());
        String itemSeparator = task.getItemSeparator();

        if(tagType == TagType.CLASSIFY){
            instanceVO.setTaskItem(instance.getItem());
            instanceVO.setTag(randTag(instance));
        } else if (tagType == TagType.NER){
            instanceVO.setTaskItem(StringUtils.split(instance.getItem(), itemSeparator));
        } else if (tagType == TagType.SEMANTIC_SIM){
            instanceVO.setTaskItem(StringUtils.split(instance.getItem(), itemSeparator));
            instanceVO.setTag(randTag(instance));
        }

        return instanceVO;
    }
}
