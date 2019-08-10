package com.labeling.demo.entity.vo;

public class UserVO {
    private String username;
    private Integer tagNum;  //当前用户已经标了多少数据

    public UserVO() {
    }

    public UserVO(String username, Integer tagNum) {
        this.username = username;
        this.tagNum = tagNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getTagNum() {
        return tagNum;
    }

    public void setTagNum(Integer tagNum) {
        this.tagNum = tagNum;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "username='" + username + '\'' +
                ", tagNum=" + tagNum +
                '}';
    }
}
