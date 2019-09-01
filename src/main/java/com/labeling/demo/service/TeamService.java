package com.labeling.demo.service;

import com.labeling.demo.entity.Team;

import java.util.List;

public interface TeamService {
    Boolean save(Team team);

    List<Team> findAll();

    Team findByName(String teamName);

    List<Team> findByTaskName(String taskName);

    List<Team> findByExpertName(String username);

    boolean updateTeam(Team team);

    Team findById(Integer teamId);

    void deleteTeamById(int teamId);
}
