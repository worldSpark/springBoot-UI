package com.fc.common.domain;

/**
 * @Description 通用待办常量
 * @author luoy
 * @Date 2020年1月22日10:44:58
 * @version 1.0.0
 * */
public class CommonScheduleConstants {

    // 模块默认名称
    public static final String COMMON_SCHEDULE_DEFAULT_MODEL_NAME = "其它";

    // 操作方式：不操作
    public static final int OPERATION_TYPE_NONE = 0;
    // 操作方式：新框架标签页
    public static final int OPERATION_TYPE_TAB = 1;
    // 操作方式：新浏览器页面
    public static final int OPERATION_TYPE_LABEL = 2;
    // 操作方式：layer窗口
    public static final int OPERATION_TYPE_WINDOW = 3;

    // 数据类型：统计
    public static final int DATA_TYPE_COUNT = 0;
    // 数据类型：数据
    public static final int DATA_TYPE_DATA = 1;

    // 系统类型：常态
    public static final String SYSTEM_STATE_NORMAL = "1";
    // 系统类型：应急
    public static final String SYSTEM_STATE_EMERGENCY = "2";

    // 排序类型：不排序
    public static final int ORDER_TYPE_NONE = 0;
    // 排序类型：正序
    public static final int ORDER_TYPE_ASC = 1;
    // 排序类型：倒叙
    public static final int ORDER_TYPE_DESC = 2;

    // --------------------------------- 模块名称开始 ---------------------------------
    // 所有待办，js中需要手动同步
    public static final String NAME_ALL_SCHEDULE = "所有待办";
    // 其它模块，建议以NAME_开头，好区分
    public static final String NAME_TEMPLATE = "模板";


    // --------------------------------- 模块名称结束 ---------------------------------
}
