package com.titansoft.entity.sqcd;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "T_BUS_SQCDITEM")
public class SqcdItem {
	@TableId
	private String id;
	private String sqcdid;//申请查档id
	private String name;//查档人姓名
	private String unitname;//查档人单位
	private String phonenumber;//查档人电话
	private String political;//查档人的政治面貌
	private String idnumber;//查档人的身份证
	private String reason;//查档原因
	private String usetype;//用途
	private String cdtype;//查档方式
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSqcdid() {
		return sqcdid;
	}
	public void setSqcdid(String sqcdid) {
		this.sqcdid = sqcdid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnitname() {
		return unitname;
	}
	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getPolitical() {
		return political;
	}
	public void setPolitical(String political) {
		this.political = political;
	}
	public String getIdnumber() {
		return idnumber;
	}
	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getUsetype() {
		return usetype;
	}
	public void setUsetype(String usetype) {
		this.usetype = usetype;
	}
	public String getCdtype() {
		return cdtype;
	}
	public void setCdtype(String cdtype) {
		this.cdtype = cdtype;
	}
	
}
