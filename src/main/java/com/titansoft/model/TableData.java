package com.titansoft.model;

import java.util.List;

/**
 * 通用表格数据对象
 * 
 * @author hongcs
 */
public class TableData {

	/**
	 * 数据总数
	 */
	private long totalCount;
	/**
	 * 数据数组
	 */
	private List<?> list;

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	

}