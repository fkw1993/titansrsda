package com.titansoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titansoft.entity.Unit;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/7/27 15:22
 */
public interface UnitMapper extends CommonMapper<Unit> {
    //通用查询
    @Select("select ${column} from ${table} where ${filter}")
    List<Map<String, Object>> getUnitList(@Param("column")String column,@Param("table")String table,@Param("filter")String filter);

    //通用删除
    @Delete("delete from ${table} where ${filter}")
    void delUnit(@Param("table")String table,@Param("filter")String filter);

    //通用修改
    @Update("update ${tablename} set ${values} where ${filter}")
    void updateUnit(@Param("tablename") String tablename, @Param("values") String values, @Param("filter") String filter);

    //t_da_unitlevel插入
    @Insert("insert into t_da_unitlevel values(${value})")
    void insertUnitLevel(@Param("value") String value);
}
