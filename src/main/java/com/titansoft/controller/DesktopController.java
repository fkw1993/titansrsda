package com.titansoft.controller;

import com.titansoft.entity.system.Product;
import com.titansoft.model.Tree;
import com.titansoft.service.DesktopService;
import com.titansoft.utils.config.PrivilegeConfig;
import com.titansoft.utils.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 系统主界面controller
 *
 * @Author: Kevin
 * @Date: 2019/7/30 15:58
 */
@RestController
@RequestMapping("/desktop")
public class DesktopController extends BaseController {
    @Autowired
    DesktopService desktopService;

    @RequestMapping(value = "/systemindex", method = RequestMethod.GET)
    @ResponseBody
    public String systemindex(@RequestParam(required = false) String code,
                              @RequestParam(required = false) String title,
                              @RequestParam(required = false) String productId,
                              @RequestParam(required = false) String color,
                              @RequestParam(required = false) String modelID) throws Exception {
        String querystr = request.getQueryString();
        //title = request.getParameter("title");
      //  String productid = request.getParameter("productId");
        request.setAttribute("title", title);
        request.getSession().setAttribute("productid", productId);
       // code = request.getParameter("code");
        //String idse = request.getParameter("productId");
        Product product = PrivilegeConfig.productMap.get(productId);
        if (product == null) {
            product = PrivilegeConfig.productMap.get("5BCD3977A26DC04B98A5A09224A76FB7");
        }
        //暂时采用代号来区分产品,以后需要修改
        if (product.getIndexpage() != null && !"".equals(product.getIndexpage())) {
            request.setAttribute("color", request.getParameter("color"));
            request.setAttribute("realname", logininfo.getRealname());
            if (product.getIndexpage().indexOf("http") == 0) {
                response.sendRedirect(product.getIndexpage());
            } else {
                request.getRequestDispatcher(product.getIndexpage() + "?" + querystr).forward(request, response);
            }
        } else {
            color = request.getParameter("color");
            request.setAttribute("color", request.getParameter("color"));
            request.setAttribute("realname", logininfo.getRealname());
            request.setAttribute("username", logininfo.getUsername());
            if ("AQWH".equals(product.getCode()) || "TJXT".equals(product.getCode())) {
                request.getRequestDispatcher("/subSystem.jsp?" + querystr).forward(request, response);
            } else {
                request.getRequestDispatcher("/subSystemSimple.jsp?" + querystr).forward(request, response);
            }
        }
        return null;
    }

    @RequestMapping(value = "/getUserName", method = RequestMethod.POST)
    public void getUserName() {
        String result = "";
        try {
            result = "{\"success\":true,\"result\":{\"username\":\"" + logininfo.getRealname() + "\"}}";
        } catch (Exception e) {
            result = "{\"success\":false,\"error\":\"" + e.getMessage() + "\"}";
        } finally {
            super.toAjax(result);
        }
    }

    /**
     * @param * @param productid:
     * @return
     * @description 获取各个子模块的菜单
     * @author Fkw
     * @date 2019/7/30
     */
    @RequestMapping(value = "/getMenu", method = RequestMethod.POST)
    public void getMenu(@RequestParam String productid) {
        String result = null;
        try {
            String roleid = logininfo.getRoleid();
            List<Tree> list = desktopService.getMenu(productid, roleid);
            result = CommonUtil.createTreeJson(list, false);

        } catch (Exception e) {
            result = "{\"success\": false, \"error\": \"" + e.getMessage() + "\"}";
        } finally {
            super.toAjax(result);
        }
    }

    /**
     * @description  各个模块桌面页面
     * @param  * @param home:
     * @return
     * @author Fkw
     * @date 2019/8/24
     */
    @GetMapping(value = "{home}/home")
    public ModelAndView home(@PathVariable("home") String home) {
      return new ModelAndView("/person/desktop/"+home+"/home");
    }
}
