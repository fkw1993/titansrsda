package com.titansoft.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.titansoft.entity.media.Store;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.mapper.StoreMapper;
import com.titansoft.model.Column;
import com.titansoft.model.ColumnJson;
import com.titansoft.model.VO;
import com.titansoft.model.XsdObject;
import com.titansoft.service.StoreService;
import com.titansoft.utils.util.CommonUtil;
import com.titansoft.utils.util.DateUtil;
import com.titansoft.utils.util.extjs.FormUtil;
import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Kevin
 * @Date: 2019/9/21 16:29
 */
@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    StoreMapper storeMapper;

    /**
     * @return
     * @description 获取表头
     * @author Fkw
     * @date 2019/9/21
     */
    @Override
    public String storeTableColumns() {
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
        column.setText("编号");
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

        column = new Column();
        column.setDataIndex("location");
        column.setText("存储路径");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(180);
        columns.add(column);

        column = new Column();
        column.setDataIndex("maxsieze");
        column.setText("'最大允许使用空间(GB)");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(180);
        columns.add(column);

        column = new Column();
        column.setDataIndex("unusedbyte");
        column.setText("磁盘剩余空间(GB)");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(180);
        columns.add(column);

        ColumnJson columnjson = new ColumnJson();
        columnjson.setColumns(columns);
        columnsJson = JSONObject.toJSONString(columnjson);
        return columnsJson;
    }

    /**
     * @param store
     * @param logininfo :
     * @return
     * @description 增加存储信息
     * @author Fkw
     * @date 2019/9/21
     */
    @Override
    public String addStore(Store store, Logininfo logininfo) {
        store.setCreatetime(DateUtil.getTimeNow(new Date()));
        store.setCreateuser(logininfo.getRealname());
        Store checkstore = storeMapper.selectOne(new QueryWrapper<Store>().eq("code", store.getCode()));
        if (checkstore == null) {
            String id = CommonUtil.getGuid();
            store.setId(id);
            store.setUnusedbyte(getFreeDiskSpace(store.getLocation().substring(0, store.getLocation().indexOf(":") + 1)) + "");
            storeMapper.insert(store);
            return "";
        } else {
            return "已存在编号相同的数据";
        }
    }

    public static long getFreeDiskSpace(String diskpath) {
        long freeSpaceInKB;
        try {
            freeSpaceInKB = FileSystemUtils.freeSpaceKb(diskpath);
            long freeSpaceInGB = freeSpaceInKB / FileUtils.ONE_MB;
            return freeSpaceInGB;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param operate
     * @param store
     * @return
     * @description 表单
     * @author Fkw
     * @date 2019/9/21
     */
    @Override
    public String storeForm(String operate, Store store) {
        // 构造表单数据 start
        ArrayList<VO> list = new ArrayList<VO>();
        VO vo = null;
        vo = new VO();
        vo.put("fieldname", "编号");
        vo.put("fieldcode", "code");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "名称");
        vo.put("fieldcode", "name");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "最大允许空间（GB）");
        vo.put("fieldcode", "maxsize");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("iswhieline", "true");

        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "存储地址");
        vo.put("fieldcode", "location");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("textrow", 3);
        vo.put("mustinput", "false");
        vo.put("iswhieline", "true");
        vo.put("controltype", "textarea");
        list.add(vo);

        // 构造表单数据 end

        // ///////////////////////////////////////////////// 生成录入表单界面 end
        // 连续录入增加条目信息 end
        // 动态表单产生且已赋值 start
        // 追加hidden域 start
        XsdObject xsdObject;
        ArrayList<XsdObject> hiddens = new ArrayList<XsdObject>();
        xsdObject = new XsdObject();
        xsdObject.setFieldName("id");
        xsdObject.setDefaultvalue(store.getId());
        hiddens.add(xsdObject);
        return FormUtil.NotButtonConvertJSON(list, store, hiddens, operate);
    }

    /**
     * @param store
     * @param logininfo :
     * @return
     * @description 修改存储信息
     * @author Fkw
     * @date 2019/9/21
     */
    @Transactional
    @Override
    public String editStore(Store store, Logininfo logininfo) {
        store.setModifytime(DateUtil.getTimeNow(new Date()));
        store.setModifyuser(logininfo.getUsername());
        storeMapper.updateById(store);
        return "";
    }

    /**
     * @param id
     * @return
     * @description 删除存储信息
     * @author Fkw
     * @date 2019/9/21
     */
    @Override
    public String delStore(String id) {
        String[] ids = id.split(",");
        for (int i = 0; i < ids.length; i++) {
            storeMapper.deleteById(ids[i]);
        }
        return "";
    }
}
