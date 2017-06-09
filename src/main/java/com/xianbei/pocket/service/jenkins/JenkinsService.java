package com.xianbei.pocket.service.jenkins;

/**
 * Created by zhudaoming on 2017/6/9.
 */
public interface JenkinsService {
    public void triggerBuild(String hook_body);
}
