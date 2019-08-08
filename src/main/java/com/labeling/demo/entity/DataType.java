package com.labeling.demo.entity;

public enum DataType {
    UNKNOWN("未知任务", 0),
    CLASSIFY("文本分类", 1);

    private String name;  //名称
    private Integer id;    //对应的编号

    DataType(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getTypeByID(Integer id){
        if (id.equals(DataType.CLASSIFY.id)){
            return DataType.CLASSIFY.name;
        }

        return DataType.UNKNOWN.name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
