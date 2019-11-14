package com.titansoft.service;

import com.titansoft.entity.Unit;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.model.Exttreenot;

import java.util.List;

/**
 * @Author: Kevin
 * @Date: 2019/7/27 15:19
 */
public interface UnitService {
    /**
     * @description 获取单位树
     * @param  * @param parentid:  父节点
     * @return
     * @author Fkw
     * @date 2019/7/27
     */
    List<Exttreenot> unitTree(String parentid);

    /**
     * @description 获取表头
     * @param  
     * @return  
     * @author Fkw
     * @date 2019/9/11 
     */
    String unitTableColumns();
    
    /**
     * @description
     * @param  * @param operate: 
    * @param unit:
     * @return  
     * @author Fkw
     * @date 2019/9/16 
     */
    String unitForm(String operate, Unit unit);

    /**
     * @description  增加单位
     * @param  * @param unit:
     * @param logininfo:
     * @return
     * @author Fkw
     * @date 2019/9/17
     */
    void addUnit(Unit unit, Logininfo logininfo);

    /**
     * @description  删除单位
     * @param  * @param unitid:
     * @return
     * @author Fkw
     * @date 2019/9/17
     */
    String delUnit(String unitid);
    
    /**
     * @description 修改单位
     * @param  
     * @return  
     * @author Fkw
     * @date 2019/9/17 
     */
    String editUnit(Unit unit);

    /**
     * @description 获取当前排序 
     * @param  * @param parentid:  
     * @return  
     * @author Fkw
     * @date 2019/9/18 
     */
    String getSort(String parentid);

    /**
     * @description 保存排序 
     * @param  * @param unitid:  
     * @return  
     * @author Fkw
     * @date 2019/9/18 
     */
    String saveSort(String unitid);

    /**
     * @description 获取单位列表
     * @param  * @param parentid:
     * @return
     * @author Fkw
     * @date 2019/9/28
     */
    List getUnitname(String parentid);

    /**
     * @description 获取部门列表
     * @param  * @param unitid:
     * @return
     * @author Fkw
     * @date 2019/9/28
     */
    List getDepartname(String unitid);

    /**
     * @description 根据用户id或者角色id获取数据权限
     * @param  * @param id: 
 * @param idFlag:  
     * @return  
     * @author Fkw
     * @date 2019/10/19 
     */
    String getAll(String id, int idFlag);

    /**
     * @description 保存数据权限
     * @param  * @param userid: 
 * @param permcodes:  
     * @return  
     * @author Fkw
     * @date 2019/10/19 
     */
    void saveDataPower(String userid, String permcodes,String idFlag);
}
