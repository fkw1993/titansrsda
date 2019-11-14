package com.titansoft.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 人事档案分类
 * 
 * @author Administrator csb 2018-3-28
 *
 */
@TableName(value="t_s_sort")
public class Sort {
	private String id;
	private String sortcode;
	private String sortname;
	private String sortfullname;
	private int sequence;
	private String parentid;
	private String isleaf;
	private String blankline;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSortcode() {
		return sortcode;
	}

	public void setSortcode(String sortcode) {
		this.sortcode = sortcode;
	}

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public String getSortfullname() {
		return sortfullname;
	}

	public void setSortfullname(String sortfullname) {
		this.sortfullname = sortfullname;
	}


	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}


	public String getBlankline() {
		return blankline;
	}

	public void setBlankline(String blankline) {
		this.blankline = blankline;
	}



}
