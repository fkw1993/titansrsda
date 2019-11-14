package com.titansoft.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.titansoft.LoadOnStartup;
import com.titansoft.controller.LoginController;
import com.titansoft.entity.*;

import com.titansoft.entity.sqcd.Sqcd;
import com.titansoft.entity.sqcd.SqcdItem;
import com.titansoft.entity.system.Auth;
import com.titansoft.entity.system.Login;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.entity.system.Privilege;
import com.titansoft.mapper.*;
import com.titansoft.model.Exttreenot;
import com.titansoft.service.LoginService;
import com.titansoft.service.UnitService;
import com.titansoft.utils.annotation.LoginLog;
import com.titansoft.utils.config.SystemConfig;
import com.titansoft.utils.util.CommonUtil;
import com.titansoft.utils.util.DateTools;
import com.titansoft.utils.util.ReverseEncript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: Kevin
 * @Date: 2019/7/25 11:36
 */
@Service
public class LoginSerciveImpl extends BaseServiceImpl implements LoginService {


    @Autowired
    Login login;

    public String productid;

    /*
     * @description 判断用户类型
     * @param  * @param username:s
     * @return
     * @author Fkw
     * @date 2019/7/25
     */
    @Override
    public String checkUserType(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            if ("临时用户".equals(user.getUsertype()) && "已激活".equals(user.getStatus())) {
                return "临时用户";
            }
            if ("普通用户".equals(user.getUsertype()) && "已激活".equals(user.getStatus())) {
                return "普通用户";
            }
        } else {
            return "";
        }
        return "";
    }

    /**
     * @description 校验登录
     * @param  * @param username:
     * @param password:
     * @return
     * @author Fkw
     * @date 2019/7/25
     */
    @Override
    public User checkUser(String username, String password) {
        String result = "";
        password = ReverseEncript.getMD5(password);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", username);
        queryWrapper.eq("pwd", password);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null && !"临时用户".equals(user.getUsertype())) {
            List<Organ> organList = organMapper.selectList(new QueryWrapper<Organ>().inSql("organid", "select organid from t_s_user where userid='" + user.getUserid() + "'"));
            if (organList.size() > 0) {
                Organ organ = organList.get(0);
                result = user.getUserid() + "," + user.getCode() + "," + user.getName() + "," + organ.getOrganid() + ","
                        + "" + "," + organ.getName();
            }
        } else {
            result = "";
        }
        return user;
    }

    @Override
    public void getAllUser() {
        userMapper.getAllUser();
    }

    /**
     * @description 登录验证方法
     * @param  * @param code: 账号
     * @param pwd: 密码
     * @param yzm: 验证码
     * @param request:
     * @return
     * @author Fkw
     * @date 2019/7/28
     */
    @Override
    @LoginLog(description = "登录系统")
    public String checkLogin(String code, String pwd, String yzm, HttpServletRequest request) throws Exception {
        String result="";
            // 用户名密码验证码解码
            if (code == null || "".equals(code.trim()))
                throw new Exception("用户名不能为空！");
            login.setCode(code);
            login.setPwd(pwd);
            String checkcodeconfig = SystemConfig.getValue("titans.checkcode") + "";
            String checkcode = request.getSession().getAttribute("checkcode") + "";
            //判断验证码是否正确
            if (!yzm.equals(checkcode)) {
                throw new Exception("验证码错误！");
            }
            //判断用户是临时用户还是管理用户，临时用户登录后直接进入利用平台
            User user = checkUser(login.getCode(), login.getPwd());
            if (user == null) {
                throw new Exception("用户名或者密码错误");
            } else {
                if ("临时用户".equals(user.getUsertype())) {
                    //登录利用系统用电话号码作为账号
                    Logininfo logininfo = new Logininfo();
                    logininfo.setUsername(login.getCode());
                    String stylelinks = "blue";
                    logininfo.setStylelinks(stylelinks);
                    logininfo.setIpaddress(request.getRemoteAddr().trim());
                    //根据电话号码获取查档id
                    List sqcdItemList=sqcdItemMapper.selectList(new QueryWrapper<SqcdItem>().eq("phonenumber",login.getCode()));
                    String sqcdid="";
                    if(sqcdItemList.size()>0){
                        SqcdItem sqcdItem= (SqcdItem) sqcdItemList.get(0);
                        sqcdid=sqcdItem.getSqcdid();
                    }
                    request.getSession().setAttribute("logininfo", logininfo);
                    result = "{\"success\":true,\"usertype\":\"临时用户\",\"sqcdid\":\"" + sqcdid + "\"}";
                }else {
                    List<Organ> organList = organMapper.selectList(new QueryWrapper<Organ>().inSql("organid", "select organid from t_s_user where userid='" + user.getUserid() + "'"));
                    Organ organ = new Organ();
                    if (organList.size() > 0) {
                        organ = organList.get(0);
                    } else {
                        throw new Exception("此用户没有分配机构！");
                    }
                    String userid = user.getUserid();
                    String username = user.getCode();
                    String realname = user.getName();
                    String unitid = organ.getOrganid();
                    String unitname = organ.getName();
                    //aliyu是否开启三员管理
                    if ("是".equals(SystemConfig.getValue("titans.isthreenummgr"))) {
                        if ("sysdba".equals(username)) {
                            throw new Exception("开启三员管理后，sysdba没有登陆本系统的权限!");
                        }
                    }
                    // 用户状态
                    if (!"已激活".equals(user.getStatus())) {
                        throw new Exception("您没有登陆本系统的权限!");
                    }
                    //根据用户名获取权限id
                    List<Map<String, Object>> list = roleMapper.getRoleIdByUsername(user.getCode());
                    String roleid = "";
                    if (list.size() > 0) {
                        roleid = CommonUtil.getListMapString(list, "ROLEID", ",") + userid;
                    } else {
                        roleid = userid;
                    }
                    //获取数据权限id
                    List<Map<String, Object>> dataList = roleMapper.getDataIdByRoleId(CommonUtil.getIDstr(roleid));
                    if (productid == null || "".equals(productid)) {
                        productid = SystemConfig.getValue("titans.productid");
                    }

                    //读入功能权限 start
                    Map<String, Map<String, String>> funmap = new HashMap<String, Map<String, String>>();
                    List<Privilege> privilegeList = privilegeMapper.selectList(null);
                    for (Privilege p : privilegeList) {
                        funmap.put(p.getPrivilegeid().toString(), null);
                    }
                    //读入功能权限 end

                    //获取系统安全维护菜单
                    List<Map<String, Object>> menuList=privilegeMapper.getMenu("43F57503466C8E58717B8379C50579ED",CommonUtil.getIDstr(roleid));
                    //获取人事档案管理模块
                    List<Map<String, Object>> moduleList=privilegeMapper.getModule("5BCD3977A26DC04B98A5A09224A76FB7",CommonUtil.getIDstr(roleid));
                    //获取干部人事信息登记
                    List<Map<String, Object>> rsxxList=privilegeMapper.getModule("824FD2D30F75377F99101D2B0F7925B3",CommonUtil.getIDstr(roleid));
                    //获取待办事项 获取最近前五个
                    Page<Sqcd> page = (Page<Sqcd>) sqcdMapper.selectPage(new Page<>(1, 5), new QueryWrapper<Sqcd>().eq("approvalstate","待处理").orderByDesc("applydate"));

                    //录入凭证信息
                    String token = "";
                    // 创建凭据对象
                    Auth auth = new Auth();
                    // 设置产品ID
                    auth.setProductcode(productid);
                    // 用户代号
                    auth.setUsercode(user.getCode());
                    // 用户所在客户端登录时的IP
                    auth.setAddress(request.getRemoteAddr());
                    auth.setPpcode("");
                    Date dt = new Date();
                    String cudatetime = DateTools.pdateToString(dt, "yyyy-MM-dd HH:mm:ss");
                    // 设置登录时间
                    auth.setLogontime(cudatetime);
                    // 设置最后访问时间
                    auth.setAccesstime(cudatetime);
                    // 获取登录验证串
                    token =  CommonUtil.getGuid();
                    // 设置登录验证串
                    auth.setAccesstoken(token);
                    // 根据用户代号和IP地址删除存在数据
                    authMapper.delete((Wrapper<Auth>) new UpdateWrapper().eq("address",request.getRemoteAddr()));
                    // 写入凭据
                    auth.setAid(CommonUtil.getGuid());
                    authMapper.insert(auth);
                    Logininfo logininfo = new Logininfo();
                    logininfo.setUserid(userid);
                    logininfo.setUnitname(unitname);
                    logininfo.setUsername(login.getCode());
                    logininfo.setUnitid(unitid);
                    logininfo.setDataid( CommonUtil.getListMapString(dataList, "unitid", ","));
                    logininfo.setRoleid(roleid);
                    logininfo.setRealname(realname);
                    logininfo.setFunmap(funmap);
                    logininfo.setIpaddress(request.getRemoteAddr().trim());
                    String stylelinks = "blue";
                    logininfo.setStylelinks(stylelinks);
                    //把数据放入session
                    request.getSession().setAttribute("logininfo", logininfo);
                    request.getSession().setAttribute("menulist", menuList);
                    request.getSession().setAttribute("modulelist", moduleList);
                    request.getSession().setAttribute("rsxxlist", rsxxList);
                    request.getSession().setAttribute("sqcdlist", page.getRecords());
                    request.getSession().setAttribute("dictionary", CommonUtil.maptojson(LoadOnStartup.dictionaryList));
                    LoginController.logininfoMap.put(request.getRemoteAddr().trim(), logininfo);

                    List<Unitstatus> objListUnitstatus = new ArrayList<Unitstatus>();
                    Unitstatus objUnitstatus = null;
                    // 在职
                    objUnitstatus = new Unitstatus();
                    objUnitstatus.setStatusid("0001");
                    objUnitstatus.setStatusname("在职");
                    objUnitstatus.setIshavechildnode("true");
                    objListUnitstatus.add(objUnitstatus);

                    // 离退
                    objUnitstatus = new Unitstatus();
                    objUnitstatus.setStatusid("0002");
                    objUnitstatus.setStatusname("离退");
                    objListUnitstatus.add(objUnitstatus);
                    objUnitstatus.setIshavechildnode("true");

                    // 身故
                    objUnitstatus = new Unitstatus();
                    objUnitstatus.setStatusid("0003");
                    objUnitstatus.setStatusname("身故");
                    objListUnitstatus.add(objUnitstatus);
                    objUnitstatus.setIshavechildnode("false");

                    // 其它
                    objUnitstatus = new Unitstatus();
                    objUnitstatus.setStatusid("0004");
                    objUnitstatus.setStatusname("其它");
                    objListUnitstatus.add(objUnitstatus);
                    objUnitstatus.setIshavechildnode("false");

                    //加载单位树
                    List<Exttreenot> objListUnit = unitService.unitTree("root");
                    String unitTree = "[";
                    for(Unitstatus objstatus : objListUnitstatus){
                        if(!"".equals(unitTree) && !"[".equals(unitTree))unitTree += ",";
                        unitTree += "{id:\""+objstatus.getStatusid()+"\", pId:0,name:\""+objstatus.getStatusname()+"\",open:true,isParent:true}";
                        if(!Boolean.parseBoolean(objstatus.getIshavechildnode()))
                            continue;
                        for(Exttreenot objUnit : objListUnit){
                            if(!"".equals(unitTree))unitTree += ",";
                            unitTree += "{id:\""+objstatus.getStatusid()+"_"+objUnit.getId()+"\", pId:\""+objstatus.getStatusid()+"\",name:\""+objUnit.getName()+"\",open:true,isParent:true}";
                            List<Exttreenot> objListUnitSub = unitService.unitTree(objUnit.getId());
                            for(Exttreenot objUnitsub : objListUnitSub){
                                unitTree += ",{id:\""+objstatus.getStatusid()+"_"+objUnitsub.getId()+"\",pId:\""+objstatus.getStatusid()+"_"+objUnit.getId()+"\",name:\""+objUnitsub.getName()+"\",pName:\""+objUnit.getName()+"\",file:\"../person/person.jsp\"}";
                            }
                        }
                    }
                    unitTree += "]";
                    request.getSession().setAttribute("treeUnitJson",unitTree);
                    //删除某一个时间段以前凭证
                    String format="yyyy-MM-dd HH:mm:ss";
                    dt =DateTools.Dateaddminute(-20);
                    String curtime=DateTools.pdateToString(dt, format);
                    authMapper.delete((Wrapper<Auth>) new UpdateWrapper().le("accesstime",curtime));
                    result = "{\"success\":true,\"usertype\":\"普通用户\"}";
                }
            }
            return result;

    }


    /**
     * @description 获取显示首页人事档案管理模块子功能
     * @param  * @param userid:
     * @param request:
     * @return
     * @author Fkw
     * @date 2019/7/27
     */
    @Override
    public List<Map> getUserPersonFun(String userid, HttpServletRequest request) {
        ArrayList<Map> resultlist = new ArrayList<Map>();
        //设置模块颜色
        String colors="#09AA89,#9C6AD0,#DCA349,#A23163,#05AAC6,#719D2E,#495E85,#2C9F67,#00CBC5,#E15476";
        String []color=colors.split(",");
        List funlist=privilegeMapper.getUserPersonFun(userid);
        for (int i=0;i<funlist.size();i++) {
            String style = "#41C393";
            Map funmap=(Map) funlist.get(i);
            if (funmap.get("SYSTEMSTYLE") != null && !"".equals(funmap.get("SYSTEMSTYLE"))) {
                style = (String) funmap.get("SYSTEMSTYLE");
            }
            Map map = new HashMap();
            map.put("id", funmap.get("PRODUCTID"));
            map.put("code", funmap.get("PRIVILEGEID"));
            map.put("style", "background-color:" + style + ";");
            map.put("icon",funmap.get("ICONCLS"));
            map.put("color",color[i]);
            map.put("url", funmap.get("URI"));
            map.put("name", funmap.get("TEXT"));
            map.put("content", funmap.get("DESCRIPTION"));
            map.put("surfaceContent", funmap.get("DESCRIPTION"));
            resultlist.add(map);
        }
        return resultlist;
    }

    /**
     * @description 获取用户拥有权限的所有product信息
     * @param  * @param userid:
     * @param request:
     * @return
     * @author Fkw
     * @date 2019/7/27
     */
    @Override
    public List<Map> getUserProduts(String userid, HttpServletRequest request) {
        List<Map<String, Object>> productList = new ArrayList();
        ArrayList<Map> resultlist = new ArrayList<Map>();
        productList=privilegeMapper.getUserProduts(userid);
        for(Map<String, Object> produtMap : productList)
        {
            String style = "#41C393";
            if(produtMap.get("SYSTEMSTYLE") != null && !"".equals(produtMap.get("SYSTEMSTYLE")))
            {
                style = produtMap.get("SYSTEMSTYLE").toString();
            }
            Map map=new HashMap();
            map.put("id", produtMap.get("PRODUCTID"));
            map.put("code", produtMap.get("CODE"));
            map.put("style", "background-color:"+style+";");
            map.put("icon", produtMap.get("ICON"));
            map.put("color", style);
            map.put("url", produtMap.get("URI"));
            map.put("name", produtMap.get("NAME"));
            map.put("content", produtMap.get("DESCRIPTION"));
            map.put("surfaceContent", "");
            resultlist.add(map);
        }
        return resultlist;
    }

    /**
     * @param userid
     * @param request :
     * @return
     * @description 获取该用户拥有权限的product信息
     * @author Fkw
     * @date 2019/7/27
     */
    @Override
    public List<Map> getUserProdutsByPower(String userid, HttpServletRequest request) {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>> ();
        ArrayList<Map> resultlist = new ArrayList<Map>();
        list=privilegeMapper.getUserProdutsByPower(userid);
        for (Map<String, Object> produtMap : list) {
            String style = "#41C393";
            if (produtMap.get("SYSTEMSTYLE") != null && !"".equals(produtMap.get("SYSTEMSTYLE"))) {
                style = produtMap.get("SYSTEMSTYLE").toString();
            }
            Map map = new HashMap();
            map.put("id", produtMap.get("PRODUCTID"));
            map.put("code", produtMap.get("CODE"));
            map.put("style", "background-color:" + style + ";");
            // map.put("iocurl", request.getContextPath() +
            // "/mainpage/img/system/" + vo.get("icon"));
            map.put("icon", produtMap.get("ICON"));
            map.put("color", style);
            map.put("url", produtMap.get("URI"));
            map.put("name", produtMap.get("NAME"));
            map.put("content", produtMap.get("DESCRIPTION"));
            map.put("surfaceContent", "");
            resultlist.add(map);
        }
        return resultlist;
    }

    /**
     * @param userid
     * @param realname :
     * @return
     * @description 获取待办事项
     * @author Fkw
     * @date 2019/7/28
     */
    @Override
    public List<Map> getMessage(String userid, String realname) {
        return null;
    }

    /**
     * @param userid
     * @return
     * @description 获取公告
     * @author Fkw
     * @date 2019/7/28
     */
    @Override
    public List<Map> getNotice(String userid) {

        List<Map> resultlist = new ArrayList<Map>();
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String nowdate = sdf.format(now);
        String sql =null;
        Page<Notice> page = (Page<Notice>) noticeMapper.selectPage(new Page<>(1, 5), new QueryWrapper<Notice>().eq("status","已发布").ge("expirationtime",nowdate).orderByDesc("publishdate"));
        List<Notice> noticeList=page.getRecords();
        for(Notice n : noticeList)
        {
            Map map=new HashMap();
            map.put("id",n.getId());
            map.put("headline",n.getHeadline());
            map.put("issue_date", n.getCreatedatetime());
            resultlist.add(map);
        }
        return resultlist;
    }


}
