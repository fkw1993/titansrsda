package com.titansoft.service;

import com.titansoft.entity.system.Logininfo;
import com.titansoft.entity.system.Privilege;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/7/27 20:45
 */
public interface PrivilegeService {

    /**
     * @description  初始化产品的所有按钮信息到内存 
     * @param  * @param productid:  
     * @return  
     * @author Fkw
     * @date 2019/7/30
     */
    void initButtonsMap(String productid);

    /**
     * @description 根据modulcode获取按钮
     * @param  * @param modulecode:
    * @param logininfo:
     * @return
     * @author Fkw
     * @date 2019/7/31
     */
    List<Map<String, String>> moduloldprivilege(String modulecode, Logininfo logininfo);
    /**
     * @description 增加
     * @param  * @param privilege:  
     * @return  
     * @author Fkw
     * @date 2019/9/7 
     */
    void addPrivilege(Privilege privilege,Logininfo logininfo);
    /**
     * @description 删除
     * @param  * @param privilege: 
 * @param logininfo:  
     * @return  
     * @author Fkw
     * @date 2019/9/7 
     */
    void delPrivilege(Privilege privilege, Logininfo logininfo);
    /**
     * @description  获取功能对象
     * @param  * @param privilegeid:
     * @return
     * @author Fkw
     * @date 2019/9/9
     */
    Privilege getPrivilege(String privilegeid);
    /**
     * @description 修改功能
     * @param  * @param privilege: 
 * @param logininfo:  
     * @return  
     * @author Fkw
     * @date 2019/9/9 
     */
    void editPrivilege(Privilege privilege, Logininfo logininfo);
    /**
     * @description 获取功能的排序 
     * @param  * @param privilege:  
     * @return  
     * @author Fkw
     * @date 2019/9/10 
     */
    String getPrivilegeSort(Privilege privilege);

    /**
     * @description 保存排序数据
     * @param  * @param privilege:  
     * @return  
     * @author Fkw
     * @date 2019/9/10 
     */
    void savePrivilegeSort(String  privilegeids, Logininfo logininfo);

    /**
     * @description 根据ID获取功能下的子功能
     * @param  * @param privilege:
     * @param logininfo:
     * @return
     * @author Fkw
     * @date 2019/9/11
     */
    String modulprivilege(Privilege privilege, Logininfo logininfo);
    
    /**
     * @description 获取系统各个模块名 
     * @param  
     * @return  
     * @author Fkw
     * @date 2019/9/11 
     */
    String getProductsCombox();
    
    /**
     * @description 获取表头 
     * @param  
     * @return  
     * @author Fkw
     * @date 2019/9/11 
     */
    String privilegeTableColumns();
    
    /**
     * @description  
     * @param  * @param privilege:  
     * @return  
     * @author Fkw
     * @date 2019/9/11 
     */
    String privilegeTree(Privilege privilege);

    /**
     * @description 表单
     * @param  * @param operate: 操作
     * @param privilege:
     * @return  
     * @author Fkw
     * @date 2019/9/11 
     */
    String privilegeForm(String operate, Privilege privilege);
}
