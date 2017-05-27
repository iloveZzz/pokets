package com.xianbei.pocket.service.dataconver.task.execJobs;

import com.xianbei.pocket.quartz.jobs.BaseJob;
import com.xianbei.pocket.service.dataconver.command.ExecuteJobKettle;
import com.xianbei.pocket.service.dataconver.task.util.ApplicationContextUtil;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhudaoming on 2017/5/8.
 */
public class ConciergePirceJsonSynJob extends BaseJob {
    private static final Logger LOG = LoggerFactory.getLogger(ConciergePirceJsonSynJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOG.info("处理ConciergePirceJsonSynJob任务!");
        JobDataMap taskEntity = jobExecutionContext.getMergedJobDataMap();
        ExecuteJobKettle executeJobKettle = (ExecuteJobKettle) ApplicationContextUtil.getContext().getBean("executeJobKettle");
        LOG.info("TaskName:【"+taskEntity.get("name")+"】");
        LOG.info("Cron:【"+taskEntity.get("cron")+"】");
        LOG.info("TaskSize:【"+ taskEntity.get("size")+"】");
        executeJobKettle.setTaskSize(Integer.valueOf(taskEntity.get("size").toString()));
        executeJobKettle.execute(taskEntity.get("name").toString());

    }
}
