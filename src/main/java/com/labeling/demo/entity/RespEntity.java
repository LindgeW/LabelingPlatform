package com.labeling.demo.entity;

import java.io.Serializable;

public class RespEntity<T> implements Serializable {
    private String message;
    private Integer code;
    private T data;
    private long work_count;

    public RespEntity(RespStatus status, T data) {
        this.code = status.getStatusCode();
        this.message = status.getStatusValue();
        this.data = data;
    }

    public RespEntity(RespStatus status) {
        this.code = status.getStatusCode();
        this.message = status.getStatusValue();
        data = null;
    }
    public RespEntity(RespStatus status, T data , long work_count) {
        this.code = status.getStatusCode();
        this.message = status.getStatusValue();
        this.data = data;
        this.work_count = work_count;
    }


    public long getWork_count() {
        return work_count;
    }

    public void setWork_count(long work_count) {
        this.work_count = work_count;
    }




    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespEntity{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
