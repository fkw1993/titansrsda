package com.titansoft.utils.util.extjs;

import com.titansoft.model.VO;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PoManager {
	/**
	 * Method 通过一个类名及属性名则返回一个值
	 * 
	 * @param fieldName
	 *            //字段名
	 * @param
	 *            //类名
	 * @return result 返回字段值
	 */
	public static Object getPoFieldValue(String fieldName, Object po) throws Exception {
		Class poClass = Class.forName(po.getClass().getName());
		fieldName = fieldName;
		String methodName = fieldName.trim().substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		String setMethod = "set" + methodName;
		String getMethod = "get" + methodName;
		Method meth = null;
		String fieldType = "string";
		Field field = null;
		Class partypes[] = new Class[1];
		Object fieldNameValue = null;
		try {
			field = poClass.getField(fieldName);
		} catch (Exception er) {
			field = null;
		}
		if (field != null) {
			fieldType = field.getType().getName().trim();
			if (fieldType.trim().toLowerCase().lastIndexOf("string") >= 0) {
				partypes[0] = field.getType().getName().getClass();
				meth = poClass.getMethod(getMethod, null);
				fieldNameValue = meth.invoke(po, null);
			} else if (fieldType.trim().toLowerCase().equalsIgnoreCase("int")) {
				partypes[0] = Integer.TYPE;
				meth = poClass.getMethod(getMethod, null);
				fieldNameValue = meth.invoke(po, null);
				fieldNameValue = String.valueOf(fieldNameValue);
			} else if (fieldType.trim().toLowerCase().lastIndexOf("integer") >= 0) {
				partypes[0] = Integer.class;
				meth = poClass.getMethod(getMethod, null);
				fieldNameValue = meth.invoke(po, null);
				fieldNameValue = String.valueOf(fieldNameValue);
			} else if (fieldType.trim().toLowerCase().lastIndexOf("double") >= 0) {
				partypes[0] = Double.TYPE;
				meth = poClass.getMethod(getMethod, null);
				fieldNameValue = meth.invoke(po, null);
				fieldNameValue = String.valueOf(fieldNameValue);
			} else if (fieldType.trim().toLowerCase().lastIndexOf("date") >= 0) {
				partypes[0] = java.util.Date.class;
				meth = poClass.getMethod(getMethod, null);
				fieldNameValue = meth.invoke(po, null);
				fieldNameValue = String.valueOf(fieldNameValue)
						.substring(0, 10);
			} else if (fieldType.trim().toLowerCase().lastIndexOf("long") >= 0) {
				partypes[0] = Long.TYPE;
				meth = poClass.getMethod(getMethod, null);
				fieldNameValue = meth.invoke(po, null);
				fieldNameValue = String.valueOf(fieldNameValue);
			}
		} else {
			meth = poClass.getMethod(getMethod, null);
			fieldNameValue = meth.invoke(po, null);
			fieldNameValue = String.valueOf(fieldNameValue);
		}
		if (fieldNameValue == null || fieldNameValue.equals("null"))
			fieldNameValue = "";
		return fieldNameValue;
	}
	
	/**
	 * Method 通过一个对象及属性设置值
	 * 
	 * @param fieldName
	 *            //字段名
	 * @param fieldvalue
	 *            //字段值
	 * @param
	 *            //对象
	 * @return result 返回字段值
	 */
	public static Object setPoFieldValue(String fieldName, String fieldvalue, Object po)
			throws Exception {
		Class poClass = Class.forName(po.getClass().getName());
		String methodName = fieldName.trim().substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		String setMethod = "set" + methodName;
		String getMethod = "get" + methodName;
		Method meth = null;
		String fieldType = "string";
		Field field = null;
		Class partypes[] = new Class[1];
		Object fieldNameValue = fieldvalue;
		Field fieldlist[] = poClass.getDeclaredFields();
		for (int i = 0; i < fieldlist.length; i++) {
			field = fieldlist[i];
			if (field.getName().equalsIgnoreCase(fieldName)) {
				if (field != null) {
					fieldType = field.getType().getName().trim();
					if (fieldType.trim().toLowerCase().lastIndexOf("string") >= 0) {
						if (!(fieldNameValue == null || fieldNameValue
								.toString().trim().equalsIgnoreCase("null"))) {
							// 设属性值
							partypes[0] = field.getType().getName().getClass();
							meth = poClass.getMethod(setMethod, partypes);
							meth.invoke(po, fieldNameValue);
						}
					} else if (fieldType.trim().toLowerCase().equalsIgnoreCase(
							"int")) {
						if (!(fieldNameValue == null || fieldNameValue
								.toString().trim().equalsIgnoreCase("null"))) {
							// 设属性值
							partypes[0] = Integer.TYPE;
							meth = poClass.getMethod(setMethod, partypes);
							meth.invoke(po, fieldNameValue);
						}
					} else if (fieldType.trim().toLowerCase().lastIndexOf(
							"integer") >= 0) {
						if (!(fieldNameValue == null || fieldNameValue
								.toString().trim().equalsIgnoreCase("null"))) {
							// 设属性值
							partypes[0] = Integer.class;
							meth = poClass.getMethod(setMethod, partypes);
							meth.invoke(po, fieldNameValue);
						}
					} else if (fieldType.trim().toLowerCase().lastIndexOf(
							"double") >= 0) {
						if (!(fieldNameValue == null || fieldNameValue
								.toString().trim().equalsIgnoreCase("null"))) {
							// 设属性值
							partypes[0] = Double.TYPE;
							meth = poClass.getMethod(setMethod, partypes);
							meth.invoke(po, fieldNameValue);
						}
					} else if (fieldType.trim().toLowerCase().lastIndexOf(
							"date") >= 0) {
						if (!(fieldNameValue == null || fieldNameValue
								.toString().trim().equalsIgnoreCase("null"))) {
							// 设属性值
							partypes[0] = java.util.Date.class;
							meth = poClass.getMethod(setMethod, partypes);
							meth.invoke(po, fieldNameValue);
						}
					} else if (fieldType.trim().toLowerCase().lastIndexOf(
							"long") >= 0) {
						if (!(fieldNameValue == null || fieldNameValue
								.toString().trim().equalsIgnoreCase("null"))) {
							// 设属性值
							partypes[0] = Long.TYPE;
							meth = poClass.getMethod(setMethod, partypes);
							meth.invoke(po, fieldNameValue);
						}
					}
				}
				break;
			}
		}
		return po;
	}
	
	/**
	 * Method 通过一个对象名及属性名则返回该属性类型
	 * 
	 * @param fieldName
	 *            //字段名
	 * @param
	 *            //对象名
	 * @return result 返回该属性类型
	 */
	public static Object getPoFieldType(String fieldName, Object po) throws Exception {
		Class poClass = Class.forName(po.getClass().getName());
		String fieldType = "string";
		Field field = null;
		try {
			field = poClass.getDeclaredField(fieldName);
		} catch (Exception er) {
			field = null;
		}
		if (field != null) {
			fieldType = field.getType().getName().trim();
			// 属性名如下： string int integer double date long
			if (fieldType.trim().toLowerCase().lastIndexOf("string") >= 0)
				fieldType = "string";
			else if (fieldType.trim().toLowerCase().equalsIgnoreCase("int"))
				fieldType = "int";
			else if (fieldType.trim().toLowerCase().lastIndexOf("integer") >= 0)
				fieldType = "integer";
			else if (fieldType.trim().toLowerCase().lastIndexOf("double") >= 0)
				fieldType = "double";
			else if (fieldType.trim().toLowerCase().lastIndexOf("date") >= 0)
				fieldType = "date";
			else if (fieldType.trim().toLowerCase().lastIndexOf("long") >= 0)
				fieldType = "long";
		}
		return fieldType;
	}
	
	private Object mapToObject(Map map,Object object) throws Exception
	{
		 Set<String> key = map.keySet();
	        for (Iterator it = key.iterator(); it.hasNext();) {
	            String s = (String) it.next();
	            //System.out.println(map.get(s));
	            setPoFieldValue(s, map.get(s).toString(), object);
	        }
		return object;
	}
	
	public static Map<String,String> objectToMap(Object object) throws Exception
	{
		Map<String,String> map = new HashMap<String,String>();
		Class poClass = Class.forName(object.getClass().getName());
		for(Field field : poClass.getDeclaredFields())
		{
			if(field.getType().equals(String.class))
			{
				try {
					map.put(object.getClass().getSimpleName().toLowerCase() + "." + field.getName().toLowerCase(), getPoFieldValue(field.getName().toLowerCase(), object)
									+ "");
				} catch (Exception e) {
				}
			}
			
		}
		return map;
	}
	
	 /**
	 * 字符转换对象
	 * 
	 *
    */
  public static Object PoToVo(Object po)
      throws Exception
  {
      VO vo = new VO();
      Class poClass = po.getClass();
      Field fieldlist[] = poClass.getDeclaredFields();
      Object values = null;
      String methname = "";
      for(int i = 0; i < fieldlist.length; i++)
      {
          Field fld = fieldlist[i];
          if(!fld.getName().trim().equals("serialVersionUID"))
          {
              methname = fld.getName().trim().substring(0, 1).toUpperCase() + fld.getName().trim().substring(1);
              Method meth = poClass.getMethod("get" + methname.trim(), null);
              values = meth.invoke(po, null);
              vo.put(fld.getName().trim(), values);
          }
      }

      return vo;
  }
  
  /**
   * vo
   * @param vo
   * @param object
   * @return
   * @throws Exception
   */
  public Object voToObject(VO vo,Object object) throws Exception
  {
		 Set<String> key = vo.keySet();
	        for (Iterator it = key.iterator(); it.hasNext();) {
	            String s = (String) it.next();
	            String values = vo.get(s)==null?"":vo.get(s).toString();
	            setPoFieldValue(s, values, object);
	        }
		return object;
  }
}
