package com.titansoft.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.titansoft.entity.Dictionary;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.mapper.DictionaryMapper;
import com.titansoft.model.*;
import com.titansoft.service.DictionaryService;
import com.titansoft.utils.util.CommonUtil;
import com.titansoft.utils.util.extjs.FormUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 数据字典实现类
 *
 * @Author: Kevin
 * @Date: 2019/7/27 16:14
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {
    @Autowired
    DictionaryMapper dictionaryMapper;

    /**
     * @param
     * @return
     * @description 数据字典加载到内存
     * @author Fkw
     * @date 2019/7/27
     */
    @Override
    public Map<String, List> getDictionaryList() {
        Map<String, List> map = new HashMap<String, List>();
        List<Dictionary> parentlist = dictionaryMapper.selectList(new QueryWrapper<Dictionary>().isNull("parentid"));
        for (int i = 0; i < parentlist.size(); i++) {
            Dictionary dictionary = (Dictionary) parentlist.get(i);
            List dictionarylist = dictionaryMapper.selectList(new QueryWrapper<Dictionary>().eq("parentid", dictionary.getId()));
            map.put(dictionary.getCode(), dictionarylist);
        }
        return map;
    }

    /**
     * @return
     * @description 数据字典树
     * @author Fkw
     * @date 2019/9/21
     */
    @Override
    public String dictionaryTree(String treeid) {
        List listtarget = new ArrayList();
        if ("0".equals(treeid)) {
            List list = dictionaryMapper.selectList(new QueryWrapper<Dictionary>().isNull("parentid").orderByAsc("sequence"));
            for (Iterator it = list.iterator(); it.hasNext(); ) {
                Dictionary dictionary = (Dictionary) it.next();
                Exttreenot tree = new Exttreenot();
                tree.setId((String) dictionary.getId());
                tree.setText((String) dictionary.getName());
                tree.setUrl((String) dictionary.getCode());
                tree.setLeaf(true);
                listtarget.add(tree);
            }
        }
        return JSONObject.toJSONString(listtarget);
    }

    /**
     * @return
     * @description 数据字典表头
     * @author Fkw
     * @date 2019/9/21
     */
    @Override
    public String dictionaryTableColumns() {
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
        column.setDataIndex("code");
        column.setText("代号");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(180);
        columns.add(column);

        column = new Column();
        column.setDataIndex("name");
        column.setText("名称");
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
     * @param dictionary
     * @return
     * @description 数据字典表格数据
     * @author Fkw
     * @date 2019/9/21
     */
    @Override
    public String dictionaryForm(String operate, Dictionary dictionary) {
        ArrayList list = new ArrayList();
        VO vo = new VO();
        vo.put("fieldname", "代号");
        vo.put("fieldcode", "code");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("mustinput", "true");
        vo.put("iswhieline", "true");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "名称");
        vo.put("fieldcode", "name");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("mustinput", "true");
        vo.put("iswhieline", "true");
        list.add(vo);
        HashMap hashMap = null;
        XsdObject xsdObject;
        ArrayList hiddens = new ArrayList();
        xsdObject = new XsdObject();
        xsdObject.setFieldName("id");
        xsdObject.setDefaultvalue(dictionary.getId());
        hiddens.add(xsdObject);
        return FormUtil.NotButtonConvertJSON(list, dictionary, hiddens, operate);
    }

    /**
     * @param dictionary
     * @param logininfo  :
     * @return
     * @description 增加数据字典
     * @author Fkw
     * @date 2019/9/21
     */
    @Override
    public String addDictionary(Dictionary dictionary, Logininfo logininfo) {
        String id = CommonUtil.getGuid();
        if (!"0".equals(dictionary.getParentid()) && dictionary.getParentid() != null) {
            //二级分类
            dictionary.setParentid(dictionary.getId());
            List list = dictionaryMapper.selectMaps(new QueryWrapper<Dictionary>().select("max(sequence) as sequence").eq("parentid", dictionary.getParentid()));
            if (list.size() > 0) {
                Map m = (Map) list.get(0);
                Integer max = (Integer) m.get("sequence");
                if (max == 0 || max == null) {
                    dictionary.setSequence(1);
                } else {
                    dictionary.setSequence(max + 1);
                }
            }
        } else {
            //一级分类
            //排序
            List list = dictionaryMapper.selectMaps(new QueryWrapper<Dictionary>().select("max(sequence) as sequence").isNull("parentid"));
            if (list.size() > 0) {
                Map m = (Map) list.get(0);
                Integer max = (Integer) m.get("sequence");
                if (max == 0 || max == null) {
                    dictionary.setSequence(1);
                } else {
                    dictionary.setSequence(max + 1);
                }
            }
        }
        dictionary.setId(id);
        dictionaryMapper.insert(dictionary);
        return "";
    }

    /**
     * @param dictionary
     * @param logininfo  :
     * @return
     * @description 修改数据字典
     * @author Fkw
     * @date 2019/9/21
     */
    @Transactional
    @Override
    public String editDictionary(Dictionary dictionary, Logininfo logininfo) {
        dictionaryMapper.updateById(dictionary);
        return "";
    }

    /**
     * @param ids
     * @return
     * @description 删除数据字典
     * @author Fkw
     * @date 2019/9/21
     */
    @Transactional
    @Override
    public String delDictionary(String ids) {
        String[] id = ids.split(",");
        for (int i = 0; i < id.length; i++) {
            List list = dictionaryMapper.selectList(new QueryWrapper<Dictionary>().eq("parentid", id[i]));
            if (list.size() > 0) {
                return "请先删除子项！";
            }
        }
        Dictionary po = dictionaryMapper.selectById(id[0]);//(Dictionary) this.basedao.findPO(Dictionary.getTablenameofpo(), Dictionary.class,  datapo.getId());
        dictionaryMapper.delete(new UpdateWrapper<Dictionary>().inSql("id", CommonUtil.getIDstr(ids)));
        //删除分类后刷新序号
        List list = null;
        if ("".equals(po.getParentid()) || po.getParentid() == null) {
            list = dictionaryMapper.selectList(new QueryWrapper<Dictionary>().isNull("parentid").orderByAsc("sequence"));

        } else {
            list = dictionaryMapper.selectList(new QueryWrapper<Dictionary>().eq("parentid", po.getParentid()).orderByAsc("sequence"));
        }
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Dictionary s = (Dictionary) list.get(i);
                s.setSequence(i + 1);
                dictionaryMapper.updateById(s);
            }
        }
        return "";
    }

    @Override
    public Boolean checkAdd(Dictionary datapo) {
        Boolean bool = false;
        if ("0".equals(datapo.getId())) {
            bool = true;
        } else {
            List list = dictionaryMapper.selectList(new QueryWrapper<Dictionary>().select("parentid").eq("id", datapo.getId()));
            if (list.size() > 0) {
                Dictionary dictionary = (Dictionary) list.get(0);
                String parentid = dictionary.getParentid();
                if ("".equals(parentid) || parentid == null) {
                    bool = true;
                }
            } else {
                bool = true;
            }
        }
        return bool;
    }

    /**
     * @param parentid
     * @return
     * @description 获取排序数据
     * @author Fkw
     * @date 2019/9/21
     */
    @Override
    public String getSort(String parentid) {
        String json = "";
        // 查询结果集
        List list = null;
        if (parentid == null || "0".equals(parentid)) {
            list = (List) dictionaryMapper.selectList(new QueryWrapper<Dictionary>().isNull("parentid").orderByAsc("sequence"));// ("select distinct * from v_Privilege where parentid " + (privilege.getParentid() == null ? "is null" : ("= '" + privilege.getParentid() + "'")) + " and productid='" + privilege.getProductid() + "' order by levelord");
        } else {
            list = (List) dictionaryMapper.selectList(new QueryWrapper<Dictionary>().eq("parentid", parentid).orderByAsc("sequence"));// ("select distinct * from v_Privilege where parentid " + (privilege.getParentid() == null ? "is null" : ("= '" + privilege.getParentid() + "'")) + " and productid='" + privilege.getProductid() + "' order by levelord");
        }
        // 当数据集合不为空时,进行遍历,转换成tree对象
        if (list != null && !list.isEmpty()) {
            // 获取迭代器
            Iterator it = list.iterator();
            // 中间变量
            Listbox listbox = null;
            Dictionary dictionary = null;
            // 功能树数组
            List<Listbox> listboxs = new ArrayList<Listbox>();
            while (it.hasNext()) {
                dictionary = (Dictionary) it.next();
                listbox = new Listbox();
                listbox.setId(dictionary.getId() + "");
                listbox.setText(dictionary.getName() + "");
                listboxs.add(listbox);
            }
            dictionary = null;
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
            Dictionary dictionary = new Dictionary();
            dictionary.setId(id[0]);
            dictionary.setSequence((i + 1));
            dictionaryMapper.updateById(dictionary);
        }
    }
}
