package com.titansoft.model;

/**
 * 通用树对象(无复选框)
 * @author 唐展鸿
 */
public class Tree {

	/**
	 * 节点ID
	 */
	private String id;
	/**
	 * 父节点ID
	 */
	private String pid = "";
	/**
	 * 节点显示值
	 */
	private String text;
	/**
	 * 节点类型
	 */
	private Integer type = 0;
	/**
	 * 是否子节点
	 */
	private boolean leaf = false;
	/**
	 * 图标样式
	 */
	private String iconCls;
	/**
	 * 是否展开
	 */
	private boolean expanded = false;
	/**
	 * 子节点数组
	 */
	private Tree[] children;
	/**
	 * 额外属性
	 */
	private Object attributes;
	/**
	 * 当节点为子节点时,设置没有子节点属性所用的空数组
	 */
	public static Tree[] isChild = new Tree[]{};
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public Tree[] getChildren() {
		return children;
	}
	public void setChildren(Tree[] children) {
		this.children = children;
	}
	public Object getAttributes() {
		return attributes;
	}
	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}
	public static Tree[] getIsChild() {
		return isChild;
	}
	public static void setIsChild(Tree[] isChild) {
		Tree.isChild = isChild;
	}
}