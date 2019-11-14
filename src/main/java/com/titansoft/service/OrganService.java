package com.titansoft.service;

import com.titansoft.entity.Organ;
import com.titansoft.entity.system.Logininfo;

import java.util.List;

/**
 * @Author: Kevin
 * @Date: 2019/7/26 18:44
 */
public interface OrganService {
    /**
     * @description 加载用户组织树
     * @param  * @param null:
     * @return
     * @author Fkw
     * @date 2019/9/11
     */
    String organTree(String organid);

    /**
     * @description 获取表头
     * @param  
     * @return  
     * @author Fkw
     * @date 2019/9/11 
     */
    String organTableColumns();

    /**
     * @description 机构表单
     * @param  * @param operate:操作
 * @param organ:  
     * @return  
     * @author Fkw
     * @date 2019/9/18 
     */
    String organForm(String operate, Organ organ);

    /**
     * @description 增加机构
     * @param  * @param null:
     * @return
     * @author Fkw
     * @date 2019/9/18
     */
    void addOrgan(Organ organ, Logininfo logininfo);
    
    /**
     * @description 修改机构
     * @param  * @param organ:  
     * @return  
     * @author Fkw
     * @date 2019/9/19 
     */
    String editOrgan(Organ organ,Logininfo logininfo);
    /**
     * @description 删除机构 
     * @param  * @param organid:  
     * @return  
     * @author Fkw
     * @date 2019/9/19 
     */
    void delOrgan(String organid);

    /**
     * @description 获取子机构 
     * @param  * @param organid:  
     * @return  
     * @author Fkw
     * @date 2019/9/19 
     */
    List checkSubOrgan(String organid);

    /**
     * @description 获取排序页面 
     * @param  * @param parentid:  
     * @return  
     * @author Fkw
     * @date 2019/9/19 
     */
    String getSort(String parentid);

    /**
     * @description 保存排序
     * @param  * @param organid:  
     * @return
     * @author Fkw
     * @date 2019/9/19 
     */
    void saveSort(String organid);
}
