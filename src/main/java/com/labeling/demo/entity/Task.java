package com.labeling.demo.entity;

public class Task {
    private String taskname;

    private Short datatype;

    private Integer corpussize;

    private String tags;

    private Boolean status = false;

    private String expertname;

    public Task(String taskname, Short datatype, Integer corpussize, String tags, Boolean status, String expertname) {
        this.taskname = taskname;
        this.datatype = datatype;
        this.corpussize = corpussize;
        this.tags = tags;
        this.status = status;
        this.expertname = expertname;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getExpertname() {
        return expertname;
    }

    public void setExpertname(String expertname) {
        this.expertname = expertname == null ? null : expertname.trim();
    }
}