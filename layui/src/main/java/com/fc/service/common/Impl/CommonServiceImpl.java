package com.fc.service.common.Impl;

import com.fc.annotation.Column;
import com.fc.annotation.FieldName;
import com.fc.annotation.Table;
import com.fc.common.conf.CommonConfig;
import com.fc.common.domain.CommonClassInfo;
import com.fc.common.domain.CommonFieldInfo;
import com.fc.common.domain.SqlCondition;
import com.fc.config.ApplicationConfig;
import com.fc.mapper.SqlMapper;
import com.fc.service.common.CommonService;
import com.fc.util.ArrayUtil;
import com.fc.util.GlobalFunc;
import com.fc.util.Pinyin4JHelper;
import com.fc.util.SqlUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Description 通用service实现层
 * @author luoy
 * @Date 2018年07月26日 下午13:29:00
 * @version 1.0.0
 **/
@Service
@Transactional
public class CommonServiceImpl implements CommonService {

    public static final Logger log = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Autowired
    private SqlMapper sqlMapper;

    @Override
    public int insert(Object object) {
        int count = 0;
        StringBuffer sql = new StringBuffer();

        Class objectClass = object.getClass();
        // 类信息↓
        CommonClassInfo classInfo = this.getCommonClassInfo(objectClass);
        // 表名↓
        String tableName = classInfo.getTableName();
        // 字段拼接↓
        String columnSql = this.getColumnSql(classInfo);
        // 值拼接↓
        String valueSql = this.getValuesSql(classInfo,object);

        // 拼接↓
        sql.append("insert into " + tableName + " (" + columnSql + ")values");
        sql.append("(" + valueSql + ")");
        // 拼接↑

        count = this.sqlMapper.insert(sql.toString());

        return count;
    }

    @Override
    public <T> int insertList(List<T> list) {
        int count = this.insertList(list,list.size());
        return count;
    }

    @Override
    public <T> int insertList(List<T> list, Integer size) {
        int count = 0;
        // 空集合直接结束
        if(list == null || list.isEmpty()){
            return count;
        }
        // 为空或未正确设置每次插入多少条，则设置默认值：100
        if(size == null || size <= 0){
            size = 100;
        }

        Class objectClass = list.get(0).getClass();
        // 类信息↓
        CommonClassInfo classInfo = this.getCommonClassInfo(objectClass);
        // 字段拼接↓
        String columnSql = this.getColumnSql(classInfo);
        // 表名↓
        String tableName = classInfo.getTableName();

        // 计数归零↓
        count = 0;
        String insertSql = "insert into " + tableName + " (" + columnSql + ")values";
        StringBuffer valueSql = new StringBuffer();
        for(int i = 0;i < list.size();i++){
            Object object = list.get(i);
            // 值拼接↓
            String currentValueSql = this.getValuesSql(classInfo,object);
            // 拼接↓
            valueSql.append("(" + currentValueSql + "),");
            // 拼接↑

            // 能被size整除或数组走到尽头时，执行一次sql
            if((i > 0 && ((i + 1) % size) == 0) || (i == (list.size() - 1))){
                // 去除末尾逗号↓
                valueSql = this.cutEndComma(valueSql);
                // 执行sql↓
                count += this.sqlMapper.insert(insertSql + valueSql);
                // 清空↓
                valueSql = new StringBuffer();
            }
        }

        return count;
    }

    @Override
    public int delete(Object object) {
        int count = 0;
        StringBuffer sql = new StringBuffer();

        // 表名↓
        Class objectClass = object.getClass();
        // 类信息↓
        CommonClassInfo classInfo = this.getCommonClassInfo(objectClass);
        String tableName = classInfo.getTableName();
        // 主键字段信息↓
        CommonFieldInfo keyFieldInfo = classInfo.getKeyFieldInfo();
        // id↓
        String id = GlobalFunc.toString(this.getValueByObjectAndMethodName(object,keyFieldInfo.getGetMethodName()));
        // 拼接↓
        sql.append("delete from " + tableName + " where " + keyFieldInfo.getColumnName() + " = '" + id + "'");
        // 拼接↑
        if(StringUtils.isNotBlank(id)){
            count = this.sqlMapper.delete(sql.toString());
        }

        return count;
    }

    @Override
    public int delete(Class objectClass,String id) {
        int count = 0;
        StringBuffer sql = new StringBuffer();
        // 类信息↓
        CommonClassInfo classInfo = this.getCommonClassInfo(objectClass);
        // 表名↓
        String tableName = classInfo.getTableName();
        // 主键信息↓
        CommonFieldInfo keyFieldInfo = classInfo.getKeyFieldInfo();
        // 拼接↓
        sql.append("delete from " + tableName + " where " + keyFieldInfo.getColumnName() + " = '" + id + "'");
        // 拼接↑
        if(StringUtils.isNotBlank(id)){
            count = this.sqlMapper.delete(sql.toString());
        }

        return count;
    }

    @Override
    public int delete(Class objectClass,List<String> ids){
        int count = 0;
        StringBuffer sql = new StringBuffer();
        // 类信息↓
        CommonClassInfo classInfo = this.getCommonClassInfo(objectClass);
        // 表名↓
        String tableName = classInfo.getTableName();
        // 拼接↓
        sql.append("delete from " + tableName + " where id" + SqlUtil.equalOrIn(ids));
        // 拼接↑
        if(!ids.isEmpty()){
            count = this.sqlMapper.delete(sql.toString());
        }

        return count;
    }

    @Override
    public int delete(Class objectClass,String[] ids){
        List<String> idList = Arrays.asList(ids);
        int count = this.delete(objectClass,idList);
        return count;
    }

    @Override
    public int delete(Class objectClass, SqlCondition sqlCondition) {
        int count = 0;
        StringBuffer sql = new StringBuffer();
        // 类信息↓
        CommonClassInfo classInfo = this.getCommonClassInfo(objectClass);
        // 表名↓
        String tableName = classInfo.getTableName();
        // 拼接↓
        sql.append("delete from " + tableName + " where 1=1");
        // 条件↓
        String conditionSql = sqlCondition.getSql();
        sql.append(conditionSql);
        // 条件↑

        // 如果条件为空 则不进行删除！
        if (StringUtils.isBlank(conditionSql)){
            return count;
        }

        // 执行删除↓
        count = this.sqlMapper.delete(sql.toString());
        // 执行删除↑
        return count;
    }

    @Override
    public int update(Object object) {
        int count = 0;
        StringBuffer sql = new StringBuffer();
        Class objectClass = object.getClass();
        // 类信息↓
        CommonClassInfo classInfo = this.getCommonClassInfo(objectClass);
        // 表名↓
        String tableName = classInfo.getTableName();
        // 主键信息↓
        CommonFieldInfo keyFieldInfo = classInfo.getKeyFieldInfo();
        // id↓
        String id = GlobalFunc.toString(getValueByObjectAndMethodName(object,keyFieldInfo.getGetMethodName()));
        // setSql语句↓
        String setSql = this.getSetSql(classInfo,object,false);
        // 拼接↓
        sql.append("update " + tableName + " set " + setSql);
        sql.append(" where " + keyFieldInfo.getColumnName() + " = '" + id + "'");
        // 拼接↑
        count = this.sqlMapper.update(sql.toString());

        return count;
    }

    @Override
    public int update(Object object, SqlCondition sqlCondition) {
        int count = 0;
        StringBuffer sql = new StringBuffer();
        Class objectClass = object.getClass();
        // 类信息↓
        CommonClassInfo classInfo = this.getCommonClassInfo(objectClass);
        // 表名↓
        String tableName = classInfo.getTableName();
        // setSql语句↓
        String setSql = this.getSetSql(classInfo,object,false);
        // where条件语句↓
        String conditionSql = sqlCondition.getSql();

        // 如果条件为空 则不进行更新！
        if (StringUtils.isBlank(conditionSql)){
            return count;
        }

        // 拼接↓
        sql.append("update " + tableName + " set " + setSql);
        sql.append(" where 1=1" + conditionSql);
        // 拼接↑
        count = this.sqlMapper.update(sql.toString());

        return count;
    }

