package com.titansoft.model.extjs;

/**
 * 项定义
 * 
 * @author lsw
 * @data 2010/07/13
 */
public class Field extends AbstractField {
	private Integer maxLength;

	private Boolean allowBlank;

	public Field() {
	}

	/**
	 * @param xtype
	 *            域类型
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
	 * 
	 */
	public Field(String xtype, String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,Boolean isCommonitem,
			Integer maxLength, Boolean allowBlank) {
		super(xtype, fieldLabel, name, value, readOnly, isLine, isCommon,isCommonitem);
		setMaxLength(maxLength);
		setAllowBlank(allowBlank);
	}
	
	/**
	 * @param xtype
	 *            域类型
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
	 * 
	 */
	public Field(String xtype, String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,
			Integer maxLength, Boolean allowBlank) {
		super(xtype, fieldLabel, name, value, readOnly, isLine, isCommon);
		setMaxLength(maxLength);
		setAllowBlank(allowBlank);
	}

	public Boolean getAllowBlank() {
		return allowBlank;
	}

	public void setAllowBlank(Boolean allowBlank) {
		this.allowBlank = allowBlank;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
}
