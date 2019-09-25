package com.labeling.demo.entity;

import java.util.Date;

public class InstanceUser {
    private Long id;

    private String username;

    private transient Long instanceId;  //序列化时不起作用

    private String tag;  //用户标签

    private transient Integer taskId;

    private Date tagTime;  //标注时间戳

    private Float responseTime;  //标注数据instanceId的响应时间

    public InstanceUser(Long id, String username, Long instanceId, String tag, Integer taskId, Float responseTime) {
        this.id = id;
        this.username = username;
        this.instanceId = instanceId;
        this.tag = tag;
        this.taskId = taskId;
        this.responseTime = responseTime;
    }

    public InstanceUser(Long id, String username, Long instanceId, String tag, Integer taskId, Date tagTime, Float responseTime) {
        this.id = id;
        this.username = username;
        this.instanceId = instanceId;
        this.tag = tag;
        this.taskId = taskId;
        this.tagTime = tagTime;
        this.responseTime = responseTime;
    }

    public InstanceUser() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Date getTagTime() {
        return tagTime;
    }

    public void setTagTime(Date tagTime) {
        this.tagTime = tagTime;
    }

    public Float getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Float responseTime) {
        this.responseTime = responseTime;
    }

    @Override
    public String toString() {
        return "InstanceUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", instanceId=" + instanceId +
                ", tag='" + tag + '\'' +
                ", taskId='" + taskId + '\'' +
                ", tagTime=" + tagTime +
                ", responseTime=" + responseTime +
                '}';
    }
}