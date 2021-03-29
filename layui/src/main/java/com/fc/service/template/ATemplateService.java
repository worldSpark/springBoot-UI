package com.fc.service.template;


import com.fc.common.domain.CommonProcessedConfig;
import com.fc.common.domain.CommonScheduleData;
import com.fc.model.template.ATemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Description 实体类模板接口
 * @author xxx
 * @date 2020年1月6日09:59:31
 * @version 1.0.0
 */
public interface ATemplateService {

    /**
     * 新增一条记录
     * @param aTemplate 模板
     * @return count
     */
    int insert(ATemplate aTemplate);

    /**
     * 更新一条记录
     * @param aTemplate 模板
     * @return count
     */
    int update(ATemplate aTemplate);

    /**
     * 查询一条记录
     * @param id 模板id
     * @return ATemplate
     */
    ATemplate getById(String id);

    /**
     * 删除一条记录
     * @param id 模板id
     * @return count
     */
    int delete(String id);

    /**
     * 分页获取模板信息
     * @param request request
     * @return data
     */
    Map<String, Object> getATemplateList(HttpServletRequest request);

    /**
     * 获得动态下拉多选框数据
     * @return list
     */
    List<Map<String, Object>> getDynamicData();

    /**
     * 检查重复项
     * @param id 主键id
     * @param normalInput 普通输入框
     * @return true重复 false不重复
     */
    boolean checkRepeat(String id, String normalInput);

    /**
     * 保存数据
     * @param request request
     * @return data
     */
    Map<String, Object> save(HttpServletRequest request);

    // ---------------------------------- 待办接口开始 ----------------------------------
    /**
     * 常态待办数量接口，直接写count语句，不要返回list.size()
     * 方法名随意，主要有待办注解即可
     * @return count
     */
    int getScheduleCount();

    /**
     * 常态待办数据接口
     * 方法名随意，主要有待办注解即可
     * @return list
     */
    List<CommonScheduleData> getScheduleData();

    /**
     * 应急待办数量接口，直接写count语句，不要返回list.size()
     * 方法名随意，主要有待办注解即可
     * @return count
     */
    int getScheduleCountOfEmergency();

    /**
     * 应急待办数据接口
     * 方法名随意，主要有待办注解即可
     * @return list
     */
    List<CommonScheduleData> getScheduleDataOfEmergency();
    // ---------------------------------- 待办接口结束 ----------------------------------
    // ---------------------------------- 已办接口开始 ----------------------------------
    /**
     * 常态、应急已办查询模板接口，
     * 方法名随意，主要有已办注解即可，常态、应急也可以分开（注意注解上的：systemStates）
     * @return config
     */
    CommonProcessedConfig getProcessedConfig();

    /**
     * 常态已办easyUI数据接口
     * 方法名随意，主要有已办注解即可
     * 只允许且必须有request、page、row按顺序摆放的三个参数!
     * @param request request
     * @param page 第n页
     * @param rows 每页多少条
     * @return data
     */
    Map<String, Object> getProcessedDataGrid(HttpServletRequest request, int page, int rows);

    /**
     * 应急已办easyUI数据接口
     * 方法名随意，主要有已办注解即可
     * 只允许且必须有request、page、row按顺序摆放的三个参数!
     * @param request request
     * @param page 第n页
     * @param rows 每页多少条
     * @return data
     */
    Map<String, Object> getProcessedDataGridOfEmergency(HttpServletRequest request, int page, int rows);
    // ---------------------------------- 已办接口结束 ----------------------------------
}
