package com.example.demo.modules.juhe.dream.api;


import java.util.HashMap;
import java.util.Map;

import com.example.demo.modules.juhe.JHCommon;

/**
*周公解梦调用示例代码 － 聚合数据
*在线接口文档：http://www.juhe.cn/docs/64
**/
 
public class DreamApi extends JHCommon {
    //配置您申请的KEY
    public static final String APPKEY ="441aeb0ae4932c6e2fdc8d810f6ad572";

    //2.解梦查询
    public static String getDreamInfo(String query){
        String url =" http://v.juhe.cn/dream/query";//请求接口地址
        Map params = new HashMap();//请求参数
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
            params.put("q",query);//梦境关键字，如：黄金 需要utf8 urlencode
            params.put("cid","");//指定分类，默认全部
            params.put("full","");//是否显示详细信息，1:是 0:否，默认0
        try {
            return net(url, params, "GET");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
 
    }

}