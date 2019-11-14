package com.titansoft.entity.view;

import com.baomidou.mybatisplus.annotation.TableName;

/**功能视图
 * @Author: Kevin
 * @Date: 2019/8/31 16:34
 */
@TableName(value = "v_privilege")
public class PrivilegeView {
    private String privilegeid;
    private String rootid;
    private String levelnum;
    private String parentid;
    private String levelord;
    private String code;
    private String text;
    private String productid;
    private String description;
    private String flag;
    private String eventname;
    private String uri;
    private String iconcls;
    private String rootlink;
    private String recommend;
    private String ishidden;
    private String checkurl;
    private String groupname;


    public String getPrivilegeid() {
        return privilegeid;
    }

    public void setPrivilegeid(String privilegeid) {
        this.privilegeid = privilegeid;
    }

    public String getRootid() {
        return rootid;
    }

    public void setRootid(String rootid) {
        this.rootid = rootid;
    }

    public String getLevelnum() {
        return levelnum;
    }

    public void setLevelnum(String levelnum) {
        this.levelnum = levelnum;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getLevelord() {
        return levelord;
    }

    public void setLevelord(String levelord) {
        this.levelord = levelord;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getIconcls() {
        return iconcls;
    }

    public void setIconcls(String iconcls) {
        this.iconcls = iconcls;
    }

    public String getRootlink() {
        return rootlink;
    }

    public void setRootlink(String rootlink) {
        this.rootlink = rootlink;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
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

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}
