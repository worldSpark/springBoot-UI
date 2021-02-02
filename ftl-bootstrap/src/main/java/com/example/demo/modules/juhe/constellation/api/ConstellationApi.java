package com.example.demo.modules.juhe.constellation.api;


import java.util.HashMap;
import java.util.Map;

import com.example.demo.modules.juhe.JHCommon;

/**
*星座运势调用示例代码 － 聚合数据
*在线接口文档：http://www.juhe.cn/docs/58
**/
 
public class ConstellationApi extends JHCommon {

    //配置您申请的KEY
    public static final String APPKEY ="1733c06d00e76cf67afa2a097207d7b0";
 
    //1.运势查询
    public static String getInfoByConstellation(String constellation){
        String url ="http://web.juhe.cn:8080/constellation/getAll";//请求接口地址
        Map params = new HashMap();//请求参数
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
            params.put("consName",constellation);//星座名称，如:白羊座
            params.put("type","today");//运势类型：today,tomorrow,week,nextweek,month,year
 
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