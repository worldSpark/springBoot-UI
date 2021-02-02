package com.example.demo.modules.juhe.stock.api;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.modules.juhe.JHCommon;
import com.example.demo.modules.juhe.stock.vo.StockResponse;
import com.example.demo.modules.juhe.stock.vo.StockResult;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StockApi extends JHCommon {

    //配置您申请的KEY
    public static final String APPKEY ="1991e8cf7b984ef74bc7b9161fcab32f";


    //4.香港股市列表
    public static StockResult getHKStockList(Integer page){
        String url ="http://web.juhe.cn:8080/finance/stock/hkall";//请求接口地址
        return getStockList(url,page);
    }

    //5.美国股市列表
    public static StockResult getUSAStockList(Integer page){
        String url ="http://web.juhe.cn:8080/finance/stock/usaall";//请求接口地址
        return getStockList(url,page);
    }

    //6.深圳股市列表
    public static StockResult getSZStockList(Integer page){
        String url ="http://web.juhe.cn:8080/finance/stock/szall";//请求接口地址
        return getStockList(url,page);
    }

    //7.沪股列表
    public static StockResult getSHStockList(Integer page){
        String url ="http://web.juhe.cn:8080/finance/stock/shall";//请求接口地址
        return getStockList(url,page);
    }

    public static StockResult getStockList(String url,Integer page){
        ObjectMapper mapper = new ObjectMapper();
        String result =null;
        Map params = new HashMap();//请求参数
        params.put("key",APPKEY);//您申请的APPKEY
        params.put("page",page);//第几页,每页20条数据,默认第1页
        try {
            result =net(url, params, "GET");
            StockResponse res = mapper.readValue(result, StockResponse.class);
            if("0".equals(res.getError_code())){
                return res.getResult();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new StockResult();
    }

    public static void main(String[]args){
        StockResult ret = getSZStockList(1);
        System.out.println(ret.getData().get(0));
    }




}
