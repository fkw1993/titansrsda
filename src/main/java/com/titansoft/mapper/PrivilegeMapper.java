package com.titansoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titansoft.entity.system.Privilege;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/7/26 16:36
 */
public interface PrivilegeMapper extends BaseMapper<Privilege> {
    //删除，调用时注意进行防sql注入的过滤
    @Delete("delete from ${tablename} where ${filter}")
    void deleteSql(@Param("tablename") String tablename, @Param("filter") String filter);

    //调用时注意进行防sql注入的过滤
    @Select("select * from ${tablename} where ${filter}")
    List<Map<String, Object>> selectSql(@Param("tablename") String tablename, @Param("filter") String filter);

    //调用时注意进行防sql注入的过滤
    @Update("update ${tablename} set ${value} where ${filter}")
    void updateSql(@Param("tablename") String tablename, @Param("value") String value, @Param("filter") String filter);

    //获取所有功能
    @Select("select code,text name,privilegeid,eventname,iconcls,recommend,parentid,productid,flag,uri,checkurl,groupname from v_Privilege order by productid,levelnum,levelord")
    List<Privilege> getAllPrivilege();

    //获取系统安全维护菜单
    @Select("select DISTINCT p.privilegeid,p.parentid,p.code,p.text,p.uri,p.iconcls,p.levelnum,p.levelord  from v_Privilege p, v_Gnqx g where p.privilegeid = g.privilegeid and p.productid=#{productid} and g.userid in (${roleid})  and p.PARENTID is null order by p.levelord ")
    List<Map<String, Object>> getMenu(@Param("productid") String productid, @Param("roleid") String roleid);

    //获取人事档案管理模块
    @Select("select DISTINCT p.privilegeid,p.parentid,p.code,p.text,p.uri,p.iconcls,p.levelnum ,p.levelord from v_Privilege p, v_Gnqx g where p.privilegeid = g.privilegeid and p.productid=#{productid}  and g.userid in (${roleid})  and p.PARENTID is null order by p.levelord")
    List<Map<String, Object>> getModule(@Param("productid") String productid, @Param("roleid") String roleid);

    //获取显示首页人事档案管理模块子功能
    @Select("select DISTINCT p.* from v_Privilege p, v_Gnqx g where p.privilegeid = g.privilegeid and p.productid='5BCD3977A26DC04B98A5A09224A76FB7'   and g.userid in (select roleid from T_S_UR where userid =#{userid} union select #{userid} from  t_s_ur) and p.PARENTID is null order by p.levelord ")
    List<Map<String, Object>> getUserPersonFun(String userid);

    //根据条件获取产品
    @Select("SELECT * FROM t_s_product p WHERE p.isdisplay='1' and p.productid IN " +
            " ((select sp.productid from t_s_privilege sp,t_s_up up where  sp.privilegeid=up.privilegeid and up.userid=#{userid} GROUP BY sp.productid)" +
            " union ( SELECT c.productid FROM t_s_privilege c where c.privilegeid in  ( SELECT b.privilegeid FROM t_s_ur a LEFT JOIN t_s_rp b ON a.roleid = b.roleid WHERE a.userid=#{userid} ) GROUP BY c.productid))" +
            " order by p.sequence")
    List<Map<String, Object>> getUserProduts(String userid);

    //获取该用户拥有权限的product信息
    @Select("SELECT * FROM t_s_product p WHERE p.isdisplay='1' and p.productid IN " + " ((select sp.productid from t_s_privilege sp,t_s_up up where  sp.privilegeid=up.privilegeid and up.userid=#{userid} GROUP BY sp.productid)" + " union ( SELECT c.productid FROM t_s_privilege c where c.privilegeid in  ( SELECT b.privilegeid FROM t_s_ur a LEFT JOIN t_s_rp b ON a.roleid = b.roleid WHERE a.userid=#{userid} ) GROUP BY c.productid))" + " order by p.sequence")
    List<Map<String, Object>> getUserProdutsByPower(String userid);

    //初始化产品的所有按钮信息到内存
    @Select("select code,text,privilegeid,eventname,iconcls,recommend groupname,parentid from v_Privilege where flag='1' and productid =#{productid} order by parentid,LEVELORD")
    List<Map<String, Object>> getInitButtons(String productid);

    //根据用户获取各个模块的菜单
    @Select("select distinct p.* from v_Privilege p, v_Gnqx g where p.privilegeid = g.privilegeid and p.productid =#{productid}  and p.parentid is null  and g.userid in (${roleid})  and (p.LEVELNUM=1 or p.LEVELNUM=2) order by p.levelnum,p.levelord")
    List<Map<String, Object>> getMenuByModule(String productid, @Param("roleid") String roleid);

    //根据用户获取各个模块的菜单
    @Select("select distinct * from v_Privilege where ${filter}")
    List<Map<String, Object>> getPrivilegeByParentid(@Param("filter") String filter);

    //获取功能视图
    @Select("select ${column}  from v_Privilege where ${filter}")
    List<Map<String, Object>> getViewPrivilegeCount(@Param("column") String column, @Param("filter") String filter);

    //插入表t_s_privilegeex
    @Insert("insert into t_s_privilegeex (privilegeid,flag,eventname,iconcls,groupname) values(#{privilegeid},#{flag},#{eventname},#{iconcls},#{groupname})")
    void insertPrivilegeex(String privilegeid, String flag, String eventname, String iconcls, String groupname);

    //插入表t_s_privlevel
    @Insert("insert into t_s_privlevel (privilegeid,rootid,parentid,levelnum,levelord) values(#{privilegeid},#{rootid},#{parentid},#{levelnum},#{levelord})")
    void insertPrivilevel(String privilegeid, String rootid, String parentid, String levelnum, String levelord);
}
