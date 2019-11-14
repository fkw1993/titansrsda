package com.titansoft.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.titansoft.entity.cadre.Cadre;
import com.titansoft.mapper.CadreMapper;
import com.titansoft.service.CadreService;
import com.titansoft.service.PrivilegeService;
import com.titansoft.utils.database.SqlFilter;
import com.titansoft.utils.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理采集，审核，管理公共请求
 *
 * @Author: Kevin
 * @Date: 2019/7/31 15:01
 */
public class CadreBaseController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CadreBaseController.class);

    @Autowired
    PrivilegeService privilegeService;
    @Autowired
    CadreService cadreService;
    @Autowired
    CadreMapper cadreMapper;

    public String getGridView(String modulecode, String glstate) {
        String json = "";
        try {
            //进入模块记录日志
           /* sv.put("logininfo", logininfo);
            sv.put("modulecode", modulecode);
            sv.put("functionname", "");
            sv.doService("security.log.bs.LogServiceBS", "writemodulelog");*/
            // 获取grid列信息
            json = getExtRSGridViewConfigService();
            // 获取按钮
            List<Map<String, String>> buttonMapList = privilegeService.moduloldprivilege(modulecode, logininfo);
            String buttonliststr = "buttonlist:{";
            String undealbuttonlist = "";
            for (int i = 0; i < buttonMapList.size(); i++) {
                Map m = buttonMapList.get(i);
                undealbuttonlist += "{\"text\":\"" + m.get("text") + "\",\"id\":\"" + m.get("privilegeid")
                        + "\",\"name\":\"" + m.get("privilegeid") + "\",\"iconCls\":\"" + m.get("iconcls")
                        + "\",\"handler\":function(){" + m.get("eventname") + "('" + m.get("privilegeid") + "');}},";

            }
            undealbuttonlist = "[" + undealbuttonlist + "]";
            buttonliststr += "undealbuttonlist:" + undealbuttonlist;
            buttonliststr += "}";
            json = json.replace("buttonlist:[]", buttonliststr);
        } catch (Exception e) {

        } finally {
            return json;
        }
    }

    public String getExtRSGridViewConfigService() {
        StringBuilder json = new StringBuilder("{\"success\":true");
        String columns = "";
        List listItems = new ArrayList();
        String storefieldsStr = "";
        List<String> storefields = new ArrayList<String>();
        Map vo = null;
        vo = new HashMap();
        vo.put("dataIndex", "id");
        vo.put("header", "id");
        vo.put("width", 100);
        vo.put("sortable", true);
        vo.put("hidden", true);
        listItems.add(vo);
        storefields.add("id");

        vo = new HashMap();
        vo.put("dataIndex", "departid");
        vo.put("header", "departid");
        vo.put("width", 100);
        vo.put("sortable", true);
        vo.put("hidden", true);
        listItems.add(vo);
        storefields.add("departid");

        vo = new HashMap();
        vo.put("renderer", "showgridtip");
        vo.put("dataIndex", "code");
        vo.put("width", 200);
        vo.put("header", "干部编号");
        vo.put("sortable", true);
        vo.put("hidden", true);
        listItems.add(vo);
        storefields.add("code");

        vo = new HashMap();
        vo.put("renderer", "showgridtip");
        vo.put("dataIndex", "name");
        vo.put("width", 100);
        vo.put("header", "干部姓名");
        vo.put("sortable", true);
        listItems.add(vo);
        storefields.add("name");

        vo = new HashMap();
        vo.put("renderer", "showgridtip");
        vo.put("dataIndex", "sex");
        vo.put("width", 50);
        vo.put("header", "性别");
        vo.put("sortable", true);
        listItems.add(vo);
        storefields.add("sex");

        vo = new HashMap();
        vo.put("renderer", "showgridtip");
        vo.put("dataIndex", "idnumber");
        vo.put("width", 200);
        vo.put("header", "身份证号");
        vo.put("sortable", true);
        listItems.add(vo);
        storefields.add("idnumber");

        vo = new HashMap();
        vo.put("renderer", "showgridtip");
        vo.put("dataIndex", "nation");
        vo.put("width", 50);
        vo.put("header", "民族");
        vo.put("sortable", true);
        listItems.add(vo);
        storefields.add("nation");

        vo = new HashMap();
        vo.put("renderer", "showgridtip");
        vo.put("dataIndex", "origin");
        vo.put("width", 100);
        vo.put("header", "籍贯");
        vo.put("sortable", true);
        listItems.add(vo);
        storefields.add("origin");

        vo = new HashMap();
        vo.put("renderer", "showgridtip");
        vo.put("dataIndex", "birthday");
        vo.put("width", 100);
        vo.put("header", "出生日期");
        vo.put("sortable", true);
        listItems.add(vo);
        storefields.add("birthday");

        vo = new HashMap();
        vo.put("renderer", "showgridtip");
        vo.put("dataIndex", "worktime");
        vo.put("width", 100);
        vo.put("header", "工作时间");
        vo.put("sortable", true);
        listItems.add(vo);
        storefields.add("worktime");

        vo = new HashMap();
        vo.put("renderer", "showgridtip");
        vo.put("dataIndex", "political");
        vo.put("width", 100);
        vo.put("header", "政治面貌");
        vo.put("sortable", true);
        listItems.add(vo);
        storefields.add("political");

        vo = new HashMap();
        vo.put("renderer", "showgridtip");
        vo.put("dataIndex", "department");
        vo.put("width", 150);
        vo.put("header", "部门名称");
        vo.put("sortable", true);
        listItems.add(vo);
        storefields.add("department");

        vo = new HashMap();
        vo.put("renderer", "showgridtip");
        vo.put("dataIndex", "unitname");
        vo.put("width", 150);
        vo.put("header", "单位名称");
        vo.put("sortable", true);
        listItems.add(vo);
        storefields.add("unitname");

        columns = JSONArray.toJSONString(listItems);
        storefieldsStr = JSONArray.toJSONString(storefields);
        json.append(", griditemlist:" + columns);
        json.append(", storefields:" + storefieldsStr);
        String sortfield = "\"name\"";
        String sorttype = "\"asc\"";
        String pkfield = "\"id\"";
        json.append(", sortfield:" + sortfield);
        json.append(", sorttype:" + sorttype);
        json.append(", pkfield:" + pkfield);
        json.append(",buttonlist:[]");
        json.append("}");
        return json.toString().replace("\"showgridtip\"", "showgridtip");
    }

    /**
     * 构造树
     */
    public String tree(String treeid) {
        String result = "[]";
        try {
            result = cadreService.tree(treeid, logininfo.getRoleid());
        } catch (Exception e) {
            log.error("加载树失败：" + e.getMessage());
        } finally {
            return result;
        }
    }

    /**
     * @param *        @param treeid:
     * @param glstate:
     * @return
     * @description 输出表格数据
     * @author Fkw
     * @date 2019/8/2
     */
    public void grid(String treeid, String glstate) {
        treeid = SqlFilter.CheckSql(treeid);
        QueryWrapper<Cadre> queryWrapper = new QueryWrapper<Cadre>();
        String unitid = null;
        String departid = null;
        if (treeid != null && !"0".equals(treeid)) {
            String treefilter = "";
            if (treeid.indexOf("-") < 0) {
                queryWrapper.eq("cadrestatus", treeid);
                treefilter += "cadrestatus='" + treeid + "'";
            } else {
                if (treeid.split("-").length == 2) {
                    unitid = treeid.split("-")[1];
                    treefilter += " unitid='" + unitid + "' and departId in (select id from v_unit v join T_S_USER_UNIT t on v.id=t.unitid where t.userid in (" + CommonUtil.getIDstr(logininfo.getRoleid()) + ")) ";
                    queryWrapper.eq("unitid", unitid).inSql("departId", "select id from v_unit v join T_S_USER_UNIT t on v.id=t.unitid where t.userid in (" + CommonUtil.getIDstr(logininfo.getRoleid()) + ")");
                }
                if (treeid.split("-").length >= 3) {
                    unitid = treeid.split("-")[1];
                    departid = treeid.split("-")[treeid.split("-").length - 1];
                    treefilter += "departId='" + departid + "' and unitid='" + unitid + "'";
                    queryWrapper.eq("departId", departid).eq("unitid", unitid);
                }
            }
            if ("sh".equals(glstate)) {
                treefilter = treefilter + " and status='sh' ";
                queryWrapper.eq("status", "sh");
            }
            super.basefilter = treefilter;
        } else {
            super.basefilter = "1=1";
        }
        Map m = super.grid();
        String[] fieldArray = m.get("fieldlist").toString().split(",");
        String order = " order by pinyin asc";
        //分页获取数据
        Page page = (Page) cadreMapper.selectPage(new Page(Long.valueOf(m.get("page").toString()), Long.valueOf(m.get("limit").toString())), (Wrapper<Cadre>) new QueryWrapper().select(fieldArray).apply(m.get("filter").toString() + SqlFilter.CheckSql(order)));
        String json = super.pageToJson(page);
        result = "{basefilter:\"" + this.basefilter + "\"," + json.substring(1);
        super.toAjax(result);
    }

    private String formatstr(String str) {
        str = str.replace("\\", "\\\\");
        str = str.replace("\"", "\\\"");
        str = str.replace(String.valueOf((char) 32), "&nbsp; ");
        str = str.replace(String.valueOf((char) 9), "&nbsp; ");
        str = str.replace(String.valueOf((char) 34), "&quot; ");
        str = str.replace(String.valueOf((char) 39), "&#39; ");
        str = str.replace(String.valueOf((char) 13), "   ");
        str = str.replace(String.valueOf((char) 10) + String.valueOf((char) 10), " </P> <P> ");
        str = str.replace(String.valueOf((char) 10), " <BR> ");
        return str;
    }


    //干部简单检索
    public void cadreSearch(Map<String, Object> params) {
        // super.setParams(request);
        super.fieldlist = "id,departid,name,sex,idnumber,department,nation,origin,birthday,worktime,political";
        // 检索词
        String searchkey = (params.get("searchkey") == null) ? "" : params.get("searchkey").toString().trim();
        // 高级检索
        String heightfilter = (params.get("heightfilter") == null) ? "" : params.get("heightfilter").toString().trim();
        // 结果中检索
        String resultcheck = (params.get("resultcheck") == null) ? "" : params.get("resultcheck").toString().trim();
        String unitname = logininfo.getUnitname();
        //数据权限sql
        String dataSql = "select distinct unitid from T_S_ROLE_UNIT where roleid in (" + CommonUtil.getIDstr(logininfo.getRoleid()) + ") UNION select distinct unitid from T_S_USER_UNIT where USERID in (" + CommonUtil.getIDstr(logininfo.getRoleid()) + ")";
        //条件构造器
        QueryWrapper<Cadre> queryWrapper = new QueryWrapper<Cadre>();
        String unitsql = " unitid in (" + dataSql + ")";
        String filter = "";
        if (!"".equals(searchkey)) {
            Map map = getWhereSql(searchkey);
            queryWrapper = (QueryWrapper<Cadre>) map.get("queryWrapper");
            filter = map.get("filter").toString();
            if ("true".equals(resultcheck)) {
                super.basefilter = "(" + super.basefilter + ") AND (" + (String) map.get("filter") + ")";
            } else {
                if ("".equals(map.get("filter"))) {

                } else {
                    super.basefilter = map.get("filter").toString();
                }
            }
        } else if (!"".equals(heightfilter)) {
            filter = getHighFilter(heightfilter);
            if ("true".equals(resultcheck)) {
                super.basefilter = "(" + super.basefilter + ") AND (" + filter + ")";
            } else {
                super.basefilter = filter;
            }

        } else {
            super.filter = "1=2";
        }
        Map m = super.grid();
        String[] fieldArray = m.get("fieldlist").toString().split(",");
        //分页获取数据
        Page page = (Page) cadreMapper.selectPage(new Page(Long.valueOf(m.get("page").toString()), Long.valueOf(m.get("limit").toString())), new QueryWrapper<Cadre>().select(fieldArray).apply(filter).apply(unitsql).orderByAsc("pinyin"));
        String json = super.pageToJson(page);
        result = "{basefilter:\"" + super.basefilter + "\"," + json.substring(1);
        super.toAjax(result);
    }

    /**
     * @param * @param searchkey:
     * @return
     * @description 把条件封装成 QueryWrapper
     * @author Fkw
     * @date 2019/8/24
     */
    public Map getWhereSql(String searchkey) {
        //如果是%，则直接搜出所有条件
        Map returnMap = new HashMap();
        String filter = "1=1";
        QueryWrapper<Cadre> queryWrapper = new QueryWrapper();
        if ("%".equals(searchkey)) {
            filter = "1=1";
        } else {
            String[] keywordArr = searchkey.split(",");
            StringBuffer filterBuffer = new StringBuffer();
            int i = 0;
            for (String condition : keywordArr) {
                String checksql = SqlFilter.CheckSql(condition);

                String whereSql = "pinyin like '%" + condition.toLowerCase() + "%' or name like '%" + condition
                        + "%' or idNumber like '%" + condition + "%'";
                queryWrapper.or(wrapper -> queryWrapper.like("pinyin", condition.toLowerCase()).or().like("name", condition).or().like("idnumber", condition));
                if (condition.trim().equals(""))
                    break;
                if (i == 0) {
                    filterBuffer.append(whereSql);
                } else {
                    filterBuffer.append("or").append(whereSql);
                    queryWrapper.or(wrapper -> queryWrapper.like("pinyin", condition.toLowerCase()).or().like("name", condition).or().like("idnumber", condition));
                }
                i++;
            }
            filter = "(" + filterBuffer.toString() + ")";
        }
        returnMap.put("queryWrapper", queryWrapper);
        returnMap.put("filter", filter);
        return returnMap;
    }

    /**
     * @param * @param idnumber:
     * @return
     * @description 处理高级检索的条件
     * @author Fkw
     * @date 2019/10/14
     */
    public String getHighFilter(String heightfilter) {
        List<String> list = new ArrayList<String>();
        String filter = "";
        JSONArray jsonArray = JSONArray.parseArray(heightfilter);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject job = jsonArray.getJSONObject(i); // 遍历 jsonarray
            // 数组，把每一个对象转成 json
            // 对象
            if (i > 0)
                list.add(job.get("op").toString());

            list.add(job.get("field").toString());
            String operator = job.get("operator").toString();
            switch (operator) {
                case "like":
                    list.add(operator);
                    list.add("'%" + job.get("value") + "%'");
                    break;
                default:
                    list.add(operator);
                    list.add("'" + job.get("value") + "'");
                    break;
            }

        }
        String[] toBeStored = list.toArray(new String[list.size()]);
        for (int j = 0; j < toBeStored.length; j++) {
            filter += toBeStored[j] + " ";
        }
        return filter;
    }

    //根据idnumber获取干部
    public Cadre getCadreByIdnumber(String idnumber) {
        return cadreMapper.selectOne(new QueryWrapper<Cadre>().eq("idnumber", idnumber));
    }
}
