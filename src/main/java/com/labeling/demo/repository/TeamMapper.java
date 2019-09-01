package com.labeling.demo.repository;

import com.labeling.demo.entity.Team;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeamMapper {
    int deleteByPrimaryKey(Integer teamId);

    int insert(Team record);

    int insertSelective(Team record);

    Team selectByPrimaryKey(Integer teamId);

    int updateByPrimaryKeySelective(Team record);

    int updateByPrimaryKey(Team record);

    @Select("select * from tb_team")
    List<Team> findAll();

    @Select("select * from tb_team where taskName = #{taskName}")
    List<Team> findByTaskName(String taskName);

    @Select("select * from tb_team where teamName = #{teamName}")
    Team findByName(String teamName);

    @Select("select * from tb_team where expertname = #{expertname}")
    List<Team> findByExpertName(String username);
}