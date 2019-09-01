package com.labeling.demo.service.impl;

import com.labeling.demo.entity.Team;
import com.labeling.demo.repository.TeamMapper;
import com.labeling.demo.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private TeamMapper teamMapper;

    @Autowired
    public TeamServiceImpl(TeamMapper teamMapper) {
        this.teamMapper = teamMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean save(Team team) {
        return teamMapper.insertSelective(team) >= 1;
    }

    @Override
    public List<Team> findAll() {
        return teamMapper.findAll();
    }

    @Override
    public Team findByName(String teamName) {
        return teamMapper.findByName(teamName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTeam(Team team) {
        return teamMapper.updateByPrimaryKeySelective(team) >= 1;
    }

    @Override
    public Team findById(Integer teamId) {
        return teamMapper.selectByPrimaryKey(teamId);
    }

    @Override
    public void deleteTeamById(int teamId) {
        teamMapper.deleteByPrimaryKey(teamId);
    }

    @Override
    public List<Team> findByTaskName(String taskName) {
        return teamMapper.findByTaskName(taskName);
    }

    @Override
    public List<Team> findByExpertName(String username) {
        return teamMapper.findByExpertName(username);
    }

}
