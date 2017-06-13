package com.xianbei.pocket.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhudaoming on 2016/11/24.
 */
@Component
@ConfigurationProperties(prefix = "task_list",locations = "file:config/kettle_task.yml")
public class TaskEntity {

    private List<Map<String, String>> task = new ArrayList<>();
    private List<Map<String, String>> quartz = new ArrayList<>();

    public List<Map<String, String>> getTask() {
        return task;
    }

    public void setTask(List<Map<String, String>> task) {
        this.task = task;
    }

    public List<Map<String, String>> getQuartz() {
        return quartz;
    }

    public void setQuartz(List<Map<String, String>> quartz) {
        this.quartz = quartz;
    }

}
