package com.titansoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titansoft.entity.statistics.CatalogueItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/9/28 16:54
 */
public interface CatalogueItemMapper extends BaseMapper<CatalogueItem> {
    // 查询干部每个目录的份数
    @Select("select count(SORTCODE) as sortcount,sortcode,IDNUMBER from (select * from ${tablename} where IDNUMBER =#{idnumber} and CATATITLE is not null and CATATITLE!='' ) s GROUP BY s.SORTCODE,s.IDNUMBER HAVING IDNUMBER =#{idnumber}")
    List<Map<String,Object>> cadreCatalogueCount(@Param("idnumber") String idnumber,@Param("tablename") String tablename);

    // 查询干部每个目录的页数
    @Select("select sum(PAGENUMBER) as pagecount ,sortcode,IDNUMBER from  (select * from ${tablename} where IDNUMBER =#{idnumber} and CATATITLE is not null and CATATITLE!='' ) s GROUP BY s.SORTCODE,s.IDNUMBER HAVING IDNUMBER =#{idnumber}")
    List<Map<String,Object>> cadreCataloguePageCount(@Param("idnumber") String idnumber,@Param("tablename") String tablename);

    //通用查询
    @Select("select ${column} from ${tablename} where ${filter}")
    List<Map<String,Object>> selectData(@Param("column") String column,@Param("tablename") String tablename,@Param("filter") String filter);

}
