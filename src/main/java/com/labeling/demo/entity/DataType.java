package com.labeling.demo.entity;

public enum DataType {
    UNKNOWN("未知任务", (short)0),
    CLASSIFY("文本分类", (short)1);

    private String name;  //名称
    private Short id;    //对应的编号

    DataType(String name, Short id) {
        this.name = name;
        this.id = id;
    }

    public static String getTypeByID(Short id){
        if (id.equals(DataType.CLASSIFY.id)){
            return DataType.CLASSIFY.name;
        }

        return DataType.UNKNOWN.name;
    }

    public String getName() {
        return name;
    }

    public Short getId() {
        return id;
    }
}
