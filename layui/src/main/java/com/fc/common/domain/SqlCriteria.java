package com.fc.common.domain;

/**
 * @Description sql判断条件
 * @author luoy
 * @Date 2018年08月06日 下午14:27:00
 * @version 1.0.0
 **/
public class SqlCriteria {

    public SqlCriteria(String matchingMethod, String variableName, Object value) {
        this.variableName = variableName;
        this.matchingMethod = matchingMethod;
        this.value = value;
    }

    private String matchingMethod; //匹配方式
    private String variableName; //变量名
    private Object value; //值

    public String getMatchingMethod() {
        return this.matchingMethod;
    }

    public String getVariableName() {
        return this.variableName;
    }

    public Object getValue() {
        return this.value;
    }

}
