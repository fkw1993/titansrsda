package com.titansoft.utils.mybatis.customizeSql;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.fr.script.function.CODE;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**自定义sql
 * @Author: Kevin
 * @Date: 2019/10/18 16:51
 */
public class GetPowerByUserId extends AbstractMethod {
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
        String sql= "select  fm06 text, max(checked) checked,orders, null checked2,privilegeid id,productid,parentid from (select x.parentid parentid,x.privilegeid,x.productid,x.text fm06,x.levelord orders, rx.privilegeid checked, null checked2 from ${vprivilegetable} x  left join ${uptable} rx on rx.userid in (#{userid}) and rx.privilegeid = x.PRIVILEGEID where  ${hiddenfilter}) t  where productid=#{productid} group by  fm06,privilegeid,productid,parentid,orders order by parentid,orders";

        //在mapper接口的方法名
        String method="getPowerByUserId";
        SqlSource sqlSource=languageDriver.createSqlSource(configuration,sql,modelClass);
        return addSelectMappedStatementForOther(mapperClass,method,sqlSource, Map.class);//addSelectMappedStatementForTable(mapperClass,method,sqlSource,tableInfo);
    }
}
