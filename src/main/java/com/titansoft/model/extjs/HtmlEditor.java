package com.titansoft.model.extjs;

/**
 * Html文本域定义
 * 
 * @author lsw
 * @data 2010/07/13
 */
public class HtmlEditor extends AbstractField {
	private Integer height;

	public HtmlEditor() {
		setXtype("htmleditor");
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
	 */
	public HtmlEditor(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon ,Boolean isCommonitem ) {
		this(fieldLabel, name, value, readOnly, isLine, isCommon,isCommonitem, null);
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
	 */
	public HtmlEditor(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon ) {
		super("htmleditor",fieldLabel, name, value, readOnly, isLine, isCommon, null);
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
	 * @param height
	 *            高度
	 */
	public HtmlEditor(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon, Boolean isCommonitem , Integer height) {
		super("htmleditor", fieldLabel, name, value, readOnly, isLine, isCommon ,isCommonitem);
		setHeight(height);
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
	 * @param height
	 *            高度
	 */
	public HtmlEditor(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon , Integer height) {
		super("htmleditor", fieldLabel, name, value, readOnly, isLine, isCommon);
		setHeight(height);
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
}
