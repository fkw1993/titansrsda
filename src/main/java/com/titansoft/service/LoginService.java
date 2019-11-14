package com.titansoft.service;


import com.titansoft.entity.User;
import com.titansoft.utils.annotation.LoginLog;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/7/25 11:33
 */
public interface LoginService {
    /**
     * @param *        @param code: 账号
     * @param pwd:     密码
     * @param yzm:     验证码
     * @param request:
     * @return
     * @description 登录验证方法
     * @author Fkw
     * @date 2019/7/28
     */
    String checkLogin(String userName, String pwd, String yzm, HttpServletRequest request) throws Exception;

    String checkUserType(String userName);


    User checkUser(String username, String password);

    void getAllUser();

    /**
     * @param *        @param userid:
     * @param request:
     * @return
     * @description 获取显示首页人事档案管理模块子功能
     * @author Fkw
     * @date 2019/7/27
     */
    List<Map> getUserPersonFun(String userid, HttpServletRequest request);

    /**
     * @param *        @param userid:
     * @param request:
     * @return
     * @description 获取用户拥有权限的所有product信息
     * @author Fkw
     * @date 2019/7/27
     */
    List<Map> getUserProduts(String userid, HttpServletRequest request);

    /**
     * @param *
     * @param userid:
     * @param request:
     * @return
     * @description 获取该用户拥有权限的product信息
     * @author Fkw
     * @date 2019/7/27
     */
    List<Map> getUserProdutsByPower(String userid, HttpServletRequest request);

    /**
     * @param *         @param userid:
     * @param realname:
     * @return
     * @description 获取待办事项
     * @author Fkw
     * @date 2019/7/28
     */
    List<Map> getMessage(String userid, String realname);

    /**
     * @description 获取公告
     * @param  * @param userid:
     * @return
     * @author Fkw
     * @date 2019/7/28
     */
    List<Map> getNotice(String userid);
}
