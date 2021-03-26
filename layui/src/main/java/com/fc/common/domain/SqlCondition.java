package com.fc.common.domain;


import com.fc.util.GlobalFunc;
import com.fc.util.SqlUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description sql查询条件集合
 * 说明：
 *   1、传递variableName（变量名），可以是下划线，也可以是驼峰式
 *   2、增加了isNotBlank 2019年8月12日14:32:44
 *   2、增加了andEqualToOrIsNullOrEmpty 2019年9月4日15:25:53
 * @author luoy
 * @Date 2018年08月06日 下午14:05:00
 * @version 1.0.0
 **/
public class SqlCondition {
    public static final String FUNC_LENGTH_OF_WORD = "LEN";

    private static final String SQL_EQUAL                       = "@name = @value";
    private static final String SQL_NOT_EQUAL                   = "@name != @value";
    private static final String SQL_EQUAL_OR_BIGGER_THAN        = "@name >= @value";
    private static final String SQL_BIGGER_THAN                 = "@name > @value";
    private static final String SQL_EQUAL_OR_LESS_THAN          = "@name <= @value";
    private static final String SQL_LESS_THAN                   = "@name < @value";
    private static final String SQL_LIKE                        = "@name like @value";
    private static final String SQL_IN                          = "@name in @value";
    private static final String SQL_NOT_IN                      = "@name not in @value";
    private static final String SQL_IS_NULL                     = "@name is null";
    private static final String SQL_IS_EMPTY                    = "@name = ''";
    private static final String SQL_IS_NULL_OR_EMPTY            = "(@name is null or @name = '')";
    private static final String SQL_IS_NOT_BLANK                = "(@name is not null and @name != '')";
    private static final String SQL_EQUAL_OR_IS_NULL_OR_EMPTY   = "(@name = @value or @name is null or @name = '')";
    private static final String SQL_AND_SQL                     = "append_and_sql";
    private static final String SQL_OR_SQL                      = "append_or_sql";
    private static final String SQL_ORDER                       = "@name @type";
    private static final String SQL_ORDER_STRING                = FUNC_LENGTH_OF_WORD +
            "(@name) @type, @name @type";

    public SqlCondition(){
        super();
        this.sqlAndCriteriaList = new ArrayList<>();
        this.sqlOrderCriteriaList = new ArrayList<>();
    }

    // sql and判断条件集合
    private List<SqlCriteria> sqlAndCriteriaList;

    // sql order排序集合
    private List<SqlCriteria> sqlOrderCriteriaList;

    // ---------------------------- 判断条件↓ ----------------------------

    // 等于
    public void andEqualTo(String variableName,Object value){
        this.sqlAndCriteriaList.add(new SqlCriteria(SqlCondition.SQL_EQUAL,variableName,value));
    }

    // 等于或为空，等同于：and (字段 = 'xxx' or 字段 is null or 字段 = '')
    public void andEqualToOrIsNullOrEmpty(String variableName,Object value){
        this.sqlAndCriteriaList.add(new SqlCriteria(SqlCondition.SQL_EQUAL_OR_IS_NULL_OR_EMPTY,variableName,value));
    }

    // 不等于
    public void andNotEqualTo(String variableName,Object value){
        this.sqlAndCriteriaList.add(new SqlCriteria(SqlCondition.SQL_NOT_EQUAL,variableName,value));
    }

    // 大于等于
    public void andEqualOrBiggerThan(String variableName, Object value){
        this.sqlAndCriteriaList.add(new SqlCriteria(SqlCondition.SQL_EQUAL_OR_BIGGER_THAN,variableName,value));
    }

    // 大于
    public void andBiggerThan(String variableName, Object value){
        this.sqlAndCriteriaList.add(new SqlCriteria(SqlCondition.SQL_BIGGER_THAN,variableName,value));
    }

