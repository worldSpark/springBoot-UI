package com.fc.model.common;
import com.fc.annotation.Column;
import com.fc.annotation.FieldName;
import com.fc.annotation.ModelName;
import com.fc.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

/**
* @Description:    编号规则维护实体
* @Author:         zhangxh
* @CreateDate:     2019/1/18 9:02
* @Version:        1.0
*/

@Data
@ModelName(name = "编号规则维护")
@Table(name="wp_code_rules")
public class CodeRules {

    @FieldName(name = "主键id")
    @Column(name = "id",type = MySqlTypeConstant.VARCHAR, isNull = false, isKey = true)
    private String id;

    @FieldName(name = "模块名称")
    @Column(name = "modular_name",type = MySqlTypeConstant.VARCHAR,length = 256)
    private String modularName;

    @FieldName(name = "是否启用")
    @Column(name = "is_enable",type = MySqlTypeConstant.VARCHAR,length = 1)
    private String isEnable;

}
