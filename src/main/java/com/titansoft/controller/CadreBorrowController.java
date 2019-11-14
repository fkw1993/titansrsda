package com.titansoft.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.titansoft.entity.sqcd.Sqcd;
import com.titansoft.entity.sqcd.SqcdItem;
import com.titansoft.mapper.SqcdItemMapper;
import com.titansoft.service.CadreBorrowService;
import com.titansoft.utils.database.SqlFilter;
import com.titansoft.utils.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 人事档案预约申请
 *
 * @Author: Kevin
 * @Date: 2019/8/19 17:28
 */
@Controller
@RequestMapping("/person/sqcd")
public class CadreBorrowController extends CadreBaseController {
    @Autowired
    CadreBorrowService cadreBorrowService;

    @Autowired
    SqcdItemMapper sqcdItemMapper;

    //主页面
    @GetMapping("/list")
    public String list() {
        return "/person/sqcd/list";
    }

    //填写完利用信息后，进入检索需要借档的干部
    @GetMapping("/addsearch")
    public ModelAndView addsearch(@RequestParam() Map<String, Object> params) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("sqcdid", params.get("sqcdid"));
        return new ModelAndView("/person/sqcd/addsearch", modelMap);
    }

    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    public void tree() {
        String treeJson = "[{\"children\":[],\"expanded\":false,\"id\":\"CC8CE6CA3B595F4AEAD4AE24EB1C99AD\",\"leaf\":true,\"pid\":\"\",\"text\":\"接待受理\",\"type\":0},"
                + "{\"children\":[],\"expanded\":false,\"id\":\"E7303C2A6AB92B4D6388DFAEA0EC3F1B\",\"leaf\":true,\"pid\":\"\",\"text\":\"预约受理\",\"type\":0}]";
        super.toAjax(treeJson);
    }

    @RequestMapping(value = "/tableColumns", method = RequestMethod.POST)
    public void tableColumns() {
        super.toAjax(cadreBorrowService.tableColumns());
    }

    @RequestMapping(value = "/tableData", method = RequestMethod.POST)
    public void tableData(Sqcd sqcd) {
        this.basefilter = "approvalstate = '" + sqcd.getApprovalstate() + "' ";
        Map m = super.grid();
        String order = " order by applydate desc";
        //分页获取数据
        Page page = (Page) sqcdMapper.selectPage(new Page(Long.valueOf(m.get("page").toString()), Long.valueOf(m.get("limit").toString())), (Wrapper<Sqcd>) new QueryWrapper().apply(SqlFilter.CheckSql(m.get("filter").toString() + order)));
        String json = super.pageToJson(page);
        result = "{basefilter:\"" + this.basefilter + "\"," + json.substring(1);
        super.toAjax(result);
    }

    //生成申请单id
    @RequestMapping(value = "/createSqcdId", method = RequestMethod.GET)
    public void createSqcdId() {
        String id = CommonUtil.getGuid();
        super.toAjax("{'success':true,'id':'" + id + "'}");
    }

    //增加查档人员表单
    @RequestMapping(value = "/toaddform", method = RequestMethod.GET)
    public void toAddForm(SqcdItem sqcdItem) {
        super.toAjax(cadreBorrowService.cadreBorrowForm("add", sqcdItem));
    }

    //增加查档人员表单
    @RequestMapping(value = "/addsave", method = RequestMethod.POST)
    public void addSave(SqcdItem sqcdItem) {
        String id = cadreBorrowService.addSave(sqcdItem, logininfo);
        result = "{'success':true,'info':'ok','sqcdid':'" + id + "'}";
        super.toAjax(result);
    }


    //修改查档人员表单
    @RequestMapping(value = "/toeditform", method = RequestMethod.GET)
    public void toEditForm(SqcdItem sqcdItem) {
        sqcdItem = sqcdItemMapper.selectOne(new QueryWrapper<SqcdItem>().eq("id", sqcdItem.getId()));
        super.toAjax(cadreBorrowService.cadreBorrowForm("edit", sqcdItem));
    }


    //删除查档人员信息
    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public void del(SqcdItem sqcdItem) {
        int i = sqcdItemMapper.deleteById(sqcdItem.getId());
        super.toAjaxSuccess();
    }

    //查档人员信息表格数据
    @RequestMapping(value = "/sqcdUserGrid", method = RequestMethod.POST)
    public void sqcdUserGrid(SqcdItem sqcdItem) {
        this.basefilter = "sqcdid = '" + sqcdItem.getSqcdid() + "' ";
        Map m = super.grid();
        String order = " order by id";
        //分页获取数据
        Page page = (Page) sqcdItemMapper.selectPage(new Page(Long.valueOf(m.get("page").toString()), Long.valueOf(m.get("limit").toString())), (Wrapper<SqcdItem>) new QueryWrapper().apply(SqlFilter.CheckSql(m.get("filter").toString() + order)));
        String json = super.pageToJson(page);
        result = "{basefilter:\"" + this.basefilter + "\"," + json.substring(1);
        super.toAjax(result);
    }

    //删除查档人员信息
    @RequestMapping(value = "/checkSqcdUser", method = RequestMethod.GET)
    public void checkSqcdUser(SqcdItem sqcdItem) {
        Boolean bool = cadreBorrowService.checkSqcdUser(sqcdItem.getSqcdid());
        result = "{\"success\":true,\"check\":" + bool + "}";
        super.toAjax(result);
    }

    //利用方式表单
    @RequestMapping(value = "/usetypeform", method = RequestMethod.GET)
    public void usertypeForm(@RequestParam String sqcdid) {
        String json = cadreBorrowService.usertypeForm(sqcdid);
        super.toAjax(json);
    }

    //保存利用方式
    @RequestMapping(value = "/saveusetype", method = RequestMethod.POST)
    public void saveUsetype(Sqcd sqcd) {
        String id = cadreBorrowService.saveUsetype(sqcd);
        result = "{'success':true,'info':'ok','sqcdid':'" + id + "'}";
        super.toAjax(result);
    }


    //干部检索
    @RequestMapping(value = "/cadresearch", method = RequestMethod.POST)
    public void cadreSearch(@RequestParam Map<String, Object> params) {
        super.cadreSearch(params);
    }

    //删除已经设置好分类和查看权限的干部
    @RequestMapping(value = "/delcadre", method = RequestMethod.POST)
    public void delCadre(@RequestParam String sqcdid,@RequestParam  String idnumber) {
        cadreBorrowService.delCadre(sqcdid,idnumber);
        super.toAjaxSuccess();
    }

    //获取已选择干部的分类树
    @RequestMapping(value = "/sortTree", method = RequestMethod.POST)
    public void sortTree(@RequestParam Map<String, Object> params) {
        String parentid = params.get("treeid").toString();
        String sqcdid = params.get("sqcdid").toString();
        String idnumber = params.get("idnumber").toString();
        // 生成基础树，没有checked属性
        List treelist = cadreBorrowService.sortTree(parentid, sqcdid, idnumber);
        super.toAjax(JSONArray.toJSONString(treelist));
    }

    @RequestMapping(value = "/saveSqcdPowerTemp", method = RequestMethod.POST)
    public void saveSqcdPowerTemp(@RequestParam Map<String, Object> params)  {
        String sortid = params.get("id").toString();
        String sqcdid =  params.get("sqcdid").toString();
        String idnumber = params.get("idnumber").toString();
        cadreBorrowService.saveSqcdPowerTemp(sortid, sqcdid, idnumber);
        cadreBorrowService.addWaterMarkImage(sqcdid);
        super.toAjax("{'success':true}");
    }

    //
    @RequestMapping(value = "/catalogueitem", method = RequestMethod.GET)
    public ModelAndView catalogueItem(@RequestParam Map<String, Object> params)  {
        ModelMap  modelMap=new ModelMap();
        modelMap.put("mudule",params.get("mudule"));
        modelMap.put("cadrename",params.get("sqcdid"));
        modelMap.put("idnumber",params.get("idnumber"));
        return new ModelAndView("/person/personInfo/articleshsqcd",modelMap);
    }

    //保存分配好的查阅权限
    @RequestMapping(value = "/saveSqcdPower", method = RequestMethod.POST)
    public void saveSqcdPower(@RequestParam  Map<String, Object> params) {
        cadreBorrowService.saveSqcdPower(params.get("id").toString(),params.get("sqid").toString(),params.get("idnumber").toString());
        super.toAjaxSuccess();
      //  super.toAjax("{'success':true,'id':'" + id + "'}");
    }

    //申请完成
    @RequestMapping(value = "/sqcdfinish", method = RequestMethod.POST)
    public void sqcdFinish(@RequestParam String id) {
        cadreBorrowService.sqcdFinish(id);
        super.toAjax("{'success':true,'id':'" + id + "'}");
    }

}
