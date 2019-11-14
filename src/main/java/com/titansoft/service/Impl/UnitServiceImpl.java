package com.titansoft.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.titansoft.entity.Unit;
import com.titansoft.entity.cadre.Cadre;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.entity.view.UnitView;
import com.titansoft.mapper.CadreMapper;
import com.titansoft.mapper.UnitMapper;
import com.titansoft.mapper.UnitViewMapper;
import com.titansoft.model.*;
import com.titansoft.service.UnitService;
import com.titansoft.utils.database.SqlFilter;
import com.titansoft.utils.database.TableName;
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
 * @Date: 2019/7/27 15:20
 */
@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    UnitMapper unitMapper;
    @Autowired
    UnitViewMapper unitViewMapper;
    @Autowired
    CadreMapper cadreMapper;

    /**
     * @param * @param parentid:  父节点
     * @return
     * @description 获取单位树
     * @author Fkw
     * @date 2019/7/27
     */
    @Override
    public List<Exttreenot> unitTree(String parentid) {
        List<Exttreenot> treelist = new ArrayList<Exttreenot>();
        List list = null;
        Exttreenot node = null;
        if ("0".equals(parentid) || "root".equals(parentid)) {
            list = unitViewMapper.selectList(new QueryWrapper<UnitView>().isNull("parentid").orderByAsc("levelord"));
        } else {
            list = unitViewMapper.selectList(new QueryWrapper<UnitView>().eq("parentid", parentid).orderByAsc("levelord"));
        }
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            UnitView unitView = (UnitView) it.next();
            node = new Exttreenot();
            node.setId(unitView.getId());
            node.setText(unitView.getText());
            node.setName(unitView.getText());
            if (unitView.getIsleaf() == null || "".equals(unitView.getIsleaf()) || "true".equals(unitView.getIsleaf())) {
                node.setLeaf(true);
            }
            treelist.add(node);
        }
        return treelist;
    }

    /**
     * @return
     * @description 获取表头
     * @author Fkw
     * @date 2019/9/11
     */
    @Override
    public String unitTableColumns() {
        String columnsJson = "";
        List<Column> columns = new ArrayList<Column>();
        Column column = null;

        column = new Column();
        column.setDataIndex("id");
        column.setText("单位id");
        column.setXtype("gridcolumnview");
        column.setHidden(true);
        column.setFlex(0);
        column.setWidth(100);
        columns.add(column);


        column = new Column();
        column.setDataIndex("text");
        column.setText("简称");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(150);
        columns.add(column);

        column = new Column();
        column.setDataIndex("unitfullname");
        column.setText("全称");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(150);
        columns.add(column);


        column = new Column();
        column.setDataIndex("levelnum");
        column.setText("序号");
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
     * @param unit    :
     * @return
     * @description
     * @author Fkw
     * @date 2019/9/16
     */
    @Override
    public String unitForm(String operate, Unit unit) {
        ArrayList<VO> list = new ArrayList<VO>();
        VO vo = null;
        vo = new VO();
        vo.put("fieldname", "单位简称");
        vo.put("fieldcode", "name");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 50);
        vo.put("iswhieline", "true");
        vo.put("display", "true");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "单位全称");
        vo.put("fieldcode", "unitfullname");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 50);
        vo.put("iswhieline", "true");
        vo.put("display", "true");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "单位代码");
        vo.put("fieldcode", "unitcode");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 50);
        vo.put("iswhieline", "true");
        vo.put("display", "true");
        vo.put("mustinput", "false");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "描述详情");
        vo.put("fieldcode", "description");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 100);
        vo.put("display", "true");
        vo.put("iswhieline", "true");
        vo.put("mustinput", "false");
        list.add(vo);

        XsdObject xsdObject;
        ArrayList<XsdObject> hiddens = new ArrayList<XsdObject>();
        xsdObject = new XsdObject();
        xsdObject.setFieldName("unitid");
        xsdObject.setDefaultvalue(unit.getUnitid());
        hiddens.add(xsdObject);
        return FormUtil.NotButtonConvertJSON(list, unit, hiddens, operate);

    }

    /**
     * @param unit
     * @param logininfo :
     * @return
     * @description 增加单位
     * @author Fkw
     * @date 2019/9/17
     */
    @Override
    public void addUnit(Unit unit, Logininfo logininfo) {
        List list = null;
        String unitid = unit.getUnitid();
        unit.setCreatetime(DateUtil.getTimeNow(new Date()));
        if (unit.getParentid() != null && !"".equals(unit.getParentid().trim())
                && !"0".equals(unit.getParentid().trim())
                && !"root".equalsIgnoreCase(unit.getParentid().trim())) {
            // 为修改机构调整，Rootid有值则为修改操作
            list = unitViewMapper.selectList(new QueryWrapper<UnitView>().eq("id", unit.getParentid()));
            Map parentpo = (Map) list.get(0);
            unit.setRootid(parentpo.get("rootid").toString());
            String levelnum = String.valueOf(Long.parseLong(parentpo.get("levelnum").toString()) + 1);
            unit.setLevelnum(levelnum);
            unit.setUnitfullname(parentpo.get("unitfullname") + "_" + unit.getName());
        } else {
            unit.setRootid(unitid);
            unit.setParentid(null);
            unit.setLevelnum("1");
            list = unitViewMapper.getMaxUnitcodeAndMaxLevelord();
            if (list != null && list.size() > 0) {
                String levelord = (String) ((Map) list.get(0)).get("levelord") == null ? "0"
                        : (String) ((Map) list.get(0)).get("levelord");
                if ("".equals(levelord)) {
                    levelord = "0";
                }
                unit.setLevelord(String.valueOf(Long.parseLong(levelord) + 1));
            } else {
                unit.setLevelord("100");
            }
            String maxunitcode = (String) ((Map) list.get(0)).get("maxunitcode");
            String newunitcode = "";
            if (maxunitcode != null && !"".equals(maxunitcode)) {
                maxunitcode = maxunitcode.replace("UNIT", "");
                newunitcode = String.valueOf(Integer.parseInt(maxunitcode) + 1);
                newunitcode = "UNIT" + StringformatZreoUtil.getNumFillZreo(newunitcode, 4);
            } else {
                newunitcode = "UNIT0001";// 当最大值不存在时，默认是0001，第一个节点
            }
            unit.setUnitcode(newunitcode);
            unit.setUnitfullname(unit.getName());
        }
        unit.setCode(unit.getCode());
        if (unit.getParentid() != null) {
            unitMapper.updateUnit(CommonUtil.getTableName(Unit.class), "isleaf='false'", " unitid ='" + SqlFilter.CheckSql(unit.getParentid()) + "'");
        }
        unit.setUnitid(CommonUtil.getGuid());
        unitMapper.insert(unit);
        unitMapper.insertUnitLevel(SqlFilter.CheckSql("'" + unit.getUnitid() + "','" + unit.getRootid() + "','" + unit.getParentid() + "','" + unit.getLevelnum() + "','" + unit.getLevelord() + "'"));
        // 记录安全数据日志数据
        int index = unit.getUnitfullname().lastIndexOf("_") == -1 ? unit.getUnitfullname().length()
                : unit.getUnitfullname().lastIndexOf("_");
        String parentfullname = unit.getUnitfullname().substring(0, index);
    }

    /**
     * @param unitid
     * @return
     * @description 删除单位
     * @author Fkw
     * @date 2019/9/17
     */
    @Override
    public String delUnit(String unitid) {
        String result = "存在子单位,请先删除所有子单位！";
        String parentid = null;
        List list = unitMapper.getUnitList("distinct *", "t_da_unitlevel", "parentid='" + unitid + "'");
        if (list == null || list.size() < 1) {
            if (list == null || list.size() < 1) {
                list = cadreMapper.selectList(new QueryWrapper<Cadre>().eq("departid", unitid));
                if (list == null || list.size() < 1) {
                    List unitViewList = unitViewMapper.selectList(new QueryWrapper<UnitView>().eq("id", unitid));//this.basedao.queryhashmap("select code,parentid from v_unit where id='" + datapo.getUnitid() + "'");
                    if (unitViewList.size() > 0) {
                        UnitView unitView = (UnitView) unitViewList.get(0);
                        parentid = unitView.getParentid();
                    }
                    ////// 记录安全数据日志数据
                    /*if (operation.equals("删除")) {
                        int index = datapo.getUnitfullname().lastIndexOf("_") == -1 ? datapo.getUnitfullname().length()
                                : datapo.getUnitfullname().lastIndexOf("_");
                        String parentfullname = datapo.getUnitfullname().substring(0, index);

                    }*/
                    // 删除单位
                    delUnitData(unitid);
                    result = "操作成功";
                    unitViewList = unitViewMapper.selectList(new QueryWrapper<UnitView>().eq("parentid", parentid));//this.basedao.queryhashmap("select * from v_unit where parentid='" + parentid + "'");
                    if (unitViewList == null || unitViewList.size() == 0) {
                        unitMapper.updateUnit("t_da_unit", "isleaf='true'", "unitid='" + parentid + "'");
                    }
                } else {
                    result = "单位存在数据,请先删除数据！";
                }
            } else {
                result = "单位存在用户,请先删除所有单位用户！";
            }
        }
        return result;
    }

    /**
     * @param unit
     * @return
     * @description 修改单位
     * @author Fkw
     * @date 2019/9/17
     */
    @Override
    public String editUnit(Unit unit) {
        // 查询是否有重复单位代码，名称
        List<Unit> unitList = unitMapper.selectList(new QueryWrapper<Unit>().ne("unitid", unit.getUnitid()).and(unitQueryWrapper -> unitQueryWrapper.eq("code", unit.getCode()).or().eq("name", unit.getName())));
                /*.queryhashmap("select * from " + Unit.getTablenameofpo() + " where (code ='" + unit.getCode()
                        + "' or name = '" + unit.getName() + "')" + "and unitid <> '" + unit.getUnitid() + "'");*/
        if (unitList.size() > 0) {
            return "您保存的数据可能有重复，如有其他问题请联系管理员！";
        } else {
            unitMapper.updateById(unit);
        }
        return "";
    }

    /**
     * @param parentid
     * @return
     * @description 获取当前排序
     * @author Fkw
     * @date 2019/9/18
     */
    @Override
    public String getSort(String parentid) {
        List list = null;
        String json = null;
        if (parentid == null || "0".equals(parentid) || "root".equals(parentid)) {
            list = unitViewMapper.selectList(new QueryWrapper<UnitView>().select("distinct id", "text", "levelord").isNull("parentid").orderByAsc("levelord"));
        } else {
            list = unitViewMapper.selectList(new QueryWrapper<UnitView>().select("distinct id", "text", "levelord").eq("parentid", parentid).orderByAsc("levelord"));
        }
        json = JSONObject.toJSONString(list);
        return json;
    }

    /**
     * @param unitids
     * @return
     * @description 保存排序
     * @author Fkw
     * @date 2019/9/18
     */
    @Override
    public String saveSort(String unitids) {
        String unitid[] = unitids.split(",");
        for (int i = 0; i < unitid.length; i++) {
            unitMapper.updateUnit("t_da_unitlevel", "levelord=" + String.format("%04d", i + 1), "unitid='" + unitid[i] + "'");
        }
        return null;
    }

    /**
     * @param parentid
     * @return
     * @description 获取单位列表
     * @author Fkw
     * @date 2019/9/28
     */
    @Override
    public List getUnitname(String parentid) {
        QueryWrapper queryWrapper = new QueryWrapper<UnitView>().select("id as unitid", "text as name");
        if (!"".equals(parentid) && parentid != null) {
            queryWrapper.eq("parentid", parentid);
        } else {
            queryWrapper.isNull("parentid");
        }
        List list = unitViewMapper.selectList(queryWrapper);
        return list;
    }

    /**
     * @param unitid
     * @return
     * @description 获取部门列表
     * @author Fkw
     * @date 2019/9/28
     */
    @Override
    public List getDepartname(String unitid) {
        QueryWrapper queryWrapper = new QueryWrapper<UnitView>().select("id as unitid", "text as name");
        if (!"".equals(unitid) && unitid != null) {
            queryWrapper.inSql("parentid", "select id from " + CommonUtil.getTableName(UnitView.class) + " where text='" + unitid + "'");
        } else {
            queryWrapper.isNull("parentid");
        }
        List list = unitViewMapper.selectList(queryWrapper);
        return list;
    }

    /**
     * @param id
     * @param idFlag :为0代表是角色
     * @return
     * @description 根据用户id或者角色id获取已有的数据权限
     * @author Fkw
     * @date 2019/10/19
     */
    @Override
    public String getAll(String id, int idFlag) {
        List<Exttreenot> treelist = new ArrayList<Exttreenot>();
        List list = null;
        List unitlist = null;
        Exttreenot node = null;
        list = CommonUtil.transformUpperCaseList(unitViewMapper.getAllUnit());
        String sql = "";
        if (idFlag == 0) {
            unitlist = unitMapper.selectFilterSql("unitid", TableName.RoleUnitTable, "roleid='" + id + "'");
        } else {
            unitlist = unitMapper.selectFilterSql("unitid", TableName.UserUnitTable, "userid='" + id + "'");
        }
        Map<String, List> parentidMap = new HashMap<String, List>();
        Map<String, String> roleFunMap = new HashMap<String, String>();
        String parentid = "";
        String lastparentid = "";
        List listorgan = null;
        String json = "[]";
        String rootid = "";
        for (int i = 0; i < list.size(); i++) {
            Map map = CommonUtil.MapNullToEmpty((Map) list.get(i));
            for (int j = 0; j < unitlist.size(); j++) {
                if (map.get("id").toString().equals(((Map) unitlist.get(j)).get("unitid").toString())) {
                    map.put("checked", "true");
                }
            }
            parentid = map.get("parentid").toString();
            if (parentid == null || "".equals(parentid)) {
                parentid = "0";
            }
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
     * @param userid
     * @param idFlag :数据权限功能（1:单位-用户，0:单位-角色）,保存勾选记录 方法根据idFlag判断是用户还是角色，对应执行相应sql
     * @return
     * @description 保存数据权限
     * @author Fkw
     * @date 2019/10/19
     */
    @Override
    public void saveDataPower(String userid, String permcodes, String idFlag) {
        // 判断是否有root，默认为没有
        int isRoot = 0;
        if ("0".equals(idFlag)) {
            unitMapper.delteteFilterSql(TableName.RoleUnitTable, "ROLEID='" + userid
                    + "' and UNITID in (select * from (select unitid FROM " + TableName.RoleUnitTable + " WHERE ROLEID='" + userid + "') a )");
        } else {
            unitMapper.delteteFilterSql(TableName.UserUnitTable, " USERID='" + userid
                    + "' and UNITID in (select * from (select unitid FROM " + TableName.UserUnitTable + " WHERE USERID='" + userid + "')a)");
        }
        // 先删除再增加
        if (!"".equals(permcodes)) {
            // 去除字符串头尾多余字符，并替换引号，根据逗号分隔成数组
            String[] unitidList = permcodes.split(",");
            // 有root，则倒序插入少1，
            if ("root".equals(unitidList[0].toString())) {
                isRoot = 1;
            }
            String tablename = "";
            if ("0".equals(idFlag)) {
                tablename = TableName.RoleUnitTable;
            } else {
                tablename = TableName.UserUnitTable;
            }
            // 插入数据
            for (int i = unitidList.length - 1; i >= isRoot; i--) {
                unitMapper.insertSql(tablename, "'" + userid + "','" + unitidList[i] + "'");
            }
        }
    }

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

    //删除单位数据
    @Transactional
    public void delUnitData(String unitid) {
        unitMapper.deleteById(unitid);
        unitMapper.delUnit("t_da_unitlevel", " unitid='" + unitid + "'");
    }
}
