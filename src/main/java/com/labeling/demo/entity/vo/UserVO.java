package com.labeling.demo.entity.vo;

import com.labeling.demo.entity.User;

public class UserVO extends User {
    private Integer tagNum = 0;  //当前用户已经标了多少数据

    public UserVO() {
    }

    public UserVO(String username, String role, Integer tagNum) {
        super(username, role);
        this.tagNum = tagNum;
    }

    public UserVO(String username, String role, String teamName, Integer tagNum) {
        super(username, role, teamName);
        this.tagNum = tagNum;
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
                "tagNum=" + tagNum +
                '}';
    }
}
