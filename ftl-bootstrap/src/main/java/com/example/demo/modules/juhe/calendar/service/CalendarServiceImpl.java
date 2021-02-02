package com.example.demo.modules.juhe.calendar.service;

import com.example.demo.modules.juhe.calendar.api.CalendarApi;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${spring.redis.isopen}")
    private boolean REDIS_IS_OPEN;

    @Override
    public JSONObject calendar(String type) {
        String key="calendar_"+type;
        String str="";
        JSONObject object=null;
        if(REDIS_IS_OPEN){
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            //采用redis缓存,先查缓存，如果存在则在缓存中获取
            if(stringRedisTemplate.hasKey(key)) {
                str = ops.get(key);
            }else{
                if("day".equalsIgnoreCase(type)){
                    str= CalendarApi.getCalendarDayInfo(new Date());
                    ops.set(key,str,6L, TimeUnit.MINUTES);
                }else if("month".equalsIgnoreCase(type)){
                    str= CalendarApi.getCalendarMonthInfo(new Date());
                    ops.set(key,str,1L, TimeUnit.HOURS);
                }if("year".equalsIgnoreCase(type)){
                    str= CalendarApi.getCalendarYearInfo(new Date());
                    ops.set(key,str,1L, TimeUnit.DAYS);
                }
            }
        }else{
            if("day".equalsIgnoreCase(type)){
                str= CalendarApi.getCalendarDayInfo(new Date());
            }else if("month".equalsIgnoreCase(type)){
                str= CalendarApi.getCalendarMonthInfo(new Date());
            }if("year".equalsIgnoreCase(type)){
                str= CalendarApi.getCalendarYearInfo(new Date());
            }
        }

        object = JSONObject.fromObject(str);
        return object;
    }
}
