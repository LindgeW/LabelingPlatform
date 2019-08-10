package com.labeling.demo.entity;

public class Task {
//    private Integer taskId;

    protected String taskname;

    protected Short datatype;

    protected Integer corpussize;

    protected String tags;

    public Task(String taskname, Short datatype, Integer corpussize, String tags) {
        this.taskname = taskname;
        this.datatype = datatype;
        this.corpussize = corpussize;
        this.tags = tags;
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
}