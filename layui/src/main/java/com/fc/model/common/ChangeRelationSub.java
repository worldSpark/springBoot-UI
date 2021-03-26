package com.fc.model.common;

import com.fc.annotation.Column;
import com.fc.annotation.FieldName;
import com.fc.annotation.ModelName;
import com.fc.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

/**
* @Description:    转换系数维护子表
* @Author:         zhangxh
* @CreateDate:     2019/1/24 18:02
* @Version:        1.0
*/
@Data
@ModelName(name = "转换系数维护子表")
@Table(name="wp_change_relation_sub")
public class ChangeRelationSub {
    @FieldName(name = "主键id")
    @Column(name = "id",type = MySqlTypeConstant.VARCHAR, isNull = false, isKey = true)
    private String id;

    @FieldName(name = "转换系数id")
    @Column(name = "pk_wp_change_relation",type = MySqlTypeConstant.VARCHAR,length = 256)
    private String pkWpChangeRelation;

    @FieldName(name = "基础单位id")
    @Column(name = "pk_wp_logistics_unit_base",type = MySqlTypeConstant.VARCHAR,length = 256)
    private String pkWpLogisticsUnitBase;

    @FieldName(name = "转换单位id")
    @Column(name = "pk_wp_logistics_unit_change",type = MySqlTypeConstant.VARCHAR,length = 256)
    private String pkWpLogisticsUnitChange;

    @FieldName(name = "转换比例")
    @Column(name = "change_proportion",type = MySqlTypeConstant.VARCHAR,length = 256)
    private String changeProportion;

    @FieldName(name = "排序")
    @Column(name = "order_no",type = MySqlTypeConstant.VARCHAR,length = 256)
    private String orderNo;

}
