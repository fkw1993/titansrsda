package com.titansoft.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 组织机构
 *
 * @author Administrator
 */
@TableName(value = "t_s_organ")
public class Organ {
    @TableId("organid")
    private String organid;//id
    private String code;// 代号
    private String name;// 名称
    private String description;// 描述
    private String createtime;// 创建时间
    private String createuser;// 创建者
    private String modifytime;// 修改时间
    private String modifyuser;// 修改人
    private String organfullname;//组织机构全称
    private String isleaf;//是否是子节点
    private String isshow;//是否显示
    private String rootid;// 根节点id
    private String parentid;// 父节点id
    private Integer levelnum;//排序
    private Integer levelord;//排序

    public String getOrganid() {
        return organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getIsleaf() {
        return isleaf;
    }

    public void setIsleaf(String isleaf) {
        this.isleaf = isleaf;
    }

    public String getOrganfullname() {
        return organfullname;
    }

    public void setOrganfullname(String organfullname) {
        this.organfullname = organfullname;
    }

    public String getIsshow() {
        return isshow;
    }

    public void setIsshow(String isshow) {
        this.isshow = isshow;
    }

    public String getRootid() {
        return rootid;
    }

    public void setRootid(String rootid) {
        this.rootid = rootid;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public Integer getLevelnum() {
        return levelnum;
    }

    public void setLevelnum(Integer levelnum) {
        this.levelnum = levelnum;
    }

    public Integer getLevelord() {
        return levelord;
    }

    public void setLevelord(Integer levelord) {
        this.levelord = levelord;
    }

}