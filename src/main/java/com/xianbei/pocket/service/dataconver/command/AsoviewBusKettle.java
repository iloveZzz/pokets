package com.xianbei.pocket.service.dataconver.command;

import com.alibaba.fastjson.JSON;
import com.xianbei.pocket.service.dataconver.adapter.KettleAdapter;
import com.xianbei.pocket.service.dataconver.task.TaskManager;
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
 * Created by zhudaoming on 2017/4/17.
 */
@Component("asoviewBusKettle")
public class AsoviewBusKettle extends AbstractCmdBase {
    private static final Logger LOG = LoggerFactory.getLogger(AsoviewBusKettle.class);
    @Autowired
    private KettleAdapter kettleAdapter;

    @Override
    public Map<String, Object> execute(String tmName) {
        Map<String, Object> mp = new HashMap<String, Object>();
        String[] args = {"20",pageNum};
        LOG.info("==================开始任务!====================== ");
        Map<String,String> variable = new HashMap<>();
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dqtime=format.format(date);
        variable.put("sysnow",dqtime);
        variable.put("last_date_time",dqtime);
        LOG.info("variable【"+ JSON.toJSONString(variable)+"】");
        Result result = kettleAdapter.execute(tmName, args,variable).getResult();

        try {
            String httpCode = kettleAdapter.getRtStr(HTTPCODE);
            String httpConnTime = kettleAdapter.getRtStr(HTTPCONNTIME);
            String planDetail = kettleAdapter.getRtStr(AbstractCmdBase.PLAN, result);
            planDetail=planDetail==null?"":planDetail;
            if (httpCode.equals(SUCCODE)&&StringUtils.isEmpty(planDetail)) {
                LOG.info("访问接口所需时间,time:" + Integer.valueOf(httpConnTime) / 1000 + "s");
                pageNum="0";
            }
            if(planDetail.contains("plan_code")){
                pageNum=String.valueOf(Integer.valueOf(pageNum)+1);
                LOG.info("访问下一页【"+pageNum+"】");
                this.execute(tmName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
