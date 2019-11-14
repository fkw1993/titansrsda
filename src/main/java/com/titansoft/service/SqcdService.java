package com.titansoft.service;

import com.titansoft.entity.sqcd.SqcdReason;
import com.titansoft.entity.system.Logininfo;

/**申请查档
 * @Author: Kevin
 * @Date: 2019/9/24 9:21
 */
public interface SqcdService {
    /**
     * @description  系统维护查档事由表头
     * @param
     * @return
     * @author Fkw
     * @date 2019/9/24
     */
    String sqcdReasonTableColumns();

    /**
     * @description  系统维护查档事由表单
     * @param  * @param operate:
     * @param sqcdReason:
     * @return
     * @author Fkw
     * @date 2019/9/24
     */
    String sqcdReasonForm(String operate, SqcdReason sqcdReason);

    /**
     * @description  增加查档事由
     * @param  * @param sqcdreason: 
     * @param logininfo:  
     * @return  
     * @author Fkw
     * @date 2019/9/24 
     */
    void addSqcdReason(SqcdReason sqcdreason, Logininfo logininfo);

    /**
     * @description  修改查档事由
     * @param  * @param sqcdreason:
     * @param logininfo:
     * @return
     * @author Fkw
     * @date 2019/9/24
     */
    String editsSqcdReason(SqcdReason sqcdreason, Logininfo logininfo);

    /**
     * @description  删除查档事由
     * @param  * @param sqcdreason:
     * @return
     * @author Fkw
     * @date 2019/9/24
     */
    void delSqcdeason(SqcdReason sqcdreason);
}
