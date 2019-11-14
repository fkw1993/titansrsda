package com.titansoft.utils.mybatis;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.titansoft.utils.mybatis.customizeSql.GetAllUnit;
import com.titansoft.utils.mybatis.customizeSql.GetPowerByRoleId;
import com.titansoft.utils.mybatis.customizeSql.GetPowerByUserId;
import org.springframework.stereotype.Component;

import java.util.List;

/**自定义sql注入器
 * @Author: Kevin
 * @Date: 2019/10/18 16:57
 */
@Component
public class SqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList=super.getMethodList(mapperClass);
        //自定义sql
        methodList.add(new GetPowerByUserId());
        methodList.add(new GetPowerByRoleId());
        methodList.add(new GetAllUnit());
        return methodList;
    }
}
