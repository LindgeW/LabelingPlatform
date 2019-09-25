package com.labeling.demo.entity;

import org.omg.PortableInterceptor.INACTIVE;

public class Team {
    private Integer teamId;

    private String teamName;

    private Integer taskId;

    private String members;

    private Boolean status = false;

    private String expertname;

    public Team(String teamName, Integer taskId, String members, String expertname, Boolean status) {
        this.teamName = teamName;
        this.taskId = taskId;
        this.members = members;
        this.expertname = expertname;
        this.status = status;
    }

    public Team(Integer teamId, String teamName, Integer taskId, String members, Boolean status, String expertname) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.taskId = taskId;
        this.members = members;
        this.status = status;
        this.expertname = expertname;
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

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members == null ? null : members.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getExpertname() {
        return expertname;
    }

    public void setExpertname(String expertname) {
        this.expertname = expertname == null ? null : expertname.trim();
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", taskId=" + taskId +
                ", members='" + members + '\'' +
                ", status=" + status +
                ", expertname='" + expertname + '\'' +
                '}';
    }
}