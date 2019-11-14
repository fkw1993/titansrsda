package com.titansoft.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.titansoft.entity.Po;
import com.titansoft.entity.UserStatus;
import com.titansoft.model.PoList;
import com.titansoft.entity.Unit;
import com.titansoft.entity.cadre.Cadre;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.model.enums.CadreBasicEnum;
import com.titansoft.model.enums.CadreExtendEnum;
import com.titansoft.model.extjs.Common;
import com.titansoft.service.CadreInfoService;
import com.titansoft.utils.config.MybatisPlusConfig;
import com.titansoft.utils.util.BeanUtil;
import com.titansoft.utils.util.CommonUtil;
import com.titansoft.utils.util.PinYinTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Kevin
 * @Date: 2019/10/2 8:57
 */
@Service
public class CadreInfoServiceImpl extends BaseServiceImpl implements CadreInfoService {

    /**
     * @param treeid
     * @param poList
     * @param logininfo
     * @return
     * @description 增加干部基本信息
     * @author Fkw
     * @date 2019/10/2
     */
    @Transactional
    @Override
    public String addCadreBasicInfo(String treeid, PoList poList, Logininfo logininfo) {
        Po basicpo = poList.getBasicpo();
        int count = cadreMapper.selectCount(new QueryWrapper<Cadre>().eq("idnumber", basicpo.getIdnumber()));
        if (count >= 1) {
            return "此干部已存在系统中";
        }
        basicpo.setWg00(CommonUtil.getGuid());
        MybatisPlusConfig.dynamicTableName.set(CadreBasicEnum.getEnumByAtype("basicinfo").getTablename());
        poMapper.delete(new UpdateWrapper<Po>().eq("idnumber", basicpo.getIdnumber()));
        poMapper.insert(basicpo);
        Cadre cadre = new Cadre();
        cadre.setId(CommonUtil.getGuid());
        cadre.setIdnumber(basicpo.getIdnumber());
        cadre.setName(basicpo.getWg01());
        cadre.setSex(basicpo.getWg02());
        cadre.setBirthday(basicpo.getWg03());
        cadre.setNation(basicpo.getWg04());
        cadre.setOrigin(basicpo.getWg05());
        cadre.setPolitical(basicpo.getWg08());
        cadre.setWorktime(basicpo.getWg10());
        cadre.setCadrestatus(userStatusMapper.selectOne(new QueryWrapper<UserStatus>().eq("name","在职")).getId());
        cadre.setUnitid(treeid.split("-")[1]);
        cadre.setDepartid(treeid.split("-")[treeid.split("-").length - 1]);
        cadre.setPinyin(PinYinTool.getPinYinHeadChar(basicpo.getWg01()));
        //获取单位名
        List unitlist = unitMapper.selectList(new QueryWrapper<Unit>().eq("unitid", treeid.split("-")[1]));
        if (unitlist.size() > 0) {
            Unit unit = (Unit) unitlist.get(0);
            cadre.setUnitname(unit.getName());
        }
        //获取部门名
        List departlist = unitMapper.selectList(new QueryWrapper<Unit>().eq("unitid", treeid.split("-")[treeid.split("-").length - 1]));
        if (departlist.size() > 0) {
            Unit unit = (Unit) departlist.get(0);
            cadre.setDepartment(unit.getName());
        }
        cadreMapper.insert(cadre);

        /**学历情况情况**/
        List<Po> educationPoList = poList.getEducationPoList();
        MybatisPlusConfig.dynamicTableName.set(CadreBasicEnum.getEnumByAtype("education").getTablename());
        poMapper.delete(new UpdateWrapper<Po>().eq("idnumber", basicpo.getIdnumber()));
        for (int i = 0; i < educationPoList.size(); i++) {
            Po educationPo = educationPoList.get(i);
            if (BeanUtil.checkBeanIsNull(educationPo)) {
                continue;
            } else {
                educationPo.setWg00(CommonUtil.getGuid());
                educationPo.setIdnumber(basicpo.getIdnumber());
                poMapper.insert(educationPo);
            }
        }
        /**工作简历**/
        List<Po> workPoList = poList.getWorkPoList();
        MybatisPlusConfig.dynamicTableName.set(CadreBasicEnum.getEnumByAtype("work").getTablename());
        poMapper.delete(new UpdateWrapper<Po>().eq("idnumber", basicpo.getIdnumber()));
        for (int i = 0; i < workPoList.size(); i++) {
            Po workPo = workPoList.get(i);
            if (BeanUtil.checkBeanIsNull(workPo)) {
                continue;
            } else {
                workPo.setWg00(CommonUtil.getGuid());
                workPo.setIdnumber(basicpo.getIdnumber());
                poMapper.insert(workPo);
            }
        }

        /**家庭主要成员及重要社会关系**/
        List<Po> familyPoList = poList.getFamilyPoList();
        MybatisPlusConfig.dynamicTableName.set(CadreBasicEnum.getEnumByAtype("family").getTablename());
        poMapper.delete(new UpdateWrapper<Po>().eq("idnumber", basicpo.getIdnumber()));
        for (int i = 0; i < familyPoList.size(); i++) {
            Po familyPo = familyPoList.get(i);
            if (BeanUtil.checkBeanIsNull(familyPo)) {
                continue;
            } else {
                familyPo.setWg00(CommonUtil.getGuid());
                familyPo.setIdnumber(basicpo.getIdnumber());
                poMapper.insert(familyPo);
            }
        }

        /**出国出境情况**/
        List<Po> abroadPoList = poList.getAbroadPoList();
        MybatisPlusConfig.dynamicTableName.set(CadreBasicEnum.getEnumByAtype("abroad").getTablename());
        poMapper.delete(new UpdateWrapper<Po>().eq("idnumber", basicpo.getIdnumber()));
        for (int i = 0; i < abroadPoList.size(); i++) {
            Po abroadPo = abroadPoList.get(i);
            if (BeanUtil.checkBeanIsNull(abroadPo)) {
                continue;
            } else {
                abroadPo.setWg00(CommonUtil.getGuid());
                abroadPo.setIdnumber(basicpo.getIdnumber());
                poMapper.insert(abroadPo);
            }
        }
        /**人事档案保管情况**/
        List<Po> archivesKeepPoList = poList.getArchivesKeepPoList();
        MybatisPlusConfig.dynamicTableName.set(CadreBasicEnum.getEnumByAtype("archivesKeep").getTablename());
        poMapper.delete(new UpdateWrapper<Po>().eq("idnumber", basicpo.getIdnumber()));
        for (int i = 0; i < archivesKeepPoList.size(); i++) {
            Po archivesKeepPo = archivesKeepPoList.get(i);
            if (BeanUtil.checkBeanIsNull(archivesKeepPo)) {
                continue;
            } else {
                archivesKeepPo.setWg00(CommonUtil.getGuid());
                archivesKeepPo.setIdnumber(basicpo.getIdnumber());
                poMapper.insert(archivesKeepPo);
            }
        }
        return "";
    }

