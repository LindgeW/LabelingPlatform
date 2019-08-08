package com.labeling.demo.service.impl;

import com.labeling.demo.entity.Team;
import com.labeling.demo.repository.TeamMapper;
import com.labeling.demo.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {

    private TeamMapper teamMapper;

    @Autowired
    public TeamServiceImpl(TeamMapper teamMapper) {
        this.teamMapper = teamMapper;
    }

    @Override
    public void save(Team team) {
        teamMapper.insertSelective(team);
    }

    @Override
    public Team findByName(String teamName) {
        return teamMapper.findByName(teamName);
    }
}
