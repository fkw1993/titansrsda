package com.titansoft.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.titansoft.entity.Notice;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.mapper.NoticeMapper;
import com.titansoft.model.Column;
import com.titansoft.model.ColumnJson;
import com.titansoft.model.VO;
import com.titansoft.model.XsdObject;
import com.titansoft.service.NoticeService;
import com.titansoft.utils.util.CommonUtil;
import com.titansoft.utils.util.DateUtil;
import com.titansoft.utils.util.extjs.FormUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: Kevin
 * @Date: 2019/9/23 11:42
 */
@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    NoticeMapper noticeMapper;

    /**
     * @return
     * @description 表头
     * @author Fkw
     * @date 2019/9/23
     */
    @Override
    public String noticeTableColumns() {
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
        column.setDataIndex("status");
        column.setText("发布状态");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(150);
        columns.add(column);

        column = new Column();
        column.setDataIndex("headline");
        column.setText("标题");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(180);
        columns.add(column);

        column = new Column();
        column.setDataIndex("publishuser");
        column.setText("发布人");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(150);
        columns.add(column);

        column = new Column();
        column.setDataIndex("publishdate");
        column.setText("发布时间");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(150);
        columns.add(column);

        column = new Column();
        column.setDataIndex("expirationtime");
        column.setText("过期时间");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(150);
        columns.add(column);

        column = new Column();
        column.setDataIndex("createuser");
        column.setText("创建人");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(150);
        columns.add(column);

        column = new Column();
        column.setDataIndex("createdatetime");
        column.setText("创建时间");
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
     * @param notice  :
     * @return
     * @description 表单
     * @author Fkw
     * @date 2019/9/23
     */
    @Override
    public String noticeForm(String operate, Notice notice) {
        //构造表单数据 start
        ArrayList list = new ArrayList();
        VO vo = new VO();

        vo = new VO();
        vo.put("fieldname", "标题");
        vo.put("fieldcode", "headline");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("iswhieline", "true");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "内容");
        vo.put("fieldcode", "content");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 300);
        vo.put("display", "true");
        vo.put("iswhieline", "true");
        vo.put("textrow", "12");
        vo.put("controltype", "htmledit");
        list.add(vo);


        vo = new VO();
        vo.put("fieldname", "过期日期");
        vo.put("fieldcode", "expirationtime");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 100);
        vo.put("display", "true"); // 是否进行隐藏
        vo.put("iswhieline", "true");
        list.add(vo);

        //构造表单数据 end
        //////////取得查看值 start
        HashMap hashMap = null;
        //////////取得查看值 end

        XsdObject xsdObject;
        ArrayList hiddens = new ArrayList();
        xsdObject = new XsdObject();
        xsdObject.setFieldName("id");
        xsdObject.setDefaultvalue(notice.getId());
        hiddens.add(xsdObject);
        //追加hidden域  end

        return FormUtil.NotButtonConvertJSON(list, notice, hiddens, operate);
    }


    /**
     * @param *          @param notice:
     * @param logininfo:
     * @return
     * @description 增加公告
     * @author Fkw
     * @date 2019/9/23
     */
    @Override
    public void addNotice(Notice notice, Logininfo logininfo) {
        String username = logininfo.getRealname();
        // 设定创建人
        notice.setCreateuser(username);
        // 创建时间
        notice.setCreatedatetime(DateUtil.getTime(new Date()));
        //创建部门
        notice.setCreatedepartment(logininfo.getUnitname());
        // 公告状态
        notice.setStatus("未发布");
        notice.setId(CommonUtil.getGuid());
        noticeMapper.insert(notice);
    }

    /**
     * @param notice
     * @param logininfo :
     * @return
     * @description 修改公告
     * @author Fkw
     * @date 2019/9/23
     */
    @Transactional
    @Override
    public String editNotice(Notice notice, Logininfo logininfo) {
        notice.setUpdateuser(logininfo.getUsername());
        notice.setUpdatedatetime(DateUtil.getTime(new Date()));
        noticeMapper.updateById(notice);
        return "";
    }

    /**
     * @param notice
     * @return
     * @description 删除公告
     * @author Fkw
     * @date 2019/9/23
     */
    @Transactional
    @Override
    public void delNotice(Notice notice) {
        noticeMapper.deleteById(notice.getId());
    }
}
