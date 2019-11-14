package com.titansoft.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.titansoft.entity.cadre.Cadre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 人事档案检索Controller
 *
 * @Author: Kevin
 * @Date: 2019/8/19 17:26
 */
@Controller
@RequestMapping("/person/cadresearch")
public class CadreSearchController extends CadreBaseController {
    public static final Logger log = LoggerFactory.getLogger(CadreSearchController.class);

    /**
     * @param
     * @return
     * @description 主页面
     * @author Fkw
     * @date 2019/8/24
     */
    @GetMapping("/zhsearch")
    public String list() {
        return "/person/zhcx/zhsearch";
    }

    /**
     * @param
     * @return
     * @description 模板
     * @author Fkw
     * @date 2019/8/24
     */
    @RequestMapping(value = "/getGridView")
    public void getGridView(@RequestParam(required = false) String modulecode) throws Exception {
        String json = "{}";
        try {
            json = getExtRSGridViewConfigService();
            // 获取按钮
            List<Map<String, String>> buttonMapList = privilegeService.moduloldprivilege(modulecode, logininfo);
            String buttonliststr = "";
            String buttonlist = "";
            for (int i = 0; i < buttonMapList.size(); i++) {
                Map m = buttonMapList.get(i);
                buttonlist += "{\"text\":\"<span>" + m.get("text") + "</span>\",\"id\":\"" + m.get("privilegeid")
                        + "\",\"name\":\"" + m.get("privilegeid") + "\",\"iconCls\":\"" + m.get("iconcls")
                        + "\",\"handler\":function(){" + m.get("eventname") + "('" + m.get("privilegeid") + "');}},";
            }
            buttonlist = "[" + buttonlist + "]";
            buttonliststr += "buttonlist:" + buttonlist;
            json = json.replace("buttonlist:[]", buttonliststr);

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            super.toAjax(json);
        }
    }



    /**
     * @param
     * @return
     * @description 表格数据
     * @author Fkw
     * @date 2019/8/24
     */
    @RequestMapping(value = "/grid", method = RequestMethod.POST)
    public void grid(@RequestParam() Map<String, Object> params) {
        super.cadreSearch(params);
    }

    //查看干部档案
    @RequestMapping(value = "/lookCadreArchives", method = RequestMethod.GET)
    public ModelAndView lookCadreArchives(@RequestParam() Map<String, Object> params) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("mudule","gl");
        modelMap.put("cadrename", params.get("cadrename"));
        modelMap.put("idnumber", params.get("idnumber"));
        return new ModelAndView("/person/personInfo/articlegl", modelMap);
    }

}
