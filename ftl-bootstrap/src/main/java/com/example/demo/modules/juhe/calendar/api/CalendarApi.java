package com.example.demo.modules.juhe.calendar.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.core.util.DateUtil;

import com.example.demo.modules.juhe.JHCommon;

/**
*万年历调用示例代码 － 聚合数据
*在线接口文档：http://www.juhe.cn/docs/177
**/
 
public class CalendarApi extends JHCommon{


    //配置您申请的KEY
    public static final String APPKEY ="72d92f4d5f27033066da9a3522da0865";

 
    //1.获取当天的详细信息
    public static String getCalendarDayInfo(Date date){
        String url ="http://v.juhe.cn/calendar/day";//请求接口地址
        Map params = new HashMap();//请求参数
            params.put("key",APPKEY);//您申请的appKey
            params.put("date", DateUtil.formatShortDate(date,"YYYY-M-d"));//指定日期,格式为YYYY-MM-DD,如月份和日期小于10,则取个位,如:2012-1-1
        try {
            return net(url, params, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 
    //2.获取当月近期假期
    public static String getCalendarMonthInfo(Date date){

        String url ="http://v.juhe.cn/calendar/month";//请求接口地址
        Map params = new HashMap();//请求参数
            params.put("key",APPKEY);//您申请的appKey
            params.put("year-month",DateUtil.formatShortDate(date,"YYYY-M"));//指定月份,格式为YYYY-MM,如月份和日期小于10,则取个位,如:2012-1
 
        try {
            return net(url, params, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 
    //3.获取当年的假期列表
    public static String getCalendarYearInfo(Date date){
        String url ="http://v.juhe.cn/calendar/year";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("key",APPKEY);//您申请的appKey
        params.put("year",DateUtil.formatDate(date,"YYYY"));//指定年份,格式为YYYY,如:2015
 
        try {
            return net(url, params, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 
 
 
    public static void main(String[] args) {
        //getCalendarDayInfo(new Date());
        getCalendarYearInfo(new Date());
    }

}