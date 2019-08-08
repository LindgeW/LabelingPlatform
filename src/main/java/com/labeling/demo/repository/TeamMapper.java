package com.labeling.demo.repository;

import com.labeling.demo.entity.Team;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamMapper {
    int deleteByPrimaryKey(Integer teamId);

    int insert(Team record);

    int insertSelective(Team record);

    Team selectByPrimaryKey(Integer teamId);

    Team findByName(String teamName);

    int updateByPrimaryKeySelective(Team record);

    int updateByPrimaryKey(Team record);
}