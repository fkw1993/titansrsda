package com.titansoft.controller;

import com.alibaba.fastjson.JSON;
import com.titansoft.entity.system.Login;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.entity.system.Product;
import com.titansoft.model.Result;
import com.titansoft.service.LoginService;
import com.titansoft.service.PrivilegeService;
import com.titansoft.utils.annotation.LoginLog;
import com.titansoft.utils.config.PrivilegeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**登录Controller
 * @Author: Kevin
 * @Date: 2019/7/25 11:03
 */
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController{
    @Autowired
    Login login;
    @Autowired
    LoginService loginService;
    @Autowired
    PrivilegeService privilegeService;
    @Autowired
    private HttpServletRequest request; //自动注入request

    public static Map<String,Logininfo> logininfoMap = new HashMap<String,Logininfo>();


    /**
     * @description 系统登录
     * @param aa1:用户名
     * @param aa2:密码
     * @param yzm:验证码
     * @return
     * @author Fkw
     * @date 2019/7/25
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(@RequestParam String aa1, @RequestParam String aa2, @RequestParam String yzm) {
        String result= null;
        try {
            result = loginService.checkLogin(aa1,aa2,yzm,request);
            return Result.SUCCESS("",JSON.parseObject(result));
        } catch (Exception e) {
            return Result.FAIL(e.getMessage());
        }

    }


    /**
     * @param
     * @return
     * @description 获取用户拥有人事档案管理模块子功能的信息
     * @author Fkw
     * @date 2019/7/27
     */
    @RequestMapping(value = "/getUserPersonFun", method = RequestMethod.POST)
    public void getUserPersonFun() {
        String userid = logininfo.getUserid();
        List list = loginService.getUserPersonFun(userid, request);
        super.toAjax("{\"success\":true,\"data\":" + JSON.toJSONString(list) + "}");
    }

    /**
     * @param
     * @return
     * @description 获取用户拥有权限的所有product信息
     * @author Fkw
     * @date 2019/7/27
     */
    @RequestMapping(value = "/getUserProduts", method = RequestMethod.POST)
    public void getUserProduts() {
        String userid = logininfo.getUserid();
        List list = loginService.getUserProduts(userid, request);
        super.toAjax("{\"success\":true,\"data\":" + JSON.toJSONString(list) + "}");
    }

    /**
     * @param
     * @return
     * @description 获取该用户拥有权限的product信息
     * @author Fkw
     * @date 2019/7/27
     */
    @RequestMapping(value = "/getUserProdutsByPower", method = RequestMethod.POST)
    public void getUserProdutsByPower() {
        String userid = logininfo.getUserid();
        List list = loginService.getUserProdutsByPower(userid, request);
        super.toAjax("{\"success\":true,\"data\":" + JSON.toJSONString(list) + "}");
    }

    /**
     * @description 获取待办事项
     * @param  
     * @return  
     * @author Fkw
     * @date 2019/7/28 
     */
    @RequestMapping(value = "/getMessage", method = RequestMethod.POST)
    public void getMessage() {
        String userid = logininfo.getUserid();
        String Realname= logininfo.getRealname();
        List list = (List) request.getSession().getAttribute("sqcdlist");
        String json=JSON.toJSONString(list);
        super.toAjax(json);
    }

    /**
     * @description 获取公告
     * @param  
     * @return  
     * @author Fkw
     * @date 2019/7/28 
     */
    @RequestMapping(value = "/getNotice", method = RequestMethod.POST)
    public void getNotice() {
        String userid = logininfo.getUserid();
        List<Map> list = loginService.getNotice(userid);
        String json=JSON.toJSONString(list);
        super.toAjax(json);
    }


}
