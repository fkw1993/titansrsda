package com.titansoft.model.extjs;

/**
 * 数字项定义
 * 
 * @author lsw
 * @data 2010/07/13
 */
public class NumberField extends Field {
	private Integer decimalPrecision = 2;

	private Double minValue;

	private Double maxValue;

	public NumberField() {
		setXtype("numberfield");
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
	 */
	public NumberField(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,Boolean isCommonitem,
			Integer maxLength, Boolean allowBlank) {
		super("numberfield", fieldLabel, name, value, readOnly, isLine,
				isCommon, isCommonitem,maxLength, allowBlank);
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
	 */
	public NumberField(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,
			Integer maxLength, Boolean allowBlank) {
		super("numberfield", fieldLabel, name, value, readOnly, isLine,
				isCommon,maxLength, allowBlank);
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
	 * @param decimalPrecision
	 *            精确位数
	 * @param minValue
	 *            最大值
	 * @param maxValue
	 *            最小值
	 */
	public NumberField(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,Boolean isCommonitem,
			Integer maxLength, Boolean allowBlank, Integer decimalPrecision,
			Double minValue, Double maxValue) {
		super("numberfield", fieldLabel, name, value, readOnly, isLine,
				isCommon,isCommonitem, maxLength, allowBlank);
		setDecimalPrecision(decimalPrecision);
		setMinValue(minValue);
		setMaxValue(maxValue);
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
	 * @param decimalPrecision
	 *            精确位数
	 * @param minValue
	 *            最大值
	 * @param maxValue
	 *            最小值
	 */
	public NumberField(String fieldLabel, String name, String value,
			Boolean readOnly, Boolean isLine, Boolean isCommon,
			Integer maxLength, Boolean allowBlank, Integer decimalPrecision,
			Double minValue, Double maxValue) {
		super("numberfield", fieldLabel, name, value, readOnly, isLine,
				isCommon, maxLength, allowBlank);
		setDecimalPrecision(decimalPrecision);
		setMinValue(minValue);
		setMaxValue(maxValue);
	}
	
	public Integer getDecimalPrecision() {
		return decimalPrecision;
	}

	public void setDecimalPrecision(Integer decimalPrecision) {
		this.decimalPrecision = decimalPrecision;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}
}
