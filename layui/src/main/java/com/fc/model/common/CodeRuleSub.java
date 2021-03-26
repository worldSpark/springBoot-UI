package com.fc.model.common;

import com.fc.annotation.Column;
import com.fc.annotation.FieldName;
import com.fc.annotation.ModelName;
import com.fc.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

/**
 * @Description:    编号规则维护实体(子表)
 * @Author:         zhangxh
 * @CreateDate:     2019/1/18 9:02
 * @Version:        1.0
 */

@Data
@ModelName(name = "编号规则维护子表")
@Table(name="wp_code_rules_sub")
public class CodeRuleSub {

    @FieldName(name = "主键id")
    @Column(name = "id",type = MySqlTypeConstant.VARCHAR, isNull = false, isKey = true)
    private String id;

    @FieldName(name = "规则id")
    @Column(name = "pk_wp_code_rules",type = MySqlTypeConstant.VARCHAR,length = 256)
    private String pkWpCodeRules;

    @FieldName(name = "类型")
    @Column(name = "type",type = MySqlTypeConstant.VARCHAR,length = 256)
    private String type;// 日期、表中字段、流水号、字符

    @FieldName(name = "规则排序")
    @Column(name = "order_no",type = MySqlTypeConstant.VARCHAR,length = 1)
    private String orderNo;

    @FieldName(name = "默认值")
    @Column(name = "default_value",type = MySqlTypeConstant.VARCHAR,length = 256)
    private String defaultValue;
}
