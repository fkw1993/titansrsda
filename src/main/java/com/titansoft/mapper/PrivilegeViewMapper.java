package com.titansoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titansoft.entity.view.PrivilegeView;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/8/31 16:38
 */
public interface PrivilegeViewMapper extends BaseMapper<PrivilegeView> {
    //自定义sql
    List<Map<String,Object>> getPowerByUserId(@Param("userid") String userid, @Param("hiddenfilter") String hiddenfilter, @Param("productid") String productid,@Param("vprivilegetable") String vprivilegetable,@Param("uptable") String uptable);

    //自定义sql
    List<Map<String,Object>> getPowerByRoleId(@Param("roleid") String roleid,@Param("productid") String productid,@Param("hiddenfilter") String hiddenfilter,@Param("vprivilegetable") String vprivilegetable,@Param("rptable") String rptable);
}
