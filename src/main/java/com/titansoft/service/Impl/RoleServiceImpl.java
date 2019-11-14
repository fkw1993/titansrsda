package com.titansoft.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.titansoft.entity.Role;
import com.titansoft.entity.User;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.entity.system.Privilege;
import com.titansoft.entity.view.PrivilegeView;
import com.titansoft.mapper.RoleMapper;
import com.titansoft.model.*;
import com.titansoft.service.RoleService;
import com.titansoft.utils.database.TableName;
import com.titansoft.utils.util.CommonUtil;
import com.titansoft.utils.util.DateUtil;
import com.titansoft.utils.util.extjs.FormUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 角色管理
 *
 * @Author: Kevin
 * @Date: 2019/9/11 18:18
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;

    @Override
    public String roleTableColumns() {
        String columnsJson = "";
        // 暂时采用固定方式,待开发平台完成后,进行修改
        List<Column> columns = new ArrayList<Column>();
        Column column = null;
        column = new Column();
        column.setDataIndex("roleid");
        column.setText("roleid");
        column.setXtype("gridcolumnview");
        column.setHidden(true);
        column.setFlex(0);
        column.setWidth(100);
        columns.add(column);

        column = new Column();
        column.setDataIndex("code");
        column.setText("角色代码");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(150);
        columns.add(column);

        column = new Column();
        column.setDataIndex("name");
        column.setText("角色名");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(150);
        columns.add(column);

        column = new Column();
        column.setDataIndex("description");
        column.setText("描述");
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
     * @param role    :
     * @return
     * @description 角色表单
     * @author Fkw
     * @date 2019/9/20
     */
    @Override
    public String roleForm(String operate, Role role) {
        //传递回前端的JSON数据字符串
        //构造表单数据 start
        ArrayList<VO> list = new ArrayList<VO>();
        VO vo = null;
        vo = new VO();
        vo.put("fieldname", "角色代码");
        vo.put("fieldcode", "code");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "角色名称");
        vo.put("fieldcode", "name");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "角色描述");
        vo.put("fieldcode", "description");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("textrow", 3);
        vo.put("mustinput", "false");
        vo.put("iswhieline", "true");
        vo.put("controltype", "textarea");
        list.add(vo);
        //构造表单数据 end

        //追加hidden域  start
        XsdObject xsdObject;
        ArrayList<XsdObject> hiddens = new ArrayList<XsdObject>();
        xsdObject = new XsdObject();
        xsdObject.setFieldName("roleid");
        xsdObject.setDefaultvalue(role.getRoleid());
        hiddens.add(xsdObject);

        xsdObject = new XsdObject();
        xsdObject.setFieldName("productid");
        xsdObject.setDefaultvalue(role.getProductid());
        hiddens.add(xsdObject);

        xsdObject = new XsdObject();
        xsdObject.setFieldName("createtime");
        xsdObject.setDefaultvalue(role.getCreatetime());
        hiddens.add(xsdObject);

        xsdObject = new XsdObject();
        xsdObject.setFieldName("createuser");
        xsdObject.setDefaultvalue(role.getCreateuser());
        hiddens.add(xsdObject);

        xsdObject = new XsdObject();
        xsdObject.setFieldName("modifytime");
        xsdObject.setDefaultvalue(role.getModifytime());
        hiddens.add(xsdObject);

        xsdObject = new XsdObject();
        xsdObject.setFieldName("modifyuser");
        xsdObject.setDefaultvalue(role.getModifyuser());
        hiddens.add(xsdObject);
        //追加hidden域  end

        return FormUtil.NotButtonConvertJSON(list, role, hiddens, operate);
    }

    /**
     * @param role
     * @param logininfo :
     * @return
     * @description 增加角色
     * @author Fkw
     * @date 2019/9/20
     */
    @Override
    public String addRole(Role role, Logininfo logininfo) {
        role.setCreatetime(DateUtil.getTimeNow(new Date()));
        role.setCreateuser(logininfo.getUsername());
        //查看是否已经有了本条记录code,productid
        Role roledb = roleMapper.selectOne(new QueryWrapper<Role>().eq("code", role.getCode()));
        if (roledb == null) {
            if ("".equals(role.getRoleid()) || role.getRoleid() == null) {
                String roleid = CommonUtil.getGuid();
                role.setRoleid(roleid);
                roleMapper.insert(role);
            } else {
                roleMapper.insert(role);
            }
        } else {
            return "已存在相同的角色";
        }
        return "";
    }

    /**
     * @param role
     * @param logininfo :
     * @return
     * @description 修改角色
     * @author Fkw
     * @date 2019/9/20
     */
    @Override
    public String editRole(Role role, Logininfo logininfo) {
        role.setModifytime(DateUtil.getTimeNow(new Date()));
        role.setModifyuser(logininfo.getUsername());
        roleMapper.updateById(role);
        return "";
    }

    /**
     * @param roleid
     * @return
     * @description 删除角色
     * @author Fkw
     * @date 2019/9/20
     */
    @Override
    public void delRole(String roleid) {
        String[] id = roleid.split(",");
        if (id.length > 0) {
            for (int i = 0; i < id.length; i++) {
                delRoleData(id[i]);
            }
        }
    }

    /**
     * @param userid
     * @return
     * @description * 获取可选角色
     * @author Fkw
     * @date 2019/10/21
     */
    @Override
    public String getAllRole(String userid) {
        List list = roleMapper.selectList(new QueryWrapper<Role>().select("roleid", "name").notInSql("roleid", "select roleid from " + TableName.UserRoleTable + " where userid='" + userid + "'"));
        String json = JSONArray.toJSONString(list);
        return json;
    }

    /**
     * @param userid
     * @return
     * @description * 获取可选角色
     * @author Fkw
     * @date 2019/10/21
     */
    @Override
    public String getSelRole(String userid) {
        List list = roleMapper.selectList(new QueryWrapper<Role>().select("roleid", "name").inSql("roleid", "select roleid from " + TableName.UserRoleTable + " where userid='" + userid + "'"));
        String json = JSONArray.toJSONString(list);
        return json;
    }

    /**
     * @param roleids
     * @param userid  :
     * @return
     * @description 保存已选角色
     * @author Fkw
     * @date 2019/10/21
     */
    @Override
    public void saveSelRole(String roleids, String userid) {
        roleMapper.delteteFilterSql(TableName.UserRoleTable, " userid='" + userid + "'");
        String[] roleid = roleids.split(",");
        for (int i = 0; i < roleid.length; i++) {
            if (!"root".equals(roleid[i]) && !"".equals(roleid[i]))
                roleMapper.insertSql(TableName.UserRoleTable, "'" + userid + "','" + roleid[i] + "'");
        }
    }

    /**
     * @param params
     * @param logininfo :
     * @return
     * @description 获取功能树
     * @author Fkw
     * @date 2019/10/21
     */
    @Override
    public String getSetFunTree(Map<String, Object> params, Logininfo logininfo) {
        List list = null;
        int level = 1;
        String userCode = "sysdba";
        String productid = params.get("productid").toString();
        String roleid = params.get("roleid").toString();
        roleid=CommonUtil.getIDstr(roleid);
        String hiddenfilter = "(x.ishidden is null or x.ishidden='')";
        if (userCode.equals(logininfo.getUsername())) {
            hiddenfilter = "(x.ishidden is null or x.ishidden='') or x.ishidden = '1'";
        }
        list = privilegeViewMapper.getPowerByRoleId(roleid, productid, hiddenfilter, CommonUtil.getTableName(PrivilegeView.class), TableName.RolePrivilegeTable);

        Map<String, List> parentidMap = new HashMap<String, List>(16);
        String lastparentid = "";
        List listorgan = null;
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map map = (Map) it.next();
            map=CommonUtil.MapNullToEmpty(map);
            String parentid = map.get("parentid").toString();
            if (parentid == null || "".equals(parentid)) {
                parentid = "0";
            }
            if (!lastparentid.equals(parentid)) {
                if (listorgan != null) {
                    parentidMap.put(lastparentid, listorgan);
                }
                listorgan = new ArrayList();
                lastparentid = parentid;
            }
            listorgan.add(map);
        }
        if (listorgan != null && listorgan.size() > 0) {
            parentidMap.put(lastparentid, listorgan);
            //递归树
            list = getTreeNode(parentidMap, "0");
        }


        return JSONArray.toJSONString(list);
    }

    /**
     * 角色功能权限递归树
     *
     * @param parentidMap
     * @param parentid
     * @return
     */
    private List<Tree> getTreeNode(Map parentidMap, String parentid) {
        List<Tree> trees = null;
        List organList = (List) parentidMap.get(parentid);
        //当功能树数据集合不为空时,进行遍历,转换成tree对象
        if (organList != null && !organList.isEmpty()) {
            //中间变量
            CheckBoxTree tree = null;
            //结果树数组
            trees = new ArrayList<Tree>();
            for (int i = 0; i < organList.size(); ++i) {
                HashMap map = (HashMap) organList.get(i);
                tree = new CheckBoxTree();
                tree.setId(map.get("id") + "");
                tree.setText(map.get("text") + "");
                if (map.get("checked") != null && !"".equals(map.get("checked"))) {
                    tree.setChecked(true);
                } else {
                    tree.setChecked(false);
                }

                ////////////////递规子类 start
                if (parentidMap.containsKey(map.get("id") + "")) {
                    List<Tree> childrenTree = getTreeNode(parentidMap, map.get("id") + "");
                    if (!childrenTree.isEmpty()) {
                        tree.setChildren(childrenTree.toArray(new Tree[0]));
                    }
                } else {
                    tree.setLeaf(true);
                }
                ////////////////递规子类 end
                trees.add(tree);

            }
        }
        return trees;
    }

    /**
     * @param permcodes
     * @param roleid    :
     * @param productid :
     * @return
     * @description
     * @author Fkw
     * @date 2019/10/21
     */
    @Override
    public void setFun(String permcodes, String roleid, String productid) {
        userMapper.delUserData(TableName.RolePrivilegeTable, " roleid='" + roleid + "' and privilegeid in (select privilegeid from " + CommonUtil.getTableName(Privilege.class) + " where productid='" + productid + "')");
        String[] permcode = permcodes.split(",");
        for (int i = 0; i <= permcode.length - 1; i++) {
            if (!"root".equals(permcode[i]) && !"".equals(permcode[i])) {
                userMapper.insertUser(TableName.RolePrivilegeTable, "'" + roleid + "','" + permcode[i] + "'");
            }
        }
    }

    /**
     * @param organid
     * @param roleid  :
     * @return
     * @description 获取机构用户树
     * @author Fkw
     * @date 2019/10/25
     */
    @Override
    public List getOrganUserTree(String organid, String roleid) {
        List list = null;
        List ouserlist=userMapper.selectMaps(new QueryWrapper<User>().select("'' parentid","name text","userid id").inSql("userid","select userid from "+CommonUtil.getTableName(User.class)+" where organid='"+organid+"'"));
        if(ouserlist != null && !ouserlist.isEmpty()){
            Map<String, List> parentidMap = new HashMap<String, List>(16);
            Map<String, String> containMap = new HashMap<String, String>(16);
            String parentid = "";
            String lastparentid = "";
            List listorgan = null;
            for(Iterator it = ouserlist.iterator(); it.hasNext();)
            {
                Map map = (Map) it.next();
                parentid = map.get("parentid").toString();
                if(parentid==null||"".equals(parentid)){
                    parentid = "0";
                }
                if(!lastparentid.equals(parentid))
                {
                    if(listorgan!=null){
                        parentidMap.put(lastparentid, listorgan);
                    }

                    listorgan = new ArrayList();
                    lastparentid = parentid;
                }
                listorgan.add(map);
            }
            parentidMap.put(lastparentid, listorgan);
            List rolelist=(List)roleMapper.selectFilterSql("*",TableName.UserRoleTable,"roleid='"+roleid+"'");
            for(Iterator it = rolelist.iterator(); it.hasNext();)
            {
                Map map = (Map) it.next();
                containMap.put(map.get("USERID").toString(), "");
            }
            if(listorgan!=null&&listorgan.size()>0)
            {
                parentidMap.put(lastparentid, listorgan);
                list = getUserTreeNode(parentidMap,containMap,"0");
            }
        }
        return list;
    }

    /**
     * @param organid
     * @param userids :
     * @param roleid  :
     * @return
     * @description 保存设置的用户组
     * @author Fkw
     * @date 2019/10/25
     */
    @Override
    public void saveSetRole(String organid, String userids, String roleid) {
        roleMapper.delteteFilterSql(TableName.UserRoleTable,"roleid='"+roleid+"' and userid in (select userid from "+CommonUtil.getTableName(User.class)+" where  organid='"+organid+"')");
        String [] useridArray=userids.split(",");
        for(int i=0;i<useridArray.length;i++) {
            if("root".equalsIgnoreCase(useridArray[i])){
                continue;
            }
            roleMapper.insertSql(TableName.UserRoleTable,"'"+useridArray[i]+"','"+roleid+"'");
        }

    }

    /**
     * 角色用户树json转换
     * @param parentidMap
     * @param containMap
     * @param parentid
     * @return
     */
    private List<Tree> getUserTreeNode(Map parentidMap,Map containMap,String parentid){
        List<Tree> trees = null;
        List organList =  (List) parentidMap.get(parentid);
        //当功能树数据集合不为空时,进行遍历,转换成tree对象
        if(organList != null && !organList.isEmpty()){
            //中间变量
            CheckBoxTree tree = null;
            //结果树数组
            trees = new ArrayList<Tree>();
            for (int i=0; i < organList.size(); ++i) {
                HashMap map = (HashMap)organList.get(i);
                tree = new CheckBoxTree();
                tree.setId(map.get("id")+"");
                tree.setText(map.get("text")+"");
                if(containMap.containsKey(map.get("id")+""))
                {
                    tree.setChecked(true);
                }
                else
                {
                    tree.setChecked(false);
                }
                ////////////////递规子类 start
                if(parentidMap.containsKey(map.get("id")+""))
                {
                    List<Tree> childrenTree = getUserTreeNode(parentidMap,containMap,map.get("id")+"");
                    if(!childrenTree.isEmpty()){
                        tree.setChildren(childrenTree.toArray(new Tree[0]));
                    }
                }
                else{
                    tree.setLeaf(true);
                }
                ////////////////递规子类 end
                trees.add(tree);
            }
        }
        return trees;
    }

    /**
     * 通过parentidMap 迭代树
     *
     * @param parentidMap
     * @param parentid
     * @return
     */
    private String getFunTreeNodeJson(Map parentidMap, String parentid) {
        StringBuilder builder = new StringBuilder("[");
        List list = (List) parentidMap.get(parentid);
        for (int i = 0; i < list.size(); ++i) {
            HashMap map = (HashMap) list.get(i);
            String id = map.get("id") == null ? "" : map.get("id").toString().trim();
            String text = map.get("text") == null ? "" : map.get("text").toString().trim();
            String iconcls = "foler";

            if (builder.length() > 1)
                builder.append(",");
            builder.append("{\"id\":\"").append(id).append("\"").append(",\"text\":\"").append(text).append("\"")
                    .append(",\"cls\":\"").append(iconcls).append("\"");

            if (map.get("checked") != null && !"".equals(map.get("checked"))) {
                builder.append(",\"checked\":").append(true);
            } else {
                builder.append(",\"checked\":").append(false);
            }

            if (map.get("checked2") != null && !"".equals(map.get("checked2"))) {
                builder.append(",\"checked2\":").append(true);
            } else {
                builder.append(",\"checked2\":").append(false);
            }
            //////////////// 递规子类 start
            if (parentidMap.containsKey(id)) {
                builder.append(",\"children\":");
                builder.append(getFunTreeNodeJson(parentidMap, id));
            } else
                builder.append(",\"leaf\":true");
            //////////////// 递规子类 end
            builder.append("}");

        }
        builder.append("]");
        return builder.toString();
    }

    @Transactional
    public void delRoleData(String roleid) {
        String filter = " roleid='" + roleid + "'";
        roleMapper.delRole("t_s_rp", filter);
        roleMapper.delRole("t_s_ur", filter);
        roleMapper.delRole("t_s_role", filter);
    }
}
