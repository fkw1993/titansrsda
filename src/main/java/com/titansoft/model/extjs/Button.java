package com.titansoft.model.extjs;

/**
 * 按钮定义
 * 
 * @author lsw
 * @data 2010/07/16
 */
public class Button extends AbstractButton {
	public Button() {
	}

	/**
	 * @param text
	 *            描述
	 * @param id
	 *            标识
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 * @param isLine
	 *            换行
	 */
	public Button(String text, String id, String scope, String handler,
			Boolean isLine, String iconCls) {
		super(text, id, scope, handler, isLine);
		super.setIconCls(iconCls);
	}
	
	/**
	 * @param text
	 *            描述
	 * @param id
	 *            标识
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 * @param isLine
	 *            换行
	 */
	public Button(String text, String id, String scope, String handler,
			Boolean isLine) {
		super(text, id, scope, handler, isLine);
	}

	/**
	 * @param text
	 *            描述
	 * @param id
	 *            标识
	 * @param type
	 *            按钮类型
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 * @param isLine
	 *            换行
	 */
	public Button(String text, String id, String type, String scope,
			String handler, Boolean isLine) {
		super(text, id, type, scope, handler, isLine);
	}

	/**
	 * @param xtype
	 *            按钮扩展类型
	 * @param text
	 *            描述
	 * @param id
	 *            标识
	 * @param type
	 *            按钮类型
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 * @param isLine
	 *            换行
	 */
	public Button(String xtype, String text, String id, String type,
			String scope, String handler, Boolean isLine) {
		super(xtype, text, id, type, scope, handler, isLine);
	}
}
