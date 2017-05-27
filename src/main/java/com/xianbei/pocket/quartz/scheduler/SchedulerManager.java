package com.xianbei.pocket.quartz.scheduler;

import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Map;
import java.util.Set;

/**
 * Created by zhudaoming on 2016/11/29.
 */
public class SchedulerManager extends StdSchedulerFactory {
    public SchedulerManager() {
        SchedulerManager schedulerFactory = null;
        try {
            schedulerFactory = new SchedulerManager("quartz.properties");
            scheduler = schedulerFactory.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public SchedulerManager(String path) throws SchedulerException {
        super(path);
    }

    /**
     * 执行多个job以及多个事件
     *
     * @param jobMap
     * @throws SchedulerException
     */
    public void executeJobs(Map jobMap) throws SchedulerException {
        this.clear();
        this.start();
        scheduler.scheduleJobs(jobMap, true);
    }

    /**
     * 执行一个job和一个事件
     *
     * @param var1
     * @param var2
     * @throws SchedulerException
     */
    public void executeJob(JobDetail var1, Trigger var2) throws SchedulerException {
        scheduler.scheduleJob(var1, var2);
    }

    /**
     * 执行一个job和多个事件
     *
     * @param var1
     * @param var2
     * @throws SchedulerException
     */
    public void executeJobForTriggers(JobDetail var1, Set<? extends Trigger> var2) throws SchedulerException {
        this.clear();
        this.start();
        scheduler.scheduleJob(var1, var2, true);
    }

    private org.quartz.Scheduler scheduler;

    public org.quartz.Scheduler getSchedulers() {
        return scheduler;
    }

    public void start() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
        }
    }

    public void stop() {
        try {
            if (scheduler.isStarted()) {
                scheduler.shutdown();
            }
        } catch (SchedulerException e) {
        }
    }

    public void clear() {
        if (scheduler != null) {
            try {
                scheduler.clear();
            } catch (SchedulerException e) {
                e.printStackTrace();
            }

        }
    }
}
