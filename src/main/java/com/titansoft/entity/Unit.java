package com.titansoft.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


/**
 * 单位信息
 *
 * @author Administrator
 */
@TableName(value = "t_da_unit")
public class Unit {
    @TableId("unitid")
    private String unitid;
    private String code;//代号
    private String name;//名称
    private String description;//描述
    private String createtime;//创建时间
    private String createuser;//创建者
    private String modifytime;//修改时间
    private String modifyuser;//修改者
    private String unitcode;//组织机构代号
    private String unitfullname;//组织机构全称
    private String isleaf;
    @TableField(exist = false)
    private String rootid;//根节点id
    @TableField(exist = false)
    private String parentid;//父节点id
    @TableField(exist = false)
    private String levelnum;//
    @TableField(exist = false)
    private String levelord;
    @TableField(exist = false)
    private String isshow;


    public String getIsshow() {
        return isshow;
    }

    public void setIsshow(String isshow) {
        this.isshow = isshow;
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


    public String getIsleaf() {
        return isleaf;
    }

    public void setIsleaf(String isleaf) {
        this.isleaf = isleaf;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public String getUnitcode() {
        return unitcode;
    }

    public void setUnitcode(String unitcode) {
        this.unitcode = unitcode;
    }

    public String getUnitfullname() {
        return unitfullname;
    }

    public void setUnitfullname(String unitfullname) {
        this.unitfullname = unitfullname;
    }



}
