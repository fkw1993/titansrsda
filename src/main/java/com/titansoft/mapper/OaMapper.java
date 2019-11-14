package com.titansoft.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titansoft.entity.system.Product;

/**
 * @Author: Kevin
 * @Date: 2019/10/4 21:30
 */
@DS("slave")
public interface OaMapper extends BaseMapper<Product> {
}
