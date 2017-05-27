package com.xianbei.pocket.service.dataconver.command;

import com.alibaba.fastjson.JSON;
import com.xianbei.pocket.service.dataconver.adapter.KettleAdapter;
import org.apache.commons.lang.StringUtils;
import org.pentaho.di.core.Result;
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
 * Created by zhudaoming on 2017/4/20.
 */
@Component("executeKettle")
public class ExecuteKettle extends AbstractCmdBase{
    private static final Logger LOG = LoggerFactory.getLogger(ExecuteKettle.class);

    @Autowired
    private KettleAdapter kettleAdapter;

    private String[] args={};

    @Override
    public Map<String, Object> execute(String tmName) {
        LOG.info("==================开始任务!====================== ");
        Map<String,String> variable = new HashMap<>();
        long starTime=System.currentTimeMillis();
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dqtime=format.format(date);
        variable.put("sysnow",dqtime);
        variable.put("last_date_time",dqtime);
        LOG.info("variable【"+ JSON.toJSONString(variable)+"】");

        kettleAdapter.execute(tmName, args,variable);

        long endTime=System.currentTimeMillis();
        long time=endTime-starTime;
        LOG.info("任务执行所需时间!!"+time/1000+"s");
        return null;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
