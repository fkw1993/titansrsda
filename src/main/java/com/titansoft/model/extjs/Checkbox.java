package com.titansoft.model.extjs;

/**
 * 复选框定义
 * 
 * @author lsw
 * @data 2010/07/13
 */
public class Checkbox extends AbstractField {
	private Boolean checked;

	public Checkbox() {
		this.setXtype("checkbox");
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
	public Checkbox(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon, Boolean isCommonitem,Boolean checked) {
		super("checkbox", fieldLabel, name, value, readOnly, isLine, isCommon,isCommonitem);
		setChecked(checked);
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
	public Checkbox(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon, Boolean checked) {
		super("checkbox", fieldLabel, name, value, readOnly, isLine, isCommon);
		setChecked(checked);
	}


	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
}
