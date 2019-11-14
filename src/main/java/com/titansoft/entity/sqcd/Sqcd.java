package com.titansoft.entity.sqcd;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 档案查档预约与受理实体类
 * @author jiaogan
 *
 */

@TableName(value="t_bus_sqcd")
public class Sqcd {
	private String id;
	private String sqrname;
	private String sqrunit;
	private String sqrphone;	
	private String daname;
	private String idnumber;
	private String usetype;
	private String approvalstate;
	private String applydate;
	private String expirydate;
	private String reason;//查档事由
	private String cdtype;
	private String sqrpolitical;
	public String getReason() {
		return reason;
	}

	public String getSqrpolitical() {
		return sqrpolitical;
	}

	public void setSqrpolitical(String sqrpolitical) {
		this.sqrpolitical = sqrpolitical;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSqrname() {
		return sqrname;
	}

	public void setSqrname(String sqrname) {
		this.sqrname = sqrname;
	}

	public String getSqrunit() {
		return sqrunit;
	}

	public void setSqrunit(String sqrunit) {
		this.sqrunit = sqrunit;
	}

	public String getSqrphone() {
		return sqrphone;
	}

	public void setSqrphone(String sqrphone) {
		this.sqrphone = sqrphone;
	}

	public String getDaname() {
		return daname;
	}

	public void setDaname(String daname) {
		this.daname = daname;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public String getUsetype() {
		return usetype;
	}

	public void setUsetype(String usetype) {
		this.usetype = usetype;
	}

	public String getApprovalstate() {
		return approvalstate;
	}

	public void setApprovalstate(String approvalstate) {
		this.approvalstate = approvalstate;
	}


	public String getApplydate() {
		return applydate;
	}

	public void setApplydate(String applydate) {
		this.applydate = applydate;
	}

	public String getExpirydate() {
		return expirydate;
	}

	public void setExpirydate(String expirydate) {
		this.expirydate = expirydate;
	}

	public String getCdtype() {
		return cdtype;
	}

	public void setCdtype(String cdtype) {
		this.cdtype = cdtype;
	}

	
	
}
