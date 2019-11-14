package com.titansoft.service;

import com.titansoft.entity.Po;

import java.util.List;

/**理采集，审核，管理公共方法
 * @Author: Kevin
 * @Date: 2019/7/31 15:12
 */
public interface CadreService {
    /**
     * @description 获取树结构
     * @param  * @param treeid:  
     * @return  
     * @author Fkw
     * @date 2019/7/31 
     */
    String tree(String treeid,String roleid);
    
    /**
     * @description 获取干部的基本信息
     * @param  * @param idnumber:  
     * @return  
     * @author Fkw
     * @date 2019/8/10 
     */
    Po getCadreBasicDetail(String idnumber);


    /**
     * @description  获取干部的额外基本信息
     * @param  * @param idnumber:
     * @return
     * @author Fkw
     * @date 2019/8/10
     */
    List<Po> getCadreBasicExtendDetail(String idnumber,  String type);
    
    /**
     * @description  
     * @param  * @param idnumber:  
     * @return  
     * @author Fkw
     * @date 2019/9/25 
     */
    String export(String idnumber);
}
