package com.labeling.demo.entity.vo;


public class TempoVO {
    private String userName;    //当前用户
    private String teamName;
    private String taskName;
    private Integer tagNum;     //当前用户标的数量
    private Integer corpusSize;  //标注数据集大小

    public TempoVO(String userName, String teamName, String taskName, Integer tagNum, Integer corpusSize) {
        this.userName = userName;
        this.teamName = teamName;
        this.taskName = taskName;
        this.tagNum = tagNum;
        this.corpusSize = corpusSize;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getTagNum() {
        return tagNum;
    }

    public void setTagNum(Integer tagNum) {
        this.tagNum = tagNum;
    }

    public Integer getCorpusSize() {
        return corpusSize;
    }

    public void setCorpusSize(Integer corpusSize) {
        this.corpusSize = corpusSize;
    }

    @Override
    public String toString() {
        return "TempoVO{" +
                "userName='" + userName + '\'' +
                ", teamName='" + teamName + '\'' +
                ", taskName='" + taskName + '\'' +
                ", tagNum=" + tagNum +
                ", corpusSize=" + corpusSize +
                '}';
    }
}
