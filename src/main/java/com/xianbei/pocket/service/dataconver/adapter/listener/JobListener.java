package com.xianbei.pocket.service.dataconver.adapter.listener;

import org.pentaho.di.core.ProgressMonitorListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhudaoming on 2017/5/3.
 */
public class JobListener implements ProgressMonitorListener {
    private static final Logger LOG = LoggerFactory.getLogger(JobListener.class);
    @Override
    public void beginTask(String s, int i) {
        LOG.info(s+"【"+i+"】");
    }

    @Override
    public void subTask(String s) {
        LOG.info("子任务:"+s);
    }

    @Override
    public boolean isCanceled() {
        LOG.info("任务已取消");
        return false;
    }

    @Override
    public void worked(int i) {
        LOG.info("工作任务数"+i);
    }

    @Override
    public void done() {
        LOG.info("载入job任务已完成");
    }

    @Override
    public void setTaskName(String s) {
        LOG.info("任务名称"+s);
    }
}
