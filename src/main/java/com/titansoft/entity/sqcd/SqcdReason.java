package com.titansoft.entity.sqcd;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @description 查档事由
 * @author Fkw
 * @date 2019/10/11
 */
@TableName(value = "t_s_sqcdreason")
public class SqcdReason {
    @TableId
    private String id;
    private String reason;
    private Integer sequence;

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

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
}
