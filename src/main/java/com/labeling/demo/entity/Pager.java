package com.labeling.demo.entity;

public class Pager {
    private Integer offset;  //当前页 offset
    private Integer pageSize;  //页面大小 size
    private Integer totalRows;  //总记录数

    public Pager(Integer offset, Integer pageSize) {
        this.offset = offset;
        this.pageSize = pageSize;
    }

    public Pager(Integer offset, Integer pageSize, Integer totalRows) {
        this.offset = offset;
        this.pageSize = pageSize;
        this.totalRows = totalRows;
    }

    // 静态构造
    public static Pager of(Integer offset, Integer pageSize){
        return new Pager(offset, pageSize);
    }

    public static Pager of(Integer offset, Integer pageSize, Integer totalRows){
        return new Pager(offset, pageSize, totalRows);
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    @Override
    public String toString() {
        return "Pager{" +
                "offset=" + offset +
                ", pageSize=" + pageSize +
                ", totalRows=" + totalRows +
                '}';
    }
}
