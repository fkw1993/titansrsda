package com.titansoft.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 权限实体类
 *
 * @author lhh
 * @version 1.0 2012-03-05
 */
@TableName(value = "t_s_privilege")
public class Privilege {
    @TableId("privilegeid")
    private String privilegeid;
    private String productid;
    private String code;
    private String name;
    private String description;
    private String createtime;
    private String createuser;
    private String modifytime;
    private String uri;                //资源位置
    private String privtype;            //权限类型
    private String status;
    private String modifyuser;
    private String recommend;      //引用其他系统的系统
    private String ishidden;//状态为true时，即只有管理员角色用户才能被授予的功能,只附给系统管理员、安全审计员、安全管理员的权限）,除了sysdba外其他用户无法对其进行操作
    private String checkurl; //本权限点需要监控的资源URL
    @TableField(exist = false)
    private String groupname;
    @TableField(exist = false)
    private String flag;                //是否按钮
    @TableField(exist = false)
    private String eventname;        //按钮事件名
    @TableField(exist = false)
    private String iconcls;            //图标
    @TableField(exist = false)
    private String rootid;
    @TableField(exist = false)
    private String parentid;
    @TableField(exist = false)
    private String levelnum;            //层级
    @TableField(exist = false)
    private String levelord;            //级内排序号
    @TableField(exist = false)
    private String fullname; //功能全称

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getLevelnum() {
        return levelnum;
    }

    public void setLevelnum(String levelnum) {
        this.levelnum = levelnum;
    }

    public String getLevelord() {
        return levelord;
    }

    public void setLevelord(String levelord) {
        this.levelord = levelord;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getRootid() {
        return rootid;
    }

    public void setRootid(String rootid) {
        this.rootid = rootid;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getIconcls() {
        return iconcls;
    }

    public void setIconcls(String iconcls) {
        this.iconcls = iconcls;
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

    public String getPrivilegeid() {
        return privilegeid;
    }

    public void setPrivilegeid(String privilegeid) {
        this.privilegeid = privilegeid;
    }

    public String getPrivtype() {
        return privtype;
    }

    public void setPrivtype(String privtype) {
        this.privtype = privtype;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getIshidden() {
        return ishidden;
    }

    public void setIshidden(String ishidden) {
        this.ishidden = ishidden;
    }

    public String getCheckurl() {
        return checkurl;
    }

    public void setCheckurl(String checkurl) {
        this.checkurl = checkurl;
    }


}
