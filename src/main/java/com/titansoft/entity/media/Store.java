package com.titansoft.entity.media;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * po描述:电子库房表
 * 
 * @author 珠海·lhh
 * @version 1.0 2016-01-30
 */
@TableName(value = "t_er_store")
public class Store implements Serializable {
	private String id; // 主键
	private String code; // 库房代号
	private String name; // 库房名称
	private String metadataname; // 元数据名称(全宗号\年度\分类全名\保管期限等)
	private String metadatavalue; // 元数据值
	private String location; // 存储位置(目录、ftp地址)
	private String maxsize; // 总空间单位(GB)
	private String physicaltype; // 物理类型(磁盘目录、ftp)
	private String unused; // 剩余空间
	private String used; // 已用空间
	private String datatype; // 存储的数据类型
	private String isonuse; // 是否使用中（即新增文件是将以它他为存储）
	private String createtime; // 创建时间
	private String createuser; // 创建用户
	private String modifytime; // 修改时间
	private String modifyuser; // 修改用户
	private String isdefault; //是否作为默认存储
	private String usepercent; //已用空间百分比
	private String usedbyte; //已经用了的字节数（实时统计空间）
	private String unusedbyte; //已经用了的字节数（实时统计空间）
	public String getUnusedbyte() {
		return unusedbyte;
	}

	public void setUnusedbyte(String unusedbyte) {
		this.unusedbyte = unusedbyte;
	}

	public String getUsedbyte() {
		return usedbyte;
	}

	public void setUsedbyte(String usedbyte) {
		this.usedbyte = usedbyte;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getMetadataname() {
		return metadataname;
	}

	public void setMetadataname(String metadataname) {
		this.metadataname = metadataname;
	}

	public String getMetadatavalue() {
		return metadatavalue;
	}

	public void setMetadatavalue(String metadatavalue) {
		this.metadatavalue = metadatavalue;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMaxsize() {
		return maxsize;
	}

	public void setMaxsize(String maxsize) {
		this.maxsize = maxsize;
	}

	public String getPhysicaltype() {
		return physicaltype;
	}

	public void setPhysicaltype(String physicaltype) {
		this.physicaltype = physicaltype;
	}

	public String getUnused() {
		return unused;
	}

	public void setUnused(String unused) {
		this.unused = unused;
	}

	public String getUsed() {
		return used;
	}

	public void setUsed(String used) {
		this.used = used;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getIsonuse() {
		return isonuse;
	}

	public void setIsonuse(String isonuse) {
		this.isonuse = isonuse;
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


	public String getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}

	public String getUsepercent() {
		return usepercent;
	}

	public void setUsepercent(String usepercent) {
		this.usepercent = usepercent;
	}
	
}