package com.labeling.demo.entity;

import java.util.Objects;

public class Instance {
    private Long instanceId;

    private Integer taskId;

    private String defaultTag;

    private String expertTag;

    private String modelTag;

    private Integer status = 0;

    private Integer tagNum = 0;

    private String item;  //保存到数据库中的数据项

    public Instance(Long instanceId, Integer taskId, String defaultTag, String expertTag, String modelTag, Integer status, Integer tagNum) {
        this.instanceId = instanceId;
        this.taskId = taskId;
        this.defaultTag = defaultTag;
        this.expertTag = expertTag;
        this.modelTag = modelTag;
        this.status = status;
        this.tagNum = tagNum;
    }

    public Instance(Integer taskId, String defaultTag, String expertTag, String modelTag, Integer status, Integer tagNum, String item) {
        this.taskId = taskId;
        this.defaultTag = defaultTag;
        this.expertTag = expertTag;
        this.modelTag = modelTag;
        this.status = status;
        this.tagNum = tagNum;
        this.item = item;
    }

    public Instance(Long instanceId, Integer taskId, String defaultTag, String expertTag, String modelTag, Integer status, Integer tagNum, String item) {
        this.instanceId = instanceId;
        this.taskId = taskId;
        this.defaultTag = defaultTag;
        this.expertTag = expertTag;
        this.modelTag = modelTag;
        this.status = status;
        this.tagNum = tagNum;
        this.item = item;
    }

    public Instance() {
        super();
    }

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getDefaultTag() {
        return defaultTag;
    }

    public void setDefaultTag(String defaultTag) {
        this.defaultTag = defaultTag;
    }

    public String getExpertTag() {
        return expertTag;
    }

    public void setExpertTag(String expertTag) {
        this.expertTag = expertTag;
    }

    public String getModelTag() {
        return modelTag;
    }

    public void setModelTag(String modelTag) {
        this.modelTag = modelTag;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTagNum() {
        return tagNum;
    }

    public void setTagNum(Integer tagNum) {
        this.tagNum = tagNum;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item == null ? null : item.trim();
    }

    @Override
    public String toString() {
        return "Instance{" +
                "instanceId=" + instanceId +
                ", taskId=" + taskId +
                ", defaultTag='" + defaultTag + '\'' +
                ", expertTag='" + expertTag + '\'' +
                ", modelTag='" + modelTag + '\'' +
                ", status=" + status +
                ", tagNum=" + tagNum +
                ", item='" + item + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instance instance = (Instance) o;
        return Objects.equals(instanceId, instance.instanceId) &&
                Objects.equals(taskId, instance.taskId) &&
                Objects.equals(defaultTag, instance.defaultTag) &&
                Objects.equals(expertTag, instance.expertTag) &&
                Objects.equals(modelTag, instance.modelTag) &&
                Objects.equals(status, instance.status) &&
                Objects.equals(tagNum, instance.tagNum) &&
                Objects.equals(item, instance.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instanceId, taskId, defaultTag, expertTag, modelTag, status, tagNum, item);
    }
}