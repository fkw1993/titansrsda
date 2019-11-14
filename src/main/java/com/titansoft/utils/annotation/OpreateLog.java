package com.titansoft.utils.annotation;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * @Author: Kevin
 * @Date: 2019/8/17 9:46
 */
@Target({METHOD, TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface OpreateLog {
    String description() default "";
}
