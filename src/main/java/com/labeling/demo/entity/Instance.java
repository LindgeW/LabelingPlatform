package com.labeling.demo.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "corpus")
public class Instance {
    private Integer taskId;  //任务Id
    private String item;   //一个数据项
    private String tag;    //标签
    private Integer status; //状态：无效(-1)、未标(0)、已标(1)

    public Instance() {
        this.taskId = 0;
        this.item = "";
        this.tag = "";
        this.status = 0;
    }

    public Instance(Integer taskId, String item, String tag, Integer status) {
        this.taskId = taskId;
        this.item = item;
        this.tag = tag;
        this.status = status;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
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
}

