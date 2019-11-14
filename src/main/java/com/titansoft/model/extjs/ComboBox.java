package com.titansoft.model.extjs;

import com.titansoft.utils.annotation.JSON;

/**
 * 下拉列表定义
 * 
 * @author lsw
 * @data 2010/07/13
 */
public class ComboBox extends Field {
	private String displayField;

	private String valueField;

	private String storeUrl;

	private String storeRoot;
	
	private boolean editable;
	
	@JSON(symbol = "")
	private String store;

	public ComboBox() {
		setXtype("combo");
		setStoreUrl("list");
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
	 * @param store
	 *            数据地址
	 */
	public ComboBox(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,
			Integer maxLength, Boolean allowBlank, String store) {
		super("combo", fieldLabel, name, value, readOnly, isLine, isCommon,
				maxLength, allowBlank);
		//setEditable(true);
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
	 * @param store
	 *            数据地址
	 */
	public ComboBox(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,
			Integer maxLength, Boolean allowBlank,Boolean editable,  String store) {
		super("combo", fieldLabel, name, value, readOnly, isLine, isCommon,
				maxLength, allowBlank);
		setEditable(editable);
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
	 * @param displayField
	 *            显示字段
	 * @param valueField
	 *            值字段
	 * @param storeUrl
	 *            数据地址
	 */
	public ComboBox(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,
			Integer maxLength, Boolean allowBlank, String displayField,
			String valueField, String storeUrl) {
		this(fieldLabel, name, value, readOnly, isLine, isCommon, maxLength,
				allowBlank, displayField, valueField, storeUrl, "list");
		setEditable(true);
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
	 * @param displayField
	 *            显示字段
	 * @param valueField
	 *            值字段
	 * @param storeUrl
	 *            数据地址
	 * @param storeRoot
	 *            根节点
	 */
	public ComboBox(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,
			Integer maxLength, Boolean allowBlank, String displayField,
			String valueField, String storeUrl, String storeRoot) {
		super("combo", fieldLabel, name, value, readOnly, isLine, isCommon,
				maxLength, allowBlank);
		setDisplayField(displayField);
		setValueField(valueField);
		setStoreUrl(storeUrl);
		setStoreRoot(storeRoot);
		setEditable(true);
	}

	public String getDisplayField() {
		return displayField;
	}

	public void setDisplayField(String displayField) {
		this.displayField = displayField;
	}

	public String getStoreUrl() {
		return storeUrl;
	}

	public void setStoreUrl(String storeUrl) {
		this.storeUrl = storeUrl;
	}

	public String getValueField() {
		return valueField;
	}

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	public String getStoreRoot() {
		return storeRoot;
	}

	public void setStoreRoot(String storeRoot) {
		this.storeRoot = storeRoot;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public boolean getEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
