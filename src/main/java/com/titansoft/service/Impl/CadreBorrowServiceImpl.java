package com.titansoft.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.titansoft.entity.Catalogue;
import com.titansoft.entity.User;
import com.titansoft.entity.cadre.Cadre;
import com.titansoft.entity.media.MediaSource;
import com.titansoft.entity.media.Store;
import com.titansoft.entity.media.Storeitem;
import com.titansoft.entity.sqcd.*;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.entity.system.Sort;
import com.titansoft.model.*;
import com.titansoft.service.CadreBorrowService;
import com.titansoft.service.UserService;
import com.titansoft.utils.util.AESUtils;
import com.titansoft.utils.util.CommonUtil;
import com.titansoft.utils.util.DateTools;
import com.titansoft.utils.util.ImageTool;
import com.titansoft.utils.util.extjs.FormUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 查单申请处理类
 *
 * @Author: Kevin
 * @Date: 2019/9/25 19:12
 */
@Service
public class CadreBorrowServiceImpl extends BaseServiceImpl implements CadreBorrowService {
    @Autowired
    UserService userService;

    @Override
    public String tableColumns() {
        String columnsJson = "";
        //暂时采用固定方式,待开发平台完成后,进行修改
        List<Column> columns = new ArrayList<Column>();
        Column column = null;

        column = new Column();
        column.setDataIndex("sqcdid");
        column.setText("查档申请id");
        column.setXtype("gridcolumnview");
        column.setHidden(true);
        column.setFlex(0);
        column.setWidth(100);
        columns.add(column);

        column = new Column();
        column.setDataIndex("sqrname");
        column.setText("申请人");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(150);
        columns.add(column);

        column = new Column();
        column.setDataIndex("sqrunit");
        column.setText("申请人单位");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(200);
        columns.add(column);


        column = new Column();
        column.setDataIndex("daname");
        column.setText("查阅干部姓名");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(150);
        columns.add(column);


        column = new Column();
        column.setDataIndex("usetype");
        column.setText("查阅方式");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(150);
        columns.add(column);


        column = new Column();
        column.setDataIndex("applydate");
        column.setText("申请时间");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(150);
        columns.add(column);


        column = new Column();
        column.setDataIndex("approvalstate");
        column.setText("申请类型");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(150);
        columns.add(column);


        ColumnJson columnjson = new ColumnJson();
        columnjson.setColumns(columns);
        columnsJson = JSONObject.toJSONString(columnjson);
        return columnsJson;
    }

