package com.fc.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Description 系统配置
 * @author luoy
 * @Date 2020年4月6日09:28:34
 * @version 1.0.0
 */
public class ApplicationConfig {

    private static ApplicationConfig applicationConfig;

    public void init() {
        applicationConfig = this;
    }

    public ApplicationConfig() {
    }

    // 数据库包
    private String dataSourceDriverClassName;

    // 数据库连接地址
    private String dataSourceUrl;

    // 数据库名
    private String dataBaseName = "";

    // 数据库用户名
    private String dataSourceUserName;

    // 数据库密码
    private String dataSourcePassword;


    // ------------------------------------------ get方法 ------------------------------------------

    public static String getDataSourceDriverClassName() {
        return applicationConfig.dataSourceDriverClassName;
    }

    public static String getDataSourceUrl() {
        return applicationConfig.dataSourceUrl;
    }

    public static String getDataSourceUserName() {
        return applicationConfig.dataSourceUserName;
    }

    public static String getDataSourcePassword() {
        return applicationConfig.dataSourcePassword;
    }

    /**
     * 获得数据库名（从连接地址中解析）
     * @return 数据库名
     */
    public static String getDataBaseName(){
        String dataBaseName;
        if(StringUtils.isNotBlank(applicationConfig.dataBaseName)){
            // 已经解析过了
            dataBaseName = applicationConfig.dataBaseName;
        }else{
            // 开始解析
            dataBaseName = new String("123456");
            if(StringUtils.isNotBlank(dataBaseName)){
                if(dataBaseName.contains("DatabaseName=")){
                    dataBaseName = dataBaseName.substring(dataBaseName.indexOf("DatabaseName=") + 13);
                }
                if(dataBaseName.contains(";")){
                    dataBaseName = dataBaseName.substring(0, dataBaseName.indexOf(";"));
                }
            }
            applicationConfig.dataBaseName = dataBaseName;
        }
        return dataBaseName;
    }
}
