package com.xianbei.pocket.service.dataconver.task.execJobs;

import com.xianbei.pocket.quartz.jobs.BaseJob;
import com.xianbei.pocket.service.dataconver.command.AsoviewBusKettle;
import com.xianbei.pocket.service.dataconver.task.util.ApplicationContextUtil;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhudaoming on 2017/4/17.
 */
public class AsoviewBusJob extends BaseJob {
    private static final Logger LOG = LoggerFactory.getLogger(AsoviewBusJob.class);

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOG.info("处理任务!");
        JobDataMap taskEntity = jobExecutionContext.getMergedJobDataMap();
        LOG.info("TaskName:【"+taskEntity.get("name")+"】");
        LOG.info("Cron:【"+taskEntity.get("cron")+"】");
        LOG.info("TaskSize:【"+ taskEntity.get("size")+"】");
        AsoviewBusKettle asoviewKettle = (AsoviewBusKettle) ApplicationContextUtil.getContext().getBean("asoviewBusKettle");
        asoviewKettle.execute(taskEntity.get("name").toString());
    }
}
