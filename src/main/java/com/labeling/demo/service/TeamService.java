package com.labeling.demo.service;

import com.labeling.demo.entity.Team;

public interface TeamService {
    void save(Team team);

    Team findByName(String teamName);
}
