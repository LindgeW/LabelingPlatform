package com.labeling.demo.entity.vo;

import com.labeling.demo.entity.Instance;

public class InstanceVO<T> extends Instance {
    private T taskItem;     // 与具体任务相关的泛型数据项
    private String tag;     // 最终展示给用户的标签(由随机标签、专家标签和模型标签决策生成)

    public InstanceVO() {
        super();
    }

    public T getTaskItem() {
        return taskItem;
    }

    public void setTaskItem(T taskItem) {
        this.taskItem = taskItem;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "InstanceVO{" +
                "taskItem=" + taskItem +
                ", tag='" + tag + '\'' +
                '}';
    }
}
