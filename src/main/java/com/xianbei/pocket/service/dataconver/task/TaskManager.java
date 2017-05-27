package com.xianbei.pocket.service.dataconver.task;

import com.xianbei.pocket.domain.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by zhudaoming on 2016/11/23.
 */
@Component
public class TaskManager {
    private int taskSize;
    private int taskInit;


    private ExecutorService newFixedThreadPool=null;


    public TaskManager(){

    }

    /**
     * 初始化方法
     */
    @PostConstruct
    public void init() {

    }


    /**
     * 执行任务
     * @param lk
     */
    public List<Future> execute(List lk,int size){
        List<Future> ft=null;
        newFixedThreadPool = Executors.newFixedThreadPool(size);
        try {
          ft  = newFixedThreadPool.invokeAll(lk);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ft;
    }
    public void shutDownNow(){
        if(newFixedThreadPool!=null){
            if(!newFixedThreadPool.isShutdown()){
                newFixedThreadPool.shutdownNow();
            }
        }
    }
    public void shutDown(){
        if(newFixedThreadPool!=null){
            if(!newFixedThreadPool.isShutdown()){
                newFixedThreadPool.shutdown();
            }
        }
    }
    public int getTaskInit() {
        return taskInit;
    }

    public void setTaskInit(int taskInit) {
        this.taskInit = taskInit;
    }

    public int getTaskSize() {
        return taskSize;
    }
    public ExecutorService getNewFixedThreadPool() {
        return newFixedThreadPool;
    }
    public void setTaskSize(int taskSize) {
        this.taskSize = taskSize;
    }
}