    /**
     * @param treeid
     * @param poList
     * @param logininfo
     * @return
     * @description 修改干部基本信息
     * @author Fkw
     * @date 2019/10/2
     */
    @Transactional
    @Override
    public String editCadreBasicInfo(String treeid, PoList poList, Logininfo logininfo) {
        Po basicpo = poList.getBasicpo();
        Cadre cadre = cadreMapper.selectOne(new QueryWrapper<Cadre>().eq("idnumber", basicpo.getIdnumber()));
        MybatisPlusConfig.dynamicTableName.set(CadreBasicEnum.getEnumByAtype("basicinfo").getTablename());
        poMapper.updateById(basicpo);
        cadre.setName(basicpo.getWg01());
        cadre.setSex(basicpo.getWg02());
        cadre.setBirthday(basicpo.getWg03());
        cadre.setNation(basicpo.getWg04());
        cadre.setOrigin(basicpo.getWg05());
        cadre.setPolitical(basicpo.getWg08());
        cadre.setWorktime(basicpo.getWg10());
        cadre.setPinyin(PinYinTool.getPinYinHeadChar(basicpo.getWg01()));
        cadreMapper.updateById(cadre);

        /**学历情况情况**/
        List<Po> educationPoList = poList.getEducationPoList();
        MybatisPlusConfig.dynamicTableName.set(CadreBasicEnum.getEnumByAtype("education").getTablename());
        for (int i = 0; i < educationPoList.size(); i++) {
            Po educationPo = educationPoList.get(i);
            if (BeanUtil.checkBeanIsNull(educationPo)) {//全部为空直接过滤
                continue;
                //wg00不为空就更新，为空就增加
            } else if (educationPo.getWg00() != null && !"".equals(educationPo.getWg00())) {
                poMapper.updateById(educationPo);
            } else {
                educationPo.setWg00(CommonUtil.getGuid());
                educationPo.setIdnumber(basicpo.getIdnumber());
                poMapper.insert(educationPo);
            }
        }

        /**工作简历**/
        List<Po> workPoList = poList.getWorkPoList();
        MybatisPlusConfig.dynamicTableName.set(CadreBasicEnum.getEnumByAtype("work").getTablename());
        for (int i = 0; i < workPoList.size(); i++) {
            Po workPo = workPoList.get(i);
            if (BeanUtil.checkBeanIsNull(workPo)) {//全部为空直接过滤
                continue;
            } else if (workPo.getWg00() != null && !"".equals(workPo.getWg00())) {
                poMapper.updateById(workPo);
            } else {
                workPo.setWg00(CommonUtil.getGuid());
                workPo.setIdnumber(basicpo.getIdnumber());
                poMapper.insert(workPo);
            }
        }

        /**家庭主要成员及重要社会关系**/
        List<Po> familyPoList = poList.getFamilyPoList();
        MybatisPlusConfig.dynamicTableName.set(CadreBasicEnum.getEnumByAtype("family").getTablename());
        for (int i = 0; i < familyPoList.size(); i++) {
            Po familyPo = familyPoList.get(i);
            if (BeanUtil.checkBeanIsNull(familyPo)) {//全部为空直接过滤
                continue;
            } else if (familyPo.getWg00() != null && !"".equals(familyPo.getWg00())) {
                poMapper.updateById(familyPo);
            } else {
                familyPo.setWg00(CommonUtil.getGuid());
                familyPo.setIdnumber(basicpo.getIdnumber());
                poMapper.insert(familyPo);
            }
        }

        /**出国出境情况**/
        List<Po> abroadPoList = poList.getAbroadPoList();
        MybatisPlusConfig.dynamicTableName.set(CadreBasicEnum.getEnumByAtype("abroad").getTablename());
        for (int i = 0; i < abroadPoList.size(); i++) {
            Po abroadPo = abroadPoList.get(i);
            if (BeanUtil.checkBeanIsNull(abroadPo)) {//全部为空直接过滤
                continue;
            } else if (abroadPo.getWg00() != null && !"".equals(abroadPo.getWg00())) {
                poMapper.updateById(abroadPo);
            } else {
                abroadPo.setWg00(CommonUtil.getGuid());
                abroadPo.setIdnumber(basicpo.getIdnumber());
                poMapper.insert(abroadPo);
            }
        }

        /**人事档案保管情况**/
        List<Po> archivesKeepPoList = poList.getArchivesKeepPoList();
        MybatisPlusConfig.dynamicTableName.set(CadreBasicEnum.getEnumByAtype("archivesKeep").getTablename());
        for (int i = 0; i < archivesKeepPoList.size(); i++) {
            Po archivesKeepPo = archivesKeepPoList.get(i);
            if (BeanUtil.checkBeanIsNull(archivesKeepPo)) {//全部为空直接过滤
                continue;
            } else if (archivesKeepPo.getWg00() != null && !"".equals(archivesKeepPo.getWg00())) {
                poMapper.updateById(archivesKeepPo);
            } else {
                archivesKeepPo.setWg00(CommonUtil.getGuid());
                archivesKeepPo.setIdnumber(basicpo.getIdnumber());
                poMapper.insert(archivesKeepPo);
            }
        }
        return "";
    }

