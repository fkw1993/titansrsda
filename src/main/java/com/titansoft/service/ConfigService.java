package com.titansoft.service;

import com.titansoft.entity.system.Config;
import com.titansoft.entity.system.Logininfo;

/**系统配置Service
 * @Author: Kevin
 * @Date: 2019/10/18 15:20
 */
public interface ConfigService {
    /**
     * @param
     * @return
     * @description 表头
     * @author Fkw
     * @date 2019/10/18
     */
    String configTableColumns();

    /**
     * @param *          @param config:
     * @param logininfo:
     * @return
     * @description 增加保存
     * @author Fkw
     * @date 2019/10/18
     */
    String addConfig(Config config, Logininfo logininfo);

    /**
     * @param *       @param operate:
     * @param config:
     * @return
     * @description 表单
     * @author Fkw
     * @date 2019/10/18
     */
    String configForm(String operate, Config config);

    /**
     * @param *          @param config:
     * @param logininfo:
     * @return
     * @description 保存修改
     * @author Fkw
     * @date 2019/10/18
     */
    String editConfig(Config config, Logininfo logininfo);

    /**
     * @param * @param id:
     * @return
     * @description 删除
     * @author Fkw
     * @date 2019/10/18
     */
    void delConfig(String id);
}