    @Override
    public int updateSelective(Object object) {
        int count = 0;
        StringBuffer sql = new StringBuffer();
        Class objectClass = object.getClass();
        // 类信息↓
        CommonClassInfo classInfo = this.getCommonClassInfo(objectClass);
        // 表名↓
        String tableName = classInfo.getTableName();
        // 主键信息↓
        CommonFieldInfo keyFieldInfo = classInfo.getKeyFieldInfo();
        // id↓
        String id = GlobalFunc.toString(getValueByObjectAndMethodName(object,keyFieldInfo.getGetMethodName()));
        // setSql语句↓
        String setSql = this.getSetSql(classInfo,object,true);
        // 拼接↓
        sql.append("update " + tableName + " set " + setSql);
        sql.append(" where " + keyFieldInfo.getColumnName() + " = '" + id + "'");
        // 拼接↑
        count = this.sqlMapper.update(sql.toString());

        return count;
    }

    @Override
    public int updateSelective(Object object, SqlCondition sqlCondition) {
        int count = 0;
        StringBuffer sql = new StringBuffer();
        Class objectClass = object.getClass();
        // 类信息↓
        CommonClassInfo classInfo = this.getCommonClassInfo(objectClass);
        // 表名↓
        String tableName = classInfo.getTableName();
        // setSql语句↓
        String setSql = this.getSetSql(classInfo,object,true);
        // where条件语句↓
        String conditionSql = sqlCondition.getSql();

        // 如果条件为空 则不进行更新！
        if (StringUtils.isBlank(conditionSql)){
            return count;
        }

        // 拼接↓
        sql.append("update " + tableName + " set " + setSql);
        sql.append(" where 1=1" + conditionSql);
        // 拼接↑
        count = this.sqlMapper.update(sql.toString());

        return count;
    }

    @Override
    public <T> int updateList(List<T> list) {
        if(list == null || list.isEmpty()){
            return 0;
        }
        return this.updateList(list, list.size());
    }

    @Override
    public <T> int updateList(List<T> list, int size) {
        // 更新顺序，从前往后
        int totalCount = 0;
        if(list == null || list.size() == 0){
            return totalCount;
        }
        // 更新位置
        int startPosition = 0;
        Class objectClass = list.get(0).getClass();
        // 类信息↓
        CommonClassInfo classInfo = this.getCommonClassInfo(objectClass);
        // 表名↓
        String tableName = classInfo.getTableName();
        // 主键信息↓
        CommonFieldInfo keyFieldInfo = classInfo.getKeyFieldInfo();
        // 主键get方法名↓
        String keyMethodName = keyFieldInfo.getGetMethodName();

        List<T> tempList;
        StringBuffer sql;
        do {
            // 装入需要用到的临时数据 ↓
            int endPosition = startPosition + size;
            if(endPosition > list.size()){
                endPosition = list.size();
            }
            tempList = list.subList(startPosition, endPosition);
            if(tempList.isEmpty()){
                break;
            }
            startPosition = endPosition;
            // 装入需要用到的临时数据 ↑

            // 主键信息↓
            List<String> primaryKeyList = new ArrayList<>();
            // 主键信息↑

            // 组装↓
            sql = new StringBuffer();
            sql.append(" update " + tableName + " set ");
            // 遍历字段
            List<CommonFieldInfo> fieldInfoList = classInfo.getCommonFieldInfoList();
            for(int i = 0;i < fieldInfoList.size();i++){
                // 字段信息
                CommonFieldInfo fieldInfo = fieldInfoList.get(i);
                // 字段名
                String columnName = fieldInfo.getColumnName();
                // 方法名
                String getMethodName = fieldInfo.getGetMethodName();
                if(fieldInfo.isKey() || fieldInfo.equals(keyFieldInfo)){
                    // 跳过主键
                    continue;
                }
                if(StringUtils.isBlank(columnName)){
                    // 没有字段注解，则跳过
                    continue;
                }
                sql.append(columnName + " = CASE " + keyFieldInfo.getColumnName());
                // 遍历数据
                for(int j = 0;j < tempList.size(); j++){
                    T obj = tempList.get(j);
                    // 通过get方法获得值,当前主键
                    String currentId = GlobalFunc.toString(this.getValueByObjectAndMethodName(obj, keyMethodName));
                    if (!primaryKeyList.contains(currentId)) {
                        primaryKeyList.add(currentId);
                    }
                    Object value = this.getValueByObjectAndMethodName(obj, getMethodName);
                    // 转义
                    String valueStr = this.valueTransference(value);
                    // sqlServer不支持设置为null
                    if("null".equals(valueStr)){
                        valueStr = "''";
                    }
                    sql.append(" WHEN '" + currentId + "' THEN " + valueStr);
                }
                sql.append(" END,");
            }
            sql = this.cutEndComma(sql);
            sql.append(" where " + keyFieldInfo.getColumnName() + SqlUtil.equalOrIn(primaryKeyList));
            // 组装↑

            // 执行
            int count = sqlMapper.update(sql.toString());
            if(totalCount < 0){
                totalCount = 0;
            }
            totalCount += count;
        }while (!tempList.isEmpty());

        return totalCount;
    }

    @Override
    public <T> int updateListSelective(List<T> list) {
        if(list == null || list.isEmpty()){
            return 0;
        }
        return this.updateListSelective(list, list.size());
    }

    @Override
    public <T> int updateListSelective(List<T> list, int size) {
        // 更新顺序，从前往后
        int totalCount = 0;
        if(list == null || list.size() == 0){
            return totalCount;
        }
        // 更新位置
        int startPosition = 0;
        Class objectClass = list.get(0).getClass();
        // 类信息↓
        CommonClassInfo classInfo = this.getCommonClassInfo(objectClass);
        // 表名↓
        String tableName = classInfo.getTableName();
        // 主键信息↓
        CommonFieldInfo keyFieldInfo = classInfo.getKeyFieldInfo();
        // 主键字段名↓
        String keyColumnName = keyFieldInfo.getColumnName();
        // 主键get方法名↓
        String keyMethodName = keyFieldInfo.getGetMethodName();

        List<T> tempList;
        StringBuffer sql;
        do {
            // 装入需要用到的临时数据 ↓
            int endPosition = startPosition + size;
            if(endPosition > list.size()){
                endPosition = list.size();
            }
            tempList = list.subList(startPosition, endPosition);
            startPosition = endPosition;
            // 装入需要用到的临时数据 ↑

            // 主键信息↓
            List<String> primaryKeyList = new ArrayList<>();
            // 主键信息↑

            // 组装↓
            sql = new StringBuffer();
            sql.append(" update " + tableName + " set");
            // 遍历字段
            List<CommonFieldInfo> fieldInfoList = classInfo.getCommonFieldInfoList();
            for(int i = 0;i < fieldInfoList.size();i++){
                // 字段信息
                CommonFieldInfo fieldInfo = fieldInfoList.get(i);
                // 字段名
                String columnName = fieldInfo.getColumnName();
                // 方法名
                String getMethodName = fieldInfo.getGetMethodName();
                if(fieldInfo.isKey() || fieldInfo.equals(keyFieldInfo)){
                    // 跳过主键
                    continue;
                }
                if(StringUtils.isBlank(columnName)){
                    // 没有字段注解，则跳过
                    continue;
                }
                StringBuffer setSql = new StringBuffer();
                boolean hasData = false;
                setSql.append(" " + columnName + " = CASE " + keyColumnName);
                // 遍历数据
                for(int j = 0;j < tempList.size(); j++){
                    T obj = tempList.get(j);
                    String currentId = GlobalFunc.toString(this.getValueByObjectAndMethodName(obj, keyMethodName));
                    // 通过get方法获得值
                    Object value = this.getValueByObjectAndMethodName(obj, getMethodName);
                    if (!primaryKeyList.contains(currentId)) {
                        primaryKeyList.add(currentId);
                    }
                    if(value == null){
                        // 数据为空则不更新
                        continue;
                    }
                    String valueStr = this.valueTransference(value);
                    hasData = true;
                    setSql.append(" WHEN '" + currentId + "' THEN " + valueStr);
                }
                if(hasData){
                    sql.append(setSql.toString() + " END,");
                }
            }
            // 组装↑

            // 去除末尾逗号↓
            sql = this.cutEndComma(sql);
            sql.append(" where " + keyFieldInfo.getColumnName() + SqlUtil.equalOrIn(primaryKeyList));

            // 执行↓
            int count = sqlMapper.update(sql.toString());
            if(totalCount < 0){
                totalCount = 0;
            }
            totalCount += count;
        }while (startPosition < list.size());

        return totalCount;
    }

