package com.titansoft.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.titansoft.entity.Organ;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.mapper.OrganMapper;
import com.titansoft.model.*;
import com.titansoft.entity.view.PrivilegeView;
import com.titansoft.service.OrganService;
import com.titansoft.utils.database.SqlFilter;
import com.titansoft.utils.util.CommonUtil;
import com.titansoft.utils.util.DateUtil;
import com.titansoft.utils.util.extjs.FormUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: Kevin
 * @Date: 2019/9/11 15:55
 */
@Service
public class OrganServiceImpl implements OrganService {
    @Autowired
    OrganMapper organMapper;

    /**
     * @param organid
     * @return
     * @description 加载用户组织树
     * @author Fkw
     * @date 2019/9/11
     */
    @Override
    public String organTree(String organid) {
        String treeJson = "";
        List organlist = null;
        List subOrganList = null;
        if ("root".equals(organid)) {
            organlist = organMapper.selectList(new QueryWrapper<Organ>().eq("parentid", "").or().isNull("parentid").orderByAsc("levelord"));
        } else {
            organlist = organMapper.selectList(new QueryWrapper<Organ>().eq("parentid", organid).orderByAsc("levelord"));

        }
        if (organlist != null && !organlist.isEmpty()) {
            //获取迭代器
            Iterator it = organlist.iterator();
            //中间变量
            Tree tree = null;
            Organ organ = null;
            //结果树数组
            List<Tree> trees = new ArrayList<Tree>();
            while (it.hasNext()) {
                organ = (Organ) it.next();
                tree = new Tree();
                tree.setId(organ.getOrganid());
                tree.setText(organ.getName());
                //为叶子节点时
                subOrganList = getSubOrgan(String.valueOf(organ.getOrganid()));
                if (subOrganList != null && !subOrganList.isEmpty()) {
                    tree.setChildren(null);
                } else {
                    tree.setChildren(Tree.isChild);
                    tree.setLeaf(true);
                }
                trees.add(tree);
            }
            treeJson = JSONObject.toJSONString(trees);
            tree = null;
            it = null;
        }
        return treeJson;
    }

    /**
     * @return
     * @description 获取表头
     * @author Fkw
     * @date 2019/9/11
     */
    @Override
    public String organTableColumns() {
        //传递回前端的JSON数据字符串
        String columnsJson = "";
        try {
            try {
                //暂时采用固定方式,待开发平台完成后,进行修改
                List<Column> columns = new ArrayList<Column>();
                Column column = null;
                column = new Column();
                column.setDataIndex("organid");
                column.setText("用户组织ID");
                column.setXtype("gridcolumnview");
                column.setHidden(true);
                column.setFlex(0);
                column.setWidth(100);
                columns.add(column);

                column = new Column();
                column.setDataIndex("code");
                column.setText("组织代码");
                column.setXtype("gridcolumnview");
                column.setFlex(0);
                column.setWidth(180);
                columns.add(column);

                column = new Column();
                column.setDataIndex("name");
                column.setText("组织名称");
                column.setXtype("gridcolumnview");
                column.setFlex(0);
                column.setWidth(300);
                columns.add(column);

                column = new Column();
                column.setDataIndex("description");
                column.setText("描述");
                column.setXtype("gridcolumnview");
                column.setFlex(0);
                column.setWidth(360);
                columns.add(column);

                column = new Column();
                column.setDataIndex("isshow");
                column.setText("是否显示");
                column.setXtype("gridcolumnview");
                column.setFlex(0);
                column.setWidth(100);
                columns.add(column);

                ColumnJson columnjson = new ColumnJson();
                columnjson.setColumns(columns);
                columnsJson = JSONObject.toJSONString(columnjson);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return columnsJson;

    }

    /**
     * @param operate
     * @param organ   :
     * @return
     * @description 机构表单
     * @author Fkw
     * @date 2019/9/18
     */
    @Override
    public String organForm(String operate, Organ organ) {
        String formJson = "";

        //构造表单数据 start
        ArrayList<VO> list = new ArrayList<VO>();
        VO vo = null;
        vo = new VO();
        vo.put("fieldname", "机构代码");
        vo.put("fieldcode", "code");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("iswhieline", "true");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "机构名称");
        vo.put("fieldcode", "name");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("iswhieline", "true");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "是否显示");
        vo.put("fieldcode", "isshow");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("iswhieline", "true");
        String tmp = "是-是|否-否";
        vo.put("controltype", "combox");
        vo.put("comboxdata", tmp);
        vo.put("mustinput", "true");
        vo.put("defaultvalue", "是");
        vo.put("isinputhidden", "false");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "机构描述");
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

        // ///////////////////////////////////////////////// 生成录入表单界面 end
        //连续录入增加条目信息 end
        //动态表单产生且已赋值 start
        //追加hidden域  start
        XsdObject xsdObject;
        ArrayList<XsdObject> hiddens = new ArrayList<XsdObject>();
        xsdObject = new XsdObject();
        xsdObject.setFieldName("organid");
        xsdObject.setDefaultvalue(organ.getOrganid());
        hiddens.add(xsdObject);

