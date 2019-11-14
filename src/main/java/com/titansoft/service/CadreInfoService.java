package com.titansoft.service;

import com.titansoft.entity.Po;
import com.titansoft.model.PoList;
import com.titansoft.entity.system.Logininfo;

import java.util.List;

/**
 * @Author: Kevin
 * @Date: 2019/10/2 8:57
 */
public interface CadreInfoService {
    /**
     * @description 增加干部基本信息
     * @param  * @param null:
     * @return
     * @author Fkw
     * @date 2019/10/2
     */
    String addCadreBasicInfo(String treeid,  PoList list, Logininfo logininfo);


    /**
     * @description 修改干部基本信息
     * @param  * @param null:
     * @return
     * @author Fkw
     * @date 2019/10/2
     */
    String editCadreBasicInfo(String treeid, PoList poList, Logininfo logininfo);

    /**
     * @description 获取干部基本信息
     * @param  * @param idnumber:
     * @return
     * @author Fkw
     * @date 2019/10/3
     */
    Po getBasicPo(String idnumber);

    /**
     * @description  获取干部其他额外信息
     * @param  * @param archivesKeep:
     * @param idnumber:
     * @param count:
     * @return
     * @author Fkw
     * @date 2019/10/3
     */
    List<Po> getBasicItem(String type, String idnumber,int count);

    /**
     * @description  获取干部扩展信息
     * @param  * @param degree:
     * @param idnumber:
     * @return
     * @author Fkw
     * @date 2019/10/3
     */
    Po getExtendPo(String type, String idnumber);

    /**
     * @description 增加扩展信息
     * @param  * @param po: 
 * @param tablename:  
     * @return  
     * @author Fkw
     * @date 2019/10/3 
     */
    void addCadreExtendInfo(Po po, String tablename);

    /**
     * @description  修改扩展信息
     * @param  * @param null:
     * @return
     * @author Fkw
     * @date 2019/10/3
     */
    void editCadreExtendInfo(Po po, String tablename);
}
