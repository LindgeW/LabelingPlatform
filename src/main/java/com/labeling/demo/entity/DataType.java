package com.labeling.demo.entity;

public enum DataType {
    UNKNOWN((short)0, "未知任务", ""),
    CLASSIFY((short)1, "文本分类", "annotate"),
    NER((short)2, "实体识别", "ner_annotate")
    ;

    private Short id;    //对应的编号
    private String name;  //名称
    private String url;  //标注页面url

    DataType(Short id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public static DataType getTypeByID(Short id){
        if (id.equals(DataType.CLASSIFY.id)){
            return DataType.CLASSIFY;
        } else if (id.equals(DataType.NER.id)){
            return DataType.NER;
        }

        return DataType.UNKNOWN;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Short getId() {
        return id;
    }
}
