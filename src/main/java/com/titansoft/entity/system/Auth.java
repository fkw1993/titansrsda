package com.titansoft.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 登录凭证实体
 *
 * @author Administrator
 */
@TableName(value = "t_s_auth")
public class Auth {
    @TableId(value = "aid", type = IdType.INPUT)
    private String aid;
    private String productcode;  //产品代号
    private String usercode;     //用户代号
    private String address;      //ip地址
    private String logontime;  //登录时间
    private String accesstime;  //最后访问时间
    private String ppcode;             //权限代号
    private String accesstoken;        //登录验证串


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getPpcode() {
        return ppcode;
    }

    public void setPpcode(String ppcode) {
        this.ppcode = ppcode;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getAccesstime() {
        return accesstime;
    }

    public void setAccesstime(String accesstime) {
        this.accesstime = accesstime;
    }

    public String getLogontime() {
        return logontime;
    }

    public void setLogontime(String logontime) {
        this.logontime = logontime;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }
}