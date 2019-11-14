package com.titansoft.service;

import com.titansoft.entity.Notice;
import com.titansoft.entity.system.Logininfo;

/**
 * @Author: Kevin
 * @Date: 2019/9/23 11:42
 */
public interface NoticeService {
    /**
     * @description 表头
     * @param
     * @return
     * @author Fkw
     * @date 2019/9/23
     */
    String noticeTableColumns();

    /**
     * @description 表单
     * @param  * @param operate:
     * @param notice:
     * @return
     * @author Fkw
     * @date 2019/9/23
     */
    String noticeForm(String operate, Notice notice);


    /**
     * @description 增加公告
     * @param  * @param notice:
     * @param logininfo:
     * @return
     * @author Fkw
     * @date 2019/9/23
     */
    void addNotice(Notice notice, Logininfo logininfo);

    /**
     * @description  
     * @param  * @param notice: 
     * @param logininfo:  
     * @return  
     * @author Fkw
     * @date 2019/9/23 
     */
    String editNotice(Notice notice, Logininfo logininfo);

    /**
     * @description 删除公告
     * @param  * @param notice:  
     * @return  
     * @author Fkw
     * @date 2019/9/23 
     */
    void delNotice(Notice notice);
}
