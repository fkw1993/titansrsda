package com.titansoft.entity.sqcd;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @Author: Kevin
 * @Date: 2019/9/27 14:55
 */
@TableName(value = "t_bus_sqcdpower")
public class SqcdPower {
    private String id;
    private String sqcdid;
    private String sortid;
    private String idnumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSqcdid() {
        return sqcdid;
    }

    public void setSqcdid(String sqcdid) {
        this.sqcdid = sqcdid;
    }

    public String getSortid() {
        return sortid;
    }

    public void setSortid(String sortid) {
        this.sortid = sortid;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }
}
