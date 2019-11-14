/**
 * @(#) XsdObject
 * 
 * Copyright (C) 2010 珠海泰坦软件系统有限公司. All rights reserved.
 */
package com.titansoft.model;
/**
 * 
 * 录入表单的值对象
 * @author 珠海 李正球 2010-08-02
 */
public class XsdObject {
	private String fieldName = "";// 字段名

	private String fieldNameTag = "";// 字段描述名

	private String fieldType = ""; // 字段类型

	private String fieldSize = ""; // 字段大小

	private String isNotNewline = "";// 是否换行

	private String isCommonUse = ""; // 常用项显示

	private String isNull = ""; // 为空显示

	private String promptcheck;// 提示项

	private String prompt;// 提示项值

	private String dpts;// 倒排显示

	private String isreadonly;// 是否只读显示

	private String inputtotal;// 选定字段是否出现计算按钮

	private String textrow; // 整行显示行数

	private String searchscope;// 是否是范围查询字段

	private String defaultvalue;// 模板字段默认值
	
	private String isinputhidden;// 是否隐藏项

	public String getIsinputhidden() {
		return isinputhidden;
	}

	public void setIsinputhidden(String isinputhidden) {
		this.isinputhidden = isinputhidden;
	}

	public String getDefaultvalue() {
		return defaultvalue;
	}

	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

	public String getSearchscope() {
		return searchscope;
	}

	public void setSearchscope(String searchscope) {
		this.searchscope = searchscope;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldNameTag() {
		return fieldNameTag;
	}

	public void setFieldNameTag(String fieldNameTag) {
		this.fieldNameTag = fieldNameTag;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getIsCommonUse() {
		return isCommonUse;
	}

	public void setIsCommonUse(String isCommonUse) {
		this.isCommonUse = isCommonUse;
	}

	public String getIsNotNewline() {
		return isNotNewline;
	}

	public void setIsNotNewline(String isNotNewline) {
		this.isNotNewline = isNotNewline;
	}

	public String getIsNull() {
		return isNull;
	}

	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}

	public String getFieldSize() {
		return fieldSize;
	}

	public void setFieldSize(String fieldSize) {
		this.fieldSize = fieldSize;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public String getPromptcheck() {
		return promptcheck;
	}

	public void setPromptcheck(String promptcheck) {
		this.promptcheck = promptcheck;
	}

	public String getDpts() {
		return dpts;
	}

	public void setDpts(String dpts) {
		this.dpts = dpts;
	}

	public String getIsreadonly() {
		return isreadonly;
	}

	public void setIsreadonly(String isreadonly) {
		this.isreadonly = isreadonly;
	}

	public String getInputtotal() {
		return inputtotal;
	}

	public void setInputtotal(String inputtotal) {
		this.inputtotal = inputtotal;
	}

	public String getTextrow() {
		return textrow;
	}

	public void setTextrow(String textrow) {
		this.textrow = textrow;
	}

}
