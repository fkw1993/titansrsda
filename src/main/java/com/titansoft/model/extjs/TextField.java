package com.titansoft.model.extjs;

/**
 * 文本项定义
 * 
 * @author lsw
 * @data 2010/07/13
 */
public class TextField extends Field {
	private String inputType;
	

	public TextField() {
		setXtype("textfield");
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
	 */
	public TextField(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,Boolean isCommonitem ,
			Integer maxLength, Boolean allowBlank,String regex,String regexText) {
		super("textfield", fieldLabel, name, value, readOnly, isLine, isCommon,isCommonitem ,
				maxLength, allowBlank);

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
	 */
	public TextField(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon ,
			Integer maxLength, Boolean allowBlank,String regex,String regexText) {
		super("textfield", fieldLabel, name, value, readOnly, isLine, isCommon,
				maxLength, allowBlank);

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
	 */
	public TextField(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon ,
			Integer maxLength, Boolean allowBlank) {
		super("textfield", fieldLabel, name, value, readOnly, isLine, isCommon,
				maxLength, allowBlank);

	}
	
	/**
	 * 
	 * @param controltype 控件名称
	 * @param fieldLabel
	 * @param name
	 * @param value
	 * @param readOnly
	 * @param isLine
	 * @param isCommon
	 * @param maxLength
	 * @param allowBlank
	 */
	public TextField(String controltype,String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon ,
			Integer maxLength, Boolean allowBlank) {
		super(controltype, fieldLabel, name, value, readOnly, isLine, isCommon,
				maxLength, allowBlank);

	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}


}
