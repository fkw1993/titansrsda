package com.titansoft.entity.media;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/** 
 * po描述:存储位置信息表
 * @author 珠海·lhh
 * @version 1.0 2016-01-30
 */
@TableName(value="t_er_storeitem")
public class Storeitem implements Serializable {

	@TableId
	private String id;
	
	private String filesize;//位置大小

	private String url;//位置信息

	private String storeid;//电子库房id
	
	private String encrypturl;//加密后路径
		
	private String watermarkurl;//水印图片路径
	
	private String watermarkenurl;//水印图片路径加密
	
	private String opturl;//优化后图片路径
	
	private String enopturl;//优化后图片路径加密
	
	private String optstatus;//优化状态
	
	private String eppurl;
	
	private String eneppurl;
	
	private String opteppurl;
	
	private String enopteppurl;
	

	public String getFilesize(){
		return filesize;
	}
	public void setFilesize(String filesize){
		this.filesize = filesize;
	}

	public String getUrl(){
		return url;
	}
	public void setUrl(String url){
		this.url = url;
	}

	public String getStoreid(){
		return storeid;
	}
	public void setStoreid(String storeid){
		this.storeid = storeid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEncrypturl() {
		return encrypturl;
	}
	public void setEncrypturl(String encrypturl) {
		this.encrypturl = encrypturl;
	}
	public String getWatermarkurl() {
		return watermarkurl;
	}
	public void setWatermarkurl(String watermarkurl) {
		this.watermarkurl = watermarkurl;
	}
	public String getWatermarkenurl() {
		return watermarkenurl;
	}
	public void setWatermarkenurl(String watermarkenurl) {
		this.watermarkenurl = watermarkenurl;
	}
	public String getOpturl() {
		return opturl;
	}
	public void setOpturl(String opturl) {
		this.opturl = opturl;
	}
	public String getEnopturl() {
		return enopturl;
	}
	public void setEnopturl(String enopturl) {
		this.enopturl = enopturl;
	}
	public String getOptstatus() {
		return optstatus;
	}
	public void setOptstatus(String optstatus) {
		this.optstatus = optstatus;
	}
	public String getEppurl() {
		return eppurl;
	}
	public void setEppurl(String eppurl) {
		this.eppurl = eppurl;
	}
	public String getEneppurl() {
		return eneppurl;
	}
	public void setEneppurl(String eneppurl) {
		this.eneppurl = eneppurl;
	}
	public String getOpteppurl() {
		return opteppurl;
	}
	public void setOpteppurl(String opteppurl) {
		this.opteppurl = opteppurl;
	}
	public String getEnopteppurl() {
		return enopteppurl;
	}
	public void setEnopteppurl(String enopteppurl) {
		this.enopteppurl = enopteppurl;
	}

}