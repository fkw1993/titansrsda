package com.titansoft.model.extjs;

/**
 * 文件项定义
 * 
 * @author lsw
 * @data 2010/07/13
 */
public class FileImageField extends TextField {
	public FileImageField() {
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
	public FileImageField(String fieldLabel, String name, String value,Boolean readOnly,
			Boolean isLine, Boolean isCommon, Boolean isCommonitem ,Integer maxLength,
			Boolean allowBlank ,String regex,String regexText) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon,isCommonitem,  maxLength,
				allowBlank , regex, regexText);
		setXtype("msFieldFileImage");
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
	public FileImageField(String fieldLabel, String name, Boolean readOnly,
			Boolean isLine, Boolean isCommon, Integer maxLength,
			Boolean allowBlank ,String regex,String regexText) {
		super(fieldLabel, name, null, readOnly, isLine, isCommon,  maxLength,
				allowBlank , regex, regexText);
		setXtype("msFieldFileImage");
		setInputType("file");
	}
}
