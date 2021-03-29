package com.fc.model.template;

import com.fc.annotation.Column;
import com.fc.annotation.FieldName;
import com.fc.annotation.ModelName;
import com.fc.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

/**
 * 待办设置
 * @author luoy
 * @date 2020年1月28日20:12:38
 * @version 1.0.0
 */
@Data
@ModelName(name = "待办设置")
@Table(name="wp_common_schedule_setting")
public class CommonScheduleSetting {

    @FieldName(name = "主键")
    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, isKey = true)
    private String id;

    @FieldName(name = "用户id")
    @Column(name = "pk_wp_user", type = MySqlTypeConstant.VARCHAR, length = 50)
    private String pkWpUser;

    // 1常态、2应急
    @FieldName(name = "系统状态")
    @Column(name = "system_state", type = MySqlTypeConstant.VARCHAR, length = 50)
    private String systemState;

}
