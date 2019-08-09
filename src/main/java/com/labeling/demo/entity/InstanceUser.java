package com.labeling.demo.entity;

public class InstanceUser {
    private Integer id;

    private String username;

    private Integer instance_id;

    private String tag;

    public InstanceUser(Integer id, String username, Integer instance_id, String tag) {
        this.id = id;
        this.username = username;
        this.instance_id = instance_id;
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

    public Integer getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(Integer instance_id) {
        this.instance_id = instance_id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }
}