package com.labeling.demo.entity.vo;

public class InstanceVO {
    private Long instanceId;    // 数据编号
    private String item;  //数据项
    private String tag;    //用户标签

    public InstanceVO() {
        super();
    }


    public InstanceVO(Long instanceId, String item) {
        this.instanceId = instanceId;
        this.item = item;
    }

    public InstanceVO(Long instanceId, String item, String tag) {
        this.instanceId = instanceId;
        this.item = item;
        this.tag = tag;
    }

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
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

    @Override
    public String toString() {
        return "InstanceVO{" +
                "instanceId=" + instanceId +
                ", item='" + item + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
