package com.titansoft.utils.config;

/**
 * @author YLC
 * 读取db.properties 文件信息
 */

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class SystemConfig {

    static private SystemConfig instance;

    public static Map<String, String> configMap = new HashMap<String, String>();
    //	public static String globallanguagevalues = "归档管理∪歸檔管理∩上传挂接包∪上傳掛接包";
    //定义一个存储前端数据的静态变量
    public static String globallanguagevalues;

    public String getConfigInfomation(String key) {
        InputStream is = getClass().getResourceAsStream("/db.properties");
        Properties dbProps = new Properties();
        try {
            dbProps.load(is);
            return dbProps.get(key).toString();
        } catch (IOException e) {
            System.err.println("不能读取属性文件请确保db.properties在CLASSPATH指定的路径中");
            return "";
        } catch (NullPointerException ex) {
            return "";
        }

    }

    public static String getValue(String key) {
        if (instance == null)
            instance = new SystemConfig();
        if (configMap.containsKey(key)) {
            return configMap.get(key);
        } else {
            return instance.getConfigInfomation(key);
        }
    }
}
