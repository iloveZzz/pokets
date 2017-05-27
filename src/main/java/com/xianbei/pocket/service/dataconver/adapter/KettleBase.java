package com.xianbei.pocket.service.dataconver.adapter;

import com.xianbei.pocket.domain.MysqlEntity;
import com.xianbei.pocket.domain.RepositoryEntity;
import com.xianbei.pocket.service.dataconver.adapter.listener.JobListener;
import org.pentaho.di.core.Result;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by zhudaoming on 2016/11/22.
 */
public abstract class KettleBase {
    private static final Logger LOG = LoggerFactory.getLogger(KettleBase.class);

    private KettleDatabaseRepository repository;
    private RepositoryDirectoryInterface directory;
    private KettleDatabaseRepositoryMeta kettleDatabaseMeta;
    private Trans trans;
    private Job jb;
    private TransMeta tm;
    private JobMeta jm;
    private String path=null;
    private List<Trans> tranLs = new ArrayList<>();

    public KettleBase() {
        repository = new KettleDatabaseRepository();
    }

    public void init() {
        LOG.info("初始化kettle基础信息");
        this.initDatabaseMeta();
        this.initRepository();
    }

    /**
     * 初始化數據庫
     *
     * @return
     */
    public void initDatabaseMeta() {
        LOG.info("初始化kettle数据源");
        DatabaseMeta dbm = new DatabaseMeta(mysqlEntity.getName(), mysqlEntity.getType(), mysqlEntity.getAccess(), mysqlEntity.getHost(), mysqlEntity.getDb(), mysqlEntity.getPort(), mysqlEntity.getUser(), mysqlEntity.getPwd());
        kettleDatabaseMeta = new KettleDatabaseRepositoryMeta(null, null, null, dbm);
    }

    /**
     * 初始化资源库
     */
    public void initRepository() {
        LOG.info("初始化kettle资源库");
        repository.clearSharedObjectCache();
        repository.init(kettleDatabaseMeta);
        try {
            repository.connect(repositoryEntity.getUser(), repositoryEntity.getPassword());
        } catch (KettleException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化资源路径
     *
     * @param path 路径
     */
    private void setTransName(String transName, String path) {
        try {
            directory = repository.loadRepositoryDirectoryTree().findDirectory(path = path == null ? "" : path);
            tm = repository.loadTransformation(transName, directory, null, true, null);
        } catch (KettleException e) {
            e.printStackTrace();
        }
    }
    private void setJobName(String name, String path) {
        try {
            directory = repository.loadRepositoryDirectoryTree().findDirectory(path = path == null ? "" : path);
            jm = repository.loadJob(name, directory,new JobListener(),null);
        } catch (KettleException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化转换信息
     */
    private Trans initTrans() {
        tm.setLogLevel(repositoryEntity.getLogLevel());
        trans = new Trans(tm);
        return trans;
    }
    /**
     * 初始化job信息
     */
    private Job initJobs() {
        jm.setLogLevel(repositoryEntity.getLogLevel());
        jb = new Job(repository,jm);
        return jb;
    }
    public Job executeJob(String job_name){
        LOG.info("开始JOB!");
        LOG.info("JOB名称:【" + job_name + "】");
        this.setJobName(job_name,this.getPath());
        Job job=this.initJobs();
        job.start();
        job.waitUntilFinished();
        if (job.getErrors()!=0) {
            LOG.info("执行JOB失败![JOB_NAME]"+job_name);
        }
        return job;
    }

    /**
     * 执行任务-同步 Trans
     *
     * @param transName 任务名称
     */
    public Trans executeTrans(String transName, String[] args,Map<String,String> variable) {
        LOG.info("开始任务!");
        LOG.info("任务名称:【" + transName + "】传入参数:【" + Arrays.asList(args) + "】");
        this.setTransName(transName, this.getPath());
        Trans trans = this.initTrans();
        tranLs.add(trans);
        try {

            trans.setVariable("sysnow",variable.get("sysnow"));
            trans.setVariable("last_date_time",variable.get("last_date_time"));
            trans.execute(args);
            trans.waitUntilFinished();
            tranLs.remove(trans);
        } catch (KettleException e) {
            LOG.info("执行任务失败!");
            LOG.info("任务名称:【" + transName + "】传入参数:【" + Arrays.asList(args) + "】");
            e.printStackTrace();
        }
        return trans;

    }

    /**
     * 执行任务-异步
     *
     * @param transName 任务名称
     */
    public Trans executeTransAsy(String transName, String[] args) {
        LOG.info("异步开始任务!");
        LOG.info("任务名称:【" + transName + "】传入参数:【" + Arrays.asList(args) + "】");
        this.setTransName(transName, null);
        Trans trans = this.initTrans();
        Thread t = new Thread(new KettleSingleTask(trans, args));
        t.start();
        return trans;

    }

    /**
     * 多线程时获取返回值
     *
     * @param keyName
     * @return
     * @throws Exception
     */
    public String getResultStrByName(String keyName, Result result) throws Exception {
        result = result == null ? this.trans.getResult() : result;
        String rtStr = null;
        if (result.getRows().size() <= 0) {
            LOG.info("获取返回值失败!");
        } else {
            rtStr = result.getRows().get(0).getString(keyName, "");
            LOG.info("返回值:【 " + keyName + " : " + rtStr + "】");
        }
        return rtStr;
    }

    public void killAll() {
        repositoryEntity.setShutdownFl(true);
        for (Trans t : tranLs) {
            if(!t.isFinishedOrStopped()){
                t.stopAll();
            }
        }
        tranLs = new ArrayList<>();
    }

    public void kill(Trans lt) {
        lt.killAll();
    }
    public RepositoryDirectoryInterface getDirectory() {
        return directory;
    }
    public KettleDatabaseRepository getRepository() {
        return repository;
    }

    private MysqlEntity mysqlEntity = new MysqlEntity();
    private RepositoryEntity repositoryEntity = new RepositoryEntity();

    public void setRepositoryEntity(RepositoryEntity repositoryEntity) {
        this.repositoryEntity = repositoryEntity;
    }

    public void setMysqlEntity(MysqlEntity mysqlEntity) {
        this.mysqlEntity = mysqlEntity;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
