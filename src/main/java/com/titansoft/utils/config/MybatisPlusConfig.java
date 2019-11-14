package com.titansoft.utils.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/8/1 16:20
 */
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {
    //动态表名
    public static ThreadLocal<String> dynamicTableName = new ThreadLocal<String>();

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //动态表名配置开始（表名不同字段相同）
        ArrayList<ISqlParser> iSqlParserArrayList = new ArrayList<ISqlParser>();
        DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();
        Map<String, ITableNameHandler> tableNameHandlerMap = new HashMap<String, ITableNameHandler>();
        tableNameHandlerMap.put("po", new ITableNameHandler() {
            @Override
            public String dynamicTableName(MetaObject metaObject, String sql, String tableName) {
                return dynamicTableName.get();
            }
        });
        dynamicTableNameParser.setTableNameHandlerMap(tableNameHandlerMap);
        iSqlParserArrayList.add(dynamicTableNameParser);
        //动态表名配置结束
        paginationInterceptor.setSqlParserList(iSqlParserArrayList);
        return paginationInterceptor;
    }
}