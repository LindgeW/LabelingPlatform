package com.labeling.demo.entity.vo;

public class InstanceVO {
    private Long instanceId;    // 数据编号
    private String taskName;  //任务名
    private String item;     //数据项
    private String tag;      //用户标签
    private Float responseTime;  //用户标签选择响应时间

    public InstanceVO() {
        super();
    }

    public InstanceVO(Long instanceId, String taskName, String item, String tag) {
        this.instanceId = instanceId;
        this.taskName = taskName;
        this.item = item;
        this.tag = tag;
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
        this.taskName = taskName;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Float getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Float responseTime) {
        this.responseTime = responseTime;
    }

    @Override
    public String toString() {
        return "InstanceVO{" +
                "instanceId=" + instanceId +
                ", taskName='" + taskName + '\'' +
                ", item='" + item + '\'' +
                ", tag='" + tag + '\'' +
                ", responseTime=" + responseTime +
                '}';
    }
}
