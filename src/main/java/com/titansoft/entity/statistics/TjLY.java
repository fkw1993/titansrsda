package com.titansoft.entity.statistics;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

//利用统计
@TableName(value = "t_tj_ly")
public class TjLY {
    @TableId
    private String id;

    private String unitcount;// 单位申请量

    private String applycount;// 申请人次

    private String cadrecount;// 被查阅干部数

    private String cdscenecount;//现场查阅量

    private String cdmobilecount;//手机预约量

    private String borrowcount;//查（借）阅量

    private String copycount;//复制量

    private String excerptcount;//摘录量

    private String time;//统计时间

    public String getUnitcount() {
        return unitcount;
    }

    public void setUnitcount(String unitcount) {
        this.unitcount = unitcount;
    }

    public String getApplycount() {
        return applycount;
    }

    public void setApplycount(String applycount) {
        this.applycount = applycount;
    }

    public String getCadrecount() {
        return cadrecount;
    }

    public void setCadrecount(String cadrecount) {
        this.cadrecount = cadrecount;
    }

    public String getCdscenecount() {
        return cdscenecount;
    }

    public void setCdscenecount(String cdscenecount) {
        this.cdscenecount = cdscenecount;
    }

    public String getCdmobilecount() {
        return cdmobilecount;
    }

    public void setCdmobilecount(String cdmobilecount) {
        this.cdmobilecount = cdmobilecount;
    }

    public String getBorrowcount() {
        return borrowcount;
    }

    public void setBorrowcount(String borrowcount) {
        this.borrowcount = borrowcount;
    }

    public String getCopycount() {
        return copycount;
    }

    public void setCopycount(String copycount) {
        this.copycount = copycount;
    }

    public String getExcerptcount() {
        return excerptcount;
    }

    public void setExcerptcount(String excerptcount) {
        this.excerptcount = excerptcount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
