package com.labeling.demo.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "corpus")
public class Instance {
    private String taskName; //隶属于哪个任务
    private String item;   //一个数据项
    private String tag;     //标签值
    private Integer tagNum;  //被标的次数，只有被标了指定次数才算被标
    private Integer status; //状态：无效(-1)、未标(0)、已标(1)

    public Instance() {
        tag = "";
        tagNum = 0;
        status = 0;
    }

    public Instance(String taskName, String item, String tag, Integer tagNum, Integer status) {
        this.taskName = taskName;
        this.item = item;
        this.tag = tag;
        this.tagNum = tagNum;
        this.status = status;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTagNum() {
        return tagNum;
    }

    public void setTagNum(Integer tagNum) {
        this.tagNum = tagNum;
    }
}


