package com.xianbei.pocket.service.dataconver.task.execJobs;

import com.xianbei.pocket.service.dataconver.command.ConciergeKettle;
import com.xianbei.pocket.service.dataconver.task.util.ApplicationContextUtil;
import com.xianbei.pocket.quartz.jobs.BaseJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by zhudaoming on 2016/11/29.
 */
public class ConciergeJob extends BaseJob {
    private static final Logger LOG = LoggerFactory.getLogger(ConciergeJob.class);

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException{
        LOG.info("处理任务!");
        JobDataMap taskEntity = jobExecutionContext.getMergedJobDataMap();
        ConciergeKettle conciergeKettle = (ConciergeKettle) ApplicationContextUtil.getContext().getBean("conciergeKettle");
        LOG.info("TaskName:【"+taskEntity.get("name")+"】");
        LOG.info("Cron:【"+taskEntity.get("cron")+"】");
        LOG.info("TaskSize:【"+ taskEntity.get("size")+"】");
        conciergeKettle.setTaskSize(Integer.valueOf(taskEntity.get("size").toString()));
        conciergeKettle.execute(taskEntity.get("name").toString());


    }
}
