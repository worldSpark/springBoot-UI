package com.fc.util;

import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @Description list 工具
 * @author luoy
 * @Date 2018年06月07日 上午08:57:00
 * @version 1.0.0
 **/
public class ArrayUtil {

    /**
     * @Description 将数组转换成字符串
     * 格式:begin+prefix+str0+suffix+split+prefix+str1+suffix...+end
     * 为空会跳过
     * @param str 被转换的数组
     * @param split 分隔符
     * @param prefix 前缀
     * @param suffix 后缀
     * @param begin 开始
     * @param end 结束
     * @return string
     */
    public static String toString(List<String> str,String split, String prefix, String suffix, String begin, String end){
        StringBuffer string = new StringBuffer();
        string.append(begin);
        for(int i = 0;i < str.size();i++){
            String s = str.get(i);
            if(StringUtils.isBlank(s)){
                continue;
            }
            string.append(prefix);
            string.append(s);
            string.append(suffix);
            if(i < (str.size() - 1)){
                string.append(split);
            }
        }
        string.append(end);
        return string.toString();
    }

    public static String toString(List<String> str, String split, String prefix, String suffix){
        return toString(str,split,prefix,suffix,"","");
    }

    public static String toString(List<String> str, String split){
        return toString(str,split,"","","","");
    }

    public static String toString(String[] str,String split, String prefix, String suffix, String begin, String end){
        List<String> list = Collections.EMPTY_LIST;
        if(str != null && str.length > 0){
            list = Arrays.asList(str);
        }
        return toString(list,split,prefix,suffix,begin,end);
    }

    public static String toString(String[] str, String split, String prefix, String suffix){
        List<String> list = Collections.EMPTY_LIST;
        if(str != null && str.length > 0){
            list = Arrays.asList(str);
        }
        return toString(list,split,prefix,suffix);
    }

    public static String toString(String[] str, String split){
        List<String> list = Collections.EMPTY_LIST;
        if(str != null && str.length > 0){
            list = Arrays.asList(str);
        }
        return toString(list,split);
    }

    /**
     * @Description 去除重复
     * @param list 集合
     * @return List
     */
    public static <T> List<T> removeRepeat(List<T> list){
        List<T> result = new ArrayList<>();
        for(T data : list){
            if(!result.contains(data)){
                result.add(data);
            }
        }
        return result;
    }

    /**
     * @Description 分隔
     * @param str str
     * @param splits 分隔符，优先判断0-n，只使用一次分隔符
     * @return list
     */
    public static List<String> split(String str, String... splits){
        List<String> list = new ArrayList<>();

        if (StringUtils.isBlank(str)) {
            return list;
        } else if (splits == null || splits.length == 0) {
            // 没有分隔符，则直接放入
            list.add(str);
        }else{
            // 有分隔符
            boolean isExist = false;
            for (int i = 0; i < splits.length; i++) {
                String c = splits[i];
                if(str.indexOf(c.replace("\\","")) > -1){
                    // 存在此字符，开始分隔，结束循环
                    isExist = true;
                    list = new ArrayList<>(Arrays.asList(str.split(c)));
                    break;
                }
            }
            if(!isExist){
                // 如果没有匹配成功，则直接放入
                list.add(str);
            }
        }

        return list;
    }

    /**
     * @Description 将字符串作为list拼接
     * @param str 原始值
     * @param split 分隔符
     * @param value 被拼接的值
     * @return str
     */
    public static String addAsList(String str, String split, String value){
        List<String> list = ArrayUtil.split(str, split);
        list.add(value);
        String result = ArrayUtil.toString(list, split);
        return result;
    }

    /**
     * @Description 将字符串作为list移除
     * @param str 原始值
     * @param split 分隔符
     * @param value 被拼接的值
     * @return str
     */
    public static String removeAsList(String str, String split, String value){
        List<String> list = ArrayUtil.split(str, split);
        while (list.contains(value)){
            list.remove(value);
        }
        String result = ArrayUtil.toString(list, split);
        return result;
    }

    /**
     * @Description 对string数组进行排序
     * 空字符串和null默认在末尾
     * @param list list
     * @param isAsc 是否正序
     * @return list
     */
    public static List<String> orderBy(List<String> list, Boolean isAsc){
        List<String> orderList = new ArrayList<>();

        for (String str : list) {
            if(StringUtils.isBlank(str) || orderList.size() == 0){
                orderList.add(str);
                continue;
            }
            // 比较
            for (int i = 0; i < orderList.size(); i++) {
                String orderString = orderList.get(i);
                if(StringUtils.isBlank(orderString)){
                    orderList.add(i, str);
                    break;
                }else{
                    // 正序，先比长度再比内容
                    if(isAsc && (str.length() < orderString.length() || str.compareTo(orderString) <= 0)){
                        orderList.add(i, str);
                        break;
                    }
                    // 倒序，先比长度再比内容
                    if(!isAsc && (str.length() > orderString.length() || str.compareTo(orderString) >= 0)){
                        orderList.add(i, str);
                        break;
                    }
                    if(i == (orderList.size() - 1)){
                        orderList.add(str);
                        break;
                    }
                }
            }
        }

        return orderList;
    }

    /**
     * 判断List集合是否为空
     * @param list
     * @return
     */
    public static boolean isNotEmpty(List list){
        return list != null && list.size() > 0;
    }


    /**
     * List<Map>根据map中的某个key 去除List中重复的map
     * @author  qiangs
     * @param list list<Map>
     * @param mapKey map中去重的键
     * @return
     */
    public static List<Map> removeRepeatMapByKey(List<Map> list, String mapKey){

        if (null == list || list.isEmpty()){
            return null;
        }
        //把list中的数据转换成msp,去掉同一id值多余数据，保留查找到第一个id值对应的数据
        List<Map> listMap = new ArrayList<>();
        Map<String, Map> msp = new HashMap<>();
        for(int i = list.size()-1 ; i>=0; i--){
            Map map = list.get(i);
            String id = (String)map.get(mapKey);
            map.remove(mapKey);
            msp.put(id, map);
        }
        //把msp再转换成list,就会得到根据某一字段去掉重复的数据的List<Map>
        Set<String> mspKey = msp.keySet();
        for(String key: mspKey){
            Map newMap = msp.get(key);
            newMap.put(mapKey, key);
            listMap.add(newMap);
        }
        return listMap;
    }
}
