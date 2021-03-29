package com.fc.service.common;


import com.fc.common.domain.CommonClassInfo;
import com.fc.common.domain.SqlCondition;

import java.util.List;
import java.util.Map;

/**
 * @Description 通用service
 * 更新：
 *  1、支持转义功能，将value中的"'"→"''"                                                   by luoy 2018-11-26
 *  2、不再通过get和set方法获取字段名，直接取@Column上的值                                   by luoy 2018-12-28
 *  3、对象级别的操作不再默认id为主键，查找isKey=true为主键，但考虑有些表没有设置isKey=true，
 *     没有找到主键后默认以id为主键                                                        by luoy 2019-01-03
 *  4、增加了convertMapKeyToAnotherMapKey方法                                             by luoy 2019-01-06
 *  5、增加了update、updateSelective方法                                                  by luoy 2019-01-14
 *  6、增加了insertList方法                                                               by luoy 2019-01-15
 *  7、增加了updateList方法                                                               by luoy 2019-02-26
 *  7、增加了convertListToMap方法                                                         by luoy 2019-02-26
 *  8、增加了removeDuplicate方法                                                          by luoy 2019-02-26
 *  9、重载了deleteByTableNameAndId方法，支持传递多个id                                     by luoy 2019-03-03
 *  10、重载了convertMapKeyToAnotherMapKey方法，支持传递单个map                             by luoy 2019-03-28
 *  11、增加deleteByTableNameAndCondition方法                                             by luoy 2019-04-29
 *  12、重载了isColumnExist方法，支持List                                                  by luoy 2019-08-08
 *  13、调整了insertMap方法，只插入表中有的字段，无关字段会跳过                                by luoy 2019-09-10
 *  14、调整了convertMapKeyFromUnderlineToUpperCase，支持多层Map                           by luoy 2020-03-02
 *  14、调整了convertMapKeyFromUnderlineToUpperCase，支持多层Map                           by luoy 2020-03-02
 *  15、增加了orderByMap，支持list-Map排序                                                by luoy 2020-03-16
 * @author luoy
 * @Date 2018年07月26日 上午11:53:00
 * @version 1.0.0
 **/
public interface CommonService {

    //--------------------------------对象数据处理↓--------------------------------

    /**
     * @Description 新增一条记录
     * @param object 对象
     * @return count
     */
    int insert(Object object);

    /**
     * @Description 新增多条记录
     * @param list 数据
     * @return count
     */
    <T> int insertList(List<T> list);

    /**
     * @Description 新增多条记录
     * @param list 数据
     * @param size >0 每次插入多少条
     * 1、如果为空或异常值，重设为100
     * 2、建议最大值不超过1000
     * @return count
     */
    <T> int insertList(List<T> list, Integer size);

    /**
     * @Description 删除一条记录
     * @param object 对象
     * @return count
     */
    int delete(Object object);

    /**
     * @Description 删除一条记录
     * @param objectClass 类对象 如User.class
     * @param id id
     * @return count
     */
    int delete(Class objectClass,String id);

    /**
     * @Description 删除多条记录
     * @param objectClass 类对象 如User.class
     * @param ids id
     * @return count
     */
    int delete(Class objectClass,List<String> ids);

    /**
     * @Description 删除多条记录
     * @param objectClass 类对象 如User.class
     * @param ids id
     * @return count
     */
    int delete(Class objectClass,String[] ids);

    /**
     * @Description 删除多条记录
     * @param objectClass 类对象 如User.class
     * @param sqlCondition sql查询条件
     * @return count
     */
    int delete(Class objectClass, SqlCondition sqlCondition);

    /**
     * @Description 更新一条记录
     * @param object 对象
     * @return count
     */
    int update(Object object);

    /**
     * @Description 有条件的更新多条记录
     * @param object 对象
     * @param sqlCondition 条件，如果没有任何条件则不更新
     * @return count
     */
    int update(Object object, SqlCondition sqlCondition);

    /**
     * @Description 有选择的更新一条记录，对象中字段为null则不更新
     * 注意：
     * 1.如果是int类型，则必须设置值，不然会变成0
     * @param object 对象
     * @return count
     */
    int updateSelective(Object object);

    /**
     * @Description 有条件有选择的更新多条记录
     * @param object 对象
     * @param sqlCondition 条件，如果没有任何条件则不更新
     * @return count
     */
    int updateSelective(Object object, SqlCondition sqlCondition);

    /**
     * @Description 批量更新数据
     * @param list 数据
     * @return count
     */
    <T> int updateList(List<T> list);

    /**
     * @Description 批量更新数据
     * @param list 数据
     * @param size >0 每次更新多少条
     * 1、如果为空或异常值，重设为100
     * 2、建议最大值不超过1000
     * @return count
     */
    <T> int updateList(List<T> list, int size);

