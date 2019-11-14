package com.titansoft.model.extjs;

/**
 * 表单定义
 * 
 * @author lsw
 * @data 2010/07/16
 */
public abstract class AbstractForm {
	public static String toJson(java.util.List<AbstractField> fields,
			java.util.List<AbstractButton> buttons) {
		try {
			StringBuffer json = new StringBuffer("{\"success\":true");

			json.append(", " + AbstractField.toJson(fields));

			json.append(", " + AbstractButton.toJson(buttons));

			json.append("}");

			return json.toString();
		} catch (Throwable ex) {
			return "{\"success\":false, \"errors\":\"" + ex.getMessage()
					+ "\"}";
		}
	}
}