        xsdObject = new XsdObject();
        xsdObject.setFieldName("parentid");
        xsdObject.setDefaultvalue(organ.getParentid());
        hiddens.add(xsdObject);

        xsdObject = new XsdObject();
        xsdObject.setFieldName("rootid");
        xsdObject.setDefaultvalue(organ.getRootid());
        hiddens.add(xsdObject);
        //追加hidden域  end
        return FormUtil.NotButtonConvertJSON(list, organ, hiddens, operate);
    }

    /**
     * @param organ
     * @param logininfo
     * @return
     * @description 增加机构
     * @author Fkw
     * @date 2019/9/18
     */
    @Override
    public void addOrgan(Organ organ, Logininfo logininfo) {
        organ.setCreatetime(DateUtil.getTimeNow(new Date()));
        organ.setCreateuser(logininfo.getUsername());
        String organid = CommonUtil.getGuid();
        organ.setOrganid(organid);
        organ.setRootid(organid);
        if ("".equals(organ.getParentid()) || organ.getParentid() == null) {
            organ.setParentid(null);
        }
        organ.setLevelnum(1);
        int count = organMapper.selectCount(new QueryWrapper<Organ>().isNull("parentid"));
        organ.setLevelord(count + 1);
        organMapper.insert(organ);
        organMapper.insertOrganLevel(SqlFilter.CheckSql("'" + organ.getOrganid() + "','" + organ.getRootid() + "','" + organ.getParentid() + "','" + organ.getLevelnum() + "','" + organ.getLevelord() + "'"));
    }

    /**
     * @param organ
     * @return
     * @description 修改机构
     * @author Fkw
     * @date 2019/9/19
     */
    @Override
    public String editOrgan(Organ organ, Logininfo logininfo) {
        List organList = organMapper.selectList(new QueryWrapper<Organ>().select("id").ne("id", organ.getOrganid()).eq("parentid", organ.getParentid()).and(organViewQueryWrapper -> organViewQueryWrapper.eq("code", organ.getCode()).or().eq("name", organ.getName())));
        if (organList.size() > 0) {
            return "您保存的数据可能有重复，如有其他问题请联系管理员！";
        } else {
            organ.setModifytime(DateUtil.getTimeNow(new Date()));
            organ.setModifyuser(logininfo.getUsername());
            organ.setParentid(null);
            organMapper.updateById(organ);
        }
        return null;
    }

    /**
     * @param organid
     * @return
     * @description 删除机构
     * @author Fkw
     * @date 2019/9/19
     */
    @Override
    public void delOrgan(String organid) {
        String[] ids = organid.split(",");
        for (int i = 0; i < ids.length; i++) {
            organMapper.deleteById(ids[i]);
        }
    }

    /**
     * @param organid
     * @return
     * @description 获取子机构
     * @author Fkw
     * @date 2019/9/19
     */
    @Override
    public List checkSubOrgan(String organid) {
        return getSubOrgan(organid);
    }

    /**
     * @param parentid
     * @return
     * @description 获取排序页面
     * @author Fkw
     * @date 2019/9/19
     */
    @Override
    public String getSort(String parentid) {
        List list = null;
        String json = null;
        if (parentid == null || "0".equals(parentid) || "root".equals(parentid)) {
            list = organMapper.selectList(new QueryWrapper<Organ>().select("organid","name","levelord").isNull("parentid").orderByAsc("levelord"));
        } else {
            list = organMapper.selectList(new QueryWrapper<Organ>().select("organid","name","levelord").eq("parentid",parentid).orderByAsc("levelord"));
        }
        // 当数据集合不为空时,进行遍历,转换成tree对象
        if (list != null && !list.isEmpty()) {
            // 获取迭代器
            Iterator it = list.iterator();
            // 中间变量
            Listbox listbox = null;
         Organ organ = null;
            // 功能树数组
            List<Listbox> listboxs = new ArrayList<Listbox>();
            while (it.hasNext()) {
                organ = (Organ) it.next();
                listbox = new Listbox();
                listbox.setId(organ.getOrganid()+"");
                listbox.setText(organ.getName() + "");
                listboxs.add(listbox);
            }
            organ = null;
            listbox = null;
            it = null;
            list = listboxs;
        }
        json=JSONObject.toJSONString(list);
        return json;
    }

    /**
     * @param organids
     * @return
     * @description 保存排序
     * @author Fkw
     * @date 2019/9/19
     */
    @Override
    public void saveSort(String organids) {
        String organid[] = organids.split(",");
        for (int i = 0; i < organid.length; i++) {
            Organ organ = new Organ();
            organ.setOrganid(organid[i]);
            organ.setLevelord(i + 1);
            organMapper.updateById(organ);
        }
    }

    private List getSubOrgan(String id) {
        List organList = null;
        if (id == null) {
            organList = organMapper.selectList(new QueryWrapper<Organ>().isNull("parentid").orderByAsc("levelord"));
        } else {
            organList = organMapper.selectList(new QueryWrapper<Organ>().eq("parentid", id).orderByAsc("levelord"));
        }
        return organList;
    }

}