    /**
     * @param idnumber
     * @return
     * @description 获取干部基本信息
     * @author Fkw
     * @date 2019/10/3
     */
    @Override
    public Po getBasicPo(String idnumber) {
        Po po = new Po();
        MybatisPlusConfig.dynamicTableName.set(CadreBasicEnum.getEnumByAtype("basicinfo").getTablename());
        List list = poMapper.selectList(new QueryWrapper<Po>().eq("idnumber", idnumber));
        if (list.size() > 0) {
            po = (Po) list.get(0);
        } else {
            Cadre cadre = cadreMapper.selectOne(new QueryWrapper<Cadre>().eq("idnumber", idnumber));
            if (cadre != null) {
                po.setIdnumber(cadre.getIdnumber());
                po.setWg01(cadre.getName());
                po.setWg02(cadre.getSex());
                po.setWg03(cadre.getBirthday());
                po.setWg05(cadre.getNation());
                po.setWg06(cadre.getOrigin());
                po.setWg09(cadre.getPolitical());
                po.setWg11(cadre.getWorktime());
            }
        }
        return po;
    }

    /**
     * @param type
     * @param idnumber :
     * @param num      :
     * @return
     * @description 获取干部其他额外信息
     * @author Fkw
     * @date 2019/10/3
     */
    @Override
    public List<Po> getBasicItem(String type, String idnumber, int num) {
        MybatisPlusConfig.dynamicTableName.set(CadreBasicEnum.getEnumByAtype(type).getTablename());
        List<Po> list = poMapper.selectList(new QueryWrapper<Po>().eq("idnumber", idnumber));
        // 查询的数据与页面行数不符遍历时会报错，所以用空对象代替
        if (list.size() < num) {
            int arr = num - list.size();
            for (int i = 1; i <= arr; i++) {
                Po p = new Po();
                list.add(p);
            }
        }
        return list;
    }

