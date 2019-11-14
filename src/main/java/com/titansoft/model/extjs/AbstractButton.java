package com.titansoft.model.extjs;

import com.titansoft.utils.annotation.JSON;

/**
 * 按钮域定义
 * 
 * @author lsw
 * @data 2010/07/16
 */
public abstract class AbstractButton {
	
	private String xtype;

	private String text;

	private String id;

	private String type;

	@JSON(symbol = "")
	private String scope;

	@JSON(symbol = "")
	private String handler;

	private Boolean isLine;
	
	private String iconCls;

	public AbstractButton() {
	}

	/**
	 * @param text
	 *            描述
	 * @param id
	 *            标识
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 * @param isLine
	 *            换行
	 */
	public AbstractButton(String text, String id, String scope, String handler,
			Boolean isLine) {
		this(text, id, "button", scope, handler, isLine);
	}

	/**
	 * @param text
	 *            描述
	 * @param id
	 *            标识
	 * @param type
	 *            按钮类型
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 * @param isLine
	 *            换行
	 */
	public AbstractButton(String text, String id, String type, String scope,
			String handler, Boolean isLine) {
		this("button", text, id, type, scope, handler, isLine);
	}

	/**
	 * @param xtype
	 *            按钮扩展类型
	 * @param text
	 *            描述
	 * @param id
	 *            标识
	 * @param type
	 *            按钮类型
	 * @param scope
	 *            处理器作用范围
	 * @param handler
	 *            处理器
	 * @param isLine
	 *            换行
	 */
	public AbstractButton(String xtype, String text, String id, String type,
			String scope, String handler, Boolean isLine) {
		setXtype(xtype);
		setText(text);
		setId(id);
		setType(type);
		setScope(scope);
		setHandler(handler);
		setIsLine(isLine);
		
		if(iconCls == null || "".equals(iconCls))
		{
			if(text.indexOf("增加")>=0)
			{
				setIconCls("icon-add");
			}
			if(text.indexOf("确定")>=0)
			{
				setIconCls("icon-confirm");
			}
			else if(text.indexOf("删除")>=0)
			{
				setIconCls("icon-del");
			}
			else if(text.indexOf("保存")>=0)
			{
				setIconCls("icon-save");
			}
			else if(text.indexOf("关闭")>=0||text.indexOf("返回")>=0||text.indexOf("取消")>=0)
			{
				setIconCls("icon-exit");
			}
			else if(text.indexOf("清除")>=0)
			{
				setIconCls("icon-return");
			}
			else if(text.indexOf("执行")>=0||text.indexOf("测试")>=0)
			{
				setIconCls("icon-excute");
			}
			else if(text.indexOf("查询")>=0)
			{
				setIconCls("icon-search2");
			}
			else if(text.indexOf("扫描")>=0)
			{
				setIconCls("icon-scan");
			}
			else if(text.indexOf("绑定")>=0)
			{
				setIconCls("icon-bind");
			}
			else
			{
				setIconCls("icon-default");
			}
		}
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public Boolean getIsLine() {
		return isLine;
	}

	public void setIsLine(Boolean isLine) {
		this.isLine = isLine;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getXtype() {
		return xtype;
	}

	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

	public String toJson() throws Throwable {
		return Common.toJson(this);
	}

	public static String toJson(java.util.List<AbstractButton> list)
			throws Throwable {
		return Common.toJson("buttons", list);
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
}
