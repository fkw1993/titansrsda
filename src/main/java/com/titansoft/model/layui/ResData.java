package com.titansoft.model.layui;

/**
 * @Author: Kevin
 * @Date: 2019/11/12 16:32
 */
public class ResData {
    /** 状态类*/
    private Status status;
    /** 数据*/
    private Object data;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
