package com.labeling.demo.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "corpus")
public class Instance {  //不建议自己设主键，MongoDB自动生成一个唯一主键
    private String id;  // MongoDB自带ObjectId主键
    @Indexed  //提高检索速度
    private Long instanceId;    //自定义主键
    private String taskName; //属于哪个任务
    private String item;   //一个数据项
    private String tag;     //标签值 - 用户标签
    private String tagMine; //“地雷”- 专家标签
    private List<String> tagModel;  //模型预测标签

    private Integer tagNum;  //被标的次数，只有被标了指定次数才算被标
    private Integer status; //状态：无效(-1)、未标(0)、已标(1)

    public Instance() {
        super();
    }

    public Instance(Long instanceId, String taskName, String item, String tag, String tagMine, Integer tagNum, Integer status) {
        this.instanceId = instanceId;
        this.taskName = taskName;
        this.item = item;
        this.tag = tag;
        this.tagMine = tagMine;
        this.tagNum = tagNum;
        this.status = status;
    }

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
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

    public String getTagMine() {
        return tagMine;
    }

    public void setTagMine(String tagMine) {
        this.tagMine = tagMine;
    }

    public Integer getTagNum() {
        return tagNum;
    }

    public void setTagNum(Integer tagNum) {
        this.tagNum = tagNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}


