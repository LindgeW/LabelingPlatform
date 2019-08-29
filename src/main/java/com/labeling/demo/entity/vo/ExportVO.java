package com.labeling.demo.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.labeling.demo.entity.Instance;
import com.labeling.demo.entity.InstanceUser;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

//public class ExportVO extends Instance implements Serializable {
//    private Long instanceId;  //数据编号
//    private String raw;       //原始数据
//    private String defaultTag; //默认标签（随机）
//    private String expertTag; //专家标签
//    private String userName;  //标注者
//    private String userTag;  //用户标签
//    private String userType;  //用户类型：专家或普通用户
//    private Date tagTime;   //标注时间
//    private Float responseTime;  //标注响应时间
//
//    public ExportVO() {
//        super();
//    }
//
//    public ExportVO(Long instanceId, String raw, String defaultTag, String expertTag, String userName, String userTag, String userType, Date tagTime, Float responseTime) {
//        this.instanceId = instanceId;
//        this.raw = raw;
//        this.defaultTag = defaultTag;
//        this.expertTag = expertTag;
//        this.userName = userName;
//        this.userTag = userTag;
//        this.userType = userType;
//        this.tagTime = tagTime;
//        this.responseTime = responseTime;
//    }
//
//    public Long getInstanceId() {
//        return instanceId;
//    }
//
//    public void setInstanceId(Long instanceId) {
//        this.instanceId = instanceId;
//    }
//
//    public String getRaw() {
//        return raw;
//    }
//
//    public void setRaw(String raw) {
//        this.raw = raw;
//    }
//
//    public String getDefaultTag() {
//        return defaultTag;
//    }
//
//    public void setDefaultTag(String defaultTag) {
//        this.defaultTag = defaultTag;
//    }
//
//    public String getExpertTag() {
//        return expertTag;
//    }
//
//    public void setExpertTag(String expertTag) {
//        this.expertTag = expertTag;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getUserTag() {
//        return userTag;
//    }
//
//    public void setUserTag(String userTag) {
//        this.userTag = userTag;
//    }
//
//    public String getUserType() {
//        return userType;
//    }
//
//    public void setUserType(String userType) {
//        this.userType = userType;
//    }
//
//    public Date getTagTime() {
//        return tagTime;
//    }
//
//    public void setTagTime(Date tagTime) {
//        this.tagTime = tagTime;
//    }
//
//    public Float getResponseTime() {
//        return responseTime;
//    }
//
//    public void setResponseTime(Float responseTime) {
//        this.responseTime = responseTime;
//    }
//
//    @Override
//    public String toString() {
//        return "ExportVO{" +
//                "instanceId=" + instanceId +
//                ", raw='" + raw + '\'' +
//                ", defaultTag='" + defaultTag + '\'' +
//                ", expertTag='" + expertTag + '\'' +
//                ", userName='" + userName + '\'' +
//                ", userTag='" + userTag + '\'' +
//                ", userType='" + userType + '\'' +
//                ", tagTime=" + tagTime +
//                ", responseTime=" + responseTime +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ExportVO exportVO = (ExportVO) o;
//        return Objects.equals(instanceId, exportVO.instanceId) &&
//                Objects.equals(raw, exportVO.raw) &&
//                Objects.equals(defaultTag, exportVO.defaultTag) &&
//                Objects.equals(expertTag, exportVO.expertTag) &&
//                Objects.equals(userName, exportVO.userName) &&
//                Objects.equals(userTag, exportVO.userTag) &&
//                Objects.equals(userType, exportVO.userType);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(instanceId, raw, defaultTag, expertTag, userName, userTag, userType);
//    }
//}

//@JSONType(ignores = {"instanceId", "expertTag"})
public class ExportVO implements Serializable {
//    @JSONField(serialize = false)  //不序列化id
    private Long instanceId;  //数据编号
    @JSONField(name = "item")  //配置序列化名称
    private String raw;       //原始数据

    private String defaultTag; //默认标签（随机）
    private String expertTag; //专家标签
//    private List<InstanceUser> records;  //该条数据的用户标注记录
    private List<InstanceUserVO> records;  //该条数据的用户标注记录

    public ExportVO() {
        super();
    }

    public ExportVO(Long instanceId, String raw, String defaultTag, String expertTag, List<InstanceUserVO> records) {
        this.instanceId = instanceId;
        this.raw = raw;
        this.defaultTag = defaultTag;
        this.expertTag = expertTag;
        this.records = records;
    }

    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getDefaultTag() {
        return defaultTag;
    }

    public void setDefaultTag(String defaultTag) {
        this.defaultTag = defaultTag;
    }

    public String getExpertTag() {
        return expertTag;
    }

    public void setExpertTag(String expertTag) {
        this.expertTag = expertTag;
    }

    public List<InstanceUserVO> getRecords() {
        return records;
    }

    public void setRecords(List<InstanceUserVO> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "ExportVO{" +
                "instanceId=" + instanceId +
                ", raw='" + raw + '\'' +
                ", defaultTag='" + defaultTag + '\'' +
                ", expertTag='" + expertTag + '\'' +
                ", records=" + records +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExportVO exportVO = (ExportVO) o;
        return Objects.equals(instanceId, exportVO.instanceId) &&
                Objects.equals(raw, exportVO.raw) &&
                Objects.equals(defaultTag, exportVO.defaultTag) &&
                Objects.equals(expertTag, exportVO.expertTag) &&
                Objects.equals(records, exportVO.records);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instanceId, raw, defaultTag, expertTag, records);
    }
}
