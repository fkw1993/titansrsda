package com.titansoft.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 干部档案目录实体类
 *
 * @author jiaogan
 */

@TableName(value = "t_s_catalogue")
public class Catalogue {
    private String id;
    private String sortcode;
    private String idnumber;
    private String catatitle;
    private String pcatacode;
    private String catacode;
    private String catayear;
    private String catamonth;
    private String cataday;
    private String pagenumber;
    private String bak;
    private String sequence;
    private String status;//是否审核入库


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getCatatitle() {
        return catatitle;
    }

    public void setCatatitle(String catatitle) {
        this.catatitle = catatitle;
    }

    public String getCatacode() {
        return catacode;
    }

    public void setCatacode(String catacode) {
        this.catacode = catacode;
    }

    public String getCatayear() {
        return catayear;
    }

    public void setCatayear(String catayear) {
        this.catayear = catayear;
    }

    public String getCatamonth() {
        return catamonth;
    }

    public void setCatamonth(String catamonth) {
        this.catamonth = catamonth;
    }

    public String getCataday() {
        return cataday;
    }

    public void setCataday(String cataday) {
        this.cataday = cataday;
    }

    public String getPagenumber() {
        return pagenumber;
    }

    public void setPagenumber(String pagenumber) {
        this.pagenumber = pagenumber;
    }

    public String getBak() {
        return bak;
    }

    public void setBak(String bak) {
        this.bak = bak;
    }

    public String getSortcode() {
        return sortcode;
    }

    public void setSortcode(String sortcode) {
        this.sortcode = sortcode;
    }

    public String getPcatacode() {
        return pcatacode;
    }

    public void setPcatacode(String pcatacode) {
        this.pcatacode = pcatacode;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
