package com.labeling.demo.entity;

public class Task {
    private String taskname;

    private Short datatype;

    private Integer corpussize;

    private String tags;

    private String itemSeparator;

    private String expertname;

    private Boolean status;

    public Task(String taskname, Short datatype, Integer corpussize, String tags, String itemSeparator, String expertname, Boolean status) {
        this.taskname = taskname;
        this.datatype = datatype;
        this.corpussize = corpussize;
        this.tags = tags;
        this.itemSeparator = itemSeparator;
        this.expertname = expertname;
        this.status = status;
    }

    public Task() {
        super();
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname == null ? null : taskname.trim();
    }

    public Short getDatatype() {
        return datatype;
    }

    public void setDatatype(Short datatype) {
        this.datatype = datatype;
    }

    public Integer getCorpussize() {
        return corpussize;
    }

    public void setCorpussize(Integer corpussize) {
        this.corpussize = corpussize;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    public String getItemSeparator() {
        return itemSeparator;
    }

    public void setItemSeparator(String itemSeparator) {
        this.itemSeparator = itemSeparator == null ? null : itemSeparator.trim();
    }

    public String getExpertname() {
        return expertname;
    }

    public void setExpertname(String expertname) {
        this.expertname = expertname == null ? null : expertname.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}