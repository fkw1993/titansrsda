package com.titansoft.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.titansoft.entity.Po;
import com.titansoft.mapper.PoMapper;
import com.titansoft.model.PoExtendList;
import com.titansoft.model.PoList;
import com.titansoft.model.enums.CadreExtendEnum;
import com.titansoft.service.CadreInfoService;
import com.titansoft.utils.config.MybatisPlusConfig;
import com.titansoft.utils.util.BeanUtil;
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

/**人事档案基本和扩展信息
 * @Author: Kevin
 * @Date: 2019/9/29 15:59
 */
@Controller
@RequestMapping("/person/cadreinfo")
public class CadreInfoController extends CadreBaseController {
    @Autowired
    CadreInfoService cadreInfoService;


    @GetMapping("/basic/list")
    public String basicList() {
        return "/person/cadreinfo/basic/list";
    }

    @GetMapping("/extend/list")
    public String extendList() {
        return "/person/cadreinfo/extend/list";
    }


    /**
     * @param *        @param modulecode:
     * @param glstate:
     * @return
     * @description 模板
     * @author Fkw
     * @date 2019/8/2
     */
    @RequestMapping(value = "/basic/getgridview")
    public void basicGetGridView(@RequestParam String modulecode, @RequestParam String glstate) {
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
    @RequestMapping(value = "/basic/tree", method = RequestMethod.POST)
    public void cadreBasicTree(@RequestParam String treeid, @RequestParam String glstate) {
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
    @RequestMapping(value = "/basic/grid", method = RequestMethod.POST)
    public void cadreBasicGrid(@RequestParam String treeid, @RequestParam String glstate) {
        super.grid(treeid, glstate);
    }

    //编辑基本信息页面
    @RequestMapping(value = "/basic/cadrebasicinfoedit", method = RequestMethod.GET)
    public ModelAndView cadreBasicInfo(@RequestParam() Map<String, Object> params) {
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();
        modelMap.put("operate", params.get("operate"));
        modelMap.put("treeid", params.get("treeid"));
        if ("add".equals(params.get("operate"))) {
            modelMap.put("educationPoList", getPO(4));
            modelMap.put("workPoList", getPO(10));
            modelMap.put("familyPoList", getPO(7));
            modelMap.put("abroadPoList", getPO(7));
            modelMap.put("archivesKeepPoList", getPO(7));
        } else if ("edit".equals(params.get("operate"))) {
            String idnumber = params.get("idnumber").toString();
            modelMap.put("educationPoList", cadreInfoService.getBasicItem("education", idnumber, 4));
            modelMap.put("workPoList", cadreInfoService.getBasicItem("work", idnumber, 10));
            modelMap.put("familyPoList", cadreInfoService.getBasicItem("family", idnumber, 7));
            modelMap.put("abroadPoList", cadreInfoService.getBasicItem("abroad", idnumber, 7));
            modelMap.put("archivesKeepPoList", cadreInfoService.getBasicItem("archivesKeep", idnumber, 7));
            modelMap.put("basicpo", cadreInfoService.getBasicPo(idnumber));
        }
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName("/person/cadreinfo/basic/editcadrebasicinfo");
        return modelAndView;
    }

    //增加和修改保存基本信息
    @RequestMapping(value = "/basic/addCadreBasicInfo", method = RequestMethod.POST)
    public void addCadreBasicInfo(PoList poList, @RequestParam String operate, @RequestParam String treeid) {
        String msg = "";
        if ("add".equals(operate)) {
            msg = cadreInfoService.addCadreBasicInfo(treeid, poList, logininfo);
        } else if ("edit".equals(operate)) {
            msg = cadreInfoService.editCadreBasicInfo(treeid, poList, logininfo);
        }
        result = "{\"success\":true,\"msg\": \"" + msg + "\"}";
        super.toAjax(result);
    }

    //查看
    @RequestMapping(value = "/basic/look", method = RequestMethod.GET)
    public ModelAndView toLookCadreBasicInfo(@RequestParam String idnumber) {
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();
        modelMap.put("educationPoList", cadreInfoService.getBasicItem("education", idnumber, 4));
        modelMap.put("workPoList", cadreInfoService.getBasicItem("work", idnumber, 10));
        modelMap.put("familyPoList", cadreInfoService.getBasicItem("family", idnumber, 7));
        modelMap.put("abroadPoList", cadreInfoService.getBasicItem("abroad", idnumber, 7));
        modelMap.put("archivesKeepPoList", cadreInfoService.getBasicItem("archivesKeep", idnumber, 7));
        modelMap.put("basicpo", cadreInfoService.getBasicPo(idnumber));
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName("/person/cadreinfo/basic/lookcadrebasicinfo");
        return modelAndView;
    }


    public List getPO(int sum) {
        List list = new ArrayList();
        for (int i = 0; i < sum; i++) {
            Po po = new Po();
            list.add(BeanUtil.nullToEmpty(po));
        }
        return list;
    }


/************************************************扩展信息**************************************************************************/


    /**
     * @param *        @param modulecode:
     * @param glstate:
     * @return
     * @description 模板
     * @author Fkw
     * @date 2019/8/2
     */
    @RequestMapping(value = "/extend/getgridview")
    public void extendGetGridView(@RequestParam String modulecode, @RequestParam String glstate) {
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
    @RequestMapping(value = "/extend/tree", method = RequestMethod.POST)
    public void cadreExtendTree(@RequestParam String treeid, @RequestParam String glstate) {
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
    @RequestMapping(value = "/extend/grid", method = RequestMethod.POST)
    public void cadreExtendGrid(@RequestParam String treeid, @RequestParam String glstate) {
        super.grid(treeid, glstate);
    }


    @RequestMapping(value = "/extend/toedit", method = RequestMethod.GET)
    public ModelAndView cadreExtendInfo(@RequestParam String idnumber,@RequestParam String operate) {
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();
        modelMap.put("jobpo", cadreInfoService.getExtendPo("job", idnumber ));
        modelMap.put("degreepo", cadreInfoService.getExtendPo("degree", idnumber));
        modelMap.put("nationalitypo", cadreInfoService.getExtendPo("nationality", idnumber));
        modelMap.put("resumepo", cadreInfoService.getExtendPo("resume", idnumber));
        modelMap.put("technicalpo", cadreInfoService.getExtendPo("technical", idnumber));
        modelMap.put("operate", operate);
        modelMap.put("idnumber", idnumber);
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName("/person/cadreinfo/extend/editCadreExtendInfo");
        return modelAndView;
    }

    @RequestMapping(value = "/extend/tolook", method = RequestMethod.GET)
    public ModelAndView lookCadreExtendInfo(@RequestParam String idnumber) {
        ModelAndView modelAndView = new ModelAndView();
        ModelMap modelMap = new ModelMap();
        modelMap.put("jobpo", cadreInfoService.getExtendPo("job", idnumber ));
        modelMap.put("degreepo", cadreInfoService.getExtendPo("degree", idnumber));
        modelMap.put("nationalitypo", cadreInfoService.getExtendPo("nationality", idnumber));
        modelMap.put("resumepo", cadreInfoService.getExtendPo("resume", idnumber));
        modelMap.put("technicalpo", cadreInfoService.getExtendPo("technical", idnumber));
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName("/person/cadreinfo/extend/lookCadreExtendInfo");
        return modelAndView;
    }

    @Autowired
    PoMapper poMapper;
    @RequestMapping(value = "/extend/save", method = RequestMethod.POST)
    public void saveCadreExtendInfo(@RequestParam String type, @RequestParam String operate,  PoExtendList poExtendList) {
        if ("add".equals(operate)) {
            MybatisPlusConfig.dynamicTableName.set(CadreExtendEnum.getEnumByAtype(type).getTablename());
            // 增加前判断是否已经存在
            List list = poMapper.selectList(new QueryWrapper<Po>().eq("idnumber",poExtendList.getPo().getIdnumber()));
            if (list.size() != 0) {
                super.toAjax("{\"success\":true,\"msg\":\"已存在不能重复增加\"}");
            } else {
                cadreInfoService.addCadreExtendInfo(poExtendList.getPo(), CadreExtendEnum.getEnumByAtype(type).getTablename());
                super.toAjax("{\"success\":true,\"msg\":\"保存成功\"}");
            }
        } else {
            cadreInfoService .editCadreExtendInfo(poExtendList.getPo(), CadreExtendEnum.getEnumByAtype(type).getTablename());
            super.toAjax("{\"success\":true,\"msg\":\"修改成功\"}");
        }
    }
}
