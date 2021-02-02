package com.example.demo.modules.juhe.sms;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.modules.juhe.JHCommon;
import net.sf.json.JSONObject;
 
/**
*短信API服务调用示例代码 － 聚合数据
*在线接口文档：http://www.juhe.cn/docs/54
**/
 
public class SMSDemo extends JHCommon {

    //配置您申请的KEY
    public static final String APPKEY ="*************************";
 
    //1.屏蔽词检查测
    public static void getRequest1(){
        String result =null;
        String url ="http://v.juhe.cn/sms/black";//请求接口地址
        Map params = new HashMap();//请求参数
            params.put("word","");//需要检测的短信内容，需要UTF8 URLENCODE
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
 
        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    //2.发送短信
    public static void getRequest2(){
        String result =null;
        String url ="http://v.juhe.cn/sms/send";//请求接口地址
        Map params = new HashMap();//请求参数
            params.put("mobile","");//接收短信的手机号码
            params.put("tpl_id","");//短信模板ID，请参考个人中心短信模板设置
            params.put("tpl_value","");//变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
            params.put("dtype","");//返回数据的格式,xml或json，默认json
 
        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
 
 
    public static void main(String[] args) {
 
    }

}