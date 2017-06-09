package com.xianbei.pocket.controller;

import com.xianbei.pocket.domain.TaskEntity;
import com.xianbei.pocket.service.dataconver.command.ConciergeKettle;
import com.xianbei.pocket.service.dataconver.task.TimingTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhudaoming on 2016/11/4.
 */
@Component
public class DataController {
    private static final Logger LOG = LoggerFactory.getLogger(DataController.class);
    @Autowired
    private ConciergeKettle commandKettle;

    @Autowired
    private TimingTask timingTask;
    @Autowired
    private TaskEntity taskEntity;

    /**
     * @param name 模板名稱
     * @return 手動執行模板, 完成數據轉換
     */
    public Map<String, Object> excTable(String name) {
        commandKettle.setShutdownFl(false);
        return commandKettle.execute(name);
    }

    public void killALL() {
        commandKettle.killAll();
    }

    /**
     * 初始化方法
     */
    @PostConstruct
    public void autoTask() {
//        List<Map<String, String>> task_list=taskEntity.getTask();
//        List<Map<String, String>> quartz_list=taskEntity.getQuartz();
//        if(task_list.size()!=quartz_list.size()){
//            LOG.error("任务与调度配置个数不匹配！请查看task.yml是否有错误！");
//        }else{
//            timingTask.stop();
//            timingTask.clearAll();//清除所有任务
//            for (int i =0;i<task_list.size();i++) {
//                Map<String, String> task=task_list.get(i);
//                Map<String, String> quartz=quartz_list.get(i);
//                LOG.info("开启定时任务【 " + i + " 】");
//                LOG.info("TaskName:【" + task.get("name") + "】");
//                LOG.info("Cron:【" + task.get("cron")+ "】");
//                LOG.info("TaskSize:【" + task.get("size") + "】");
//                LOG.info("job_mapper:【" + quartz.get("job_mapper") + "】");
//                Map<String, String> param = timingTask.addListener(
//                        quartz.get("job_name"),
//                        quartz.get("job_group"),
//                        quartz.get("trigger_name"),
//                        quartz.get("trigger_group")
//                );
//
//                param = timingTask.addCron(param, task.get("cron"));
//                Map dataParam = new HashMap();
//                Class job_mapper=null;
//                try {
//                     job_mapper=Class.forName(quartz.get("job_mapper"));
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    timingTask.executeAutoTask(param, task, job_mapper);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        timingTask.start();


    }
}
