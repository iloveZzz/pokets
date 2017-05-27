package com.xianbei.pocket.service.dataconver.adapter;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.trans.Trans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by zhudaoming on 2016/11/30.
 */
public class KettleSingleTask implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(KettleSingleTask.class);
    private Trans trans;
    private String[] args;
    public KettleSingleTask(Trans trans, String[] args){
        this.trans= trans;
        this.args=args;
    }
    @Override
    public void run() {
        try {
            trans.execute(args);
            trans.waitUntilFinished();
        } catch (KettleException e) {
            LOG.info("执行任务失败!");
            LOG.info("传入参数:【" + Arrays.asList(args)+"】");
            e.printStackTrace();
        }
    }
}
