package com.titansoft.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.titansoft.entity.*;
import com.titansoft.entity.log.DataLog;
import com.titansoft.entity.log.LoginLog;
import com.titansoft.entity.log.SecurityLog;
import com.titansoft.entity.media.Store;
import com.titansoft.entity.sqcd.SqcdReason;
import com.titansoft.entity.system.Config;
import com.titansoft.entity.system.Privilege;
import com.titansoft.entity.system.Sort;
import com.titansoft.entity.view.PrivilegeView;
import com.titansoft.entity.view.UnitView;
import com.titansoft.entity.view.UserView;
import com.titansoft.mapper.*;
import com.titansoft.model.Datapermission;
import com.titansoft.model.TableData;
import com.titansoft.service.*;
import com.titansoft.utils.database.SqlFilter;
import com.titansoft.utils.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统安全维护
 *
 * @Author: Kevin
 * @Date: 2019/8/24 21:24
 */
@Controller
@RequestMapping("/person/security")
public class SystemSecurityController extends BaseController {

    //桌面
    @GetMapping(value = "{home}/home")
    public ModelAndView home(@PathVariable("home") String home) {
        return new ModelAndView("/person/desktop/" + home + "/home");
    }

    //桌面的图表数据
    @RequestMapping(value = "/logdata", method = RequestMethod.POST)
    public void logData(@RequestParam Map<String, String> params) {
        String tablename = params.get("tablename");
        Page page = null;
        if ("t_s_errorlog".equals(tablename)) {
            page = (Page) errorLogMapper.selectPage(new Page(Long.valueOf(params.get("page").toString()), Long.valueOf(params.get("limit").toString())), null);
        }
        if ("t_s_log".equals(tablename)) {
            page = (Page) loginLogMapper.selectPage(new Page(Long.valueOf(params.get("page").toString()), Long.valueOf(params.get("limit").toString())), null);
        }
        if ("t_s_securitylog".equals(tablename)) {
            page = (Page) securityLogMapper.selectPage(new Page(Long.valueOf(params.get("page").toString()), Long.valueOf(params.get("limit").toString())), null);
        }
        TableData tableData = new TableData();
        tableData.setTotalCount(page.getTotal());
        tableData.setList(page.getRecords());
        super.toAjax(JSONObject.toJSONString(tableData).toString());
    }

    //异常操作
    @RequestMapping(value = "/countErrorLog", method = RequestMethod.POST)
    public void countErrorLog(@RequestParam(required = false) String year,
                              @RequestParam(required = false) String filerStr) {
        String json = logService.countErrorLog(year, filerStr);
        if (json == null) {
            json = "{\"success\":false}";
        }
        super.toAjax(json);
    }

    //登录情况
    @RequestMapping(value = "/countLoginState", method = RequestMethod.POST)
    public void countLoginState(@RequestParam(required = false) String year,
                                @RequestParam(required = false) String filerStr,
                                @RequestParam(required = false) String filerStr1) {
        String json = logService.countLoginState(year, filerStr, filerStr1);
        if (json == null) {
            json = "{\"success\":false}";
        }
        super.toAjax(json);
    }

    //电子文件容量饼图
    @RequestMapping(value = "/storePie", method = RequestMethod.POST)
    public void storePie() {
        int size = mediaService.storePie();
        String json = "";
        json = "{\"size\":" + size + "}";
        super.toAjax(json);
    }

    //系统维护模块各个页面的跳转
    @GetMapping(value = "/{type}/main")
    public ModelAndView toMain(@PathVariable("type") String type) {
        return new ModelAndView("person/security/" + type + "/main");
    }

    // 根据ID获取功能下的子功能
    @RequestMapping(value = "/modulprivilege", method = RequestMethod.GET)
    public void modulprivilege(Privilege privilege) {
        String s = privilegeService.modulprivilege(privilege, logininfo);
        super.toAjax(s);
    }

/***************************************************************系统功能管理Start********************************************************************/
    /**
     * @param
     * @return
     * @description 系统功能管理模块名
     * @author Fkw
     * @date 2019/8/31
     */
    @RequestMapping(value = "/privilege/productsCombox", method = RequestMethod.GET)
    public void getProductsCombox() {
        super.toAjax(privilegeService.getProductsCombox());
    }

    /**
     * @param
     * @return
     * @description 系统功能管理表头
     * @author Fkw
     * @date 2019/8/31
     */
    @RequestMapping(value = "/privilege/tableColumns", method = RequestMethod.POST)
    public void privilegeTableColumns() {
        super.toAjax(privilegeService.privilegeTableColumns());
    }

    //加载系统管理树
    @RequestMapping(value = "/privilege/tree", method = RequestMethod.POST)
    public void privilegeTree(Privilege privilege) {
        super.toAjax(privilegeService.privilegeTree(privilege));
    }

    //加载系统管理数据
    @RequestMapping(value = "/privilege/tableData", method = RequestMethod.POST)
    public void privilegeGrid(Privilege privilege) throws Exception {
        //过滤条件
        if (privilege.getProductid() != null && (privilege.getProductid().equals(privilege.getParentid()))) {
            privilege.setParentid(null);
        }
        this.basefilter = " parentid " + (privilege.getParentid() == null ? "is null" : ("= '" + privilege.getParentid() + "'")) + " and productid='" + privilege.getProductid() + "'";
        String order = " order by " + sortfield + " " + dir;
        Page page = (Page) privilegeViewMapper.selectPage(new Page(Long.valueOf(super.page), Long.valueOf(super.limit)), (Wrapper<PrivilegeView>) new QueryWrapper().apply(SqlFilter.CheckSql(super.getAllFilter() + order)));
        List maps = new ArrayList<Map>();
        for (Object privilegeView : page.getRecords()) {
            maps.add(privilegeView);
        }
        TableData tableData = new TableData();
        tableData.setTotalCount(page.getTotal());
        tableData.setList(maps);
        super.toAjax(JSONObject.toJSONString(tableData));
    }

    //查看功能
    @RequestMapping(value = "/privilege/tolookprivilege", method = RequestMethod.POST)
    public void tolookPrivilege(Privilege privilege) throws Throwable {
        Privilege db_privilege = privilegeService.getPrivilege(privilege.getPrivilegeid());
        super.toAjax(privilegeService.privilegeForm("look", db_privilege));
    }

    //增加功能页面
    @RequestMapping(value = "/privilege/toaddprivilege", method = RequestMethod.POST)
    public void toAddPrivilege(Privilege privilege) throws Throwable {
        super.toAjax(privilegeService.privilegeForm("add", privilege));
    }

    //增加功能
    @RequestMapping(value = "/privilege/addprivilege", method = RequestMethod.POST)
    public void addPrivilege(@RequestParam Map<String, Object> params) {
        try {
            params = decodeMap(params);
            Privilege privilege = BeanUtils.mapToBean(params, Privilege.class);
            privilegeService.addPrivilege(privilege, logininfo);
            super.toAjaxSuccess();
        } catch (Exception e) {
            super.toAjaxError("服务器错误！");
        }
    }

