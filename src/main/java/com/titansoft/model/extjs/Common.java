package com.titansoft.model.extjs;

/**
 * 公共信息处理
 * 
 * @author lsw
 * @data 2010/07/16
 */
public abstract class Common {
	public static String toJson(String listName, java.util.List list){
		String json = "";
		if (list != null) {
			for (Object field : list) {
				if (!json.equals(""))
					json += ",";
				json += toJson(field);
			}
		}
		return "\"" + listName + "\":[" + json + "]";
	}

	public static String toJson(Object target) {
		String json = "", temp;
		Class clazz = target.getClass();
		while (!clazz.getName().equals("java.lang.Object")) {
			for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
				temp = Common.fieldToJson(field, target);
				if (temp != null) {
					if (!json.equals(""))
						json += ",";
					json += temp;
				}
			}
			clazz = clazz.getSuperclass();
		}
		return "{" + json + "}";
	}

	private static String fieldToJson(java.lang.reflect.Field field,
			Object target) {
		field.setAccessible(true);
		Object value = null;
		try {
			value = field.get(target);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (value == null) return null;
		
		String typeName = field.getType().getName();
		String fieldName = "\"" + field.getName() + "\":";
		String fieldValue = "";
		com.titansoft.utils.annotation.JSON json = field.getAnnotation(com.titansoft.utils.annotation.JSON.class);
		String symbol = json == null ? "\"" : json.symbol();

		fieldValue = value.toString();
		if (typeName.equals("java.lang.String")) {
			fieldValue = symbol
					+ (symbol.equals("") ? fieldValue : fieldValue.replace(
							symbol, "\\" + symbol)) + symbol;
		}
		if(field.getName().trim().equalsIgnoreCase("regex"))
			fieldValue = value.toString();
		return fieldName + fieldValue;
	}
}