    @Override
    public <T> T getById(Class<T> objectClass,String id){
        if(StringUtils.isBlank(id)){
            return null;
        }
        StringBuffer sql = new StringBuffer();
        // 类信息
        CommonClassInfo classInfo = this.getCommonClassInfo(objectClass);
        // 表名↓
        String tableName = classInfo.getTableName();
        // 主键信息↓
        CommonFieldInfo keyFieldInfo = classInfo.getKeyFieldInfo();
        String keyColumnName = keyFieldInfo.getColumnName();
        // Map对象↓
        sql.append("select * from " + tableName + " where " + keyColumnName + " = '" + id + "'");
        Map<String,Object> map = this.sqlMapper.sqlQueryOne(sql.toString());
        // Map对象↑
        // 生成对象↓
        T object = this.convertMapToObject(objectClass,map);
        // 生成对象↑

        return object;
    }

    @Override
    public <T> List<T> getAll(Class<T> objectClass) {
        StringBuffer sql = new StringBuffer();
        // 类信息↓
        CommonClassInfo classInfo = this.getCommonClassInfo(objectClass);
        // 表名↓
        String tableName = classInfo.getTableName();
        // Map对象↓
        sql.append("select * from " + tableName);
        // 获得数据↓
        List<Map<String,Object>> dataList = this.sqlMapper.sqlQueryList(sql.toString());
        // 获得数据↑
        // 组装对象集合↓
        List<T> list = this.convertMapListToObjectList(objectClass,dataList);
        // 组装对象集合↑

        return list;
    }

    @Override
    public <T> List<T> getByCondition(Class<T> objectClass, SqlCondition sqlCondition){
        StringBuffer sql = new StringBuffer();
        // 类信息↓
        CommonClassInfo classInfo = this.getCommonClassInfo(objectClass);
        // 表名↓
        String tableName = classInfo.getTableName();
        // Map对象↓
        sql.append("select * from " + tableName + " where 1=1");
        // 条件↓
        String conditionSql = sqlCondition.getSql();
        sql.append(conditionSql);
        // 条件↑
        // 获得数据↓
        List<Map<String,Object>> dataList = this.sqlMapper.sqlQueryList(sql.toString());
        // 获得数据↑
        // 组装对象集合↓
        List<T> list = this.convertMapListToObjectList(objectClass,dataList);
        // 组装对象集合↑

        return list;
    }

    @Override
    public List<String> getUniqueColumn(Class objectClass, String columnName) {
        StringBuffer sql = new StringBuffer();
        columnName = this.getColumnNameByFieldName(columnName);
        // 类信息↓
        CommonClassInfo classInfo = this.getCommonClassInfo(objectClass);
        // 表名↓
        String tableName = classInfo.getTableName();
        // Map对象↓
        sql.append("select " + columnName + " from " + tableName);
        // 获得数据↓
        List<String> list = this.sqlMapper.sqlQueryWithUniqueColumnList(sql.toString());
        // 获得数据↑

        return list;
    }

    @Override
    public List<String> getUniqueColumn(Class objectClass, String columnName, SqlCondition sqlCondition) {
        StringBuffer sql = new StringBuffer();
        columnName = this.getColumnNameByFieldName(columnName);
        // 类信息↓
        CommonClassInfo classInfo = this.getCommonClassInfo(objectClass);
        // 表名↓
        String tableName = classInfo.getTableName();
        // Map对象↓
        sql.append("select " + columnName + " from " + tableName + " where 1=1");
        // 条件↓
        String conditionSql = sqlCondition.getSql();
        sql.append(conditionSql);
        // 条件↑
        // 获得数据↓
        List<String> list = this.sqlMapper.sqlQueryWithUniqueColumnList(sql.toString());
        // 获得数据↑

        return list;
    }

    @Override
    public int getCount(Class objectClass) {
        StringBuffer sql = new StringBuffer();
        // 类信息↓
        CommonClassInfo classInfo = this.getCommonClassInfo(objectClass);
        // 表名↓
        String tableName = classInfo.getTableName();
        // Map对象↓
        sql.append("select count(1) from " + tableName);
        // 获得统计数据↓
        int count = this.sqlMapper.sqlCount(sql.toString());
        // 获得统计数据↑

        return count;
    }

    @Override
    public int getCount(Class objectClass, SqlCondition sqlCondition) {
        // 类信息↓
        CommonClassInfo classInfo = this.getCommonClassInfo(objectClass);
        // 表名↓
        String tableName = classInfo.getTableName();
        int count = this.getCount(tableName,sqlCondition);
        return count;
    }

    @Override
    public Map<String,Object> getByConditionWithEasyUI(Class objectClass,int page,int rows,SqlCondition sqlCondition){
        StringBuffer sql = new StringBuffer();
        // 类信息↓
        CommonClassInfo classInfo = this.getCommonClassInfo(objectClass);
        // 表名↓
        String tableName = classInfo.getTableName();
        // Map对象↓
        sql.append("select * from " + tableName + " where 1=1");
        // 条件↓
        String conditionSql = sqlCondition.getSql();
        sql.append(conditionSql);
        // 条件↑
        // 获得数据↓
        PageHelper.startPage(page,rows);
        List<Map<String,Object>> dataList = this.sqlMapper.sqlQueryList(sql.toString());
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(dataList);
        List<Map<String,Object>> pageList = pageInfo.getList();
        long total = pageInfo.getTotal();
        // 获得数据↑
        // 组装对象集合↓
        List<T> list = this.convertMapListToObjectList(objectClass,pageList);
        // 组装对象集合↑

        Map<String,Object> map = new HashMap<>();
        map.put("rows",list);
        map.put("total",total);

        return map;
    }

    @Override
    public boolean isTableExist(String tableName) {
        String sql = "select count(1) from INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME='" + tableName + "'";
        sql += " and " + CommonConfig.DATA_BASE_COLUMN + " = '" + ApplicationConfig.getDataBaseName() + "'";
        int count = this.sqlMapper.sqlCount(sql);
        return count > 0;
    }

    @Override
    public boolean isColumnExist(String tableName, String... columnName) {
        List<String> columnNameList = new ArrayList<>();
        if(columnName != null){
            columnNameList = new ArrayList<>(Arrays.asList(columnName));
        }
        return isColumnExist(tableName, columnNameList);
    }

