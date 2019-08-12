package com.labeling.demo.entity.vo;

import com.labeling.demo.entity.InstanceUser;

public class InstanceUserVO extends InstanceUser {
    private String item;

    public InstanceUserVO() {
        super();
    }

    public InstanceUserVO(InstanceUser instanceUser, String item) {
        super(instanceUser.getId(), instanceUser.getUsername(), instanceUser.getInstanceId(), instanceUser.getTag());
        this.item = item;
    }

    public InstanceUserVO(Integer id, String username, Long instanceId, String tag, String item) {
        super(id, username, instanceId, tag);
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public String toString() {
        System.out.println(getId());
        System.out.println(getInstanceId());
        System.out.println(getTag());
        return "InstanceUserVO{" +
                "item='" + item + '\'' +
                '}';
    }
}
