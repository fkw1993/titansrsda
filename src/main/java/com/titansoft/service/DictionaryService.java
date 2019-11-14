package com.titansoft.service;

import com.titansoft.entity.Dictionary;
import com.titansoft.entity.system.Logininfo;

import java.util.List;
import java.util.Map;

/**数据字典
 * @Author: Kevin
 * @Date: 2019/7/27 16:13
 */
public interface DictionaryService {
    Map<String, List> getDictionaryList();

    /**
     * @description 数据字典树
     * @param
     * @return
     * @author Fkw
     * @date 2019/9/21
     */
    String dictionaryTree(String treeid);

    /**
     * @description 数据字典表头
     * @param
     * @return
     * @author Fkw
     * @date 2019/9/21
     */
    String dictionaryTableColumns();

    /**
     * @description 数据字典表格数据
     * @param
     * @return
     * @author Fkw
     * @date 2019/9/21
     */
    String dictionaryForm(String operate, Dictionary dictionary);

    /**
     * @description  增加数据字典
     * @param  * @param dictionary:
     * @param logininfo:
     * @return
     * @author Fkw
     * @date 2019/9/21
     */
    String addDictionary(Dictionary dictionary, Logininfo logininfo);

    /**
     * @description  修改数据字典
     * @param  * @param dictionary:
     * @param logininfo:
     * @return
     * @author Fkw
     * @date 2019/9/21
     */
    String editDictionary(Dictionary dictionary, Logininfo logininfo);

    /**
     * @description 删除数据字典
     * @param  * @param null:
     * @return
     * @author Fkw
     * @date 2019/9/21
     */
    String delDictionary(String id);

    /**
     * @description  
     * @param  * @param dictionary:  
     * @return  
     * @author Fkw
     * @date 2019/9/21 
     */
    Boolean checkAdd(Dictionary dictionary);

    /**
     * @description 获取排序数据 
     * @param  * @param parentid:  
     * @return  
     * @author Fkw
     * @date 2019/9/21 
     */
    String getSort(String parentid);

    /**
     * @description 保存排序
     * @param  * @param ids:  
     * @return  
     * @author Fkw
     * @date 2019/9/21 
     */
    void saveSort(String ids);
}
