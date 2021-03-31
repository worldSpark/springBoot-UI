package com.fc.task;

import com.alibaba.fastjson.JSON;
import com.fc.common.domain.ParameterTypeEntity;
import com.fc.util.AesUtil;
import com.fc.util.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: springbootui
 * @description:
 * @author: Mr.Wan
 * @create: 2021-03-30 17:04
 **/
@Slf4j
@Component
public class timingtask {

    @Scheduled(cron = "0 15 2 * * ?")
    public void run() throws Exception {
        //获取别的项目的token
        String token = token();
        //数据发送的请求
        String cKey = "u2qagd4s0w5v81bz";
        ParameterTypeEntity parameterTypeEntity = new ParameterTypeEntity();
        parameterTypeEntity.setInterfaceSign("DW01");
        String success = sendDate(new Object(), parameterTypeEntity, cKey, token);
        //解析发送后的结果
        JSONObject jsonObject = JSONObject.fromObject(success);
        //获取code值
        String code = jsonObject.getString("code");                            String list = jsonObject.getString("data");
        //获取数据
        String thisList = jsonObject.getString("data");
    }

    public String token() throws Exception {
        Map<String, Object> map = new HashMap<>();
        String appId = "36114028675697";
//        String appId="33091118711929";
        // 获取当前时间
        long timeMillis = System.currentTimeMillis();
        Date date = new Date(timeMillis);
        // 转换提日期输出格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        // 格式化
        String format = dateFormat.format(date);
        // 加密
        String keys = "qgnjaqjl" + format;
        String encryptOne = AesUtil.aesEncrypt(appId, keys);
        // 获取token
        map.put("appId", encryptOne);
        String jsonString = JSON.toJSONString(map);
        return getToken(jsonString);
    }

    /**
     * 获取token
     *
     * @param parameters
     * @return
     */
    private static String getToken(String parameters) {
        String result = HttpRequest.sendPost("http://222.175.245.154:8000/services/getToken", parameters);
        // 解析发送后的结果
        JSONObject object = JSONObject.fromObject(result);
        // 获取code值
        String codes = object.getString("code");
        // 如果code为500，停止定时器，打印错误
        if ("500".equals(codes)) {
            return "0";
        }
        String token = object.getString("data");
        return token;
    }

    private static String sendDate(Object upLoadEntity, ParameterTypeEntity parameterTypeEntity, String cKey, String token) throws Exception {
        // 将对象json化
        JSONObject jsonObject = JSONObject.fromObject(upLoadEntity);
        // 将旧数据对象用AES加密
        String encrypt = AesUtil.aesEncrypt(jsonObject.toString(), cKey);
        // 设置传参对象的token、和加密后的数据的值
        parameterTypeEntity.setToken(token);
        parameterTypeEntity.setBusinessParams(encrypt);
        // 将传参对象json化
        JSONObject parameter = JSONObject.fromObject(parameterTypeEntity);
        // 通过post方法发送给上级系统
        String success = HttpRequest.sendPost("http://222.175.245.154:8000/services/write", parameter.toString());
        return success;
    }

}
