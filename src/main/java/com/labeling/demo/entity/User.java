package com.labeling.demo.entity;

public class User {
    private String username;

    private String password;

    private String role;

    private String teamName;

    public User(String username, String teamName) {
        this.username = username;
        this.teamName = teamName;
    }

    public User(String username, String password, String role, String teamName) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.teamName = teamName;
    }

    public User() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName == null ? null : teamName.trim();
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}