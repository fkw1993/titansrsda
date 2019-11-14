package com.titansoft.model.extjs;

import com.titansoft.utils.annotation.JSON;

/**
 * 下拉列表文本域定义
 * 
 * @author lsw
 * @data 2010/07/16
 */
public class ComboText extends TextField {
	@JSON(symbol = "")
	private String store;

	public ComboText() {
		super();
		setXtype("combotext");
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
	 * @param stroe
	 *            数据仓库
	 */
	public ComboText(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon, Boolean isCommonitem ,
			Integer maxLength, Boolean allowBlank, String store ,String regex,String regexText) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon, isCommonitem, maxLength,
				allowBlank , regex, regexText);
		setXtype("combotext");
		setStore(store);
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
	 * @param stroe
	 *            数据仓库
	 */
	public ComboText(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,
			Integer maxLength, Boolean allowBlank, String store ,String regex,String regexText) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon, maxLength,
				allowBlank , regex, regexText);
		setXtype("combotext");
		setStore(store);
	}

	public String getStore() {
		return store;
	}

	public void setStore(String stroe) {
		this.store = stroe;
	}
}
