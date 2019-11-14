package com.titansoft.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.titansoft.entity.Role;
import com.titansoft.entity.User;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.entity.system.Privilege;
import com.titansoft.entity.view.PrivilegeView;
import com.titansoft.mapper.RoleMapper;
import com.titansoft.mapper.UserMapper;
import com.titansoft.model.Column;
import com.titansoft.model.ColumnJson;
import com.titansoft.model.VO;
import com.titansoft.model.XsdObject;
import com.titansoft.service.UserService;
import com.titansoft.utils.database.SqlFilter;
import com.titansoft.utils.database.TableName;
import com.titansoft.utils.util.CommonUtil;
import com.titansoft.utils.util.DateTools;
import com.titansoft.utils.util.DateUtil;
import com.titansoft.utils.util.ReverseEncript;
import com.titansoft.utils.util.extjs.FormUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @Author: Kevin
 * @Date: 2019/9/11 17:16
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;

    /**
     * @return
     * @description 获取表头
     * @author Fkw
     * @date 2019/9/11
     */
    @Override
    public String userTableColumns() {
        String columnsJson = "";
        // 暂时采用固定方式,待开发平台完成后,进行修改
        List<Column> columns = new ArrayList<Column>();
        Column column = null;

        column = new Column();
        column.setDataIndex("userid");
        column.setText("用户id");
        column.setXtype("gridcolumnview");
        column.setHidden(true);
        column.setFlex(0);
        column.setWidth(100);
        columns.add(column);

        column = new Column();
        column.setDataIndex("username");
        column.setText("编号");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(150);
        columns.add(column);

        column = new Column();
        column.setDataIndex("realname");
        column.setText("姓名");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(180);
        columns.add(column);

        column = new Column();
        column.setDataIndex("usertype");
        column.setText("用户类型");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(150);
        columns.add(column);

        column = new Column();
        column.setDataIndex("description");
        column.setText("用户描述");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(260);
        columns.add(column);

        ColumnJson columnjson = new ColumnJson();
        columnjson.setColumns(columns);
        columnsJson = JSONObject.toJSONString(columnjson);

        return columnsJson;
    }

    /**
     * @param organid
     * @return
     * @description 通过机构id用户
     * @author Fkw
     * @date 2019/9/19
     */
    @Override
    public List getUserByOrganid(String organid) {
        List list = userMapper.selectList(new QueryWrapper<User>().eq("organid", organid));
        return list;
    }

    /**
     * @param operate
     * @param user    :
     * @return
     * @description 增加用户表单
     * @author Fkw
     * @date 2019/9/19
     */
    @Override
    public String userForm(String operate, User user) {
        ArrayList<VO> list = new ArrayList<VO>();

        VO vo = new VO();
        vo.put("fieldname", "登录账号");
        vo.put("fieldcode", "code");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "用户姓名");
        vo.put("fieldcode", "name");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "用户类型");
        vo.put("fieldcode", "usertype");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("iswhieline", "true");
        String tmp = "系统管理员-系统管理员|安全管理员-安全管理员|安全审计员-安全审计员|普通用户-普通用户|利用用户-利用用户";
        vo.put("controltype", "combox");
        vo.put("comboxdata", tmp);
        vo.put("mustinput", "true");
        vo.put("defaultvalue", "普通用户");
        vo.put("isinputhidden", "false");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "用户描述");
        vo.put("fieldcode", "description");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("textrow", 3);
        vo.put("iswhieline", "true");
        vo.put("mustinput", "false");
        vo.put("controltype", "textarea");
        list.add(vo);

        // 追加hidden域 start
        XsdObject xsdObject;
        ArrayList<XsdObject> hiddens = new ArrayList<XsdObject>();
        xsdObject = new XsdObject();
        xsdObject.setFieldName("userid");
        xsdObject.setDefaultvalue(user.getUserid());
        hiddens.add(xsdObject);

        xsdObject = new XsdObject();
        xsdObject.setFieldName("organid");
        xsdObject.setDefaultvalue(user.getOrganid());
        hiddens.add(xsdObject);

        xsdObject = new XsdObject();
        xsdObject.setFieldName("createtime");
        xsdObject.setDefaultvalue(user.getCreatetime());
        hiddens.add(xsdObject);

        xsdObject = new XsdObject();
        xsdObject.setFieldName("createuser");
        xsdObject.setDefaultvalue(user.getCreateuser());
        hiddens.add(xsdObject);

        return FormUtil.NotButtonConvertJSON(list, user, hiddens, operate);
    }

    /**
     * @param user
     * @param logininfo :
     * @return
     * @description 增加用戶
     * @author Fkw
     * @date 2019/9/19
     */
    @Override
    public String adduser(User user, Logininfo logininfo) {
        user.setCreateuser(logininfo.getUsername());
        user.setUserid(CommonUtil.getGuid());
        boolean isMatch = Pattern.matches("[\u4e00-\u9fa5_a-zA-Z0-9]{2,16}", user.getCode());
        if (!isMatch) {
            return "用户名格式错误";
        }
        user.setCreatetime(DateUtil.getTimeNow(new Date()));
        user.setCreateuser(logininfo.getUsername());
        List list = userMapper.selectList(new QueryWrapper<User>().select("name").eq("code", user.getCode()));
        if (list != null && list.size() > 0) {
            return "用戶已存在";
        }
        user.setPwd(ReverseEncript.getMD5("555"));
        user.setStatus("已激活");
        userMapper.insert(user);
        if ("系统管理员|安全管理员|安全审计员".indexOf(user.getUsertype()) >= 0) {
            list = roleMapper.selectList(new QueryWrapper<Role>().eq("name", user.getUsertype()));//this.basedao.queryhashmap("select * from T_S_ROLE where name='" + datapo.getUsertype() + "'");
            if (list != null && list.size() > 0) {
                Role role = (Role) list.get(0);
                userMapper.insertUser("t_s_ur", "'" + user.getUserid() + "','" + role.getRoleid() + "'");
            }
        }
        return "";
    }

    /**
     * @param user
     * @param logininfo :
     * @return
     * @description 修改用户
     * @author Fkw
     * @date 2019/9/19
     */
    @Override
    public String editUser(User user, Logininfo logininfo) {
        user.setModifyuser(logininfo.getUsername());
        userMapper.updateById(user);
        return "";
    }

    /**
     * @param userid
     * @return
     * @description 删除用户
     * @author Fkw
     * @date 2019/9/19
     */
    @Transactional
    @Override
    public void delUser(String userid) {
        String filter = SqlFilter.CheckSql(" userid in (" + CommonUtil.getIDstr(userid) + ")");
        userMapper.delUserData("t_s_ur", filter);
        userMapper.delUserData("t_s_ur", filter);
        userMapper.delUserData("t_s_ur", filter);
        userMapper.delete(new UpdateWrapper<User>().inSql("userid", CommonUtil.getIDstr(userid)));
    }

    /**
     * @param u
     * @param expiretime :过期时间
     * @return
     * @description 创建临时用户
     * @author Fkw
     * @date 2019/9/27
     */
    @Override
    public void createUser(User u, String expiretime) {
        //判断用户是否存在
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("code", u.getCode()));
        if (user != null) {
            user.setStatus("已激活");
            user.setExpirestime(expiretime);
            user.setUsertype("临时用户");
            userMapper.updateById(user);
        } else {
            u.setUserid(CommonUtil.getGuid());
            u.setCreatetime(DateTools.pdateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
            u.setPwd(ReverseEncript.getMD5("555"));
            u.setExpirestime(expiretime);
            u.setUsertype("临时用户");
            u.setStatus("已激活");
            userMapper.insert(u);
        }
    }

    /**
     * @param params
     * @return
     * @description 获取用户的功能权限树
     * @author Fkw
     * @date 2019/10/18
     */
    @Override
    public String getSetFunTree(Map<String, Object> params, Logininfo logininfo) {
        String treejson = "";
        String productid = params.get("productid").toString();
        String userid = params.get("userid").toString();
        try {
            if (productid != null) {
                try {
                    treejson = getExtTreeNodes(userid, logininfo, productid);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return treejson;
    }

    /**
     * @param permcodes
     * @param userid    : 用户id
     * @param productid : 产品id
     * @return
     * @description 保存功能权限
     * @author Fkw
     * @date 2019/10/19
     */
    @Override
    public void setFun(String permcodes, String userid, String productid) {
        userMapper.delUserData(TableName.UserPrivilegeTable," userid='" + userid + "' and privilegeid in (select privilegeid from "+CommonUtil.getTableName(Privilege.class)+" where productid='" + productid + "')");
        String []permcode=permcodes.split(",");
        for (int i = 0; i <= permcode.length - 1; i++) {
            if (!"root".equals(permcode[i]) && !"".equals(permcode[i])) {
                userMapper.insertUser(TableName.UserPrivilegeTable,"'" + userid + "','" + permcode[i] + "'");
            }
        }
    }

    /**
     * @param *          @param userid:
     * @param logininfo:
     * @param productid:
     * @return
     * @description 根据用户的权限加载功能树
     * @author Fkw
     * @date 2019/10/19
     */
    private String getExtTreeNodes(String userid, Logininfo logininfo, String productid) {
        String json = "[]";
        String hiddenfilter = "(x.ishidden is null or x.ishidden='')";
        if ("sysdba".equals(logininfo.getUsername())) {
            hiddenfilter = "(x.ishidden is null or x.ishidden=''or x.ishidden='1') ";
        }
        Map<String, String> roleFunMap = new HashMap<String, String>();
        List<Map<String, Object>> list = privilegeViewMapper.getPowerByUserId(userid, hiddenfilter, productid,CommonUtil.getTableName(PrivilegeView.class), TableName.UserPrivilegeTable);
        /**List<Map<String, Object>> rolefunlist = privilegeViewMapper.getPowerByRoleId(userid, productid, hiddenfilter,CommonUtil.getTableName(PrivilegeView.class),TableName.RolePrivilegeTable,CommonUtil.getTableName(Role.class), TableName.UserRoleTable);
        for (Map<String, Object> map : rolefunlist) {
            roleFunMap.put((String) map.get("id"), "");
        }**/
        Map<String, List> parentidMap = new HashMap<String, List>();
        String parentid = "";
        String lastparentid = "";
        List listorgan = null;
        int i = 0;
        for (Iterator it = list.iterator(); it.hasNext(); ) {

            Map map = CommonUtil.MapNullToEmpty((Map<String, Object>) it.next());
            parentid = map.get("parentid").toString();
            if (roleFunMap.get(map.get("id")) != null) {
                map.put("checked2", "true");
                i++;
            }
            if (parentid == null || "".equals(parentid))
                parentid = "0";
            if (!lastparentid.equals(parentid)) {
                if (listorgan != null)
                    parentidMap.put(lastparentid, listorgan);
                listorgan = new ArrayList();
                lastparentid = parentid;
            }
            listorgan.add(map);
        }
        if (listorgan != null && listorgan.size() > 0) {
            parentidMap.put(lastparentid, listorgan);
            json = getFunTreeNodeJson(parentidMap, "0");
        }
        return json;
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
}
