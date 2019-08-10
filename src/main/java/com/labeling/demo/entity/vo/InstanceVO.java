package com.labeling.demo.entity.vo;

public class InstanceVO {
    private Long id;       // 数据编号
    private String item;  //数据项
    private String tag;    //标签值

    public InstanceVO() {
        super();
    }

    public InstanceVO(Long id, String item, String tag) {
        this.id = id;
        this.item = item;
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
