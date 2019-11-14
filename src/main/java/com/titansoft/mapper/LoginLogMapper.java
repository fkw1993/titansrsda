package com.titansoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titansoft.entity.log.LoginLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/8/15 18:04
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {
    @Select("SELECT month,COUNT(MONTH) as count FROM (SELECT SUBSTR(DOTIME, 6, 2)as month FROM ${tablename} WHERE DOTIME LIKE '${year}%' AND  DODESC LIKE '${filerStr}%' ) MONTHS GROUP BY MONTH order by MONTH")
    List<Map<String,Object>>  getLoginLogCount(@Param("tablename") String tablename, @Param("year") String year, @Param("filerStr") String filerStr);


}
