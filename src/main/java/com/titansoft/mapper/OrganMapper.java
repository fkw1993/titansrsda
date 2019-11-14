package com.titansoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titansoft.entity.Organ;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/7/25 15:45
 */
public interface OrganMapper extends BaseMapper<Organ> {
    //通用查询
    @Select("select ${column} from ${table} where ${filter}")
    List<Map<String, Object>> getOrganList(@Param("column")String column, @Param("table")String table, @Param("filter")String filter);

    //通用删除
    @Delete("delete from ${table} where ${filter}")
    void delOrgan(@Param("table")String table,@Param("filter")String filter);

    //通用修改
    @Update("update ${tablename} set ${values} where ${filter}")
    void updateOrgan(@Param("tablename") String tablename, @Param("values") String values, @Param("filter") String filter);

    //t_s_organlevel插入
    @Insert("insert into t_s_organlevel values(${value})")
    void insertOrganLevel(@Param("value") String value);
}