    //修改页面
    @RequestMapping(value = "/privilege/toeditprivilege", method = RequestMethod.POST)
    public void toEditPrivilege(Privilege privilege) throws Throwable {
        Privilege db_privilege = privilegeService.getPrivilege(privilege.getPrivilegeid());
        super.toAjax(privilegeService.privilegeForm("edit", db_privilege));
    }

    //修改功能
    @RequestMapping(value = "/privilege/editprivilege", method = RequestMethod.POST)
    public void editPrivilege(@RequestParam Map<String, Object> params) throws Throwable {
        params = decodeMap(params);
        Privilege privilege = BeanUtils.mapToBean(params, Privilege.class);
        privilegeService.editPrivilege(privilege, logininfo);
        super.toAjaxSuccess();
    }

    //删除
    @RequestMapping(value = "/privilege/delprivilege", method = RequestMethod.POST)
    public void delPrivilege(Privilege privilege) {
        try {
            List list = privilegeViewMapper.selectList(new QueryWrapper<PrivilegeView>().in("parentid", CommonUtil.getIDstr(privilege.getPrivilegeid())));
            if (list.size() == 0) {
                privilegeService.delPrivilege(privilege, logininfo);
            }
            super.toAjaxSuccess();
        } catch (Exception e) {
            super.toAjaxError("服务器错误！");
        }
    }

    //调序前，获取当前的排序
    @RequestMapping(value = "/privilege/getPrivilegeSort", method = RequestMethod.POST)
    public void getPrivilegeSort(Privilege privilege) throws Throwable {
        if (privilege.getProductid() != null && (privilege.getProductid().equals(privilege.getParentid()))) {
            privilege.setParentid(null);
        }
        String json = privilegeService.getPrivilegeSort(privilege);
        super.toAjax(json);
    }

    //保存排序数据
    @RequestMapping(value = "/privilege/savePrivilegeSort", method = RequestMethod.POST)
    public void savePrivilegeSort(@RequestParam Map<String, Object> params) throws Throwable {
        String privilegeids = (String) params.get("items") == null ? "" : (String) params.get("items");
        privilegeService.savePrivilegeSort(privilegeids, logininfo);
        super.toAjaxSuccess();
    }

/***************************************************************系统功能管理END********************************************************************/

    /***************************************************************单位管理start********************************************************************/
    @Autowired
    UnitService unitService;
    @Autowired
    UnitViewMapper unitViewMapper;

    //加载单位树
    @RequestMapping(value = "/unit/tree", method = RequestMethod.POST)
    public void unitTree(Privilege privilege) {
        String json = JSONObject.toJSONString(unitService.unitTree(treeid));
        super.toAjax(json);
    }

    //加载单位数据
    @RequestMapping(value = "/unit/grid", method = RequestMethod.POST)
    public void unitGrid() {
        try {
            if (treeid == null || "0".equals(treeid)) {
                basefilter = "parentid is null";
            } else {
                basefilter = "parentid='" + treeid + "'";
            }
            Map m = super.grid();
            //排序语句
            String sortsql = " order by " + m.get("sortfield") + " " + m.get("dir");
            //获取分页数据
            Page page = (Page) unitViewMapper.selectPage(new Page(Long.valueOf(super.page), Long.valueOf(super.limit)), (Wrapper<UnitView>) new QueryWrapper().apply(SqlFilter.CheckSql(m.get("filter").toString() + sortsql)));
            String json = super.pageToJson(page);
            result = "{basefilter:\"" + this.basefilter + "\"," + json.substring(1);
            super.toAjax(result);
        } catch (Exception er) {
            log.info(er.getMessage());
        }
    }

    //获取表头
    @RequestMapping(value = "/unit/unitTableColumns", method = RequestMethod.POST)
    public void unitTableColumns() throws IOException {
        super.toAjax(unitService.unitTableColumns());
    }

    //获取增加表单
    @RequestMapping(value = "/unit/toaddunit", method = RequestMethod.POST)
    public void toAddUnit() throws IOException {
        super.toAjax(unitService.unitForm("add", new Unit()));
    }

    //增加功能
    @RequestMapping(value = "/unit/addunit", method = RequestMethod.POST)
    public void addUnit(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        Unit unit = BeanUtils.mapToBean(params, Unit.class);
        unitService.addUnit(unit, logininfo);
        super.toAjaxSuccess();
    }

    //获取修改表单
    @RequestMapping(value = "/unit/toeditunit", method = RequestMethod.POST)
    public void toEditUnit(Unit unit) {
        unit = unitMapper.selectOne(new QueryWrapper<Unit>().eq("unitid", unit.getUnitid()));
        super.toAjax(unitService.unitForm("edit", unit));
    }

    //获取修改表单
    @RequestMapping(value = "/unit/editunit", method = RequestMethod.POST)
    public void editUnit(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        Unit unit = BeanUtils.mapToBean(params, Unit.class);
        String msg = unitService.editUnit(unit);
        if (!"".equals(msg)) {
            super.toAjaxError(msg);
        } else {
            super.toAjaxSuccess();
        }
    }

    //删除单位
    @RequestMapping(value = "/unit/delunit", method = RequestMethod.POST)
    public void delUnit(Unit unit) {
        String msg = unitService.delUnit(unit.getUnitid());
        super.toAjax("{\"success\":true,msg:'" + msg + "'}");
    }

    //排序
    @RequestMapping(value = "/unit/sort", method = RequestMethod.POST)
    public void sortUnit(@RequestParam Map<String, Object> params) {
        String parentid = params.get("parentid").toString();
        String json = unitService.getSort(parentid);
        super.toAjax("{\"success\":true,list:" + json + "}");
    }

    @RequestMapping(value = "/unit/savesort", method = RequestMethod.POST)
    public void saveSortUnit(@RequestParam Map<String, Object> params) {
        String unitid = params.get("unitid").toString();
        String msg = unitService.saveSort(unitid);
        super.toAjaxSuccess();
    }
/***************************************************************单位管理end********************************************************************/

    /***************************************************************用户组织start********************************************************************/
    @Autowired
    OrganService organService;

    //加载用户组织树
    @RequestMapping(value = "/organ/tree", method = RequestMethod.POST)
    public void organTree(Organ organ) {
        super.toAjax(organService.organTree(organ.getOrganid()));
    }

    //获取表头
    @RequestMapping(value = "/organ/organTableColumns", method = RequestMethod.POST)
    public void organTableColumns(Organ organ) {
        super.toAjax(organService.organTableColumns());
    }

