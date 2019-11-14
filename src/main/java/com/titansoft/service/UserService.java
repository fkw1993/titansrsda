package com.titansoft.service;

import com.titansoft.entity.User;
import com.titansoft.entity.system.Logininfo;

import java.util.List;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/9/11 17:16
 */
public interface UserService {
    /**
     * @description 获取表头
     * @param  
     * @return  
     * @author Fkw
     * @date 2019/9/11 
     */
    String userTableColumns();

    /**
     * @description 通过机构id用户
     * @param  * @param organid:
     * @return
     * @author Fkw
     * @date 2019/9/19
     */
    List getUserByOrganid(String organid);

    /**
     * @description 增加用户表单
     * @param  * @param operate: 操作
     * @param user:
     * @return  
     * @author Fkw
     * @date 2019/9/19 
     */
    String userForm(String operate, User user);

    /**
     * @description 增加用戶
     * @param  * @param user:
     * @param logininfo:
     * @return
     * @author Fkw
     * @date 2019/9/19
     */
    String adduser(User user, Logininfo logininfo);

    /**
     * @description 修改用户
     * @param  * @param user:
     * @param logininfo:
     * @return
     * @author Fkw
     * @date 2019/9/19
     */
    String editUser(User user, Logininfo logininfo);

    /**
     * @description 删除用户
     * @param  * @param userid:  
     * @return  
     * @author Fkw
     * @date 2019/9/19 
     */
    void delUser(String userid);

    /**
     * @description  创建临时用户
     * @param  * @param user: 用户对象
     * @param expiretime:过期时间
     * @return
     * @author Fkw
     * @date 2019/9/27
     */
    void createUser(User user, String expiretime);

    /**
     * @description 获取用户的功能权限树 
     * @param  * @param params:  
     * @return
     * @author Fkw
     * @date 2019/10/18 
     */
    String getSetFunTree(Map<String, Object> params,Logininfo logininfo);

    /**
     * @description  保存功能权限
     * @param  * @param permcodes: 选择的id
     * @param userid: 用户id
     * @param productid: 产品id
     * @return
     * @author Fkw
     * @date 2019/10/19
     */
    void setFun(String permcodes, String userid, String productid);
}
