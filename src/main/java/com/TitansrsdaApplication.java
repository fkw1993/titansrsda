package com;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.support.http.StatViewServlet;
import com.fr.web.ReportServlet;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;*/
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
//扫描mapper
@MapperScan("com.titansoft.mapper")
//扫描filter
@ServletComponentScan(basePackages ="com.titansoft")
//开启事务支持
@EnableTransactionManagement
//开启定时任务
@EnableScheduling

public class TitansrsdaApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(TitansrsdaApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(TitansrsdaApplication.class);
    }

    //配置fineReport的servlet
    @Bean
    public ServletRegistrationBean fineReportServletBean() { // 一定要返回ServletRegistrationBean
        ServletRegistrationBean bean = new ServletRegistrationBean(new ReportServlet()); // 放入自己的Servlet对象实例
        bean.addUrlMappings("/ReportServer"); // 访问路径值
        bean.setLoadOnStartup(0);
        System.out.println("/////////////////////////////////////////////////////////////////");
        return bean;
    }

    //配置druid的servlet
    @Bean
    public ServletRegistrationBean druidServiletBean() { // 一定要返回ServletRegistrationBean
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        bean.addInitParameter("allow", "");
        bean.addInitParameter("loginUsername", "admin");
        bean.addInitParameter("loginPassword", "admin");
        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
        bean.addInitParameter("resetEnable", "false");
        bean.setLoadOnStartup(1);
        return bean;
    }

    /**
     * 1.系统代码和sql语句禁止使用出现表名，可以通过在类上的mybatisplus注解和在TableName类中记录表名,获取表名的方法1.CommonUtil.getTableName(Cadre.class) 2.TableName.xxx
     * 2.动态切换数据源 @DS("slave")使用注解在mapper接口上，slave为数据源的名称
     * 3.自定义sql,使用SqlInjector自定义sql注入器
     */
}
