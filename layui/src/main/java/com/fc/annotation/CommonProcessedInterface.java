package com.fc.annotation;


import com.fc.common.domain.CommonProcessedConstants;
import com.fc.common.domain.SystemCategoryEnum;

import java.lang.annotation.*;

/**
 * 用于配置已办接口
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommonProcessedInterface {
    // 模板名称
    String modelName() default CommonProcessedConstants.COMMON_PROCESSED_DEFAULT_MODEL_NAME;

    // 子模板名称（可以为空）
    String subModelName() default CommonProcessedConstants.COMMON_PROCESSED_DEFAULT_SUB_MODEL_NAME;

    // 子模板排序号，正序
    int subOrderNo() default 1;

    // 数据类型，select、data，见CommonProcessedConstants
    int dataType();

    // 系统类型，常态、应急，为空则归类到常态中，见CommonProcessedConstants
    // 可以即为常态，也为应急
    String[] systemStates() default CommonProcessedConstants.SYSTEM_STATE_NORMAL;

    // 系统分类：内部、外部、学校。。。，默认内部
    SystemCategoryEnum[] systemCategory() default SystemCategoryEnum.CATEGORY_INSIDE;
}
