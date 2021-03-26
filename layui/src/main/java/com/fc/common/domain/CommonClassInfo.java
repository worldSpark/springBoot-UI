package com.fc.common.domain;


import lombok.Data;

import java.util.List;

/**
 * @Description class转换成适合sql处理的对象
 * @author luoy
 * @Date 2018年12月15日 下午13:52:00
 * @version 1.0.0
 **/
@Data
public class CommonClassInfo {

    // 注解上表名
    private String tableName;

    // 类中字段
    private List<CommonFieldInfo> commonFieldInfoList;

    /**
     * @Description 获得主键信息
     * 注意：
     * 1、推荐在主键的@Column中使用isKey=true
     * 2、如果没有找到主键，则默认搜索id字段
     * @return CommonFieldInfo
     */
    public CommonFieldInfo getKeyFieldInfo(){
        CommonFieldInfo keyFieldInfo = null;
        for(CommonFieldInfo info : this.commonFieldInfoList){
            if(info.isKey()){
                keyFieldInfo = info;
                break;
            }
        }
        // 如果没有找到，则搜索id字段
        if(keyFieldInfo == null){
            for(CommonFieldInfo info : this.commonFieldInfoList){
                if("id".equals(info.getFieldName())){
                    keyFieldInfo = info;
                    break;
                }
            }
        }
        return keyFieldInfo;
    }
}
