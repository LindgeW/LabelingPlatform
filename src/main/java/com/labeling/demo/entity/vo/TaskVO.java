package com.labeling.demo.entity.vo;

import com.labeling.demo.entity.DataType;
import com.labeling.demo.entity.Task;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class TaskVO extends Task {
    private String dataType;    //标注类型
    private String[] tagSet;    //标签集

    public TaskVO(Task task) {
        super(task.getTaskname(), task.getDatatype(), task.getCorpussize(), task.getTags(), task.getStatus(), task.getExpertname());
        this.dataType = DataType.getTypeByID(task.getDatatype());
        this.tagSet = StringUtils.split(task.getTags(), ";");
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String[] getTagSet() {
        return tagSet;
    }

    public void setTagSet(String[] tagSet) {
        this.tagSet = tagSet;
    }

    @Override
    public String toString() {
        return "TaskVO{" +
                "dataType='" + dataType + '\'' +
                ", tagSet=" + Arrays.toString(tagSet) +
                '}';
    }
}
