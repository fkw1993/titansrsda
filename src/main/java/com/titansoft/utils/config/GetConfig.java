package com.titansoft.utils.config;

/**
 *
 * 读取db.properties配置信息
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class GetConfig {

	static private GetConfig instance;

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
			instance = new GetConfig();
		return instance.getConfigInfomation(key);

	}

}
