package com.titansoft.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.titansoft.entity.cadre.Cadre;
import com.titansoft.entity.sqcd.SqcdItem;
import com.titansoft.entity.sqcd.SqcdPower;
import com.titansoft.model.extjs.Common;
import com.titansoft.utils.database.SqlFilter;
import com.titansoft.utils.util.CommonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**利用平台
 * @Author: Kevin
 * @Date: 2019/10/12 10:01
 */
@Controller
@RequestMapping("/person/ly")
public class UtilizationPlatformController extends CadreBaseController{

    //主页面
    @GetMapping("/lyindex")
    public String lyindex() {
        return "/person/ly/lyindex";
    }

    //主页面
    @GetMapping("/lymain")
    public ModelAndView lymain(@RequestParam() Map<String, Object> params) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("modulecode", params.get("modulecode"));
        modelMap.put("sqcdid", params.get("sqcdid"));
        return new ModelAndView("/person/ly/lymain", modelMap);

    }

    //获取字段和按钮
    @RequestMapping(value = "/getGridView")
    public void getgridview(@RequestParam String modulecode) {
        String json = "";
        json = getExtRSGridViewConfigService();
        // 获取按钮
        String buttonliststr = "";
        String buttonlist = "";
        buttonlist = "[" + buttonlist + "]";
        buttonliststr += "buttonlist:" + buttonlist;
        buttonliststr = "buttonlist:[{\"text\":\"浏览档案信息\",\"id\":\"7844A045B56B90A9754521C7D766F69E\",\"name\":\"7844A045B56B90A9754521C7D766F69E\",\"iconCls\":\"icon-look\",\"handler\":function(){look('7844A045B56B90A9754521C7D766F69E');}},]";
        json = json.replace("buttonlist:[]", buttonliststr);
        super.toAjax(json);
    }

    @RequestMapping(value = "/grid", method = RequestMethod.POST)
    public void cadreLyGrid(@RequestParam() Map<String, Object> params) {
        super.setParams(request);
        super.fieldlist = "id,departid,name,sex,idnumber,department,nation,origin,birthday,worktime,political";
        // 检索词
        String searchkey = (params.get("searchkey") == null) ? "" : params.get("searchkey").toString().trim();
        // 高级检索
        String heightfilter = (params.get("heightfilter") == null) ? "" : params.get("heightfilter").toString().trim();
        // 结果中检索
        String resultcheck = (params.get("resultcheck") == null) ? "" : params.get("resultcheck").toString().trim();
        String sqcdsql = " idnumber  in (select idnumber from "+ CommonUtil.getTableName(SqcdPower.class) +" where sqcdid='"+params.get("sqcdid").toString()+"' )";
        QueryWrapper<Cadre> queryWrapper = new QueryWrapper<Cadre>().eq("1","1");
        if (!"".equals(searchkey)) {
            Map map =super.getWhereSql(searchkey);
            queryWrapper = (QueryWrapper<Cadre>) map.get("queryWrapper");
            if ("true".equals(resultcheck)) {
                super.basefilter = "(" + super.basefilter + ") AND (" + (String) map.get("filter") + ")";
            } else {
                super.basefilter = map.get("filter") +" and "+ sqcdsql;
            }
        } else if (!"".equals(heightfilter)) {

           /* sv.put("heightfilter", heightfilter);
            sv.doService("ztitans.zhcx.bs.ZhcxBS", "gridhsearch");

            if ("true".equals(resultcheck)) {
                super.basefilter = "(" + super.basefilter + ") AND (" + (String) sv.get("whereSql") + ")";
            } else {
                super.basefilter = (String) sv.get("whereSql");
            }*/

        } else {
            super.filter = "idnumber  in (select idnumber from "+CommonUtil.getTableName(SqcdPower.class)+" where sqcdid='"+params.get("sqcdid").toString()+"')";
        }
        Map m = super.grid();
        String[] fieldArray = m.get("fieldlist").toString().split(",");
        String order = " order by pinyin asc";
        //分页获取数据
        Page page = (Page) cadreMapper.selectPage(new Page(Long.valueOf(m.get("page").toString()), Long.valueOf(m.get("limit").toString())), new QueryWrapper<Cadre>().select(fieldArray).apply(m.get("filter").toString()+ SqlFilter.CheckSql(order)));
        String json = super.pageToJson(page);
        result = "{basefilter:\""+basefilter+"\"," + json.substring(1);
        super.toAjax(result);
    }

    //主页面
    @GetMapping("/articlely")
    public ModelAndView articleLy(@RequestParam() Map<String, Object> params) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("cadrename", params.get("cadrename"));
        modelMap.put("idnumber", params.get("idnumber"));
        modelMap.put("sqcdid", params.get("sqcdid"));
        return new ModelAndView("/person/personInfo/articleshly", modelMap);
    }
}
