package com.titansoft.model;

/**
 * 数据权限功能的实体类,用来存放树,主要添加了checked属性,与前端生成树的方法形成属性对应
 * 
 * @author Lime
 * @date 2018/4/13
 */
public class Datapermission {
	private static final long serialVersionUID = -2229850185084729409L;
	private String text;// 节点名称
	private String id;// 节点id
	private boolean leaf; // 叶子
	private String cls; // 文件夹或文件图标 file,folder
	private String cid;// 类别ID
	private String url;// URL相对链接<%=request.getContextPath()%>/除外
	private String tag;// 节点别名
	/**
	 * checked2暂时不清楚用途，默认好像为false,具体请看前端方法
	 */
	private boolean checked;
	private boolean checked2;

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public boolean isChecked2() {
		return checked2;
	}

	public void setChecked2(Boolean checked2) {
		this.checked2 = checked2;
	}
}
