package com.titansoft.entity.log;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 日志实体类（安全日志）
 *
 * @author lic
 */
@TableName(value = "t_s_securitylog")
public class SecurityLog {
    @TableId
    private String id;//主键id
    private String username;//用户名
    private String realname;//真实名
    private String userorgan;//用户机构全名
    private String userorganid;//用户机构id
    private String ipaddress;//ip地址
    private String dotime;//操作时间
    private String dodesc;//操作描述
    private String functionname;//操作
    private String isnormal;//操作性质（正常操作，异常操作）
    private String position;

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

    public String getUserorgan() {
        return userorgan;
    }

    public void setUserorgan(String userorgan) {
        this.userorgan = userorgan;
    }

    public String getUserorganid() {
        return userorganid;
    }

    public void setUserorganid(String userorganid) {
        this.userorganid = userorganid;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


}
