package com.titansoft.entity.system;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Component;

/**
 * @Author: Kevin
 * @Date: 2019/7/25 11:08
 */
@Component
public class Login {

    //用户账号
    private String code;

    /**
     * 账号密码
     */
    private String pwd;

    /**
     * 验证码
     */
    private String checkcode;


    /**
     * 子系统ID
     */
    private String proid;

    /**
     * 所登录的客户端IP
     */
    private String ip;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
