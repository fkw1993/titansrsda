package com.titansoft.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

//系统公告
@TableName(value = "T_S_NOTICE")
public class Notice {
    @TableId
    private String id;  //id
    private String headline;//标题
    private String content;//内容
    private String wenhao;//文号
    private String publishdepartment;//发布部门
    private String publishuser;//发布人
    private String publishdate;//发布时间
    private String status;//发布状态
    private String expirationtime;//过期时间
    private String createuser;//创建人
    private String createdepartment;//创建部门
    private String createdatetime;//创建时间
    private String updatedatetime;//修改时间
		private String updateuser;//修改人
    private String productid; //产品ID


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWenhao() {
		return wenhao;
	}

	public void setWenhao(String wenhao) {
		this.wenhao = wenhao;
	}

	public String getPublishdepartment() {
		return publishdepartment;
	}

	public void setPublishdepartment(String publishdepartment) {
		this.publishdepartment = publishdepartment;
	}

	public String getPublishuser() {
		return publishuser;
	}

	public void setPublishuser(String publishuser) {
		this.publishuser = publishuser;
	}

	public String getPublishdate() {
		return publishdate;
	}

	public void setPublishdate(String publishdate) {
		this.publishdate = publishdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExpirationtime() {
		return expirationtime;
	}

	public void setExpirationtime(String expirationtime) {
		this.expirationtime = expirationtime;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getCreatedepartment() {
		return createdepartment;
	}

	public void setCreatedepartment(String createdepartment) {
		this.createdepartment = createdepartment;
	}

	public String getCreatedatetime() {
		return createdatetime;
	}

	public void setCreatedatetime(String createdatetime) {
		this.createdatetime = createdatetime;
	}

	public String getUpdatedatetime() {
		return updatedatetime;
	}

	public void setUpdatedatetime(String updatedatetime) {
		this.updatedatetime = updatedatetime;
	}

	public String getUpdateuser() {
		return updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}
}
