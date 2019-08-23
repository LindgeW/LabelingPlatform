package com.labeling.demo.entity;

public class Instance {
    private Long instanceId;

    private String taskName;

    private String item;

    private String tagExpert;

    private String tagModel;

    private Integer status;

    private Integer tagNum;

    public Instance(String taskName, String tagExpert, String tagModel, Integer status, Integer tagNum, String item) {
        this.taskName = taskName;
        this.item = item;
        this.tagExpert = tagExpert;
        this.tagModel = tagModel;
        this.status = status;
        this.tagNum = tagNum;
    }

    public Instance() {
        super();
    }

    public Instance(Long instanceId, String taskName, String tagExpert, String tagModel, Integer status, Integer tagNum, String item) {
        this.instanceId = instanceId;
        this.taskName = taskName;
        this.tagExpert = tagExpert;
        this.tagModel = tagModel;
        this.status = status;
        this.tagNum = tagNum;
        this.item = item;
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
                ", tagExpert='" + tagExpert + '\'' +
                ", tagModel='" + tagModel + '\'' +
                ", status=" + status +
                ", tagNum=" + tagNum +
                ", item='" + item + '\'' +
                '}';
    }

}