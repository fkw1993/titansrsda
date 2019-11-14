package com.titansoft.model.extjs;

/**
 * 多功能按钮定义
 * 
 * @author lsw
 * @data 2010/07/16
 */
public class MultiButton extends AbstractButton {
	private Boolean joinHidden;
	
	private Boolean joinChecked;

	private String joinBoxLabel;
	
	private Boolean increaseHidden;

	private Boolean increaseChecked;

	private String increaseBoxLabel;

	public MultiButton() {
		setXtype("multibutton");
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
	 * @param joinChecked
	 *            连续录入是否选中
	 * @param joinChecked
	 *            连续录入标识文本
	 * @param increaseChecked
	 *            序号递增是否选中
	 * @param increaseBoxLabel
	 *            序号递增标识文本
	 */
	public MultiButton(String text, String id, String scope, String handler,
			Boolean isLine, Boolean joinChecked, String joinBoxLabel,
			Boolean increaseChecked, String increaseBoxLabel) {
		super("multibutton", text, id, null, scope, handler, isLine);
		setJoinChecked(joinChecked);
		setJoinBoxLabel(joinBoxLabel);
		setIncreaseChecked(increaseChecked);
		setIncreaseBoxLabel(increaseBoxLabel);
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
	 * @param joinHidden
	 *            是否隐藏连续录入
	 * @param joinChecked
	 *            连续录入是否选中
	 * @param joinChecked
	 *            连续录入标识文本
	 * @param increaseHidden
	 *            是否隐藏序号递增
	 * @param increaseChecked
	 *            序号递增是否选中
	 * @param increaseBoxLabel
	 *            序号递增标识文本
	 */
	public MultiButton(String text, String id, String scope, String handler,
			Boolean isLine, Boolean joinHidden, Boolean joinChecked, String joinBoxLabel,
			Boolean increaseHidden, Boolean increaseChecked, String increaseBoxLabel) {
		super("multibutton", text, id, null, scope, handler, isLine);
		setJoinHidden(joinHidden);
		setJoinChecked(joinChecked);
		setJoinBoxLabel(joinBoxLabel);
		setIncreaseHidden(increaseHidden);
		setIncreaseChecked(increaseChecked);
		setIncreaseBoxLabel(increaseBoxLabel);
	}

	public Boolean getJoinChecked() {
		return this.joinChecked;
	}

	public void setJoinChecked(Boolean joinChecked) {
		this.joinChecked = joinChecked;
	}

	public String getJoinBoxLabel() {
		return this.joinBoxLabel;
	}

	public void setJoinBoxLabel(String joinBoxLabel) {
		this.joinBoxLabel = joinBoxLabel;
	}

	public Boolean getIncreaseChecked() {
		return this.increaseChecked;
	}

	public void setIncreaseChecked(Boolean increaseChecked) {
		this.increaseChecked = increaseChecked;
	}

	public String getIncreaseBoxLabel() {
		return this.increaseBoxLabel;
	}

	public void setIncreaseBoxLabel(String increaseBoxLabel) {
		this.increaseBoxLabel = increaseBoxLabel;
	}

	public Boolean getIncreaseHidden() {
		return increaseHidden;
	}

	public Boolean getJoinHidden() {
		return joinHidden;
	}

	public void setIncreaseHidden(Boolean increaseHidden) {
		this.increaseHidden = increaseHidden;
	}

	public void setJoinHidden(Boolean joinHidden) {
		this.joinHidden = joinHidden;
	}

}
