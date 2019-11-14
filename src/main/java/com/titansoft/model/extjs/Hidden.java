package com.titansoft.model.extjs;

/**
 * 隐藏项定义
 * 
 * @author lsw
 * @data 2010/07/13
 */
public class Hidden extends AbstractField {
	public Hidden() {
		setXtype("hidden");
	}

	/**
	 * @param name
	 *            字段名
	 * @param value
	 *            字段值
	 */
	public Hidden(String name, String value) {
		super("hidden", "", name, value, false, false, true,true);
	}
}
