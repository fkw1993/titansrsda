package com.titansoft.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.titansoft.entity.log.ErrorLog;
import com.titansoft.entity.log.LoginLog;
import com.titansoft.entity.system.Privilege;
import com.titansoft.mapper.ErrorLogMapper;
import com.titansoft.mapper.LoginLogMapper;
import com.titansoft.model.Column;
import com.titansoft.model.ColumnJson;
import com.titansoft.model.Exttreenot;
import com.titansoft.service.LogService;
import com.titansoft.utils.config.PrivilegeConfig;
import com.titansoft.utils.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: Kevin
 * @Date: 2019/8/30 15:54
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    ErrorLogMapper errorLogMapper;
    @Autowired
    LoginLogMapper loginLogMapper;

    /**
     * 统计两种异常的次数
     * <p>
     * 返回月份以及相应的次数
     *
     * @param year
     * @param filerStr
     * @throws Exception
     * @author Lime
     * @date 2018/4/20
     */
    @Override
    public String countErrorLog(String year, String filerStr) {
        String[] array = new String[12];
        String[] array1 = new String[12];
        Arrays.fill(array, "0");
        Arrays.fill(array1, "0");
        List<Map<String, Object>> loginErroList = errorLogMapper.getErrorLogEqErrortype(CommonUtil.getTableName(ErrorLog.class), year, filerStr);
        List<Map<String, Object>> otherErroList = errorLogMapper.getErrorLogNoEqErrortype(CommonUtil.getTableName(ErrorLog.class), year, filerStr);
        for (Map m : loginErroList) {
            array[Integer.parseInt(m.get("month").toString()) - 1] = m.get("count").toString();
        }
        for (Map m : otherErroList) {
            array1[Integer.parseInt(m.get("month").toString()) - 1] = m.get("count").toString();
        }
        JSONArray jsonArr = new JSONArray();
        jsonArr.addAll(Arrays.asList(array));
        jsonArr.addAll(Arrays.asList(array1));
        return jsonArr.toString();
    }

    /**
     * 统计登录状态次数
     * <p>
     * 返回月份以及的次数
     *
     * @param year
     * @param filerStr
     * @param filerStr1
     * @throws Exception
     * @author Lime
     * @date 2018/4/23
     */
    @Override
    public String countLoginState(String year, String filerStr, String filerStr1) {
        //登录,注销两个数组,12个月的初始数据
        String[] array = new String[]{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
        String[] array1 = new String[]{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
        List<Map<String, Object>> loginList = loginLogMapper.getLoginLogCount(CommonUtil.getTableName(LoginLog.class), year, filerStr);
        List<Map<String, Object>> logoutList = loginLogMapper.getLoginLogCount(CommonUtil.getTableName(LoginLog.class), year, filerStr1);

//        登录、注销，分别转数组
        for (Map m : loginList) {
            array[Integer.parseInt(m.get("month").toString()) - 1] = m.get("count").toString();
        }
        for (Map m : logoutList) {
            array1[Integer.parseInt(m.get("month").toString()) - 1] = m.get("count").toString();
        }

//        构建json
        JSONArray jsonArr = new JSONArray();
        jsonArr.addAll(Arrays.asList(array));
        jsonArr.addAll(Arrays.asList(array1));

        return jsonArr.toString();
    }

    @Override
    public String tree(String modulecode) {
        List listtarget = new ArrayList();
        List<Privilege> list = PrivilegeConfig.privilegeListParentidMap.get(modulecode);
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Privilege map = (Privilege) it.next();
            Exttreenot tree = new Exttreenot();
            tree.setId((String) map.getPrivilegeid());
            tree.setText((String) map.getName());
            tree.setUrl((String) map.getUri());
            tree.setLeaf(true);
            listtarget.add(tree);
        }
        return JSONArray.toJSONString(listtarget);
    }

    /**
     * @return
     * @description 登录日志表头
     * @author Fkw
     * @date 2019/9/23
     */
    @Override
    public String loginLogColumns() {
        //传递回前端的JSON数据字符串
        String columnsJson = "";

        //暂时采用固定方式,待开发平台完成后,进行修改
        List<Column> columns = new ArrayList<Column>();
        Column column = null;

        column = new Column();
        column.setDataIndex("loginId");
        column.setText("登录ID");
        column.setXtype("gridcolumnview");
        column.setHidden(true);
        column.setFlex(0);
        column.setWidth(100);
        columns.add(column);

        column = new Column();
        column.setDataIndex("username");
        column.setText("用户名");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(120);
        columns.add(column);

        column = new Column();
        column.setDataIndex("realname");
        column.setText("真实名");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(200);
        columns.add(column);

        column = new Column();
        column.setDataIndex("unitfullname");
        column.setText("单位全称");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(250);
        columns.add(column);

        column = new Column();
        column.setDataIndex("ipaddress");
        column.setText("ip地址");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(120);
        columns.add(column);

        column = new Column();
        column.setDataIndex("dotime");
        column.setText("操作时间");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(180);
        columns.add(column);

        column = new Column();
        column.setDataIndex("dodesc");
        column.setText("操作描述");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(300);
        columns.add(column);

        column = new Column();
        column.setDataIndex("functionname");
        column.setText("平台系统名");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(200);
        columns.add(column);

        column = new Column();
        column.setDataIndex("isnormal");
        column.setText("操作性质");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(100);
        columns.add(column);

        column = new Column();
        column.setDataIndex("unitid");
        column.setText("单位id");
        column.setXtype("gridcolumnview");
        column.setHidden(true);
        column.setFlex(0);
        column.setWidth(100);
        columns.add(column);

        ColumnJson columnjson = new ColumnJson();
        columnjson.setColumns(columns);
        columnsJson = JSONObject.toJSONString(columnjson);

        return columnsJson;


    }

    /**
     * 获取安全日志
     *
     * @return
     * @description
     * @author Fkw
     * @date 2019/9/23
     */
    @Override
    public String safeLogColumns() {
        String columnsJson = "";

        //暂时采用固定方式,待开发平台完成后,进行修改
        List<Column> columns = new ArrayList<Column>();
        Column column = null;

        column = new Column();
        column.setDataIndex("safeid");
        column.setText("ID");
        column.setXtype("gridcolumnview");
        column.setHidden(true);
        column.setFlex(0);
        column.setWidth(100);
        columns.add(column);

        column = new Column();
        column.setDataIndex("username");
        column.setText("用户名");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(120);
        columns.add(column);

        column = new Column();
        column.setDataIndex("realname");
        column.setText("真实名");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(180);
        columns.add(column);


        column = new Column();
        column.setDataIndex("ipaddress");
        column.setText("ip地址");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(120);
        columns.add(column);

        column = new Column();
        column.setDataIndex("dotime");
        column.setText("操作时间");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(180);
        columns.add(column);

        column = new Column();
        column.setDataIndex("dodesc");
        column.setText("操作描述");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(250);
        columns.add(column);

        column = new Column();
        column.setDataIndex("functionname");
        column.setText("操作");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(180);
        columns.add(column);

        column = new Column();
        column.setDataIndex("isnormal");
        column.setText("操作情况");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(200);
        columns.add(column);

        column = new Column();
        column.setDataIndex("position");
        column.setText("位置");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(200);
        columns.add(column);

        ColumnJson columnjson = new ColumnJson();
        columnjson.setColumns(columns);
        columnsJson = JSONObject.toJSONString(columnjson);

        return columnsJson;
    }
}
