package com.titansoft.service;

/**
 * @Author: Kevin
 * @Date: 2019/8/30 11:00
 */
public interface LogService {
    /**
     * 统计两种异常的次数
     * <p>
     * 返回月份以及相应的次数
     *
     * @throws Exception
     * @author Lime
     * @date 2018/4/20
     */
    String countErrorLog(String year, String filerStr);

    /**
     * 统计登录状态次数
     * <p>
     * 返回月份以及的次数
     *
     * @throws Exception
     * @author Lime
     * @date 2018/4/23
     */
    String countLoginState(String year, String filerStr, String filerStr1);

    String tree(String modulecode);

    /**
     * @description 登录日志表头
     * @param  
     * @return  
     * @author Fkw
     * @date 2019/9/23 
     */
    String loginLogColumns();

    /**获取安全日志
     * @description
     * @param  * @param null:
     * @return
     * @author Fkw
     * @date 2019/9/23
     */
    String safeLogColumns();
}
