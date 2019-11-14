package com.titansoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.titansoft.entity.media.MediaSource;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/8/2 17:32
 */
public interface MediaSourceMapper extends BaseMapper<MediaSource> {
    @Select("select max(fileorder) maxfileorder from  ${tablename} where erid=#{erid} ")
    List<Map<String, Object>> selectMaxFileOrderByErid(@Param("tablename") String tablename,@Param("erid") String erid);

    @Select("select sum(filesize) as size from ${tablename}")
    List<Map<String, Object>> getSumFilesize(@Param("tablename") String tablename);

}
