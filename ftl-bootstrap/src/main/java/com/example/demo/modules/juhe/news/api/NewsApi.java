package com.example.demo.modules.juhe.news.api;


import com.example.demo.modules.juhe.JHCommon;

import java.util.HashMap;
import java.util.Map;

/**
*新闻调用示例代码 － 聚合数据
*在线接口文档：http://www.juhe.cn/docs/64
**/
 
public class NewsApi extends JHCommon {
    //配置您申请的KEY
    public static final String APPKEY ="7b72d7642408e6248e96b6c1b06a5cb7";

    //2.解梦查询
    public static String getNewsList(String query){
        String url ="http://v.juhe.cn/toutiao/index";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("key",APPKEY);
        params.put("type","");//指定分类，默认top头条
        try {
            return net(url, params, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}