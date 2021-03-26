package com.fc.annotation;

import java.lang.annotation.*;

/**
 * @Description 实体类注释注解，为获取操作对象注解。
 * @author fug
 * @since 2018-10-15 14:40
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModelName {
    String name();
}
