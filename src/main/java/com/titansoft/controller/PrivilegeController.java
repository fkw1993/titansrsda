package com.titansoft.controller;

import com.alibaba.fastjson.JSON;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.entity.system.Privilege;
import com.titansoft.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 权限
 *
 * @Author: Kevin
 * @Date: 2019/7/27 20:44
 */
@RestController
@RequestMapping("/privilege")
public class PrivilegeController extends BaseController {
    @Autowired
    private HttpServletRequest request; //自动注入request
    @Autowired
    PrivilegeService privilegeService;

    @RequestMapping(value = "/initmodulprivilege",method = RequestMethod.POST)
    public void initmodulprivilege(@RequestBody Privilege privilege) {
        //调用初始化产品的所有按钮信息到内存方法,获取返回结果
        privilegeService.initButtonsMap(privilege.getProductid());
    }

}
