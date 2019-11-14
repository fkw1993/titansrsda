package com.titansoft.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.titansoft.entity.system.Config;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.model.Column;
import com.titansoft.model.ColumnJson;
import com.titansoft.model.VO;
import com.titansoft.model.XsdObject;
import com.titansoft.service.ConfigService;
import com.titansoft.utils.config.SystemConfig;
import com.titansoft.utils.util.CommonUtil;
import com.titansoft.utils.util.extjs.FormUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统配置Service实现类
 *
 * @Author: Kevin
 * @Date: 2019/10/18 15:20
 */
@Service
public class ConfigServiceImpl extends BaseServiceImpl implements ConfigService {
    /**
     * @return
     * @description 表头
     * @author Fkw
     * @date 2019/10/18
     */
    @Override
    public String configTableColumns() {
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
        column.setDataIndex("configcode");
        column.setText("配置代码");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(200);
        columns.add(column);

        column = new Column();
        column.setDataIndex("configvalue");
        column.setText("配置值");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(200);
        columns.add(column);

        column = new Column();
        column.setDataIndex("configdesc");
        column.setText("描述");
        column.setXtype("gridcolumnview");
        column.setFlex(0);
        column.setWidth(300);
        columns.add(column);

        ColumnJson columnjson = new ColumnJson();
        columnjson.setColumns(columns);
        columnsJson = JSONObject.toJSONString(columnjson);
        return columnsJson;
    }

    /**
     * @param config
     * @param logininfo :
     * @return
     * @description 增加保存
     * @author Fkw
     * @date 2019/10/18
     */
    @Override
    public String addConfig(Config config, Logininfo logininfo) {
        List<Config> configList = configMapper.selectList(new QueryWrapper<Config>().eq("configcode", config.getConfigcode()));
        if (configList.size() > 0) {
            return "已经存在相同的配置项";
        } else {
            config.setId(CommonUtil.getGuid());
            configMapper.insert(config);
            return "";
        }
    }

    /**
     * @param operate
     * @param config  :
     * @return
     * @description 表单
     * @author Fkw
     * @date 2019/10/18
     */
    @Override
    public String configForm(String operate, Config config) {
        //传递回前端的JSON数据字符串
        //构造表单数据 start
        ArrayList<VO> list = new ArrayList<VO>();
        VO vo = null;
        vo = new VO();
        vo.put("fieldname", "配置代码");
        vo.put("fieldcode", "configcode");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "配置值");
        vo.put("fieldcode", "configvalue");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("iswhieline", "true");
        list.add(vo);

        vo = new VO();
        vo.put("fieldname", "描述");
        vo.put("fieldcode", "configdesc");
        vo.put("fieldtype", "String");
        vo.put("fieldlength", 200);
        vo.put("display", "true");
        vo.put("textrow", 3);
        vo.put("mustinput", "false");
        vo.put("iswhieline", "true");
        vo.put("controltype", "textarea");
        list.add(vo);
        //构造表单数据 end

        //追加hidden域  start
        XsdObject xsdObject;
        ArrayList<XsdObject> hiddens = new ArrayList<XsdObject>();
        xsdObject = new XsdObject();
        xsdObject.setFieldName("id");
        xsdObject.setDefaultvalue(config.getId());
        hiddens.add(xsdObject);

        //追加hidden域  end

        return FormUtil.NotButtonConvertJSON(list, config, hiddens, operate);
    }

    /**
     * @param config
     * @param logininfo :
     * @return
     * @description 保存修改
     * @author Fkw
     * @date 2019/10/18
     */
    @Transactional
    @Override
    public String editConfig(Config config, Logininfo logininfo) {
        configMapper.updateById(config);
        List<Config> configList=configMapper.selectList(null);
        //重新加载所有配置项
        if (configList.size() > 0) {
            SystemConfig.configMap.clear();
            for (Config configs : configList) {
                SystemConfig.configMap.put(configs.getConfigcode(), configs.getConfigvalue());
            }
        }
        return "";
    }

    /**
     * @param id@return
     * @description 删除
     * @author Fkw
     * @date 2019/10/18
     */
    @Transactional
    @Override
    public void delConfig(String id) {
        String[] idarry = id.split(",");
        if (idarry.length > 0) {
            for (int i = 0; i < idarry.length; i++) {
                configMapper.deleteById(idarry[i]);
            }
        }
    }
}
