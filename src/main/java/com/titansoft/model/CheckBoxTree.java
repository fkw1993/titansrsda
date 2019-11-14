package com.titansoft.model;

/**
 * 通用树对象(有复选框)
 * @author 唐展鸿
 */
public class CheckBoxTree extends Tree{
	/**
	 * 节点是否被选中
	 */
	private boolean checked = false;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}