package codeGenerator;

/**
 * @Description 自动生成器常量
 * @author luoy
 * @version 1.0.0
 * @Date 2020年3月9日09:09:09
 **/
public class CGConstants {

    // 默认版本号
    public static final String DEFAULT_VERSION = "1.0.0";

    // jsp相对路径（相对于java文件夹）
    public static final String JSP_PACKAGE_PATH = "../webapp/WEB-INF/jsp";

    // 新增修改方式 - 窗口
    public static int ADD_OR_EDIT_TYPE_WINDOW = 1;
    // 新增修改方式 - 跳转
    public static int ADD_OR_EDIT_TYPE_URL = 2;

    // 查询条件方式 - 普通字符查询
    public static int SEARCH_TYPE_NORMAL = 1;
    // 查询条件方式 - 数字范围查询
    public static int SEARCH_TYPE_NUMBER_AREA = 2;
    // 查询条件方式 - 数字智能查询（支持输入：>10,>10且<100，等于10）
    public static int SEARCH_TYPE_NUMBER_SMART = 3;
    // 查询条件方式 - 整数范围查询
    public static int SEARCH_TYPE_INT_AREA = 4;
    // 查询条件方式 - 日期范围查询
    public static int SEARCH_TYPE_DATE_AREA = 5;
    // 查询条件方式 - 固定下拉框单选查询
    public static int SEARCH_TYPE_SELECT_SINGLE = 6;
    // 查询条件方式 - 固定下拉框多选查询
    public static int SEARCH_TYPE_SELECT_MULTIPLE = 7;
}
