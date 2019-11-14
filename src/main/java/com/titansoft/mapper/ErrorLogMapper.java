package com.titansoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titansoft.entity.log.ErrorLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/8/30 11:36
 */
public interface ErrorLogMapper extends BaseMapper<ErrorLog>{

    @Select("SELECT MONTH,COUNT(MONTH)  as count  FROM (SELECT SUBSTR(DOTIME, 6, 2)as month FROM ${tablename} WHERE DOTIME LIKE '${year}%' AND ERRORTYPE = #{errortype}) MONTHS GROUP BY MONTH order by MONTH")
    List<Map<String,Object>> getErrorLogEqErrortype(@Param("tablename") String tablename,@Param("year") String year, @Param("errortype") String errortype);

    @Select("SELECT MONTH,COUNT(MONTH)  as count  FROM (SELECT SUBSTR(DOTIME, 6, 2)as month FROM ${tablename} WHERE DOTIME LIKE '${year}%' AND ERRORTYPE <>#{errortype}) MONTHS GROUP BY MONTH order by MONTH")
    List<Map<String,Object>>  getErrorLogNoEqErrortype(@Param("tablename") String tablename,@Param("year") String year,@Param("errortype") String errortype);

}
