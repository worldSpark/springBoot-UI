package com.fc.util.thread;

import com.alibaba.fastjson.JSONObject;
import com.fc.model.common.PositionEntity;
import com.fc.util.GlobalFunc;
import com.fc.util.HttpRequest;
import com.fc.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:
 *
 * @Author: wanpq
 * @Date: 2020/7/14 0014 14:33
 * @Version 1.0
 */
@Slf4j
public class ReportPointByThread implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    //请求路径
    private String url = "";
    //当前参数
    private String thisData;
    //用于同步轨迹数据
    private Map<String,Object> thisMap;
    //用于传输token信息
    private Map<String, Object> token;
    //用于装非360的集合
//    private List<PositionEntity> list = new ArrayList<>();

//    public List<PositionEntity> getList() {
//        return list;
//    }
//
//    public void setList(List<PositionEntity> list) {
//        this.list = list;
//    }

    public ReportPointByThread(String thisData, Map<String,Object> thisMap){
        this.thisData = thisData;
        this.thisMap = thisMap;
    }

    public Map<String, Object> getToken() {
        return token;
    }

    public void setToken(Map<String, Object> token) {
        this.token = token;
    }

    @Override
    public void run() {
        //获取token
        try {
            if (token==null||token.isEmpty()) {
                Map<String, Object> tokenMap = HttpRequest.token();
                String code = GlobalFunc.toString(tokenMap.get("code"));
                token =tokenMap;
                if(!"0".equals(code)){
                    log.info("获取token失败,"+GlobalFunc.toString(token.get("message")));
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //根据url和参数获取结果
        List<PositionEntity> list  = new ArrayList<>();
        List<PositionEntity> listData = JSONObject.parseArray(thisData, PositionEntity.class);
        for (PositionEntity positionEntity : listData) {
            if (thisMap.containsKey(positionEntity.getSerialNumber())) {
                list.add(positionEntity);
            }
        }

        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat indDate = new SimpleDateFormat("yyyyMMdd");
        //同步轨迹数据
        if (list != null && list.size() > 0) {
            String thisImei = list.get(0).getSerialNumber();
            Long reportDate = list.get(0).getReportDate();
            String workDate = format.format(reportDate);
            for (PositionEntity position : list) {
                Map<String, Object> thisMap = new HashMap<>();
                Integer factoryCode = position.getCompCode();
                String imei = position.getSerialNumber();
                long date = position.getReportDate() /*+ (8 * 60 * 60 * 1000)*/;
                String sendTime = dateFormat.format(date);
                String indexDate = indDate.format(date);
                double longitude = position.getLongitude();
                double latitude = position.getLatitude();
                Integer azimuth = position.getOrientation();
                double speed = position.getSpeed();
                Integer altitude = position.getAltitude();
                thisMap.put("imei", imei);
                thisMap.put("factory_code", factoryCode);
                thisMap.put("send_time", sendTime);
                thisMap.put("index_date", indexDate);
                thisMap.put("longitude", longitude);
                thisMap.put("latitude", latitude);
                thisMap.put("altitude", altitude);
                thisMap.put("azimuth", azimuth);
                thisMap.put("speed", speed);
                JSONObject jsonObj=new JSONObject(thisMap);
                log.info("--------------------------------"+"该农机设备号为:"+thisImei+"开始更新轨迹信息");
                try {
                    String success = HttpRequest.sendPostByHeaders("http://national-sync.dtwl360.com/track/currentDataTransfer", jsonObj.toString(), GlobalFunc.toString(token.get("token")));
                    net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(success);
                    String code = jsonObject.getString("code");
                    code = "1".equals(code)?"成功":"失败";
                    if(jsonObject.getString("message").indexOf("已过期")>-1&&"失败".equals(code)){
                        token = HttpRequest.token();
                        String success1 = HttpRequest.sendPostByHeaders("http://national-sync.dtwl360.com/track/currentDataTransfer", jsonObj.toString(), GlobalFunc.toString(token.get("token")));
                        net.sf.json.JSONObject jsonObject1 = net.sf.json.JSONObject.fromObject(success1);
                        String code1 = jsonObject1.getString("code");
                        code1 = "1".equals(code1)?"成功":"失败";
                        log.info("--------------------------------"+"同步轨迹数据"+code1+","+jsonObject1.getString("message"));
                    }
                    token.put("code",code);
                    token.put("message",jsonObject.getString("message"));
                } catch (Exception e) {
                    log.info("--------------------------------农机设备号为:" + thisImei + "同步轨迹数据发送失败");
                    e.printStackTrace();
                }
            }
        }
    }

}
