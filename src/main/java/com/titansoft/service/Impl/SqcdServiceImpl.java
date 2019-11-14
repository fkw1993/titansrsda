package com.titansoft.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.titansoft.entity.sqcd.SqcdReason;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.mapper.SqcdreasonMapper;
import com.titansoft.model.Column;
import com.titansoft.model.ColumnJson;
import com.titansoft.model.VO;
import com.titansoft.model.XsdObject;
import com.titansoft.service.SqcdService;
import com.titansoft.utils.util.CommonUtil;
import com.titansoft.utils.util.extjs.FormUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 申请查档实现
 *
 * @Author: Kevin
 * @Date: 2019/9/24 9:21
 */
@Service
public class SqcdServiceImpl implements SqcdService {
    @Autowired
    SqcdreasonMapper sqcdreasonMapper;

    /**
     * @return
     * @description 系统维护查档事由表头
     * @author Fkw
     * @date 2019/9/24
     */
    @Override
    public String sqcdReasonTableColumns() {
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
        column.setDataIndex("reason");
        column.setText("查档事由");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(500);
        columns.add(column);

        ColumnJson columnjson = new ColumnJson();
        columnjson.setColumns(columns);
        columnsJson = JSONObject.toJSONString(columnjson);
        return columnsJson;
    }

    /**
     * @param operate
     * @param sqcdReason :
     * @return
     * @description 系统维护查档事由表单
     * @author Fkw
     * @date 2019/9/24
     */
    @Override
    public String sqcdReasonForm(String operate, SqcdReason sqcdReason) {
        ArrayList list = new ArrayList();
        VO vo = new VO();
        vo.put("fieldname", "查档事由");
        vo.put("fieldcode", "reason");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("mustinput", "true");
        vo.put("iswhieline", "false");
        list.add(vo);

        XsdObject xsdObject;
        ArrayList hiddens = new ArrayList();
        xsdObject = new XsdObject();
        xsdObject.setFieldName("id");
        xsdObject.setDefaultvalue(sqcdReason.getId());
        hiddens.add(xsdObject);
        // 追加hidden域 end
        String json = "";

        return FormUtil.NotButtonConvertJSON(list, sqcdReason, hiddens, operate);
    }

    /**
     * @param sqcdreason
     * @param logininfo  :
     * @return
     * @description 增加查档事由
     * @author Fkw
     * @date 2019/9/24
     */
    @Override
    public void addSqcdReason(SqcdReason sqcdreason, Logininfo logininfo) {
        List maxlist = sqcdreasonMapper.selectMaps(new QueryWrapper<SqcdReason>().select("max(sequence) as max"));
        int max = 0;
        if (maxlist.size() > 0) {
            Map maxmap = (Map) maxlist.get(0);
            String maxString = maxmap.get("max").toString();
            if ("".equals(maxString) || maxString == null) {
                max = 1;
            } else {
                max = Integer.parseInt(maxString) + 1;
            }
        }
        sqcdreason.setId(CommonUtil.getGuid());
        sqcdreason.setSequence(max);
        sqcdreasonMapper.insert(sqcdreason);
    }

    /**
     * @param sqcdreason
     * @param logininfo  :
     * @return
     * @description 修改查档事由
     * @author Fkw
     * @date 2019/9/24
     */
    @Override
    public String editsSqcdReason(SqcdReason sqcdreason, Logininfo logininfo) {
        sqcdreasonMapper.updateById(sqcdreason);
        return "";
    }

    /**
     * @param sqcdreason
     * @return
     * @description 删除查档事由
     * @author Fkw
     * @date 2019/9/24
     */
    @Override
    public void delSqcdeason(SqcdReason sqcdreason) {
        String ids = sqcdreason.getId();
        String[] id = ids.split(",");
        for (int i = 0; i < id.length; i++) {
            sqcdreasonMapper.deleteById(id[i]);
        }
        //重新排序
        List srList = sqcdreasonMapper.selectList(new QueryWrapper<SqcdReason>().orderByAsc("sequence"));
        for (int i = 0; i < srList.size(); i++) {
            SqcdReason srPo = (SqcdReason) srList.get(i);
            srPo.setSequence(i + 1);
            sqcdreasonMapper.updateById(srPo);
        }
    }
}
