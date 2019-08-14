package com.labeling.demo.service;

import com.labeling.demo.entity.Team;

import java.util.List;

public interface TeamService {
    Boolean save(Team team);

    List<Team> findAll();

    Team findByName(String teamName);

    boolean updateByTeamName(Team team);

    Team findByTaskName(String taskName);
}