    /**
     * @Description 有选择的批量更新数据
     * @param list 数据
     * @return count
     */
    <T> int updateListSelective(List<T> list);

    /**
     * @Description 有选择的批量更新数据
     * @param list 数据
     * @param size >0 每次更新多少条
     * 1、如果为空或异常值，重设为100
     * 2、建议最大值不超过1000
     * @return count
     */
    <T> int updateListSelective(List<T> list, int size);

    /**
     * @Description 通过id获得对象
     * @param objectClass 类对象
     * @param id id
     * @return 单个对象
     */
    <T> T getById(Class<T> objectClass,String id);

    /**
     * @Description 获得全部对象集合
     * @param objectClass 类对象
     * @return 对象数组
     */
    <T> List<T> getAll(Class<T> objectClass);

    /**
     * @Description 通过查询条件获得对象集合
     * @param objectClass 类对象
     * @param sqlCondition sql查询条件
     * @return 对象数组
     */
    <T> List<T> getByCondition(Class<T> objectClass, SqlCondition sqlCondition);

    /**
     * @Description 获得表中的某列
     * @param objectClass 类对象
     * @param columnName 列名
     * @return String数组
     */
    List<String> getUniqueColumn(Class objectClass,String columnName);

    /**
     * @Description 通过查询条件获得表中的某列
     * @param objectClass 类对象
     * @param columnName 列名
     * @param sqlCondition sql查询条件
     * @return String数组
     */
    List<String> getUniqueColumn(Class objectClass, String columnName, SqlCondition sqlCondition);

    /**
     * @Description 统计全部
     * @param objectClass 类对象
     * @return count
     */
    int getCount(Class objectClass);

    /**
     * @Description 通过查询条件统计对象个数
     * @param objectClass 类对象
     * @param sqlCondition sql查询条件
     * @return count
     */
    int getCount(Class objectClass, SqlCondition sqlCondition);

    /**
     * @Description 通过查询条件获得对象集合
     * @param objectClass 类对象
     * @param page 页码
     * @param rows 每页多少条
     * @param sqlCondition sql查询条件
     * @return map{rows:list<T>,total:long}
     */
    Map<String,Object> getByConditionWithEasyUI(Class objectClass, int page, int rows, SqlCondition sqlCondition);

    /**
     * @Description 组装对象
     * @param objectClass 类对象
     * @param map map
     * @return 单个对象
     */
    <T> T convertMapToObject(Class<T> objectClass,Map<String,Object> map);

    /**
     * @Description 组装对象集合
     * @param objectClass 类对象
     * @param mapList mapList
     * @return 对象集合
     */
    <T> List<T> convertMapListToObjectList(Class<T> objectClass,List<Map<String,Object>> mapList);

    //--------------------------------对象数据处理↑--------------------------------
    //--------------------------------Map数据处理↓--------------------------------

    /**
     * @Description 获得一条Map记录
     * @param tableName 表名
     * @param id id
     * @return map
     */
    Map<String,Object> getMapByTableNameAndId(String tableName,String id);

    /**
     * @Description 获得所有Map记录
     * @param tableName 表名
     * @param sqlCondition 条件
     * @return list
     */
    List<Map<String,Object>> getMapByTableNameAndSqlCondition(String tableName,SqlCondition sqlCondition);

    /**
     * @Description 新增一条记录
     * @param tableName 表名
     * @param map map
     * @return count
     */
    int insertMap(String tableName,Map<String,Object> map);

    /**
     * @Description 更新一条记录 默认id为主键
     * @param tableName 表名
     * @param map map
     * @return count
     */
    int updateMap(String tableName,Map<String,Object> map);

    /**
     * @Description 有选择性的更新一条记录 默认id为主键
     * @param tableName 表名
     * @param map map
     * @return count
     */
    int updateMapSelective(String tableName,Map<String,Object> map);

    //--------------------------------Map数据处理↑--------------------------------
    //--------------------------------表处理↓--------------------------------

    /**
     * @Description 判断表是否存在
     * @param tableName 表名
     * @return boolean
     */
    boolean isTableExist(String tableName);

    /**
     * @Description 判断表中字段是否存在
     * @param tableName 表名
     * @param columnName 字段名，可传多个
     * @return boolean true：空、数据库字段中有这些字段 false：任意传过来的字段在数据库中不存在
     */
    boolean isColumnExist(String tableName,String... columnName);

    /**
     * @Description 判断表中字段是否存在
     * @param tableName 表名
     * @param columnName 字段名集合
     * @return boolean true：空、数据库字段中有这些字段 false：任意传过来的字段在数据库中不存在
     */
    boolean isColumnExist(String tableName,List<String> columnName);

