package com.xianbei.pocket.service.dataconver.task;

import com.xianbei.pocket.quartz.jobs.manager.JobDetailManager;
import com.xianbei.pocket.quartz.scheduler.SchedulerManager;
import com.xianbei.pocket.quartz.trigger.TriggerManager;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by zhudaoming on 2016/11/29.
 */
@Component
public class TimingTask {
    private JobDetailManager jobDetailManager;
    private TriggerManager triggerManager;
    private SchedulerManager schedulerManager;

    /**
     * 初始化方法
     */
    @PostConstruct
    public void init() {
        jobDetailManager = new JobDetailManager();
        triggerManager = new TriggerManager();
        schedulerManager = new SchedulerManager();
    }


    public Map<String, String> addListener(String jobName, String jobGroup, String triggerName, String triggerGroup) {
        Map<String, String> param = new HashMap();
        param.put("job_name", jobName);
        param.put("job_group", jobGroup);
        param.put("trigger_name", triggerName);
        param.put("trigger_group", triggerGroup);
        return param;
    }

    public Map<String, String> addCron(Map<String, String> param, String cronStr) {
        param.put("cronStr", cronStr);
        return param;
    }

    /**
     * 一个job-一个事件
     *
     * @param map      参数
     * @param jobClass
     */
    public void executeAutoTask(Map<String, String> map, Map jobDataMap, Class<? extends Job> jobClass) throws Exception {
        JobDetail jobDetail = jobDetailManager.getJobDetail(jobDetailManager.addJob(map.get("job_name"), map.get("job_group"),jobClass).setJobData(new JobDataMap(jobDataMap)));
        Trigger trigger = triggerManager.getTrigger(triggerManager.addTrigger(map.get("trigger_name"), map.get("trigger_group")), map.get("cronStr"));
        schedulerManager.executeJob(jobDetail, trigger);
    }

    /**
     * 一个job-多个事件
     *
     * @param jobParam     参数
     * @param triggerParam 参数
     */
    public void executeAutoTask(Map<String, String> jobParam, List<Map<String, String>> triggerParam, Class<? extends Job> jobClass) throws Exception {
        JobDetail jobDetail = jobDetailManager.getJobDetail(jobDetailManager.addJob(jobParam.get("job_name"), jobParam.get("job_group"),jobClass));
        Set triggerSet = new HashSet();
        for (Map<String, String> ms : triggerParam) {
            Trigger trigger = triggerManager.getTrigger(triggerManager.addTrigger(ms.get("trigger_name"), ms.get("trigger_group")), ms.get("cronStr"));
            triggerSet.add(trigger);
        }
        schedulerManager.executeJobForTriggers(jobDetail, triggerSet);
    }
    public void clearAll(){
        schedulerManager.clear();
    }
    public void stop(){
        schedulerManager.stop();
    }
    public void start(){
        schedulerManager.start();
    }

}
