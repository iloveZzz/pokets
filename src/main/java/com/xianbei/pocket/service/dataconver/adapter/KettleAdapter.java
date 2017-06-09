package com.xianbei.pocket.service.dataconver.adapter;

import com.xianbei.pocket.pojo.MysqlEntity;
import com.xianbei.pocket.pojo.RepositoryEntity;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.Result;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.job.Job;
import org.pentaho.di.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Created by zhudaoming on 2016/11/21.
 */
@Component
public class KettleAdapter extends KettleBase {
    @Autowired
    private MysqlEntity mysqlEntity;
    @Autowired
    private RepositoryEntity repositoryEntity;

    public KettleAdapter() {

    }

    /**
     * 初始化方法
     */
    @PostConstruct
    public void init() {
//        if(System.getProperty("catalina.home").contains("tomcat-a")) {
            this.setMysqlEntity(mysqlEntity);
            this.setRepositoryEntity(repositoryEntity);
            try {
                KettleEnvironment.init();
                super.init();
            } catch (KettleException e) {
                e.printStackTrace();
            }
//        }
    }

    public Trans execute(String transName, String[] args,Map<String,String> variable) {
        return this.executeTrans(transName, args,variable);
    }
    public Job execute(String job_name) {
        return this.executeJob(job_name);
    }

    public Trans executeAsyn(String transName, String[] args) {
        return this.executeTransAsy(transName, args);
    }

    public void killAllTrans() {
        this.killAll();
    }
    public void setShutDownFl(boolean fl){
        repositoryEntity.setShutdownFl(fl);
    }
    public boolean isShutdownFl(){
       return repositoryEntity.isShutdownFl();
    }
    public String getRtStr(String key) throws Exception {
        return this.getResultStrByName(key, null);
    }

    public String getRtStr(String key, Result result) throws Exception {
        return this.getResultStrByName(key, result);
    }
}
