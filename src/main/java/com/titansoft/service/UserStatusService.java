package com.titansoft.service;

import com.titansoft.entity.UserStatus;
import com.titansoft.entity.system.Logininfo;

/**
 * @Author: Kevin
 * @Date: 2019/9/12 14:47
 */
public interface UserStatusService {
    /**
     * @description 获取表头 
     * @param  
     * @return  
     * @author Fkw
     * @date 2019/9/12 
     */
    String userStatusTableColumns();
    /**
     * @description 树结构
     * @param  
     * @return  
     * @author Fkw
     * @date 2019/9/12 
     */
    String userStatusTree();

    /**
     * @description 用户状态表单 
     * @param  * @param add: 
     * @param userstatus:  
     * @return  
     * @author Fkw
     * @date 2019/9/21 
     */
    String userStatusForm(String operate, UserStatus userstatus);

    /**
     * @description 增加用户状态 
     * @param  * @param userstatus: 
     * @param logininfo:  
     * @return  
     * @author Fkw
     * @date 2019/9/21 
     */
    String addUserStatus(UserStatus userstatus, Logininfo logininfo);

    /**
     * @description 修改用户状态 
     * @param  * @param userstatus: 
     * @param logininfo:  
     * @return  
     * @author Fkw
     * @date 2019/9/21 
     */
    String editUserstatus(UserStatus userstatus, Logininfo logininfo);

    /**
     * @description 删除用户状态
     * @param  * @param id:  
     * @return  
     * @author Fkw
     * @date 2019/9/21 
     */
    String delUserstatus(String id);
}
