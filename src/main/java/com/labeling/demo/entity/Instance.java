package com.labeling.demo.entity;

import java.util.Objects;

public class Instance {
    private Long instanceId;

    private String taskName;

    private String tagDefault;

    private String tagExpert;

    private String tagModel;

    private Integer status;

    private Integer tagNum;

    private String item;  //保存到数据库中的数据项

    public Instance(Long instanceId, String taskName, String tagDefault, String tagExpert, String tagModel, Integer status, Integer tagNum) {
        this.instanceId = instanceId;
        this.taskName = taskName;
        this.tagDefault = tagDefault;
        this.tagExpert = tagExpert;
        this.tagModel = tagModel;
        this.status = status;
        this.tagNum = tagNum;
    }

    public Instance(String taskName, String tagDefault, String tagExpert, String tagModel, Integer status, Integer tagNum, String item) {
        this.taskName = taskName;
        this.tagDefault = tagDefault;
        this.tagExpert = tagExpert;
        this.tagModel = tagModel;
        this.status = status;
        this.tagNum = tagNum;
        this.item = item;
    }

    public Instance(Long instanceId, String taskName, String tagDefault, String tagExpert, String tagModel, Integer status, Integer tagNum, String item) {
        this.instanceId = instanceId;
        this.taskName = taskName;
        this.tagDefault = tagDefault;
        this.tagExpert = tagExpert;
        this.tagModel = tagModel;
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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public String getTagDefault() {
        return tagDefault;
    }

    public void setTagDefault(String tagDefault) {
        this.tagDefault = tagDefault == null ? null : tagDefault.trim();
    }

    public String getTagExpert() {
        return tagExpert;
    }

    public void setTagExpert(String tagExpert) {
        this.tagExpert = tagExpert == null ? null : tagExpert.trim();
    }

    public String getTagModel() {
        return tagModel;
    }

    public void setTagModel(String tagModel) {
        this.tagModel = tagModel == null ? null : tagModel.trim();
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
                ", taskName='" + taskName + '\'' +
                ", tagDefault='" + tagDefault + '\'' +
                ", tagExpert='" + tagExpert + '\'' +
                ", tagModel='" + tagModel + '\'' +
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
                Objects.equals(taskName, instance.taskName) &&
                Objects.equals(tagDefault, instance.tagDefault) &&
                Objects.equals(tagExpert, instance.tagExpert) &&
                Objects.equals(tagModel, instance.tagModel) &&
                Objects.equals(tagNum, instance.tagNum) &&
                Objects.equals(item, instance.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instanceId, taskName, tagDefault, tagExpert, tagModel, tagNum, item);
    }
}