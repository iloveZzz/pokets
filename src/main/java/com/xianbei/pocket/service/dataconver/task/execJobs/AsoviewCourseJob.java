package com.xianbei.pocket.service.dataconver.task.execJobs;

import com.xianbei.pocket.quartz.jobs.BaseJob;
import com.xianbei.pocket.service.dataconver.command.AsoviewCourseKettle;
import com.xianbei.pocket.service.dataconver.task.util.ApplicationContextUtil;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhudaoming on 2017/4/19.
 */
public class AsoviewCourseJob extends BaseJob {
    private static final Logger LOG = LoggerFactory.getLogger(AsoviewCourseJob.class);

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOG.info("处理任务!");
        JobDataMap taskEntity = jobExecutionContext.getMergedJobDataMap();
        LOG.info("TaskName:【"+taskEntity.get("name")+"】");
        LOG.info("Cron:【"+taskEntity.get("cron")+"】");
        LOG.info("TaskSize:【"+ taskEntity.get("size")+"】");
        AsoviewCourseKettle asoviewKettle = (AsoviewCourseKettle) ApplicationContextUtil.getContext().getBean("asoviewCourseKettle");
        asoviewKettle.setTaskSize(Integer.valueOf(taskEntity.get("size").toString()));
        asoviewKettle.setMonth_count(Integer.valueOf(taskEntity.get("month_count").toString()));
        asoviewKettle.execute(taskEntity.get("name").toString());
    }
}