package com.titansoft.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.titansoft.entity.Catalogue;
import com.titansoft.entity.cadre.Cadre;
import com.titansoft.entity.media.MediaSource;
import com.titansoft.entity.media.Store;
import com.titansoft.entity.media.Storeitem;
import com.titansoft.entity.sqcd.SqcdStore;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.mapper.*;
import com.titansoft.service.MediaService;
import com.titansoft.utils.config.SystemConfig;
import com.titansoft.utils.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 材料附件处理
 *
 * @Author: Kevin
 * @Date: 2019/8/2 17:09
 */
@Service
@Transactional
public class MediaServiceImpl extends BaseServiceImpl implements MediaService {
    private static final Logger logger = LoggerFactory.getLogger(MediaServiceImpl.class);

    @Autowired
    MediaSourceMapper mediaSourceMapper;
    @Autowired
    StoreMapper storeMapper;
    @Autowired
    StoreitemMapper storeitemMapper;
    @Autowired
    CatalogueMapper catalogueMapper;
    @Autowired
    CadreMapper cadreMapper;

    static String key;

    static {
        try {
            key = AESUtils.getSecretKey("GZSGAT-GBRSDA");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param filename
     * @param datatype :
     * @param erid     :
     * @return
     * @description 判断文件是否重复
     * @author Fkw
     * @date 2019/8/2
     */
    @Override
    public boolean checkFileName(String filename, String datatype, String erid) {
        filename = filename.substring(0, filename.lastIndexOf("."));
        Integer count = mediaSourceMapper.selectCount(new QueryWrapper<MediaSource>().eq("erid", erid).eq("filename", filename));
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param filePath
     * @param filename :
     * @param file     :
     * @param erid     :
     * @param datatype :
     * @return
     * @description 上传文件
     * @author Fkw
     * @date 2019/8/3
     */
    @Override
    public String saveFile(String filePath, String filename, MultipartFile file, String erid, String datatype) {
        Store store = storeMapper.selectOne(new QueryWrapper<Store>().eq("datatype", datatype).eq("isdefault", "是"));
        Storeitem storeitem = new Storeitem();
        boolean saveresult = false;
        String relativefullpath = "";//原图路径
        String optPath = "";//优化后图片路径
        String unitAndNameStr = "";
        Store optStore = storeMapper.selectOne(new QueryWrapper<Store>().eq("datatype", "OPTIMIZE").eq("isdefault", "是"));
        String optLocation = optStore.getLocation();
        relativefullpath = filePath + "/" + filename;
        unitAndNameStr = getUnitAndNameStr(filePath);
        optPath = optLocation + "/" + unitAndNameStr + "/" + erid;
        saveresult = saveFileOnDisk(file, store.getLocation(), relativefullpath, optPath, filename);

        storeitem.setStoreid(store.getId());
        storeitem.setFilesize(file.getSize() + "");

        //原图路径加密
        storeitem.setUrl(relativefullpath);
        byte[] inputData = relativefullpath.getBytes();
        inputData = AESUtils.encrypt(inputData, key);
        String encryptUrl = AESUtils.parseByte2HexStr(inputData);
        storeitem.setEncrypturl(encryptUrl);

        //优化图片
        storeitem.setOpturl(unitAndNameStr + "/" + erid + "/" + filename);
        byte[] optInputData = (unitAndNameStr + "/" + erid + "/" + filename).getBytes();
        optInputData = AESUtils.encrypt(optInputData, key);
        String enopturl = AESUtils.parseByte2HexStr(optInputData);
        storeitem.setEnopturl(enopturl);

        if (saveresult) {
            storeitem.setId(CommonUtil.getGuid());
            storeitemMapper.insert(storeitem);
        }
        return storeitem.getId();
    }

    /**
     * @param filePath
     * @param name     :
     * @param file     :
     * @param erid     :
     * @param datatype :
     * @return
     * @description 上传文件
     * @author Fkw
     * @date 2019/8/3
     */
    @Override
    public String saveFile(String filePath, String filename, File file, String erid, String datatype) {
        Store store = storeMapper.selectOne(new QueryWrapper<Store>().eq("datatype", datatype).eq("isdefault", "是"));
        Storeitem storeitem = new Storeitem();
        boolean saveresult = false;
        String relativefullpath = "";//原图路径
        String optPath = "";//优化后图片路径
        String unitAndNameStr = "";
        Store optStore = storeMapper.selectOne(new QueryWrapper<Store>().eq("datatype", "OPTIMIZE").eq("isdefault", "是"));
        String optLocation = optStore.getLocation();
        relativefullpath = filePath + "/" + filename;
        unitAndNameStr = getUnitAndNameStr(filePath);
        optPath = optLocation + "/" + unitAndNameStr + "/" + erid;
        saveresult = saveFileOnDisk(file, store.getLocation(), relativefullpath, optPath, filename);

        storeitem.setStoreid(store.getId());
        storeitem.setFilesize(file.length() + "");

        //原图路径加密
        storeitem.setUrl(relativefullpath);
        byte[] inputData = relativefullpath.getBytes();
        inputData = AESUtils.encrypt(inputData, key);
        String encryptUrl = AESUtils.parseByte2HexStr(inputData);
        storeitem.setEncrypturl(encryptUrl);

        //优化图片
        storeitem.setOpturl(unitAndNameStr + "/" + erid + "/" + filename);
        byte[] optInputData = (unitAndNameStr + "/" + erid + "/" + filename).getBytes();
        optInputData = AESUtils.encrypt(optInputData, key);
        String enopturl = AESUtils.parseByte2HexStr(optInputData);
        storeitem.setEnopturl(enopturl);

        if (saveresult) {
            storeitem.setId(CommonUtil.getGuid());
            storeitemMapper.insert(storeitem);
        }
        return storeitem.getId();
    }

    @Override
    public void addFile(MediaSource mediaSource) {
        //缩略图路径
        String thumbpath = SystemConfig.getValue("titans.thumbpath");
        if (!thumbpath.replace(File.pathSeparator, "/").endsWith("/")) {
            thumbpath = thumbpath.replace(File.pathSeparator, "/") + "/";
        }
        String sql = "";

        String maxFileOrder = "0";
        List<Map<String, Object>> list = mediaSourceMapper.selectMaxFileOrderByErid(CommonUtil.getTableName(MediaSource.class), mediaSource.getErid());
        if (list.get(0) == null) {
            maxFileOrder = "0";
        } else {
            maxFileOrder = list.get(0).get("maxfileorder").toString();
        }
        mediaSource.setFileorder(StringformatZreoUtil.getNumFillZreo(((Integer.parseInt(maxFileOrder)) + 1) + "", 4));
        mediaSource.setFileformat(FileTool.getExts(mediaSource.getFilename()));
        try {
            mediaSource.setFilename(mediaSource.getFilename().substring(0, mediaSource.getFilename().lastIndexOf(".")));
        } catch (Exception e) {
            System.out.println("*********************文件名：" + mediaSource.getFilename() + ",截取错误***************************");
            e.printStackTrace();
        }
        mediaSource.setCreatetime(DateUtil.getTimeNow(new Date()));
        mediaSource.setId(CommonUtil.getGuid());
        mediaSourceMapper.insert(mediaSource);

    }

    /**
     * @param sortId
     * @param mudule :
     * @param source :
     * @return
     * @description 获取文件存储路径
     * @author Fkw
     * @date 2019/8/3
     */
    @Override
    public String getCataMediaPath(String sortId, String mudule, String source) {
        String path = "";
        List<MediaSource> list = mediaSourceMapper.selectList(new QueryWrapper<MediaSource>().eq("erid", sortId).orderByAsc("createtime"));
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                MediaSource mediaSource = list.get(i);
                String storeitemId = mediaSource.getStoreitemid();
                Storeitem storeitem = (Storeitem) storeitemMapper.selectOne(new QueryWrapper<Storeitem>().eq("id", storeitemId));
                String url = "";
                if (mudule != null && "gl".equals(mudule)) {
                    url = storeitem.getEnopteppurl();
                } else {
                    url = storeitem.getEnopturl();// 原图路径加密后
                }
                if (url.indexOf(".pdf") < 0 && url.indexOf(".PDF") < 0) {
                    if ("".equals(path)) {
                        path = storeitemId + "-" + url;
                    } else {
                        path += "," + storeitemId + "-" + url;
                    }
                }
            }
        }
        return path;
    }

    /**
     * @return
     * @description 定时任务：把原始图片转换成优化图片
     * @author Fkw
     * @date 2019/8/4
     */
    @Override
    public void changeImages() {
        //查询前100条未转换的数据
        Page<Storeitem> storeitemIPage = (Page<Storeitem>) storeitemMapper.selectPage(new Page<Storeitem>(1, 100), new QueryWrapper<Storeitem>().isNull("optstatus"));
        List<Storeitem> list = storeitemIPage.getRecords();
        if (!list.isEmpty()) {
            //获取原始图片路径
            String location = ((Store) storeMapper.selectOne(new QueryWrapper<Store>().eq("code", "SOURCE"))).getLocation();
            //获取优化图片路径
            String optLocation = ((Store) storeMapper.selectOne(new QueryWrapper<Store>().eq("code", "OPTIMIZE"))).getLocation();
            for (int i = 0; i < list.size(); i++) {
                Map map = CommonUtil.convertToMap(list.get(i));
                String mediapath = (String) map.get("url");
                String inputpath = location + "/" + mediapath;
                //判断源文件是否存在
                File intputfile = new File(inputpath);
                if (!intputfile.exists()) {
                    Storeitem storeitem = list.get(i);
                    storeitem.setOptstatus("未找到源文件");
                    storeitemMapper.updateById(storeitem);
                    continue;
                }
                String optPath = (String) map.get("opturl");
                String optUrl = optLocation + "/" + optPath;
                String optDir = optUrl.substring(0, optUrl.lastIndexOf("/"));
                String fileName = optUrl.substring(optUrl.lastIndexOf("/") + 1, optUrl.length());
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                String nowDateStr = df.format(new Date());
                String outputUrl = optDir + "/" + nowDateStr + "/" + fileName;
                if (!new File(optDir + "/" + nowDateStr).isDirectory()) {
                    new File(optDir + "/" + nowDateStr).mkdirs();
                }
                String enOutputUrl = optDir + "/" + nowDateStr + "/" + "解密";
                if (!new File(enOutputUrl).isDirectory()) {
                    new File(enOutputUrl).mkdirs();
                }
                AESUtils.decryptedFile(inputpath, enOutputUrl + "/" + fileName);
                //解密图片
                ChangeImageSize.scale(new File(enOutputUrl + "/" + fileName), outputUrl, 2, false);
                //转换图片加密
                AESUtils.encryptFile(outputUrl, optUrl);
                //删除临时文件夹
                try {
                    FileTool.delDir(optDir + "/" + nowDateStr);
                } catch (IOException e) {
                    logger.error("删除文件失败");
                }
                Storeitem storeitem = list.get(i);
                storeitem.setOptstatus("success");
                storeitemMapper.updateById(storeitem);
            }
        }
    }

    /**
     * @param isselectAll
     * @param idnumbers   :
     * @return
     * @description 形成永久库保存文件
     * @author Fkw
     * @date 2019/8/12
     */
    @Override
    public void strucEepFile(String isselectAll, String idnumbers, Logininfo logininfo) {
        //获取采集库地址
        Store store = storeMapper.selectOne(new QueryWrapper<Store>().eq("datatype", "SOURCE"));
        String locationSource = store.getLocation();
        //优化图像库存放地址
        store = storeMapper.selectOne(new QueryWrapper<Store>().eq("datatype", "OPTIMIZE"));
        String locationOpt = store.getLocation();
        //获取永久库存放地址
        store = storeMapper.selectOne(new QueryWrapper<Store>().eq("datatype", "EEP"));
        String locationEep = store.getLocation();
        //是否是全选
        if ("true".equals(isselectAll)) {
            List list = catalogueMapper.getCatalogueIsNotGL();
            if (!list.isEmpty()) {
                String currentCadreCode = "";
                String unitName = "";//
                String idNumber = "";
                String name = "";
                for (int i = 0; i < list.size(); i++) {
                    Map map = (Map) list.get(i);
                    String id = map.get("id").toString();
                    String sortcode = map.get("sortcode").toString() == null ? "" : map.get("sortcode").toString();
                    String sequence = map.get("sequence").toString() == null ? "" : map.get("sequence").toString();
                    String idnumber = map.get("idnumber").toString() == null ? "" : map.get("idnumber").toString();
                    String catatitle = map.get("catatitle").toString() == null ? "" : map.get("idnumber").toString();
                   /* DaProcess p=new DaProcess(Constant.getGuid(),logininfo.getRealname(),"审核通过了一条目录材料，目录编号<"+sortcode+"-"+sequence+">,目录题名<"+catatitle+">",DateTools.pdateToString(new Date(), "yyyy-MM-dd HH:mm:ss"),idnumber);
                    this.mediaDAO.addPo(DaProcess.getTablenameofpo(), p);*/
                    if (i == 0) {
                        currentCadreCode = idnumber;
                        Cadre cadre = cadreMapper.selectOne(new QueryWrapper<Cadre>().eq("idnumber", currentCadreCode));
                        if (cadre != null) {
                            unitName = cadre.getUnitname().toString();
                            idNumber = cadre.getIdnumber().toString();
                            name = cadre.getName().toString();
                        }
                    } else {
                        if (!idnumber.equals(currentCadreCode)) {
                            currentCadreCode = idnumber;
                            Cadre cadre = cadreMapper.selectOne(new QueryWrapper<Cadre>().eq("idnumber", currentCadreCode));
                            if (cadre != null) {
                                unitName = cadre.getUnitname().toString();
                                idNumber = cadre.getIdnumber().toString();
                                name = cadre.getName().toString();
                            }
                        }
                    }
                    String eepPath = locationEep + "/" + unitName + "/" + name + idNumber;
                    String eepUandN = unitName + "/" + name + idNumber;
                    strucEepFileByCataId(id, locationSource, eepPath, eepUandN, locationOpt, sortcode, sequence);
                    catalogueMapper.updateStatusById(CommonUtil.getTableName(Catalogue.class), "gl", id);
                }
            }
        } else {
            String[] cardreArr = idnumbers.split(",");
            String unitName = "";//
            String idnumber = "";
            String name = "";
            for (String cadreCode : cardreArr) {// wrapper.isNotNull("CATATITLE").ne("CATATITLE",""))
                List list = catalogueMapper.selectList(new QueryWrapper<Catalogue>().and(wrapper -> wrapper.isNotNull("CATATITLE").ne("CATATITLE", ""))
                        .and(wrapper -> wrapper.isNotNull("STATUS").ne("STATUS", "gl")).isNotNull("SORTCODE").eq("idnumber", cadreCode).orderByAsc(false, new String[]{"SORTCODE", "SEQUENCE"}));
                if (!list.isEmpty()) {
                    Cadre cadre = cadreMapper.selectOne(new QueryWrapper<Cadre>().eq("idnumber", cadreCode));
                    if (cadre != null) {
                        unitName = cadre.getUnitname().toString();
                        idnumber = cadre.getIdnumber().toString();
                        name = cadre.getName().toString();
                    }
                    for (int i = 0; i < list.size(); i++) {
                        Map map = (Map) CommonUtil.convertToMap(list.get(i));
                        String id = map.get("id").toString();
                        String sortcode = map.get("sortcode").toString() == null ? "" : map.get("sortcode").toString();
                        String sequence = map.get("sequence").toString() == null ? "" : map.get("sequence").toString();
                        String catatitle = map.get("catatitle").toString() == null ? "" : map.get("catatitle").toString();
                       /* DaProcess p = new DaProcess(Constant.getGuid(), logininfo.getRealname(), "审核通过了一条目录材料，目录编号<" + sortcode + "-" + sequence + ">,目录题名<" + catatitle + ">", DateTools.pdateToString(new Date(), "yyyy-MM-dd HH:mm:ss"), idnumber);
                        this.mediaDAO.addPo(DaProcess.getTablenameofpo(), p);*/
                        String eepPath = locationEep + "/" + unitName + "/" + name + idnumber;
                        String eepUandN = unitName + "/" + name + idnumber;
                        this.strucEepFileByCataId(id, locationSource, eepPath, eepUandN, locationOpt, sortcode,
                                sequence);
                        catalogueMapper.updateStatusById(CommonUtil.getTableName(Catalogue.class), "gl", id);
                    }
                }
            }
        }
    }

    /**
     * @return
     * @description 系统安全维护的饼状图
     * @author Fkw
     * @date 2019/8/30
     */
    @Override
    public int storePie() {
        List list = mediaSourceMapper.getSumFilesize(CommonUtil.getTableName(MediaSource.class));
        Map m = (Map) list.get(0);
        double size = (double) Integer.valueOf(m.get("size").toString());
        double gbsize = size / 1000 / 1024 / 1024;
        Store store = storeMapper.selectOne(new QueryWrapper<Store>().eq("code", "SOURCE"));
        int maxgbsize = Integer.valueOf(store.getMaxsize().toString());
        double pe = gbsize / maxgbsize;
        double db = pe * 100;//得到百分比
        int percent = (int) db;
        return percent;
    }

    /**
     * @param datatype
     * @param idnumber :
     * @return
     * @description 获取下载文件的路径
     * @author Fkw
     * @date 2019/9/28
     */
    @Override
    public String getDownfile(String datatype, String idnumber) {
        Store store = storeMapper.selectOne(new QueryWrapper<Store>().eq("datatype", datatype));
        String location = store.getLocation();
        String result = "";
        Cadre cadre = cadreMapper.selectOne(new QueryWrapper<Cadre>().eq("idnumber", idnumber)); //this.mediaDAO.queryhashmap("select * from T_BUS_CADRE where idnumber='" + idnumber + "'");
        String path = "";
        String temppath = "";
        if (cadre != null) {
            temppath = location + "/download/" + cadre.getName() + cadre.getIdnumber();// 临时存放压缩路径
            if (!new File(temppath).exists()) {
                new File(temppath).mkdirs();
            }
            List listStoreItem = storeitemMapper.selectList(new QueryWrapper<Storeitem>().inSql("id", "select STOREITEMID from T_ER_SRCLIST where ERID in(select id from T_S_CATALOGUE where IDNUMBER = '" + idnumber + "')"));
            if (!listStoreItem.isEmpty()) {
                for (int i = 0; i < listStoreItem.size(); i++) {
                    Storeitem maps = (Storeitem) listStoreItem.get(i);
                    String enEncryptUrl = maps.getEnopteppurl();
                    if ("".equals(enEncryptUrl)) {
                        continue;
                    }
                    byte[] enInputData = AESUtils.parseHexStr2Byte(enEncryptUrl);
                    byte[] enOutputData = AESUtils.decrypt(enInputData, key);
                    String enOutputStr = new String(enOutputData);

                    path = location + "/" + enOutputStr;
                    String fileName = path.substring(path.lastIndexOf("/") + 1, path.length());
                    String targetPath = temppath + "/" + fileName;
                    FileTool.copyFile(new File(path), new File(targetPath));
                }
            }
            if (temppath != null && new File(temppath).isDirectory()) {
                result = temppath.substring(0, temppath.length() - 1) + ".zip";
                ZipOption.expZip(temppath, result);
                ZipOption.del(temppath);
            }
        }
        return result;
    }


    /**
     * 通过目录ID构造永久库电子图片
     *
     * @param cataId     目录ID
     * @param sourcePath 采集库路径
     * @param eepPath    永久库路径
     * @throws Exception
     */
    public void strucEepFileByCataId(String cataId, String sourcePath, String eepPath, String eepUandN, String optPath,
                                     String sortcode, String sequence) {
        try {
            List list = mediaSourceMapper.selectList(new QueryWrapper<MediaSource>().eq("erid", cataId));
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Map map = CommonUtil.convertToMap(list.get(i));
                    String storeItemId = map.get("storeitemid").toString();
                    String fileorder = map.get("fileorder").toString();
                    String fileformat = map.get("fileformat").toString();
                    Storeitem storeitem = storeitemMapper.selectOne(new QueryWrapper<Storeitem>().eq("id", storeItemId));
                    if (storeitem != null) {
                        Map mapStoreItem = CommonUtil.convertToMap(storeitem);
                        // 归档原始图像数据--start
                        String url = mapStoreItem.get("url").toString();
                        String sourcefile = sourcePath + "/" + url;
                        File sourceFile = new File(sourcefile);
                        if (sourceFile.exists()) {
                            if (!new File(eepPath + "/原始图像数据/").isDirectory()) {
                                new File(eepPath + "/原始图像数据/").mkdirs();
                            }
                            String eepFile = eepPath + "/原始图像数据/" + sortcode + "-" + sequence + "-" + fileorder + "."
                                    + fileformat;
                            InputStream is = new FileInputStream(sourceFile);
                            OutputStream os = new FileOutputStream(eepFile);
                            byte[] bytefer = new byte[1024];
                            int length = 0;
                            while ((length = is.read(bytefer)) > 0) {
                                os.write(bytefer, 0, length);
                            }
                            os.close();
                            is.close();
                            // 加密归档后图片
                            String opteppurl = eepUandN + "/原始图像数据/" + sortcode + "-" + sequence + "-" + fileorder + "."
                                    + fileformat;
                            byte[] inputData = opteppurl.getBytes();
                            inputData = AESUtils.encrypt(inputData, key);
                            String enEepFile = AESUtils.parseByte2HexStr(inputData);
                            // 更新表T_ER_STOREITEM中归档电子图片路径(原图像、优化图像)
                            storeitem.setEppurl(opteppurl);
                            storeitem.setEneppurl(enEepFile);
                            storeitemMapper.updateById(storeitem);
                        } // 归档原始图像数据--end
                        // 归档优化图像数据--start
                        String optUrl = mapStoreItem.get("opturl").toString();
                        String optfile = optPath + "/" + optUrl;
                        File optFile = new File(optfile);
                        if (optFile.exists()) {
                            if (!new File(eepPath + "/优化图像数据/").isDirectory()) {
                                new File(eepPath + "/优化图像数据/").mkdirs();
                            }
                            String optEepFile = eepPath + "/优化图像数据/" + sortcode + "-" + sequence + "-" + fileorder + "."
                                    + fileformat;

                            InputStream is = new FileInputStream(optFile);
                            OutputStream os = new FileOutputStream(optEepFile);
                            byte[] bytefer = new byte[1024];
                            int length = 0;
                            while ((length = is.read(bytefer)) > 0) {
                                os.write(bytefer, 0, length);
                            }
                            os.close();
                            is.close();

                            // 加密归档后图片
                            String enopteppurl = eepUandN + "/优化图像数据/" + sortcode + "-" + sequence + "-" + fileorder + "."
                                    + fileformat;
                            byte[] inputData = enopteppurl.getBytes();
                            inputData = AESUtils.encrypt(inputData, key);
                            String enOptEepFile = AESUtils.parseByte2HexStr(inputData);
                            // 更新表T_ER_STOREITEM中归档电子图片路径(原图像、优化图像)
                            storeitem.setOpteppurl(enopteppurl);
                            storeitem.setEnopteppurl(enOptEepFile);
                            storeitemMapper.updateById(storeitem);
                        }
                        // 归档优化图像数据--end
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据采集库地址截取'单位'以及'人名+身份证'
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String getUnitAndNameStr(String url) {
        String str = "";
        if (url.indexOf("/") > 0) {
            String unit = url.substring(0, url.indexOf("/"));
            String unitElse = url.substring(url.indexOf("/") + 1, url.length());
            String nameAndId = unitElse.substring(0, unitElse.indexOf("/"));
            str = unit + "/" + nameAndId;
        }
        return str;
    }

    public static boolean saveFileOnDisk(MultipartFile file, String Rootlocation, String relativefullpath, String
            optPath, String fileName) {
        boolean success = false;
        try {
            long startMili = System.currentTimeMillis();
            String absolutePath = Rootlocation.replace(File.separatorChar, '/')
                    .endsWith("/") ? Rootlocation : Rootlocation + "/";
            absolutePath += relativefullpath.replace(File.separatorChar, '/')
                    .startsWith("/") ? relativefullpath.substring(1) : relativefullpath; //D:/document/mediasource2/2017/11/27/0D6608D6EDDBFDD2175BBA9BB3C4FE6D/48540923dd54564ef9dbe6d5b2de9c82d0584f9b.jpg
            String dirs = absolutePath.substring(0, absolutePath.length() - ("/" + new File(absolutePath).getName()).length());
            if (!new File(dirs).isDirectory()) {
                new File(dirs).mkdirs();
            }
            success = true;
            long endMili = System.currentTimeMillis();
            System.out.println("复制文件时间" + (endMili - startMili));
            //文件加密
            String destFilePath = dirs + "/" + new File(absolutePath).getName();
            AESUtils.encryptFile(file, destFilePath);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return success;
    }


    public static boolean saveFileOnDisk(File file, String Rootlocation, String relativefullpath, String
            optPath, String fileName) {
        boolean success = false;
        try {
            long startMili = System.currentTimeMillis();
            String absolutePath = Rootlocation.replace(File.separatorChar, '/')
                    .endsWith("/") ? Rootlocation : Rootlocation + "/";
            absolutePath += relativefullpath.replace(File.separatorChar, '/')
                    .startsWith("/") ? relativefullpath.substring(1) : relativefullpath; //D:/document/mediasource2/2017/11/27/0D6608D6EDDBFDD2175BBA9BB3C4FE6D/48540923dd54564ef9dbe6d5b2de9c82d0584f9b.jpg
            String dirs = absolutePath.substring(0, absolutePath.length() - ("/" + new File(absolutePath).getName()).length());
            if (!new File(dirs).isDirectory()) {
                new File(dirs).mkdirs();
            }
            success = true;
            long endMili = System.currentTimeMillis();
            System.out.println("复制文件时间" + (endMili - startMili));
            //文件加密
            String destFilePath = dirs + "/" + new File(absolutePath).getName();
            AESUtils.encryptFile(file, destFilePath);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return success;
    }

    /**
     * 记录zip包上传信息
     *
     * @param idNumber  身份证号
     * @param imageName 文件名称
     * @param imageFile 文件路径
     */
    @Override
    public void addZipfile(String idNumber, String imageName, String imageFile, String message) {
        try {
            DES des = new DES();
            MediaSource mediaSource = new MediaSource();
            mediaSource.setFilename(imageName);
            mediaSource.setDatatype("SOLID");
            mediaSource.setFilesize(imageFile.length());
            mediaSource.setStoreitemid("");
            mediaSource.setErid(idNumber);
            mediaSource.setDocumenttype("MAINFILE");
            mediaSource.setHashcode(des.getEncryptedString(ReverseEncript.Encrypt(imageFile, "SHA-1")));
            mediaSource.setMessage(message);
            addFile(mediaSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param sortId
     * @param sqcdid :
     * @return
     * @description 利用平台查看被分配权限的材料附件
     * @author Fkw
     * @date 2019/10/12
     */
    @Override
    public String getCataMediaPathLY(String sortId, String sqcdid, String datatype) {
        String path = "";
        String encrypturl = "";
        List<MediaSource> mediaSourceList = mediaSourceMapper.selectList(new QueryWrapper<MediaSource>().eq("erid", sortId).orderByAsc("createtime"));
        List storeList = sqcdStoreMapper.selectList(new QueryWrapper<SqcdStore>().eq("sqcdid", sqcdid));
        if (!mediaSourceList.isEmpty()) {
            for (int i = 0; i < mediaSourceList.size(); i++) {
                MediaSource mediaSource = mediaSourceList.get(i);
                String storeitemId = mediaSource.getStoreitemid();
                boolean bool = false;
                for (int j = 0; j < storeList.size(); j++) {
                    SqcdStore sqcdStore = (SqcdStore) storeList.get(j);
                    if (storeitemId.equals(sqcdStore.getStoreid())) {
                        bool = true;
                        break;
                    }
                }
                if (bool) {
                    Storeitem storeitem = storeitemMapper.selectById(storeitemId);
                    String url = "";
                    url = storeitem.getEnopturl();// 原图路径加密后
                    if (url.indexOf(".pdf") < 0 && url.indexOf(".PDF") < 0) {
                        if ("".equals(path)) {
                            path = url;
                        } else {
                            path += "," + url;
                        }
                    }
                }
            }
            // path = url.substring(0, url.lastIndexOf("/"));
        }


        return path;
    }

    /**
     * @param cataImageFiles
     * @param cataId         :
     * @param cadreCode      :
     * @return
     * @description 挂接电子文件  --- 多人批量采集
     * @author Fkw
     * @date 2019/10/28
     */
    @Override
    public String addImageMultiple(File[] cataImageFiles, String cataId, String cadreCode) {
        String result = "";
        try {
            for (File file : cataImageFiles) {
                String imageFilePath = this.struFilePathBycadrecode(cadreCode, cataId);
                String imagePath = file.getAbsolutePath();
                String imageName = imagePath.substring(imagePath.lastIndexOf("\\") + 1, imagePath.length());
                String storeitemid = saveFile(imageFilePath, imageName, file, cataId, "SOURCE");
                DES des = new DES();
                MediaSource mediaSource = new MediaSource();
                mediaSource.setFilename(imageName);
                mediaSource.setDatatype("SOURCE");
                mediaSource.setFilesize((int) file.length());
                mediaSource.setStoreitemid(storeitemid);
                mediaSource.setErid(cataId);
                mediaSource.setDocumenttype("MAINFILE");
                mediaSource.setHashcode(des.getEncryptedString(ReverseEncript.Encrypt(file, "SHA-1")));
                addFile(mediaSource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 组装存储路径---根据员工身份证号获取
     *
     * @param sortId
     * @return
     */
    public String struFilePathBycadrecode(String code, String sortId) {
        String path = "";
        try {
            String unitName = "";//
            String name = "";
            String idNumber = "";
            List listCadre = cadreMapper.selectMaps(new QueryWrapper<Cadre>().eq("idnumber", code));
            if (!listCadre.isEmpty()) {
                Map mapCadre = (HashMap) listCadre.get(0);
                unitName = mapCadre.get("unitname").toString();
                idNumber = mapCadre.get("idnumber").toString();
                name = mapCadre.get("name").toString();
            }
            path = unitName + "/" + name + idNumber + "/" + sortId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
}
