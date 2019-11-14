package com.titansoft.entity.log;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 日志实体类（异常日志）
 *
 * @author Aliyu
 */
@TableName(value = "t_s_errorlog")
public class ErrorLog {
    @TableId
    private String id;//主键id
    private String username;//用户名
    private String realname;//真实名
    private String unitid;//单位id
    private String unitfullname;//单位全称
    private String ipaddress;//ip地址
    private String dotime;//异常时间
    private String dodesc;//异常描述
    private String errortype;//异常类型（攻击行为，异常行为）
    private String errorlocation;//异常源（登录异常默认为平台类型，功能异常：功能名称，数据异常为：WSWG或者分类名？）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public String getUnitfullname() {
        return unitfullname;
    }

    public void setUnitfullname(String unitfullname) {
        this.unitfullname = unitfullname;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getDotime() {
        return dotime;
    }

    public void setDotime(String dotime) {
        this.dotime = dotime;
    }

    public String getDodesc() {
        return dodesc;
    }

    public void setDodesc(String dodesc) {
        this.dodesc = dodesc;
    }

    public String getErrortype() {
        return errortype;
    }

    public void setErrortype(String errortype) {
        this.errortype = errortype;
    }

    public String getErrorlocation() {
        return errorlocation;
    }

    public void setErrorlocation(String errorlocation) {
        this.errorlocation = errorlocation;
    }


}
