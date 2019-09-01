package com.labeling.demo.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum TagType {
    UNKNOWN((short)0, "未知任务", ""),
    CLASSIFY((short)1, "文本分类", "annotate"),
    NER((short)2, "实体识别", "ner_annotate"),
    SEMANTIC_SIM((short)3, "语义相似度", "semantic_annotate");
    ;

    private Short id;    //编号
    private String name;  //名称
    private String url;  //标注页面url

    private static Map<Short, TagType> valueMap = new HashMap<>();

    static {
        for (TagType tagType: TagType.values()){
            valueMap.put(tagType.getId(), tagType);
        }
    }

    TagType(Short id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public static TagType getTypeByID(Short id){
        return valueMap.get(id);
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
