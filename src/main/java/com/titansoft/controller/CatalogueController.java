package com.titansoft.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.titansoft.entity.Catalogue;
import com.titansoft.service.CatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**干部分类Controller
 * @Author: Kevin
 * @Date: 2019/8/1 16:50
 */
@Controller
@RequestMapping("/catalogue")
public class CatalogueController extends BaseController {
    @Autowired
    CatalogueService catalogueService;
    /**
     * @description 初始化目录数据
     * @param  * @param idNumber:
     * @return
     * @author Fkw
     * @date 2019/8/3
     */
    @RequestMapping(value = "/initCatalogueInfo", method = RequestMethod.POST)
    public void initCatalogueInfo(@RequestParam String idNumber) {
        catalogueService.initCatalogueInfo(idNumber);

    }

    @RequestMapping(value = "/getCatalogueInfo", method = RequestMethod.POST)
    public void getCatalogueInfo(@RequestParam String idNumber) {
        // String idNumber=request.getParameter("idNumber").toString();
        String json = catalogueService.getCatalogueInfo(null, idNumber);
        super.toAjax(json);
    }

    /**
     * @description 保存目录信息
     * @param  * @param idNumber:
 * @param sortCode:
 * @param cataId:
 * @param fieldName:
 * @param fieldVal:
     * @return
     * @author Fkw
     * @date 2019/8/2
     */
    @RequestMapping(value = "/saveCatalogueInfo", method = RequestMethod.POST)
    public void saveCatalogueInfo(@RequestParam(required = false)
                                          String idNumber,
                                  @RequestParam(required = false)
                                          String sortCode,
                                  @RequestParam(required = false)
                                          String cataId,
                                  @RequestParam(required = false)
                                          String fieldName,
                                  @RequestParam(required = false)
                                          String fieldVal) {
        catalogueService.saveCatalogueInfo(idNumber, sortCode, cataId, fieldName, fieldVal, logininfo);

    }

    /**
     * @description  新增目录行
     * @param
     * @return
     * @author Fkw
     * @date 2019/8/2
     */
    @RequestMapping(value = "/saveAddCatalogueInfo", method = RequestMethod.POST)
    public void saveAddCatalogueInfo(@RequestParam(required = false) String idNumber,@RequestParam(required = false) String  cataId){
        catalogueService.saveAddCatalogue(idNumber, cataId);
        String json = "[{success:true}]";
        super.toAjax(json);
    }


    /**
     * @description 删除目录行
     * @param  * @param idNumber:
    * @param cataId:
     * @return
     * @author Fkw
     * @date 2019/8/2
     */
    @RequestMapping(value = "/removeCatalogueInfo", method = RequestMethod.POST)
    public void removeCatalogueInfo(@RequestParam(required = false) String idNumber,@RequestParam(required = false) String  cataId) throws IOException {
        catalogueService.removeCatalogueInfo(idNumber, cataId,logininfo);
        String json = "[{success:true}]";
        super.toAjax(json);
    }

    /**
     * @description 判断目录标题是否为空
     * @param  * @param cataId:  
     * @return  
     * @author Fkw
     * @date 2019/8/2 
     */
    @RequestMapping(value = "/checkTitle", method = RequestMethod.POST)
    public void checkTitle(@RequestParam(required = false) String cataId) throws Exception {
        boolean bool=this.catalogueService.checkTitle(cataId);
        super.toAjax("{\"success\": "+bool+"}");
    }


    @RequestMapping(value = "/getTreeJson", method = RequestMethod.POST)
    public void getTreeJson(@RequestParam(required = false) String idnumber) throws Exception {
        String json = catalogueService.getTreeJson(idnumber);
        super.toAjax(json);
    }


    @RequestMapping(value = "/getTreeJsonSqcd", method = RequestMethod.POST)
    public void getTreeJsonSqcd(@RequestParam(required = false) String idnumber,@RequestParam(required = false) String sqcdid) throws Exception {
        String json = catalogueService.getTreeJsonSqcd(idnumber,sqcdid);
        super.toAjax(json);
    }

    //利用平台通过过滤查阅权限获取目录树
    @RequestMapping(value = "/getTreeJsonLy", method = RequestMethod.POST)
    public void getTreeJsonLy(@RequestParam(required = false) String idnumber,@RequestParam(required = false) String sqcdid) throws Exception {
        String json = catalogueService.getTreeJsonLy(idnumber,sqcdid);
        super.toAjax(json);
    }
}
