package com.titansoft.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 角色实体表
 * 
 * @author hongcs
 *
 */
@TableName(value = "t_s_role")
public class Role {
	/**
	 * 角色id
	 */
	@TableId("roleid")
	private String roleid;
	/**
	 * 产品外键
	 */
	private String productid;
	/**
	 * 产品编号
	 */
	private String code;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;

	/**
	 * 创建时间
	 */
	private String createtime;
	/**
	 * 创建者
	 */
	private String createuser;
	/**
	 * 修改时间
	 */
	private String modifytime;
	/**
	 * 修改者
	 */
	private String modifyuser;
	/**
	 * 是否是产品管理员
	 */
	private String isadmin;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 隐藏角色（系统管理员、安全管理员、安全审计员）,除了sysdba外其他用户无法对其进行操作
	 */
	private String ishidden;

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(String isadmin) {
		this.isadmin = isadmin;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getModifytime() {
		return modifytime;
	}

	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}

	public String getModifyuser() {
		return modifyuser;
	}

	public void setModifyuser(String modifyuser) {
		this.modifyuser = modifyuser;
	}

	public String getIshidden() {
		return ishidden;
	}

	public void setIshidden(String ishidden) {
		this.ishidden = ishidden;
	}
}