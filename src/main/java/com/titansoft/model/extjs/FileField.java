package com.titansoft.model.extjs;

/**
 * 文件项定义
 * 
 * @author lsw
 * @data 2010/07/13
 */
public class FileField extends TextField {
	public FileField() {
		super();
		setInputType("file");
	}

	/**
	 * @param fieldLabel
	 *            描述
	 * @param name
	 *            字段名
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
	public FileField(String fieldLabel, String name, Boolean readOnly,
			Boolean isLine, Boolean isCommon, Boolean isCommonitem ,Integer maxLength,
			Boolean allowBlank ,String regex,String regexText) {
		super(fieldLabel, name, null, readOnly, isLine, isCommon,isCommonitem,  maxLength,
				allowBlank , regex, regexText);
		setInputType("file");
	}
	
	/**
	 * @param fieldLabel
	 *            描述
	 * @param name
	 *            字段名
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
	public FileField(String fieldLabel, String name, Boolean readOnly,
			Boolean isLine, Boolean isCommon, Integer maxLength,
			Boolean allowBlank ,String regex,String regexText) {
		super(fieldLabel, name, null, readOnly, isLine, isCommon,  maxLength,
				allowBlank , regex, regexText);
		setInputType("file");
	}
}
