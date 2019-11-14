package com.titansoft.model.extjs;

public class Label extends AbstractField {
	public Label() {
		setXtype("label");
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
	 * @param isLine
	 *            换行
	 * @param isCommon
	 *            常用项
	 */
	public Label(String fieldLabel, String name, String value, Boolean isLine,
			Boolean isCommon ,Boolean isCommonitem) {
		super("label", fieldLabel, name, value, true, isLine, isCommon ,isCommonitem);
	}
}
