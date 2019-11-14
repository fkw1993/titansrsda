package com.titansoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/10/4 13:43
 */
public interface CommonMapper<T> extends BaseMapper<T> {
    @Select("select ${column} from ${table}")
    List<Map<String,String>> selectSql(@Param("column")String column,@Param("table")String table);

    @Select("select ${column} from ${table} where ${filter}")
    List<Map<String,String>> selectFilterSql(@Param("column")String column,@Param("table")String table,@Param("filter")String filter);

    @Delete("delete from ${table} where ${filter}")
    int delteteFilterSql(@Param("table")String table,@Param("filter")String filter);

    @Insert("insert into  ${table} values(${values})")
    int insertSql(@Param("table")String table,@Param("values")String values);
}
