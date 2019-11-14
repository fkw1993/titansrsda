package com.titansoft.model.extjs;


import com.titansoft.utils.annotation.JSON;

public class Radiogroup extends AbstractField{
	private String fieldLabel;
	private String xtype;
	@JSON(symbol="")
	private String items;
	private String columns;
	private Boolean isLine;
	private String id;
	public Radiogroup(){
		setXtype("radiogroup");
	}
	public Radiogroup(String fieldLabel,String items,String columns,Boolean isLine,String id)
	  {
	    setXtype("radiogroup");
	    setFieldLabel(fieldLabel);
	    setItems(items);
	    setColumns(columns);
	    setIsLine(isLine);
	    setId(id);
	  }
	public String getColumns() {
		return columns;
	}
	public void setColumns(String columns) {
		this.columns = columns;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	public String getFieldLabel() {
		return fieldLabel;
	}
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
	public String getXtype() {
		return xtype;
	}
	public void setXtype(String xtype) {
		this.xtype = xtype;
	}
	public Boolean getIsLine() {
		return isLine;
	}
	public void setIsLine(Boolean isLine) {
		this.isLine = isLine;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
