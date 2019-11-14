package com.titansoft.entity.statistics;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


@TableName(value = "t_tj_lyreason")
public class TjLYReason {
    @TableId
    private String id;
    private String reason;
    private String unitcount;
    private String applycount;
    private String cadrecount;
    private static final String tablenameofpo = "t_tj_lyreason";//对应数据库表名

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

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

    public static String getTablenameofpo() {
        return tablenameofpo;
    }

}
