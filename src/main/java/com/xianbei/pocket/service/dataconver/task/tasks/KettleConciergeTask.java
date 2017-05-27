package com.xianbei.pocket.service.dataconver.task.tasks;

import com.alibaba.fastjson.JSON;
import com.xianbei.pocket.service.dataconver.adapter.KettleAdapter;
import com.xianbei.pocket.service.dataconver.command.AbstractCmdBase;
import com.xianbei.pocket.domain.TaskFt;
import org.pentaho.di.core.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by zhudaoming on 2016/11/23.
 */
public class KettleConciergeTask extends AbstractTask implements Callable<TaskFt> {
    private static final Logger LOG = LoggerFactory.getLogger(KettleConciergeTask.class);

    public KettleConciergeTask(KettleAdapter kettleAdapter, String tm, String[] args, Map<String, String> variable) {
        this.kettleAdapter = kettleAdapter;
        this.tm = tm;
        this.args = args;
        this.variable = variable;
    }

    @Override
    public TaskFt call() throws Exception {
        TaskFt taskFt = new TaskFt();
        taskFt.setTmName(tm);
        taskFt.setArgs(args);
        this.setTaskFt(taskFt);
        LOG.info("任务名称:【" + tm + "】shutdown状态:【" + kettleAdapter.isShutdownFl() + "】");
        if (!kettleAdapter.isShutdownFl()) {
            Result result = kettleAdapter.execute(tm, this.args, this.variable).getResult();
            this.setTaskState(result);
        }

        return this.getTaskFt();
    }

    public void setTaskState(Result result) throws Exception {
        TaskFt taskFt = this.getTaskFt();
        String code = kettleAdapter.getRtStr(AbstractCmdBase.HTTPCODE, result);
        if (code != null) {
            taskFt.setRtCode(code);
            taskFt.setRtTime(kettleAdapter.getRtStr(AbstractCmdBase.HTTPCONNTIME, result));
            taskFt.setRtPageNum(kettleAdapter.getRtStr(AbstractCmdBase.PAGENUM, result));
            taskFt.setUrl(kettleAdapter.getRtStr(AbstractCmdBase.URL, result));
            taskFt.setRtPageSize(kettleAdapter.getRtStr(AbstractCmdBase.PAGESIZE, result));
        } else {
            taskFt.setRtCode("500");
            taskFt.setRtTime("0");
        }
    }

}
