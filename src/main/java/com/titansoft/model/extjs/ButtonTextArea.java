package com.titansoft.model.extjs;

/**
 * 按钮文本域定义
 * 
 * @author lsw
 * @data 2010/07/16
 */
public class ButtonTextArea extends ButtonText {
	private Integer rows;

	public ButtonTextArea() {
		super();
		setXtype("buttontextarea");
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
	 * @param rows
	 *            行数
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 */
	public ButtonTextArea(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon, Boolean isCommonitem ,
			Integer maxLength, Boolean allowBlank, String text, Integer rows,
			String scope, String handler ,String regex,String regexText) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon, isCommonitem , maxLength,
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
	 * @param text
	 *            按钮文字
	 * @param rows
	 *            行数
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 */
	public ButtonTextArea(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon, 
			Integer maxLength, Boolean allowBlank, String text, Integer rows,
			String scope, String handler ,String regex,String regexText) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon,  maxLength,
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
	 * @param text
	 *            按钮文字
	 * @param rows
	 *            行数
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 */
	public ButtonTextArea(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon, 
			Integer maxLength, Boolean allowBlank, String text, Integer rows,
			String scope, String handler ) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon,  maxLength,
				allowBlank, text, scope, handler );
		setXtype("buttontextarea");
		setRows(rows);
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}
}
