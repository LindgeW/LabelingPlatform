package com.labeling.demo.service;

import com.labeling.demo.entity.DataType;

import java.util.List;

public interface DataTypeService {
    void save(DataType dataType);

    DataType findByName(String typeName);

    List<DataType> findAll();

    void updateDataType(DataType dataType);
}
