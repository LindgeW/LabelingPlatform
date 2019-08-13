package com.labeling.demo.entity;

public class Team {
    private Integer teamId;

    private String teamName;

    private String taskName;

    private String members;

    private Boolean status;

    public Team(String teamName, String taskName,String members) {
        this.teamName = teamName;
        this.taskName = taskName;
        this.members = members;
    }

    public Team(Integer teamId, String teamName, String taskName, String members) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.taskName = taskName;
        this.members = members;
    }

    public Team(Integer teamId, String teamName, String taskName, String members, Boolean status) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.taskName = taskName;
        this.members = members;
        this.status = status;
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

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}