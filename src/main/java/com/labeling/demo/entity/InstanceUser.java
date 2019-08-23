package com.labeling.demo.entity;

import java.util.Date;

public class InstanceUser {
    private Long id;

    private String username;

    private Long instanceId;

    private String tag;

    private String taskname;

    private Date tagTime;

    private Float responseTime;

    public InstanceUser(Long id, String username, Long instanceId, String tag, String taskname, Float responseTime) {
        this.id = id;
        this.username = username;
        this.instanceId = instanceId;
        this.tag = tag;
        this.taskname = taskname;
        this.responseTime = responseTime;
    }

    public InstanceUser(Long id, String username, Long instanceId, String tag, String taskname, Date tagTime, Float responseTime) {
        this.id = id;
        this.username = username;
        this.instanceId = instanceId;
        this.tag = tag;
        this.taskname = taskname;
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

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname == null ? null : taskname.trim();
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
}