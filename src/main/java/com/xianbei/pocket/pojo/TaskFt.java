package com.xianbei.pocket.pojo;

/**
 * Created by zhudaoming on 2016/11/23.
 */
public class TaskFt {
    private String rtCode;
    private String rtTime;
    private String rtPageSize;
    private String rtPageNum;
    private String rtMessage;
    private String url;
    private String tmName;
    private String[] args;

    public TaskFt() {
    }
    public String getRtCode() {
        return rtCode;
    }

    public void setRtCode(String rtCode) {
        this.rtCode = rtCode;
    }

    public String getRtTime() {
        return rtTime;
    }

    public void setRtTime(String rtTime) {
        this.rtTime = rtTime;
    }

    public String getRtPageSize() {
        return rtPageSize;
    }

    public void setRtPageSize(String rtPageSize) {
        this.rtPageSize = rtPageSize;
    }

    public String getRtPageNum() {
        return rtPageNum;
    }

    public void setRtPageNum(String rtPageNum) {
        this.rtPageNum = rtPageNum;
    }

    public String getRtMessage() {
        return rtMessage;
    }

    public void setRtMessage(String rtMessage) {
        this.rtMessage = rtMessage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTmName() {
        return tmName;
    }

    public void setTmName(String tmName) {
        this.tmName = tmName;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
