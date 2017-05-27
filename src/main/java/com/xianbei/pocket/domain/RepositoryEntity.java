package com.xianbei.pocket.domain;

import org.pentaho.di.core.logging.LogLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by zhudaoming on 2016/11/22.
 */
@Component
@ConfigurationProperties(prefix = "repository",locations = "classpath:kettle.properties")
public class RepositoryEntity implements Serializable {
    private String user;
    private String password;

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    private String logLevel;
    private boolean shutdownFl=false;
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LogLevel getLogLevel() {
        if(logLevel.equals("debug")){
            return LogLevel.DEBUG;
        }else if(logLevel.equals("info")){
            return LogLevel.BASIC;
        }else if(logLevel.equals("error")){
            return LogLevel.ERROR;
        }else{
            return LogLevel.NOTHING;
        }
    }

    public boolean isShutdownFl() {
        return shutdownFl;
    }

    public void setShutdownFl(boolean shutdownFl) {
        this.shutdownFl = shutdownFl;
    }
}
