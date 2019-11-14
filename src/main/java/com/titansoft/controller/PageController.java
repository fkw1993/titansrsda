package com.titansoft.controller;

import com.fr.report.core.A.S;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.model.layui.Status;
import com.titansoft.model.layui.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**页面跳转处理
 * @Author: Kevin
 * @Date: 2019/7/25 9:56
 */
@Controller
public class PageController extends BaseController{
    @Autowired
    private HttpServletRequest request; //自动注入request

    //首页
    @GetMapping("/index")
    public String login() {
        return "index";
    }

    @GetMapping("/layui/page/{pages}")
    public String goPage(@PathVariable("pages")String page) {
        page=page.substring(0,page.indexOf("."));
        return page;
    }

    @RequestMapping(value = "/cadretree", method = RequestMethod.POST)
    public void cadretree(@RequestParam() Map<String, Object> params) {


    }




    //退出登录
    @GetMapping("/logout")
    public String logout() {
        //清除登录信息
        if(logininfo!=null){
            logininfo=null;
        }
        return "/index";
    }

    @GetMapping(value = "/error/404")
    public String error_404() {
        return "error";
    }

    /**
     * 500页面
     */
    @GetMapping(value = "/error/500")
    public String error_500() {
        return "comm/error_500";
    }


    //系统主界面
    @GetMapping("/mainpage")
    public String indexPage() {
        Logininfo logininfo = (Logininfo) request.getSession().getAttribute("logininfo");
        request.setAttribute("realname", logininfo.getRealname());
        request.setAttribute("username", logininfo.getUsername());
        //List sqcdlist=this.protalBS.sqcdList();
        //session.put("sqcdlist", sqcdlist);
        return "/mainpage";
    }


    //查看人事档案详细
    @GetMapping("/viewer/index")
    public ModelAndView lookArchivesDetail(@RequestParam String mudule, @RequestParam String sortId) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("mudule", mudule);
        modelMap.put("sortId", sortId);
        return new ModelAndView("/person/viewer/index", modelMap);
    }

    //利用平台查看人事档案详细
    @GetMapping("/viewer/indexly")
    public ModelAndView lookArchivesDetailLY(@RequestParam String mudule, @RequestParam String sortId,@RequestParam String sqcdid) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("mudule", mudule);
        modelMap.put("sortId", sortId);
        modelMap.put("sqcdid", sqcdid);
        return new ModelAndView("/person/viewer/indexly", modelMap);
    }

}
