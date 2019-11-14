package com.titansoft.controller;

/**
 * 干部人事档案统计
 *
 * @Author: Kevin
 * @Date: 2019/9/28 15:05
 */

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/person/cadrestatistics")
public class CadreStatisticsController extends CadreBaseController {

    //电子文件容量饼图
    @RequestMapping(value = "/storePie", method = RequestMethod.POST)
    public void storePie() {
        int size = mediaService.storePie();
        String json = "";
        json = "{\"size\":" + size + "}";
        super.toAjax(json);
    }

    //异常操作
    @RequestMapping(value = "/countErrorLog", method = RequestMethod.POST)
    public void countErrorLog(@RequestParam(required = false) String year,
                              @RequestParam(required = false) String filerStr) {
        String json = logService.countErrorLog(year, filerStr);
        if (json == null) {
            json = "{\"success\":false}";
        }
        super.toAjax(json);
    }


    //人事档案材料统计页面跳转
    @RequestMapping(value = "/TjPersonCataloguePage", method = RequestMethod.GET)
    public ModelAndView tjPersonCataloguePage() {
        return new ModelAndView("/person/tj/TjPersonCatalogue");
    }

    //利用统计页面跳转
    @RequestMapping(value = "/TjLYPage", method = RequestMethod.GET)
    public ModelAndView tjTyPage() {
        return new ModelAndView("/person/tj/TjLY");
    }

    //获取单位列表
    @RequestMapping(value = "/getUnitname", method = RequestMethod.GET)
    public void getUnitname() {
        List list = unitService.getUnitname(null);
        super.toAjax("{\"success\":true,list:" + JSONObject.toJSONString(list) + "}");
    }

    //获取部门列表
    @RequestMapping(value = "/getDepartname", method = RequestMethod.GET)
    public void getDepartname(@RequestParam String unitid) {
        List list = unitService.getDepartname(unitid);
        super.toAjax("{\"success\":true,list:" + JSONObject.toJSONString(list) + "}");
    }

    //干部分类统计
    @RequestMapping(value = "/tjPersonCatalogue", method = RequestMethod.GET)
    public void tjPersonCatalogue(@RequestParam Map<String, String> params)  {
       cadreStatisticsService.tjPersonCatalogue(params);
       super.toAjaxSuccess();
    }

    //利用统计页面跳转
    @RequestMapping(value = "/tjuse", method = RequestMethod.GET)
    public void tjUse(@RequestParam Map<String, String> params) {
        cadreStatisticsService.tjUse(params);
        super.toAjaxSuccess();
    }
}
