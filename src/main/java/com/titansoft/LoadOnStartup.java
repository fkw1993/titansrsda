package com.titansoft;

import com.titansoft.entity.system.Config;
import com.titansoft.entity.system.Product;
import com.titansoft.mapper.*;
import com.titansoft.service.DictionaryService;
import com.titansoft.utils.config.MybatisPlusConfig;
import com.titansoft.utils.config.PrivilegeConfig;
import com.titansoft.utils.config.SystemConfig;
import com.titansoft.utils.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**系统启动初始化
 * @Author: Kevin
 * @Date: 2019/7/25 19:31
 */
@Component
public class LoadOnStartup implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(LoadOnStartup.class);
    @Autowired
    PoMapper poMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    OaMapper oaMapper;
    @Autowired
    PrivilegeConfig privilegeConfig;
    @Autowired
    DictionaryService dictionaryService;
    @Autowired
    ConfigMapper configMapper;
    @Autowired
    PrivilegeViewMapper privilegeViewMapper;
    @Value("${server.port}")
    private String port;//端口
    @Value("${server.servlet.context-path}")
    private String rootpath;
    @Value("${titans.systemversion}")
    private String systemVersion;//系统版本

    @Value("${spring.profiles.active}")//开发环境dev  现场pro
    private String dev;



    public static Map<String, List> dictionaryList = new HashMap<String,List>();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("************************开始初始化数据************************");
        log.info("************************初始化产品信息************************");
        List<Product> productList=productMapper.selectList(null);
        List<Product> productList2=oaMapper.selectList(null);
        String localPort= CommonUtil.getLocalIpAddr()+":"+CommonUtil.getLocalPort();
        if("dev".equals(dev)){//开发环境用127
            localPort="127.0.0.1:"+CommonUtil.getLocalPort();
        }
        if(localPort!=null) {//现场环境用真实IP
            for (Product product : productList) {
                String uri = product.getUri();
                //根据配置替换product表端口号
                String serverPort = uri.substring(0, uri.lastIndexOf(":"));
                if (!serverPort.equals(localPort)) {
                    product.setUri("http://"+ localPort+rootpath);
                    productMapper.updateById(product);
                }
            }
        }else{
            log.error("无法获取到服务器Ip,请手动去数据库修改");
        }
        log.info("************************初始化所有系统配置项目到内存************************");
        List<Config> configList=configMapper.selectList(null);
        if (configList.size() > 0) {
            for (Config config : configList) {
                SystemConfig.configMap.put(config.getConfigcode(), config.getConfigvalue());
            }
        }
        log.info("************************初始化所有系统功能到内存************************");
        privilegeConfig.initPrivilegeMap();
        log.info("************************初始化数据字典到内存************************");
        dictionaryList=dictionaryService.getDictionaryList();
        log.info("************************结束初始化数据************************");
    }
}
