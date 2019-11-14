package com.titansoft.entity.sqcd;

import com.baomidou.mybatisplus.annotation.TableName;

/**申请查档被分配允许查看的电子材料
 * @Author: Kevin
 * @Date: 2019/10/11 17:22
 */
@TableName(value = "t_bus_sqcdstore")
public class SqcdStore {
    private String id;
    private String sqcdid;//对应Sqcd的ID
    private String storeid;//
    private String idnumber;//身份证号码

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

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }
}
