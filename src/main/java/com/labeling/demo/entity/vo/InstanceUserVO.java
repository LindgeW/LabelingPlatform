package com.labeling.demo.entity.vo;

import com.labeling.demo.entity.InstanceUser;

public class InstanceUserVO extends InstanceUser {
    private String item;
//    private Float responseTime; //用户标注的响应时间（随机标注的用户响应时间会很短）

    public InstanceUserVO() {
        super();
    }

    public InstanceUserVO(InstanceUser instanceUser, String item) {
        super(instanceUser.getId(), instanceUser.getUsername(), instanceUser.getInstanceId(), instanceUser.getTag(), instanceUser.getTaskname(), instanceUser.getResponseTime());
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
