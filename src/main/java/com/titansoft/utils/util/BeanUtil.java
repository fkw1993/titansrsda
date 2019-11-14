package com.titansoft.utils.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Timestamp;

;

/**
 * @Author: Kevin
 * @Date: 2019/9/30 15:21
 */
public class BeanUtil {
    //使bean中为null的属性转换成空字符串
    public static Object nullToEmpty(Object o) {
        for (Field f : o.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.get(o) == null) { //判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
                    f.set(o, "");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return o;
    }

    /**
     * 通过po对象获取属性值
     * @param po
     * @param name
     * @param value
     */
    public static void setPOValue(Object po, String name, Object value) {
        try {
            Class<?> clazz = po.getClass();
            Field field = clazz.getDeclaredField(name);
            Class<?> clazzValue = field.getType();
            if (field != null) {
                Method m = po.getClass()
                        .getMethod(
                                "set" + name.substring(0, 1).toUpperCase()
                                        + name.substring(1),
                                new Class[] { clazzValue });
                if (m != null)
                    m.invoke(
                            po,
                            new Object[] { convertTypeObject(value, clazzValue) });
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 实体对象转成sql值
     * @param value
     * @param clazz
     * @return
     */
    public static Object convertTypeObject(Object value, Class<?> clazz) {
        if (value == null)
            return value;
        if (clazz == String.class) {
            if (!(value instanceof String))
                value = value.toString();
        } else if (clazz == long.class || clazz == Long.class) {
            if (!(value instanceof Long))
                value = Long.parseLong(value.toString());
        } else if (clazz == int.class || clazz == Integer.class) {
            if (!(value instanceof Integer))
                value = Integer.parseInt(value.toString());
        } else if (clazz == boolean.class || clazz == Boolean.class) {
            if (!(value instanceof Boolean))
                value = "1".equals(value.toString())
                        || "true".equalsIgnoreCase(value.toString());
        } else if (clazz == Date.class) {
            if (value instanceof Timestamp)
                value = new Date(((Timestamp) value).getTime());
            else if (value instanceof String)
                value = Date.valueOf(value.toString());
        } else if (clazz == Date.class) {

        }
        return value;
    }

    //判断对象属性是否都是空的
    public static boolean checkBeanIsNull(Object o) {
        boolean bool=true;
        for (Field f : o.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.get(o) != null&&!"".equals(f.get(o))) { //判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return bool;
    }
}
