package com.fc.annotation;

import java.lang.annotation.*;

/**
* @Description: 使用此注解可以在项目启动时sqlserver自动建表
 *                  锁定住不同服务器同一数据库的其它人通过sqlserver自动建表服务对该表的操作
 *        注解重启服务后生效
 *        使用此注解不可提交！！！
*                                   ！！！
 *                                    ！！！
 *                                       ！！！
 *  *
* @Author: gongwb
* @Date: 2019/3/21 17:53
*/
//表示注解加在接口、类、枚举等
@Target(ElementType.TYPE)
//VM将在运行期也保留注释，因此可以通过反射机制读取注解的信息
@Retention(RetentionPolicy.RUNTIME)
//将此注解包含在javadoc中
@Documented
//允许子类继承父类中的注解
@Inherited
public @interface PrivateTable {
    String value() default "";
}
