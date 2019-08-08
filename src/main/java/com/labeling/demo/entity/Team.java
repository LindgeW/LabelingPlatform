package com.labeling.demo.entity;

public class Team {
    private Integer teamId;

    private String teamName;

    private String taskName;

    private Short memberNum;

    public Team(String teamName, String taskName, Short memberNum) {
        this.teamName = teamName;
        this.taskName = taskName;
        this.memberNum = memberNum;
    }

    public Team(Integer teamId, String teamName, String taskName, Short memberNum) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.taskName = taskName;
        this.memberNum = memberNum;
    }

    public Team() {
        super();
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName == null ? null : teamName.trim();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public Short getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(Short memberNum) {
        this.memberNum = memberNum;
    }
}