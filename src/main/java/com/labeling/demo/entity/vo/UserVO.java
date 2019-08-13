package com.labeling.demo.entity.vo;

public class UserVO {
    private String username;
    private String role = "user";
    private Integer tagNum = 0;  //当前用户已经标了多少数据

    public UserVO() {
    }

    public UserVO(String username, String role) {
        this.username = username;
        this.role = role;
        this.tagNum = 0;
    }

    public UserVO(String username, String role, Integer tagNum) {
        this.username = username;
        this.role = role;
        this.tagNum = tagNum;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
