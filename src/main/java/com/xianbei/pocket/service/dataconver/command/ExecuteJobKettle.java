package com.xianbei.pocket.service.dataconver.command;

import com.alibaba.fastjson.JSON;
import com.xianbei.pocket.service.dataconver.adapter.KettleAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhudaoming on 2017/5/3.
 */
@Component("executeJobKettle")
public class ExecuteJobKettle extends AbstractCmdBase{
    private static final Logger LOG = LoggerFactory.getLogger(ExecuteJobKettle.class);

    @Autowired
    private KettleAdapter kettleAdapter;

    private String[] args={};
    @Override
    public Map<String, Object> execute(String tmName) {
        LOG.info("==================开始任务!====================== ");
        long starTime=System.currentTimeMillis();
        kettleAdapter.execute(tmName);
        long endTime=System.currentTimeMillis();
        long time=endTime-starTime;
        LOG.info("任务执行所需时间!!"+time/1000+"s");
        return null;
    }
}
