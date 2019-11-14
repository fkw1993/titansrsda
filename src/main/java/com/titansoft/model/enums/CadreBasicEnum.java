package com.titansoft.model.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/8/10 15:37
 */
public enum CadreBasicEnum {
    basicinfo("basicinfo","t_s_cadre_basicinfo","基本信息",0),
    work("work","t_s_cadre_basic_work","工作简历",10),
    family("family","t_s_cadre_basic_family","家庭主要成员及重要社会关系",7),
    abroad("abroad","t_s_cadre_basic_abroad","出国出境情况",7),
    archivesKeep("archivesKeep","t_s_cadre_basic_archivesKeep","人事档案保管情况",4),
    education("education","t_s_cadre_basic_education","学历情况",4),
    transfer("transfer","t_da_transfer","",0);//转递

    private String type;
    private String tablename; //表名
    private String des;//描述
    private int num;//要显示的行数

    private  CadreBasicEnum(String type,String tablename, String des, int num) {
        this.type=type;
        this.tablename=tablename;
        this.des=des;
        this.num=num;
    }
    private static Map<String,CadreBasicEnum> typecodeMap = new HashMap<String,CadreBasicEnum>();

    public static CadreBasicEnum getEnumByAtype(String atype) {
        if(typecodeMap.get(atype) != null)
        {
            return typecodeMap.get(atype);
        }
        else
        {
            for (CadreBasicEnum c : CadreBasicEnum.values()) {
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
