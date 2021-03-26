package com.fc.annotation;

import java.lang.annotation.*;

/**
 * @Description 自定义字段注解类
 *   该类为获取实体类属性上自定义注解，用于给全局变量field字段赋值,并使用反射取值。
 * @author fug
 * @since 2018-10-15 14:03
 */

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME )
public @interface FieldName {
    int sort() default 0 ;
    String name();
}
