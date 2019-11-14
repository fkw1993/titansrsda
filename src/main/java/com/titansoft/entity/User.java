package com.titansoft.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**系统用户
 * @Author: Kevin
 * @Date: 2019/7/25 11:39
 */
@TableName(value="t_s_user")
public class User {
    @TableId("userid")
    private String userid;//用户主键id
    private String code;//帐号
    private String name;//名称
    private String description;//描述说明
    private String createtime;//创建时间
    private String createuser;//创建者名称
    private String modifytime;//修改时间
    private String modifyuser;//修改者名称
    private String pwd;//密码
    private String expirestime;//过期时间
    private String status;//状态（是否在用？）
    private String cardid;//身份证号
    private String usertype;//用户类型
    private String changedate;//上一次修改密码的时间
    private String organid;//机构id

    public String getChangedate() {
        return changedate;
    }
    public void setChangedate(String changedate) {
        this.changedate = changedate;
    }
    public String getCardid() {
        return cardid;
    }
    public void setCardid(String cardid) {
        this.cardid = cardid;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getExpirestime() {
        return expirestime;
    }
    public void setExpirestime(String expirestime) {
        this.expirestime = expirestime;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getOrganid() {
        return organid;
    }
    public void setOrganid(String organid) {
        this.organid = organid;
    }
    public String getUsertype() {
        return usertype;
    }
    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

}
