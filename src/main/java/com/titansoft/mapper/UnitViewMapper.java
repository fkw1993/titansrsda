package com.titansoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titansoft.entity.view.UnitView;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/9/11 11:48
 */
public interface UnitViewMapper extends BaseMapper<UnitView> {
    //通用查询
    @Select("select ${column} from ${table} where ${filter}")
    List<Map<String, Object>> getUnitViewList(@Param("column")String column,@Param("table")String table,@Param("filter")String filter);

    @Select("select max(unitcode) as maxunitcode ,max(levelord) as levelord  from v_unit where parentid is null")
    List<Map<String, Object>> getMaxUnitcodeAndMaxLevelord();

    //根据用户权限读取根单位树
    @Select("select * from v_unit v join T_S_USER_UNIT t on v.id=t.unitid where t.userid in (${userid}) and v.parentid is null  order by levelord")
    List<Map<String,Object>> getUnitByUserid(@Param("userid") String userid);

    //根据用户权限读取子单位树
    @Select("select * from v_unit v join T_S_USER_UNIT t on v.id=t.unitid where  t.userid in (${userid}) and  parentid =#{parentid} order by levelord")
    List<Map<String,Object>> getUnitPartByUserid(@Param("userid")String userid,String parentid);

    //自定义sql
    List<Map<String,Object>> getAllUnit();
}