    //获取数据
    @RequestMapping(value = "/organ/organTableData", method = RequestMethod.POST)
    public void organTableData(Organ organ) {
        if (organ.getOrganid() == null || "root".equals(organ.getOrganid())) {
            this.basefilter = " parentid ='' or parentid is null";
        } else {
            this.basefilter = " parentid = '" + organ.getOrganid() + "'";
        }
        String order = " order by " + sortfield + " " + dir;
        Page page = (Page) organMapper.selectPage(new Page(Long.valueOf(super.page), Long.valueOf(super.limit)), (Wrapper<Organ>) new QueryWrapper().apply(SqlFilter.CheckSql(super.getAllFilter() + order)));
        List maps = new ArrayList<Map>();
        for (Object organView : page.getRecords()) {
            maps.add(organView);
        }
        TableData tableData = new TableData();
        tableData.setTotalCount(page.getTotal());
        tableData.setList(maps);
        super.toAjax(JSONObject.toJSONString(tableData));
    }

    //获取增加表单
    @RequestMapping(value = "/organ/toaddorgan", method = RequestMethod.POST)
    public void toAddOrgan() {
        super.toAjax(organService.organForm("add", new Organ()));
    }

    //增加功能
    @RequestMapping(value = "/organ/addorgan", method = RequestMethod.POST)
    public void addOrgan(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        Organ organ = BeanUtils.mapToBean(params, Organ.class);
        organService.addOrgan(organ, logininfo);
        super.toAjaxSuccess();
    }

    //获取查看表单
    @RequestMapping(value = "/organ/lookorgan", method = RequestMethod.POST)
    public void toLookOrgan(Organ organ) {
        organ = organMapper.selectOne(new QueryWrapper<Organ>().eq("organid", organ.getOrganid()));
        super.toAjax(organService.organForm("look", organ));
    }

    //获取修改表单
    @RequestMapping(value = "/organ/toeditorgan", method = RequestMethod.POST)
    public void toEditOrgan(Organ organ) {
        organ = organMapper.selectOne(new QueryWrapper<Organ>().eq("organid", organ.getOrganid()));
        super.toAjax(organService.organForm("edit", organ));
    }

    //保存修改
    @RequestMapping(value = "/organ/editorgan", method = RequestMethod.POST)
    public void editOrgan(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        Organ organ = BeanUtils.mapToBean(params, Organ.class);
        String msg = organService.editOrgan(organ, logininfo);
        if (!"".equals(msg)) {
            super.toAjaxError(msg);
        } else {
            super.toAjaxSuccess();
        }
    }

    //删除
    @RequestMapping(value = "/organ/delorgan", method = RequestMethod.POST)
    public void delOrgan(Organ organ) {
        //是否含有子机构
        List organList = organService.checkSubOrgan(organ.getOrganid());
        String errormsg = "";
        if (organList.size() > 0) {
            errormsg = "该机构下存在子机构";
        }
        //是否含有用户
        List userList = userService.getUserByOrganid(organ.getOrganid());
        if (userList.size() > 0) {
            errormsg = "该机构下存在用户";
        }
        if ("".equals(errormsg)) {
            organService.delOrgan(organ.getOrganid());
            super.toAjaxSuccess();
        } else {
            super.toAjaxError(errormsg);
        }
    }

    //排序页面
    @RequestMapping(value = "/organ/sort", method = RequestMethod.POST)
    public void sortOrgan(@RequestParam Map<String, Object> params) {
        String parentid = params.get("organid").toString();
        String json = organService.getSort(parentid);
        super.toAjax(json);
    }

    //保存排序
    @RequestMapping(value = "/organ/savesort", method = RequestMethod.POST)
    public void saveSortOrgan(@RequestParam Map<String, Object> params) {
        String organid = (String) params.get("items") == null ? "" : (String) params.get("items");
        organService.saveSort(organid);
        super.toAjaxSuccess();
    }
/***************************************************************用户组织end********************************************************************/

    /***************************************************************用户信息Start********************************************************************/
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;
    @Autowired
    UserViewMapper userViewMapper;


    //获取表头
    @RequestMapping(value = "/user/userTableColumns", method = RequestMethod.POST)
    public void userTableColumns() {
        super.toAjax(userService.userTableColumns());
    }

    //获取数据
    @RequestMapping(value = "/user/userTableData", method = RequestMethod.POST)
    public void userTableData(Organ organ) {
        if ("root".equals(organ.getOrganid())) {
            this.basefilter = " organid is null ";
        } else {
            this.basefilter = " organid = '" + organ.getOrganid() + "'";
        }
        String order = " order by " + sortfield + " " + dir;
        Page page = (Page) userViewMapper.selectPage(new Page(Long.valueOf(super.page), Long.valueOf(super.limit)), (Wrapper<UserView>) new QueryWrapper().apply(SqlFilter.CheckSql(super.getAllFilter() + order)));
        List maps = new ArrayList<Map>();
        for (Object o : page.getRecords()) {
            maps.add(o);
        }
        TableData tableData = new TableData();
        tableData.setTotalCount(page.getTotal());
        tableData.setList(maps);
        super.toAjax(JSONObject.toJSONString(tableData));
    }

    //获取增加表单
    @RequestMapping(value = "/user/toadduser", method = RequestMethod.POST)
    public void toAdduser(User user) {
        super.toAjax(userService.userForm("add", user));
    }

    //增加功能
    @RequestMapping(value = "/user/adduser", method = RequestMethod.POST)
    public void adduser(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        User user = BeanUtils.mapToBean(params, User.class);
        String msg = userService.adduser(user, logininfo);
        if ("".equals(msg)) {
            super.toAjaxSuccess();
        } else {
            super.toAjaxError(msg);
        }
    }

    //获取修改表单
    @RequestMapping(value = "/user/toedituser", method = RequestMethod.POST)
    public void toEdituser(User user) {
        user = userMapper.selectOne(new QueryWrapper<User>().eq("userid", user.getUserid()));
        super.toAjax(userService.userForm("edit", user));
    }

    //保存修改
    @RequestMapping(value = "/user/edituser", method = RequestMethod.POST)
    public void edituser(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        User user = BeanUtils.mapToBean(params, User.class);
        String msg = userService.editUser(user, logininfo);
        if (!"".equals(msg)) {
            super.toAjaxError(msg);
        } else {
            super.toAjaxSuccess();
        }
    }

    //删除
    @RequestMapping(value = "/user/deluser", method = RequestMethod.POST)
    public void deluser(User user) {
        userService.delUser(user.getUserid());
    }

    //获取用户的功能树
    @RequestMapping(value = "/user/getSetFunTree", method = RequestMethod.POST)
    public void getUserSetFunTree(@RequestParam Map<String, Object> params) {
        super.toAjax(userService.getSetFunTree(params, logininfo));
    }

/***************************************************************用户信息end********************************************************************/

    /***************************************************************角色管理Start********************************************************************/
    @Autowired
    RoleService roleService;
    @Autowired
    RoleMapper roleMapper;

    //获取表头
    @RequestMapping(value = "/role/roleTableColumns", method = RequestMethod.POST)
    public void roleTableColumns() {
        super.toAjax(roleService.roleTableColumns());
    }


