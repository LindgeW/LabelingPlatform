package com.labeling.demo.entity;

public class Pager {
    private Integer pageNumber;  //当前页
    private Integer pageSize;  //页面大小

    public Pager(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }



    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }
}
