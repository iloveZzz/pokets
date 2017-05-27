package com.xianbei.pocket.service.dataconver.command;

import com.xianbei.pocket.domain.TaskFt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by zhudaoming on 2016/11/4.
 */
public abstract class AbstractCmdBase {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractCmdBase.class);

    public static final String HTTPCODE = "httpcode";
    public static final String HTTPCONNTIME = "httpConnTime";
    public static final String PAGENUM = "pageNum";
    public static final String URL = "url";
    public static final String PAGESIZE = "pageSize";
    public static final String SUCCODE = "200";
    public static final String ERRORCODE = "500";
    public static final String PLAN = "plan";
    public abstract Map<String,Object> execute(String tmName);

    protected int taskCount = 0;
    protected  int errorCount=0;
    protected int taskSize = 3;//同时运行任务的个数
    protected String pageNum = "0";

    public List<String[]>  getErrorList(List<Future> ft){
        List<String[]> errorArgs = new ArrayList<>();
        Map<String, Object> dataParam = new HashMap();
        for (Future f : ft) {
            TaskFt tf = null;
            try {
                tf = (TaskFt) f.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (ERRORCODE.equals(tf.getRtCode())) {
                LOG.info("处理执行失败的任务!");
                LOG.info("参数tm:【" + tf.getTmName() + "】");
                LOG.info("参数getArgs:【" + Arrays.asList(tf.getArgs()) + "】");
                errorArgs.add(tf.getArgs());
            }
        }
        return errorArgs;
    }

    public int getTaskSize() {
        return taskSize;
    }

    public void setTaskSize(int taskSize) {
        this.taskSize = taskSize;
    }


}