    // 小于等于
    public void andEqualOrLessThan(String variableName,Object value){
        this.sqlAndCriteriaList.add(new SqlCriteria(SqlCondition.SQL_EQUAL_OR_LESS_THAN,variableName,value));
    }

    // 小于
    public void andLessThan(String variableName,Object value){
        this.sqlAndCriteriaList.add(new SqlCriteria(SqlCondition.SQL_LESS_THAN,variableName,value));
    }

    // like模糊查询 value值需要自行增加%%
    public void andLikeTo(String variableName,Object value){
        this.sqlAndCriteriaList.add(new SqlCriteria(SqlCondition.SQL_LIKE,variableName,value));
    }

    // in查询
    public void andIn(String variableName,List valueList){
        this.sqlAndCriteriaList.add(new SqlCriteria(SqlCondition.SQL_IN,variableName,valueList));
    }
    public void andIn(String variableName,String[] valueList){
        List<String> list = Arrays.asList(valueList);
        this.andIn(variableName,list);
    }

    // not in查询
    public void andNotIn(String variableName,List valueList){
        this.sqlAndCriteriaList.add(new SqlCriteria(SqlCondition.SQL_NOT_IN,variableName,valueList));
    }

    // null查询
    public void andIsNull(String variableName){
        this.sqlAndCriteriaList.add(new SqlCriteria(SqlCondition.SQL_IS_NULL,variableName,null));
    }

    // 空字符串查询
    public void andIsEmpty(String variableName){
        this.sqlAndCriteriaList.add(new SqlCriteria(SqlCondition.SQL_IS_EMPTY,variableName,null));
    }

    // null或空字符串
    public void andIsNullOrEmpty(String variableName){
        this.sqlAndCriteriaList.add(new SqlCriteria(SqlCondition.SQL_IS_NULL_OR_EMPTY,variableName,null));
    }

    // 不为空
    public void andIsNotBlank(String variableName){
        this.sqlAndCriteriaList.add(new SqlCriteria(SqlCondition.SQL_IS_NOT_BLANK,variableName,null));
    }

    // 拼接sql 不需要写and
    public void andSql(String sql){
        this.sqlAndCriteriaList.add(new SqlCriteria(SqlCondition.SQL_AND_SQL,sql,null));
    }

    // 拼接sql 不需要写or
    public void orSql(String sql){
        this.sqlAndCriteriaList.add(new SqlCriteria(SqlCondition.SQL_OR_SQL,sql,null));
    }

    // ---------------------------- 判断条件↑ ----------------------------
    // ---------------------------- 排序↓ ----------------------------

    // order by 顺序查询
    public void orderByASC(String variableName){
        this.sqlOrderCriteriaList.add(new SqlCriteria(SqlCondition.SQL_ORDER,variableName,"asc"));
    }

    // order by 倒序查询
    public void orderByDESC(String variableName){
        this.sqlOrderCriteriaList.add(new SqlCriteria(SqlCondition.SQL_ORDER,variableName,"desc"));
    }

    // order by 字符串顺序查询
    public void orderStringByASC(String variableName){
        this.sqlOrderCriteriaList.add(new SqlCriteria(SqlCondition.SQL_ORDER_STRING,variableName,"asc"));
    }

    // order by 字符串倒序查询
    public void orderStringByDESC(String variableName){
        this.sqlOrderCriteriaList.add(new SqlCriteria(SqlCondition.SQL_ORDER_STRING,variableName,"desc"));
    }

    // order by sql拼接 不需要加order by
    public void appendOrderSql(String variableName){
        this.sqlOrderCriteriaList.add(new SqlCriteria(SqlCondition.SQL_ORDER,variableName,""));
    }

    // ---------------------------- 排序↑ ----------------------------

    public List<SqlCriteria> getCriteriaList(){
        return this.sqlAndCriteriaList;
    }

