package com.example.demo.modules.juhe.book.api;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.modules.juhe.JHCommon;

/**
 *图书电商数据调用示例代码 － 聚合数据
 *在线接口文档：http://www.juhe.cn/docs/50
 **/

public class SyncBookApi extends JHCommon {
    //配置您申请的KEY
    public static final String APPKEY ="7af21c0b33df207a77dae059c654bd65";

    //1.图书分类目录
    public static String getBookCatalog(){
        String url ="http://apis.juhe.cn/goodbook/catalog";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
        params.put("dtype","json");//返回数据的格式,xml或json，默认json
        try {
            return net(url, params, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //2.图书内容
    public static String getBookList(String catalog,Integer startPage){
        String url ="http://apis.juhe.cn/goodbook/query";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
        params.put("catalog_id",catalog);//目录编号
        params.put("pn",startPage);//数据返回起始
        params.put("rn",30);//数据返回条数，最大30
        params.put("dtype","json");//返回数据的格式,xml或json，默认json
        try {
            return net(url, params, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}