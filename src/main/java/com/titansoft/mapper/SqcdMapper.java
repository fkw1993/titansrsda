package com.titansoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titansoft.entity.sqcd.Sqcd;

/**
 * @Author: Kevin
 * @Date: 2019/7/27 11:05
 */
public interface SqcdMapper extends BaseMapper<Sqcd> {
    /*@Select("<script>"
            + "<if test='dataSource=mysql'>"
            +"select * from t_bus_sqcd where  approvalstate='待处理'   order by applydate desc limit 5"
            + "</if>"
            + "<if test='dataSource=sqlserver'>"
            +"select top 5 * from t_bus_sqcd where  approvalstate='待处理'   order by applydate desc"
            + "</if>"
            +"</script>"
    )
    List<Sqcd> getSqcdList(@Param(value="dataSource")String dataSource);*/



}
