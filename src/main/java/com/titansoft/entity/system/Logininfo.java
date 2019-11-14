package com.titansoft.entity.system;

import org.springframework.stereotype.Component;

import javax.servlet.ServletResponse;
import java.util.HashMap;
import java.util.Map;
@Component
public class Logininfo {
	private String userid;
    private String username;
    private String realname;
    private String pwd;
    private String dataid;//数据权限id
    private String unitid;
    private String unitname;
	private String roleid;
	private String webpath;
	private String realpath;
	private String ipaddress;
	private String productcode;


	private ServletResponse response =null;
	private String stylelinks; //用户对应样式名称
	//利用页面是否有权限直接跳转到电子管理页面
	private boolean isadmin;
	//功能权限设置
	private Map funmap=null;
	
	//登录次数
	private Integer visitsl;
	

	public Integer getVisitsl() {
		return visitsl;
	}
	public void setVisitsl(Integer visitsl) {
		this.visitsl = visitsl;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public ServletResponse getResponse() {
		return response;
	}
	public void setResponse(ServletResponse response) {
		this.response = response;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	
	public String getUnitid() {
		return unitid;
	}
	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}
	public String getUnitname() {
		return unitname;
	}
	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getWebpath() {
		return webpath;
	}
	public void setWebpath(String webpath) {
		this.webpath = webpath;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}

	public void setFunmap(HashMap<String, String> funmap) {
		this.funmap = funmap;
	}
	public Map getFunmap() {
		return funmap;
	}
	public void setFunmap(Map funmap) {
		this.funmap = funmap;
	}
	public String getRealpath() {
		return realpath;
	}
	public void setRealpath(String realpath) {
		this.realpath = realpath;
	}
	public String getStylelinks() {
		return stylelinks;
	}
	public void setStylelinks(String stylelinks) {
		this.stylelinks = stylelinks;
	}
	public boolean isIsadmin() {
		return isadmin;
	}
	public void setIsadmin(boolean isadmin) {
		this.isadmin = isadmin;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getDataid() {
		return dataid;
	}

	public void setDataid(String dataid) {
		this.dataid = dataid;
	}
}
