package com.xianbei.pocket.service.dataconver.task.execJobs;

import com.xianbei.pocket.quartz.jobs.BaseJob;
import com.xianbei.pocket.service.dataconver.command.AsoviewBusKettle;
import com.xianbei.pocket.service.dataconver.command.ExecuteKettle;
import com.xianbei.pocket.service.dataconver.task.util.ApplicationContextUtil;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhudaoming on 2017/4/20.
 */
public class AsoviewPriceJsonJob extends BaseJob {
    private static final Logger LOG = LoggerFactory.getLogger(AsoviewPriceJsonJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOG.info("处理任务!");
        JobDataMap taskEntity = jobExecutionContext.getMergedJobDataMap();
        LOG.info("TaskName:【"+taskEntity.get("name")+"】");
        LOG.info("Cron:【"+taskEntity.get("cron")+"】");
        LOG.info("TaskSize:【"+ taskEntity.get("size")+"】");
        ExecuteKettle executeKettle = (ExecuteKettle) ApplicationContextUtil.getContext().getBean("executeKettle");
        executeKettle.execute(taskEntity.get("name").toString());
    }
}
