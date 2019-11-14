package com.titansoft.service;

import com.titansoft.entity.system.Logininfo;
import com.titansoft.entity.system.Sort;

/**
 * @Author: Kevin
 * @Date: 2019/9/12 9:32
 */
public interface SortService {
    //获取表头
    String sortTableColumns();
    /**
     * @description 获取分类树
     * @param  * @param treeid:  
     * @return  
     * @author Fkw
     * @date 2019/9/12 
     */
    String sortTree(String treeid);

    /**
     * @description 表单
     * @param  * @param add:
     * @param sort:
     * @return
     * @author Fkw
     * @date 2019/9/21
     */
    String sortForm(String operate, Sort sort);

    /**
     * @description 增加分类
     * @param  * @param sort:
     * @param logininfo:
     * @return
     * @author Fkw
     * @date 2019/9/21
     */
    String addsort(Sort sort, Logininfo logininfo);

    /**
     * @description
     * @param  * @param sort: 修改分类
     * @param logininfo:
     * @return
     * @author Fkw
     * @date 2019/9/21
     */
    String editSort(Sort sort, Logininfo logininfo);

    /**
     * @description  删除分类
     * @param  * @param id:
     * @return
     * @author Fkw
     * @date 2019/9/21
     */
    void delSort(String id);

    /**
     * @description 校验分类最多只能有二级分类 
     * @param  * @param sort:  
     * @return  
     * @author Fkw
     * @date 2019/9/21 
     */
    Boolean checkAdd(Sort sort);

    /**
     * @description 获取排序页面 
     * @param  * @param parentid:  
     * @return  
     * @author Fkw
     * @date 2019/9/21 
     */
    String getSort(String parentid);

    /**
     * @description 保存排序 
     * @param  * @param organid:  
     * @return  
     * @author Fkw
     * @date 2019/9/21 
     */
    void saveSort(String organid);
}
