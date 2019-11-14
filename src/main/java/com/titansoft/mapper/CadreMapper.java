package com.titansoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titansoft.entity.cadre.Cadre;
import com.titansoft.entity.Po;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Kevin
 * @Date: 2019/7/31 19:14
 */
public interface CadreMapper extends BaseMapper<Cadre> {
    /**
     * @description 获取人事基本信息
     * @param  * @param basicinfo:
    * @param idnumber:
     * @return
     * @author Fkw
     * @date 2019/8/10
     */
    @Select("select * from ${tablename} where idnumber=#{idnumber}")
    List<Po> getCadreBasic(@Param("tablename") String tablename, @Param("idnumber") String idnumber);


}
