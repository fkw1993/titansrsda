package com.titansoft.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类、方法和字段属性定义
 * 
 * @author lsw
 * @data 2010/07/16
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JSON {
	String symbol() default "\"";
}
