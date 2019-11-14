package com.titansoft.controller;

import com.titansoft.entity.Po;
import com.titansoft.service.CadreSHService;
import com.titansoft.service.MediaService;
import com.titansoft.utils.annotation.OpreateLog;
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
 * 人事档案审核controller
 *
 * @Author: Kevin
 * @Date: 2019/8/5 10:58
 */
@Controller
@RequestMapping("/person/cadresh")
public class CadreSHController extends CadreBaseController {
    @Autowired
    CadreSHService cadreSHService;
    @Autowired
    MediaService mediaService;
    /**
     * @param
     * @return
     * @description 跳转到审核界面
     * @author Fkw
     * @date 2019/8/6
     */
    @GetMapping("/list")
    public String list() {
        return "/person/cadresh/list";
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
    public void cadreSHTree(@RequestParam String treeid, @RequestParam String glstate) {
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
    public void cadreSHGrid(@RequestParam String treeid, @RequestParam String glstate) {
        super.grid(treeid, glstate);
    }

    /**
     * @param * @param params:
     * @return
     * @description 查看档案信息
     * @author Fkw
     * @date 2019/8/10
     */
    @OpreateLog
    @GetMapping(value = "/lookpersonsh")
    public ModelAndView lookpersonsh(@RequestParam() Map<String, Object> params) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("mudule", params.get("mudule"));
        modelMap.put("cadrename", params.get("cadrename"));
        modelMap.put("idnumber", params.get("idnumber"));
        modelMap.put("deptname", params.get("deptname"));
        return new ModelAndView("/person/cadresh/lookpersonsh", modelMap);
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

    @RequestMapping(value = "/lookCadreArchives", method = RequestMethod.GET)
    public ModelAndView lookCadreArchives(@RequestParam() Map<String, Object> params) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("mudule", params.get("mudule"));
        modelMap.put("cadrename", params.get("cadrename"));
        modelMap.put("idnumber", params.get("idnumber"));
        return new ModelAndView("/person/personInfo/articlesh", modelMap);
    }

    /**
     * @param
     * @return
     * @description 审核入库或者退回
     * @author Fkw
     * @date 2019/8/11
     */
    @OpreateLog
    @RequestMapping(value = "/cadreinfoCheck", method = RequestMethod.POST)
    public void cadreinfoCheck(@RequestParam()Map<String ,Object> params) {
        String json = "";
        String result=cadreSHService.cadreinfoCheck(params.get("isselectAll").toString(),params.get("idnumber").toString(),params.get("name").toString(),params.get("operationType").toString(),logininfo);
        if("success".equals(result)){
            json = "[{result:'true'}]";
            //形成永久库文件
            //mediaService.strucEepFile(params.get("isselectAll").toString(),params.get("idnumber").toString(),logininfo);
        }else{
            json = "[{result:'false'}]";
        }
        super.toAjax(json);
    }
}
