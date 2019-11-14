package com.titansoft.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 系统配置实体类
 * @author Aliyu
 *
 */
@TableName(value = "t_com_config")
public class Config {
	private String id;//主键id
	private String configcode;//配置代号
	private String configvalue;//配置值
	private String configdesc;//配置描述
	private String configgroup;//配置分组
	private String groupdesc;//分组描述
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getConfigcode() {
		return configcode;
	}
	public void setConfigcode(String configcode) {
		this.configcode = configcode;
	}
	public String getConfigvalue() {
		return configvalue;
	}
	public void setConfigvalue(String configvalue) {
		this.configvalue = configvalue;
	}
	public String getConfigdesc() {
		return configdesc;
	}
	public void setConfigdesc(String configdesc) {
		this.configdesc = configdesc;
	}
	public String getConfiggroup() {
		return configgroup;
	}
	public void setConfiggroup(String configgroup) {
		this.configgroup = configgroup;
	}
	public String getGroupdesc() {
		return groupdesc;
	}
	public void setGroupdesc(String groupdesc) {
		this.groupdesc = groupdesc;
	}

}
