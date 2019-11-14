package com.titansoft.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.entity.system.Sort;
import com.titansoft.mapper.SortMapper;
import com.titansoft.model.*;
import com.titansoft.service.SortService;
import com.titansoft.utils.util.extjs.FormUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: Kevin
 * @Date: 2019/9/12 9:32
 */
@Service
public class SortServiceImpl implements SortService {
    @Autowired
    SortMapper sortMapper;

    @Override
    public String sortTableColumns() {
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
        column.setDataIndex("sortcode");
        column.setText("分类号");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(100);
        columns.add(column);

        column = new Column();
        column.setDataIndex("sortname");
        column.setText("分类名");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(320);
        columns.add(column);

        column = new Column();
        column.setDataIndex("sortfullname");
        column.setText("分类全名");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(320);
        columns.add(column);

        column = new Column();
        column.setDataIndex("blankline");
        column.setText("预留目录行数");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(100);
        columns.add(column);

        ColumnJson columnjson = new ColumnJson();
        columnjson.setColumns(columns);
        columnsJson = JSONObject.toJSONString(columnjson);
        return columnsJson;
    }

    /**
     * @param treeid
     * @return
     * @description 获取分类树
     * @author Fkw
     * @date 2019/9/12
     */
    @Override
    public String sortTree(String treeid) {
        List listtarget = new ArrayList();
        String sql = "";
        if ("0".equals(treeid)) {
            sql = "select * from t_s_sort where parentid is null order by  CAST(sequence AS DECIMAL)";
            List<Sort> list = sortMapper.selectList(new QueryWrapper<Sort>().isNull("parentid").orderByAsc("sequence"));
            for (Iterator it = list.iterator(); it.hasNext(); ) {
                Sort sort = (Sort) it.next();
                Exttreenot tree = new Exttreenot();
                tree.setId(sort.getId());
                tree.setText(sort.getSortname());
                tree.setUrl(sort.getSortcode());
                tree.setLeaf(Boolean
                        .parseBoolean(sort.getIsleaf().toString() == "" ? "true" : sort.getIsleaf().toString()));
                listtarget.add(tree);
            }

        } else {
            List<Sort> list = sortMapper.selectList(new QueryWrapper<Sort>().eq("parentid", treeid).orderByAsc("sequence"));
            for (Iterator it = list.iterator(); it.hasNext(); ) {
                Sort sort = (Sort) it.next();
                Exttreenot tree = new Exttreenot();
                tree.setId(sort.getId());
                tree.setText(sort.getSortname());
                tree.setUrl(sort.getSortcode());
                tree.setLeaf(Boolean
                        .parseBoolean(sort.getIsleaf().toString() == "" ? "true" : sort.getIsleaf().toString()));
                listtarget.add(tree);
            }
        }
        return JSONObject.toJSONString(listtarget);
    }

