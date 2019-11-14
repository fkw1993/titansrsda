package com.titansoft.service;

import com.titansoft.entity.system.Logininfo;

/**
 * @Author: Kevin
 * @Date: 2019/8/10 15:27
 */
public interface CadreSHService {
    /**
     * @param *              @param isselectAll:
     * @param idnumber:
     * @param name:
     * @param operationType:
     * @param logininfo:
     * @return
     * @description 审核入库
     * @author Fkw
     * @date 2019/8/12
     */
    String  cadreinfoCheck(String isselectAll, String idnumber, String name, String operationType, Logininfo logininfo);
}
