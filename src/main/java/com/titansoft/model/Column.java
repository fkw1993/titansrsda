package com.titansoft.model;

/**
 * 通用表格列头对象
 * 
 * @author hongcs
 */
public class Column {

	/**
	 * 该列的value值
	 */
	private String dataIndex;
	/**
	 * 表头该列的显示名称
	 */
	private String text;
	/**
	 * 控件类型
	 */
	private String xtype;
	/**
	 * 对齐方向
	 */
	private String align = "left";
	/**
	 * 列头是否可以隐藏
	 */
	private boolean hideable = true;
	/**
	 * 列头是否可以排序
	 */
	private boolean sortable = true;
	/**
	 * 列头是否隐藏
	 */
	private boolean hidden = false;
	/**
	 * 渲染，可指定函数
	 */
	private String renderer;
	/**
	 * flex布局宽度自适应
	 */
	private int flex = 1;
	/**
	 * 是否查询显示(搜索栏)
	 */
	private boolean issearch = true;
	/**
	 * 是否范围控件(搜索栏)
	 */
	private boolean searchscope = false;
	/**
	 * 是否锁定列
	 */
	private boolean locked = false;
	
	/**
	 * 列宽
	 */
	private int width;
	

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getXtype() {
		return xtype;
	}

	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public boolean isHideable() {
		return hideable;
	}

	public void setHideable(boolean hideable) {
		this.hideable = hideable;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public String getRenderer() {
		return renderer;
	}

	public void setRenderer(String renderer) {
		this.renderer = renderer;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public int getFlex() {
		return flex;
	}

	public void setFlex(int flex) {
		this.flex = flex;
	}

	public boolean isIssearch() {
		return issearch;
	}

	public void setIssearch(boolean issearch) {
		this.issearch = issearch;
	}

	public boolean isSearchscope() {
		return searchscope;
	}

	public void setSearchscope(boolean searchscope) {
		this.searchscope = searchscope;
	}

	public Column(String dataIndex, String text, String xtype) {
		this.dataIndex = dataIndex;
		this.text = text;
		this.xtype = xtype;
	}

	public Column() {
	}
}