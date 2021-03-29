package com.fc.common.conf;

/**
 * @program: springbootui
 * @description:
 * @author: Mr.Wan
 * @create: 2021-03-25 10:01
 **/
public class CommonConfig {
    // 最后一次更新时间
    public static final String LAST_UPDATE_TIME = "2019年3月20日11:33:35";

    // 数据库名称
    public static final String DATA_BASE_NAME = "springbootv2";
    // 数据库账号
    public static final String DATA_USER_NAME = "root";
    // 数据库密码
    public static final String DATA_PASSWORD = "123456";
    // 项目名称
    public static final String PROJECT_NAME = "";
    // 数据库表字段名
    public static final String DATA_BASE_COLUMN = "TABLE_CATALOG";
    // @Column中字段自增长名称
    public static final String COLUMN_AUTO_INCREASE_NAME = "isIdentity";
    // 数据库text类型对应的名称
    public static final String TEXT_NAME_OF_SQL = "TEXT";
    // 数据库改变字段的关键字
    public static final String KEY_WORD_OF_CHANGE_COLUMN = "alter";
    // 数据库中，字符长度的函数
    public static final String FUNC_LENGTH_OF_WORD = "LEN";

    // 系统日志保存时间（单位：小时） 建议超过一天
    public static final int SYSTEM_LOG_RETAIN_TIME = 24 * 7;

    //------↓↓↓-----用户登陆锁定配置------↓↓↓-----//

    //用户登陆密码错误临时锁定可输入次数
    public static final Integer LOGIN_USER_LOCK_TEMPORARY_NUM =5;

    //用户登陆密码错误永久锁定可输入次数
    public static final Integer LOGIN_USER_LOCK_PERPETUAL_NUM =10;

    //用户登陆密码错误临时锁定时间（秒）
    public static final Integer LOGIN_USER_LOCK_TEMPORARY_TIME =1200;

    //用户登陆密码错误锁定设置的用户截断字符串拼接方式（对应下面所拼接的锁定标识）
    public static final String LOGIN_USER_LOCK_SUBSTRING = "#";

    //用户登陆密码错误计数+拼接字符串
    public static final String LOGIN_USER_LOCK_COUNT = "#_LOGIN_USER_LOCK_COUNT";

    //用户登陆密码错误锁定+拼接字符串
    public static final String LOGIN_USER_IS_LOCK = "#_LOGIN_USER_IS_LOCK";

    //用户登陆密码错误锁定设置下次可登录时间+拼接字符串
    public static final String LOGIN_USER_LOCK_NEXT_TIME = "#_LOGIN_USER_LOCK_NEXT_TIME";

    //------↑↑↑-----用户登陆锁定配置------↑↑↑-----//
    //redis用户菜单授权
    public static final String LOGIN_USER_RESOURCES_JURISDICTION = "RESOURCES";

    public static final String FILE_ROOT_PATH = "D:\\upload";


}
