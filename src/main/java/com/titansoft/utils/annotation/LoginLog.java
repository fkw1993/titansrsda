package com.titansoft.utils.annotation;

import java.lang.annotation.*;

/**登录登出日志注解
 * @Author: Kevin
 * @Date: 2019/8/15 17:48
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})//作用在参数和方法上
@Retention(RetentionPolicy.RUNTIME)//运行时注解
@Documented//表明这个注解应该被 javadoc工具记录
public @interface LoginLog {
    String description() default "";
}
