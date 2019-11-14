package com.titansoft.service;

import com.titansoft.entity.sqcd.Sqcd;
import com.titansoft.entity.sqcd.SqcdItem;
import com.titansoft.entity.system.Logininfo;

import java.util.List;

/**
 * @Author: Kevin
 * @Date: 2019/9/25 19:12
 */
public interface CadreBorrowService {
    //表头
    String tableColumns();

    /**
     * @param *         @param operate:操作
     * @param sqcdItem:
     * @return
     * @description 表单
     * @author Fkw
     * @date 2019/9/26
     */
    String cadreBorrowForm(String operate, SqcdItem sqcdItem);

    /**
     * @param *          @param sqcdItem:
     * @param logininfo:
     * @return
     * @description 添加查档人员基本信息
     * @author Fkw
     * @date 2019/9/26
     */
    String addSave(SqcdItem sqcdItem, Logininfo logininfo);

    /**
     * @param * @param sqcdid:
     * @return
     * @description
     * @author Fkw
     * @date 2019/9/27
     */
    Boolean checkSqcdUser(String sqcdid);

    /**
     * @param * @param sqcdid:
     * @return
     * @description 查档方式表单
     * @author Fkw
     * @date 2019/9/27
     */
    String usertypeForm(String sqcdid);

    /**
     * @param * @param sqcd:
     * @return
     * @description 保存利用方式
     * @author Fkw
     * @date 2019/9/27
     */
    String saveUsetype(Sqcd sqcd);

    /**
     * @param *         @param parentid:
     * @param sqcdid:
     * @param idnumber:
     * @return
     * @description 获取选择干部的分类树
     * @author Fkw
     * @date 2019/9/27
     */
    List sortTree(String parentid, String sqcdid, String idnumber);

    /**
     * @description 删除已经设置好分类和查看权限的干部 
     * @param  * @param sqcdid: 
 * @param idnumber:  
     * @return  
     * @author Fkw
     * @date 2019/9/27 
     */
    void delCadre(String sqcdid, String idnumber);

    /**
     * @description 申请完成 
     * @param  * @param id:  
     * @return  
     * @author Fkw
     * @date 2019/9/27 
     */
    void sqcdFinish(String id);

    /**
     * @description 临时保存分配查阅权限
     * @param  * @param sortid: 
 * @param sqcdid: 
 * @param idnumber:  
     * @return  
     * @author Fkw
     * @date 2019/9/27 
     */
    void saveSqcdPowerTemp(String sortid, String sqcdid, String idnumber);

    void addWaterMarkImage(String sqcdid);

    /**
     * @description 保存分配好的查阅权限 
     * @param  * @param id: 
 * @param sqid: 
 * @param idnumber:  
     * @return  
     * @author Fkw
     * @date 2019/10/11 
     */
    void saveSqcdPower(String  id, String sqid, String idnumber);
}
