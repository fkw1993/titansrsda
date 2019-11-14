package com.titansoft.model.extjs;

import com.titansoft.utils.annotation.JSON;

/**
 * 按钮文本项定义
 * 
 * @author lsw
 * @data 2010/07/16
 */
public class ButtonText extends TextField {
	private String text;

	@JSON(symbol = "")
	private String scope;

	@JSON(symbol = "")
	private String handler;

	public ButtonText() {
		super();
		setXtype("buttontext");
	}

	/**
	 * @param fieldLabel
	 *            描述
	 * @param name
	 *            字段名
	 * @param value
	 *            字段值
	 * @param readOnly
	 *            只读
	 * @param isLine
	 *            换行
	 * @param isCommon
	 *            常用项
	 * @param maxLength
	 *            最大文本长度
	 * @param allowBlank
	 *            允许空白
	 * @param text
	 *            按钮文字
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 */
	public ButtonText(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,Boolean isCommonitem ,
			Integer maxLength, Boolean allowBlank, String text, String scope,
			String handler ,String regex,String regexText) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon,isCommonitem , maxLength,
				allowBlank , regex, regexText);
		setXtype("buttontext");
		setText(text);
		setScope(scope);
		setHandler(handler);
	}
	
	/**
	 * @param fieldLabel
	 *            描述
	 * @param name
	 *            字段名
	 * @param value
	 *            字段值
	 * @param readOnly
	 *            只读
	 * @param isLine
	 *            换行
	 * @param isCommon
	 *            常用项
	 * @param maxLength
	 *            最大文本长度
	 * @param allowBlank
	 *            允许空白
	 * @param text
	 *            按钮文字
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 */
	public ButtonText(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,
			Integer maxLength, Boolean allowBlank, String text, String scope,
			String handler ,String regex,String regexText) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon, maxLength,
				allowBlank , regex, regexText);
		setXtype("buttontext");
		setText(text);
		setScope(scope);
		setHandler(handler);
	}
	
	/**
	 * @param fieldLabel
	 *            描述
	 * @param name
	 *            字段名
	 * @param value
	 *            字段值
	 * @param readOnly
	 *            只读
	 * @param isLine
	 *            换行
	 * @param isCommon
	 *            常用项
	 * @param maxLength
	 *            最大文本长度
	 * @param allowBlank
	 *            允许空白
	 * @param text
	 *            按钮文字
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 */
	public ButtonText(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,
			Integer maxLength, Boolean allowBlank, String text, String scope,
			String handler ) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon, maxLength,
				allowBlank );
		setXtype("buttontext");
		setText(text);
		setScope(scope);
		setHandler(handler);
	}


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}
}
