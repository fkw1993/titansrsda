package com.titansoft.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.titansoft.entity.Catalogue;
import com.titansoft.entity.Po;
import com.titansoft.entity.UserStatus;
import com.titansoft.entity.cadre.Cadre;
import com.titansoft.entity.media.MediaSource;
import com.titansoft.entity.media.Store;
import com.titansoft.entity.system.Sort;
import com.titansoft.model.UnitExtTree;
import com.titansoft.model.enums.CadreBasicEnum;
import com.titansoft.service.CadreService;
import com.titansoft.utils.util.AESUtils;
import com.titansoft.utils.util.CommonUtil;
import com.titansoft.utils.util.DateTools;
import com.titansoft.utils.util.ZipOption;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/7/31 15:13
 */
@Service
public class CadreServiceImpl extends BaseServiceImpl implements CadreService {
    private static final Logger log = LoggerFactory.getLogger(CadreServiceImpl.class);

    /**
     * @param treeid
     * @return
     * @description 获取树结构
     * @author Fkw
     * @date 2019/7/31
     */
    @Override
    public String tree(String treeid, String roleid) {
        String result = "[]";
        List<UnitExtTree> treelist = new ArrayList<UnitExtTree>();
        try {
            List list = null;
            UnitExtTree node = null;
            boolean isRoot = false;
            // 状态值
            if ("0".equals(treeid)) {
                isRoot = true;
            } else {
                List<UserStatus> userStatusList = userStatusMapper.selectList(new QueryWrapper<UserStatus>().orderByAsc("sequence"));
                Boolean bool = false;
                for (int i = 0; i < userStatusList.size(); i++) {
                    UserStatus userStatus = (UserStatus) userStatusList.get(i);
                    if (treeid.equals(userStatus.getId())) {
                        bool = true;
                    }
                }
                if (bool) {
                    String userid = CommonUtil.getIDstr(roleid);
                    list = CommonUtil.transformUpperCaseList(unitViewMapper.getUnitByUserid(userid));
                } else {
                    String[] p = treeid.split("-");
                    int index = p.length;
                    String userid = CommonUtil.getIDstr(roleid);
                    list = CommonUtil.transformUpperCaseList(unitViewMapper.getUnitPartByUserid(userid, treeid.split("-")[index - 1]));
                }
            }

            if (isRoot) {
                List<UserStatus> userStatusList = userStatusMapper.selectList(new QueryWrapper<UserStatus>().orderByAsc("sequence"));
                for (int i = 0; i < userStatusList.size(); i++) {
                    UserStatus userStatus = (UserStatus) userStatusList.get(i);
                    node = new UnitExtTree();
                    node.setId(userStatus.getId());
                    node.setText(userStatus.getName());
                    node.setUrl(userStatus.getFullname());
                    node.setIconCls("icon-units");
                    node.setLeaf(false);
                    treelist.add(node);

                }
            } else {
                if (treeid.indexOf("-") < 0) {
                    for (Iterator it = list.iterator(); it.hasNext(); ) {
                        Map map = (Map) it.next();
                        node = new UnitExtTree();
                        node.setId(treeid + "-" + (String) map.get("id"));
                        node.setText((String) map.get("text"));
                        node.setUrl((String) map.get("unitfullname"));
                        if (map.get("isleaf") == null || "".equals(map.get("isleaf")) || "true".equals(map.get("isleaf"))) {
                            node.setIconCls("icon-units");
                            node.setLeaf(true);
                        } else {
                            node.setIconCls("icon-units");
                            node.setLeaf(false);
                        }
                        treelist.add(node);
                    }
                } else {
                    for (Iterator it = list.iterator(); it.hasNext(); ) {
                        Map map = (Map) it.next();
                        node = new UnitExtTree();
                        node.setId(treeid + "-" + (String) map.get("id"));
                        node.setText((String) map.get("text"));
                        node.setUrl((String) map.get("unitfullname"));
                        if (map.get("isleaf") == null || "".equals(map.get("isleaf")) || "true".equals(map.get("isleaf"))) {
                            node.setIconCls("icon-units");
                            node.setLeaf(true);
                        } else {
                            node.setIconCls("icon-units");
                            node.setLeaf(false);
                        }
                        treelist.add(node);
                    }
                }

            }
            //result = Constant.listtojson(objListUnit);
        } catch (Exception e) {
            log.error(e.getMessage());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(baos));
            String exception = baos.toString();
            System.out.println("baos:" + exception);
        } finally {
            result = JSONArray.toJSONString(treelist);
        }
        return result;
    }

    /**
     * @param idnumber
     * @return
     * @description 获取干部的基本信息
     * @author Fkw
     * @date 2019/8/10
     */
    @Override
    public Po getCadreBasicDetail(String idnumber) {
        List<Po> poList = cadreMapper.getCadreBasic(CadreBasicEnum.getEnumByAtype("basicinfo").getTablename(), idnumber);
        //如果为空则在干部表获取基本信息
        Po po = new Po();
        if (poList.size() == 0) {
            List<Cadre> cadreList = cadreMapper.selectList(new QueryWrapper<Cadre>().eq("idnumber", idnumber));
            if (cadreList.size() > 0) {
                Cadre cadre = (Cadre) cadreList.get(0);
                po.setIdnumber(cadre.getIdnumber());
                po.setWg01(cadre.getName());
                po.setWg02(cadre.getSex());
                po.setWg03(cadre.getBirthday());
                po.setWg05(cadre.getNation());
                po.setWg06(cadre.getOrigin());
                po.setWg09(cadre.getPolitical());
                po.setWg11(cadre.getWorktime());
            }
        } else {
            po = poList.get(0);
        }
        return po;
    }

    /**
     * @param idnumber
     * @param :        要获取的个数，不足拿空对象
     * @return
     * @description 获取干部的额外基本信息
     * @author Fkw
     * @date 2019/8/10
     */
    @Override
    public List<Po> getCadreBasicExtendDetail(String idnumber, String type) {
        int num = CadreBasicEnum.getEnumByAtype(type).getNum();
        List<Po> list = cadreMapper.getCadreBasic(CadreBasicEnum.getEnumByAtype(type).getTablename(), idnumber);
        // 查询的数据与页面行数不符遍历时会报错，所以用空对象代替
        if (list.size() < num) {
            int arr = num - list.size();
            for (int i = 1; i <= arr; i++) {
                Po p = new Po();
                list.add(p);
            }
        }
        return list;
    }

    /**
     * @param idnumbers
     * @return
     * @description
     * @author Fkw
     * @date 2019/9/25
     */
    @Override
    public String export(String idnumbers) {
        try {
            String[] idnumber = idnumbers.split(",");

            String location = "";
            String tempPath = "";
            String xmlPath = "";
            String zipPath = "";
            List erlist = storeMapper.selectList(new QueryWrapper<Store>().eq("datatype", "EEP"));//this.basedao.queryhashmap("select * from t_er_store where datatype='EEP'");
            for (int i = 0; i < idnumber.length; i++) {
                Cadre cadre = cadreMapper.selectOne(new QueryWrapper<Cadre>().eq("idnumber", idnumber[i]));//this.basedao.queryhashmap("select * from t_bus_cadre where idnumber='"+idnumber[i]+"'");
                if (cadre == null) {
                    break;
                }
                //创建临时数据包文件夹
                if (erlist.size() > 0) {
                    Store store = (Store) erlist.get(0);
                    location = (String) store.getLocation();
                    zipPath = location + "/转递导出数据包临时/" + cadre.getName() + cadre.getIdnumber();
                    xmlPath = location + "/转递导出数据包临时/" + cadre.getName() + cadre.getIdnumber() + "/" + cadre.getName() + cadre.getIdnumber();
                    tempPath = location + "/转递导出数据包临时/" + cadre.getName() + cadre.getIdnumber() + "/" + cadre.getName() + cadre.getIdnumber() + "/原始图像数据";
                    File f = new File(tempPath);
                    if (!f.exists())
                        f.mkdirs();
                    f = new File(tempPath.replace("原始图像数据", "优化图像数据"));
                    if (!f.exists())
                        f.mkdirs();
                } else {
                    return "";
                }
                // 1、创建document对象
                Document document = DocumentHelper.createDocument();
                // 2、创建根节点rss
                Element root = document.addElement("数字档案");
                // 3、向rss节点添加version属性
                root.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
                root.addAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
                // 4、生成子节点及子节点内容
                Element personMsg = root.addElement("人员基本信息");
                Element name = personMsg.addElement("姓名");
                name.setText(cadre.getName().toString());
                Element sex = personMsg.addElement("性别");
                sex.setText(cadre.getSex().toString());
                Element nation = personMsg.addElement("名族");
                nation.setText(cadre.getNation().toString());
                Element birthday = personMsg.addElement("出生日期");
                birthday.setText(cadre.getBirthday().toString());
                Element idcode = personMsg.addElement("公民身份号码");
                idcode.setText(cadre.getIdnumber().toString());
                //生成目录信息
                Element catalog = root.addElement("目录信息");
                List cataloguelist = null;//this.basedao.queryhashmap("select * from (select *,case CHARINDEX ('-', sortcode) when 0 then sortcode else SUBSTRING(SORTCODE,0,CHARINDEX ('-', sortcode)) end as sortcode1 from T_S_CATALOGUE where IDNUMBER = '"+idnumber[i]+"' and CATATITLE is not null) t_catalogue order by CAST(sortcode1 as int),SORTCODE,CAST(SEQUENCE as int)");
                List sortList = sortMapper.selectList(new QueryWrapper<Sort>().isNull("parentid").orderByAsc("sequence"));
                for (int s = 0; s < sortList.size(); s++) {
                    String sortCode = "";
                    Sort sort = (Sort) sortList.get(s);
                    //判断是否含有子分类
                    List<Sort> childSortList = sortMapper.selectList(new QueryWrapper<Sort>().eq("parentid", sort.getId()).orderByAsc("sequence"));
                    if (childSortList.size() > 0) {
                        for (int z = 0; z < childSortList.size(); z++) {
                            Sort childSort = childSortList.get(z);
                            sortCode = childSort.getSortcode();
                            cataloguelist = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("idnumber", idnumber[i]).eq("sortcode",sortCode).isNotNull("catatitle").orderByAsc("sequence"));
                            addItemCatalogue(cataloguelist, catalog);
                        }
                    } else {
                        sortCode = sort.getSortcode();
                        cataloguelist = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("idnumber", idnumber[i]).eq("sortcode",sortCode).isNotNull("catatitle").orderByAsc("sequence"));
                        addItemCatalogue(cataloguelist, catalog);
                    }
                }
      /*          for(int k=0;k<cataloguelist.size();k++) {
                    Map m=(Map) cataloguelist.get(k);
                    Element personCatalog = catalog.addElement("档案目录条目");
                    Element leihao = personCatalog.addElement("类号");
                    String sortcode=m.get("sortcode").toString();
                    if(sortcode.contains("-")) {
                        sortcode=sortcode.substring(0, sortcode.indexOf("-"));
                    }
                    leihao.setText(CommonUtil.ToCH(Integer.parseInt(sortcode)));
                    Element xuhao = personCatalog.addElement("序号");
                    xuhao.setText(m.get("sequence").toString());

                    Element clmc = personCatalog.addElement("材料名称");
                    clmc.setText(m.get("catatitle").toString());

                    Element time = personCatalog.addElement("材料形成时间");
                    time.setText(m.get("catayear").toString()+m.get("catamonth").toString()+m.get("cataday").toString());

                    List srclist=null;//this.basedao.queryhashmap("select * from t_er_srclist where erid='"+m.get("id")+"' order by CREATETIME");
                    Element pageNum = personCatalog.addElement("页数");
                    pageNum.setText(srclist.size()+"");

                    Element beizhu = personCatalog.addElement("备注");

                    for(int j=0;j<srclist.size();j++) {
                        Map p=(Map) srclist.get(j);
                        Element file=personCatalog.addElement("原始图像数据");
                        file.setText(p.get("filename")+"."+p.get("fileformat").toString().toLowerCase());
                    }

                }*/
                //拷贝电子文件
                File[] imgfiles = new File(location + "/" + cadre.getUnitname() + "/" + cadre.getName() + cadre.getIdnumber() + "/原始图像数据").listFiles();
                if (imgfiles != null) {
                    for (int m = 0; m < imgfiles.length; m++) {
                        if (imgfiles[m].isFile()) {
                            File sourceFile = imgfiles[m];
                            AESUtils.decryptedFile(sourceFile.getAbsolutePath(), tempPath + "/" + sourceFile.getName());
                        }
                    }
                }
                File[] optimgfiles = new File(location + "/" + cadre.getUnitname() + "/" + cadre.getName() + cadre.getIdnumber() + "/优化图像数据").listFiles();
                if (optimgfiles != null) {
                    for (int n = 0; n < optimgfiles.length; n++) {
                        if (optimgfiles[n].isFile()) {
                            File sourceFile = imgfiles[n];
                            AESUtils.decryptedFile(sourceFile.getAbsolutePath(), tempPath.replace("原始图像数据", "优化图像数据") + "/" + sourceFile.getName());
                        }
                    }
                }
                // 5、设置生成xml的格式
                OutputFormat format = OutputFormat.createPrettyPrint();
                // 设置编码格式
                format.setEncoding("UTF-8");


                //创建xml文件
                File file = new File(xmlPath + "/" + cadre.getName() + cadre.getIdnumber() + ".xml");
                XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
                // 设置是否转义，默认使用转义字符
                writer.setEscapeText(false);
                writer.write(document);
                writer.close();
                ZipOption.expZip(zipPath, zipPath+".zip");
                ZipOption.del(zipPath);

/*
                //记录系统日志
                Date dt=new Date();
                String dotime= DateTools.pdateToString(dt, "yyyy-MM-dd HH:mm:ss");
                Logdata logdata = new Logdata(Constant.getGuid(), logininfo.getUsername(), logininfo.getRealname(),
                        logininfo.getUnitname(), logininfo.getUnitid(), logininfo.getIpaddress(), dotime,
                        "干部'"+cadreMap.get("name").toString()+"'、身份证号："+cadreMap.get("idnumber").toString()+"进行了导出转递数据包操作",
                        "导出数据包", "正常","档案转递管理-导出数据包");
                this.basedao.addPo(Logdata.getTablenameofpo(),logdata);*/


                return zipPath + ".zip";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public void addItemCatalogue(List cataloguelist, Element catalog) {
        for (int k = 0; k < cataloguelist.size(); k++) {
            Map m = CommonUtil.convertToMap(cataloguelist.get(k));
            Element personCatalog = catalog.addElement("档案目录条目");
            Element leihao = personCatalog.addElement("类号");
            String sortcode = m.get("sortcode").toString();
            if (sortcode.contains("-")) {
                sortcode = sortcode.substring(0, sortcode.indexOf("-"));
            }
            leihao.setText(CommonUtil.ToCH(Integer.parseInt(sortcode)));
            Element xuhao = personCatalog.addElement("序号");
            xuhao.setText(m.get("sequence").toString());

            Element clmc = personCatalog.addElement("材料名称");
            clmc.setText(m.get("catatitle").toString());

            Element time = personCatalog.addElement("材料形成时间");
            time.setText(m.get("catayear").toString() + m.get("catamonth").toString() + m.get("cataday").toString());

            List srclist = mediaSourceMapper.selectList(new QueryWrapper<MediaSource>().eq("erid", m.get("id")).orderByAsc("CREATETIME"));
            Element pageNum = personCatalog.addElement("页数");
            pageNum.setText(srclist.size() + "");

            Element beizhu = personCatalog.addElement("备注");
            for (int j = 0; j < srclist.size(); j++) {
                Map p = CommonUtil.convertToMap(srclist.get(j));
                Element file = personCatalog.addElement("原始图像数据");
                file.setText(p.get("filename") + "." + p.get("fileformat").toString().toLowerCase());
            }

        }
    }
}
