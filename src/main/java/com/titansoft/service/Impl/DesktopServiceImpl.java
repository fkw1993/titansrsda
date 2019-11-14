package com.titansoft.service.Impl;

import com.titansoft.entity.system.Product;
import com.titansoft.mapper.PrivilegeMapper;
import com.titansoft.mapper.ProductMapper;
import com.titansoft.model.Tree;
import com.titansoft.service.DesktopService;
import com.titansoft.service.PrivilegeService;
import com.titansoft.utils.config.PrivilegeConfig;
import com.titansoft.utils.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/7/30 16:02
 */
@Service
public class DesktopServiceImpl implements DesktopService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    PrivilegeMapper privilegeMapper;
    /**
     * @param productid
     * @param roleid    :
     * @return
     * @description 获取各个模块的菜单
     * @author Fkw
     * @date 2019/7/30
     */
    @Override
    public List<Tree> getMenu(String productid, String roleid) {
        List<Product> productlist =productMapper.selectList(null);
        Map<String,Product> productMap = new HashMap<String,Product>();
        for(Product product : productlist)
        {
            productMap.put(product.getProductid(), product);
        }
        List<Map<String ,Object>> list = privilegeMapper.getMenuByModule(productid, CommonUtil.getIDstr(roleid));
        for (int i = 0; i < list.size(); i++) {
            Map map=list.get(i);
            if(map.get("RECOMMEND")!=null&&!"".equals(map.get("RECOMMEND"))){
                map.put("ROOTLINK", PrivilegeConfig.productMap.get(map.get("RECOMMEND").toString()).getUri());
            }
        }
        return createTreesList(CommonUtil.transformUpperCaseList(list));
    }

    /**
     * 遍历菜单集合,生成树泛型数组
     * @param lists 菜单集合
     */
    private List<Tree> createTreesList(List<Map<String ,Object>> lists){
        //中间变量
        Tree parenTree = null;
        Tree childTree = null;
        List<Tree> trees = new ArrayList<Tree>();
        List<Tree> childrenTree = new ArrayList<Tree>();
        for(Map map : lists){
            //属于一级菜单时
            if("".equals(map.get("parentid"))||null==map.get("parentid")){
                parenTree = new Tree();
                parenTree.setId(map.get("privilegeid") + "");
                parenTree.setText(map.get("text") + "");
                parenTree.setIconCls(map.get("iconcls") + "");
                //二级菜单
                for(Map subMap : lists){
                    if(map.get("privilegeid").equals(subMap.get("parentid"))){
                        childTree = new Tree();
                        childTree.setId(subMap.get("privilegeid") + "");
                        childTree.setText(subMap.get("text") + "");
                        childTree.setIconCls(subMap.get("iconcls") + "");
                        childTree.setLeaf(true);
                        childTree.setAttributes(subMap);
                        childTree.setChildren(Tree.isChild);
                        childrenTree.add(childTree);
                    }
                }
                if(!childrenTree.isEmpty()){
                    parenTree.setChildren(childrenTree.toArray(new Tree[0]));
                }else{
                    parenTree.setLeaf(true);
                }
                parenTree.setAttributes(map);
                trees.add(parenTree);
                childrenTree.clear();
            }
        }
        // 情况临时变量
        parenTree = null;
        childTree = null;
        childrenTree = null;
        return trees;
    }
}
