package com.titansoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titansoft.entity.cadre.CadreTransfer;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Kevin
 * @Date: 2019/9/24 16:07
 */
public interface CadreTransferMapper extends BaseMapper<CadreTransfer> {
    //通用删除
    @Delete("delete from ${table} ")
    void del(@Param("table")String table);

    //通用增加
    @Insert("insert into  ${table} ${value}")
    void insertTransfer(@Param("table")String table,@Param("value")String value);
}
