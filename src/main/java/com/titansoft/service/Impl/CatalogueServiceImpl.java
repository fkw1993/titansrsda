package com.titansoft.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.titansoft.entity.Catalogue;
import com.titansoft.entity.media.MediaSource;
import com.titansoft.entity.media.Store;
import com.titansoft.entity.media.Storeitem;
import com.titansoft.entity.sqcd.SqcdStore;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.entity.system.Sort;
import com.titansoft.service.CatalogueService;
import com.titansoft.utils.util.CommonUtil;
import com.titansoft.utils.util.DateTools;
import com.titansoft.utils.util.FileUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/8/1 16:51
 */
@Service
public class CatalogueServiceImpl extends BaseServiceImpl implements CatalogueService {
    /**
     * @param idNumber
     * @return
     * @description 初始化干部目录信息（根据分类预留空行数量补齐）
     * @author Fkw
     * @date 2019/8/1
     */
    @Override
    public void initCatalogueInfo(String idNumber) {
        List sortList = sortMapper.selectList(new QueryWrapper<Sort>().eq("isleaf", "true"));
        for (int i = 0, len = sortList.size(); i < len; i++) {
            Sort sort = (Sort) sortList.get(i);
            String sortCode = sort.getSortcode().toString();
            int blankLine = Integer.parseInt(sort.getBlankline().toString());
            List catalogueLis = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("idnumber", idNumber).eq("sortcode", sortCode).orderByAsc("sequence"));
            int cataSequ = catalogueLis.size();
            if (catalogueLis.isEmpty() || cataSequ < blankLine) {
                for (int j = cataSequ; j < blankLine; j++) {
                    Catalogue catalogue = new Catalogue();
                    catalogue.setSortcode(sortCode);
                    catalogue.setIdnumber(idNumber);
                    catalogue.setSequence(String.valueOf(j + 1));
                    catalogue.setId(CommonUtil.getGuid());
                    catalogueMapper.insert(catalogue);
                }
            }
        }
    }

    /**
     * @param treeId
     * @return
     * @description 通过干部编号获取(该方法只适用分类两级 ， 目录一级)
     * @author Fkw
     * @date 2019/8/1
     */
    @Override
    public String getCatalogueInfo(String treeId, String idNumber) {
        String json = "";
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;
        QueryWrapper<Sort> sortQueryWrapper = new QueryWrapper<>();
        if ("".equals(treeId) || "root".equals(treeId) || treeId == null) {
            sortQueryWrapper.isNull("parentid");
        } else {
            sortQueryWrapper.eq("parentid", treeId);
        }
        List sortList = sortMapper.selectList(sortQueryWrapper.select("id", "sortcode", "sortname", "sortfullname", "sequence", "parentid", "blankline").orderByAsc("sequence"));

        for (int i = 0, len = sortList.size(); i < len; i++) {
            Sort sort = (Sort) sortList.get(i);
            String id = sort.getId().toString();
            String sortCodeF = sort.getSortcode().toString();
            jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("sortcode", sortCodeF);
            jsonObject.put("sortname", sort.getSortname().toString());
            jsonObject.put("issort", "true");
            jsonObject.put("blankline", Integer.parseInt(sort.getBlankline().toString()));
            jsonObject.put("sequence", sort.getSequence());
            jsonObject.put("isParent", "true");
            jsonObject.put("isHaveMedia", "");

            List<Sort> listSub = sortMapper.selectList(new QueryWrapper<Sort>().select("id", "sortcode", "sortname", "sortfullname", "sequence", "parentid", "blankline").eq("parentid", id).orderByAsc("sequence"));
            if (listSub.isEmpty()) {
                jsonObject.put("isleaf", "true");
            } else {
                jsonObject.put("isleaf", "false");
            }
            jsonArray.add(jsonObject);
            for (int j = 0, lenSub = listSub.size(); j < lenSub; j++) {
                Sort sortSub = (Sort) listSub.get(j);
                String ids = sortSub.getId().toString();
                String sortCodeS = sortSub.getSortcode().toString();
                jsonObject = new JSONObject();
                jsonObject.put("id", ids);
                jsonObject.put("sortcode", sortCodeS);
                jsonObject.put("sortname", sortSub.getSortname().toString());
                jsonObject.put("catayear", "");
                jsonObject.put("catamonth", "");
                jsonObject.put("cataday", "");
                jsonObject.put("pagenumber", "");
                jsonObject.put("bak", "");
                jsonObject.put("issort", "true");
                jsonObject.put("blankline", Integer.parseInt(sortSub.getBlankline().toString()));
                jsonObject.put("sequence", sortSub.getSequence());
                jsonObject.put("isParent", "false");
                jsonObject.put("isHaveMedia", "");
                jsonArray.add(jsonObject);

                // 第二级分类下面的目录
                List cataListS = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("idnumber", idNumber).eq("sortcode", sortCodeS).orderByAsc("SEQUENCE"));
                for (int x = 0, lenCata = cataListS.size(); x < lenCata; x++) {
                    Catalogue catalogue = (Catalogue) cataListS.get(x);
                    Map mapCata = CommonUtil.convertToMap(catalogue);
                    String idCata = mapCata.get("id").toString();
                    List mediaList = catalogueMapper.getMedia(idCata);
                    jsonObject = new JSONObject();
                    jsonObject.put("id", idCata);
                    jsonObject.put("sortcode", mapCata.get("catacode").toString());
                    jsonObject.put("sortname", mapCata.get("catatitle").toString());
                    jsonObject.put("catayear", mapCata.get("catayear").toString());
                    jsonObject.put("catamonth", mapCata.get("catamonth").toString());
                    jsonObject.put("cataday", mapCata.get("cataday").toString());
                    jsonObject.put("pagenumber", mapCata.get("pagenumber").toString());
                    jsonObject.put("bak", mapCata.get("bak").toString());
                    jsonObject.put("issort", "false");
                    jsonObject.put("sequence", Integer.parseInt(mapCata.get("sequence").toString()));
                    jsonObject.put("isParent", "false");
                    jsonObject.put("status", mapCata.get("status").toString());
                    jsonObject.put("isHaveMedia", mediaList.size() > 0 ? "true" : "false");
                    jsonArray.add(jsonObject);
                }
            }

            // 第一级分类下面的目录
            List cataListF = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("idnumber", idNumber).eq("sortcode", sortCodeF).orderByAsc("SEQUENCE"));//this.catalogueBS.getCatalogueByCadrecode(idNumber, sortCodeF);
            for (int x = 0, lenCata = cataListF.size(); x < lenCata; x++) {
                Catalogue catalogue = (Catalogue) cataListF.get(x);
                Map mapCata = CommonUtil.convertToMap(catalogue);
                String idCata = mapCata.get("id").toString();
                List mediaList = catalogueMapper.getMedia(idCata);
                jsonObject = new JSONObject();
                jsonObject.put("id", idCata);
                jsonObject.put("sortcode", mapCata.get("catacode").toString());
                jsonObject.put("sortname", mapCata.get("catatitle").toString());
                jsonObject.put("catayear", mapCata.get("catayear").toString());
                jsonObject.put("catamonth", mapCata.get("catamonth").toString());
                jsonObject.put("cataday", mapCata.get("cataday").toString());
                jsonObject.put("pagenumber", mapCata.get("pagenumber").toString());
                jsonObject.put("bak", mapCata.get("bak").toString());
                jsonObject.put("issort", "false");
                jsonObject.put("sequence", Integer.parseInt(mapCata.get("sequence").toString()));
                jsonObject.put("isParent", "false");
                jsonObject.put("status", mapCata.get("status").toString());
                jsonObject.put("isHaveMedia", mediaList.size() > 0 ? "true" : "false");
                jsonArray.add(jsonObject);
            }
        }
        json = jsonArray.toString();
        return json;
    }

    /**
     * @param idnumber
     * @param sortCode   :
     * @param cataId     :
     * @param fieldName  :
     * @param fieldValue :
     * @param logininfo  :
     * @return
     * @description 保存干部目录信息
     * @author Fkw
     * @date 2019/8/2
     */
    @Override
    public void saveCatalogueInfo(String idnumber, String sortCode, String cataId, String fieldName, String fieldValue, Logininfo logininfo) {
        try {
            String name = "";
            List catalogueList = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("id", cataId));
            if (catalogueList.isEmpty()) {// insert
                Catalogue catalogue = new Catalogue();
                catalogue.setId(CommonUtil.getGuid());
                catalogue.setIdnumber(idnumber);
                catalogue.setCatacode(sortCode);
                catalogue.setSortcode(sortCode.substring(0, sortCode.indexOf("-")));
                catalogue.setStatus("cj");
                switch (fieldName) {
                    case "catatitle":
                        catalogue.setCatatitle(fieldValue);
                        name = "材料题名";
                        break;
                    case "catayear":
                        catalogue.setCatayear(fieldValue);
                        name = "材料年份";
                        break;
                    case "catamonth":
                        catalogue.setCatamonth(fieldValue);
                        name = "材料月份";
                        break;
                    case "cataday":
                        catalogue.setCataday(fieldValue);
                        name = "材料日期";
                        break;
                    case "pagenumber":
                        catalogue.setPagenumber(fieldValue);
                        name = "材料页数";
                        break;
                    case "bak":
                        catalogue.setBak(fieldValue);
                        name = "材料备注";
                        break;
                    default:
                        System.out.println("无匹配");
                }
                catalogueMapper.insert(catalogue);

               /* //过程信息
                DaProcess p = new DaProcess(Constant.getGuid(), logininfo.getRealname(), "增加了目录编号为<" + sortCode + ">的" + name + "," + name + "的值为：" + fieldValue, DateTools.pdateToString(new Date(), "yyyy-MM-dd HH:mm:ss"), idnumber);
                this.basedao.addPo(DaProcess.getTablenameofpo(), p);

                //系统日志
                Date dt = new Date();
                String dotime = DateTools.pdateToString(dt, "yyyy-MM-dd HH:mm:ss");
                Logdata logdata = new Logdata(Constant.getGuid(), logininfo.getUsername(), logininfo.getRealname(),
                        logininfo.getUnitname(), logininfo.getUnitid(), logininfo.getIpaddress(), dotime,
                        "增加了目录编号为<" + sortCode + ">的" + name + "," + name + "的值为：" + fieldValue,
                        "新增", "正常", "");
                this.basedao.addPo(Logdata.getTablenameofpo(), logdata);*/

            } else {// update
                Catalogue catalogue = (Catalogue) catalogueList.get(0);
                Map m = CommonUtil.convertToMap(catalogue);
                String oldvalue = (String) m.get(fieldName);
                switch (fieldName) {
                    case "catatitle":
                        name = "材料题名";
                        catalogue.setCatatitle(fieldValue);
                        break;
                    case "catayear":
                        name = "材料年份";
                        catalogue.setCatayear(fieldValue);
                        break;
                    case "catamonth":
                        name = "材料月份";
                        catalogue.setCatamonth(fieldValue);
                        break;
                    case "cataday":
                        name = "材料日期";
                        catalogue.setCataday(fieldValue);
                        break;
                    case "pagenumber":
                        name = "材料页数";
                        catalogue.setPagenumber(fieldValue);
                        break;
                    case "bak":
                        name = "材料备注";
                        catalogue.setBak(fieldValue);
                        break;
                    default:
                        System.out.println("无匹配");
                }
                catalogue.setStatus("cj");
                //更新记录
                catalogueMapper.update(catalogue, new UpdateWrapper<Catalogue>().eq("id", cataId));
                Date dt = new Date();
                String dotime = DateTools.pdateToString(dt, "yyyy-MM-dd HH:mm:ss");
                String cataCode = m.get("sortcode").toString() + "-" + m.get("sequence").toString();
               /* if (oldvalue == null || "".equals(oldvalue)) {
                    DaProcess p = new DaProcess(Constant.getGuid(), logininfo.getRealname(), "增加了目录编号为<" + cataCode + ">的" + name + "," + name + "的值为：" + fieldValue, DateTools.pdateToString(new Date(), "yyyy-MM-dd HH:mm:ss"), idnumber);
                    this.basedao.addPo(DaProcess.getTablenameofpo(), p);

                    //系统日志
                    Logdata logdata = new Logdata(Constant.getGuid(), logininfo.getUsername(), logininfo.getRealname(),
                            logininfo.getUnitname(), logininfo.getUnitid(), logininfo.getIpaddress(), dotime,
                            "增加了目录编号为<" + cataCode + ">的" + name + "," + name + "的值为：" + fieldValue,
                            "新增", "正常", "人事档案采集-编辑-增加目录信息");
                    this.basedao.addPo(Logdata.getTablenameofpo(), logdata);
                } else {
                    //过程信息
                    DaProcess p = new DaProcess(Constant.getGuid(), logininfo.getRealname(), "修改了目录编号为<" + m.get("sortcode") + "-" + m.get("sequence") + ">的<" + name + ">,原值<" + oldvalue + "> 修改后的值<" + fieldValue + ">", DateTools.pdateToString(new Date(), "yyyy-MM-dd HH:mm:ss"), m.get("idnumber").toString());
                    this.basedao.addPo(DaProcess.getTablenameofpo(), p);

                    //系统日志
                    Logdata logdata = new Logdata(Constant.getGuid(), logininfo.getUsername(), logininfo.getRealname(),
                            logininfo.getUnitname(), logininfo.getUnitid(), logininfo.getIpaddress(), dotime,
                            "修改了目录编号为<" + cataCode + ">的" + name + "," + name + "的值为：" + fieldValue,
                            "修改", "正常", "人事档案采集-编辑-修改目录信息");
                    this.basedao.addPo(Logdata.getTablenameofpo(), logdata);
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param idNumber
     * @param cataId   :
     * @return
     * @description 新增目录行
     * @author Fkw
     * @date 2019/8/2
     */
    @Override
    public void saveAddCatalogue(String idNumber, String cataId) {
        try {
            List catalogueList = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("id", cataId));
            if (!catalogueList.isEmpty()) {
                Map map = CommonUtil.convertToMap(catalogueList.get(0));
                String sortcode = map.get("sortcode").toString();
                int squence = Integer.parseInt(map.get("sequence").toString());
                List listS = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("sortcode", sortcode).eq("idnumber", idNumber).orderByAsc("sequence"));
                if (squence != listS.size()) {
                    for (int i = 0; i < listS.size(); i++) {
                        Map maps = CommonUtil.convertToMap(listS.get(i));
                        int squenceS = Integer.parseInt(maps.get("sequence").toString());
                        if (squenceS > squence) {
                            String newSeq = String.valueOf(squenceS + 1);
                            Catalogue catalogue = (Catalogue) listS.get(i);
                            catalogue.setSequence(newSeq);
                            catalogueMapper.update(catalogue, new UpdateWrapper<Catalogue>().eq("id", catalogue.getId()));
                        }
                    }
                }

                String addSqu = String.valueOf(squence + 1);
                Catalogue catalogue = new Catalogue();
                catalogue.setId(CommonUtil.getGuid());
                catalogue.setSequence(addSqu);
                catalogue.setSortcode(sortcode);
                catalogue.setIdnumber(idNumber);
                catalogueMapper.insert(catalogue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param idNumber
     * @param cataId   :
     * @return
     * @description 删除目录
     * @author Fkw
     * @date 2019/8/2
     */
    @Override
    public void removeCatalogueInfo(String idNumber, String cataId, Logininfo logininfo) throws IOException {
        //获取Mediasource注解，通过注解获取表名
        TableName tableName = MediaSource.class.getAnnotation(TableName.class);
        // 删除电子文件
        List<Storeitem> listItem = storeitemMapper.selectList(new QueryWrapper<Storeitem>().inSql("id", "select storeitemid from " + tableName.value() + " where erid = '" + cataId + "'"));
        if (!listItem.isEmpty()) {
            String location = "";
            String locationOpt = "";
            String locationWm = "";
            List<Store> sourceList = storeMapper.selectList(new QueryWrapper<Store>().eq("datatype", "SOURCE"));
            if (!sourceList.isEmpty()) {
                Store store = (Store) sourceList.get(0);
                location = store.getLocation();
            }
            List<Store> optList = storeMapper.selectList(new QueryWrapper<Store>().eq("datatype", "OPTIMIZE"));
            if (!optList.isEmpty()) {
                Store store = (Store) optList.get(0);
                locationOpt = store.getLocation();
            }

            List<Store> wmList = storeMapper.selectList(new QueryWrapper<Store>().eq("datatype", "WATERMARK"));
            if (!wmList.isEmpty()) {
                Store store = (Store) wmList.get(0);
                locationWm = store.getLocation();
            }

            Storeitem storeitem = (Storeitem) listItem.get(0);
            String url = storeitem.getUrl();
            String urlDir = url.substring(0, url.lastIndexOf("/"));

            //原图片
            String sourceDir = location + "/" + urlDir;
            FileUtil.delDir(sourceDir);

            //水印图片
            String wmDir = locationWm + "/" + urlDir;
            FileUtil.delDir(wmDir);

            //优化图片
            String optDir = locationOpt + "/" + urlDir;
            FileUtil.delDir(optDir);
        }
        // 删除目录

        List catalogueList = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("id", cataId));

        if (!catalogueList.isEmpty()) {
            Catalogue catalogue = (Catalogue) catalogueList.get(0);
            String sortcode = catalogue.getSortcode();
            int squence = Integer.valueOf(catalogue.getSequence());
            catalogueMapper.deleteById(cataId);
            List<Catalogue> listS = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("sortcode", sortcode).eq("idnumber", idNumber).orderByAsc("sequence"));
               /* //过程信息
                DaProcess p=new DaProcess(Constant.getGuid(),logininfo.getRealname(),"删除了目录题名为<"+map.get("catatitle")+">的目录",DateTools.pdateToString(new Date(), "yyyy-MM-dd HH:mm:ss"),idnumber);
                this.basedao.addPo(DaProcess.getTablenameofpo(), p);

                //记录系统日志
                Date dt=new Date();
                String dotime=DateTools.pdateToString(dt, "yyyy-MM-dd HH:mm:ss");
                String catTitle = map.get("catatitle").toString();
                String cataCode = map.get("sortcode").toString() + "-" + map.get("sequence").toString();
                if(catTitle!=null && !"".equals(catTitle)){
                    Logdata logdata = new Logdata(Constant.getGuid(), logininfo.getUsername(), logininfo.getRealname(),
                            logininfo.getUnitname(), logininfo.getUnitid(), logininfo.getIpaddress(), dotime,
                            "删除了目录编号为<"+cataCode+">的干部目录",
                            "删除目录行", "正常","人事档案采集-编辑-删除目录行");
                    this.basedao.addPo(Logdata.getTablenameofpo(),logdata);
                }*/

            if (squence <= listS.size()) {
                for (int i = 0; i < listS.size(); i++) {
                    Catalogue catalogue1 = (Catalogue) listS.get(i);
                    int squenceS = Integer.parseInt(catalogue1.getSequence());
                    if (squenceS > squence) {
                        String newSeq = String.valueOf(squenceS - 1);
                        catalogue1.setSequence(newSeq);
                        catalogueMapper.updateById(catalogue1);

                    }
                }
            }

        }

    }

    /**
     * @param cataId
     * @return
     * @description 判断目录标题是否为空
     * @author Fkw
     * @date 2019/8/2
     */
    @Override
    public boolean checkTitle(String cataId) {
        boolean bool = false;
        List catalogueList = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("id", cataId));
        if (catalogueList.size() > 0) {
            Catalogue catalogue = (Catalogue) catalogueList.get(0);
            String title = catalogue.getCatatitle();
            if (title == null || "".equals(title)) {
                bool = false;
            } else {
                bool = true;
            }
        } else {
            bool = false;
        }
        return bool;
    }

    /**
     * @param idnumber
     * @return
     * @description 人事档案详细信息的目录树
     * @author Fkw
     * @date 2019/8/4
     */
    @Override
    public String getTreeJson(String idnumber) {
        String json = "";
        try {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = null;
            List sortList = sortMapper.selectList(new QueryWrapper<Sort>().select("id", "sortcode", "sortname", "sortfullname", "sequence", "parentid", "blankline").isNull("parentid").orderByAsc("sequence"));
            for (int i = 0, len = sortList.size(); i < len; i++) {
                Map map = (HashMap) CommonUtil.convertToMap(sortList.get(i));
                String id = map.get("id").toString();
                String sortCodeF = map.get("sortcode").toString();
                String text = map.get("sortname").toString();
                jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("pId", "0");
                jsonObject.put("name", text);
                jsonObject.put("open", "true");
                jsonObject.put("isParent", "true");
                jsonObject.put("issort", "true");
                jsonObject.put("status", "");
                jsonArray.add(jsonObject);
                List listSub = sortMapper.selectList(new QueryWrapper<Sort>().select("id", "sortcode", "sortname", "sortfullname", "sequence", "parentid", "blankline").eq("parentid", id).orderByAsc("sequence"));
                for (int j = 0, lenSub = listSub.size(); j < lenSub; j++) {
                    Map mapSub = (HashMap) CommonUtil.convertToMap(listSub.get(j));
                    String ids = mapSub.get("id").toString();
                    String sortCodeS = mapSub.get("sortcode").toString();
                    String texts = mapSub.get("sortname").toString();
                    jsonObject = new JSONObject();
                    jsonObject.put("id", ids);
                    jsonObject.put("pId", id);
                    jsonObject.put("name", texts);
                    jsonObject.put("pName", id);
                    jsonObject.put("issort", "true");
                    jsonObject.put("status", "");
                    jsonArray.add(jsonObject);
                    List cataListS = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("idnumber", idnumber).eq("sortcode", sortCodeS).ne("CATATITLE", "").isNotNull("CATATITLE").orderByAsc("sequence"));
                    for (int x = 0, lenCata = cataListS.size(); x < lenCata; x++) {
                        Map mapCata = (HashMap) CommonUtil.convertToMap(cataListS.get(x));
                        String idCata = mapCata.get("id").toString();
                        String textc = mapCata.get("catatitle").toString();
                        String status = mapCata.get("status").toString();
                        jsonObject = new JSONObject();
                        jsonObject.put("id", idCata);
                        jsonObject.put("pId", ids);
                        jsonObject.put("name", textc);
                        jsonObject.put("pName", texts);
                        jsonObject.put("issort", "false");
                        jsonObject.put("cataClass", "sec");
                        jsonObject.put("status", status);
                        jsonArray.add(jsonObject);
                    }
                }
                // 第一级分类下面的目录
                List cataListF = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("idnumber", idnumber).eq("sortcode", sortCodeF).ne("CATATITLE", "").isNotNull("CATATITLE").orderByAsc("sequence"));
                for (int x = 0, lenCata = cataListF.size(); x < lenCata; x++) {
                    Map mapCata = (HashMap) CommonUtil.convertToMap(cataListF.get(x));
                    String idCata = mapCata.get("id").toString();
                    String textc = mapCata.get("catatitle").toString();
                    String status = mapCata.get("status").toString();
                    jsonObject = new JSONObject();
                    jsonObject.put("id", idCata);
                    jsonObject.put("pId", id);
                    jsonObject.put("name", textc);
                    jsonObject.put("pName", text);
                    jsonObject.put("issort", "false");
                    jsonObject.put("cataClass", "firs");
                    jsonObject.put("status", status);
                    jsonArray.add(jsonObject);
                }
            }
            json = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return json;
        }
    }

    /**
     * @param idnumber
     * @param sqcdid   :
     * @return
     * @description 查档预约模块的加载已经勾选的分类树
     * @author Fkw
     * @date 2019/9/28
     */
    @Override
    public String getTreeJsonSqcd(String idnumber, String sqcdid) {
        String json = "";
        try {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = null;
            String treeId = "";
            List sortList = sortMapper.selectList(new QueryWrapper<Sort>().select("id", "sortcode", "sortname", "sortfullname", "sequence", "parentid", "blankline").isNull("parentid").inSql("id", "select sortid from T_BUS_SQCDPOWER where sqcdid ='" + sqcdid + "'").orderByAsc("sequence"));
            for (int i = 0, len = sortList.size(); i < len; i++) {
                Map map = (HashMap) CommonUtil.convertToMap(sortList.get(i));
                String id = map.get("id").toString();
                String sortCodeF = map.get("sortcode").toString();
                String text = map.get("sortname").toString();
                jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("pId", "0");
                jsonObject.put("name", text);
                jsonObject.put("open", "true");
                jsonObject.put("isParent", "true");
                jsonObject.put("issort", "true");
                jsonObject.put("sortcode", sortCodeF);
                jsonArray.add(jsonObject);
                List listSub = sortMapper.selectList(new QueryWrapper<Sort>().select("id", "sortcode", "sortname", "sortfullname", "sequence", "parentid", "blankline").eq("parentid", id).inSql("id", "select sortid from T_BUS_SQCDPOWER where sqcdid ='" + sqcdid + "'").orderByAsc("sequence"));
                for (int j = 0, lenSub = listSub.size(); j < lenSub; j++) {
                    Map mapSub = (HashMap) CommonUtil.convertToMap(listSub.get(j));
                    String ids = mapSub.get("id").toString();
                    String sortCodeS = mapSub.get("sortcode").toString();
                    String texts = mapSub.get("sortname").toString();
                    jsonObject = new JSONObject();
                    jsonObject.put("id", ids);
                    jsonObject.put("pId", id);
                    jsonObject.put("name", texts);
                    jsonObject.put("pName", id);
                    jsonObject.put("issort", "true");
                    jsonArray.add(jsonObject);

                    List cataListS = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("idnumber", idnumber).eq("sortcode", sortCodeS).ne("CATATITLE", "").isNotNull("CATATITLE").orderByAsc("sequence"));
                    for (int x = 0, lenCata = cataListS.size(); x < lenCata; x++) {
                        Map mapCata = (HashMap) cataListS.get(x);
                        String idCata = mapCata.get("id").toString();
                        String textc = mapCata.get("catatitle").toString();
                        jsonObject = new JSONObject();
                        jsonObject.put("id", idCata);
                        jsonObject.put("pId", ids);
                        jsonObject.put("name", textc);
                        jsonObject.put("pName", texts);
                        jsonObject.put("issort", "false");
                        jsonObject.put("cataClass", "sec");
                        jsonArray.add(jsonObject);
                    }
                }
                // 第一级分类下面的目录
                List cataListF = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("idnumber", idnumber).eq("sortcode", sortCodeF).ne("CATATITLE", "").isNotNull("CATATITLE").orderByAsc("sequence"));
                for (int x = 0, lenCata = cataListF.size(); x < lenCata; x++) {
                    Map mapCata = (HashMap) CommonUtil.convertToMap(cataListF.get(x));
                    String idCata = mapCata.get("id").toString();
                    String textc = mapCata.get("catatitle").toString();
                    jsonObject = new JSONObject();
                    jsonObject.put("id", idCata);
                    jsonObject.put("pId", id);
                    jsonObject.put("name", textc);
                    jsonObject.put("pName", text);
                    jsonObject.put("issort", "false");
                    jsonObject.put("cataClass", "firs");
                    jsonArray.add(jsonObject);
                }
            }
            json = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return json;
        }
    }

    /**
     * @param catalogue
     * @return
     * @description 增加或者更新目录信息
     * @author Fkw
     * @date 2019/10/4
     */
    @Override
    public String addCatalogue(Catalogue catalogue) {
        String sortCode = catalogue.getSortcode();
        String idnumber = catalogue.getIdnumber();
        String sequence = catalogue.getSequence();
        List cataList = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("idnumber", idnumber).eq("sortcode", sortCode).eq("sequence", sequence).orderByAsc("sequence"));
        if (cataList.isEmpty()) {
            catalogueMapper.insert(catalogue);
            return catalogue.getId();
        } else {
            Catalogue catalogue1 = (Catalogue) cataList.get(0);
            String id = catalogue1.getId();
            catalogue.setId(id);
            catalogueMapper.updateById(catalogue);
            return id;
        }
    }

    /**
     * @param idnumber
     * @param sqcdid   :
     * @return
     * @description 利用平台通过过滤查阅权限获取目录树
     * @author Fkw
     * @date 2019/10/12
     */
    @Override
    public String getTreeJsonLy(String idnumber, String sqcdid) {
        String json = "";
        try {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = null;
            String treeId = "";
            List sortList = sortMapper.selectList(new QueryWrapper<Sort>().select("id", "sortcode", "sortname", "sortfullname", "sequence", "parentid", "blankline").isNull("parentid").inSql("sortcode", "select sortcode from " + CommonUtil.getTableName(Catalogue.class) + " where id in (select erid from " + CommonUtil.getTableName(MediaSource.class) + " where STOREITEMID  in (select storeid from " + CommonUtil.getTableName(SqcdStore.class) + " where sqcdid ='" + sqcdid + "'))").orderByAsc("sequence"));
            for (int i = 0, len = sortList.size(); i < len; i++) {
                Map map = (HashMap) CommonUtil.convertToMap(sortList.get(i));
                String id = map.get("id").toString();
                String sortCodeF = map.get("sortcode").toString();
                String text = map.get("sortname").toString();
                jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("pId", "0");
                jsonObject.put("name", text);
                jsonObject.put("open", "true");
                jsonObject.put("isParent", "true");
                jsonObject.put("issort", "true");
                jsonObject.put("sortcode", sortCodeF);
                jsonArray.add(jsonObject);
                List listSub = sortMapper.selectList(new QueryWrapper<Sort>().select("id", "sortcode", "sortname", "sortfullname", "sequence", "parentid", "blankline").eq("parentid", id).inSql("sortcode", "select sortcode from " + CommonUtil.getTableName(Catalogue.class) + " where id in (select erid from " + CommonUtil.getTableName(MediaSource.class) + " where STOREITEMID  in (select storeid from " + CommonUtil.getTableName(SqcdStore.class) + " where sqcdid ='" + sqcdid + "'))").orderByAsc("sequence"));
                for (int j = 0, lenSub = listSub.size(); j < lenSub; j++) {
                    Map mapSub = (HashMap) CommonUtil.convertToMap(listSub.get(j));
                    String ids = mapSub.get("id").toString();
                    String sortCodeS = mapSub.get("sortcode").toString();
                    String texts = mapSub.get("sortname").toString();
                    jsonObject = new JSONObject();
                    jsonObject.put("id", ids);
                    jsonObject.put("pId", id);
                    jsonObject.put("name", texts);
                    jsonObject.put("pName", id);
                    jsonObject.put("issort", "true");
                    jsonArray.add(jsonObject);

                    List cataListS = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("idnumber", idnumber).eq("sortcode", sortCodeS).ne("CATATITLE", "").isNotNull("CATATITLE").inSql("id", "select erid from " + CommonUtil.getTableName(MediaSource.class) + " where STOREITEMID in ( select STOREID from " + CommonUtil.getTableName(SqcdStore.class) + " where sqcdid='" + sqcdid + "')").orderByAsc("sequence"));
                    for (int x = 0, lenCata = cataListS.size(); x < lenCata; x++) {
                        Map mapCata = (HashMap) cataListS.get(x);
                        String idCata = mapCata.get("id").toString();
                        String textc = mapCata.get("catatitle").toString();
                        jsonObject = new JSONObject();
                        jsonObject.put("id", idCata);
                        jsonObject.put("pId", ids);
                        jsonObject.put("name", textc);
                        jsonObject.put("pName", texts);
                        jsonObject.put("issort", "false");
                        jsonObject.put("cataClass", "sec");
                        jsonArray.add(jsonObject);
                    }
                }
                // 第一级分类下面的目录
                List cataListF = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("idnumber", idnumber).eq("sortcode", sortCodeF).ne("CATATITLE", "").isNotNull("CATATITLE").inSql("id", "select erid from " + CommonUtil.getTableName(MediaSource.class) + " where STOREITEMID in ( select STOREID from " + CommonUtil.getTableName(SqcdStore.class) + " where sqcdid='" + sqcdid + "')").orderByAsc("sequence"));
                for (int x = 0, lenCata = cataListF.size(); x < lenCata; x++) {
                    Map mapCata = (HashMap) CommonUtil.convertToMap(cataListF.get(x));
                    String idCata = mapCata.get("id").toString();
                    String textc = mapCata.get("catatitle").toString();
                    jsonObject = new JSONObject();
                    jsonObject.put("id", idCata);
                    jsonObject.put("pId", id);
                    jsonObject.put("name", textc);
                    jsonObject.put("pName", text);
                    jsonObject.put("issort", "false");
                    jsonObject.put("cataClass", "firs");
                    jsonArray.add(jsonObject);
                }
            }
            json = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return json;
        }
    }

    /**
     * @param catalogue
     * @return
     * @description 多人批量采集干部目录信息
     * @author Fkw
     * @date 2019/10/28
     */
    @Override
    public String addCatalogueMultiple(Catalogue catalogue) {
        String sortCode = catalogue.getSortcode();
        String idnumber = catalogue.getIdnumber();
        String cataid = "";
        List cataList = catalogueMapper.selectMaps(new QueryWrapper<Catalogue>().eq("idnumber", idnumber).eq("sortcode", sortCode).isNull("catatitle").orderByAsc("sequence"));
        if (cataList.isEmpty()) {
            List cataMax = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("idnumber", idnumber).eq("sortcode", sortCode).orderByAsc("sequence"));
            if (cataMax.isEmpty()) {
                catalogue.setSequence("1");
                cataid = catalogue.getId();
                catalogueMapper.insert(catalogue);
                return cataid;
            } else {
                Map mapCard = (HashMap) cataList.get(0);
                String sequence = mapCard.get("sequence").toString();
                String newSeq = String.valueOf(Integer.parseInt(sequence) + 1);
                catalogue.setSequence(newSeq);
                catalogueMapper.insert(catalogue);
                cataid = catalogue.getId();
                return cataid;
            }
        } else {
            Map mapCard = (HashMap) cataList.get(0);
            String id = mapCard.get("id").toString();
            String sequence = mapCard.get("sequence").toString();
            catalogue.setSequence(sequence);
            catalogue.setId(id);
            catalogueMapper.updateById(catalogue);
            return mapCard.get("id").toString();
        }
    }
}
