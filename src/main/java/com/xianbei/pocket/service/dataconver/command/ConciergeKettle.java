package com.xianbei.pocket.service.dataconver.command;

import com.alibaba.fastjson.JSON;
import com.xianbei.pocket.service.dataconver.adapter.KettleAdapter;
import com.xianbei.pocket.domain.TaskFt;
import com.xianbei.pocket.service.dataconver.task.tasks.KettleConciergeTask;
import com.xianbei.pocket.service.dataconver.task.TaskManager;
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
 * Created by zhudaoming on 2016/11/21.
 * etl-通过接口:https://pocket-concierge.jp/api
 */
@Component("conciergeKettle")
public class ConciergeKettle extends AbstractCmdBase {
    private static final Logger LOG = LoggerFactory.getLogger(ConciergeKettle.class);

    @Autowired
    private KettleAdapter kettleAdapter;

    @Autowired
    private TaskManager taskManager;

    public Boolean getShutdownFl() {
        return shutdownFl;
    }

    public void setShutdownFl(Boolean shutdownFl) {
        this.shutdownFl = shutdownFl;
        kettleAdapter.setShutDownFl(shutdownFl);
    }

    private Boolean shutdownFl=false;
    /**
     * 執行kettle 資源庫方法
     *
     * @param tm
     * @return
     */
    public Map<String, Object> execute(String tm) {
        Map<String, Object> mp = new HashMap<String, Object>();
        String[] args = {"0"};
        LOG.info("==================开始任务!====================== ");
        LOG.info("获取第0页数据");
        Map<String,String> variable = new HashMap<>();
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dqtime=format.format(date);
        variable.put("sysnow",dqtime);
        variable.put("last_date_time",dqtime);
        LOG.info("variable【"+JSON.toJSONString(variable)+"】");
        kettleAdapter.execute(tm, args,variable);
        if(shutdownFl){
            LOG.info("停止执行任务成功!!");
            kettleAdapter.setShutDownFl(false);
            return mp;
        }
        long starTime=System.currentTimeMillis();
        try {
            String httpCode = kettleAdapter.getRtStr(HTTPCODE);
            String httpConnTime = kettleAdapter.getRtStr(HTTPCONNTIME);
            if (httpCode == null&&taskCount<3) {
                LOG.error("获取接口返回数据失败");
                this.execute(tm);
                taskCount++;
            } else if (httpCode.equals(SUCCODE)) {
                taskCount=0;
                LOG.info("访问接口所需时间,time:" + Integer.valueOf(httpConnTime) / 1000 + "s");
                String pageSize = kettleAdapter.getRtStr(PAGESIZE);
                LOG.info("获取接口数据成功,pageSize:" + pageSize);
                LOG.info("第0页数据获取完毕!");
                int taskSize = Integer.valueOf(pageSize);
                int taskInit = 1;
                List<Future> ft = taskManager.execute(this.getKctLs(tm, taskInit, taskSize,variable),this.getTaskSize());

                if(shutdownFl){
                    LOG.info("停止执行任务成功!!");
                    kettleAdapter.setShutDownFl(false);
                    return mp;
                }
                List<String[]> errorLs = this.resultFt(this.getErrorList(ft), tm,variable);
                LOG.info("==================任务执行完毕!!====================== ");
                LOG.info("剩余错误任务size【"+errorLs.size()+"】");
                LOG.info("剩余错误任务【"+ JSON.toJSONString(errorLs)+"】");
                long endTime=System.currentTimeMillis();
                long time=endTime-starTime;
                LOG.info("任务执行所需时间!!"+time/1000+"s");
                LOG.info("线程执行完毕关闭线程池");
                taskManager.shutDown();
                LOG.info("线程执行完毕关闭成功!");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mp;
    }

    /**
     * 执行任务
     *
     * @param tm   任务名称
     * @param args 传入参数
     * @return
     */
    public Map<String, Object> execute(String tm, String[] args) {

        Map<String, Object> mp = new HashMap<String, Object>();
        try {
            Map<String,String> variable = new HashMap<>();
            variable.put("sysnow","");
            variable.put("last_date_time","");
            kettleAdapter.execute(tm, args,variable);

            String httpCode = kettleAdapter.getRtStr(HTTPCODE);
            if (httpCode == null) {
                LOG.error("获取接口返回数据失败");
            } else if (httpCode.equals(SUCCODE)) {
                String httpConnTime = kettleAdapter.getRtStr(HTTPCONNTIME);
                String pageSize = kettleAdapter.getRtStr(PAGESIZE);
                LOG.info("访问接口所需时间,time:" + Integer.valueOf(httpConnTime) / 1000 + "s");
                LOG.info("获取接口数据成功,pageSize:" + pageSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mp;
    }

    /**
     * 获取执行任务列表
     *
     * @param tm
     * @param taskInit
     * @param taskSize
     * @param variable
     * @return
     */
    public List<KettleConciergeTask> getKctLs(String tm, int taskInit, int taskSize, Map<String, String> variable) {
        List<KettleConciergeTask> lk = new ArrayList();
        for (int i = taskInit; i <= taskSize; i++) {
            String[] ti = {String.valueOf(i)};
            KettleConciergeTask kettleTask = new KettleConciergeTask(kettleAdapter, tm, ti,variable);
            lk.add(kettleTask);
        }
        return lk;
    }

    /**
     * 获取执行任务列表
     *
     * @param tm
     * @param errorArgs
     * @return
     */
    public List<KettleConciergeTask> getKctLs(String tm, List<String[]> errorArgs,Map<String, String> variable) {
        List<KettleConciergeTask> lk = new ArrayList();
        for (String[] e : errorArgs) {
            KettleConciergeTask kettleTask = new KettleConciergeTask(kettleAdapter, tm, e,variable);
            lk.add(kettleTask);
        }
        return lk;
    }
    /**
     * 重新处理发生错误的任务
     *
     * @param le 错误列表
     * @param tm
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<String[]> resultFt(List<String[]> le, String tm,Map<String, String> variable) throws ExecutionException, InterruptedException {
        List<String[]> errorArgs = le;
        Map<String, Object> dataParam = new HashMap();
        if (errorArgs.size() > 0&&errorCount<5&&!shutdownFl) {
            errorCount++;
            LOG.info("处理错误的任务,tm:【" + tm + "】");
            LOG.info("处理错误的任务,size:【" + errorArgs.size() + "】");
            List<Future> ftError = taskManager.execute(this.getKctLs(tm, errorArgs,variable),this.getTaskSize());
            errorArgs=this.resultFt(this.getErrorList(ftError), tm,variable);
        }else{
            errorCount=0;
        }
        return errorArgs;

    }
    public void killAll(){
        this.setShutdownFl(true);
        this.taskCount=0;
        this.errorCount=0;
        kettleAdapter.killAllTrans();
    }
    public void validateShutDown(){

    }
}
