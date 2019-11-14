package com.titansoft.model.extjs;

import com.titansoft.utils.annotation.JSON;

/**
 * 按钮文本域定义
 * 
 * @author lsw
 * @data 2010/07/16
 */
public class ButtonTwoTextArea extends ButtonText {
	private Integer rows;
	
	private String text2;

	@JSON(symbol = "")
	private String scope2;

	@JSON(symbol = "")
	private String handler2;

	public ButtonTwoTextArea() {
		super();
		setXtype("buttontwotextarea");
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
	 * @param rows
	 *            行数
	 * @param text
	 *            按钮文字
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 * @param text2
	 *            按钮文字2
	 * @param scope2
	 *            处理器作用范围2
	 * @param handler2
	 *            处理器2
	 */
	public ButtonTwoTextArea(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,  Boolean isCommonitem,
			Integer maxLength, Boolean allowBlank, Integer rows,
			String text, String scope, String handler,
			String text2, String scope2, String handler2 ,String regex,String regexText) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon, isCommonitem,maxLength,
				allowBlank, text, scope, handler , regex, regexText);
		setXtype("buttontextarea");
		setRows(rows);
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
	 * @param rows
	 *            行数
	 * @param text
	 *            按钮文字
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 * @param text2
	 *            按钮文字2
	 * @param scope2
	 *            处理器作用范围2
	 * @param handler2
	 *            处理器2
	 */
	public ButtonTwoTextArea(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,  
			Integer maxLength, Boolean allowBlank, Integer rows,
			String text, String scope, String handler,
			String text2, String scope2, String handler2 ,String regex,String regexText) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon,maxLength,
				allowBlank, text, scope, handler , regex, regexText);
		setXtype("buttontextarea");
		setRows(rows);
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getHandler2() {
		return handler2;
	}

	public void setHandler2(String handler2) {
		this.handler2 = handler2;
	}

	public String getScope2() {
		return scope2;
	}

	public void setScope2(String scope2) {
		this.scope2 = scope2;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}
}
