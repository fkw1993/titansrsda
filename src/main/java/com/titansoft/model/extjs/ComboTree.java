package com.titansoft.model.extjs;

/**
 * 下拉列表定义
 * 
 * @author lsw
 * @data 2010/07/13
 */
public class ComboTree extends Field {
	private String displayField;

	private String valueField = "";

	private String dataUrl;

	private String valueText;
	
	private String linkfield;
	
	private boolean editable;
	public ComboTree() {
		setXtype("combotree");
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
	 * @param dataUrl
	 *            数据地址
	 */
	public ComboTree(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,Boolean isCommonitem,
			Integer maxLength, Boolean allowBlank, String dataUrl) {
		super("combotree", fieldLabel, name, value, readOnly, isLine, isCommon, isCommonitem,
				maxLength, allowBlank);
		setDataUrl(dataUrl);
	}

	
	/**aliyu 增加的构造方法（多一个editable字段）
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
	 * @param dataUrl
	 *            数据地址
	 */
	public ComboTree(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,
			Integer maxLength, Boolean allowBlank,Boolean editable, String dataUrl) {
		super("combotree", fieldLabel, name, value, readOnly, isLine, isCommon,
				maxLength, allowBlank);
		setEditable(editable);
		setDataUrl(dataUrl);
	}
	/**
	 * 
	 * @param fieldLabel
	 * @param name
	 * @param value
	 * @param readOnly
	 * @param isLine
	 * @param isCommon
	 * @param maxLength
	 * @param allowBlank
	 * @param dataUrl
	 */
	public ComboTree(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,
			Integer maxLength, Boolean allowBlank,String dataUrl) {
		super("combotree", fieldLabel, name, value, readOnly, isLine, isCommon,
				maxLength, allowBlank);
		setDataUrl(dataUrl);
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
	 * @param dataUrl
	 *            数据地址
	 */
	public ComboTree(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,Boolean isCommonitem,
			Integer maxLength, Boolean allowBlank, String displayField,
			String valueField, String dataUrl) {
		this(fieldLabel, name, value, readOnly, isLine, isCommon, isCommonitem,maxLength,
				allowBlank, dataUrl);
		setDisplayField(displayField);
		setValueField(valueField);
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
	 * @param dataUrl
	 *            数据地址
	 */
	public ComboTree(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,
			Integer maxLength, Boolean allowBlank, String displayField,
			String valueField,Boolean editable, String dataUrl) {
		this(fieldLabel, name, value, readOnly, isLine, isCommon,maxLength,
				allowBlank,editable, dataUrl);
		setDisplayField(displayField);
		setValueField(valueField);
	}

	public String getDisplayField() {
		return displayField;
	}

	public void setDisplayField(String displayField) {
		this.displayField = displayField;
	}

	public String getValueField() {
		return valueField;
	}

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	public String getDataUrl() {
		return dataUrl;
	}

	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}

	public String getValueText() {
		return valueText;
	}

	public void setValueText(String valueText) {
		this.valueText = valueText;
	}

	public String getLinkfield() {
		return linkfield;
	}

	public void setLinkfield(String linkfield) {
		this.linkfield = linkfield;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	
}