    @Override
    public boolean isColumnExist(String tableName, List<String> columnName) {
        if(columnName == null || columnName.isEmpty()){
            return true;
        }

        StringBuffer sql = new StringBuffer();
        sql.append(" select distinct column_name from INFORMATION_SCHEMA.COLUMNS");
        sql.append(" WHERE TABLE_NAME = '" + tableName + "'");

        List<String> columnList = sqlMapper.sqlQueryWithUniqueColumnList(sql.toString());

        for(int i = 0;i < columnName.size(); i++){
            // 当前需要判断是否存在字段
            String c = columnName.get(i);
            boolean isExist = false;
            for(int j = 0;j < columnList.size();j++){
                // 数据库中的字段
                String name = columnList.get(j);
                if(name.equals(c)){
                    isExist = true;
                    break;
                }
            }
            if(!isExist){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean dropTable(String tableName) {
        boolean result = false;
        if(this.isTableExist(tableName)){
            result = true;
            this.sqlMapper.sqlExecute("drop table " + tableName);
        }
        return result;
    }

    @Override
    public List<String> getTableByCondition(SqlCondition sqlCondition) {
        String sql = "select distinct table_name from INFORMATION_SCHEMA.columns";
        sql += " where " + CommonConfig.DATA_BASE_COLUMN + " = '" + ApplicationConfig.getDataBaseName() + "'";
        // 条件语句
        String conditionSql = sqlCondition.getSql();
        sql += conditionSql;
        List<String> columnList = this.sqlMapper.sqlQueryWithUniqueColumnList(sql);

        return columnList;
    }

    @Override
    public List<String> getColumnByTableName(String tableName) {
        String sql = "select distinct column_name from INFORMATION_SCHEMA.columns where table_name='" + tableName + "'";
        sql +=  " and " + CommonConfig.DATA_BASE_COLUMN + " = '" + ApplicationConfig.getDataBaseName() + "'";
        List<String> columnList = this.sqlMapper.sqlQueryWithUniqueColumnList(sql);

        return columnList;
    }

    @Override
    public List<String> getColumnByCondition(SqlCondition sqlCondition) {
        String sql = "select distinct column_name from INFORMATION_SCHEMA.columns";
        sql +=  " where " + CommonConfig.DATA_BASE_COLUMN + " = '" + ApplicationConfig.getDataBaseName() + "'";
        // 条件语句
        String conditionSql = sqlCondition.getSql();
        sql += conditionSql;
        List<String> columnList = this.sqlMapper.sqlQueryWithUniqueColumnList(sql);

        return columnList;
    }

    @Override
    public <T> T convertMapToObject(Class<T> objectClass,Map<String,Object> map){
        T object = null;
        if (map == null || map.isEmpty()){
            return object;
        }
        // 类信息↓
        CommonClassInfo classInfo = this.getCommonClassInfo(objectClass, true);
        // 字段信息↓
        List<CommonFieldInfo> commonFieldInfoList = classInfo.getCommonFieldInfoList();

        try {
            object = objectClass.newInstance();
            // 插入数据↓
            for(int i = 0;i < commonFieldInfoList.size();i++){
                CommonFieldInfo fieldInfo = commonFieldInfoList.get(i);
                String fieldName = fieldInfo.getFieldName();
                // 方法名↓
                String methodName = fieldInfo.getSetMethodName();
                // 字段名↓
                String columnName = fieldInfo.getColumnName();
                // 传值属性
                Class<?> fieldClass = fieldInfo.getFieldClass();
                if(StringUtils.isBlank(columnName)){
                    // 没有字段注解，则跳过
                    continue;
                }
                // 取得它的set方法
                Method method = null;
                Method[] declaredMethods = objectClass.getDeclaredMethods();
                for (Method declaredMethod : declaredMethods) {
                    // 方法名
                    String currentMethodName = declaredMethod.getName();
                    // 参数
                    Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                    // 名称相等
                    if(currentMethodName.equalsIgnoreCase("set" + fieldName)){
                        // 参数相同
                        if(parameterTypes != null && parameterTypes.length > 0 && parameterTypes[0] == fieldClass){
                            // 匹配成功
                            method = declaredMethod;
                            break;
                        }
                    }
                }
                // 取值↓
                if(method != null && map.containsKey(columnName)){
                    Object value = map.get(columnName);
                    // 使用set方法↓
                    if(fieldClass == String.class){
                        String stringValue = GlobalFunc.toString(value);
                        method.invoke(object,stringValue);
                    }else if(fieldClass == int.class || fieldClass == Integer.class){
                        Integer integerValue = GlobalFunc.parseInt(value);
                        method.invoke(object,integerValue);
                    }else if(fieldClass == Date.class){
                        // TODO 日期转换木有工具 _(:з」∠)_
                        method.invoke(object,value);
                    }else if(fieldClass == double.class || fieldClass == Double.class){
                        Double doubleValue = GlobalFunc.parseDouble(value);
                        method.invoke(object,doubleValue);
                    }else if(fieldClass == BigDecimal.class){
                        String str = GlobalFunc.toString(value);
                        if(StringUtils.isBlank(str)){
                            // 空字符串不存入
                            continue;
                        }
                        BigDecimal bigDecimal = new BigDecimal(str);
                        method.invoke(object,bigDecimal);
                    }else{
                        method.invoke(object,value);
                    }
                }
            }
            // 插入数据↑
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public <T> List<T> convertMapListToObjectList(Class<T> objectClass,List<Map<String,Object>> mapList){
        List<T> list = new ArrayList<>();
        if(mapList != null){
            for(int i = 0;i < mapList.size();i++){
                Map<String,Object> map = mapList.get(i);
                T object = this.convertMapToObject(objectClass, map);
                list.add(object);
            }
        }
        return list;
    }

    @Override
    public Map<String, Object> getMapByTableNameAndId(String tableName, String id) {
        String sql = "select * from " + tableName + " where id = '" + id + "'";
        Map<String,Object> map = this.sqlMapper.sqlQueryOne(sql);
        return map;
    }

    @Override
    public List<Map<String, Object>> getMapByTableNameAndSqlCondition(String tableName, SqlCondition sqlCondition) {
        String conditionSql = sqlCondition.getSql();
        String sql = "select * from " + tableName + " where 1=1" + conditionSql;
        List<Map<String, Object>> list = this.sqlMapper.sqlQueryList(sql);
        return list;
    }

    @Override
    public int insertMap(String tableName, Map<String, Object> map) {
        int count = 0;
        if(map == null || map.size() == 0){
            return count;
        }

        String columnName = "";
        String columnValue = "";

        // 表中所有字段
        List<String> columnNameList = this.getColumnByTableName(tableName);

        // 遍历所有字段
        for(int i = 0;i < columnNameList.size();i++){
            String name = columnNameList.get(i);
            String value = this.valueTransference(GlobalFunc.toString(map.get(name)));
            columnName += name + ",";
            columnValue += value + ",";
        }
        if(columnName.endsWith(",")){
            columnName = columnName.substring(0,columnName.length() - 1);
        }
        if(columnValue.endsWith(",")){
            columnValue = columnValue.substring(0,columnValue.length() - 1);
        }

        String sql = " insert into " + tableName + "(" + columnName + ")values(" + columnValue + ")";
        count = this.sqlMapper.insert(sql);
        return count;
    }

    @Override
    public int updateMap(String tableName, Map<String, Object> map) {
        int count = 0;
        if(!map.containsKey("id")){
            return count;
        }

        String id = GlobalFunc.toString(map.get("id"));
        // 表中所有字段
        List<String> columnNameList = this.getColumnByTableName(tableName);

        StringBuffer sql = new StringBuffer();
        sql.append("update " + tableName + " set ");

        // 遍历所有字段
        for(int i = 0;i < columnNameList.size();i++){
            String columnName = columnNameList.get(i);
            if("id".equals(columnName)){
                continue;
            }
            Object value = map.get(columnName);
            // 转义
            sql.append(" " + columnName + "=" + this.valueTransference(value) + ",");
        }

        String sqlString = sql.toString();
        if(sqlString.endsWith(",")){
            sqlString = sqlString.substring(0,sqlString.length() - 1);
        }
        sqlString += " where id = '" + id + "'";

        count = this.sqlMapper.update(sqlString);

        return count;
    }

    @Override
    public int updateMapSelective(String tableName, Map<String, Object> map) {
        int count = 0;
        if(!map.containsKey("id")){
            return count;
        }

        String id = GlobalFunc.toString(map.get("id"));
        // 表中所有字段
        List<String> columnNameList = this.getColumnByTableName(tableName);

        StringBuffer sql = new StringBuffer();
        sql.append("update " + tableName + " set");

        // 遍历所有字段
        for(int i = 0;i < columnNameList.size();i++){
            String columnName = columnNameList.get(i);
            if("id".equals(columnName)){
                continue;
            }

            if(!map.containsKey(columnName)){
                // 如果值为空则跳过
                continue;
            }
            Object value = map.get(columnName);
            // 转义
            sql.append(" " + columnName + "=" + this.valueTransference(value) + ",");
        }

        String sqlString = sql.toString();
        if(sqlString.endsWith(",")){
            sqlString = sqlString.substring(0,sqlString.length() - 1);
        }
        sqlString += " where id = '" + id + "'";

        count = this.sqlMapper.update(sqlString);

        return count;
    }

    @Override
    public int deleteByTableNameAndId(String tableName, String id) {
        SqlCondition sqlCondition = new SqlCondition();
        if(StringUtils.isNotBlank(id)){
            sqlCondition.andEqualTo("id", id);
        }
        return this.deleteByTableNameAndCondition(tableName, sqlCondition);
    }

    @Override
    public int deleteByTableNameAndId(String tableName, List<String> idList) {
        SqlCondition sqlCondition = new SqlCondition();
        if(idList != null && !idList.isEmpty()){
            sqlCondition.andIn("id", idList);
        }
        return this.deleteByTableNameAndCondition(tableName, sqlCondition);
    }

    @Override
    public int deleteByTableNameAndCondition(String tableName, SqlCondition sqlCondition) {
        if(sqlCondition == null || sqlCondition.isAndCriteriaListEmpty()){
            // 如果条件为空，不允许删除
            return 0;
        }
        String conditionSql = sqlCondition.getSql();
        String sql = "delete from " + tableName + " where 1=1 " + conditionSql;
        int count = sqlMapper.delete(sql);
        return count;
    }

    @Override
    public Map<String,Object> convertMapKeyFromUnderlineToUpperCase(Map<String,Object> map) {
        Map<String,Object> newMap = this.innerConvertMapKeyFromUnderlineToUpperCase(new ArrayList<>(), map);
        return newMap;
    }

    @Override
    public List<Map<String,Object>> convertMapKeyFromUnderlineToUpperCase(List<Map<String,Object>> mapList) {
        for(int i = 0;i < mapList.size();i++){
            Map<String,Object> map = mapList.get(i);
            Map<String,Object> newMap = convertMapKeyFromUnderlineToUpperCase(map);
            mapList.remove(i);
            mapList.add(i,newMap);
        }
        return mapList;
    }

    @Override
    public Boolean isSqlExecutable(String sql) {
        boolean result = true;
        try {
            this.sqlMapper.sqlExecute(sql);
        }catch (Exception e){
            result = false;
        }
        return result;
    }

    @Override
    public Map<String, Object> convertObjectToMap(Object object) {
        Map<String, Object> map = new HashMap<>();
        // 获取f对象对应类中的所有属性域
        Field[] fields = object.getClass().getDeclaredFields();
        int len = fields.length;
        for (int i = 0;i < len;i++) {
            String varName = fields[i].getName();
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o = fields[i].get(object);
                if (o != null){
                    map.put(varName, o);
                }
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> convertObjectListToMapList(List list) {
        if(list == null){
            return Collections.emptyList();
        }
        List<Map<String,Object>> result = new ArrayList<>();
        for(int i = 0;i < list.size();i++){
            Object obj = list.get(i);
            Map<String,Object> map = this.convertObjectToMap(obj);
            result.add(map);
        }
        return result;
    }

    @Override
    public int getCount(String tableName) {
        String sql = "select count(1) from " + tableName;
        int count = sqlMapper.sqlCount(sql);
        return count;
    }

    @Override
    public int getCount(String tableName, SqlCondition sqlCondition) {
        StringBuffer sql = new StringBuffer();
        // Map对象↓
        sql.append("select count(1) from " + tableName + " where 1=1");
        // 条件↓
        String conditionSql = sqlCondition.getSql();
        sql.append(conditionSql);
        // 条件↑
        // 获得统计数据↓
        int count = this.sqlMapper.sqlCount(sql.toString());
        // 获得统计数据↑

        return count;
    }

    @Override
    public CommonClassInfo getCommonClassInfo(Class<?> objectClass){
        return this.getCommonClassInfo(objectClass, false);
    }

    @Override
    public CommonClassInfo getCommonClassInfo(Class<?> objectClass, boolean withOutAnnotation){
        CommonClassInfo classInfo = new CommonClassInfo();

        String tableName = "";
        List<CommonFieldInfo> commonFieldInfoList = new ArrayList<>();

        // 第一步 获得类对象注解上的表名 ↓
        tableName = GlobalFunc.toString(this.getAnnotationValue(objectClass, Table.class,"name"));
        // 第一步 ↑

        // 第二步 遍历对象中的字段属性 ↓
        Field[] fieldList = objectClass.getDeclaredFields();
        CommonFieldInfo commonFieldInfo;
        Object value;
        for(Field field : fieldList){
            // 类中字段名
            String fieldName = field.getName();
            // set方法名称
            String setMethodName = this.getMethodName("set",fieldName);
            // get方法名称
            String getMethodName = this.getMethodName("get",fieldName);
            // 表中字段名
            String columnName = GlobalFunc.toString(this.getAnnotationValue(field, Column.class,"name"));
            // 类中字段属性
            Class<?> fieldClass = field.getType();
            // 表中字段属性
            String columnTypeName = GlobalFunc.toString(this.getAnnotationValue(field,Column.class,"type")).toUpperCase();
            // 字段中文注释
            String annotateName = GlobalFunc.toString(this.getAnnotationValue(field, FieldName.class,"name"));
            // 是否为主键
            boolean isKey = false;
            value =  this.getAnnotationValue(field,Column.class,"isKey");
            if(value != null){
                isKey = (boolean) value;
            }
            // 主键是否自增长
            boolean isAutoIncrease = false;
            value = this.getAnnotationValue(field,Column.class,CommonConfig.COLUMN_AUTO_INCREASE_NAME);
            if(value != null){
                isAutoIncrease = (boolean) value;
            }
            // 这四个参数都是必须的（注释后同时适用于非数据库实体）
//            if(StringUtils.isBlank(fieldName)
//                    || StringUtils.isBlank(columnName)
//                    || fieldClass == null
//                    || StringUtils.isBlank(columnTypeName)){
//                continue;
//            }

            if(withOutAnnotation){
                // 忽略字段没有注解的影响
                if(field.getAnnotation(Column.class) == null){
                    columnName = SqlUtil.convertToLowercase(fieldName, "_", "");
                    columnTypeName = "VARCHAR";
                }
            }

            // 保存
            commonFieldInfo = new CommonFieldInfo();
            commonFieldInfo.setSetMethodName(setMethodName);
            commonFieldInfo.setGetMethodName(getMethodName);
            commonFieldInfo.setFieldName(fieldName);
            commonFieldInfo.setColumnName(columnName);
            commonFieldInfo.setFieldClass(fieldClass);
            commonFieldInfo.setColumnTypeName(columnTypeName);
            commonFieldInfo.setAnnotateName(annotateName);
            commonFieldInfo.setIsKey(isKey);
            commonFieldInfo.setIsAutoIncrease(isAutoIncrease);
            commonFieldInfo.setSetMethodName(setMethodName);
            commonFieldInfo.setGetMethodName(getMethodName);
            commonFieldInfoList.add(commonFieldInfo);
        }
        // 第二步 ↑

        classInfo.setTableName(tableName);
        classInfo.setCommonFieldInfoList(commonFieldInfoList);

        return classInfo;
    }

    @Override
    public List<Map<String, Object>> convertMapKeyToAnotherMapKey(List<Map<String, Object>> list, String tableName,
                                                                  String keyColumnName, String targetColumnName,
                                                                  String keyMapName, String targetMapName) {
        list = this.convertMapKeyToAnotherMapKey(list,tableName,keyColumnName,targetColumnName,keyMapName,targetMapName,null);
        return list;
    }

    @Override
    public List<Map<String, Object>> convertMapKeyToAnotherMapKey(List<Map<String, Object>> list, String tableName,
                                                                  String keyColumnName, String targetColumnName,
                                                                  String keyMapName, String targetMapName,
                                                                  String separator) {
        // 第一步，取出所有数据↓
        List<String> valueList = new ArrayList<>();
        for(Map<String,Object> map : list){
            if(map.containsKey(keyMapName)){
                String value = GlobalFunc.toString(map.get(keyMapName));
                // 分隔符不为空↓
                if(StringUtils.isNotBlank(separator)){
                    valueList.addAll(Arrays.asList(value.split(separator)));
                }else{
                    valueList.add(value);
                }
            }
        }
        // 去重
        valueList = ArrayUtil.removeRepeat(valueList);

        // 第二步，查询↓
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.andIn(keyColumnName,valueList);
        List<Map<String, Object>> dataListMap = this.getMapByTableNameAndSqlCondition(tableName, sqlCondition);

        // 第三步，往List中放入targetMapName↓
        for(Map<String,Object> map : list){
            String targetValue = "";
            if(map.containsKey(keyMapName)){
                String value = GlobalFunc.toString(map.get(keyMapName));
                // 当前map的关键字集合↓
                List<String> currentKeyValueList = new ArrayList<>();
                // 分隔符不为空↓
                if(StringUtils.isNotBlank(separator)){
                    currentKeyValueList.addAll(Arrays.asList(value.split(separator)));
                }else{
                    currentKeyValueList.add(value);
                }
                // 符合条件的集合↓
                List<Map<String,Object>> currentMapList = new ArrayList<>();
                for(String mapKey : currentKeyValueList){
                    for(Map<String,Object> dataMap : dataListMap){
                        // 查询出数据中包含key，且原始数据中的value值等于查询出数据中的value值，且target值不为空
                        if(dataMap.containsKey(keyColumnName)
                                && mapKey.equals(GlobalFunc.toString(dataMap.get(keyColumnName)))
                                && StringUtils.isNotBlank(GlobalFunc.toString(dataMap.get(targetColumnName)))
                                ){
                            currentMapList.add(dataMap);
                        }
                    }
                }
                // 如果有多个则拼接↓
                for(int i = 0;i < currentMapList.size(); i++){
                    Map<String, Object> currentMap = currentMapList.get(i);
                    targetValue += GlobalFunc.toString(currentMap.get(targetColumnName));
                    if(i < (currentMapList.size() - 1)){
                        // 超过一个数据，没有分隔符，则默认逗号
                        if(StringUtils.isBlank(separator)){
                            separator = ",";
                        }
                        targetValue += separator;
                    }
                }
            }
            // 最后放入↓
            map.put(targetMapName,targetValue);
        }

        return list;
    }

    @Override
    public Map<String, Object> convertMapKeyToAnotherMapKey(Map<String, Object> map, String tableName,
                                                                  String keyColumnName, String targetColumnName,
                                                                  String keyMapName, String targetMapName) {
        map = this.convertMapKeyToAnotherMapKey(map,tableName,keyColumnName,targetColumnName,keyMapName,targetMapName,null);
        return map;
    }

    @Override
    public Map<String, Object> convertMapKeyToAnotherMapKey(Map<String, Object> map, String tableName,
                                                                  String keyColumnName, String targetColumnName,
                                                                  String keyMapName, String targetMapName,
                                                                  String separator) {
        // 第一步，取出所有数据↓
        List<String> valueList = new ArrayList<>();
        if(map.containsKey(keyMapName)){
            String value = GlobalFunc.toString(map.get(keyMapName));
            // 分隔符不为空↓
            if(StringUtils.isNotBlank(separator)){
                valueList.addAll(Arrays.asList(value.split(separator)));
            }else{
                valueList.add(value);
            }
        }
        // 去重
        valueList = ArrayUtil.removeRepeat(valueList);

        // 第二步，查询↓
        SqlCondition sqlCondition = new SqlCondition();
        sqlCondition.andIn(keyColumnName,valueList);
        List<Map<String, Object>> dataListMap = this.getMapByTableNameAndSqlCondition(tableName, sqlCondition);

        // 第三步，往map中放入targetMapName↓
        String targetValue = "";
        if(map.containsKey(keyMapName)){
            String value = GlobalFunc.toString(map.get(keyMapName));
            // 当前map的关键字集合↓
            List<String> currentKeyValueList = new ArrayList<>();
            // 分隔符不为空↓
            if(StringUtils.isNotBlank(separator)){
                currentKeyValueList.addAll(Arrays.asList(value.split(separator)));
            }else{
                currentKeyValueList.add(value);
            }
            // 符合条件的集合↓
            List<Map<String,Object>> currentMapList = new ArrayList<>();
            for(String mapKey : currentKeyValueList){
                for(Map<String,Object> dataMap : dataListMap){
                    // 查询出数据中包含key，且原始数据中的value值等于查询出数据中的value值，且target值不为空
                    if(dataMap.containsKey(keyColumnName)
                            && mapKey.equals(GlobalFunc.toString(dataMap.get(keyColumnName)))
                            && StringUtils.isNotBlank(GlobalFunc.toString(dataMap.get(targetColumnName)))
                            ){
                        currentMapList.add(dataMap);
                    }
                }
            }
            // 如果有多个则拼接↓
            for(int i = 0;i < currentMapList.size(); i++){
                Map<String, Object> currentMap = currentMapList.get(i);
                targetValue += GlobalFunc.toString(currentMap.get(targetColumnName));
                if(i < (currentMapList.size() - 1)){
                    // 超过一个数据，没有分隔符，则默认逗号
                    if(StringUtils.isBlank(separator)){
                        separator = ",";
                    }
                    targetValue += separator;
                }
            }
        }
        // 最后放入↓
        map.put(targetMapName,targetValue);

        return map;
    }

    @Override
    public <T> Map<String, T> convertListToMap(List<T> list, String fieldName) {
        Map<String, T> map = new HashMap<>();
        if(StringUtils.isBlank(fieldName) || list == null || list.isEmpty()){
            return map;
        }

        // 类信息
        Class<?> objectClass = list.get(0).getClass();
        // 字段信息
        CommonFieldInfo keyFieldInfo = this.getCommonFieldInfoByFieldName(objectClass, fieldName);
        if(keyFieldInfo == null){
            return map;
        }

        // 开始转换
        String getMethodName = keyFieldInfo.getGetMethodName();
        for(int i = 0;i < list.size();i++){
            T obj = list.get(i);
            String keyValue = GlobalFunc.toString(this.getValueByObjectAndMethodName(obj, getMethodName));
            if(map.containsKey(keyValue)){
                log.info("存在相同数据，旧数据已被覆盖！keyValue:" + keyValue);
            }
            map.put(keyValue, obj);
        }

        return map;
    }

    @Override
    public <T> List<T> removeDuplicate(List<T> list, String fieldName) {
        return this.removeDuplicate(list, fieldName, true);
    }

    @Override
    public <T> List<T> removeDuplicate(List<T> list, String fieldName, boolean isCover) {
        int replacedCount = 0;
        if(list == null || list.isEmpty()){
            return list;
        }

        // 类信息
        Class<?> objectClass = list.get(0).getClass();
        // 字段
        CommonFieldInfo commonFieldInfo = this.getCommonFieldInfoByFieldName(objectClass, fieldName);
        if(commonFieldInfo == null){
            return list;
        }
        // 方法
        String getMethodName = commonFieldInfo.getGetMethodName();

        List<String> keyList = new ArrayList<>();
        if(isCover){
            // 从后往前
            for(int i = (list.size() - 1); i >= 0;i--){
                T obj = list.get(i);
                String value = GlobalFunc.toString(this.getValueByObjectAndMethodName(obj, getMethodName));
                if(keyList.contains(value)){
                    // 已存在，则移除
                    list.remove(i);
                    replacedCount++;
                }else{
                    keyList.add(value);
                }
            }
        }else{
            // 从前往后
            for(int i = 0; i < list.size();i++){
                T obj = list.get(i);
                String value = GlobalFunc.toString(this.getValueByObjectAndMethodName(obj, getMethodName));
                if(keyList.contains(value)){
                    // 已存在，则移除
                    list.remove(i);
                    replacedCount++;
                    // 保持i不变
                    i--;
                }else{
                    keyList.add(value);
                }
            }
        }

        log.info("移除" + fieldName + "字段相同的数据：" + replacedCount + "个");

        return list;
    }

    @Override
    public String getColumnNameByFieldName(String fieldName) {
        String columnName = SqlUtil.convertToLowercase(fieldName, "_", "");
        if (columnName.startsWith("_")) {
            columnName = columnName.substring(1, columnName.length());
        }
        return columnName;
    }

    @Override
    public Object getAnnotationValue(Object object,Class annotationClass,String name){
        Object value = null;
        Annotation annotation = null;
        // 获取注解
        if(object instanceof Class){
            annotation = ((Class) object).getAnnotation(annotationClass);
        }else if(object instanceof Field){
            annotation = ((Field) object).getAnnotation(annotationClass);
        }
        if(annotation == null){
            return value;
        }
        try {
            // 尝试取得注解上的参数
            Method annotationMethod = annotation.getClass().getDeclaredMethod(name);
            value = annotationMethod.invoke(annotation);
        } catch (Exception e) {
            e.printStackTrace();
            return value;
        }
        return value;
    }

    @Override
    public List<String> orderByPinYin(List<String> list) {
        List<String> orderList = new ArrayList<>();
        if(list == null || list.isEmpty()){
            return list;
        }
        // 转换拼音
        Map<String, List<String>> pinYinMap = new HashMap<>();
        for(int i = 0;i < list.size();i++){
            String strValue = list.get(i);
            // 拼音
            String pinYin = "";
            try {
                pinYin = Pinyin4JHelper.getStringPinYin(strValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 放入
            if(pinYinMap.containsKey(pinYin)){
                List<String> strList = pinYinMap.get(pinYin);
                strList.add(strValue);
            }else{
                List<String> strList = new ArrayList<>();
                strList.add(strValue);
                pinYinMap.put(pinYin, strList);
            }
        }
        // 拼音排序
        List<String> pinYinList = new ArrayList<>(pinYinMap.keySet());
        Collections.sort(pinYinList);
        // 装回
        for (String pinYin : pinYinList) {
            List<String> objList = pinYinMap.get(pinYin);
            orderList.addAll(objList);
        }
        return orderList;
    }

    @Override
    public <T> List<T> orderByPinYin(List<T> list, String fieldName) {
        List<T> orderList = new ArrayList<>();
        if(list == null || list.isEmpty() || StringUtils.isBlank(fieldName)){
            return list;
        }

        // 类信息
        Class<?> objectClass = list.get(0).getClass();
        // 字段信息
        CommonFieldInfo fieldInfo = this.getCommonFieldInfoByFieldName(objectClass, fieldName);
        if(fieldInfo == null){
            return orderList;
        }

        // 转换拼音
        Map<String, List<T>> pinYinMap = new HashMap<>();
        String getMethodName = fieldInfo.getGetMethodName();
        for(int i = 0;i < list.size();i++){
            T obj = list.get(i);
            String strValue = GlobalFunc.toString(this.getValueByObjectAndMethodName(obj, getMethodName));
            // 拼音
            String pinYin = "";
            try {
                pinYin = Pinyin4JHelper.getStringPinYin(strValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 放入
            if(pinYinMap.containsKey(pinYin)){
                List<T> objList = pinYinMap.get(pinYin);
                objList.add(obj);
            }else{
                List<T> objList = new ArrayList<>();
                objList.add(obj);
                pinYinMap.put(pinYin, objList);
            }
        }
        // 拼音排序
        List<String> pinYinList = new ArrayList<>(pinYinMap.keySet());
        Collections.sort(pinYinList);
        // 装回
        for (String pinYin : pinYinList) {
            List<T> objList = pinYinMap.get(pinYin);
            orderList.addAll(objList);
        }
        return orderList;
    }

    @Override
    public <T> List<T> orderByAssistList(List<T> list, String fieldName, List<String> assistList) {
        List<T> orderList = list;
        if(list == null || list.isEmpty() || StringUtils.isBlank(fieldName) || assistList == null || assistList.isEmpty()){
            return orderList;
        }

        // 类信息
        Class<?> objectClass = list.get(0).getClass();
        // 字段信息
        CommonFieldInfo fieldInfo = this.getCommonFieldInfoByFieldName(objectClass, fieldName);
        if(fieldInfo == null){
            return orderList;
        }
        String getMethodName = fieldInfo.getGetMethodName();

        // 空容器
        orderList = new ArrayList<>();
        // 已匹配的位置
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < assistList.size(); i++) {
            String assist = assistList.get(i);
            T obj = null;
            for (int j = 0; j < list.size(); j++) {
                T currentObj = list.get(j);
                // 排序字段对应的值
                String strValue = GlobalFunc.toString(this.getValueByObjectAndMethodName(currentObj, getMethodName));
                if(assist.equals(strValue)){
                    obj = currentObj;
                    break;
                }
            }
            if(obj != null){
                orderList.add(obj);
                indexList.add(i);
            }
        }
        // 未匹配的放最后
        for (int i = 0; i < list.size(); i++) {
            if(!indexList.contains(i)){
                T obj = list.get(i);
                orderList.add(obj);
            }
        }

        return orderList;
    }

    @Override
    public List<Map<String, Object>> orderMapByAssistList(List<Map<String, Object>> list, String keyName, List<String> assistList) {
        List<Map<String, Object>> orderList = list;
        if(list == null || list.isEmpty() || StringUtils.isBlank(keyName) || assistList == null || assistList.isEmpty()){
            return orderList;
        }

        // 空容器
        orderList = new ArrayList<>();
        // 已匹配的位置
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < assistList.size(); i++) {
            String assist = assistList.get(i);
            Map<String, Object> map = null;
            for (int j = 0; j < list.size(); j++) {
                Map<String, Object> currentMap = list.get(j);
                // 排序字段对应的值
                String strValue = GlobalFunc.toString(currentMap.get(keyName));
                if(assist.equals(strValue)){
                    map = currentMap;
                    break;
                }
            }
            if(map != null){
                orderList.add(map);
                indexList.add(i);
            }
        }
        // 未匹配的放最后
        for (int i = 0; i < list.size(); i++) {
            if(!indexList.contains(i)){
                Map<String, Object> obj = list.get(i);
                orderList.add(obj);
            }
        }

        return orderList;
    }

    @Override
    public <T> List<T> orderByField(List<T> list, String fieldName, boolean isAsc) {
        List<T> orderList = new ArrayList<>();
        if(list == null || list.isEmpty() || StringUtils.isBlank(fieldName)){
            return list;
        }

        // 类信息
        Class<?> objectClass = list.get(0).getClass();
        // 字段信息
        CommonFieldInfo fieldInfo = this.getCommonFieldInfoByFieldName(objectClass, fieldName);
        if(fieldInfo == null){
            return orderList;
        }

        // 类属性
        Class<?> fieldClass = fieldInfo.getFieldClass();
        if(int.class.equals(fieldClass) || Integer.class.equals(fieldClass)){
            // int类型，转换Map
            Map<Integer, List<T>> integerMap = new HashMap<>();
            String getMethodName = fieldInfo.getGetMethodName();
            for(int i = 0;i < list.size();i++){
                T obj = list.get(i);
                Integer intValue = GlobalFunc.parseInt(this.getValueByObjectAndMethodName(obj, getMethodName));
                // 放入
                if(integerMap.containsKey(intValue)){
                    List<T> objList = integerMap.get(intValue);
                    objList.add(obj);
                }else{
                    List<T> objList = new ArrayList<>();
                    objList.add(obj);
                    integerMap.put(intValue, objList);
                }
            }
            // 数字排序
            List<Integer> intList = new ArrayList<>(integerMap.keySet());
            Collections.sort(intList);
            if(!isAsc){
                Collections.reverse(intList);
            }
            // 装回
            for (Integer intValue : intList) {
                List<T> objList = integerMap.get(intValue);
                orderList.addAll(objList);
            }
        }else{
            // String类型
            // 转换拼音
            Map<String, List<T>> strMap = new HashMap<>();
            String getMethodName = fieldInfo.getGetMethodName();
            for(int i = 0;i < list.size();i++){
                T obj = list.get(i);
                String strValue = GlobalFunc.toString(this.getValueByObjectAndMethodName(obj, getMethodName));
                // 放入
                if(strMap.containsKey(strValue)){
                    List<T> objList = strMap.get(strValue);
                    objList.add(obj);
                }else{
                    List<T> objList = new ArrayList<>();
                    objList.add(obj);
                    strMap.put(strValue, objList);
                }
            }
            // 字符排序
            List<String> strList = new ArrayList<>(strMap.keySet());
            strList = this.orderByPinYin(strList);
            if(!isAsc){
                Collections.reverse(strList);
            }
            // 装回
            for (String str : strList) {
                List<T> objList = strMap.get(str);
                orderList.addAll(objList);
            }
        }

        return orderList;
    }

    @Override
    public List<Map<String, Object>> orderByMap(List<Map<String, Object>> list, String fieldName, String fieldType, boolean isAsc) {
        List<Map<String, Object>> orderList = new ArrayList<>();
        if(list == null || list.isEmpty() || StringUtils.isBlank(fieldName) || StringUtils.isBlank(fieldType)){
            return list;
        }

        if("INT".equalsIgnoreCase(fieldType)){
            // int类型，转换Map
            Map<Integer, List<Map<String, Object>>> integerMap = new HashMap<>();
            for(int i = 0;i < list.size();i++){
                Map<String, Object> obj = list.get(i);
                Integer intValue = GlobalFunc.parseInt(obj.get(fieldName));
                // 放入
                if(integerMap.containsKey(intValue)){
                    List<Map<String, Object>> objList = integerMap.get(intValue);
                    objList.add(obj);
                }else{
                    List<Map<String, Object>> objList = new ArrayList<>();
                    objList.add(obj);
                    integerMap.put(intValue, objList);
                }
            }
            // 数字排序
            List<Integer> intList = new ArrayList<>(integerMap.keySet());
            Collections.sort(intList);
            if(!isAsc){
                Collections.reverse(intList);
            }
            // 装回
            for (Integer intValue : intList) {
                List<Map<String, Object>> objList = integerMap.get(intValue);
                orderList.addAll(objList);
            }
        }else{
            // String类型
            // 转换拼音
            Map<String, List<Map<String, Object>>> strMap = new HashMap<>();
            for(int i = 0;i < list.size();i++){
                Map<String, Object> obj = list.get(i);
                String strValue = GlobalFunc.toString(obj.get(fieldName));
                // 放入
                if(strMap.containsKey(strValue)){
                    List<Map<String, Object>> objList = strMap.get(strValue);
                    objList.add(obj);
                }else{
                    List<Map<String, Object>> objList = new ArrayList<>();
                    objList.add(obj);
                    strMap.put(strValue, objList);
                }
            }
            // 字符排序
            List<String> strList = new ArrayList<>(strMap.keySet());
            strList = this.orderByPinYin(strList);
            if(!isAsc){
                Collections.reverse(strList);
            }
            // 装回
            for (String str : strList) {
                List<Map<String, Object>> objList = strMap.get(str);
                orderList.addAll(objList);
            }
        }

        return orderList;
    }

    // ------------------------------------分割线 以下是私有方法------------------------------------

    /**
     * @Description 获得字段名对应的方法名
     * @param type 方法类型 get/set
     * @param fieldName 字段名（驼峰式）
     * @return 方法名
     */
    private String getMethodName(String type,String fieldName){
        if(StringUtils.isBlank(fieldName)){
            return null;
        }
        // 理论上只要将第一个字母转大写就行了
        fieldName = fieldName.substring(0,1).toUpperCase() + fieldName.substring(1,fieldName.length());
        if("get".equals(type.toLowerCase())){
            return "get" + fieldName;
        }else{
            return "set" + fieldName;
        }
    }

    /**
     * @Description 获得对象方法返回的值
     * @param object 对象
     * @param methodName get方法
     * @return 值
     */
    private Object getValueByObjectAndMethodName(Object object,String methodName) {
        Object value = null;
        Class objectClass = object.getClass();
        try {
            Method method = objectClass.getMethod(methodName);
            value = method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * @Description 转义 处理单引号 反斜杠
     * @param value value
     * @return 转义后的值
     */
    private String valueTransference(Object value){
        String result = SqlUtil.convertToString(value);
        return result;
    }

    /**
     * @Description 去除末尾的逗号
     * @param sql sql语句
     * @return StringBuffer
     */
    private StringBuffer cutEndComma(StringBuffer sql){
        String str = sql.toString().trim();
        if(str.endsWith(",")){
            str = str.substring(0,str.length() - 1);
        }
        return new StringBuffer(str);
    }

    /**
     * @Description 获得字段sql语句
     * 如：id,name,...
     * @param commonClassInfo 类信息
     * @return String
     */
    private String getColumnSql(CommonClassInfo commonClassInfo){
        StringBuffer columnSql = new StringBuffer();
        // 字段信息集合
        List<CommonFieldInfo> commonFieldInfoList = commonClassInfo.getCommonFieldInfoList();
        for(int j = 0;j < commonFieldInfoList.size();j++){
            // 字段信息
            CommonFieldInfo fieldInfo = commonFieldInfoList.get(j);
            String columnName = fieldInfo.getColumnName();
            if(fieldInfo.isKey() && fieldInfo.isAutoIncrease()){
                // 如果是id且自增长，则跳过
                continue;
            }
            if(StringUtils.isBlank(columnName)){
                // 没有字段注解，则跳过
                continue;
            }
            columnSql.append(columnName + ",");
        }
        // 去除末尾多余逗号↓
        columnSql = this.cutEndComma(columnSql);
        return columnSql.toString();
    }

    /**
     * @Description 获得值sql语句
     * 如：'1','张三',...
     * @param commonClassInfo 类信息
     * @param object 存储值的对象
     * @return String
     */
    private String getValuesSql(CommonClassInfo commonClassInfo, Object object){
        StringBuffer valueSql = new StringBuffer();
        // 字段信息集合
        List<CommonFieldInfo> commonFieldInfoList = commonClassInfo.getCommonFieldInfoList();
        for(int i = 0;i < commonFieldInfoList.size();i++){
            // 字段信息
            CommonFieldInfo fieldInfo = commonFieldInfoList.get(i);
            String columnName = fieldInfo.getColumnName();
            String getMethodName = fieldInfo.getGetMethodName();
            Object value = this.getValueByObjectAndMethodName(object,getMethodName);
            if(fieldInfo.isKey() && fieldInfo.isAutoIncrease()){
                // 如果是id且自增长，则跳过
                continue;
            }
            if(StringUtils.isBlank(columnName)){
                // 没有字段注解，则跳过
                continue;
            }
            // 转义
            valueSql.append(this.valueTransference(value) + ",");
        }
        // 去除末尾多余逗号↓
        valueSql = this.cutEndComma(valueSql);
        return valueSql.toString();
    }

    /**
     * @Description 获得setSql的语句 不包含set
     * 如：id='...',name='...'
     * @param object 数据对象
     * @param skipNull 是否跳过空字段
     * @return String
     */
    private String getSetSql(CommonClassInfo commonClassInfo, Object object, boolean skipNull){
        // 字段信息集合
        List<CommonFieldInfo> fieldInfoList = commonClassInfo.getCommonFieldInfoList();
        // 主键
        CommonFieldInfo keyFieldInfo = commonClassInfo.getKeyFieldInfo();
        StringBuffer setSql = new StringBuffer();
        for(int i = 0;i < fieldInfoList.size();i++){
            // 字段信息
            CommonFieldInfo fieldInfo = fieldInfoList.get(i);
            // 字段名
            String columnName = fieldInfo.getColumnName();
            // 方法名
            String getMethodName = fieldInfo.getGetMethodName();
            if(fieldInfo.isKey() || fieldInfo.equals(keyFieldInfo)){
                // 跳过主键
                continue;
            }
            if(StringUtils.isBlank(columnName)){
                // 没有字段注解，则跳过
                continue;
            }
            // 通过get方法获得值
            Object value = this.getValueByObjectAndMethodName(object,getMethodName);
            if(value == null && skipNull){
                // 如果值为空，且开启了跳过空的功能
                continue;
            }
            // 转义
            setSql.append(columnName + "=" + this.valueTransference(value) + ",");
        }
        // 去掉末尾多余逗号
        setSql = this.cutEndComma(setSql);
        return setSql.toString();
    }

    /**
     * @Description 通过字段名称获得通用字段信息
     * @param objectClass 类对象
     * @param fieldName 字段名称
     * @return CommonFieldInfo
     */
    private CommonFieldInfo getCommonFieldInfoByFieldName(Class<?> objectClass, String fieldName){
        CommonFieldInfo commonFieldInfo = null;
        if(objectClass == null || StringUtils.isBlank(fieldName)){
            return commonFieldInfo;
        }

        CommonClassInfo commonClassInfo = this.getCommonClassInfo(objectClass);
        List<CommonFieldInfo> commonFieldInfoList = commonClassInfo.getCommonFieldInfoList();

        for(int i = 0; i < commonFieldInfoList.size();i++){
            CommonFieldInfo fieldInfo = commonFieldInfoList.get(i);
            if(fieldName.equals(fieldInfo.getFieldName())){
                commonFieldInfo = fieldInfo;
                break;
            }
        }

        if(commonFieldInfo == null){
            log.info("字段匹配失败！" + objectClass.getName() + "中不包含" + fieldName + "字段");
        }

        return commonFieldInfo;
    }

    /**
     * 递归转换Map中的key为驼峰式命名
     * @param convertedList 已转换的集合
     * @param map map
     * @return 转换后的map
     */
    private Map<String, Object> innerConvertMapKeyFromUnderlineToUpperCase(List<Object> convertedList, Map map){
        convertedList.add(map);
        Map<String,Object> newMap = new HashMap<>();
        Iterator<Map.Entry<Object, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Object, Object> entry = iterator.next();
            if(!(entry.getKey() instanceof String)){
                // 跳过非String的key
                continue;
            }
            String key = GlobalFunc.toString(entry.getKey());
            Object value = entry.getValue();
            String newKey = SqlUtil.convertToUppercase(key);
            // 如果value是map，则递归转换
            if(!convertedList.contains(value) && (value instanceof Map)){
                Map valueMap = (Map) value;
                value = this.innerConvertMapKeyFromUnderlineToUpperCase(convertedList, valueMap);
            }
            // 如果转换驼峰成功，且旧map有一个相同的key，比如原来的map就有user_id和userId，则抛出运行时异常
            if(!key.equals(newKey) && map.containsKey(newKey)){
                throw(new RuntimeException("转换驼峰失败，map中已存在名称为[" + newKey + "]的key！"));
            }
            newMap.put(newKey,value);
        }
        return newMap;
    }
}
