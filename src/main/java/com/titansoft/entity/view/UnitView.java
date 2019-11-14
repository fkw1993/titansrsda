package com.titansoft.entity.view;

import com.baomidou.mybatisplus.annotation.TableName;

/**单位视图
 * @Author: Kevin
 * @Date: 2019/9/11 11:42
 */
@TableName(value = "v_unit")
public class UnitView {
    private  String id;
    private  String code;
    private  String text;
    private  String rootid;
    private  String parentid;
    private  String levelnum;
    private  String levelord;
    private  String unitcode;
    private  String unitfullname;
    private  String isleaf;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIsleaf() {
        return isleaf;
    }

    public void setIsleaf(String isleaf) {
        this.isleaf = isleaf;
    }
}
