package com.titansoft.model.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/10/3 18:43
 */
public enum CadreExtendEnum {
    job("job", "t_s_cadre_job", "基本信息"),
    degree("degree", "t_s_cadre_degree", "基本信息"),
    nationality("nationality", "t_s_cadre_nationality", "基本信息"),
    resume("resume", "t_s_cadre_resume", "基本信息"),
    technical("technical", "t_s_cadre_technical", "基本信息");


    private String type;
    private String tablename; //表名
    private String des;//描述

    private CadreExtendEnum(String type, String tablename, String des) {
        this.type = type;
        this.tablename = tablename;
        this.des = des;
    }

    private static Map<String, CadreExtendEnum> typecodeMap = new HashMap<String, CadreExtendEnum>();

    public static CadreExtendEnum getEnumByAtype(String atype) {
        if (typecodeMap.get(atype) != null) {
            return typecodeMap.get(atype);
        } else {
            for (CadreExtendEnum c : CadreExtendEnum.values()) {
                if (c.getType().equalsIgnoreCase(atype)) {
                    return c;
                }
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
    }
