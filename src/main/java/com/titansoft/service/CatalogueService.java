package com.titansoft.service;

import com.titansoft.entity.Catalogue;
import com.titansoft.entity.system.Logininfo;

import java.io.IOException;

/**
 * @Author: Kevin
 * @Date: 2019/8/1 16:51
 */
public interface CatalogueService {
    /**
     * @param * @param idNumber:
     * @return
     * @description 初始化干部目录信息（根据分类预留空行数量补齐）
     * @author Fkw
     * @date 2019/8/1
     */
    void initCatalogueInfo(String idNumber);

    /**
     * @param * @param treeId:
     * @return
     * @description 通过干部编号获取(该方法只适用分类两级 ， 目录一级)
     * @author Fkw
     * @date 2019/8/1
     */
    String getCatalogueInfo(String treeId, String idNumber);

    /**
     * @param *          @param idNumber:
     * @param sortCode:
     * @param cataId:
     * @param fieldName:
     * @param fieldVal:
     * @param logininfo:
     * @return
     * @description 保存干部目录信息
     * @author Fkw
     * @date 2019/8/2
     */
    void saveCatalogueInfo(String idNumber, String sortCode, String cataId, String fieldName, String fieldVal, Logininfo logininfo);

    /**
     * @param *       @param idNumber:
     * @param cataId:
     * @return
     * @description 新增目录行
     * @author Fkw
     * @date 2019/8/2
     */
    void saveAddCatalogue(String idNumber, String cataId);

    /**
     * @description 删除目录
     * @param  * @param idNumber: 
 * @param cataId:  
     * @return  
     * @author Fkw
     * @date 2019/8/2 
     */
    void removeCatalogueInfo(String idNumber, String cataId,Logininfo logininfo) throws IOException;

    /**
     * @description 判断目录标题是否为空 
     * @param  * @param cataId:  
     * @return  
     * @author Fkw
     * @date 2019/8/2 
     */
    boolean checkTitle(String cataId);

    /**
     * @description 人事档案详细信息的目录树
     * @param  * @param idnumber:  
     * @return  
     * @author Fkw
     * @date 2019/8/4 
     */
    String getTreeJson(String idnumber);

    /**
     * @description  查档预约模块的加载已经勾选的分类树
     * @param  * @param idnumber:
     * @param sqcdid:
     * @return
     * @author Fkw
     * @date 2019/9/28
     */
    String getTreeJsonSqcd(String idnumber, String sqcdid);

    /**
     * @description  增加或者更新目录信息 
     * @param  * @param catalogue:  
     * @return  
     * @author Fkw
     * @date 2019/10/4 
     */
    String addCatalogue(Catalogue catalogue);

    /**
     * @description 利用平台通过过滤查阅权限获取目录树 
     * @param  * @param idnumber: 
 * @param sqcdid:  
     * @return  
     * @author Fkw
     * @date 2019/10/12 
     */
    String getTreeJsonLy(String idnumber, String sqcdid);

    /**
     * @description 多人批量采集干部目录信息
     * @param  * @param catalogue:  
     * @return  
     * @author Fkw
     * @date 2019/10/28 
     */
    String addCatalogueMultiple(Catalogue catalogue);
}
