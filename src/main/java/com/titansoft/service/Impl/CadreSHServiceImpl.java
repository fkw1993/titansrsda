package com.titansoft.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.titansoft.entity.cadre.Cadre;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.mapper.CadreMapper;
import com.titansoft.service.CadreSHService;
import com.titansoft.service.MediaService;
import com.titansoft.utils.util.DateTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author: Kevin
 * @Date: 2019/8/12 22:46
 */
@Service
@Transactional
public class CadreSHServiceImpl implements CadreSHService {
    @Autowired
    CadreMapper cadreMapper;
    @Autowired
    MediaService mediaService;
    /**
     * @param isselectAll
     * @param idnumbers      :
     * @param name          :
     * @param operationType :
     * @param logininfo     :
     * @return
     * @description 审核入库
     * @author Fkw
     * @date 2019/8/12
     */
    @Override
    public String cadreinfoCheck(String isselectAll, String idnumbers, String name, String operationType, Logininfo logininfo) {
        String result = "";
        try {
            String operaValue = "gl";
            if ("back".equals(operationType)) {
                operaValue = "cj";
            }
            if ("true".equals(isselectAll)) {
                cadreMapper.update(new Cadre(),new UpdateWrapper<Cadre>().set("status",operaValue).set("ischange","true").ne("status",operaValue));
            } else {
                String[] idnumberArr = idnumbers.split(",");
                String[] nameArr = name.split(",");
                for (int i = 0; i < idnumberArr.length; i++) {
                    String idnumber = idnumberArr[i].toString();
                    String cadreName = nameArr[i].toString();
                    cadreMapper.update(new Cadre(),new UpdateWrapper<Cadre>().set("status",operaValue).set("ischange","true").eq("idnumber",idnumber));
                    //记录系统日志
                    Date dt=new Date();
                    String dotime= DateTools.pdateToString(dt, "yyyy-MM-dd HH:mm:ss");
                  /*  if("back".equals(operationType)){
                        Logdata logdata = new Logdata(Constant.getGuid(), logininfo.getUsername(), logininfo.getRealname(),
                                logininfo.getUnitname(), logininfo.getUnitid(), logininfo.getIpaddress(), dotime,
                                "干部'"+cadreName+"'、身份证号："+idnumber+"审核退回到人事档案采集模块",
                                "审核退回", "正常","档案入库审核-审核退回");
                        this.basedao.addPo(Logdata.getTablenameofpo(),logdata);
                    }else{
                        Logdata logdata = new Logdata(Constant.getGuid(), logininfo.getUsername(), logininfo.getRealname(),
                                logininfo.getUnitname(), logininfo.getUnitid(), logininfo.getIpaddress(), dotime,
                                "干部'"+cadreName+"'、身份证号："+idnumber+"审核入库到人事档案管理模块",
                                "审核入库", "正常","档案入库审核-审核入库");
                        this.basedao.addPo(Logdata.getTablenameofpo(),logdata);
                    }*/

                }
            }
            result = "success";
            mediaService.strucEepFile(isselectAll,idnumbers,logininfo);

        } catch (Exception e) {
            result = "failure";
            e.printStackTrace();
        }
        return result;
    }
}
