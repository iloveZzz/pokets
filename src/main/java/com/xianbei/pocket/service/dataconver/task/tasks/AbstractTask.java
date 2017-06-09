package com.xianbei.pocket.service.dataconver.task.tasks;

import com.xianbei.pocket.pojo.TaskFt;
import com.xianbei.pocket.service.dataconver.adapter.KettleAdapter;

import java.util.Map;

/**
 * Created by zhudaoming on 2017/4/19.
 */
public class AbstractTask {
    protected KettleAdapter kettleAdapter;
    protected String[] args;
    protected String tm;
    private TaskFt taskFt;
    protected Map<String,String> variable;

    public TaskFt getTaskFt() {
        return taskFt;
    }

    public void setTaskFt(TaskFt taskFt) {
        this.taskFt = taskFt;
    }


}
