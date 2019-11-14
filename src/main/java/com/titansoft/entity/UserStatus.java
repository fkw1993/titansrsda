package com.titansoft.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 干部状态
 */
@TableName(value = "t_s_userstatus")
public class UserStatus {
    @TableId
    private String id;
    private String name;//名称
    private String fullname;
    private String description;
    private Integer sequence;

    public UserStatus() {
        super();
    }

    public UserStatus(String id, String name, String fullname, String description, Integer sequence) {
        super();
        this.id = id;
        this.name = name;
        this.fullname = fullname;
        this.description = description;
        this.sequence = sequence;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

   

}
