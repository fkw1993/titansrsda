package com.titansoft.controller;

import com.titansoft.service.CadreCJService;
import com.titansoft.service.PrivilegeService;
import com.titansoft.utils.annotation.OpreateLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 干部人事采集模块controller
 *
 * @Author: Kevin
 * @Date: 2019/7/31 11:15
 */
@Controller
@RequestMapping("/person/cadrecj")
public class CadreCJController extends CadreBaseController {
    @Autowired
    CadreCJService cadreCJService;
    @Autowired
    PrivilegeService privilegeService;

    @GetMapping("/list")
    public String list() {
        return "/person/cadrecj/list";
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
    public void cadreCJTree(@RequestParam String treeid, @RequestParam String glstate) {
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
    public void cadreCJGrid(@RequestParam String treeid, @RequestParam String glstate) {
        super.grid(treeid, glstate);
    }

    /**
     * @param *             @param idnumber:
     * @param modelAndView:
     * @return
     * @description 采集模块编辑功能
     * @author Fkw
     * @date 2019/8/2
     */
    @OpreateLog
    @RequestMapping(value = "/cjtemplate", method = RequestMethod.GET)
    public ModelAndView editCadreCJ(@RequestParam("idnumber") String idnumber,@RequestParam("modulecode") String modulecode, ModelAndView modelAndView) {
        return new ModelAndView("/person/cadrecj/cjtemplate", "idnumber", idnumber);
    }

    /**
     * @param *             @param sortId:
     * @param modelAndView:
     * @return
     * @description 采集模块查看图片
     * @author Fkw
     * @date 2019/8/3
     */
    @RequestMapping(value = "/showimage", method = RequestMethod.GET)
    public ModelAndView showImage(@RequestParam("sortId") String sortId, ModelAndView modelAndView) {
        return new ModelAndView("/person/cadrecj/showimage", "sortId", sortId);
    }

    /**
     * @param * @param params:
     * @return
     * @description 浏览档案信息
     * @author Fkw
     * @date 2019/8/3
     */
    @RequestMapping(value = "/lookpersoncj", method = RequestMethod.GET)
    public ModelAndView lookPersonCJ(@RequestParam() Map<String, Object> params) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("mudule", params.get("mudule"));
        modelMap.put("cadrename", params.get("cadrename"));
        modelMap.put("idnumber", params.get("idnumber"));
        modelMap.put("deptname", params.get("deptname"));
        return new ModelAndView("/person/cadrecj/lookpersoncj", modelMap);
    }

    @OpreateLog
    @RequestMapping(value = "/lookCadreArchives", method = RequestMethod.GET)
    public ModelAndView lookCadreArchives(@RequestParam() Map<String, Object> params) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("mudule", params.get("mudule"));
        modelMap.put("cadrename", params.get("cadrename"));
        modelMap.put("idnumber", params.get("idnumber"));
        return new ModelAndView("/person/personInfo/articlecj", modelMap);
    }

    /**
     * @param * @param params:
     * @return
     * @description 提交审核
     * @author Fkw
     * @date 2019/8/15
     */
    @OpreateLog
    @RequestMapping(value = "/checkShenHe", method = RequestMethod.POST)
    public void checkShenHe(@RequestParam String modulecode,@RequestParam String idnumber) {
        String msg = cadreCJService.checkAudit(idnumber);
        if ("".equals(msg)) {
            cadreCJService.shenhe(idnumber, logininfo);
        }
        String json = "{\"success\":true,\"msg\": \"" + msg + "\"}";
        super.toAjax(json);
    }


    /**
     * 批量采集干部信息
     *
     * @throws Exception
     */
    @RequestMapping(value = "/uploadBatchAddfile", method = RequestMethod.POST)
    public void uploadBatchAddfile(@RequestParam Map<String, String> params, @RequestParam("file") MultipartFile file) throws Exception {
        String resultMsg = "";
        String batchAddType = params.get("batchAddType");
        String mudule = params.get("mudule");
        try {
            if ("standard".equals(batchAddType)) {// 标准数据包接收
                // 检查数据包是否上传过
                resultMsg = cadreCJService.uploadStandardAdd(mudule, file, logininfo);
            } else if ("multiple".equals(batchAddType)) {// 多人增量采集
                  resultMsg = cadreCJService.uploadMultipleAdd(params,file,logininfo);
            }
            if (!"".equals(resultMsg)) {
                result = "{\"success\": true, \"message\": \"" + resultMsg + "\"}";
            } else {
                resultMsg = "干部目录信息采集成功!";
                result = "{\"success\": true, \"message\": \"" + resultMsg + "\"}";
            }
        } catch (Exception e) {

            result = "{\"success\": true, \"message\": \"" + e.getMessage() + "\"}";
        } finally {
            super.toAjax(result);
        }
    }

    //打印目录
    @OpreateLog
    @RequestMapping(value = "/print", method = RequestMethod.POST)
    public void print(@RequestParam String modulecode,@RequestParam String idnumber) {
        super.toAjaxSuccess();
    }

}
