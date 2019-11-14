package com.titansoft.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.titansoft.entity.Catalogue;
import com.titansoft.entity.sqcd.Sqcd;
import com.titansoft.entity.sqcd.SqcdItem;
import com.titansoft.entity.sqcd.SqcdPower;
import com.titansoft.entity.sqcd.SqcdReason;
import com.titansoft.entity.statistics.CatalogueItem;
import com.titansoft.entity.statistics.TjLY;
import com.titansoft.entity.statistics.TjLYReason;
import com.titansoft.service.CadreStatisticsService;
import com.titansoft.utils.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Kevin
 * @Date: 2019/9/28 16:43
 */
@Service
public class CadreStatisticsServiceImpl extends BaseServiceImpl implements CadreStatisticsService {

    /**
     * @param params
     * @return
     * @description 干部分类统计
     * @author Fkw
     * @date 2019/9/28
     */
    @Override
    public void tjPersonCatalogue(Map<String, String> params) {
        String unitname = params.get("unitname") == null ? "" : params.get("unitname");
        String department = params.get("department") == null ? "" : params.get("department");
        String cadrename = params.get("cadrename") == null ? "" : params.get("cadrename");
        String idcard = params.get("idnumber") == null ? "" : params.get("idnumber");
        String filter = "";
        //条件构造器
        QueryWrapper queryWrapper = new QueryWrapper();
        if (!"".equals(unitname) && unitname != null && !"null".equals(unitname)) {
            filter += " and unitname like '%" + unitname + "%'";
            queryWrapper.like("unitname", unitname);
        }
        if (!"".equals(department) && department != null && !"null".equals(department)) {
            filter += " and department like '%" + department + "%'";
            queryWrapper.like("department", department);
        }
        if (!"".equals(cadrename) && cadrename != null) {
            filter += " and name like '%" + cadrename + "%'";
            queryWrapper.like("name", cadrename);
        }
        if (!"".equals(idcard) && idcard != null) {
            filter += " and idnumber like '%" + idcard + "%'";
            queryWrapper.like("idnumber", idcard);
        }
        List cadreList = cadreMapper.selectMaps(queryWrapper);
        // 新建对象接收
        CatalogueItem cataItem = null;
        catalogueItemMapper.delete(null);
        for (int i = 0; i < cadreList.size(); i++) {
            Map m = (Map) cadreList.get(i);
            String idnumber = m.get("idnumber").toString();
            // 查询每个干部每个目录的份数
            List pieceList = catalogueItemMapper.cadreCatalogueCount(idnumber,CommonUtil.getTableName(Catalogue.class));
            cataItem = new CatalogueItem();
            cataItem.setId(CommonUtil.getGuid());
            cataItem.setIdnumber(idnumber);
            cataItem.setUnitname(m.get("unitname").toString());
            cataItem.setDepartment(m.get("department").toString());
            cataItem.setName(m.get("name").toString());
            for (int j = 0; j < pieceList.size(); j++) {
                Map pieceMap = (Map) pieceList.get(j);
                String sortcode = pieceMap.get("sortcode").toString();
                switch (sortcode) {
                    case "1":
                        cataItem.setSortPiece1(pieceMap.get("sortcount").toString());
                        break;
                    case "2":
                        cataItem.setSortPiece2(pieceMap.get("sortcount").toString());
                        break;
                    case "3":
                        cataItem.setSortPiece3(pieceMap.get("sortcount").toString());
                        break;
                    case "4-1":
                        cataItem.setSortPiece4_1(pieceMap.get("sortcount").toString());
                        break;
                    case "4-2":
                        cataItem.setSortPiece4_2(pieceMap.get("sortcount").toString());
                        break;
                    case "4-3":
                        cataItem.setSortPiece4_3(pieceMap.get("sortcount").toString());
                        break;
                    case "4-4":
                        cataItem.setSortPiece4_4(pieceMap.get("sortcount").toString());
                        break;
                    case "5":
                        cataItem.setSortPiece5(pieceMap.get("sortcount").toString());
                        break;
                    case "6":
                        cataItem.setSortPiece6(pieceMap.get("sortcount").toString());
                        break;
                    case "7":
                        cataItem.setSortPiece7(pieceMap.get("sortcount").toString());
                        break;
                    case "8":
                        cataItem.setSortPiece8(pieceMap.get("sortcount").toString());
                        break;
                    case "9-1":
                        cataItem.setSortPiece9_1(pieceMap.get("sortcount").toString());
                        break;
                    case "9-2":
                        cataItem.setSortPiece9_2(pieceMap.get("sortcount").toString());
                        break;
                    case "9-3":
                        cataItem.setSortPiece9_3(pieceMap.get("sortcount").toString());
                        break;
                    case "9-4":
                        cataItem.setSortPiece9_4(pieceMap.get("sortcount").toString());
                        break;
                    case "10":
                        cataItem.setSortPiece10(pieceMap.get("sortcount").toString());
                        break;
                }
            }
            List pagenumList = catalogueItemMapper.cadreCataloguePageCount(idnumber,CommonUtil.getTableName(Catalogue.class));
            for (int j = 0; j < pagenumList.size(); j++) {
                Map pnumMap = (Map) pagenumList.get(j);
                String sortcode = pnumMap.get("sortcode").toString();
                switch (sortcode) {
                    case "1":
                        cataItem.setSortPnum1(pnumMap.get("pagecount").toString());
                        break;
                    case "2":
                        cataItem.setSortPnum2(pnumMap.get("pagecount").toString());
                        break;
                    case "3":
                        cataItem.setSortPnum3(pnumMap.get("pagecount").toString());
                        break;
                    case "4-1":
                        cataItem.setSortPnum4_1(pnumMap.get("pagecount").toString());
                        break;
                    case "4-2":
                        cataItem.setSortPnum4_2(pnumMap.get("pagecount").toString());
                        break;
                    case "4-3":
                        cataItem.setSortPnum4_3(pnumMap.get("pagecount").toString());
                        break;
                    case "4-4":
                        cataItem.setSortPnum4_4(pnumMap.get("pagecount").toString());
                        break;
                    case "5":
                        cataItem.setSortPnum5(pnumMap.get("pagecount").toString());
                        break;
                    case "6":
                        cataItem.setSortPnum6(pnumMap.get("pagecount").toString());
                        break;
                    case "7":
                        cataItem.setSortPnum7(pnumMap.get("pagecount").toString());
                        break;
                    case "8":
                        cataItem.setSortPnum8(pnumMap.get("pagecount").toString());
                        break;
                    case "9-1":
                        cataItem.setSortPnum9_1(pnumMap.get("pagecount").toString());
                        break;
                    case "9-2":
                        cataItem.setSortPnum9_2(pnumMap.get("pagecount").toString());
                        break;
                    case "9-3":
                        cataItem.setSortPnum9_3(pnumMap.get("pagecount").toString());
                        break;
                    case "9-4":
                        cataItem.setSortPnum9_4(pnumMap.get("pagecount").toString());
                        break;
                    case "10":
                        cataItem.setSortPnum10(pnumMap.get("pagecount").toString());
                        break;
                }
            }
            catalogueItemMapper.insert(cataItem);
        }
    }

