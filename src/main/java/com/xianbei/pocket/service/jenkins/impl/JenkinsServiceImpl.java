package com.xianbei.pocket.service.jenkins.impl;

import com.alibaba.fastjson.JSON;
import com.xianbei.pocket.pojo.JenkinJob;
import com.xianbei.pocket.service.jenkins.JenkinsService;
import com.xianbei.pocket.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhudaoming on 2017/6/9.
 */
@Service("jenkinsService")
public class JenkinsServiceImpl implements JenkinsService {
    @Autowired
    private JenkinJob jenkinJob;

    @Override
    public void triggerBuild(String hook_body) {
        try {
            Map job_map = null;
            Map m = JSON.parseObject(hook_body, HashMap.class);
            Map repository = JSON.parseObject(m.get("repository").toString(), HashMap.class);
            String repo_name = repository.get("name").toString();
            String repo_full_name = repository.get("full_name").toString();
            for (Map job_m : jenkinJob.getJobs()) {
                if (job_m.containsValue(repo_name)) {
                    job_map = job_m;
                }
            }
            if (job_map != null) {
                String jenkinsUrl = jenkinJob.getJenkins_host()
                        +"/job/" + job_map.get("job_name")
                        + "/" + job_map.get("job_type")
                        + "?token=" + job_map.get("job_token")
                        + "&" + job_map.get("job_param");
                System.out.println(jenkinsUrl);
                System.out.println(repo_name);
                System.out.println(repo_full_name);
                String rep_body=HttpUtil.getResponseBody(HttpUtil.createConnection(jenkinsUrl));
                System.out.println(rep_body);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