    /**
     * @Description 将查询条件组装成sql语句
     * @return sql:and ... and ...
     */
    public String getSql(){
        StringBuffer sql = new StringBuffer();

        // sql and条件拼接
        if(this.sqlAndCriteriaList != null){
            for(int i = 0;i < this.sqlAndCriteriaList.size(); i++){
                SqlCriteria sqlCriteria = this.sqlAndCriteriaList.get(i);
                // 匹配方式
                String matchingMethod = sqlCriteria.getMatchingMethod();
                // 变量名/字段名/sql
                String variableName = sqlCriteria.getVariableName();
                // 值
                Object value = sqlCriteria.getValue();

                if (SqlCondition.SQL_OR_SQL.equals(matchingMethod)){
                    sql.append(" or " + variableName + " ");
                }
                else if(SqlCondition.SQL_AND_SQL.equals(matchingMethod)){
                    // sql拼接
                    sql.append(" and " + variableName + " ");
                }else{
                    // 其它
                    // 字段名，进行转换，大写 → _小写
                    String columnName = SqlUtil.convertToLowercase(variableName,"_","");;
                    // 字段值
                    StringBuffer columnValue = new StringBuffer();
                    if(value == null){
                        columnValue.append("null");
                    }else if(value instanceof List){
                        List<Object> valueList = (List<Object>) value;

                        columnValue.append("(");
                        if(valueList.isEmpty()){
                            columnValue.append("''");
                        }
                        for (int j = 0;j < valueList.size();j++){
                            Object obj = valueList.get(j);
                            String val = SqlUtil.convertToString(obj);
                            columnValue.append(val);
                            if (j < (valueList.size() - 1)){
                                columnValue.append(",");
                            }
                        }
                        columnValue.append(")");
                    }else{
                        String val = SqlUtil.convertToString(value);
                        columnValue.append(val);
                    }

                    // 替换@name、@value
                    String resultSql = matchingMethod;
                    resultSql = resultSql.replaceAll("@name", columnName);
                    resultSql = resultSql.replaceAll("@value", columnValue.toString());
                    sql.append(" and " + resultSql + " ");
                }
            }
        }

        // sql order条件拼接
        if(this.sqlOrderCriteriaList != null){
            for(int i = 0;i < this.sqlOrderCriteriaList.size(); i++){
                SqlCriteria sqlCriteria = this.sqlOrderCriteriaList.get(i);
                String variableName = sqlCriteria.getVariableName();
                String matchingMethod = sqlCriteria.getMatchingMethod();
                // 排序方式，正序、倒序
                String type = GlobalFunc.toString(sqlCriteria.getValue());
                // 字段名
                String columnName = SqlUtil.convertToLowercase(variableName,"_","");

                if(i == 0){
                    sql.append(" order by");
                }
                String orderSql = matchingMethod;
                orderSql = orderSql.replaceAll("@name", columnName);
                orderSql = orderSql.replaceAll("@type", type);
                sql.append(" " + orderSql);
                if(i < (this.sqlOrderCriteriaList.size() - 1)){
                    sql.append(",");
                }
            }
        }
        return sql.toString();
    }

    /**
     * @Description 判断条件是否为空，不包括排序
     * @return boolean
     */
    public boolean isAndCriteriaListEmpty(){
        boolean result = false;
        if(this.sqlAndCriteriaList == null || this.sqlAndCriteriaList.isEmpty()){
            result = true;
        }
        return result;
    }

    /**
     * @Description 判断条件是否为空，不包括排序
     * @return boolean
     */
    public boolean isOrderCriteriaListEmpty(){
        boolean result = false;
        if(this.sqlOrderCriteriaList == null || this.sqlOrderCriteriaList.isEmpty()){
            result = true;
        }
        return result;
    }

    /**
     * sql查询中wher条件中字符串有单引号将其转换成双引号的处理
     * @param compareCharacter
     * @return
     */
    public static String stringToQueryTransformation(String compareCharacter){
        if(compareCharacter.indexOf("'")>-1){
            compareCharacter=compareCharacter.replaceAll("'","''");
        }
        return compareCharacter;
    }
}
