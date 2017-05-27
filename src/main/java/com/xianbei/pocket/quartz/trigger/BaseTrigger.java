package com.xianbei.pocket.quartz.trigger;

import org.quartz.CronScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

/**
 * Created by zhudaoming on 2016/11/29.
 */
public abstract class BaseTrigger {
    public TriggerBuilder addTrigger(String job, String group){
        TriggerBuilder triggerBuilder=TriggerBuilder.newTrigger();
        triggerBuilder.withIdentity(job, group);
        return  triggerBuilder;
    }
    public Trigger getTrigger(TriggerBuilder triggerBuilder, String cronStr){
        return triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cronStr)).startNow().build();
    }
}
