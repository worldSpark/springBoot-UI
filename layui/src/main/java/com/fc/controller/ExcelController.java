package com.fc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fc.model.common.MachineryInfoOrigin;
import com.fc.model.common.MachineryInfo;
import com.fc.util.*;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @program:
 * @description:
 * @author: Mr.Wan
 * @create: 2021-03-29 11:32
 **/
@Controller
@RequestMapping("/excel")
public class ExcelController {

    //查询页面,里面有导入和下载模板导出等功能
    @RequestMapping("/batchQuery")
    public String batchQuery() {
        return "common/batchQuery";
    }


    //导入页面
    @RequestMapping("/doImportMachinery")
    public String doImportMachinery() {
        return "common/doImportMachinery";
    }

    //下载模板
    @RequestMapping(value = "/downloadTemplate", method = RequestMethod.GET)
    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
        String filePath = "/upload/出厂编号查询.xlsx";
        ExcelUtil.downloadTemplate(request, response, filePath, "出厂编号查询.xlsx");
    }

    //查询列表集合
    @ResponseBody
    @RequestMapping("/getMachineryData")
    public void getMachineryData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> machineryData = new HashMap<>();
        //List<Map<String,Object>> dataList = (List<Map<String, Object>>) machineryData.get("rows");
        Gson gson=new Gson();
        response.getWriter().print(gson.toJson(machineryData));
    }

    //查询导入集合
    @ResponseBody
    @RequestMapping("/getListByExcel")
    public void getListByExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Map<String,Object>> datas = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("机具名称","modelName");
        headerMap.put("出厂编号","factoryNumber");
        headerMap.put("物联网设备号","iotNumber");
        headerMap.put("作业面积","workArea");
        headerMap.put("轨迹点数","locationNum");
        headerMap.put("购机者","realName");
        headerMap.put("数据源","compCode");
        headerMap.put("状态","tmState");
        headerMap.put("补贴状态","tab");
        headerMap.put("更换过设备号","replaced");
        try {
            MultipartRequest multipartRequest=(MultipartRequest) request;
            MultipartFile excelFile=multipartRequest.getFile("excelFile");
            if(excelFile!=null){
                //判断后缀是xls还是xlsx
                String filename = excelFile.getOriginalFilename();
                if(filename.indexOf(".")>-1){
                    String type = filename.substring(filename.indexOf(".") + 1, filename.length());
                    if("xls".equals(type)){
                        map = ExcelUtil.readXls(excelFile.getInputStream(),headerMap);
                    }else if("xlsx".equals(type)){
                        map = ExcelUtil.readXlsx(excelFile.getInputStream(),headerMap);
                    }
                }
            }
            //如果显示所有信息,则要默认匹配查询农机
            List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("list");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //循环取出出厂编号和物联网设备号
            for (Map<String, Object> objectMap : list) {
                String factoryNumber = GlobalFunc.toString(objectMap.get("factoryNumber"));
                MachineryInfo machineryInfo = new MachineryInfo();
                machineryInfo.setFactoryNumber(factoryNumber);
                //获取集合信息
                Map<String, Object> thisMap = new HashMap<>();
//                Map<String, Object> thisMap = machineryInfoService.getMachineryByList(machineryInfo);
                //根据出厂编号获取sum总数
//                String locationNum = machineryInfoService.getLocationNum(factoryNumber);
                String locationNum = "";
                //获取设备号集合信息
                List<String> split = ArrayUtil.split(factoryNumber, ",");
                Map<String, Object> hashMap = getMachineryOrganiData(split);
                objectMap.put("locationNum",locationNum);
                if(thisMap!=null){
                    for (Map.Entry<String, Object> entry : thisMap.entrySet()) {
                        if(headerMap.containsValue(entry.getKey())){
                            if("realName".equals(entry.getKey())){
                                objectMap.put(entry.getKey(), SecurityUtil.decryptAES(GlobalFunc.toString(entry.getValue())));
                            }else{
                                objectMap.put(entry.getKey(),GlobalFunc.toString(entry.getValue()));
                            }
                        }
                    }
                    int size = 0;
                    String time = "";
                    //循环取第一个
                    for (Map.Entry<String, Object> entry :hashMap.entrySet()){
                        MachineryInfoOrigin organi = (MachineryInfoOrigin) entry.getValue();
                        time = format.format(organi.getCreateTime());
                        size = organi.getState()-1;
                        break;
                    }
                    String  replaced = size>0?"该农机更换了"+size+"次设备信息,最近更新时间为:"+time:"未更换过";
                    objectMap.put("replaced",replaced);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //防止乱码
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(new Gson().toJson(map));
    }

    /**
     * 导出
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/doExcel",method = {RequestMethod.GET,RequestMethod.POST})
    public void doExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sheetName="农机导出";
        String titleName="农机导出";
        //即可以从前台传,也可以数据库获取
        String data = request.getParameter("data");
        List<Map<String,Object>> dataList = JSONObject.parseObject(data, List.class);
        for (Map<String, Object> map : dataList) {
            String tmState = GlobalFunc.toString(map.get("tmState"));
            tmState = "1".equals(tmState)?"未绑定物联网":"3".equals(tmState)?"正常":"2".equals(tmState)?"未绑定用户":"未知";
            map.put("tmState",tmState);
        }
        String[] headers={"机具名称","出厂编号","物联网设备号","作业面积","轨迹点数","购机者","数据源","状态","补贴状态"};
        String[] originals={"modelName","factoryNumber","iotNumber","workArea","locationNum","realName","compCode","tmState","tab"};
        ExcelUtil.exportExcel(sheetName, titleName, headers, dataList, response, originals,null);
    }

    //农机详情页面
    @RequestMapping("/doDetailsMachinery")
    public String doDetailsMachinery(HttpServletRequest request, Model model) {
        String factoryNumber = GlobalFunc.toString(request.getParameter("factoryNumber"));
        String iotNumber = GlobalFunc.toString(request.getParameter("iotNumber"));
        String machineryId = GlobalFunc.toString(request.getParameter("machineryId"));
        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String result = HttpRequest.sendPost("http://172.31.203.179:8082/report/selectVehicleMonthTrack?comDeviceId="+iotNumber+"&time="+time,"");
        List<Map<String, Object>> list = JSON.parseObject(result, new ArrayList<Map<String, Object>>().getClass());
        //根据id查询时间和面积
//        List<Map<String, Object>> maps = machineryInfoService.selectMachineryTime(machineryId);
        List<Map<String, Object>> maps = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            String time1 = GlobalFunc.toString(map.get("time"));
            for (Map<String, Object> stringObjectMap : list) {
                String time2 = GlobalFunc.toString(stringObjectMap.get("time"));
                if(time2.contains(time1)){
                    map.put("track","0");
                    break;
                }
            }
            map.put("factoryNumber",factoryNumber);
            map.put("iotNumber",iotNumber);
        }
        model.addAttribute("list",new Gson().toJson(maps));
        return "common/doDetailsMachinery";
    }


    public  Map<String, Object> getMachineryOrganiData(List<String> machinery){
        //根据id集合查询factory_number,iot_number,create_time
//        List<MachineryInfoOrigin> machineryOrganiData = machineryInfoMapper.getMachineryOrganiData(machinery);
        List<MachineryInfoOrigin> machineryOrganiData = new ArrayList<>();
        //排序且根据iotnumber去重
        machineryOrganiData = machineryOrganiData.stream()
                .sorted((MachineryInfoOrigin mar1, MachineryInfoOrigin mar2) -> mar1.getCreateTime().compareTo(mar2.getCreateTime()))
                .filter(distinctByKey((p) -> (p.getIotNumber())))
                .collect(Collectors.toList());
        //排序
        machineryOrganiData = machineryOrganiData.stream().sorted(Comparator.comparing(MachineryInfoOrigin::getCreateTime).reversed()).collect(Collectors.toList());
        Map<String, Long> collect = machineryOrganiData.stream().collect(Collectors.groupingBy(p -> p.getFactoryNumber(), Collectors.counting()));
//                .collect(Collectors.groupingBy(p -> p.getFactoryNumber(), Collectors.counting()));
        //获取到出厂编号对应的去重数据,循环获取个数放入字段中
        Map<String, Object> hashMap = new HashMap<>();
        for (Map.Entry<String, Long> entry : collect.entrySet()) {
            String newFactoryNumber = entry.getKey();
            for (MachineryInfoOrigin machineryOrganiDatum : machineryOrganiData) {
                if(machineryOrganiDatum.getFactoryNumber().equals(newFactoryNumber)){
                    machineryOrganiDatum.setState(GlobalFunc.parseInt(entry.getValue()));
                    hashMap.put(newFactoryNumber,machineryOrganiDatum);
                    break;
                }
            }
        }
        return hashMap;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
