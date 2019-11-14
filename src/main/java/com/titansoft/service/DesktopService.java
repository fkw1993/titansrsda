package com.titansoft.service;

import com.titansoft.model.Tree;

import java.util.List;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/7/30 16:01
 */
public interface DesktopService {
    /**
     * @description 获取各个模块的菜单
     * @param  * @param productid: 
 * @param roleid:  
     * @return  
     * @author Fkw
     * @date 2019/7/30 
     */
    List<Tree> getMenu(String productid, String roleid);
}
