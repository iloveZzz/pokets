package com.xianbei.pocket.quartz.jobs.manager;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhudaoming on 2016/11/29.
 */
public abstract class BaseJobDetail {
    public BaseJobDetail() {
    }

    /**
     * 添加job集合
     *
     * @param jobList
     */
    public List<JobDetail> addJobs(List<Map<String, Object>> jobList) {
        List<JobDetail> lj = new ArrayList<>();
//        for (Map<String, Object> job : jobList) {
//            JobBuilder jb = this.addJob(job.get("jn").toString(), job.get("group").toString());
//            lj.add(jb.build());
//        }
        return lj;
    }

    /**
     * 添加job
     *
     * @param job
     * @param group
     * @return
     */
    public JobBuilder addJob(String job, String group,Class<? extends Job> jobClass) {
        JobBuilder jb = JobBuilder.newJob(jobClass).withIdentity(job, group);
        return jb;
    }

    /**
     * job传入数据参数
     *
     * @param jb
     * @param newJobDataMap
     * @return
     */
    public JobBuilder setJobData(JobBuilder jb, JobDataMap newJobDataMap) {
        return jb.setJobData(newJobDataMap);
    }

    /**
     * 获取jobDetail
     *
     * @param jb
     * @return
     */
    public JobDetail getJobDetail(JobBuilder jb) {
        return jb.build();
    }

}
