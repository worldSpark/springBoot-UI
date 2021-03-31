package com.fc.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fc.common.domain.AjaxResult;
import com.fc.model.common.PositionEntity;
import com.fc.model.common.VehicleHistory;
import com.fc.util.GlobalFunc;
import com.fc.util.HttpRequest;
import com.fc.util.SecurityUtil;
import com.fc.util.thread.ReportPointByThread;
import com.fc.util.thread.ThirdPartMachineInfoManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @program: influxdb
 * @description:
 * @author: Mr.Wan
 * @create: 2021-03-30 17:35
 **/
@Slf4j
@RestController
public class InfluxdbController {

    @Autowired
    private InfluxDBTemplate<Point> influxDBTemplate;

    //用于存储非360的农机信息
    private Map<String,Object> thisMap = new HashMap<>();

    public String InsertInfluxDB(String deviceId, JSONArray jsonArray) {
        log.info("获取数据:{}条", jsonArray.size());

        List<Point> points = new ArrayList<Point>();

        //将MQ中获得的字符串解析成JSON对象
        for (int i = 0; i < jsonArray.size(); i++) {
            int orientation = 0;
            int altitude = 0;
            float speed = 0;
            double longitude = 0;
            double latitude = 0;
            JSONObject info = jsonArray.getJSONObject(i);
            if (!info.get("lng").toString().isEmpty()){
                longitude = Double.parseDouble(info.get("lng").toString());
            }
            if (!info.get("lat").toString().isEmpty()){
                latitude = Double.parseDouble(info.get("lat").toString());
            }
            if (!info.get("speed").toString().isEmpty()){
                speed = Float.parseFloat(info.get("speed").toString());
            }
            if (!info.get("azimuth").toString().isEmpty()){
                orientation = (int) Float.parseFloat(info.get("azimuth").toString());
            }

            if (!info.get("altitude").toString().isEmpty()) {
                altitude = (int) Float.parseFloat(info.get("altitude").toString());
            }
            String gpstime = info.get("gpsUtcTime").toString();
            //获取时间戳
            long ts  = 0;
            try {
                ts = Timestamp.valueOf(gpstime).getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Long time = ts;
            // 如果是北京时间就加8个小时
            Long localTime = time + (8 * 60 * 60 * 1000);
            Long reportTime = time + (16 * 60 * 60 * 1000);
            final Point p = Point.measurement("vehicle")
                    .time(localTime, TimeUnit.MILLISECONDS)
                    .tag("comDeviceId", 360 + deviceId)
                    .addField("longitude", longitude)
                    .addField("latitude", latitude)
                    .addField("altitude", altitude)
                    .addField("orientation", orientation)
                    .addField("speed", speed)
                    .build();
            points.add(p);
        }
        //存储
        influxDBTemplate.write(points);

        log.info("nxt{call}==>[influxdb历史数据存储]:{}", points.toString());
        return points.toString();
    }

    /**
     * 上报历史位置数据
     *
     * @param data 定位数据
     * @return
     * @author 王昊
     * @date 2019-03-26 15:40
     */
    @ResponseBody
    @PostMapping("/reportHistoryPoint")
    public Object reportHistoryPoint(String data) {
        return getReportPointByFlag("1",data);
    }

    /**
     * 上报实时位置数据
     *
     * @param data
     * @return
     */
    @ResponseBody
    @PostMapping("/reportPoint")
    public Object reportPoint(String data) {
        return getReportPointByFlag("0",data);
    }


    /**
     * 查询的接口
     * @param code
     * @return
     */
    @PostMapping("/selectListNew")
    @ResponseBody
    public List<Map<String, Object>> selectListNew(@RequestBody String code) {

        List<Map<String, Object>> mapList = new ArrayList<>();

        // 将json字符串转化为JavaBean
        List<String> codeList = new Gson().fromJson(code, new TypeToken<List<String>>() {
        }.getType());

        // 只需获取类型的泛型类 作为TypeToken的泛型参数,构造一个匿名的子类,通过getType()方法获取到使用的泛型类的泛型参数类型
        List<VehicleHistory> vehicleList = new ArrayList<>();


        //历史influx连接
        InfluxDB hisotryInfluxDB = influxDBTemplate.getConnection();
        for (String c : codeList) {
            Map<String, Object> map = new HashMap<>();
            try {

                Calendar cal=Calendar.getInstance();
                cal.add(Calendar.DATE,1);//这里改为1
                Date time=cal.getTime();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String day = format.format(time);
                Date dateEndTime = format.parse(day);//把字符串日期转化为date
                Calendar calendar = Calendar.getInstance();//得到日历
                calendar.setTime(dateEndTime);//把截止时间赋给日历;
                calendar.add(calendar.MONTH, -3);//设置为前3月
                String startTime = format.format(calendar.getTime());
                //sql语句
                String sql = "SELECT * FROM vehicle WHERE comDeviceId = '" + c + "' and time < '"+day+"' and time>'"+startTime+"' and latitude!=0 order by time desc limit 1";


                //查询历史结果集
                Query hisotryQuery = new Query(sql, "iotdatabase");
                QueryResult historyResult = hisotryInfluxDB.query(hisotryQuery);

                InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();

                List<VehicleHistory> hisotry = resultMapper.toPOJO(historyResult, VehicleHistory.class);

                //实时数据为空
                vehicleList.addAll(hisotry);

            } catch (Exception e) {
                map.put("msg", "InfluxDB中未查到(" + c + ")该设备信息！");
                map.put("error", e);
                mapList.add(map);
            }
        }

        for (VehicleHistory v : vehicleList) {
            Map<String, Object> map = new HashMap<>();
            map.put("comDeviceId", v.getComDeviceId());
            map.put("speed", v.getSpeed());
            map.put("time", v.getTime());
            map.put("altitude", v.getAltitude());
            map.put("latitude", v.getLatitude());
            map.put("longitude", v.getLongitude());
            map.put("orientation", v.getOrientation());
            map.put("online", v.getOnline());
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 获取历史接口,推送信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/reportHisrotyByTrack")
    @ResponseBody
    public Object reportHisrotyByTrack(String start, String end) throws Exception {

        /*String start = "2020-07-17";
        String end = "2020-01-01";*/
        if(StringUtils.isBlank(start)||StringUtils.isBlank(end)){
            return AjaxResult.error( "未填写时间");
        }
        List<Date> dateList = getListDate(start, end);
        String reQuestTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat indexSimple = new SimpleDateFormat("yyyyMMdd");
        //SimpleDateFormat sendFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter sendFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

        List<Map<String, Object>> mapList = new ArrayList<>();
//        List<Map<String, Object>> mapList = driverCardResginerMapper.getDriverCardIotNumbers();
        log.info("获取非360的数量为"+mapList.size());
        for (Map<String, Object> map : mapList) {
            String iotNumber = GlobalFunc.toString(map.get("iot_number"));
            String compCode = GlobalFunc.toString(map.get("comp_Code"));
            thisMap.put(iotNumber,compCode);
        }

        //历史influx连接
        InfluxDB hisotryInfluxDB = influxDBTemplate.getConnection();
        log.info("--------------------------------"+"开始连接influxdb"+"--------------------------------");
        String todayStart = " 00:00:00";
        String todayEnd = " 23:59:59";
        long successCount = 0L;
        long errorCount = 0L;

        //循环日期
        for (Date date : dateList) {
            Map<String, Object> token = getThisToken();
            if(!"success".equals(GlobalFunc.toString(token.get("msg")))){
                return AjaxResult.error( "获取token失败,"+GlobalFunc.toString(token.get("message")));
            }

            String thisDate = simpleDateFormat.format(date);
            String indexDate = indexSimple.format(date);

            //循环非360集合
            long thisCount = 0L;

            for (Map.Entry<String, Object> entry : thisMap.entrySet()) {
                thisCount++;
                String imei = entry.getKey();
                String compCode = GlobalFunc.toString(entry.getValue());
                String deviceId = compCode+imei;
                String sql = "SELECT * FROM vehicle WHERE time >='"+thisDate+todayStart+"' and time<='"+thisDate+todayEnd+"' and comDeviceId='"+deviceId+"'";
                //查询历史结果集
                Query hisotryQuery = new Query(sql, "iotdatabase");
                try {
                    QueryResult historyResult = hisotryInfluxDB.query(hisotryQuery);
                    InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
                    List<VehicleHistory> hisotrys = resultMapper.toPOJO(historyResult, VehicleHistory.class);
                    log.info("--------------------------------"+"influxdb查询数据成功,农机设备号为:"+imei+"查询坐标集合数为:"+hisotrys.size()+",时间为:"+thisDate+"，deviceId为:"+deviceId+"--------------------------------");

                    List<Map<String,Object>> thisList = new ArrayList<>();
                    Map<String, Object> map = new HashMap<>();
                    //循环对比deviceId

                    if (hisotrys != null && hisotrys.size() > 0) {
                        for (int i = 0; i < hisotrys.size(); i++) {
                            VehicleHistory hisotry = hisotrys.get(i);
                            Map<String, Object> thisMap = new HashMap<>();
                            String thisDeviceId = hisotry.getComDeviceId();
                            Instant time = hisotry.getTime();
                            String sendTime = sendFormat.format(time);
                            if (thisDeviceId.equals(deviceId)) {
                                Double longitude = hisotry.getLongitude();
                                Double latitude = hisotry.getLatitude();
                                Integer orientation = hisotry.getOrientation();
                                Double speed = hisotry.getSpeed();
                                Double altitude = hisotry.getAltitude();
                                thisMap.put("factory_code", compCode);
                                thisMap.put("imei", imei);
                                thisMap.put("send_time", sendTime);
                                thisMap.put("index_date", indexDate);
                                thisMap.put("longitude", longitude);
                                thisMap.put("latitude", latitude);
                                thisMap.put("azimuth", orientation);
                                thisMap.put("speed", speed);
                                thisMap.put("altitude", altitude);
                                thisList.add(thisMap);
                            }
                        }
                        map.put("imei", imei);
                        map.put("work_date", thisDate);
                        map.put("data", thisList);
                        JSONObject jsonObj = new JSONObject(map);
                        log.info("--------------------------------"+"该农机设备号为:"+imei+"开始更新轨迹信息"+"循环次数:"+thisCount+"--------------------------------");
                        try {
                            String success = HttpRequest.sendPostByHeaders("http://national-sync.dtwl360.com/track/datatransfer", jsonObj.toString(), GlobalFunc.toString(token.get("token")));
                            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(success);
                            String code = jsonObject.getString("code");
                            code = "1".equals(code)?"成功":"失败";
                            log.info("--------------------------------"+"同步轨迹数据"+code+","+jsonObject.getString("message")+"循环次数在"+thisCount
                                    +"----------------------------------"+"成功数为:"+successCount+"个,失败数为:"+errorCount+"个");
                            if(jsonObject.getString("message").indexOf("已过期")>-1&&"失败".equals(code)){
                                token = getThisToken();
                                String headers = HttpRequest.sendPostByHeaders("http://national-sync.dtwl360.com/track/datatransfer", jsonObj.toString(), GlobalFunc.toString(token.get("token")));
                                net.sf.json.JSONObject jsonObject1 = net.sf.json.JSONObject.fromObject(headers);
                                String code1 = jsonObject1.getString("code");
                                code1 = "1".equals(code1)?"成功":"失败";
                                log.info("--------------------------------"+"同步轨迹数据"+code1+","+jsonObject1.getString("message")+"循环次数在"+thisCount+"----------------------------------");
                            }
                            successCount++;
                        } catch (Exception e) {
                            log.info("农机设备号为:"+imei+"同步轨迹数据发送失败"+"循环次数:"+thisCount);
                            errorCount++;
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    log.info("--------------------------------"+"influxdb查询数据失败,目前的农机设备号为"+imei+"循环次数:"+thisCount+"----------------------------------");
                    //e.printStackTrace();
                }
            }
        }
        return AjaxResult.success( start+"到"+end+"的时间请求完成,"+"请求开始时间为:"
                +reQuestTime+"现在的时间为:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"同步轨迹数据成功数为:"+successCount+"个,失败数为:"+errorCount+"个");
    }

    /**
     * 获取一个时间集合
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private List<Date> getListDate(String startTime, String endTime) {

        String start = startTime;
        String end = endTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dStart = null;
        Date dEnd = null;
        try {
            dStart = sdf.parse(start);
            dEnd = sdf.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Date> dateList = findDates(dStart, dEnd);
        return dateList;
    }

    /**
     * 获取某段时间内的所有日期
     *
     * @param dStart
     * @param dEnd
     * @return
     */
    public List<Date> findDates(Date dStart, Date dEnd) {
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(dStart);

        List dateList = new ArrayList();
        //把起始日期加上
        dateList.add(dStart);
        // 此日期是否在指定日期之后
        while (dEnd.after(cStart.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cStart.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(cStart.getTime());
        }
        Collections.reverse(dateList);
        return dateList;
    }

    @Scheduled(cron = "0 30 23 * * ?")
    public Object getTimingByTrack() throws Exception {
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date time=cal.getTime();
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(time);
        return reportHisrotyByTrack(yesterday,yesterday);
    }

    public Map<String,Object> getThisToken(){
        Map<String,Object> token = new HashMap<>();
        try {
            token = HttpRequest.token();
            if("0".equals(GlobalFunc.toString(token.get("code")))){
                log.info("--------------------------------"+"获取token成功"+"--------------------------------");
                token.put("msg","success");
            }else {
                log.info("获取token失败,"+GlobalFunc.toString(token.get("message")));
                token.put("msg","error");
            }
        } catch (Exception e) {
            log.info("获取token失败");
            token.put("msg","error");
            //e.printStackTrace();
        }
        return token;
    }

    @RequestMapping("/selectVehicleMonthTrack")
    @ResponseBody
    public List<Map<String,Object>> selectVehicleMonthTrack(String comDeviceId, String time) {
        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        InfluxDB hisotryInfluxDB = influxDBTemplate.getConnection();
        List<VehicleHistory> vehicleList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> mapList = new ArrayList<>();

        try {
            Date dateEndTime = format.parse(time);//把字符串日期转化为date
            Calendar calendar = Calendar.getInstance();//得到日历
            calendar.setTime(dateEndTime);//把截止时间赋给日历;
            calendar.add(calendar.MONTH, -2);//设置为前2月
            String startTime = format.format(calendar.getTime());
            String sql = "SELECT *  FROM vehicle WHERE comDeviceId = '" + comDeviceId + "' and time <= '"+time+"' and time>='"+startTime+"' " +
                    "and latitude!=0 ";
            Query hisotryQuery = new Query(sql, "iotdatabase");
            QueryResult historyResult = hisotryInfluxDB.query(hisotryQuery);
            InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
            vehicleList = resultMapper.toPOJO(historyResult, VehicleHistory.class);

        }catch (Exception e){
            map.put("msg", "InfluxDB中未查到(" + comDeviceId + ")该设备信息！");
            map.put("error", e );
            mapList.add(map);
        }
        Set<String> sets = new HashSet<>();
        for (VehicleHistory v : vehicleList) {
            String thisTime = format.format(Date.from(v.getTime()));
            sets.add(thisTime);
        }
        for (String set : sets) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("time",set);
            mapList.add(hashMap);
        }
        return mapList;
    }


    @PostMapping("/getVehicleListBySql")
    @ResponseBody
    public Object getVehicleListBySql(String sql) {
        InfluxDB influxDB = influxDBTemplate.getConnection();
       /* String sql = "SELECT * FROM vehicle where time >='" + startTime + "' and time <='" + endTime+" and latitude!=0 ";
        if("1".equals(status)){
            sql+= "' and comDeviceId =~/^"+code+"/ ";
        }else {
            sql+= "' and comDeviceId = '"+code+"' ";
        }*/
        sql = SecurityUtil.decryptAES(sql);
        log.info("sql查询语句:{}", sql);
        Query query = new Query(sql, "iotdatabase");
        QueryResult result = influxDB.query(query);
        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        List<VehicleHistory> list = resultMapper.toPOJO(result, VehicleHistory.class);
        for (VehicleHistory vehicleHistory : list) {
            vehicleHistory.setTimes(vehicleHistory.getTime().toEpochMilli());
        }
        return AjaxResult.successData(200 , list);
    }


    /**
     * 根据请求参数的结果判断走实时接口还是历史接口,以免代码重复
     * @param flag ,为0查询实时接口,为1查询历史接口
     * @return
     */
    public Object getReportPointByFlag(String flag,String data){

        //----------------------------------以下是存入influxdb的数据,同时同步轨迹信息数据-----------------------------------
        List<PositionEntity> list = JSONObject.parseArray(data, PositionEntity.class);
        String updateTrack = "";

        String positionStr = "";
        InfluxDB rtInfluxDB = null;
        if("0".equalsIgnoreCase(flag)){
            //获取非360的集合,取出发送过来的信息对比,将包含在集合中的数据集合取出来
            Map<String,Object> token = sendByHttp(data);
            if(token!=null&&!token.isEmpty()){
                updateTrack = " ，--------------------------------同步轨迹信息："+ GlobalFunc.toString(token.get("message"));
            }else {
                updateTrack = " ，--------------------------------同步轨迹信息失败";
            }
            log.info(updateTrack);

            positionStr = "实时";
            rtInfluxDB = InfluxDBFactory.connect("http://172.31.204.105:8086", "admin", "1234567");
        }else if("1".equalsIgnoreCase(flag)){
            positionStr = "历史";
        }
        log.info("report{reportPoint}==>[上报"+positionStr+"位置]，" + data);
        try {
            Integer success = 0;
            Integer error = 0;
            for (PositionEntity position : list) {
                /**
                 * -----------------------------influx调用开始插入----------------------------------------
                 */
                String serialNumber = position.getSerialNumber();
                String comDeviceId = position.getCompCode() + serialNumber;
                position.setReportDate(position.getReportDate() + (8 * 60 * 60 * 1000));
                log.info("report{reportHistoryPoint}==>["+positionStr+"位置信息：]，" + position.toString());
                final Point p = Point.measurement("vehicle")
                        .time(position.getReportDate(), TimeUnit.MILLISECONDS)
                        .tag("comDeviceId", comDeviceId)
                        .addField("longitude", position.getLongitude())
                        .addField("latitude", position.getLatitude())
                        .addField("altitude", position.getAltitude().intValue())
                        .addField("orientation", position.getOrientation())
                        .addField("speed", position.getSpeed())
                        .build();
                influxDBTemplate.write(p);
                if(rtInfluxDB!=null){
                    rtInfluxDB.write("realTimeDB", "realTime", p);
                }
                success++;
                /**
                 * ---------------------------influx调用结束-----------------------------------------
                 */
            }
            if (error <= 0) {
                log.info("report{reportPoint}==>[上报"+positionStr+"位置成功]");
                return AjaxResult.success( "上报定位数据成功"+updateTrack);
            } else {
                log.error("report{reportPoint}==>[上报结果：{}条上报成功,{}条上报失败]", success, error);
                return AjaxResult.error("上报结果：" + success + "条上报成功," + error + "条数据上报失败"+updateTrack);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("report{reportPoint}==>[上报"+positionStr+"位置异常]，{}", e.toString());
            return AjaxResult.error("发生异常,异常原因为：" + e.toString()+updateTrack);
        }
    }

    /**
     * 根据Http请求获取数据
     * @param data
     */
    public Map<String, Object> sendByHttp(String data) {
        //用于存储非360的农机信息
        List<PositionEntity> list = new ArrayList<>();
        Map<String, Object> token = new HashMap<>();
        if(thisMap==null||thisMap.isEmpty()){
            List<Map<String, Object>> mapList = new ArrayList<>();
//            List<Map<String, Object>> mapList = driverCardResginerMapper.getDriverCardIotNumbers();
            for (Map<String, Object> map : mapList) {
                String iotNumber = GlobalFunc.toString(map.get("iot_number"));
                String compCode = GlobalFunc.toString(map.get("comp_Code"));
                thisMap.put(iotNumber,compCode);
            }
        }
        try {
            ThirdPartMachineInfoManager instance = ThirdPartMachineInfoManager.getInstance();
            ReportPointByThread reportPointByThread = new ReportPointByThread(data, thisMap);
            reportPointByThread.run();
            instance.addExecuteTask(reportPointByThread);
            token = reportPointByThread.getToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }


}
