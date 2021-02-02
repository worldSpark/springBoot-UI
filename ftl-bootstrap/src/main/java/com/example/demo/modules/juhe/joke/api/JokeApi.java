package com.example.demo.modules.juhe.joke.api;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.modules.juhe.JHCommon;

/**
*笑话大全调用示例代码 － 聚合数据
*在线接口文档：http://www.juhe.cn/docs/95
**/
 
public class JokeApi extends JHCommon {
    //配置您申请的KEY
    public static final String APPKEY ="0a8df7e8c8f3d8f758f65da633260fe6";
    //2.最新笑话
    public static String getRecentJokeList(Integer page,Integer pagesize){
        String url ="http://v.juhe.cn/joke/content/text.php";//请求接口地址
        Map params = new HashMap();//请求参数
            params.put("page",page);//当前页数,默认1
            params.put("pagesize",pagesize);//每次返回条数,默认1,最大20
            params.put("key",APPKEY);//您申请的key
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