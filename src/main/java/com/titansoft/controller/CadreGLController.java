package com.titansoft.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.titansoft.entity.DaProcess;
import com.titansoft.entity.Po;
import com.titansoft.mapper.DaProcessMapper;
import com.titansoft.model.Column;
import com.titansoft.model.ColumnJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 人事档案管理controller
 *
 * @Author: Kevin
 * @Date: 2019/8/6 19:53
 */

@Controller
@RequestMapping("/person/cadregl")
public class CadreGLController extends CadreBaseController {
    @Autowired
    DaProcessMapper daProcessMapper;


    @GetMapping("/list")
    public String list() {
        return "/person/cadregl/list";
    }

    /**
     * @param *        @param modulecode:
     * @param glstate:
     * @return
     * @description 模板
     * @author Fkw
     * @date 2019/8/2
     */
    @RequestMapping(value = "/getgridview")
    public void getgridview(@RequestParam String modulecode, @RequestParam String glstate) {
        String json = "";
        json = super.getGridView(modulecode, glstate);
        super.toAjax(json);
    }

    /**
     * @param *        @param treeid:
     * @param glstate:
     * @return
     * @description 单位树结构
     * @author Fkw
     * @date 2019/8/2
     */
    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    public void cadreGLTree(@RequestParam String treeid, @RequestParam String glstate) {
        super.toAjax(super.tree(treeid));
    }

    /**
     * @param *        @param treeid:
     * @param glstate:
     * @return
     * @description 人事表格数据
     * @author Fkw
     * @date 2019/8/2
     */
    @RequestMapping(value = "/grid", method = RequestMethod.POST)
    public void cadreGLGrid(@RequestParam String treeid, @RequestParam String glstate) {
        super.grid(treeid, glstate);
    }


    /**
     * @param * @param params:
     * @return
     * @description 浏览档案信息
     * @author Fkw
     * @date 2019/8/3
     */
    @RequestMapping(value = "/lookpersongl", method = RequestMethod.GET)
    public ModelAndView lookPerson(@RequestParam() Map<String, Object> params) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("mudule", params.get("mudule"));
        modelMap.put("cadrename", params.get("cadrename"));
        modelMap.put("idnumber", params.get("idnumber"));
        modelMap.put("deptname", params.get("deptname"));
        return new ModelAndView("person/cadregl/lookpersongl", modelMap);
    }

    @RequestMapping(value = "/lookCadreArchives", method = RequestMethod.GET)
    public ModelAndView lookCadreArchives(@RequestParam() Map<String, Object> params) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("mudule", params.get("mudule"));
        modelMap.put("cadrename", params.get("cadrename"));
        modelMap.put("idnumber", params.get("idnumber"));
        return new ModelAndView("/person/personInfo/articlegl", modelMap);
    }

    /**
     * @param
     * @return
     * @description 过程信息表格表头
     * @author Fkw
     * @date 2019/8/22
     */
    @RequestMapping(value = "/daprocesscolumns", method = RequestMethod.POST)
    public void tableColumns() throws Exception {
        //调用内部获取表格列头方法
        String columnsJson = "";
        try {
            //暂时采用固定方式,待开发平台完成后,进行修改
            List<Column> columns = new ArrayList<Column>();
            Column column = null;

            column = new Column();
            column.setDataIndex("id");
            column.setText("id");
            column.setXtype("gridcolumnview");
            column.setHidden(true);
            column.setFlex(0);
            column.setWidth(100);
            columns.add(column);

            column = new Column();
            column.setDataIndex("username");
            column.setText("行为人");
            column.setXtype("gridcolumnview");
            column.setFlex(0);
            column.setWidth(200);
            columns.add(column);

            column = new Column();
            column.setDataIndex("processmsg");
            column.setText("行为描述");
            column.setXtype("gridcolumnview");
            column.setFlex(0);
            column.setWidth(1000);
            columns.add(column);

            column = new Column();
            column.setDataIndex("processdate");
            column.setText("行为时间");
            column.setXtype("gridcolumnview");
            column.setFlex(0);
            column.setWidth(200);
            columns.add(column);

            ColumnJson columnjson =   new ColumnJson();
            columnjson.setColumns(columns);

            columnsJson = JSONObject.toJSONString(columnjson);
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            request.setCharacterEncoding("UTF-8");
            response.getWriter().write(columnsJson);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * @param
     * @return
     * @description 过程信息grid
     * @author Fkw
     * @date 2019/8/19
     */
    @RequestMapping(value = "/daprocessgrid", method = RequestMethod.POST)
    public void grid(@RequestParam() String idnumber) throws Exception {
        String subfilter = filter;
        this.fieldlist = "id,username,processmsg,processdate,idnumber";
        filter = "idnumber='" + idnumber + "'";
        Map m = super.grid();
        String[] fieldArray = fieldlist.toString().split(",");
        Page page = (Page) daProcessMapper.selectPage(new Page(Long.valueOf(m.get("page").toString()), Long.valueOf(m.get("limit").toString())), new QueryWrapper<DaProcess>().select(fieldArray).eq("idnumber",idnumber).orderByDesc("processdate"));
        String json = super.pageToJson(page);
        result = "{basefilter:\"" + this.basefilter + "\"," + json.substring(1);
        super.toAjax(result);

    }


    /**
     * @param * @param idnumber:
     * @return
     * @description 查看人事基本信息
     * @author Fkw
     * @date 2019/8/10
     */
    @GetMapping(value = "/lookpersondetail")
    public ModelAndView lookpersondetail(@RequestParam String idnumber,@RequestParam String modulecode) {
        ModelMap modelMap = new ModelMap();
        Po basicpo = cadreService.getCadreBasicDetail(idnumber);
        List<Po> workPoList = cadreService.getCadreBasicExtendDetail(idnumber, "work");
        List<Po> familyPoList = cadreService.getCadreBasicExtendDetail(idnumber, "family");
        List<Po> abroadPoList = cadreService.getCadreBasicExtendDetail(idnumber, "abroad");
        List<Po> archivesKeepPoList = cadreService.getCadreBasicExtendDetail(idnumber, "archivesKeep");
        List<Po> educationPoList = cadreService.getCadreBasicExtendDetail(idnumber, "education");
        modelMap.put("basicpo", basicpo);
        modelMap.put("workPoList", workPoList);
        modelMap.put("familyPoList", familyPoList);
        modelMap.put("abroadPoList", abroadPoList);
        modelMap.put("archivesKeepPoList", archivesKeepPoList);
        modelMap.put("educationPoList", educationPoList);
        return new ModelAndView("/person/cadreinfo/basic/lookcadrebasicinfo", modelMap);
    }
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public void test() throws InterruptedException {
        int count=100;
        int dcount=0;
        for(int i=0;i<=100;i++){
            dcount=i;
            request.getSession().setAttribute("pcount",100);
            request.getSession().setAttribute("dcount",dcount);
            Thread.sleep(1000);
        }
    }

    public static void main(String[] args) {
        int count=20;
        int dcount=0;
        for(int i=0;i<=100;i++){
            dcount=i;
            double adcount=i/100;
            System.out.println(adcount);
        }
    }
}
