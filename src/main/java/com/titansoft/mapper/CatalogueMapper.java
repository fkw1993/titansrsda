package com.titansoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titansoft.entity.Catalogue;
import com.titansoft.utils.util.CommonUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/8/1 16:52
 */
public interface CatalogueMapper extends BaseMapper<Catalogue> {

    @Select("select * from T_ER_SRCLIST where erid=#{erid}")
    List<Map<String,Object>> getMedia(String erid);

    /**
     * @description 获取状态不是管理的干部目录；
     * @param  
     * @return  
     * @author Fkw
     * @date 2019/8/13 
     */
    @Select("select * from ${} where (catatitle is not null and CATATITLE<>'') and (STATUS <> 'gl' or STATUS is null) and SORTCODE is not null order by CADRECODE asc,SORTCODE,SEQUENCE")
    List<Map<String,Object>> getCatalogueIsNotGL();

    /**
     * @description 根据id修改状态
     * @param  * @param gl:
     * @param id:
     * @return
     * @author Fkw
     * @date 2019/8/13
     */
    @Update("update ${tablename} set  STATUS =#{status} where id = #{id}")
    void updateStatusById(@Param("tablename")String tablename, @Param("status") String status,@Param("id")String id);
}

