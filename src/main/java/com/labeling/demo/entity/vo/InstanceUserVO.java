package com.labeling.demo.entity.vo;

import com.labeling.demo.entity.InstanceUser;
import org.apache.commons.lang3.StringUtils;

public class InstanceUserVO<T> extends InstanceUser {
    private transient T item;
    private String role;

    public InstanceUserVO() {
        super();
    }

    public InstanceUserVO(InstanceUser instanceUser, T item, String role) {
        super(instanceUser.getId(), instanceUser.getUsername(), instanceUser.getInstanceId(), instanceUser.getTag(), instanceUser.getTaskId(), instanceUser.getResponseTime());
        this.item = item;
        this.role = role;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        System.out.println(super.toString());
        return "InstanceUserVO{" +
                "item=" + item +
                ", role='" + role + '\'' +
                '}';
    }
}
