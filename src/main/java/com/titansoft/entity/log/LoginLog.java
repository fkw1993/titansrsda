package com.titansoft.entity.log;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @Author: Kevin
 * @Date: 2019/8/15 17:58
 */
@TableName(value = "t_s_log")
public class LoginLog {
    @TableId
    private String id;//主键id
    private String username;//用户名
    private String realname;//真实名
    private String ipaddress;//ip地址
    private String dotime;//操作时间
    private String dodesc;//操作描述
    private String functionname;//平台系统名
    private String isnormal;//操作性质（是否正常操作）
    private String unitid;//单位id
    private String unitfullname;//单位全称

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

    public String getFunctionname() {
        return functionname;
    }

    public void setFunctionname(String functionname) {
        this.functionname = functionname;
    }

    public String getIsnormal() {
        return isnormal;
    }

    public void setIsnormal(String isnormal) {
        this.isnormal = isnormal;
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

    public LoginLog(String id, String username, String realname, String ipaddress, String dotime, String dodesc, String functionname, String isnormal, String unitid, String unitfullname) {
        this.id = id;
        this.username = username;
        this.realname = realname;
        this.ipaddress = ipaddress;
        this.dotime = dotime;
        this.dodesc = dodesc;
        this.functionname = functionname;
        this.isnormal = isnormal;
        this.unitid = unitid;
        this.unitfullname = unitfullname;
    }
}
