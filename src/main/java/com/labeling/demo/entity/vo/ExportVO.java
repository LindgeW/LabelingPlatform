package com.labeling.demo.entity.vo;

import java.io.Serializable;
import java.util.Date;

public class ExportVO implements Serializable {
    private String raw;       //原始数据
    private String expertTag; //专家标签
    private String userName;    //标注者
    private String userTag;  //用户标签
    private Date tagTime;  //标注时间

    public ExportVO() {
        super();
    }

    public ExportVO(String raw, String expertTag, String userName, String userTag, Date tagTime) {
        this.raw = raw;
        this.expertTag = expertTag;
        this.userName = userName;
        this.userTag = userTag;
        this.tagTime = tagTime;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getExpertTag() {
        return expertTag;
    }

    public void setExpertTag(String expertTag) {
        this.expertTag = expertTag;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTag() {
        return userTag;
    }

    public void setUserTag(String userTag) {
        this.userTag = userTag;
    }

    public Date getTagTime() {
        return tagTime;
    }

    public void setTagTime(Date tagTime) {
        this.tagTime = tagTime;
    }

    @Override
    public String toString() {
        return "ExportVO{" +
                "raw='" + raw + '\'' +
                ", expertTag='" + expertTag + '\'' +
                ", userName='" + userName + '\'' +
                ", userTag='" + userTag + '\'' +
                ", tagTime=" + tagTime +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        } else if(this == obj) {  //先比较hashCode，不同时再分别比较属性
            return true;
        } else if(obj instanceof ExportVO){
            ExportVO objOther = (ExportVO) obj;
            return (raw.equals(objOther.getRaw()) && userName.equals(objOther.getUserName()) && expertTag.equals(objOther.getExpertTag()) && userTag.equals(objOther.getUserTag()));
        }

        return false;
    }
}
