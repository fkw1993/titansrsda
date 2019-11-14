package com.titansoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titansoft.entity.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/7/26 19:39
 */
public interface RoleMapper extends CommonMapper<Role> {
    //通用删除
    @Delete("delete from ${table} where ${filter}")
    void delRole(@Param("table")String table,@Param("filter")String filter);

    //根据用户名获取权限id
    @Select("select * from T_S_UR where userid in ( select userid from  t_s_user where code=#{username} )")
    List<Map<String,Object>> getRoleIdByUsername(String username);

    //获取数据权限id
    @Select("select distinct unitid from T_S_ROLE_UNIT where roleid in (${roleid}) UNION select distinct unitid from T_S_USER_UNIT where USERID in (${roleid})")
    List<Map<String,Object>> getDataIdByRoleId(@Param("roleid") String roleid);

}
