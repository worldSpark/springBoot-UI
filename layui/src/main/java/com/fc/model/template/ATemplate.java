package com.fc.model.template;

import com.fc.annotation.Column;
import com.fc.annotation.FieldName;
import com.fc.annotation.ModelName;
import com.fc.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

/**
 * @Description 实体类模板
 * @author xxx
 * @date 2020年1月6日09:59:31
 * @version 1.0.0
 */
@Data
@ModelName(name = "实体类模板")
@Table(name="wp_a_template")
public class ATemplate {

    @FieldName(name = "主键id")
    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, isKey = true, length = 50)
    private String id;

    @FieldName(name = "普通输入框")
    @Column(name = "normal_input", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String normalInput;

    @FieldName(name = "整数输入框")
    @Column(name = "int_input", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String intInput;

    @FieldName(name = "整数输入框开始")
    @Column(name = "int_input_start", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String intInputStart;

    @FieldName(name = "整数输入框结束")
    @Column(name = "int_input_end", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String intInputEnd;

    // yyyy
    @FieldName(name = "年")
    @Column(name = "date_input_y", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String dateInputY;

    // yyyy-MM
    @FieldName(name = "年月")
    @Column(name = "date_input_ym", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String dateInputYM;

    // yyyy-MM-dd
    @FieldName(name = "年月日")
    @Column(name = "date_input_ymd", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String dateInputYMD;

    // yyyy-MM-dd HH:mm:ss
    @FieldName(name = "年月日时分秒")
    @Column(name = "date_input_ymdhms", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String dateInputYMDHMS;

    // yyyy-MM-dd
    @FieldName(name = "日期输入框开始")
    @Column(name = "date_input_start", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String dateInputStart;

    // yyyy-MM-dd
    @FieldName(name = "日期输入框结束")
    @Column(name = "date_input_end", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String dateInputEnd;

    @FieldName(name = "下拉单选框")
    @Column(name = "normal_single_select", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String normalSingleSelect;

    @FieldName(name = "下拉多选框")
    @Column(name = "normal_multiple_select", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String normalMultipleSelect;

    @FieldName(name = "动态下拉多选框")
    @Column(name = "dynamic_normal_multiple_select", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String dynamicNormalMultipleSelect;

    @FieldName(name = "是否启用")
    @Column(name = "enabled", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String enabled;

    @FieldName(name = "人员选择框")
    @Column(name = "pk_wp_user", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String pkWpUser;

    @FieldName(name = "部门选择框")
    @Column(name = "pk_wp_department", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String pkWpDepartment;

    @FieldName(name = "单选框")
    @Column(name = "normal_single_check", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String normalSingleCheck;

    @FieldName(name = "多选框")
    @Column(name = "normal_multiple_check", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String normalMultipleCheck;

    @FieldName(name = "分组多选框")
    @Column(name = "group_normal_multiple_check", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String groupNormalMultipleCheck;

    @FieldName(name = "文本框")
    @Column(name = "normal_textarea", type = MySqlTypeConstant.VARCHAR, length = 4000)
    private String normalTextarea;
}