    /**
     * @param operate
     * @param sqcdItem :
     * @return
     * @description 表单
     * @author Fkw
     * @date 2019/9/26
     */
    @Override
    public String cadreBorrowForm(String operate, SqcdItem sqcdItem) {
        ArrayList list = new ArrayList();
        VO vo = new VO();
        vo.put("fieldname", "申请人");
        vo.put("fieldcode", "name");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("mustinput", "true");
        vo.put("iswhieline", "false");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "申请人单位");
        vo.put("fieldcode", "unitname");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("mustinput", "true");
        vo.put("iswhieline", "true");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "政治面貌");
        vo.put("fieldcode", "political");
        vo.put("defaultvalue", "党员");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("mustinput", "true");
        vo.put("iswhieline", "false");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "申请人手机号码");
        vo.put("fieldcode", "phonenumber");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("mustinput", "true");
        vo.put("iswhieline", "false");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "身份证号码");
        vo.put("fieldcode", "idnumber");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("mustinput", "true");
        vo.put("iswhieline", "false");
        list.add(vo);
        String json = "";
        XsdObject xsdObject;
        ArrayList hiddens = new ArrayList();
        xsdObject = new XsdObject();
        xsdObject.setFieldName("id");
        if (operate.equals("add")) {
            xsdObject.setDefaultvalue(CommonUtil.getGuid());
        } else {
            xsdObject.setDefaultvalue(sqcdItem.getId());
        }
        hiddens.add(xsdObject);
        xsdObject = new XsdObject();
        xsdObject.setFieldName("sqcdid");
        xsdObject.setDefaultvalue(sqcdItem.getSqcdid());
        hiddens.add(xsdObject);
        return FormUtil.ConvertJSON(list, sqcdItem, hiddens, operate);
    }

    /**
     * @param sqcdItem
     * @param logininfo :
     * @return
     * @description 添加查档人员基本信息
     * @author Fkw
     * @date 2019/9/26
     */
    @Override
    public String addSave(SqcdItem sqcdItem, Logininfo logininfo) {

        SqcdItem sqcdItem_db = sqcdItemMapper.selectById(sqcdItem.getId());
        if (sqcdItem_db == null) {
            sqcdItem.setId(CommonUtil.getGuid());
            sqcdItemMapper.insert(sqcdItem);
        } else {
            sqcdItemMapper.updateById(sqcdItem);
        }
        String sqid = sqcdItem.getSqcdid();
        String sqrname = "";
        String sqrunit = "";
        List list = sqcdItemMapper.selectList(new QueryWrapper<SqcdItem>().eq("sqcdid", sqid));
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                SqcdItem m = (SqcdItem) list.get(i);
                sqrname += m.getName() + "、";
                if (!sqrunit.contains(m.getUnitname())) {
                    sqrunit += m.getUnitname() + "、";
                }
            }
            Sqcd sqcd = sqcdMapper.selectById(sqid);// this.basedao.query("select * from " + Sqcd.getTablenameofpo() + " where id='" + sqid + "'");
            if (sqcd == null) {
                Sqcd sq = new Sqcd();
                sq.setId(sqid);
                sq.setSqrname(sqrname);
                sq.setSqrunit(sqrunit);
                sq.setApprovalstate("接待受理");
                sqcdMapper.insert(sq);
            } else {
                Sqcd sq = new Sqcd();
                sq.setId(sqid);
                sq.setSqrname(sqrname);
                sq.setSqrunit(sqrunit);
                sqcdMapper.updateById(sq);
            }
        }

      /*  //记录系统日志
        Date dt=new Date();
        String dotime= DateTools.pdateToString(dt, "yyyy-MM-dd HH:mm:ss");
        Logdata logdata = new Logdata(Constant.getGuid(), logininfo.getUsername(), logininfo.getRealname(),
                logininfo.getUnitname(), logininfo.getUnitid(), logininfo.getIpaddress(), dotime,
                ""+datapo.getUnitname()+"的"+datapo.getName()+"发起了申请查档",
                "申请登记", "正常","查询预约与受理-申请登记");
        this.basedao.addPo(Logdata.getTablenameofpo(),logdata);*/

        return sqcdItem.getId();
    }

    /**
     * @param sqcdid
     * @return
     * @description
     * @author Fkw
     * @date 2019/9/27
     */
    @Override
    public Boolean checkSqcdUser(String sqcdid) {
        boolean bool = false;
        String sqrname = "";
        String sqrunit = "";
        List list = sqcdItemMapper.selectList(new QueryWrapper<SqcdItem>().eq("sqcdid", sqcdid));
        if (list.size() > 0) {
            bool = true;
            for (int i = 0; i < list.size(); i++) {
                SqcdItem m = (SqcdItem) list.get(i);
                sqrname += m.getName() + "、";
                if (!sqrunit.contains(m.getUnitname().toString())) {
                    sqrunit += m.getUnitname().toString() + "、";
                }
            }
            Sqcd sqcd_db = sqcdMapper.selectById(sqcdid);// this.basedao.query("select * from " + Sqcd.getTablenameofpo() + " where id='" + sqid + "'");
            if (sqcd_db == null) {
                Sqcd sqcd = new Sqcd();
                sqcd.setId(sqcdid);
                sqcd.setSqrname(sqrname);
                sqcd.setSqrunit(sqrunit);
                sqcd.setApprovalstate("接待受理");
                sqcdMapper.insert(sqcd);
            }
        } else {
            bool = false;
        }
        return bool;
    }

    /**
     * @param sqcdid
     * @return
     * @description 查档方式表单
     * @author Fkw
     * @date 2019/9/27
     */
    @Override
    public String usertypeForm(String sqcdid) {
        ArrayList list = new ArrayList();
        Sqcd sqcd = sqcdMapper.selectById(sqcdid);
        VO vo = new VO();
        vo = new VO();
        vo.put("fieldname", "查档方式");
        vo.put("fieldcode", "cdtype");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("controltype", "combox");
        vo.put("display", "true");
        vo.put("mustinput", "true");
        vo.put("iswhieline", "false");
        vo.put("comboxdata", "现场查阅-现场查阅|手机申请-手机申请");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "利用方式");
        vo.put("fieldcode", "usetype");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("controltype", "combox");
        vo.put("display", "true");
        vo.put("mustinput", "true");
        vo.put("iswhieline", "false");
        vo.put("comboxdata", "查（借）阅-查（借）阅|复制-复制|摘录-摘录");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "查档事由");
        vo.put("fieldcode", "reason");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("controltype", "combox");
        vo.put("display", "true");
        vo.put("iswhieline", "true");
        vo.put("mustinput", "true");
        vo.put("comboxsql", sqcdreasonMapper.selectMaps(new QueryWrapper<SqcdReason>().select("reason").orderByAsc("sequence")));
        list.add(vo);
        String json = "";
        XsdObject xsdObject;
        ArrayList hiddens = new ArrayList();

        xsdObject = new XsdObject();
        xsdObject.setFieldName("id");
        xsdObject.setDefaultvalue(sqcdid);
        hiddens.add(xsdObject);
        json = FormUtil.ConvertJSON(list, sqcd, hiddens, "edit");
        json = json.replace("保存", "下一步");
        json = json.substring(0, json.length() - 2)
                + ",{\"xtype\":\"button\",\"text\":\"上传材料\",\"id\":\"Upload\",\"type\":\"button\",\"handler\":uploadClick,\"isLine\":false,\"iconCls\":\"icon-down2\"}]}";
        return json;
    }

    /**
     * @param * @param sqcd:
     * @return
     * @description 保存利用方式
     * @author Fkw
     * @date 2019/9/27
     */
    @Override
    public String saveUsetype(Sqcd sqcd) {
        sqcdMapper.updateById(sqcd);
        SqcdItem si = new SqcdItem();
        si.setUsetype(sqcd.getUsetype());
        si.setCdtype(sqcd.getCdtype());
        si.setReason(sqcd.getReason());
        sqcdItemMapper.update(si, new UpdateWrapper<SqcdItem>().eq("sqcdid", sqcd.getId()));
        return sqcd.getId();
    }

    /**
     * @param parentid
     * @param sqcdid   :
     * @param idnumber :
     * @return
     * @description 获取选择干部的分类树
     * @author Fkw
     * @date 2019/9/27
     */
    @Override
    public List sortTree(String parentid, String sqcdid, String idnumber) {
        List<Exttreenot> treelist = new ArrayList<Exttreenot>();
        List<Datapermission> dataPermissionList = new ArrayList<Datapermission>();
        List list = null;
        Exttreenot node = null;
        List userlist = null;
        if ("0".equals(parentid) || "root".equals(parentid)) {
            list = sortMapper.selectList(new QueryWrapper<Sort>().isNull("parentid").orderByAsc("sequence"));

        } else {
            list = sortMapper.selectList(new QueryWrapper<Sort>().eq("parentid", parentid).orderByAsc("sequence"));

        }
        // 判断是否已经分配查阅权限，若有进行数据回显
        List sqcdlist = sqcdPowerMapper.selectList(new QueryWrapper<SqcdPower>().eq("sqcdid", sqcdid).eq("idnumber", idnumber));
        String sqcdsortid = "";
        if (sqcdlist.size() > 0) {
            for (int i = 0; i < sqcdlist.size(); i++) {
                SqcdPower sqcdPower = (SqcdPower) sqcdlist.get(i);
                sqcdsortid += sqcdPower.getSortid() + ",";
            }
        }
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Sort sort = (Sort) it.next();
            Datapermission datapermission = new Datapermission();
            datapermission.setId((String) sort.getId());
            datapermission.setText(sort.getSortcode() + " " + sort.getSortfullname());
            datapermission.setLeaf(false);
            if (sqcdlist.size() > 0) {
                if (sqcdsortid.contains(sort.getId())) {
                    datapermission.setChecked(true);
                    if (sortMapper.selectCount(new QueryWrapper<Sort>().eq("parentid", sort.getId())) > 0) {
                        datapermission.setChecked2(true);
                    }
                } else {
                    datapermission.setChecked(false);
                }
            } else {
                datapermission.setChecked(false);
            }
            dataPermissionList.add(datapermission);
        }
        return dataPermissionList;
    }

    /**
     * @param sqcdid
     * @param idnumber :
     * @return
     * @description 删除已经设置好分类和查看权限的干部
     * @author Fkw
     * @date 2019/9/27
     */
    @Transactional
    @Override
    public void delCadre(String sqcdid, String idnumber) {
        sqcdPowerMapper.delete(new UpdateWrapper<SqcdPower>().eq("idnumber", idnumber).eq("sqcdid", sqcdid));
    }

    /**
     * @param id
     * @return
     * @description 申请完成
     * @author Fkw
     * @date 2019/9/27
     */
    @Override
    public void sqcdFinish(String id) {
        Sqcd sqcd = sqcdMapper.selectById(id);
        sqcd.setApplydate(DateTools.pdateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        sqcdMapper.updateById(sqcd);
        List list = sqcdItemMapper.selectList(new QueryWrapper<SqcdItem>().eq("sqcdid", id));
        String phones = "";
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                SqcdItem sqcdItem = (SqcdItem) list.get(i);
                User user = new User();
                user.setCode(sqcdItem.getPhonenumber());
                user.setName(sqcdItem.getName());
                userService.createUser(user, DateTools.pdateToString(new Date(), "yyyyMMdd"));
            }
        }
    }

    /**
     * @param sortids
     * @param sqcdid   :
     * @param idnumber :
     * @return
     * @description 临时保存分配查阅权限
     * @author Fkw
     * @date 2019/9/27
     */
    @Override
    public void saveSqcdPowerTemp(String sortids, String sqcdid, String idnumber) {
        sqcdPowerMapper.delete(new UpdateWrapper<SqcdPower>().eq("sqcdid", sqcdid).eq("idnumber", idnumber));
        String[] sortid = sortids.split(",");
        for (int i = 0; i < sortid.length; i++) {
            if (!"".equals(sortid[i]) && !"root".equals(sortid[i])) {
                SqcdPower sqcdPower = new SqcdPower();
                sqcdPower.setId(CommonUtil.getGuid());
                sqcdPower.setSqcdid(sqcdid);
                sqcdPower.setSortid(sortid[i]);
                sqcdPower.setIdnumber(idnumber);
                sqcdPowerMapper.insert(sqcdPower);

            }
        }
        Cadre cadre = cadreMapper.selectOne(new QueryWrapper<Cadre>().eq("idnumber", idnumber));
        Sqcd sqcd = sqcdMapper.selectById(sqcdid);
        String names = sqcd.getDaname();
        sqcd.setDaname(cadre.getName() + "、");
        sqcdMapper.updateById(sqcd);
    }

    @Override
    public void addWaterMarkImage(String sqcdid) {
        Sqcd sqcd = sqcdMapper.selectById(sqcdid);
        Cadre cadre = cadreMapper.selectOne(new QueryWrapper<Cadre>().eq("idnumber", sqcd.getIdnumber()));
        if (cadre == null) {
            return;
        }
        Store watermarkStore = storeMapper.selectOne(new QueryWrapper<Store>().eq("datatype", "WATERMARK"));
        String wmlocation = "";
        String epplocation = "";
        if (watermarkStore != null) {
            wmlocation = watermarkStore.getLocation();
        }
        Store eepStore = storeMapper.selectOne(new QueryWrapper<Store>().eq("datatype", "EEP"));
        if (eepStore != null) {
            epplocation = eepStore.getLocation();
        }
        List storeList = storeitemMapper.selectList(new QueryWrapper<Storeitem>().inSql("id", "select storeitemid from " + CommonUtil.getTableName(MediaSource.class) + " where erid in (select id from " + CommonUtil.getTableName(Catalogue.class) + " where idnumber ='" + sqcd.getIdnumber() + "' and sortcode in (select sortcode from " + CommonUtil.getTableName(Sort.class) + " where id in (select sortid from " + CommonUtil.getTableName(SqcdPower.class) + " where sqcdid='" + sqcd.getId() + "')))"));
        // 创建存放水印图片文件夹
        File wmfile = new File(
                wmlocation + "/" + sqcd.getSqrname() + sqcd.getSqrphone() + sqcd.getExpirydate() + "/"
                        + cadre.getUnitname() + "/" + cadre.getName() + cadre.getIdnumber() + "/优化图像数据");
        if (!wmfile.exists()) {
            wmfile.mkdirs();
        }
        for (int i = 0; i < storeList.size(); i++) {
            Storeitem storeitem = (Storeitem) storeList.get(i);
            String eneppurl = storeitem.getEneppurl();
            if ("".equals(eneppurl)) {
                System.out.println(storeitem.getId());
                continue;
            }
            byte[] enInputData = AESUtils.parseHexStr2Byte(eneppurl);
            byte[] enOutputData = AESUtils.decrypt(enInputData, AESUtils.getSecretKey("GZSGAT-GBRSDA"));
            String enOutputStr = new String(enOutputData);
            eneppurl = epplocation + "/" + enOutputStr;
            AESUtils.decryptedFile(eneppurl.replace("原始图像数据", "优化图像数据"),
                    wmlocation + "/" + sqcd.getSqrname() + sqcd.getSqrphone() + sqcd.getExpirydate()
                            + "/" + enOutputStr.replace("原始图像数据", "优化图像数据"));
            ImageTool.addWaterMark(
                    wmlocation + "/" + sqcd.getSqrname() + sqcd.getSqrphone() + sqcd.getExpirydate()
                            + "/" + enOutputStr.replace("原始图像数据", "优化图像数据"),
                    wmlocation + "/" + sqcd.getSqrname() + sqcd.getSqrphone() + sqcd.getExpirydate()
                            + "/" + enOutputStr.replace("原始图像数据", "优化图像数据"),
                    "" + sqcd.getSqrname() + " " + sqcd.getSqrphone() + " 日期："
                            + DateTools.pdateToString(new Date(), "yyyy-MM-dd"));
        }
    }

    /**
     * @param id
     * @param sqid     :
     * @param idnumber :
     * @return
     * @description 保存分配好的查阅权限
     * @author Fkw
     * @date 2019/10/11
     */
    @Transactional
    @Override
    public void saveSqcdPower(String id, String sqid, String idnumber) {
        sqcdStoreMapper.delete(new UpdateWrapper<SqcdStore>().eq("sqcdid", sqid).eq("idnumber", idnumber));
        String[] idArry = id.split(",");
        for (int i = 0; i < idArry.length; i++) {
            Storeitem storeitem = storeitemMapper.selectById(idArry[i]);
            if (storeitem == null) {
                continue;
            }
            SqcdStore sqcdStore = new SqcdStore();
            sqcdStore.setId(CommonUtil.getGuid());
            sqcdStore.setSqcdid(sqid);
            sqcdStore.setStoreid(storeitem.getId());
            sqcdStore.setIdnumber(idnumber);
            sqcdStoreMapper.insert(sqcdStore);
        }
    }
}
