package com.fc.model.common;

import com.fc.annotation.Column;
import com.fc.annotation.FieldName;
import com.fc.annotation.ModelName;
import com.fc.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

/**
* @Description:    转换系数维护
* @Author:         zhangxh
* @CreateDate:     2019/1/24 17:41
* @Version:        1.0
*/
@Data
@ModelName(name = "转换系数维护")
@Table(name="wp_change_relation")
public class ChangeRelation {

    @FieldName(name = "主键id")
    @Column(name = "id",type = MySqlTypeConstant.VARCHAR, isNull = false, isKey = true)
    private String id;

    @FieldName(name = "转换系数名称")
    @Column(name = "relation_name",type = MySqlTypeConstant.VARCHAR,length = 256)
    private String relationName;

    @FieldName(name = "是否启用")
    @Column(name = "is_enable",type = MySqlTypeConstant.VARCHAR,length = 1)
    private String isEnable;

}