    //获取数据
    @RequestMapping(value = "/role/roleTableData", method = RequestMethod.POST)
    public void roleTableData(@RequestParam Map<String, Object> params) {
        super.basefilter = "";
        Map m = super.grid();
        String order = " order by " + sortfield + " " + dir;
        Page page = (Page) roleMapper.selectPage(new Page(Long.valueOf(super.page), Long.valueOf(super.limit)), (Wrapper<Role>) new QueryWrapper().apply(SqlFilter.CheckSql(m.get("filter") + order)));
        List maps = new ArrayList<Map>();
        for (Object o : page.getRecords()) {
            maps.add(o);
        }
        TableData tableData = new TableData();
        tableData.setTotalCount(page.getTotal());
        tableData.setList(maps);
        super.toAjax(JSONObject.toJSONString(tableData));
    }

    //获取增加表单
    @RequestMapping(value = "/role/toaddrole", method = RequestMethod.POST)
    public void toAddRole() {
        super.toAjax(roleService.roleForm("add", new Role()));
    }

    //增加功能
    @RequestMapping(value = "/role/addrole", method = RequestMethod.POST)
    public void addRole(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        Role role = BeanUtils.mapToBean(params, Role.class);
        String msg = roleService.addRole(role, logininfo);
        if ("".equals(msg)) {
            super.toAjaxSuccess();
        } else {
            super.toAjaxError(msg);
        }
    }

    //获取查看表单
    @RequestMapping(value = "/role/lookrole", method = RequestMethod.POST)
    public void toLookRole(Role role) {
        role = roleMapper.selectOne(new QueryWrapper<Role>().eq("roleid", role.getRoleid()));
        super.toAjax(roleService.roleForm("look", role));
    }

    //获取修改表单
    @RequestMapping(value = "/role/toeditrole", method = RequestMethod.POST)
    public void toEditRole(Role role) {
        role = roleMapper.selectOne(new QueryWrapper<Role>().eq("roleid", role.getRoleid()));
        super.toAjax(roleService.roleForm("edit", role));
    }

    //保存修改
    @RequestMapping(value = "/role/editrole", method = RequestMethod.POST)
    public void editRole(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        Role role = BeanUtils.mapToBean(params, Role.class);
        String msg = roleService.editRole(role, logininfo);
        super.toAjaxSuccess();
    }

    //删除
    @RequestMapping(value = "/role/delrole", method = RequestMethod.POST)
    public void delRole(Role role) {
        roleService.delRole(role.getRoleid());
        super.toAjaxSuccess();
    }

    //获取用户的功能树
    @RequestMapping(value = "/role/getSetFunTree", method = RequestMethod.POST)
    public void getRoleSetFunTree(@RequestParam Map<String, Object> params) {
        super.toAjax(roleService.getSetFunTree(params, logininfo));
    }

    //保存角色的功能树
    @RequestMapping(value = "/role/saveFunPower", method = RequestMethod.POST)
    public void saveRoleFunPower(@RequestParam Map<String, Object> params) {
        roleService.setFun(params.get("permcodes").toString(), params.get("roleid").toString(), params.get("productid").toString());
        super.toAjaxSuccess();
    }

    //管理角色用户
    @RequestMapping(value = "/role/getOrganUserTree", method = RequestMethod.POST)
    public void getOrganUserTree(@RequestParam(required = false) String organid, @RequestParam(required = false) String roleid) {
        if (organid == null || roleid == null) {
            super.toAjaxSuccess();
        } else {
            super.toAjax(JSONArray.toJSONString(roleService.getOrganUserTree(organid, roleid)));
        }
    }

    //保存设置的用户组
    @RequestMapping(value = "/role/saveSetRole", method = RequestMethod.POST)
    public void saveSetRole(@RequestParam Map<String, Object> params) {
       roleService.saveSetRole(params.get("organid").toString(),params.get("userids").toString(),params.get("roleid").toString());
       super.toAjaxSuccess();
    }

    //获取数据权限树
    @RequestMapping(value = "/role/getDataTree", method = RequestMethod.POST)
    public void getRoleDataTree(@RequestParam Map<String, Object> params) {
        //id指标（用户或角色），0为角色，1为用户
        int idFlag = 0;
        String json = "";
//		用户id 或 角色id
        String id = params.get("roleid") + "";
        List<Datapermission> list = null;
        String parentid = request.getParameter("treeid") + "";
        // 生成基础树，没有checked属性
        json = unitService.getAll(id, idFlag);
        if ("".equals(json) || json == null) {
            json = "{\"success\":false}";
        }
        super.toAjax(json);
    }

    //保存角色的数据权限
    @RequestMapping(value = "/role/saveDataPower", method = RequestMethod.POST)
    public void saveRoleDataPower(@RequestParam Map<String, Object> params) {
        unitService.saveDataPower(params.get("roleid").toString(), params.get("permcodes").toString(), "0");
        super.toAjaxSuccess();
    }
/***************************************************************角色管理end********************************************************************/
    /***************************************************************权限分配Start********************************************************************/
    //保存用户的功能权限
    @RequestMapping(value = "/power/saveFunPower", method = RequestMethod.POST)
    public void saveUserFunPower(@RequestParam Map<String, Object> params) {
        userService.setFun(params.get("permcodes").toString(), params.get("userid").toString(), params.get("productid").toString());
        super.toAjaxSuccess();
    }

    //获取用户的数据权限树
    @RequestMapping(value = "/power/getDataTree", method = RequestMethod.POST)
    public void getUserDataTree(@RequestParam Map<String, Object> params) {
        //id指标（用户或角色），0为角色，1为用户
        int idFlag = 0;
        String json = "";
//		用户id 或 角色id
        String id = params.get("roleid") + "";
        List<Datapermission> list = null;
        String parentid = request.getParameter("treeid") + "";
        // 生成基础树，没有checked属性
        // 如果为空，是用户-单位，否则，是角色-单位
        if ("null".equals(id)) {
            idFlag = 1;
            id = params.get("userid") + "";
        }
        json = unitService.getAll(id, idFlag);
        if ("".equals(json) || json == null) {
            json = "{\"success\":false}";
        }
        super.toAjax(json);
    }

    //保存用户的数据权限
    @RequestMapping(value = "/power/saveDataPower", method = RequestMethod.POST)
    public void saveUserDataPower(@RequestParam Map<String, Object> params) {
        unitService.saveDataPower(params.get("userid").toString(), params.get("permcodes").toString(), "1");
        super.toAjaxSuccess();
    }

    //获取可选角色
    @RequestMapping(value = "/power/getAllRole", method = RequestMethod.GET)
    public void getAllRole(@RequestParam Map<String, Object> params) {
        super.toAjax(roleService.getAllRole(params.get("userid").toString()));
    }

    //获取已选角色
    @RequestMapping(value = "/power/getSelRole", method = RequestMethod.GET)
    public void getSelRole(@RequestParam Map<String, Object> params) {
        super.toAjax(roleService.getSelRole(params.get("userid").toString()));
    }

