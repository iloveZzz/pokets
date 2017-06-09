package com.xianbei.pocket.service.dataconver.command;

import com.alibaba.fastjson.JSON;
import com.xianbei.pocket.entity.ItravelApiBusiness;
import com.xianbei.pocket.service.dataconver.adapter.KettleAdapter;
import com.xianbei.pocket.service.dataconver.task.TaskManager;
import com.xianbei.pocket.service.dataconver.task.tasks.KettleAsoviewTask;
import com.xianbei.pocket.dao.typehandler.ItravelApiBusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by zhudaoming on 2017/4/19.
 */
@Component("asoviewCourseKettle")
public class AsoviewCourseKettle extends AbstractCmdBase {

    private static final Logger LOG = LoggerFactory.getLogger(AsoviewCourseKettle.class);

    @Autowired
    private KettleAdapter kettleAdapter;

    @Autowired
    private TaskManager taskManager;

    @Autowired
    private ItravelApiBusMapper itravelApiBusMapper;

    private Integer month_count=1;


    @Override
    public Map<String, Object> execute(String tmName) {
        LOG.info("获取任务列表");
        long starTime = System.currentTimeMillis();
        List<ItravelApiBusiness> list = itravelApiBusMapper.getItravelApiBusList("play");

        Map<String, Object> mp = new HashMap<String, Object>();
        LOG.info("==================开始任务!====================== ");
        Map<String, String> variable = new HashMap<>();
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dqtime = format.format(date);
        variable.put("sysnow", dqtime);
        variable.put("last_date_time", dqtime);
        LOG.info("variable【" + JSON.toJSONString(variable) + "】");
        List<Future> ft = taskManager.execute(this.getKtrList(list, tmName, variable), taskSize);
        List<String[]> errorLs = null;
        try {
            List<String[]> le = this.getErrorList(ft);
            errorLs = this.resultFt(le, tmName, variable);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOG.info("==================任务执行完毕!!====================== ");
        LOG.info("剩余错误任务size【" + errorLs.size() + "】");
        LOG.info("剩余错误任务【" + JSON.toJSONString(errorLs) + "】");
        long endTime = System.currentTimeMillis();
        long time = endTime - starTime;
        LOG.info("任务执行所需时间!!" + time / 1000 + "s");
        LOG.info("线程执行完毕关闭线程池");
        taskManager.shutDown();
        LOG.info("线程执行完毕关闭成功!");
        return null;
    }

    public List<KettleAsoviewTask> getKtrList(List<ItravelApiBusiness> list, String tm, Map<String, String> variable) {
        List<KettleAsoviewTask> tkList = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String nowTime = format.format(calendar.getTime());
        calendar.add(Calendar.MONTH, month_count);
        Date nextDate = calendar.getTime();
        String nextTime = format.format(nextDate);
        for (ItravelApiBusiness itravelApiBusiness : list) {
            String[] args = {itravelApiBusiness.getApiBusinessId(), nowTime, nextTime};
            KettleAsoviewTask kettleAsoviewTask = new KettleAsoviewTask(kettleAdapter, tm, args, variable);
            tkList.add(kettleAsoviewTask);
        }
        return tkList;
    }

    public List<String[]> resultFt(List<String[]> errorArgs, String tm, Map<String, String> variable) throws ExecutionException, InterruptedException {

        if (errorArgs.size() > 0 && errorCount < 5) {
            errorCount++;
            LOG.info("处理错误的任务,tm:【" + tm + "】");
            LOG.info("处理错误的任务,size:【" + errorArgs.size() + "】");
            List<Future> ftError = taskManager.execute(this.getErrortk(tm, errorArgs, variable), this.getTaskSize());
            errorArgs = this.resultFt(this.getErrorList(ftError), tm, variable);
        } else {
            errorCount = 0;
        }
        return errorArgs;

    }

    public List<KettleAsoviewTask> getErrortk(String tm, List<String[]> errorArgs, Map<String, String> variable) {
        List<KettleAsoviewTask> lk = new ArrayList();
        for (String[] e : errorArgs) {
            KettleAsoviewTask kettleAsoviewTask = new KettleAsoviewTask(kettleAdapter, tm, e, variable);
            lk.add(kettleAsoviewTask);
        }
        return lk;
    }

    public Integer getMonth_count() {
        return month_count;
    }

    public void setMonth_count(Integer month_count) {
        this.month_count = month_count;
    }
}