    /**
     * @Description 删除表
     * @param tableName 表名
     * @return boolean
     */
    boolean dropTable(String tableName);

    /**
     * @Description 获得数据库中的表
     * @param sqlCondition 查询条件
     * @return list
     */
    List<String> getTableByCondition(SqlCondition sqlCondition);

    /**
     * @Description 获得表中字段名
     * @param tableName tableName
     * @return list
     */
    List<String> getColumnByTableName(String tableName);

    /**
     * @Description 获得表中字段名
     * @param sqlCondition 查询条件
     * @return list
     */
    List<String> getColumnByCondition(SqlCondition sqlCondition);

    //--------------------------------表处理↑--------------------------------
    //--------------------------------其它↓--------------------------------

    /**
     * @Description 删除一条记录 默认id为主键
     * @param tableName 表名
     * @param id id
     * @return count
     */
    int deleteByTableNameAndId(String tableName,String id);

    /**
     * @Description 删除多条记录 默认id为主键
     * @param tableName 表名
     * @param idList id集合
     * @return count
     */
    int deleteByTableNameAndId(String tableName,List<String> idList);

    /**
     * @Description 根据条件删除
     * @param tableName 表名
     * @param sqlCondition 条件
     * @return count
     */
    int deleteByTableNameAndCondition(String tableName,SqlCondition sqlCondition);

    /**
     * @Description 转换Map中的key为驼峰式命名
     * @param map map
     * @return Map
     */
    Map<String,Object> convertMapKeyFromUnderlineToUpperCase(Map<String,Object> map);

    /**
     * @Description 转换Map中的key为驼峰式命名
     * @param mapList mapList
     * @return Map集合
     */
    List<Map<String,Object>> convertMapKeyFromUnderlineToUpperCase(List<Map<String,Object>> mapList);

    /**
     * @Description 验证sql是否可执行
     * @param sql sql
     * @return boolean
     */
    Boolean isSqlExecutable(String sql);

    /**
     * @Description 对象转换Map
     * @param object 对象
     * @return map
     */
    Map<String,Object> convertObjectToMap(Object object);

    /**
     * @Description 对象转换Map
     * @param list list对象
     * @return map
     */
    List<Map<String,Object>> convertObjectListToMapList(List list);

    /**
     * @Description 统计数据个数
     * @param tableName 表名
     * @return 数据个数
     */
    int getCount(String tableName);

    /**
     * @Description 通过查询条件统计对象个数
     * @param tableName 表名
     * @param sqlCondition sql查询条件
     * @return count
     */
    int getCount(String tableName, SqlCondition sqlCondition);

    /**
     * @Description 全能方法 把class转成方便使用的对象
     * @param objectClass 类对象
     * @return 类信息对象
     */
    CommonClassInfo getCommonClassInfo(Class<?> objectClass);

    /**
     * @Description 全能方法 把class转成方便使用的对象
     * @param objectClass 类对象
     * @param withOutAnnotation 忽略注解，没有注解的时候也能解析成功
     * @return 类信息对象
     */
    CommonClassInfo getCommonClassInfo(Class<?> objectClass, boolean withOutAnnotation);


    /**
     * @Description 将list中的某个字段转换成某张表中的字段
     * 具体用法：
     * 1、list<Map>中有key user_id,需要转换成user_name：
     *   list = convertMapKeyToAnotherMapKey(list,"wp_user","id","real_name","pk_wp_user","user_name");
     * @param list 查询出的list数据
     * @param tableName 表名
     * @param keyColumnName 字段名
     * @param targetColumnName 目标字段名
     * @param keyMapName list中需要被转换的key
     * @param targetMapName list被转换后
     * @return list
     */
    List<Map<String,Object>> convertMapKeyToAnotherMapKey(List<Map<String,Object>> list,String tableName,
                                                          String keyColumnName, String targetColumnName,
                                                          String keyMapName, String targetMapName);

    /**
     * @Description 将list中的某个字段转换成某张表中的字段
     * 1、list<Map>中有key 逗号拼接的user_id,需要转换成逗号拼接的user_name：
     *   list = convertMapKeyToAnotherMapKey(list,"wp_user","id","real_name","pk_wp_user","user_name",",");
     * @param list 查询出的list数据
     * @param tableName 表名
     * @param keyColumnName 字段名
     * @param targetColumnName 目标字段名
     * @param keyMapName list中需要被转换的key
     * @param targetMapName list被转换后
     * @param separator 分隔符
     * @return list
     */
    List<Map<String,Object>> convertMapKeyToAnotherMapKey(List<Map<String,Object>> list,String tableName,
                                                          String keyColumnName, String targetColumnName,
                                                          String keyMapName, String targetMapName,
                                                          String separator);

