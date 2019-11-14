package com.titansoft.model.extjs;

/**
 * 单选框定义
 * 
 * @author lsw
 * @data 2010/07/13
 */
public class Radio extends Checkbox {
	public Radio() {
		setXtype("radio");
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
	 * @param checked
	 *            是否选中
	 */
	public Radio(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon, Boolean isCommonitem , Boolean checked) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon,isCommonitem, checked);
		setXtype("radio");
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
	 * @param checked
	 *            是否选中
	 */
	public Radio(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,  Boolean checked) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon, checked);
		setXtype("radio");
	}
}
