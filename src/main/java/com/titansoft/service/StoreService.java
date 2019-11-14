package com.titansoft.service;

import com.titansoft.entity.media.Store;
import com.titansoft.entity.system.Logininfo;

/**
 * @Author: Kevin
 * @Date: 2019/9/21 16:28
 */
public interface StoreService {
    /**
     * @description 获取表头
     * @param  
     * @return  
     * @author Fkw
     * @date 2019/9/21 
     */
    String storeTableColumns();

    /**
     * @description 增加存储信息
     * @param  * @param store:
     * @param logininfo:
     * @return
     * @author Fkw
     * @date 2019/9/21
     */
    String addStore(Store store, Logininfo logininfo);

   /**
    * @description  表单
    * @param  * @param null:
    * @return
    * @author Fkw
    * @date 2019/9/21
    */
    String storeForm(String operate, Store store);

    /**
     * @description 修改存储信息
     * @param  * @param store:
     * @param logininfo:
     * @return
     * @author Fkw
     * @date 2019/9/21
     */
    String editStore(Store store, Logininfo logininfo);

    /**
     * @description 删除存储信息
     * @param  * @param store:
     * @return
     * @author Fkw
     * @date 2019/9/21
     */
    String delStore(String id);
}
