package com.titansoft.entity.system;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 系统产品
 */
@TableName(value = "t_s_product")
public class Product {
    @TableId
    private String productid;
    private String code;
    private String name;
    private String description;
    private String uri;
    private String createtime;
    private String createuser;
    private String modifytime;
    private String modifyuser;
    private String status;
    private String isdisplay;
    private String indexpage;
    private String icon;
    private String sequence;
    private String systemstyle; //系统主题
    public static int productlevel = 3; // { 0, "试用版" }, { 1, "标准版" }, { 2, "高级版" }, { 3, "综合版" }

    public String getSystemstyle() {
        return systemstyle;
    }

    public void setSystemstyle(String systemstyle) {
        this.systemstyle = systemstyle;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
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

    public String getIsdisplay() {
        return isdisplay;
    }

    public void setIsdisplay(String isdisplay) {
        this.isdisplay = isdisplay;
    }

    public String getIndexpage() {
        return indexpage;
    }

    public void setIndexpage(String indexpage) {
        this.indexpage = indexpage;
    }

}
