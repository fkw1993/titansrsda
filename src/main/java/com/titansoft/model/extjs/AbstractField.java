package com.titansoft.model.extjs;

/**
 * 表单域定义
 * 
 * @author lsw
 * @data 2010/07/13
 */
public abstract class AbstractField {
	private String xtype;

	private String fieldLabel;

	private String name;

	private String value;

	private Boolean readOnly;

	private Boolean isLine;

	private Boolean isCommon; //档案信息或者其他数据
	
	private Boolean isCommonitem;//元数据信息
	
	private Boolean isRange;
	
	private String title;
	
	private int length;
	
	private int width;
	
	private String vtype;
	
    private String regex;
    private String regexText;

    private String groupname;
    
    private String groupcollapsed;
    
    private String groupcolumn; //每组显示多少个字段
    
	public AbstractField() {
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
	 * @param readOnly
	 *            只读
	 * @param isLine
	 *            换行
	 * @param isCommon
	 *            常用项
	 */
	public AbstractField(String xtype, String fieldLabel, String name,
                         String value, Boolean readOnly, Boolean isLine, Boolean isCommon  , Boolean isCommonitem  ) {
		setXtype(xtype);
		setFieldLabel(fieldLabel);
		setName(name);
		setValue(value);
		setReadOnly(readOnly);
		setIsLine(isLine);
		setIsCommon(isCommon);
		setIsCommonitem(isCommonitem); //增加元数据项
		
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
	 * @param readOnly
	 *            只读
	 * @param isLine
	 *            换行
	 * @param isCommon
	 *            常用项
	 */
	public AbstractField(String xtype, String fieldLabel, String name,
                         String value, Boolean readOnly, Boolean isLine, Boolean isCommon   ) {
		setXtype(xtype);
		setFieldLabel(fieldLabel);
		setName(name);
		setValue(value);
		setReadOnly(readOnly);
		setIsLine(isLine);
		setIsCommon(isCommon);
		setIsCommonitem(isCommonitem); //增加元数据项
		
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	public Boolean getIsLine() {
		return isLine;
	}

	public void setIsLine(Boolean isLine) {
		this.isLine = isLine;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getXtype() {
		return xtype;
	}

	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

	public Boolean getIsCommon() {
		return isCommon;
	}

	public void setIsCommon(Boolean isCommon) {
		this.isCommon = isCommon;
	}
	

	public String toJson() throws Throwable {
		return Common.toJson(this);
	}

	public static String toJson(java.util.List<AbstractField> list){
		return Common.toJson("items", list);
	}

	public Boolean getIsRange() {
		return isRange;
	}

	public void setIsRange(Boolean isRange) {
		this.isRange = isRange;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getVtype() {
		return vtype;
	}

	public void setVtype(String vtype) {
		this.vtype = vtype;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setIsCommonitem(Boolean isCommonitem) {
		this.isCommonitem = isCommonitem;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getRegexText() {
		return regexText;
	}

	public void setRegexText(String regexText) {
		this.regexText = regexText;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getGroupcollapsed() {
		return groupcollapsed;
	}

	public void setGroupcollapsed(String groupcollapsed) {
		this.groupcollapsed = groupcollapsed;
	}

	public String getGroupcolumn() {
		return groupcolumn;
	}

	public void setGroupcolumn(String groupcolumn) {
		this.groupcolumn = groupcolumn;
	}

	
}
