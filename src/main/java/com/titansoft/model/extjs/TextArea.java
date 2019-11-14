package com.titansoft.model.extjs;

/**
 * 文本域定义
 * 
 * @author lsw
 * @data 2010/07/13
 */
public class TextArea extends Field {
	private Integer rows;

	public TextArea() {
		setXtype("textarea");
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
	 */
	public TextArea(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon, Boolean isCommonitem ,
			Integer maxLength, Boolean allowBlank, Integer rows) {
		super("textarea", fieldLabel, name, value, readOnly, isLine, isCommon,isCommonitem ,
				maxLength, allowBlank);
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
	 */
	public TextArea(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon ,
			Integer maxLength, Boolean allowBlank, Integer rows) {
		super("textarea", fieldLabel, name, value, readOnly, isLine, isCommon ,
				maxLength, allowBlank);
		setRows(rows);
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}
}
