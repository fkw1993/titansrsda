package com.titansoft.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.titansoft.entity.Po;
import com.titansoft.entity.cadre.Cadre;
import com.titansoft.entity.cadre.CadreTransfer;
import com.titansoft.mapper.CadreMapper;
import com.titansoft.mapper.CadreTransferMapper;
import com.titansoft.model.enums.CadreBasicEnum;
import com.titansoft.service.CadreService;
import com.titansoft.utils.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 档案转递模块
 *
 * @Author: Kevin
 * @Date: 2019/8/19 11:35
 */
@Controller
@RequestMapping("/person/transfer")
public class CadreTransferController extends CadreBaseController {
    @Autowired
    CadreMapper cadreMapper;
    @Autowired
    CadreService cadreService;

    /**
     * @param
     * @return
     * @description 跳转到审核界面
     * @author Fkw
     * @date 2019/8/6
     */
    @GetMapping("/list")
    public String list() {
        return "/person/transfer/list";
    }

    /**
     * @param * @param modulecode:
     * @return
     * @description 模板
     * @author Fkw
     * @date 2019/8/2
     */
    @RequestMapping(value = "/getgridview")
    public void getgridview(@RequestParam String modulecode) {
        String json = "";
        json = super.getGridView(modulecode, "");
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
     * @param * @param treeid:
     * @return
     * @description 人事表格数据
     * @author Fkw
     * @date 2019/8/2
     */
    @RequestMapping(value = "/grid", method = RequestMethod.POST)
    public void cadreTransferGrid() {
        super.filter = " idnumber in (select idnumber from t_da_transfer)";
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.inSql("idnumber", "select idnumber from t_da_transfer");
        Map m = super.grid();
        String[] fieldArray = m.get("fieldlist").toString().split(",");
        String order = " order by pinyin asc";
        Page page = (Page) cadreMapper.selectPage(new Page(Long.valueOf(m.get("page").toString()), Long.valueOf(m.get("limit").toString())), (Wrapper<Cadre>) queryWrapper.orderByAsc("pinyin"));
        String json = super.pageToJson(page);
        result = "{basefilter:\"" + this.basefilter + "\"," + json.substring(1);
        super.toAjax(result);
    }

    /**
     * @description 增加转递干部
     * @author Fkw
     * @date 2019/8/2
     */
    @GetMapping(value = "/addsearch")
    public String cadreAddsearch() {
        return "/person/transfer/addsearch";
    }

    /**
     * @description 进入转递登记表单回显干部基本数据
     * @author Fkw
     * @date 2019/8/2
     */
    @RequestMapping(value = "/toaddform", method = RequestMethod.POST)
    public void toaddform(@RequestParam String id) {
        Cadre cadre = cadreMapper.selectOne(new QueryWrapper<Cadre>().eq("id", id));
        // 校驗是否已經存在
        List<Po> transferList = cadreMapper.getCadreBasic(CadreBasicEnum.getEnumByAtype("transfer").getTablename(), cadre.getIdnumber());
        if (transferList.size() < 1) {
            // 获取基本信息
            Po basicpo = cadreService.getCadreBasicDetail(cadre.getIdnumber());
            Po po = new Po();
            po.setIdnumber(cadre.getIdnumber());
            po.setWg01(cadre.getName());
            po.setWg02(cadre.getSex());
            po.setWg03(cadre.getOrigin());// 设置籍贯
            po.setWg04(basicpo.getWg06());
            po.setWg05(cadre.getPolitical());
            request.getSession().setAttribute("po", po);
            super.toAjax("{\"success\":true,\"msg\":\"\"}");
        } else {
            super.toAjax("{\"success\":true,\"msg\":\"已存在此干部人员\"}");

        }
    }

    /**
     * @param
     * @return
     * @description 转递申请登记表
     * @author Fkw
     * @date 2019/9/24
     */
    @GetMapping(value = "/transferadd")
    public String transferAdd() {
        return "/person/transfer/transferadd";
    }

    @Autowired
    CadreTransferMapper cadreTransferMapper;

    @RequestMapping(value = "/addtransfer", method = RequestMethod.POST)
    public void AddTransfer(CadreTransfer po) {
        po.setWg00(CommonUtil.getGuid());
        cadreTransferMapper.insert(po);
        super.toAjaxSuccess();
    }

    //查看
    @GetMapping(value = "/tolookform")
    public String toLookForm(@RequestParam String id) throws Exception {
        Po po = cadreTransferMapper.selectOne(new QueryWrapper<CadreTransfer>().eq("idnumber", id));
        request.getSession().setAttribute("po", po);
        return "/person/transfer/transferlook";
    }


    @RequestMapping(value = "/transferform", method = RequestMethod.POST)
    public void transferForm(@RequestParam String idnumber) {
        String[] idnumbers = idnumber.split(",");
        cadreTransferMapper.del("t_da_transferform");
        for (int i = 0; i < idnumbers.length; i++) {
            cadreTransferMapper.insertTransfer("t_da_transferform", "select * from t_da_transfer where idnumber='" + idnumbers[i] + "'");
        }
        super.toAjaxSuccess();
    }

    //导出数据包
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void export(@RequestParam String idnumber) {
        String path=cadreService.export(idnumber);
        super.toAjax("{\"success\":true,\"filepath\":\""+path+"\"}");
    }
}
