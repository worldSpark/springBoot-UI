package com.fc.common.domain;

import lombok.Data;

/**
 * @Description class信息中的字段
 * @author luoy
 * @Date 2019年01月03日 上午09:32:00
 * @version 1.0.0
 **/
@Data
public class CommonFieldInfo {

    // 是否为主键
    private boolean isKey;

    // 主键是否为自增长
    private boolean isAutoIncrease;

    // 类中字段名
    private String fieldName;

    // 表中字段名
    private String columnName;

    // 类中字段属性
    private Class<?> fieldClass;

    // 表中字段类型 全大写
    private String columnTypeName;

    // 类中get方法名称
    private String getMethodName;

    // 类中set方法名称
    private String setMethodName;

    // 类中字段注解名，中文
    private String annotateName;

    public boolean isKey() {
        return this.isKey;
    }

    public void setIsKey(boolean key) {
        this.isKey = key;
    }

    public boolean isAutoIncrease() {
        return this.isAutoIncrease;
    }

    public void setIsAutoIncrease(boolean autoIncrease) {
        this.isAutoIncrease = autoIncrease;
    }
}
