package com.titansoft.utils.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * sql注入过滤有害字符
 *
 * @Author: Kevin
 * @Date: 2019/8/31 16:11
 */
public class SqlFilter {
    private static final Logger logger = LoggerFactory.getLogger(SqlFilter.class);

    /**
     * SQL注入过滤
     *
     * @param str 待验证的字符串
     */
    public static String CheckSql(String str)  {
        if ("".equals(str)) {
            return null;
        }
        // 去掉'|"|;|\字符
       // str = str.replace("'", "");
        str = str.replace("\"", "");
        str = str.replace(";", "");
        str = str.replace("\\", "");
        // 转换成小写
        String newstr = str.toLowerCase();
        // 非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alert",
                "create", "drop"};
        // 判断是否包含非法字符
        for (String keyword : keywords) {
            if (newstr.contains(keyword)) {
               logger.error("SQL注入检测到包含非法字符:"+str);
               return "";
            }
        }
        return str;
    }
}
