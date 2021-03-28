package com.fc.service.common;


import com.fc.model.logistics.CodeRuleSub;
import com.fc.model.logistics.CodeRules;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
* @Description:    编号规则维护service
* @Author:         zhangxh
* @CreateDate:     2019/1/18 9:11
* @Version:        1.0
*/
public interface CodeRulesService {

    void insert(CodeRules codeRules);

    void update(CodeRules codeRules);

    void insertSub(CodeRuleSub sub);

    void updateSub(CodeRuleSub sub);

    /**
     * @Description 获得主表数据
     * @param id id
     * @return codeRules
     */
    CodeRules getById(String id);

    /**
     * @Description 保存、更新规则
     * @param data json数据，前后带中括号
     * @return message
     */
    Map<String,Object> addOrUpdate(String data);

    /**
    * @Description:    分页查询列表数据
    * @Author:         zhangxh
    * @CreateDate:     2019/1/18 9:39
    * @Version:        1.0
    */
    Map getListData(HttpServletRequest request);

    /**
    * @Description:    根据id获取规则数据
    * @Author:         zhangxh
    * @CreateDate:     2019/1/18 14:00
    * @Version:        1.0
    */
    List<Map<String,Object>> getListById(String id);

    /**
    * @Description:    根据主表id获取子表数据
    * @Author:         zhangxh
    * @CreateDate:     2019/1/21 16:19
    * @Version:        1.0
    */
    List<CodeRuleSub> getSubList(String id);

    /**
    * @Description:    获取编号
    * @param:ruleName   编号规则名称
    * @param:columnName   编号列名
    * @param:objectClass   实体类class
    * @Author:         zhangxh
    * @CreateDate:     2019/1/20 16:17
    * @Version:        1.0
    */
    <T> String getNumber(String ruleName,String columnName,Class<T> objectClass);


    /**
     * 根据字段名内容获取编号
     * @param columnName 需要查询的字段名内容,如user_name
     * @param codeColumn 实体类的编号字段,如code_name
     * @param objectClass 实体类 如user.class
     * @return
     */
    String getContentByGenerateNumber(String columnName,String codeColumn,Class objectClass);
}
