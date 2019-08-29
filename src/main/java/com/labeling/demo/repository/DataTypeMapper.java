package com.labeling.demo.repository;

import com.labeling.demo.entity.DataType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface DataTypeMapper {
    int deleteByPrimaryKey(Short id);

    int insert(DataType record);

    int insertSelective(DataType record);

    DataType selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(DataType record);

    int updateByPrimaryKey(DataType record);

    @Select("select * from tb_data_type where typeName = #{typeName}")
    DataType findByName(String typeName);

    @Select("select * from tb_data_type")
    List<DataType> findAll();
}