    /**
     * @param type
     * @param idnumber :
     * @return
     * @description 获取干部扩展信息
     * @author Fkw
     * @date 2019/10/3
     */
    @Override
    public Po getExtendPo(String type, String idnumber) {
       Cadre cadre = cadreMapper.selectOne(new QueryWrapper<Cadre>().eq("idnumber",idnumber));
       MybatisPlusConfig.dynamicTableName.set(CadreExtendEnum.getEnumByAtype(type).getTablename());
       Po po =poMapper.selectOne(new QueryWrapper<Po>().eq("idnumber",idnumber));
        if (po == null) {
            po = new Po();
            if (cadre!=null) {
                po.setWg00(CommonUtil.getGuid());
                po.setWg01(cadre.getName());
                po.setWg02(cadre.getSex());
                po.setWg03(cadre.getBirthday());
                po.setWg04(cadre.getNation());
                po.setWg05(cadre.getOrigin());
                po.setWg08(cadre.getWorktime());
                po.setIdnumber(cadre.getIdnumber());
                // 同步基本信息
                poMapper.insert(po);
            }
        } else {
            if (cadre!=null){
                po.setWg01(cadre.getName());
                po.setWg02(cadre.getSex());
                po.setWg03(cadre.getBirthday());
                po.setWg04(cadre.getNation());
                po.setWg05(cadre.getOrigin());
                po.setWg08(cadre.getWorktime());
                po.setIdnumber(cadre.getIdnumber());
                // 同步基本信息
               poMapper.updateById(po);
            }
        }
        return po;

    }

    /**
     * @param po
     * @param tablename :
     * @return
     * @description 保存扩展信息
     * @author Fkw
     * @date 2019/10/3
     */
    @Override
    public void addCadreExtendInfo(Po po, String tablename) {
        MybatisPlusConfig.dynamicTableName.set(tablename);
        po.setWg00(CommonUtil.getGuid());
        poMapper.insert(po);
    }

    /**
     * @param po
     * @param tablename
     * @return
     * @description 修改扩展信息
     * @author Fkw
     * @date 2019/10/3
     */
    @Transactional
    @Override
    public void editCadreExtendInfo(Po po, String tablename) {
        MybatisPlusConfig.dynamicTableName.set(tablename);
        poMapper.updateById(po);
    }


}
