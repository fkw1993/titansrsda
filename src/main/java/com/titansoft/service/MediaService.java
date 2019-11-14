package com.titansoft.service;

import com.titansoft.entity.media.MediaSource;
import com.titansoft.entity.system.Logininfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @Author: Kevin
 * @Date: 2019/8/2 17:09
 */
public interface MediaService {
    /**
     * @description 判断文件是否重复
     * @param  * @param name: 
 * @param datatype: 
 * @param erid:  
     * @return  
     * @author Fkw
     * @date 2019/8/2 
     */
    boolean checkFileName(String name, String datatype, String erid);

    /**
     * @description  上传文件
     * @param  * @param filePath:
     * @param name:
     * @param file:
     * @param erid:
     * @param datatype:
     * @return
     * @author Fkw
     * @date 2019/8/3
     */
    String saveFile(String filePath, String name, MultipartFile file, String erid, String datatype);

    /**
     * @description  上传文件
     * @param  * @param filePath:
     * @param name:
     * @param file:
     * @param erid:
     * @param datatype:
     * @return
     * @author Fkw
     * @date 2019/8/3
     */
    String saveFile(String filePath, String name, File file, String erid, String datatype);



    void addFile(MediaSource mediaSource);
    /**
     * @description 获取文件存储路径 
     * @param  * @param sortId: 
 * @param mudule: 
 * @param source:  
     * @return  
     * @author Fkw
     * @date 2019/8/3 
     */
    String getCataMediaPath(String sortId, String mudule, String source);

    /**
     * @description 定时任务：把原始图片转换成优化图片
     * @param
     * @return
     * @author Fkw
     * @date 2019/8/4
     */
    void changeImages();

    /**
     * @description 形成永久库保存文件
     * @param  * @param isselectAll:
     * @param idnumbers:
     * @return
     * @author Fkw
     * @date 2019/8/12
     */
    void strucEepFile(String isselectAll, String idnumbers, Logininfo logininfo);
    
    /**
     * @description 系统安全维护的饼状图 
     * @param  
     * @return  
     * @author Fkw
     * @date 2019/8/30 
     */
    int storePie();

    /**
     * @description 获取下载文件的路径
     * @param  * @param datatype:类型
     * @param idnumber:
     * @return
     * @author Fkw
     * @date 2019/9/28
     */
    String getDownfile(String datatype, String idnumber);



    /**
     * @description 记录zip包上传信息
     * @param  * @param formatname: 
 * @param imageName: 
 * @param zipPath: 
 * @param message:
     * @return  
     * @author Fkw
     * @date 2019/10/4 
     */
    void addZipfile(String formatname, String imageName, String zipPath, String message);

    /**
     * @description 利用平台查看被分配权限的材料附件
     * @param  * @param sortId: 
     * @param sqcdid:
     * @return  
     * @author Fkw
     * @date 2019/10/12 
     */
    String getCataMediaPathLY(String sortId, String sqcdid,String datatype);

    /**
     * @description 挂接电子文件  --- 多人批量采集
     * @param  * @param cataImageFiles: 
 * @param cataId: 
 * @param cadreCode:  
     * @return  
     * @author Fkw
     * @date 2019/10/28 
     */
    String addImageMultiple(File[] cataImageFiles, String cataId, String cadreCode);
}
