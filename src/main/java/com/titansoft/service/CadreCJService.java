package com.titansoft.service;

import com.titansoft.entity.system.Logininfo;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.runtime.Log;

import java.util.Map;

/**人事档案采集Service
 * @Author: Kevin
 * @Date: 2019/7/31 14:41
 */
public interface CadreCJService {
    /**
     * @description 判断是否符合提交审核的条件 
     * @param  * @param idnumber:  
     * @return  
     * @author Fkw
     * @date 2019/8/16 
     */
    String checkAudit(String idnumber);
    /**
     * @description 提交审核 
     * @param  * @param idnumber: 
 * @param logininfo:  
     * @return  
     * @author Fkw
     * @date 2019/8/16 
     */
    void shenhe(String idnumber, Logininfo logininfo);

    /**
     * @description 标准数据包接收
     * @param  * @param mudule:  
     * @return  
     * @author Fkw
     * @date 2019/10/4 
     */
    String uploadStandardAdd(String mudule, MultipartFile file, Logininfo logininfo);

    /**
     * @description 多人增量采集
     * @param  * @param mudule:  
     * @return  
     * @author Fkw
     * @date 2019/10/28 
     */
    String uploadMultipleAdd(Map<String, String> params,MultipartFile file,Logininfo logininfo);
}