    //保存已选角色
    @RequestMapping(value = "/power/saveSelRole", method = RequestMethod.POST)
    public void saveSelRole(@RequestParam Map<String, Object> params) {
        roleService.saveSelRole(params.get("roleids").toString(), params.get("userid").toString());
        super.toAjaxSuccess();
    }
    /***************************************************************权限分配end********************************************************************/
    /***************************************************************分类维护Start********************************************************************/

    @Autowired
    SortService sortService;

    //加载用户组织树
    @RequestMapping(value = "/sort/tree", method = RequestMethod.POST)
    public void sortTree(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        super.toAjax(sortService.sortTree(treeid));
    }

    //获取表头
    @RequestMapping(value = "/sort/sortTableColumns", method = RequestMethod.POST)
    public void sortTableColumns() {
        super.toAjax(sortService.sortTableColumns());
    }

    //获取数据
    @RequestMapping(value = "/sort/sortTableData", method = RequestMethod.POST)
    public void sortTableData() {
        String treefilter = "";
        this.fieldlist = "id,sortcode,sortname,sortfullname,sequence,blankline";
        if (treeid == null || "0".equals(treeid)) {
            treefilter = "parentid is null";
            super.basefilter = treefilter;
        } else {
            treefilter = "parentid='" + treeid + "'";
            super.basefilter = treefilter;
        }
        Map m = super.grid();
        String[] fieldArray = m.get("fieldlist").toString().split(",");
        String order = " order by " + sortfield + " " + dir;
        Page page = (Page) sortMapper.selectPage(new Page(Long.valueOf(m.get("page").toString()), Long.valueOf(m.get("limit").toString())), (Wrapper<Sort>) new QueryWrapper().apply(SqlFilter.CheckSql(m.get("filter") + order)));
        List maps = new ArrayList<Map>();
        for (Object o : page.getRecords()) {
            maps.add(o);
        }
        TableData tableData = new TableData();
        tableData.setTotalCount(page.getTotal());
        tableData.setList(maps);
        super.toAjax(JSONObject.toJSONString(tableData));
    }