    /**
     * @param params
     * @return
     * @description 利用统计
     * @author Fkw
     * @date 2019/9/28
     */
    @Transactional
    @Override
    public void tjUse(Map<String, String> params) {
        String starttime = params.get("starttime") == null ? "" : params.get("starttime");
        String endtime = params.get("endtime") == null ? "" : params.get("endtime");
        String filter = "1=1";
        if ("".equals(starttime) || starttime == null || "null".equals(starttime)) {
            if ("".equals(endtime) || endtime == null || "null".equals(endtime)) {
            } else {
                filter += " and a.applydate<'" + endtime + " 23:59:59'";
            }
        } else {
            if ("".equals(endtime) || endtime == null || "null".equals(endtime)) {
                filter += " and a.applydate>'" + starttime + " 00:00:00'";
            } else {
                filter += " and a.applydate>'" + starttime + " 00:00:00' and a.applydate<'" + endtime + " 23:59:59'";
            }
        }
        TjLY tjly = new TjLY();
        String column = "";
        String tablename = "";
        String sqlfilter = "";
        //单位申请量
        column = "DISTINCT b.unitname";
        tablename = CommonUtil.getTableName(Sqcd.class) + " a," + CommonUtil.getTableName(SqcdItem.class) + " b ";
        sqlfilter = filter + " and a.id=b.sqcdid";
        List unitcountList = catalogueItemMapper.selectData(column, tablename, sqlfilter);
        tjly.setUnitcount(unitcountList.size() + "");

        //申请人次
        column = "*";
        tablename = CommonUtil.getTableName(Sqcd.class) + " a";
        sqlfilter = filter;
        List applycountList = catalogueItemMapper.selectData(column, tablename, sqlfilter);
        tjly.setApplycount(applycountList.size() + "");

        //被查阅干部数
        column = "DISTINCT idnumber";
        tablename = CommonUtil.getTableName(SqcdPower.class);
        sqlfilter = "sqcdid in (select id from " + CommonUtil.getTableName(Sqcd.class) + " a where " + filter + ")";
        List cadrecountList = catalogueItemMapper.selectData(column, tablename, sqlfilter);
        tjly.setCadrecount(cadrecountList.size() + "");


        //现场查阅量
        column = "DISTINCT b.sqcdid";
        tablename = CommonUtil.getTableName(Sqcd.class) + " a," + CommonUtil.getTableName(SqcdItem.class) + " b";
        sqlfilter = filter + " and a.id=b.sqcdid and b.cdtype='现场查阅'";
        List cdscenecountList = catalogueItemMapper.selectData(column, tablename, sqlfilter);
        tjly.setCdscenecount(cdscenecountList.size() + "");

        //手机申请量
        column = "DISTINCT b.sqcdid";
        tablename = CommonUtil.getTableName(Sqcd.class) + " a," + CommonUtil.getTableName(SqcdItem.class) + " b";
        sqlfilter = filter + " and a.id=b.sqcdid and b.cdtype='手机申请'";
        List cdmobilecountList = catalogueItemMapper.selectData(column, tablename, sqlfilter);
        tjly.setCdmobilecount(cdmobilecountList.size() + "");

        //借阅量
        column = "DISTINCT b.sqcdid";
        tablename = CommonUtil.getTableName(Sqcd.class) + " a," + CommonUtil.getTableName(SqcdItem.class) + " b";
        sqlfilter = filter + " and a.id=b.sqcdid and b.cdtype='查（借）阅'";
        List borrowcountList = catalogueItemMapper.selectData(column, tablename, sqlfilter);
        tjly.setBorrowcount(borrowcountList.size() + "");

        //复制量
        column = "DISTINCT b.sqcdid";
        tablename = CommonUtil.getTableName(Sqcd.class) + " a," + CommonUtil.getTableName(SqcdItem.class) + " b";
        sqlfilter = filter + " and a.id=b.sqcdid and  b.usetype='复制' ";
        List copycountList = catalogueItemMapper.selectData(column, tablename, sqlfilter);
        tjly.setCopycount(copycountList.size() + "");

        //摘录量
        column = "DISTINCT b.sqcdid";
        tablename = CommonUtil.getTableName(Sqcd.class) + " a," + CommonUtil.getTableName(SqcdItem.class) + " b";
        sqlfilter = filter + " and a.id=b.sqcdid and  b.usetype='摘录' ";
        List excerptcountList = catalogueItemMapper.selectData(column, tablename, sqlfilter);
        tjly.setExcerptcount(excerptcountList.size() + "");
        //插入表

        tjLyMapper.delete(null);
        tjly.setId(CommonUtil.getGuid());
        tjLyMapper.insert(tjly);


        TjLYReason tjr = new TjLYReason();
        List reasonList = sqcdreasonMapper.selectList(new QueryWrapper<SqcdReason>().orderByAsc("sequence"));//this.basedao.queryhashmap("select * from "+ SqcdReason.getTablenameofpo()+" order by sequence");
        tjLyReasonMapper.delete(null);
        for (int i = 0; i < reasonList.size(); i++) {
            String reason = ((SqcdReason) reasonList.get(i)).getReason();
            List count = sqcdItemMapper.selectMaps(new QueryWrapper<SqcdItem>().select("count(DISTINCT UNITNAME) as unitcount", "count(DISTINCT SQCDID) as applycount").eq("reason", reason).inSql("sqcdid", "select id  from " + CommonUtil.getTableName(Sqcd.class) + " a where " + filter));
            List cadreList = sqcdPowerMapper.selectList(new QueryWrapper<SqcdPower>().select(" DISTINCT idnumber").inSql("sqcdid", "select  sqcdid from " + CommonUtil.getTableName(SqcdItem.class) + " where reason='" + reason + "' and sqcdid in(select id  from " + CommonUtil.getTableName(Sqcd.class) + " a where " + filter + ")"));
            tjr.setId(CommonUtil.getGuid());
            tjr.setReason(reason);
            tjr.setUnitcount(((Map) count.get(0)).get("unitcount").toString());
            tjr.setApplycount(((Map) count.get(0)).get("applycount").toString());
            tjr.setCadrecount(cadreList.size() + "");
            tjLyReasonMapper.insert(tjr);
        }
    }
}
