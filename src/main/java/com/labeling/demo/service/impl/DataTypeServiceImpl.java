package com.labeling.demo.service.impl;

import com.labeling.demo.entity.DataType;
import com.labeling.demo.repository.DataTypeMapper;
import com.labeling.demo.service.DataTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DataTypeServiceImpl implements DataTypeService {

    private DataTypeMapper dataTypeMapper;

    @Autowired
    public DataTypeServiceImpl(DataTypeMapper dataTypeMapper) {
        this.dataTypeMapper = dataTypeMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(DataType dataType) {
        dataTypeMapper.insertSelective(dataType);
    }

    @Override
    public DataType findByName(String typeName) {
        return dataTypeMapper.findByName(typeName);
    }

    @Override
    public List<DataType> findAll() {
        return dataTypeMapper.findAll();
    }

    @Override
    public void updateDataType(DataType dataType) {
        dataTypeMapper.updateByPrimaryKeySelective(dataType);
    }
}