    //// 校验分类最多只能有二级分类
    @RequestMapping(value = "/sort/checkAdd", method = RequestMethod.POST)
    public void checkAdd(Sort sort) {
        Boolean bool = false;
        try {
            bool = sortService.checkAdd(sort);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result = "{\"success\":true,msg:" + bool + "}";
        super.toAjax(result);
    }

    //获取增加表单
    @RequestMapping(value = "/sort/toaddsort", method = RequestMethod.POST)
    public void toAddsort(Sort sort) {
        super.toAjax(sortService.sortForm("add", sort));
    }

    //增加功能
    @RequestMapping(value = "/sort/addsort", method = RequestMethod.POST)
    public void addsort(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        Sort sort = BeanUtils.mapToBean(params, Sort.class);
        String msg = sortService.addsort(sort, logininfo);
        if ("".equals(msg)) {
            super.toAjaxSuccess();
        } else {
            super.toAjaxError(msg);
        }
    }

    //获取修改表单
    @RequestMapping(value = "/sort/toeditsort", method = RequestMethod.POST)
    public void toEditsort(Sort sort) {
        sort = sortMapper.selectOne(new QueryWrapper<Sort>().eq("id", sort.getId()));
        super.toAjax(sortService.sortForm("edit", sort));
    }

    //保存修改
    @RequestMapping(value = "/sort/editsort", method = RequestMethod.POST)
    public void editsort(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        Sort sort = BeanUtils.mapToBean(params, Sort.class);
        String msg = sortService.editSort(sort, logininfo);
        if (!"".equals(msg)) {
            super.toAjaxError(msg);
        } else {
            super.toAjaxSuccess();
        }
    }

    //删除
    @RequestMapping(value = "/sort/delsort", method = RequestMethod.POST)
    public void delsort(Sort sort) {
        sortService.delSort(sort.getId());
    }

    //排序页面
    @RequestMapping(value = "/sort/sort", method = RequestMethod.POST)
    public void getSort(@RequestParam Map<String, Object> params) {
        String parentid = params.get("parentid").toString();
        String json = sortService.getSort(parentid);
        super.toAjax(json);
    }

    //保存排序
    @RequestMapping(value = "/sort/savesort", method = RequestMethod.POST)
    public void saveSort(@RequestParam Map<String, Object> params) {
        String ids = (String) params.get("items") == null ? "" : (String) params.get("items");
        sortService.saveSort(ids);
        super.toAjaxSuccess();
    }
/***************************************************************分类维护end********************************************************************/

    /***************************************************************用户状态维护start********************************************************************/
    @Autowired
    UserStatusMapper userStatusMapper;
    @Autowired
    UserStatusService userStatusService;

    //加载树
    @RequestMapping(value = "/userstatus/tree", method = RequestMethod.POST)
    public void userstatusTree() {
        super.toAjax(userStatusService.userStatusTree());
    }

    //获取表头
    @RequestMapping(value = "/userstatus/userStatusTableColumns", method = RequestMethod.POST)
    public void userStatusTableColumns() {
        super.toAjax(userStatusService.userStatusTableColumns());
    }

    //获取表格数据
    @RequestMapping(value = "/userstatus/userStatusTableData", method = RequestMethod.POST)
    public void userStatusTableData(@RequestParam Map<String, Object> params) {
        basefilter = "";
        Map m = super.grid();
        String[] fieldArray = m.get("fieldlist").toString().split(",");
        String order = " order by " + sortfield + " " + dir;
        Page page = (Page) userStatusMapper.selectPage(new Page(Long.valueOf(m.get("page").toString()), Long.valueOf(m.get("limit").toString())), (Wrapper<UserStatus>) new QueryWrapper().apply(SqlFilter.CheckSql(m.get("filter") + order)));
        List maps = new ArrayList<Map>();
        for (Object o : page.getRecords()) {
            maps.add(o);
        }
        TableData tableData = new TableData();
        tableData.setTotalCount(page.getTotal());
        tableData.setList(maps);
        super.toAjax(JSONObject.toJSONString(tableData));
    }

    //获取增加表单
    @RequestMapping(value = "/userstatus/toadduserstatus", method = RequestMethod.POST)
    public void toAdduserstatus(UserStatus userstatus) {
        super.toAjax(userStatusService.userStatusForm("add", userstatus));
    }

    //增加功能
    @RequestMapping(value = "/userstatus/adduserstatus", method = RequestMethod.POST)
    public void adduserstatus(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        UserStatus userstatus = BeanUtils.mapToBean(params, UserStatus.class);
        String msg = userStatusService.addUserStatus(userstatus, logininfo);
        if ("".equals(msg)) {
            super.toAjaxSuccess();
        } else {
            super.toAjaxError(msg);
        }
    }

    //获取修改表单
    @RequestMapping(value = "/userstatus/toedituserstatus", method = RequestMethod.POST)
    public void toEdituserstatus(UserStatus userstatus) {
        userstatus = userStatusMapper.selectOne(new QueryWrapper<UserStatus>().eq("id", userstatus.getId()));
        super.toAjax(userStatusService.userStatusForm("edit", userstatus));
    }

    //保存修改
    @RequestMapping(value = "/userstatus/edituserstatus", method = RequestMethod.POST)
    public void edituserstatus(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        UserStatus userstatus = BeanUtils.mapToBean(params, UserStatus.class);
        String msg = userStatusService.editUserstatus(userstatus, logininfo);
        if (!"".equals(msg)) {
            super.toAjaxError(msg);
        } else {
            super.toAjaxSuccess();
        }
    }

    //删除
    @RequestMapping(value = "/userstatus/deluserstatus", method = RequestMethod.POST)
    public void deluserstatus(UserStatus userstatus) {
        String msg = userStatusService.delUserstatus(userstatus.getId());
        if ("".equals(msg)) {
            super.toAjaxSuccess();
        } else {
            super.toAjaxError(msg);
        }
    }


/***************************************************************用户状态维护end********************************************************************/
    /***************************************************************数据字典维护start********************************************************************/
    //加载树
    @RequestMapping(value = "/dictionary/tree", method = RequestMethod.POST)
    public void dictionaryTree() {
        super.toAjax(dictionaryService.dictionaryTree(treeid));
    }

    //获取表头
    @RequestMapping(value = "/dictionary/dictionaryTableColumns", method = RequestMethod.POST)
    public void dictionaryTableColumns() {
        super.toAjax(dictionaryService.dictionaryTableColumns());
    }

    //获取表格数据
    @RequestMapping(value = "/dictionary/dictionaryTableData", method = RequestMethod.POST)
    public void dictionaryTableData(@RequestParam Map<String, Object> params) {
        if (treeid == null || "0".equals(treeid)) {
            basefilter = "parentid is null";
        } else {
            basefilter = "parentid='" + treeid + "'";
        }
        Map m = super.grid();
        String[] fieldArray = m.get("fieldlist").toString().split(",");
        String order = " order by " + sortfield + " " + dir;
        Page page = (Page) dictionaryMapper.selectPage(new Page(Long.valueOf(m.get("page").toString()), Long.valueOf(m.get("limit").toString())), (Wrapper<Dictionary>) new QueryWrapper().apply(SqlFilter.CheckSql(m.get("filter") + order)));
        List maps = new ArrayList<Map>();
        for (Object o : page.getRecords()) {
            maps.add(o);
        }
        TableData tableData = new TableData();
        tableData.setTotalCount(page.getTotal());
        tableData.setList(maps);
        super.toAjax(JSONObject.toJSONString(tableData));
    }

    @RequestMapping(value = "/dictionary/checkAdd", method = RequestMethod.POST)
    public void checkAddDictionary(Dictionary dictionary) {
        Boolean bool = false;
        try {
            bool = dictionaryService.checkAdd(dictionary);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result = "{\"success\":true,msg:" + bool + "}";
        super.toAjax(result);
    }

    //获取增加表单
    @RequestMapping(value = "/dictionary/toadddictionary", method = RequestMethod.POST)
    public void toAdddictionary(Dictionary dictionary) {
        super.toAjax(dictionaryService.dictionaryForm("add", dictionary));
    }

    //增加功能
    @RequestMapping(value = "/dictionary/adddictionary", method = RequestMethod.POST)
    public void adddictionary(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        Dictionary dictionary = BeanUtils.mapToBean(params, Dictionary.class);
        String msg = dictionaryService.addDictionary(dictionary, logininfo);
        if ("".equals(msg)) {
            super.toAjaxSuccess();
        } else {
            super.toAjaxError(msg);
        }
    }

    //获取修改表单
    @RequestMapping(value = "/dictionary/toeditdictionary", method = RequestMethod.POST)
    public void toEditdictionary(Dictionary dictionary) {
        dictionary = dictionaryMapper.selectOne(new QueryWrapper<Dictionary>().eq("id", dictionary.getId()));
        super.toAjax(dictionaryService.dictionaryForm("edit", dictionary));
    }

    //保存修改
    @RequestMapping(value = "/dictionary/editdictionary", method = RequestMethod.POST)
    public void editdictionary(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        Dictionary dictionary = BeanUtils.mapToBean(params, Dictionary.class);
        String msg = dictionaryService.editDictionary(dictionary, logininfo);
        if (!"".equals(msg)) {
            super.toAjaxError(msg);
        } else {
            super.toAjaxSuccess();
        }
    }

    //删除
    @RequestMapping(value = "/dictionary/deldictionary", method = RequestMethod.POST)
    public void deldictionary(Dictionary dictionary) {
        String msg = dictionaryService.delDictionary(dictionary.getId());
        if ("".equals(msg)) {
            super.toAjaxSuccess();
        } else {
            super.toAjaxError(msg);
        }
    }

    //排序页面
    @RequestMapping(value = "/dictionary/getdictionarysort", method = RequestMethod.POST)
    public void getDictionarySort(@RequestParam Map<String, Object> params) {
        String parentid = params.get("parentid").toString();
        String json = dictionaryService.getSort(parentid);
        super.toAjax(json);
    }

    //保存排序
    @RequestMapping(value = "/dictionary/savedictionarysort", method = RequestMethod.POST)
    public void saveDictionarySort(@RequestParam Map<String, Object> params) {
        String ids = (String) params.get("items") == null ? "" : (String) params.get("items");
        dictionaryService.saveSort(ids);
        super.toAjaxSuccess();
    }
/***************************************************************数据字典维护end********************************************************************/

    /***************************************************************文件存储start********************************************************************/
    @Autowired
    StoreService storeService;

    //获取表头
    @RequestMapping(value = "/store/storeTableColumns", method = RequestMethod.POST)
    public void storeTableColumns() {
        super.toAjax(storeService.storeTableColumns());
    }


    //获取数据
    @RequestMapping(value = "/store/storeTableData", method = RequestMethod.POST)
    public void storeTableData(@RequestParam Map<String, Object> params) {
        super.basefilter = "";
        Map m = super.grid();
        String order = " order by " + sortfield + " " + dir;
        Page page = (Page) storeMapper.selectPage(new Page(Long.valueOf(super.page), Long.valueOf(super.limit)), (Wrapper<Store>) new QueryWrapper().apply(SqlFilter.CheckSql(m.get("filter") + order)));
        List maps = new ArrayList<Map>();
        for (Object o : page.getRecords()) {
            maps.add(o);
        }
        TableData tableData = new TableData();
        tableData.setTotalCount(page.getTotal());
        tableData.setList(maps);
        super.toAjax(JSONObject.toJSONString(tableData));
    }

    //获取增加表单
    @RequestMapping(value = "/store/toaddstore", method = RequestMethod.POST)
    public void toAddstore(Store store) {
        super.toAjax(storeService.storeForm("add", store));
    }

    //增加功能
    @RequestMapping(value = "/store/addstore", method = RequestMethod.POST)
    public void addstore(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        Store store = BeanUtils.mapToBean(params, Store.class);
        String msg = storeService.addStore(store, logininfo);
        if ("".equals(msg)) {
            super.toAjaxSuccess();
        } else {
            super.toAjaxError(msg);
        }
    }

    //获取修改表单
    @RequestMapping(value = "/store/toeditstore", method = RequestMethod.POST)
    public void toEditstore(Store store) {
        store = storeMapper.selectOne(new QueryWrapper<Store>().eq("id", store.getId()));
        super.toAjax(storeService.storeForm("edit", store));
    }

    //保存修改
    @RequestMapping(value = "/store/editstore", method = RequestMethod.POST)
    public void editstore(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        Store store = BeanUtils.mapToBean(params, Store.class);
        String msg = storeService.editStore(store, logininfo);
        if (!"".equals(msg)) {
            super.toAjaxError(msg);
        } else {
            super.toAjaxSuccess();
        }
    }

    //删除
    @RequestMapping(value = "/store/delstore", method = RequestMethod.POST)
    public void delstore(Store store) {
        String msg = storeService.delStore(store.getId());
        if ("".equals(msg)) {
            super.toAjaxSuccess();
        } else {
            super.toAjaxError(msg);
        }
    }
/***************************************************************文件存储end********************************************************************/

    /***************************************************************系统日志start********************************************************************/
    @Autowired
    LogService logService;
    @Autowired
    DataLogMapper dataLogMapper;
    @Autowired
    LoginLogMapper loginLogMapper;
    @Autowired
    SecurityLogMapper securityLogMapper;

    //获取树结构
    @RequestMapping(value = "/log/tree", method = RequestMethod.POST)
    public void logTree(@RequestParam String modulecode) {
        super.toAjax(logService.tree(modulecode));
    }


    //获取表头
    @RequestMapping(value = "/log/loginLogColumns", method = RequestMethod.POST)
    public void loginLogColumns() {
        super.toAjax(logService.loginLogColumns());
    }

    //获取表头
    @RequestMapping(value = "/log/safeLogColumns", method = RequestMethod.POST)
    public void safeLogColumns() {
        super.toAjax(logService.safeLogColumns());
    }

    //获取数据
    @RequestMapping(value = "/log/logDataTables", method = RequestMethod.POST)
    public void logTableData(@RequestParam Map<String, Object> params) {
        super.basefilter = "";
        Map m = super.grid();
        String order = " order by " + sortfield + " " + dir;
        Page page = null;
        String tablename = params.get("tablename").toString();
        if (CommonUtil.getTableName(DataLog.class).equals(tablename)) {
            //数据日志分页
            page = (Page) dataLogMapper.selectPage(new Page(Long.valueOf(super.page), Long.valueOf(super.limit)), (Wrapper<DataLog>) new QueryWrapper().apply(SqlFilter.CheckSql(m.get("filter") + order)));
        }
        if (CommonUtil.getTableName(LoginLog.class).equals(tablename)) {
            //登录日志分页
            page = (Page) loginLogMapper.selectPage(new Page(Long.valueOf(super.page), Long.valueOf(super.limit)), (Wrapper<LoginLog>) new QueryWrapper().apply(SqlFilter.CheckSql(m.get("filter") + order)));
        }
        if (CommonUtil.getTableName(SecurityLog.class).equals(tablename)) {
            //数据日志分页
            page = (Page) securityLogMapper.selectPage(new Page(Long.valueOf(super.page), Long.valueOf(super.limit)), (Wrapper<SecurityLog>) new QueryWrapper().apply(SqlFilter.CheckSql(m.get("filter") + order)));
        }
        List maps = new ArrayList<Map>();
        for (Object o : page.getRecords()) {
            maps.add(o);
        }
        TableData tableData = new TableData();
        tableData.setTotalCount(page.getTotal());
        tableData.setList(maps);
        super.toAjax(JSONObject.toJSONString(tableData));
    }


/***************************************************************系统日志end**********************************************************************/


    /***************************************************************系统公告start********************************************************************/
    @Autowired
    NoticeService noticeService;

    //获取表头
    @RequestMapping(value = "/notice/noticeTableColumns", method = RequestMethod.POST)
    public void noticeTableColumns() {
        super.toAjax(noticeService.noticeTableColumns());
    }

    //获取数据
    @RequestMapping(value = "/notice/noticeTableData", method = RequestMethod.POST)
    public void noticeTableData(Notice notice) {
        super.basefilter = "";
        Map m = super.grid();
        String order = " order by " + sortfield + " " + dir;
        Page page = (Page) noticeMapper.selectPage(new Page(Long.valueOf(super.page), Long.valueOf(super.limit)), (Wrapper<Notice>) new QueryWrapper().apply(SqlFilter.CheckSql(m.get("filter") + order)));
        List maps = new ArrayList<Map>();
        for (Object o : page.getRecords()) {
            maps.add(o);
        }
        TableData tableData = new TableData();
        tableData.setTotalCount(page.getTotal());
        tableData.setList(maps);
        super.toAjax(JSONObject.toJSONString(tableData));
    }

    //获取增加表单
    @RequestMapping(value = "/notice/toaddnotice", method = RequestMethod.POST)
    public void toAddNotice() {
        super.toAjax(noticeService.noticeForm("add", new Notice()));
    }

    //增加功能
    @RequestMapping(value = "/notice/addnotice", method = RequestMethod.POST)
    public void addNotice(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        Notice notice = BeanUtils.mapToBean(params, Notice.class);
        noticeService.addNotice(notice, logininfo);
        super.toAjaxSuccess();
    }

    //获取查看表单
    @RequestMapping(value = "/notice/looknotice", method = RequestMethod.POST)
    public void toLookNotice(Notice notice) {
        notice = noticeMapper.selectOne(new QueryWrapper<Notice>().eq("id", notice.getId()));
        super.toAjax(noticeService.noticeForm("look", notice));
    }

    //获取修改表单
    @RequestMapping(value = "/notice/toeditnotice", method = RequestMethod.POST)
    public void toEditNotice(Notice notice) {
        notice = noticeMapper.selectOne(new QueryWrapper<Notice>().eq("id", notice.getId()));
        super.toAjax(noticeService.noticeForm("edit", notice));
    }

    //保存修改
    @RequestMapping(value = "/notice/editnotice", method = RequestMethod.POST)
    public void editNotice(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        Notice notice = BeanUtils.mapToBean(params, Notice.class);
        String msg = noticeService.editNotice(notice, logininfo);
        if (!"".equals(msg)) {
            super.toAjaxError(msg);
        } else {
            super.toAjaxSuccess();
        }
    }

    //删除
    @RequestMapping(value = "/notice/delnotice", method = RequestMethod.POST)
    public void delNotice(Notice notice) {
        noticeService.delNotice(notice);
        super.toAjaxSuccess();
    }

    //首页查看详细
    @RequestMapping(value = "/notice/maindetail", method = RequestMethod.POST)
    public ModelAndView mainDetail(@RequestParam String id) {
        List mapList = noticeMapper.selectMaps(new QueryWrapper<Notice>().eq("id", id.replace(",", "")));
        ModelMap modelMap = new ModelMap();
        modelMap.put("map", mapList.get(0));
        return new ModelAndView("/person/security/notice/noticedetail", modelMap);
    }
/***************************************************************系统公告end**********************************************************************/

    /***************************************************************图片水印start********************************************************************/
/***************************************************************图片水印end**********************************************************************/

    /***************************************************************查档事由start********************************************************************/
    @Autowired
    SqcdService sqcdService;
    @Autowired
    SqcdreasonMapper sqcdreasonMapper;

    //获取表头
    @RequestMapping(value = "/sqcdreason/sqcdReasonTableColumns", method = RequestMethod.POST)
    public void sqcdReasonTableColumns() {
        super.toAjax(sqcdService.sqcdReasonTableColumns());
    }

    //获取数据
    @RequestMapping(value = "/sqcdreason/sqcdResonableData", method = RequestMethod.POST)
    public void sqcdreasonTableData(SqcdReason sqcdreason) {
        super.basefilter = "";
        Map m = super.grid();
        String order = " order by " + sortfield + " " + dir;
        Page page = (Page) sqcdreasonMapper.selectPage(new Page(Long.valueOf(super.page), Long.valueOf(super.limit)), (Wrapper<SqcdReason>) new QueryWrapper().apply(SqlFilter.CheckSql(m.get("filter") + order)));
        List maps = new ArrayList<Map>();
        for (Object o : page.getRecords()) {
            maps.add(o);
        }
        TableData tableData = new TableData();
        tableData.setTotalCount(page.getTotal());
        tableData.setList(maps);
        super.toAjax(JSONObject.toJSONString(tableData));
    }

    //获取增加表单
    @RequestMapping(value = "/sqcdreason/toaddsqcdreason", method = RequestMethod.POST)
    public void toAddsqcdreason() {
        super.toAjax(sqcdService.sqcdReasonForm("add", new SqcdReason()));
    }

    //增加功能
    @RequestMapping(value = "/sqcdreason/addsqcdreason", method = RequestMethod.POST)
    public void addsqcdreason(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        SqcdReason sqcdreason = BeanUtils.mapToBean(params, SqcdReason.class);
        sqcdService.addSqcdReason(sqcdreason, logininfo);
        super.toAjaxSuccess();
    }

    //获取查看表单
    @RequestMapping(value = "/sqcdreason/looksqcdreason", method = RequestMethod.POST)
    public void toLooksqcdreason(SqcdReason sqcdreason) {
        sqcdreason = sqcdreasonMapper.selectOne(new QueryWrapper<SqcdReason>().eq("id", sqcdreason.getId()));
        super.toAjax(sqcdService.sqcdReasonForm("look", sqcdreason));
    }

    //获取修改表单
    @RequestMapping(value = "/sqcdreason/toeditsqcdreason", method = RequestMethod.POST)
    public void toEditsqcdreason(SqcdReason sqcdreason) {
        sqcdreason = sqcdreasonMapper.selectOne(new QueryWrapper<SqcdReason>().eq("id", sqcdreason.getId()));
        super.toAjax(sqcdService.sqcdReasonForm("edit", sqcdreason));
    }

    //保存修改
    @RequestMapping(value = "/sqcdreason/editsqcdreason", method = RequestMethod.POST)
    public void editsqcdreason(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        SqcdReason sqcdreason = BeanUtils.mapToBean(params, SqcdReason.class);
        String msg = sqcdService.editsSqcdReason(sqcdreason, logininfo);
        if (!"".equals(msg)) {
            super.toAjaxError(msg);
        } else {
            super.toAjaxSuccess();
        }
    }

    //删除
    @RequestMapping(value = "/sqcdreason/delsqcdreason", method = RequestMethod.POST)
    public void delsqcdreason(SqcdReason sqcdreason) {
        sqcdService.delSqcdeason(sqcdreason);
        super.toAjaxSuccess();
    }
/***************************************************************查档事由end**********************************************************************/
    /***************************************************************系统配置start**********************************************************************/
//获取表头
    @RequestMapping(value = "/config/configTableColumns", method = RequestMethod.POST)
    public void configTableColumns() {
        super.toAjax(configService.configTableColumns());
    }


    //获取数据
    @RequestMapping(value = "/config/configTableData", method = RequestMethod.POST)
    public void configTableData(@RequestParam Map<String, Object> params) {
        super.basefilter = "";
        Map m = super.grid();
        String order = " order by " + sortfield + " " + dir;
        Page page = (Page) configMapper.selectPage(new Page(Long.valueOf(super.page), Long.valueOf(super.limit)), (Wrapper<Config>) new QueryWrapper().apply(SqlFilter.CheckSql(m.get("filter") + order)));
        List maps = new ArrayList<Map>();
        for (Object o : page.getRecords()) {
            maps.add(o);
        }
        TableData tableData = new TableData();
        tableData.setTotalCount(page.getTotal());
        tableData.setList(maps);
        super.toAjax(JSONObject.toJSONString(tableData));
    }

    //获取增加表单
    @RequestMapping(value = "/config/toaddconfig", method = RequestMethod.POST)
    public void toAddConfig() {
        super.toAjax(configService.configForm("add", new Config()));
    }

    //增加功能
    @RequestMapping(value = "/config/addconfig", method = RequestMethod.POST)
    public void addConfig(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        Config config = BeanUtils.mapToBean(params, Config.class);
        String msg = configService.addConfig(config, logininfo);
        if ("".equals(msg)) {
            super.toAjaxSuccess();
        } else {
            super.toAjaxError(msg);
        }
    }

    //获取查看表单
    @RequestMapping(value = "/config/lookconfig", method = RequestMethod.POST)
    public void toLookConfig(Config config) {
        config = configMapper.selectOne(new QueryWrapper<Config>().eq("id", config.getId()));
        super.toAjax(configService.configForm("look", config));
    }

    //获取修改表单
    @RequestMapping(value = "/config/toeditconfig", method = RequestMethod.POST)
    public void toEditConfig(Config config) {
        config = configMapper.selectOne(new QueryWrapper<Config>().eq("id", config.getId()));
        super.toAjax(configService.configForm("edit", config));
    }

    //保存修改
    @RequestMapping(value = "/config/editconfig", method = RequestMethod.POST)
    public void editConfig(@RequestParam Map<String, Object> params) {
        params = decodeMap(params);
        Config config = BeanUtils.mapToBean(params, Config.class);
        String msg = configService.editConfig(config, logininfo);
        super.toAjaxSuccess();
    }

    //删除
    @RequestMapping(value = "/config/delconfig", method = RequestMethod.POST)
    public void delConfig(Config config) {
        configService.delConfig(config.getId());
        super.toAjaxSuccess();
    }

/***************************************************************系统配置end**********************************************************************/


}