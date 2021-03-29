package com.fc.annotation;


import com.fc.common.domain.CommonScheduleConstants;
import com.fc.common.domain.SystemCategoryEnum;

import java.lang.annotation.*;

/**
 * 用于配置待办接口
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommonScheduleInterface {

    // 模板名称
    String modelName() default CommonScheduleConstants.COMMON_SCHEDULE_DEFAULT_MODEL_NAME;

    // 数据类型，count、data，见CommonScheduleConstants
    int dataType();

    // 系统类型，常态、应急，为空则归类到常态中，见CommonScheduleConstants
    // 可以即为常态，也为应急
    String[] systemStates() default CommonScheduleConstants.SYSTEM_STATE_NORMAL;

    // 系统分类：内部、外部、学校。。。，默认内部
    SystemCategoryEnum[] systemCategory() default SystemCategoryEnum.CATEGORY_INSIDE;

    // 自动根据时间排序，默认倒序，见CommonScheduleConstants
    int autoOrder() default CommonScheduleConstants.ORDER_TYPE_DESC;
}
