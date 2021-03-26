package com.fc.util;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description sql工具
 * 1、equalOrIn()方法增加了转义功能               --2019年9月20日11:53:03
 * @author luoy
 * @Date 2018/4/24 9:47
 * @version 1.0.0
 */
public class SqlUtil {

    //方法列表：
    //1、equalOrIn() 将数组转换成等于或in(数组)
    //2、convertToLowercase() 将大写转小写 并加下划线 aBC → a前缀b后缀前缀c后缀
    //3、convertToString() 将对象转字符串，数字不带单引号，其它带单引号
    //4、transferValue() 转义特殊符号，方便sql进行处理
    //5、getOrderSqlOfSpecial() 用于特殊排序，前部分是非数字，后部分是数字

    /**
     * 将数组转换成等于或in(数组)
     * @param str 数组
     * @return ='值' 或 in('1','2','3')
     */
    public static String equalOrIn(String[] str) {
        if (str == null) {
            return " =''";
        }
        if (str.length == 0) {
            return " =''";
        }
        if (str.length == 1) {
            String value = str[0];
            value = SqlUtil.transferValue(value);
            return " = '" + value + "'";
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append(" in (");
            for (int i = 0; i < str.length; i++) {
                String value = str[i];
                value = SqlUtil.transferValue(value);
                sb.append("'" + value + "'");
                if (i < str.length - 1) {
                    sb.append(",");
                }
            }
            sb.append(")");
            return sb.toString();
        }
    }

    /**
     * 将数组转换成等于或in(数组)
     * @param list 数组
     * @return ='值' 或 in('1','2','3')
     */
    public static String equalOrIn(List<String> list){
        String[] str;
        if(list != null){
            str = list.toArray(new String[list.size()]);
        }else {
            str = new String[0];
        }
        return equalOrIn(str);
    }

    public static String[] split(String str,String c){
        if (StringUtils.isBlank(str)){
            return null;
        }
        str = str.trim();
        if(str.contains(c)){
            return str.split(c);
        }else{
            return new String[]{str};
        }
    }

    /**
     * @Description 将大写转小写 并加下划线 aBC → a前缀b后缀前缀c后缀
     * @param original 原始
     * @param prefix 前缀
     * @param suffix 后缀
     * @param start 起始位置 >= 0
     * @param end 结束位置 <=length
     * @return result
     */
    public static String convertToLowercase(String original,String prefix,String suffix,int start,int end){
        StringBuffer str = new StringBuffer();
        String regexA = "^[A-Z]{1,}$";
        for(int i = 0;i < original.length();i++){
            String s = original.substring(i,i+1);
            if(i >= start && i < end && s.matches(regexA)){
                str.append(prefix + s.toLowerCase() + suffix);
            }else{
                str.append(s);
            }
        }
        return str.toString();
    }

    public static String convertToLowercase(String original,String prefix,String suffix,int start){
        return convertToLowercase(original,prefix,suffix,start,original.length());
    }

    public static String convertToLowercase(String original,String prefix,String suffix){
        return convertToLowercase(original,prefix,suffix,0,original.length());
    }

    public static String convertToLowercase(String original){
        return convertToLowercase(original,"","",0,original.length());
    }

    /**
     * @Description 将下划线+小写字母转大写,第一个字母默认小写
     * @param original 原始
     * @return result
     */
    public static String convertToUppercase(String original){
        if(StringUtils.isBlank(original)){
            return original;
        }
        String result = original;
        String regexA = "^[A-Z]{1,}$";
        while (result.contains("_")){
            String target = result.substring(result.indexOf("_") + 1,result.indexOf("_") + 2);
            result = result.replaceAll("_" + target,target.toUpperCase());
        }
        String start = original.substring(0,1);
        if(start.matches(regexA)){
            result = start.toLowerCase() + result.substring(1);
        }
        return result;
    }

    /**
     * @Description 将对象转字符串，数字不带单引号，其它带单引号
     * @param obj 值
     * @return String
     */
    public static String convertToString(Object obj){
        StringBuffer str = new StringBuffer();
        if(obj == null){
            str.append("null");
        }else if(obj instanceof Integer){
            Integer val = (Integer) obj;
            str.append(val);
        }else if (obj instanceof Double){
            Double val = (Double) obj;
            str.append(val);
        }else if (obj instanceof Float){
            Float val = (Float) obj;
            str.append(val);
        }else if (obj instanceof BigDecimal){
            BigDecimal val = (BigDecimal) obj;
            str.append(val);
        }else{
            String s = GlobalFunc.toString(obj);
            //sql转义
            s = SqlUtil.transferValue(s);
            str.append("'" + s + "'");
        }
        return str.toString();
    }

    /**
     * @Description 转义特殊符号，方便sql进行处理
     * @param value 字符串
     * @return 结果
     */
    public static String transferValue(String value){
        String result = value;
        //sql转义 将单个' 转成 两个'
        if(result.contains("'")){
            result = result.replaceAll("'","''");
        }
        //如果有反斜杠
        if(result.contains("\\")){
            result = result.replaceAll("\\\\","\\\\\\\\");
        }
        return result;
    }

    /**
     * @Description 用于特殊排序，前部分是非数字，后部分是数字 - liuj
     * @param column 字段名称
     * @return orderSql 不带order
     */
    /* mysql版本，需要转sqlserver
    public static String getOrderSqlOfSpecial(String column){
        // 反转一次，取前面数字部分，再反转成正常数字，再截取获得完整数字，所得的就是数字部分，
        String temp = "substring(" + column + ", instr(" + column + ", reverse(-(-reverse(" + column + ")))))";
        // replace就是前面的非数字部分
        String sql = " replace(" + column + ", " + temp + ",''),";
        // 后面数字部分
        sql += " CONVERT(" + temp + ", SIGNED)";
        return sql;
    }
    */
}
