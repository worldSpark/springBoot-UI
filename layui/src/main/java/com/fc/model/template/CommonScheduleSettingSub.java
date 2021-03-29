package com.fc.model.template;

import com.fc.annotation.Column;
import com.fc.annotation.FieldName;
import com.fc.annotation.ModelName;
import com.fc.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

/**
 * 待办设置子表
 * @author luoy
 * @date 2020年1月28日20:12:38
 * @version 1.0.0
 */
@Data
@ModelName(name = "待办设置子表")
@Table(name="wp_common_schedule_setting_sub")
public class CommonScheduleSettingSub {

    @FieldName(name = "主键")
    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, isKey = true)
    private String id;

    @FieldName(name = "待办设置主表")
    @Column(name = "pk_wp_common_schedule_setting", type = MySqlTypeConstant.VARCHAR, length = 255)
    private String pkWpCommonScheduleSetting;

    @FieldName(name = "模块名")
    @Column(name = "model_name", type = MySqlTypeConstant.VARCHAR, length = 1024)
    private String modelName;

    // 1置顶、2隐藏
    @FieldName(name = "模块设置")
    @Column(name = "model_setting", type = MySqlTypeConstant.VARCHAR,  length = 255)
    private String modelSetting;

}
