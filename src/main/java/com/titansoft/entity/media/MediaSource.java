package com.titansoft.entity.media;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 内容文件浏览源文件实体类
 * 
 * @author 珠海·lhh
 * @version 1.0 2016-1-30
 * 
 */
@TableName(value = "t_er_srclist")
public class MediaSource {
	private String id; // 主键
	private String erid; // 电子文件erid
	private String fileorder; // 文件序号
	private String filename; // 文件名
	private String fileformat; // 文件格式（后缀名）
	private Integer filesize; // 文件大小
	private String createuser; //
	private String createtime; //
	private String modifyuser; //
	private String modifytime; //
	private String storeitemid; // 文件流hash
	private String documenttype; // 文档数据类型
	private String documentindex;// 文档类型展示优先级
	private String hashcode;// 文件的流hash
	@TableField(exist = false)
	private String datatype;
	@TableField(exist = false)
	private String filepath;
	@TableField(exist = false)
	private String glstate = "";//管理阶段，划分采集和管理的界限，CJ表示采集阶段，GL表示管理阶段
	@TableField(exist = false)
	private static final String orderfield="filename";//排序字段（初始值设置，没有set方法）
	@TableField(exist = false)
	private String message = "";//存放异常信息


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getErid() {
		return erid;
	}

	public void setErid(String erid) {
		this.erid = erid;
	}

	public String getFileorder() {
		return fileorder;
	}

	public void setFileorder(String fileorder) {
		this.fileorder = fileorder;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFileformat() {
		return fileformat;
	}

	public void setFileformat(String fileformat) {
		this.fileformat = fileformat;
	}

	public Integer getFilesize() {
		return filesize;
	}

	public void setFilesize(Integer filesize) {
		this.filesize = filesize;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getModifyuser() {
		return modifyuser;
	}

	public void setModifyuser(String modifyuser) {
		this.modifyuser = modifyuser;
	}

	public String getModifytime() {
		return modifytime;
	}

	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}

	public String getStoreitemid() {
		return storeitemid;
	}

	public void setStoreitemid(String storeitemid) {
		this.storeitemid = storeitemid;
	}

	public String getDocumenttype() {
		return documenttype;
	}

	public void setDocumenttype(String documenttype) {
		this.documenttype = documenttype;
	}

	public String getDocumentindex() {
		return documentindex;
	}

	public void setDocumentindex(String documentindex) {
		this.documentindex = documentindex;
	}

	public String getHashcode() {
		return hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getGlstate() {
		return glstate;
	}

	public void setGlstate(String glstate) {
		this.glstate = glstate;
	}

	public static String getOrderfield() {
		return orderfield;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
}
