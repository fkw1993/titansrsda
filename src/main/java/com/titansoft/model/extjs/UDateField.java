package com.titansoft.model.extjs;

/**
 * 日期项定义
 * 
 * @author lsw
 * @data 2010/07/13
 */
public class UDateField extends DateField {
	public UDateField() {
		setXtype("udatefield");
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
	public UDateField(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon, Boolean isCommonitem ,
			Integer maxLength, Boolean allowBlank) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon,isCommonitem ,
				maxLength, allowBlank);
		setXtype("udatefield");
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
	public UDateField(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon, 
			Integer maxLength, Boolean allowBlank) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon ,
				maxLength, allowBlank);
		setXtype("udatefield");
	}
}
