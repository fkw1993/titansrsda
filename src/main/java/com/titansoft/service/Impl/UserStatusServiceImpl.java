package com.titansoft.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.titansoft.entity.cadre.Cadre;
import com.titansoft.entity.UserStatus;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.mapper.CadreMapper;
import com.titansoft.mapper.UserStatusMapper;
import com.titansoft.model.*;
import com.titansoft.service.UserStatusService;
import com.titansoft.utils.util.CommonUtil;
import com.titansoft.utils.util.extjs.FormUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: Kevin
 * @Date: 2019/9/12 14:48
 */
@Service
public class UserStatusServiceImpl implements UserStatusService {
    @Autowired
    UserStatusMapper userStatusMapper;
    @Autowired
    CadreMapper cadreMapper;

    /**
     * @return
     * @description 获取表头
     * @author Fkw
     * @date 2019/9/12
     */
    @Override
    public String userStatusTableColumns() {
        String columnsJson = "";
        // 暂时采用固定方式,待开发平台完成后,进行修改
        List<Column> columns = new ArrayList<Column>();
        Column column = null;

        column = new Column();
        column.setDataIndex("id");
        column.setText("id");
        column.setXtype("gridcolumnview");
        column.setHidden(true);
        column.setFlex(0);
        column.setWidth(100);
        columns.add(column);

        column = new Column();
        column.setDataIndex("name");
        column.setText("名称");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(150);
        columns.add(column);

        column = new Column();
        column.setDataIndex("fullname");
        column.setText("全称");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(180);
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
     * @return
     * @description 树结构
     * @author Fkw
     * @date 2019/9/12
     */
    @Override
    public String userStatusTree() {
        Exttreenot node = null;
        String treeJson = "";
        List<Exttreenot> treelist = new ArrayList<Exttreenot>();
        List statusList = userStatusMapper.selectList(new QueryWrapper<UserStatus>().orderByAsc("sequence"));
        if (statusList != null && !statusList.isEmpty()) {
            //获取迭代器
            Iterator it = statusList.iterator();
            //中间变量
            UserStatus userStatus = null;
            //结果树数组
            while (it.hasNext()) {
                userStatus = (UserStatus) it.next();
                node = new Exttreenot();
                node.setId(userStatus.getId());
                node.setText(userStatus.getName());
                node.setLeaf(true);
                treelist.add(node);
            }
        }
        return JSONObject.toJSONString(treelist);
    }

    /**
     * @param operate
     * @param userstatus :
     * @return
     * @description 用户状态表单
     * @author Fkw
     * @date 2019/9/21
     */
    @Override
    public String userStatusForm(String operate, UserStatus userstatus) {
        // 传递回前端的JSON数据字符串
        String formJson = "";
        // 构造表单数据 start

        ArrayList list = new ArrayList();
        VO vo = new VO();
        vo.put("fieldname", "名称");
        vo.put("fieldcode", "name");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "全称");
        vo.put("fieldcode", "fullname");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("mustinput", "false");
        vo.put("iswhieline", "false");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "描述");
        vo.put("fieldcode", "description");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("mustinput", "false");
        vo.put("iswhieline", "true");
        list.add(vo);

        // 构造表单数据 end

        ////////// 取得查看值 start
        HashMap hashMap = null;
        ////////// 取得查看值 end

        // ///////////////////////////////////////////////// 生成录入表单界面 end
        // 连续录入增加条目信息 end
        // 动态表单产生且已赋值 start
        // 追加hidden域 start
        XsdObject xsdObject;
        ArrayList hiddens = new ArrayList();
        xsdObject = new XsdObject();
        xsdObject.setFieldName("id");
        xsdObject.setDefaultvalue(userstatus.getId());
        hiddens.add(xsdObject);
        return FormUtil.NotButtonConvertJSON(list, userstatus, hiddens, operate);
    }

    /**
     * @param userstatus
     * @param logininfo  :
     * @return
     * @description 增加用户状态
     * @author Fkw
     * @date 2019/9/21
     */
    @Override
    public String addUserStatus(UserStatus userstatus, Logininfo logininfo) {
        String id = CommonUtil.getGuid();
        userstatus.setId(id);
        List list = userStatusMapper.selectMaps(new QueryWrapper<UserStatus>().select("max(sequence) as seq"));
        Integer maxseq = (Integer) ((Map) list.get(0)).get("seq");
        userstatus.setSequence(maxseq + 1);
        userStatusMapper.insert(userstatus);
        return "";
    }

    /**
     * @param userstatus
     * @param logininfo  :
     * @return
     * @description 修改用户状态
     * @author Fkw
     * @date 2019/9/21
     */
    @Override
    public String editUserstatus(UserStatus userstatus, Logininfo logininfo) {
        userStatusMapper.updateById(userstatus);
        return "";
    }

    /**
     * @param id
     * @return
     * @description 删除用户状态
     * @author Fkw
     * @date 2019/9/21
     */
    @Override
    public String delUserstatus(String id) {
        int count = 0;
        count = cadreMapper.selectCount(new QueryWrapper<Cadre>().eq("cadrestatus", id));
        if (count > 0) {
            return "该干部状态下还有数据，无法删除";
        } else {
            userStatusMapper.deleteById(id);
            return "";
        }
    }
}
