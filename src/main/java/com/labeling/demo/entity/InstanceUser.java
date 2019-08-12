package com.labeling.demo.entity;

public class InstanceUser {
    private Integer id;

    private String username;

    private Long instanceId;

    private String tag;

    public InstanceUser(Integer id, String username, Long instanceId, String tag) {
        this.id = id;
        this.username = username;
        this.instanceId = instanceId;
        this.tag = tag;
    }

    public InstanceUser() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @Override
    public String toString() {
        return "InstanceUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", instanceId=" + instanceId +
                ", tag='" + tag + '\'' +
                '}';
    }
}