    /**
     * @Description 将map中的某个字段转换成某张表中的字段
     * @param map 查询出的map数据
     * @param tableName 表名
     * @param keyColumnName 字段名
     * @param targetColumnName 目标字段名
     * @param keyMapName list中需要被转换的key
     * @param targetMapName list被转换后
     * @return list
     */
    Map<String,Object> convertMapKeyToAnotherMapKey(Map<String,Object> map,String tableName,
                                                          String keyColumnName, String targetColumnName,
                                                          String keyMapName, String targetMapName);

    /**
     * @Description 将map中的某个字段转换成某张表中的字段
     * @param map 查询出的map数据
     * @param tableName 表名
     * @param keyColumnName 字段名
     * @param targetColumnName 目标字段名
     * @param keyMapName list中需要被转换的key
     * @param targetMapName list被转换后
     * @param separator 分隔符
     * @return list
     */
    Map<String,Object> convertMapKeyToAnotherMapKey(Map<String,Object> map,String tableName,
                                                          String keyColumnName, String targetColumnName,
                                                          String keyMapName, String targetMapName,
                                                          String separator);

    /**
     * @Description list to map,将对象中的某个字段作为map中的key
     * 注意：
     * 1、如果list数据中存在多个字段相同的数据，则以最后一个为准，注意数据丢失
     * 2、推荐keyName是不重复的标识符
     * 3、仅支持对象处理，不支持List<Map>
     * @param list 集合
     * @param keyName 主键名称
     * @return Map
     */
    <T> Map<String, T> convertListToMap(List<T> list, String keyName);

    /**
     * @Description 根据对象中的某个字段，移除相同数据
     * @param list 数据
     * @param fieldName 字段名称
     * @return count
     */
    <T> List<T> removeDuplicate(List<T> list, String fieldName);

    /**
     * @Description 根据对象中的某个字段，移除相同数据
     * @param list 数据
     * @param fieldName 字段名称
     * @param isCover true:list排后的数据覆盖排前的数据
     * @return count
     */
    <T> List<T> removeDuplicate(List<T> list, String fieldName, boolean isCover);

    /**
     * @Description 驼峰转换下划线，常用于实体类中的字段转表中的字段
     * 如 userName → user_name
     * @param fieldName 实体字段
     * @return 表字段名
     */
    String getColumnNameByFieldName(String fieldName);

    /**
     * @Description 获得注解上的值
     * @param object 需要取参数的对象
     * @param annotationClass 注解对象
     * @param name 注解中的参数
     * @return value
     */
     Object getAnnotationValue(Object object,Class annotationClass,String name);

    /**
     * @Description 按拼音排序
     * @param list string集合
     * @return list 排序后的集合
     */
    List<String> orderByPinYin(List<String> list);

    /**
     * @Description 将集合对象中的某个字段按拼音排序
     * @param list 对象集合
     * @param fieldName 存储中文的变量名，建议String类型
     * @return list 排序后的集合
     */
    <T> List<T> orderByPinYin(List<T> list, String fieldName);

    /**
     * @Description 将集合对象中的某个字段按已知集合排序
     * @param list 对象集合
     * @param fieldName 变量名，建议String类型
     * @param assistList 辅助集合
     * @return list 排序后的集合
     */
    <T> List<T> orderByAssistList(List<T> list, String fieldName, List<String> assistList);

    /**
     * @Description 将Map集合中的某个字段按已知集合排序
     * @param list Map集合
     * @param keyName 变量名，建议String类型
     * @param assistList 辅助集合
     * @return list 排序后的集合
     */
    List<Map<String, Object>> orderMapByAssistList(List<Map<String, Object>> list, String keyName, List<String> assistList);

    /**
     * @Description 将集合对象中的某个字段直接排序
     * @param list 对象集合
     * @param fieldName 变量名，建议String、int类型
     * @param isAsc true正序 false倒序
     * @return list 排序后的集合
     */
    <T> List<T> orderByField(List<T> list, String fieldName, boolean isAsc);

    /**
     * @Description 将集合List-Map中的某个字段直接排序
     * @param list 对象集合
     * @param fieldName 变量名，建议String、int类型
     * @param fieldType 字段排序方式，STRING(拼音排序)，INT(数字排序)
     * @param isAsc true正序 false倒序
     * @return list 排序后的集合
     */
    List<Map<String, Object>> orderByMap(List<Map<String, Object>> list, String fieldName, String fieldType, boolean isAsc);

    //--------------------------------其它↑--------------------------------

}
