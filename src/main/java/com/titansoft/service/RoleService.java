package com.titansoft.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.titansoft.entity.Role;
import com.titansoft.entity.system.Logininfo;

import java.util.List;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/9/11 18:18
 */
public interface RoleService  {
    String roleTableColumns();

    /**
     * @description 角色表单
     * @param  * @param operate:
     * @param role:
     * @return
     * @author Fkw
     * @date 2019/9/20
     */
    String roleForm(String operate, Role role);

    /**
     * @description 增加角色
     * @param  * @param role:
     * @param logininfo:
     * @return
     * @author Fkw
     * @date 2019/9/20
     */
    String addRole(Role role, Logininfo logininfo);

    /**
     * @description 修改角色
     * @param  * @param role:
     * @param logininfo:
     * @return
     * @author Fkw
     * @date 2019/9/20
     */
    String editRole(Role role, Logininfo logininfo);

    /**
     * @description 删除角色 
     * @param  * @param roleid:  
     * @return  
     * @author Fkw
     * @date 2019/9/20 
     */
    void delRole(String roleid);

    /**
     * @description  * 获取可选角色
     * @param  * @param userid:
     * @return
     * @author Fkw
     * @date 2019/10/21
     */
    String getAllRole(String userid);

    /**
     * @description  * 获取已选角色
     * @param  * @param userid:
     * @return
     * @author Fkw
     * @date 2019/10/21
     */
    String getSelRole(String userid);

    /**
     * @description 保存已选角色
     * @param  * @param roleids:
     * @param userid:
     * @return
     * @author Fkw
     * @date 2019/10/21
     */
    void saveSelRole(String roleids, String userid);

    /**
     * @description 获取角色功能树
     * @param  * @param params:
     * @param logininfo:
     * @return
     * @author Fkw
     * @date 2019/10/21
     */
    String getSetFunTree(Map<String, Object> params, Logininfo logininfo);

    /**
     * @description 保存角色的功能树
     * @param  * @param permcodes:
     * @param roleid:
     * @param productid:
     * @return
     * @author Fkw
     * @date 2019/10/21
     */
    void setFun(String permcodes, String roleid, String productid);

    /**
     * @description 获取机构用户树
     * @param  * @param organid:
 * @param roleid:
     * @return
     * @author Fkw
     * @date 2019/10/25
     */
    List getOrganUserTree(String organid, String roleid);

    /**
     * @description  保存设置的用户组
     * @param  * @param organid:
     * @param userids:
     * @param roleid:
     * @return
     * @author Fkw
     * @date 2019/10/25
     */
    void saveSetRole(String organid, String userids, String roleid);
}
