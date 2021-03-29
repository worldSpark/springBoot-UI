package com.fc.common.domain;

/**
 * @Description 通用已办常量
 * @author luoy
 * @Date 2020年2月09日10:55:22
 * @version 1.0.0
 * */
public class CommonProcessedConstants {

    // 模块默认名称
    public static final String COMMON_PROCESSED_DEFAULT_MODEL_NAME = "其它";
    // 模块默认子名称
    public static final String COMMON_PROCESSED_DEFAULT_SUB_MODEL_NAME = "未定义";

    // 操作方式和CommonScheduleConstants一致
    // 操作方式：不操作
    public static final int OPERATION_TYPE_NONE = 0;
    // 操作方式：新框架标签页
    public static final int OPERATION_TYPE_TAB = 1;
    // 操作方式：新浏览器页面
    public static final int OPERATION_TYPE_LABEL = 2;
    // 操作方式：layer窗口
    public static final int OPERATION_TYPE_WINDOW = 3;

    // 数据类型：查询模板
    public static final int DATA_TYPE_SELECT = 0;
    // 数据类型：数据
    public static final int DATA_TYPE_DATA = 1;

    // 系统类型：常态
    public static final String SYSTEM_STATE_NORMAL = "1";
    // 系统类型：应急
    public static final String SYSTEM_STATE_EMERGENCY = "2";

    // 查询单元类型：普通输入框
    public static final int SEARCH_TYPE_INPUT = 0;
    // 查询单元类型：combobox下拉单选框，不可编辑
    public static final int SEARCH_TYPE_SINGLE_COMBOBOX = 1;
    // 查询单元类型：combobox下拉多选框，不可编辑
    public static final int SEARCH_TYPE_MULTIPLE_COMBOBOX = 2;
    // 查询单元类型：单个日期输入框，yyyy-MM-dd
    public static final int SEARCH_TYPE_SINGLE_DATEBOX = 3;
    // 查询单元类型：两个日期输入框，yyyy-MM-dd，request要获取两个，在原字段尾部追加Start和End
    public static final int SEARCH_TYPE_MULTIPLE_DATEBOX = 4;

    // --------------------------------- 模块名称开始 ---------------------------------
    // 模块名称，建议以NAME_开头，子模块名用SUB_NAME_开头，好区分

    // 模板A
    public static final String NAME_TEMPLATE_A = "模板A";
    public static final String SUB_NAME_TEMPLATE_A_FIRST = "模板A1";
    public static final String SUB_NAME_TEMPLATE_A_SECOND = "模板A2";

    // 模板B
    public static final String NAME_TEMPLATE_B = "模板B";
    public static final String SUB_NAME_TEMPLATE_B_FIRST = "模板B1";
    public static final String SUB_NAME_TEMPLATE_B_SECOND = "模板B2";

    // --------------------------------- 模块名称结束 ---------------------------------
}
