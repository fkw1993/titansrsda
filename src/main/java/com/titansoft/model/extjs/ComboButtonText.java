package com.titansoft.model.extjs;

import com.titansoft.utils.annotation.JSON;

/**
 * 下拉列表按钮文本域定义
 * 
 * @author lsw
 * @data 2010/08/10
 */
public class ComboButtonText extends ButtonText {
	@JSON(symbol = "")
	private String store;
	
	private String storeUrl;

	public ComboButtonText() {
		super();
		setXtype("combobuttontext");
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
	 * @param text
	 *            按钮文字
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 */
	public ComboButtonText(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon, Boolean isCommonitem ,
			Integer maxLength, Boolean allowBlank, String store, String text,
			String scope, String handler ,String regex,String regexText) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon, isCommonitem , maxLength,
				allowBlank, text, scope, handler , regex, regexText);
		setXtype("combobuttontext");
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
	 * @param text
	 *            按钮文字
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 */
	public ComboButtonText(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,
			Integer maxLength, Boolean allowBlank, String store, String text,
			String scope, String handler ,String regex,String regexText) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon , maxLength,
				allowBlank, text, scope, handler , regex, regexText);
		setXtype("combobuttontext");
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
	 * @param storeUrl
	 *            数据仓库Url
	 * @param text
	 *            按钮文字
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 */
	public ComboButtonText(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon, Boolean isCommonitem ,
			Integer maxLength, Boolean allowBlank, String storeUrl, String text,
			String scope, String handler, Boolean hold ,String regex,String regexText) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon, isCommonitem , maxLength,
				allowBlank, text, scope, handler, regex, regexText);
		setXtype("combobuttontext");
		setStoreUrl(storeUrl);
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
	 * @param storeUrl
	 *            数据仓库Url
	 * @param text
	 *            按钮文字
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 */
	public ComboButtonText(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon, 
			Integer maxLength, Boolean allowBlank, String storeUrl, String text,
			String scope, String handler, Boolean hold ,String regex,String regexText) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon , maxLength,
				allowBlank, text, scope, handler, regex, regexText);
		setXtype("combobuttontext");
		setStoreUrl(storeUrl);
	}

	public String getStore() {
		return store;
	}

	public void setStore(String stroe) {
		this.store = stroe;
	}
	
	public String getStoreUrl() {
		return storeUrl;
	}

	public void setStoreUrl(String storeUrl) {
		this.storeUrl = storeUrl;
	}
}
