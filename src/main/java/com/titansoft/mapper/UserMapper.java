package com.titansoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titansoft.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: Kevin
 * @Date: 2019/7/25 11:43
 */
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from t_s_user")
    List<User> getAllUser();

    //通用删除sql
    @Delete("delete from ${tablename} where ${filter}")
    void  delUserData(@Param("tablename")String tablename, @Param("filter")String filter);

    //通用删除sql
    @Insert("insert  into ${tablename} values (${value})")
    void  insertUser(@Param("tablename")String tablename, @Param("value")String value);
}
