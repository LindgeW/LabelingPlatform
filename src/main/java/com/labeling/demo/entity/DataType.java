package com.labeling.demo.entity;

public class DataType {
    private Short id;

    private String typeName;

    private String url;

    public DataType(String typeName, String url) {
        this.typeName = typeName;
        this.url = url;
    }

    public DataType(Short id, String typeName, String url) {
        this.id = id;
        this.typeName = typeName;
        this.url = url;
    }

    public DataType() {
        super();
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    @Override
    public String toString() {
        return "DataType{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}