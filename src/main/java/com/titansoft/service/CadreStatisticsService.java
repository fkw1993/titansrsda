package com.titansoft.service;

import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/9/28 16:43
 */
public interface CadreStatisticsService {
    /**
     * @description 干部分类统计 
     * @param  * @param params:  
     * @return  
     * @author Fkw
     * @date 2019/9/28 
     */
    void tjPersonCatalogue(Map<String, String> params);

    /**
     * @description 利用统计 
     * @param  * @param params:  
     * @return  
     * @author Fkw
     * @date 2019/9/28 
     */
    void tjUse(Map<String, String> params);
}
