package com.titansoft.utils.mybatis.customizeSql;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.titansoft.entity.view.UnitView;
import com.titansoft.utils.util.CommonUtil;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/10/19 15:51
 */
public class GetAllUnit extends AbstractMethod {
    /**
     * 注入自定义 MappedStatement
     *
     * @param mapperClass mapper 接口
     * @param modelClass  mapper 泛型
     * @param tableInfo   数据库表反射信息
     * @return MappedStatement
     */
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql="select id ,text,PARENTID from "+ CommonUtil.getTableName(UnitView.class) +"  group by id,levelord, text,parentid order by parentid ,levelord";
        //在mapper接口的方法名
        String method="getAllUnit";
        SqlSource sqlSource=languageDriver.createSqlSource(configuration,sql,modelClass);
        return addSelectMappedStatementForOther(mapperClass,method,sqlSource, Map.class);
    }
}