    /**
     * @param operate
     * @param sort    :
     * @return
     * @description 获取表单
     * @author Fkw
     * @date 2019/9/21
     */
    @Override
    public String sortForm(String operate, Sort sort) {
        String formJson = "";
        // 构造表单数据 start
        if ("add".equals(operate)) {
            String maxSorcode = getMaxSortcode(sort.getId());
            sort.setSortcode(maxSorcode);
        }
        ArrayList list = new ArrayList();
        VO vo = new VO();
        vo.put("fieldname", "分类号");
        vo.put("fieldcode", "sortcode");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("mustinput", "true");
        vo.put("iswhieline", "true");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "分类名称");
        vo.put("fieldcode", "sortname");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("mustinput", "true");
        vo.put("iswhieline", "true");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "分类全称");
        vo.put("fieldcode", "sortfullname");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("mustinput", "true");
        vo.put("iswhieline", "true");
        list.add(vo);


        vo = new VO();
        vo.put("fieldname", "预留目录行数");
        vo.put("fieldcode", "blankline");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("mustinput", "true");
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
        xsdObject.setDefaultvalue(sort.getId());
        hiddens.add(xsdObject);
        // 追加hidden域 end
        String json = "";

        return FormUtil.NotButtonConvertJSON(list, sort, hiddens, operate);
    }

    //获取最大分类号
    private String getMaxSortcode(String id) {
        String maxid = "";
        List list = null;
        if ("0".equals(id)) {
            list = sortMapper.selectMaps(new QueryWrapper<Sort>().select(" max(sequence) as max").isNull("parentid"));//this.basedao.queryhashmap("select max(convert(int,SORTCODE)) as sortcode from "+Sort.getTablenameofpo()+" where parentid is null");
            if (list.size() > 0) {
                Map m = (Map) list.get(0);
                Integer sortcode = (Integer) m.get("max");
                if (0 == sortcode) {
                    maxid = "1";
                } else {
                    maxid = (sortcode + 1) + "";
                }
            }
        } else {
            list = sortMapper.selectMaps(new QueryWrapper<Sort>().select(" max(sequence) as max").eq("parentid", id));
            if (list.size() > 0) {
                Map m = (Map) list.get(0);
                Integer sortcode = (Integer) m.get("max");
                List sortlist = sortMapper.selectList(new QueryWrapper<Sort>().select("sortcode").eq("id", id));
                String parentcode = "";
                if (sortlist.size() > 0) {
                    Sort sort = (Sort) sortlist.get(0);
                    parentcode = sort.getSortcode();
                }
                if (0 == sortcode) {
                    maxid = parentcode + "-1";
                } else {
                    maxid = parentcode + "-" + (sortcode + 1);
                }
            }
        }
        return maxid;
    }

    /**
     * @param sort
     * @param logininfo :
     * @return
     * @description 增加分类
     * @author Fkw
     * @date 2019/9/21
     */
    @Override
    public String addsort(Sort sort, Logininfo logininfo) {
        return null;
    }

    /**
     * @param sort
     * @param logininfo :
     * @return
     * @description
     * @author Fkw
     * @date 2019/9/21
     */
    @Override
    public String editSort(Sort sort, Logininfo logininfo) {
        return null;
    }

    /**
     * @param id
     * @return
     * @description 删除分类
     * @author Fkw
     * @date 2019/9/21
     */
    @Override
    public void delSort(String id) {

    }

    /**
     * @param sort
     * @return
     * @description 校验分类最多只能有二级分类
     * @author Fkw
     * @date 2019/9/21
     */
    @Override
    public Boolean checkAdd(Sort sort) {
        Boolean bool = false;

        if ("0".equals(sort.getId())) {
            bool = true;
        } else {
            Sort sortdb = sortMapper.selectOne(new QueryWrapper<Sort>().select("parentid").eq("id", sort.getId()));
            if (sortdb == null) {
                bool = true;
            }
        }
        return bool;
    }

    /**
     * @param parentid
     * @return
     * @description 获取排序页面
     * @author Fkw
     * @date 2019/9/21
     */
    @Override
    public String getSort(String parentid) {
        List list = null;
        String json = null;
        if (parentid == null || "0".equals(parentid) || "root".equals(parentid)) {
            list = sortMapper.selectList(new QueryWrapper<Sort>().select("id", "sortname", "sequence").isNull("parentid").orderByAsc("sequence"));

        } else {
            list = sortMapper.selectList(new QueryWrapper<Sort>().select("id", "sortname", "sequence").eq("parentid", parentid).orderByAsc("sequence"));

        }
        // 当数据集合不为空时,进行遍历,转换成tree对象
        if (list != null && !list.isEmpty()) {
            // 获取迭代器
            Iterator it = list.iterator();
            // 中间变量
            Listbox listbox = null;
            Sort sort = null;
            // 功能树数组
            List<Listbox> listboxs = new ArrayList<Listbox>();
            while (it.hasNext()) {
                sort = (Sort) it.next();
                listbox = new Listbox();
                listbox.setId(sort.getId() + "");
                listbox.setText(sort.getSortname() + "");
                listboxs.add(listbox);
            }
            sort = null;
            listbox = null;
            it = null;
            list = listboxs;
        }
        json = JSONObject.toJSONString(list);
        return json;
    }

    /**
     * @param ids
     * @return
     * @description 保存排序
     * @author Fkw
     * @date 2019/9/21
     */
    @Transactional
    @Override
    public void saveSort(String ids) {
        String id[] = ids.split(",");
        for (int i = 0; i < id.length; i++) {
            Sort sort=new Sort();
            sort.setId(id[0]);
            sort.setSequence((i+1));
            sortMapper.updateById(sort);
        }
    }
}
