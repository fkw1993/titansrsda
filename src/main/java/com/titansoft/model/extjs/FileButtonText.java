package com.titansoft.model.extjs;

/**
 * 文件按钮文本域定义
 * 
 * @author lsw
 * @data 2010/08/16
 */
public class FileButtonText extends ButtonText {
	private String hiddenName;

	private String hiddenValue;

	private String separator;

	public FileButtonText() {
		super();
		setXtype("filebuttontext");
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
	 * @param text
	 *            按钮文字
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 * @param hiddenName
	 *            隐藏域名
	 * @param hiddenValue
	 *            隐藏域值
	 * @param separator
	 *            分隔符
	 */
	public FileButtonText(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon, Boolean isCommonitem ,
			Integer maxLength, Boolean allowBlank, String text, String scope,
			String handler, String hiddenName, String hiddenValue,
			String separator ,String regex,String regexText) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon,isCommonitem , maxLength,
				allowBlank, text, scope, handler , regex, regexText);
		setXtype("filebuttontext");
		setHiddenName(hiddenName);
		setHiddenValue(hiddenValue);
		setSeparator(separator);
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
	 * @param text
	 *            按钮文字
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 * @param hiddenName
	 *            隐藏域名
	 * @param hiddenValue
	 *            隐藏域值
	 * @param separator
	 *            分隔符
	 */
	public FileButtonText(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,
			Integer maxLength, Boolean allowBlank, String text, String scope,
			String handler, String hiddenName, String hiddenValue,
			String separator ,String regex,String regexText) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon ,maxLength,
				allowBlank, text, scope, handler , regex, regexText);
		setXtype("filebuttontext");
		setHiddenName(hiddenName);
		setHiddenValue(hiddenValue);
		setSeparator(separator);
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
	 * @param text
	 *            按钮文字
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 * @param hiddenName
	 *            隐藏域名
	 * @param hiddenValue
	 *            隐藏域值
	 * @param separator
	 *            分隔符
	 */
	public FileButtonText(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,
			Integer maxLength, Boolean allowBlank, String text, String scope,
			String handler, String hiddenName, String hiddenValue,
			String separator ) {
		super(fieldLabel, name, value, readOnly, isLine, isCommon ,maxLength,
				allowBlank, text, scope, handler );
		setXtype("filebuttontext");
		setHiddenName(hiddenName);
		setHiddenValue(hiddenValue);
		setSeparator(separator);
	}
	
	


	public String getHiddenName() {
		return hiddenName;
	}

	public void setHiddenName(String hiddenName) {
		this.hiddenName = hiddenName;
	}

	public String getHiddenValue() {
		return hiddenValue;
	}

	public void setHiddenValue(String hiddenValue) {
		this.hiddenValue = hiddenValue;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}
}
