package com.titansoft.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.entity.system.Privilege;
import com.titansoft.entity.system.Product;
import com.titansoft.entity.view.PrivilegeView;
import com.titansoft.mapper.PrivilegeMapper;
import com.titansoft.mapper.PrivilegeViewMapper;
import com.titansoft.mapper.ProductMapper;
import com.titansoft.model.*;

import com.titansoft.service.PrivilegeService;
import com.titansoft.utils.config.PrivilegeConfig;
import com.titansoft.utils.database.SqlFilter;
import com.titansoft.utils.util.CommonUtil;
import com.titansoft.utils.util.DateUtil;
import com.titansoft.utils.util.StringformatZreoUtil;
import com.titansoft.utils.util.extjs.FormUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: Kevin
 * @Date: 2019/7/27 20:45
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {
    @Autowired
    PrivilegeMapper privilegeMapper;
    @Autowired
    PrivilegeViewMapper privilegeViewMapper;
    @Autowired
    ProductMapper productMapper;

    public static Map<String, List<Map<String, String>>> buttonsStrMap = new HashMap<String, List<Map<String, String>>>();

    /**
     * @param productid
     * @return
     * @description 初始化产品的所有按钮信息到内存
     * @author Fkw
     * @date 2019/7/30
     */
    @Override
    public void initButtonsMap(String productid) {
        // 将查询的按钮信息绑定到静态变量中
        buttonsStrMap = new HashMap<String, List<Map<String, String>>>();
        List listsub = privilegeMapper.getInitButtons(productid);
        if (listsub != null && !listsub.isEmpty()) {
            String code = "";
            String text = "";
            String privilegeid = "";
            String eventname = "";
            String iconcls = "";
            String groupname = "";
            String texts = "";
            String parentid = "";
            for (java.util.Iterator<HashMap> itsub = listsub.iterator(); itsub.hasNext(); ) {
                // 组装按钮
                HashMap hashmap = itsub.next();
                Map<String, String> buttonitem = new HashMap<String, String>();
                text = hashmap.get("TEXT") == null ? "" : hashmap.get("TEXT").toString().trim();
                parentid = hashmap.get("PARENTID") == null ? "" : hashmap.get("PARENTID").toString().trim();
                buttonitem.put("code", hashmap.get("CODE") == null ? "" : hashmap.get("code").toString().trim());
                buttonitem.put("text", text);
                buttonitem.put("privilegeid", hashmap.get("PRIVILEGEID") == null ? "" : hashmap.get("PRIVILEGEID").toString().trim());
                buttonitem.put("eventname", hashmap.get("EVENTNAME") == null ? "" : hashmap.get("EVENTNAME").toString().trim());
                buttonitem.put("iconcls", hashmap.get("ICONCLS") == null ? "" : hashmap.get("ICONCLS").toString().trim());
                buttonitem.put("groupname", hashmap.get("GROUPNAME") == null ? "" : hashmap.get("GROUPNAME").toString().trim());

                if (buttonsStrMap.get(parentid) == null) {
                    buttonsStrMap.put(parentid, new ArrayList<Map<String, String>>());
                }
                buttonsStrMap.get(parentid).add(buttonitem);

            }
        }
    }

    /**
     * @param modulecode
     * @param logininfo  :
     * @return
     * @description 根据modulcode获取按钮
     * @author Fkw
     * @date 2019/7/31
     */
    @Override
    public List<Map<String, String>> moduloldprivilege(String modulecode, Logininfo logininfo) {
        List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
        List<Privilege> listsub = PrivilegeConfig.privilegeListParentidMap
                .get(modulecode);

        if (listsub != null && !listsub.isEmpty()) {
            String text = "";
            String texts = "";
            for (Privilege priv : listsub) {

                if (!logininfo.getFunmap().containsKey(priv.getPrivilegeid())) {
                    continue;
                }
                Map<String, String> buttonitem = new HashMap<String, String>();
                text = priv.getName() == null ? "" : priv.getName().toString().trim();
                buttonitem.put("code", priv.getCode() == null ? "" : priv.getCode().toString().trim());
                buttonitem.put("text", text);
                buttonitem.put("privilegeid",
                        priv.getPrivilegeid() == null ? "" : priv.getPrivilegeid().toString().trim());
                buttonitem.put("eventname", priv.getEventname() == null ? "" : priv.getEventname().toString().trim());
                buttonitem.put("iconcls", priv.getIconcls() == null ? "" : priv.getIconcls().toString().trim());
                buttonitem.put("groupname", priv.getGroupname() == null ? "" : priv.getGroupname().toString().trim());
                maps.add(buttonitem);
            }

        }

        return maps;
    }

    /**
     * @param privilege
     * @return
     * @description 增加
     * @author Fkw
     * @date 2019/9/7
     */
    @Transactional
    @Override
    public void addPrivilege(Privilege privilege, Logininfo logininfo) {
        privilege.setPrivilegeid(CommonUtil.getGuid());
        privilege.setCreatetime(DateUtil.getTimeNow(new Date()));
        privilege.setCreateuser(logininfo.getUsername());
        // 当为非第一级功能时
        if (privilege.getParentid() != null && !"".equals(privilege.getParentid().trim()) && !"0".equals(privilege.getParentid().trim()) && !privilege.getParentid().equals(privilege.getProductid())) {
            // List list = this.basedao.queryhashmap("select distinct * from v_Privilege where privilegeid = '" + privilege.getParentid() + "'");
            List list = privilegeViewMapper.selectList(new QueryWrapper<PrivilegeView>().eq("privilegeid", privilege.getParentid()));
            PrivilegeView privilegeView = (PrivilegeView) list.get(0);
            privilege.setRootid(privilegeView.getRootid());
            privilege.setLevelnum(Integer.parseInt(privilegeView.getLevelnum() + 1) + "");
            list = privilegeViewMapper.selectList(new QueryWrapper<PrivilegeView>().eq("parentid", privilege.getParentid()));
            privilege.setLevelord(StringformatZreoUtil.getDhFieldValue(String.valueOf((list != null && !list.isEmpty()) ? 1 + list.size() : 1), 3));
        } else {
            // 当为第一级功能时
            privilege.setRootid(CommonUtil.getGuid());
            privilege.setParentid(null);
            privilege.setLevelnum(1 + "");
            // List list = this.basedao.queryhashmap("select 1 from v_Privilege where parentid is null and productid = '" + privilege.getProductid() + "'");
            List list = privilegeViewMapper.selectList(new QueryWrapper<PrivilegeView>().isNull("parentid").eq("productid", privilege.getProductid()));
            privilege.setLevelord(StringformatZreoUtil.getDhFieldValue(String.valueOf((list != null && !list.isEmpty()) ? 1 + list.size() : 1), 3));
        }

        if (privilege.getUri() != null && !"".equals(privilege.getUri()) && !(privilege.getUri().indexOf("modulecode=") > 0))
            privilege.setUri(privilege.getUri() + ((privilege.getUri().indexOf("?") > 0) ? "&" : "?") + "modulecode=" + privilege.getPrivilegeid());
        if (privilege.getCheckurl() != null && !"".equals(privilege.getCheckurl()) && !(privilege.getCheckurl().indexOf("modulecode=") > 0)) {
            String[] checkurl = privilege.getCheckurl().split(",");
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < checkurl.length; i++) {
                String s = checkurl[i] + ((checkurl[i].indexOf("?") > 0) ? "&" : "?") + "modulecode=" + privilege.getPrivilegeid();
                sb.append(s + ",");
            }
            sb.substring(0, sb.length());
            privilege.setCheckurl(sb.toString());
        }

        privilege.setStatus("1");
        privilege.setPrivtype("1");
        privilegeMapper.insert(privilege);
        privilegeMapper.insertPrivilegeex(privilege.getPrivilegeid(), privilege.getFlag(), privilege.getEventname(), privilege.getIconcls(), privilege.getGroupname());
        privilegeMapper.insertPrivilevel(privilege.getPrivilegeid(), privilege.getRootid(), privilege.getParentid(), privilege.getLevelnum(), privilege.getLevelord());
    }

    /**
     * @param privilege
     * @param logininfo :
     * @return
     * @description 删除
     * @author Fkw
     * @date 2019/9/7
     */
    @Transactional
    @Override
    public void delPrivilege(Privilege privilege, Logininfo logininfo) {
        privilegeMapper.deleteSql("T_S_UP", "privilegeid in (" + CommonUtil.getIDstr(privilege.getPrivilegeid()) + ")");
        privilegeMapper.deleteSql("t_s_rp", "privilegeid in (" + CommonUtil.getIDstr(privilege.getPrivilegeid()) + ")");
        privilegeMapper.deleteSql("t_s_privilegeex", "privilegeid in (" + CommonUtil.getIDstr(privilege.getPrivilegeid()) + ")");
        privilegeMapper.deleteSql("t_s_privlevel", "privilegeid in (" + CommonUtil.getIDstr(privilege.getPrivilegeid()) + ")");
        privilegeMapper.deleteSql("t_s_privilege", "privilegeid in (" + CommonUtil.getIDstr(privilege.getPrivilegeid()) + ")");
    }

    /**
     * @param privilegeid
     * @return
     * @description 获取功能对象
     * @author Fkw
     * @date 2019/9/9
     */
    @Override
    public Privilege getPrivilege(String privilegeid) {
        Privilege resultPrivilege = new Privilege();
        resultPrivilege = privilegeMapper.selectOne(new QueryWrapper<Privilege>().eq("privilegeid", privilegeid));
        if (resultPrivilege != null) {
            List list = privilegeMapper.selectSql("t_s_Privilegeex", SqlFilter.CheckSql("  privilegeid = '" + resultPrivilege.getPrivilegeid() + "'"));
            Map privilegesMap = (Map) list.get(0);
            resultPrivilege.setFlag(privilegesMap.get("FLAG") == null ? "" : privilegesMap.get("FLAG") + "");
            resultPrivilege.setEventname(privilegesMap.get("EVENTNAME") == null ? "" : privilegesMap.get("EVENTNAME") + "");
            resultPrivilege.setIconcls(privilegesMap.get("ICONCLS") == null ? "" : privilegesMap.get("ICONCLS") + "");
            resultPrivilege.setGroupname(privilegesMap.get("GROUPNAME") == null ? "" : privilegesMap.get("GROUPNAME") + "");

            list = privilegeMapper.selectSql(" t_s_privlevel", SqlFilter.CheckSql("  privilegeid = '" + resultPrivilege.getPrivilegeid() + "'"));
            privilegesMap = (Map) list.get(0);
            resultPrivilege.setRootid(privilegesMap.get("ROOTID") == null ? "" : privilegesMap.get("ROOTID") + "");
            resultPrivilege.setParentid(privilegesMap.get("PARENTID") == null ? "" : privilegesMap.get("PARENTID") + "");
            resultPrivilege.setLevelnum(privilegesMap.get("LEVELNUM") == null ? "0" : privilegesMap.get("LEVELNUM") + "");
            resultPrivilege.setLevelord(privilegesMap.get("LEVELORD") == null ? "0" : privilegesMap.get("LEVELORD") + "");
        } else {
            resultPrivilege = null;
        }
        return resultPrivilege;
    }

    /**
     * @param privilege
     * @param logininfo :
     * @return
     * @description 修改功能
     * @author Fkw
     * @date 2019/9/9
     */
    @Transactional
    @Override
    public void editPrivilege(Privilege privilege, Logininfo logininfo) {
        privilege.setModifytime(DateUtil.getTimeNow(new Date()));
        privilege.setModifyuser(logininfo.getUsername());
        if (privilege.getUri() != null && !privilege.getUri().contains("modulecode") && !"".equals(privilege.getUri())) {
            if (!privilege.getUri().contains("?")) {
                privilege.setUri(privilege.getUri() + "?modulecode=" + privilege.getPrivilegeid());
            } else {
                privilege.setUri(privilege.getUri() + "&modulecode=" + privilege.getPrivilegeid());
            }
        }
        privilegeMapper.updateById(privilege);
        privilegeMapper.updateSql("t_s_privilegeex", "flag='" + privilege.getFullname() + "',eventname='" + privilege.getEventname() + "',iconcls='" + privilege.getIconcls() + "',groupname='" + privilege.getGroupname() + "'", "privilegeid='" + privilege.getPrivilegeid() + "'");
    }

    /**
     * @param privilege
     * @return
     * @description 获取功能的排序
     * @author Fkw
     * @date 2019/9/10
     */
    @Override
    public String getPrivilegeSort(Privilege privilege) {
        String itemsJson = "[]";
        try {
            // 功能树数据集合
            List itemsList = null;
            try {
                // 根据条件查找返回数据
                itemsList = getItemSort(privilege);
                // 当功能树数据集合不为空时,进行遍历,转换成tree对象
                if (itemsList != null && !itemsList.isEmpty()) {
                    itemsJson = JSONObject.toJSONString(itemsList);
                }
                itemsList = null;
            } catch (Exception e) {

            }
            itemsList = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemsJson;
    }

    /**
     * @param privilegesids
     * @return
     * @description 保存排序数据
     * @author Fkw
     * @date 2019/9/10
     */
    @Override
    public void savePrivilegeSort(String privilegesids, Logininfo logininfo) {
        String[] privilegeidArray = privilegesids.split(",");
        int l = privilegeidArray.length;
        for (int i = 0; i < l; i++) {
            Privilege privilege = new Privilege();
            privilege.setPrivilegeid(privilegeidArray[i]);
            privilege.setLevelord(StringformatZreoUtil.getDhFieldValue(String.valueOf((i + 1)), 3));
            privilegeMapper.updateSql("t_s_privlevel", "levelord='" + privilege.getLevelord() + "'", " privilegeid='" + privilege.getPrivilegeid() + "'");
        }
    }

    /**
     * @param privilege
     * @param logininfo :
     * @return
     * @description 根据ID获取功能下的子功能
     * @author Fkw
     * @date 2019/9/11
     */
    @Override
    public String modulprivilege(Privilege privilege, Logininfo logininfo) {
        //传递回前端的JSON数据字符串
        String tbarJson = "";
        HashMap<String, ArrayList<String>> groupList = new HashMap<String, ArrayList<String>>();
        List<Map> buttonlist = new ArrayList<Map>();
        StringBuilder buttonJson = new StringBuilder();
        try {
            //功能树数据集合
            List<Map<String, String>> privilegeList = null;
            try {
                //根据条件查找返回数据
                privilegeList = moduloldprivilege(privilege, logininfo);
            } catch (Exception e) {

            }
            //当功能树数据集合不为空时,进行遍历,转换成tree对象
            if (privilegeList != null && !privilegeList.isEmpty()) {
                for (int i = 0; i < privilegeList.size(); i++) {
                    Map<String, String> m = privilegeList.get(i);
                    StringBuilder builder = new StringBuilder();
                    builder.append("{\"text\":\"");
                    builder.append(m.get("text"));
                    builder.append("\"").append(",\"id\":\"").append(m.get("privilegeid")).append("\"");
                    builder.append(",\"name\":\"").append(m.get("code")).append("\"");
                    if (m.get("iconcls") != null && !"".equals(m.get("iconcls"))) {
                        builder.append(",\"iconCls\":\"").append(m.get("iconcls")).append("\"");
                    }
                    if (m.get("groupname") != null && !"".equals(m.get("groupname"))) {

                        builder.append(",\"handler\":function(){").append(m.get("eventname")).append("();");
                        builder.append("}}");
                    } else {
                        //builder.append(",\"handler\":\"").append(m.get("eventname")).append("\"");
                        //builder.append(",\"handler\":\"").append(m.get("eventname")).append("('"+m.get("privilegeid")+"')").append("\"");
                        builder.append(",\"handler\":function(){").append(m.get("eventname")).append("('" + m.get("privilegeid") + "');");
                        builder.append("}}");
                        //	builder.append("}");
                    }
                    if (m.get("groupname") != null && !"".equals(m.get("groupname"))) {

                        if (!groupList.containsKey(m.get("groupname"))) {
                            groupList.put(m.get("groupname"), new ArrayList<String>());
                            Map buttonmap = new HashMap();
                            buttonmap.put("groupname", m.get("groupname"));
                            buttonmap.put("type", "buttongroup");
                            buttonlist.add(buttonmap);
                        }
                        groupList.get(m.get("groupname")).add(builder.toString());
                    } else {


                        Map buttonmap = new HashMap();
                        buttonmap.put("type", "button");
                        buttonmap.put("button", builder);
                        buttonlist.add(buttonmap);
                    }
                }

                for (Map buttonmap : buttonlist) {
                    if ("button".equals(buttonmap.get("type"))) {
                        if (buttonJson.length() > 0)
                            buttonJson.append(",\"-\",");
                        buttonJson.append(buttonmap.get("button"));
                    } else {
                        if (buttonJson.length() > 0) {
                            buttonJson.append(",\"-\",");
                        }
                        buttonJson.append("{\"xtype\":\"changesplitbutton\",\"iconCls\":\"").append("icon-execute").append("\", \"text\": \"").append(buttonmap.get("groupname")).append("\", menu:[");
                        int i = 0;
                        for (String bitem : groupList.get(buttonmap.get("groupname"))) {
                            i++;
                            buttonJson.append(bitem);
                            if (i < groupList.get(buttonmap.get("groupname")).size()) {
                                buttonJson.append(",\"-\",");
                            }
                        }
                        buttonJson.append("]}");
                    }
                }


            }
            privilegeList = null;
        } catch (Exception e) {

            e.printStackTrace();
        }
        //return Result.setResult(errorCode, tbarJson);"[" + buttonJson.toString() + "]";
        return "[" + buttonJson.toString() + "]";
    }

    /**
     * @return
     * @description 获取系统各个模块名
     * @author Fkw
     * @date 2019/9/11
     */
    @Override
    public String getProductsCombox() {
        List<Product> comboList = productMapper.selectList(null);
        List<Combobox> comboboxs = new ArrayList<Combobox>();
        if (comboList != null && !comboList.isEmpty()) {
            Iterator it = comboList.iterator();
            Combobox combobox = null;
            Product product = null;
            while (it.hasNext()) {
                product = (Product) it.next();
                combobox = new Combobox();
                combobox.setId(product.getProductid());
                combobox.setUrl(product.getUri());
                combobox.setName(product.getName());
                comboboxs.add(combobox);
            }
            return JSONObject.toJSONString(comboboxs);
        } else {
            return "";
        }
    }

    /**
     * @return
     * @description 获取表头
     * @author Fkw
     * @date 2019/9/11
     */
    @Override
    public String privilegeTableColumns() {
        String columnsJson = "";
        //暂时采用固定方式,待开发平台完成后,进行修改
        List<Column> columns = new ArrayList<Column>();
        Column column = null;

        column = new Column();
        column.setDataIndex("privilegeid");
        column.setText("功能ID");
        column.setXtype("gridcolumnview");
        column.setHidden(true);
        column.setFlex(0);
        column.setWidth(100);
        columns.add(column);

        column = new Column();
        column.setDataIndex("code");
        column.setText("功能代码");
        column.setXtype("gridcolumnview");
        column.setHidden(true);
        column.setFlex(0);
        column.setWidth(100);
        columns.add(column);

        column = new Column();
        column.setDataIndex("text");
        column.setText("功能名称");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(200);
        columns.add(column);

        column = new Column();
        column.setDataIndex("iconcls");
        column.setText("功能图标");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(160);
        columns.add(column);

        column = new Column();
        column.setDataIndex("uri");
        column.setText("功能地址");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(250);
        columns.add(column);

        column = new Column();
        column.setDataIndex("description");
        column.setText("产品描述");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(320);
        columns.add(column);

        ColumnJson columnjson = new ColumnJson();
        columnjson.setColumns(columns);
        columnsJson = JSONObject.toJSONString(columnjson);
        return columnsJson;
    }

    /**
     * @param privilege
     * @return
     * @description
     * @author Fkw
     * @date 2019/9/11
     */
    @Override
    public String privilegeTree(Privilege privilege) {
        String treeJson = "";
        //功能树数据集合
        List privilegeList = null;
        List subPrivilegeList = null;
        //根据条件查找返回数据
        privilegeList = privilegeMapper.getPrivilegeByParentid(" parentid " + (privilege.getParentid() == null ? "is null" : ("= '" + privilege.getParentid() + "'")) + " and productid='" + privilege.getProductid() + "' order by levelord");
        //当功能树数据集合不为空时,进行遍历,转换成tree对象
        if (privilegeList != null && !privilegeList.isEmpty()) {
            //获取迭代器
            Iterator it = privilegeList.iterator();
            //中间变量
            Tree tree = null;
            Map map = null;
            //结果树数组
            List<Tree> trees = new ArrayList<Tree>();
            while (it.hasNext()) {
                map = (Map) it.next();
                tree = new Tree();
                tree.setId(map.get("PRIVILEGEID") + "");
                tree.setText(map.get("TEXT") + "");
                subPrivilegeList = privilegeMapper.getPrivilegeByParentid(" parentid " + (map.get("PRIVILEGEID") == null ? " is null" : ("= '" + map.get("PRIVILEGEID") + "'")) + " and productid='" + map.get("PRODUCTID") + "' order by levelord");
                if (subPrivilegeList != null && !subPrivilegeList.isEmpty()) {
                    tree.setChildren(null);
                } else {
                    tree.setChildren(Tree.isChild);
                    tree.setLeaf(true);
                }
                trees.add(tree);
            }
            treeJson = JSONObject.toJSONString(trees);
            map = null;
            tree = null;
            it = null;
        }
        privilegeList = null;
        return treeJson;
    }

    /**
     * @param operate
     * @param privilege :
     * @return
     * @description 表单
     * @author Fkw
     * @date 2019/9/11
     */
    @Override
    public String privilegeForm(String operate, Privilege privilege) {
        ArrayList<VO> list = new ArrayList<VO>();
        VO vo = null;
        //功能名称
        vo = new VO();
        vo.put("fieldname", "功能名称");
        vo.put("fieldcode", "name");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 50);
        vo.put("iswhieline", "false");
        vo.put("display", "true");
        list.add(vo);
        //功能图标
        vo = new VO();
        vo.put("fieldname", "功能图标");
        vo.put("fieldcode", "iconcls");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 100);
        vo.put("display", "true");
        vo.put("iswhieline", "false");
        vo.put("mustinput", "false");
        list.add(vo);
        //按扭组别
        vo = new VO();
        vo.put("fieldname", "按扭组别");
        vo.put("fieldcode", "groupname");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 100);
        vo.put("display", "true");
        vo.put("iswhieline", "true");
        vo.put("mustinput", "false");
        list.add(vo);
        //按扭组别
        vo = new VO();
        vo.put("fieldname", "事件名称");
        vo.put("fieldcode", "eventname");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 100);
        vo.put("display", "true");
        vo.put("iswhieline", "true");
        vo.put("mustinput", "false");
        list.add(vo);
        //功能地址
        vo = new VO();
        vo.put("fieldname", "功能地址");
        vo.put("fieldcode", "uri");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("mustinput", "false");
        vo.put("iswhieline", "true");
        list.add(vo);
        //引用系统
        vo = new VO();
        vo.put("fieldname", "引用系统");
        vo.put("fieldcode", "recommend");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("mustinput", "false");
        vo.put("iswhieline", "true");
        String tmp = "";
        for (String key : PrivilegeConfig.productMap.keySet()) {
            tmp += "|" + PrivilegeConfig.productMap.get(key).getName() + "-" + key;
        }
        vo.put("controltype", "combox");
        vo.put("comboxdata", tmp.substring(1));
        vo.put("editable", true);
        list.add(vo);

        //监控地址
        vo = new VO();
        vo.put("fieldname", "监控地址");
        vo.put("fieldcode", "checkurl");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 800);
        vo.put("display", "true");
        vo.put("textrow", 3);
        vo.put("iswhieline", "true");
        vo.put("mustinput", "false");
        vo.put("controltype", "textarea");
        list.add(vo);

        //功能说明
        vo = new VO();
        vo.put("fieldname", "功能说明");
        vo.put("fieldcode", "description");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("textrow", 3);
        vo.put("iswhieline", "true");
        vo.put("mustinput", "false");
        vo.put("controltype", "textarea");
        list.add(vo);

        //	  追加hidden域  start
        XsdObject xsdObject;
        ArrayList<XsdObject> hiddens = new ArrayList<XsdObject>();
        xsdObject = new XsdObject();
        xsdObject.setFieldName("privilegeid");
        xsdObject.setDefaultvalue(privilege.getPrivilegeid());
        hiddens.add(xsdObject);

        xsdObject = new XsdObject();
        xsdObject.setFieldName("parentid");
        xsdObject.setDefaultvalue(privilege.getParentid());
        hiddens.add(xsdObject);

        xsdObject = new XsdObject();
        xsdObject.setFieldName("productid");
        xsdObject.setDefaultvalue(privilege.getProductid());
        hiddens.add(xsdObject);

        xsdObject = new XsdObject();
        xsdObject.setFieldName("code");
        xsdObject.setDefaultvalue(privilege.getCode());
        hiddens.add(xsdObject);
        //追加hidden域  end
        return FormUtil.NotButtonConvertJSON(list, privilege, hiddens, operate);
    }

    public List<Map<String, String>> moduloldprivilege(Privilege privilege, Logininfo logininfo) throws Exception {
        List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
        List<Privilege> listsub = PrivilegeConfig.privilegeListParentidMap.get(privilege.getParentid());

        if (listsub != null && !listsub.isEmpty()) {
            String text = "";
            String texts = "";
            for (Privilege priv : listsub) {

                if (!logininfo.getFunmap().containsKey(priv.getPrivilegeid())) {
                    continue;
                }
                Map<String, String> buttonitem = new HashMap<String, String>();
                text = priv.getName() == null ? "" : priv.getName().toString().trim();
                texts = text;
                if (texts != null) {
                    text = texts;
                }
                buttonitem.put("code", priv.getCode() == null ? "" : priv.getCode().toString().trim());
                buttonitem.put("text", text);
                buttonitem.put("privilegeid", priv.getPrivilegeid() == null ? "" : priv.getPrivilegeid().toString().trim());
                buttonitem.put("eventname", priv.getEventname() == null ? "" : priv.getEventname().toString().trim());
                buttonitem.put("iconcls", priv.getIconcls() == null ? "" : priv.getIconcls().toString().trim());
                buttonitem.put("groupname", priv.getGroupname() == null ? "" : priv.getGroupname().toString().trim());
                maps.add(buttonitem);
            }

        }

        return maps;
    }

    /**
     * 获取排序数据
     *
     * @param privilege 功能管理对象
     * @return
     * @throws Exception
     */
    public List getItemSort(Privilege privilege) throws Exception {
        // 查询结果集
        List list = null;
        if (privilege.getParentid() == null) {
            list = (List) privilegeViewMapper.selectList(new QueryWrapper<PrivilegeView>().select("distinct *").isNull("parentid").eq("productid", privilege.getProductid()).orderByAsc("levelord"));// ("select distinct * from v_Privilege where parentid " + (privilege.getParentid() == null ? "is null" : ("= '" + privilege.getParentid() + "'")) + " and productid='" + privilege.getProductid() + "' order by levelord");
        } else {
            list = (List) privilegeViewMapper.selectList(new QueryWrapper<PrivilegeView>().select("distinct *").eq("parentid", privilege.getParentid()).eq("productid", privilege.getProductid()).orderByAsc("levelord"));
        }
        // 当数据集合不为空时,进行遍历,转换成tree对象
        if (list != null && !list.isEmpty()) {
            // 获取迭代器
            Iterator it = list.iterator();
            // 中间变量
            Listbox listbox = null;
            PrivilegeView privilegeView = null;
            // 功能树数组
            List<Listbox> listboxs = new ArrayList<Listbox>();
            while (it.hasNext()) {
                privilegeView = (PrivilegeView) it.next();
                listbox = new Listbox();
                listbox.setId(privilegeView.getPrivilegeid() + "");
                listbox.setText(privilegeView.getText() + "");
                listboxs.add(listbox);
            }
            privilegeView = null;
            listbox = null;
            it = null;
            list = listboxs;
        }
        return list;
    }
}
