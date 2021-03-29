package com.fc.service.common;


import com.fc.common.domain.CommonScheduleData;
import com.fc.model.template.CommonScheduleCatalog;
import com.fc.model.template.CommonScheduleSetting;

import java.util.List;
import java.util.Map;

/**
 * 通用待办接口
 * @author luoy
 * @Date 2020年1月22日11:25:27
 * @version 1.0.0
 */
public interface CommonScheduleService {

    /**
     * 加载所有接口，放置全局变量中
     */
    void loadInterfaceMap();

    /**
     * 获得待办目录，包括【所有待办】
     * @param systemState 系统状态
     * @return list
     */
    List<CommonScheduleCatalog> getScheduleCatalog(String systemState);

    /**
     * 获得待办个数
     * @param modelName 模块名
     * @param systemState 系统状态
     * @return list
     */
    int getScheduleCount(String modelName, String systemState);

    /**
     * 获得待办数据
     * @param modelName 模块名
     * @param systemState 系统状态
     * @return list
     */
    List<CommonScheduleData> getScheduleData(String modelName, String systemState);

    /**
     * 获得待办设置
     * @param id 设置id
     * @return setting
     */
    CommonScheduleSetting getScheduleSetting(String id);

    /**
     * 获得待办设置
     * @param userId 用户id
     * @param systemState 系统状态
     * @return setting
     */
    CommonScheduleSetting getScheduleSettingByUserId(String userId, String systemState);

    /**
     * 获得某个用户设置了置顶的模块名
     * @param settingId 用户设置id
     * @return setting
     */
    List<String> getRoofModelNameBySettingId(String settingId);

    /**
     * 获得某个用户设置了隐藏的模块名
     * @param settingId 用户设置id
     * @return setting
     */
    List<String> getHiddenModelNameBySettingId(String settingId);

    /**
     * 更新待办设置
     * @param commonScheduleSetting 待办设置
     * @return count
     */
    int updateCommonScheduleSetting(CommonScheduleSetting commonScheduleSetting);

    /**
     * 获得待办目录，【所有待办】除外
     * @param systemState 系统状态
     * @return list
     */
    List<String> getModelNameList(String systemState);

    /**
     * 置顶、取消置顶模块
     * @param userId 用户id
     * @param modelName 模块名
     * @param systemState 系统状态
     * @param type true置顶，false取消置顶
     * @return message
     */
    Map<String, Object> roofModel(String userId, String modelName, String systemState, boolean type);

    /**
     * 显示、隐藏模块
     * @param userId 用户id
     * @param modelName 模块名
     * @param systemState 系统状态
     * @param type true显示，false隐藏
     * @return message
     */
    Map<String, Object> toggleModel(String userId, String modelName, String systemState, boolean type);

    /**
     * 刷新待办数据
     * @param userId 用户id
     * @return true刷新成功，false刷新失败（用户不在线）
     */
    boolean refreshSchedule(String userId);
}
