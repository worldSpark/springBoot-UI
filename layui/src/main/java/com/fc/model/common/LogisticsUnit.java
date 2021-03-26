package com.fc.model.common;


import com.fc.annotation.Column;
import com.fc.annotation.FieldName;
import com.fc.annotation.ModelName;
import com.fc.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

/**
* @Description:    商品单位维护
* @Author:         zhangxh
* @CreateDate:     2019/1/22 11:01
* @Version:        1.0
*/
@Data
@ModelName(name = "商品单位维护")
@Table(name="wp_logistics_unit")
public class LogisticsUnit {
    @FieldName(name = "主键id")
    @Column(name = "id",type = MySqlTypeConstant.VARCHAR, isNull = false, isKey = true)
    private String id;

    @FieldName(name = "单位")
    @Column(name = "unit_name",type = MySqlTypeConstant.VARCHAR,length = 256)
    private String unitName;

    @FieldName(name = "是否启用")
    @Column(name = "is_enable",type = MySqlTypeConstant.VARCHAR,length = 256)
    private String isEnable;
}
