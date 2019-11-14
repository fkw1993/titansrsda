package com.titansoft.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.titansoft.entity.Catalogue;
import com.titansoft.entity.cadre.Cadre;
import com.titansoft.entity.media.MediaSource;
import com.titansoft.entity.media.Store;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.service.CadreCJService;
import com.titansoft.utils.util.*;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * 人事档案采集Service实现类
 *
 * @Author: Kevin
 * @Date: 2019/7/31 14:41
 */
@Service
public class CadreCJServiceImpl extends BaseServiceImpl implements CadreCJService {
    /**
     * @param idnumber
     * @return
     * @description 判断是否符合提交审核的条件
     * @author Fkw
     * @date 2019/8/16
     */
    @Override
    public String checkAudit(String idnumber) {
        String msg = "";
        // 判断是否有采集的目录
        List<Catalogue> catalogueList = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("idnumber", idnumber).isNotNull("CATATITLE").and(wrapper -> wrapper.eq("status", "cj").or().eq("status", "").or().isNull("status")));
        if (catalogueList.size() > 0) {
            for (int i = 0; i < catalogueList.size(); i++) {
                Catalogue catalogue = catalogueList.get(i);
                if ("".equals(catalogue.getCatatitle())) {
                    msg = "该干部含有没有标题的目录";
                    return msg;
                }
                // 判断目录下是否含有电子图片
                List srcList = mediaSourceMapper.selectList(new QueryWrapper<MediaSource>().eq("erid", catalogue.getId()));
                if (srcList.size() == 0) {
                    msg = "部分目录下没有挂接电子图片";
                    return msg;
                }
            }
        } else {
            msg = "该干部没有新的采集目录";
            return msg;

        }
        return msg;
    }

    /**
     * @param idnumbers
     * @param logininfo :
     * @return
     * @description 提交审核
     * @author Fkw
     * @date 2019/8/16
     */
    @Override
    public void shenhe(String idnumbers, Logininfo logininfo) {
        Boolean bool = false;
        String[] idnumberArr = idnumbers.split(",");
        for (String idnumber : idnumberArr) {
            // 判断是否有采集的目录
            List<Catalogue> catalogueList = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("idnumber", idnumber).isNotNull("CATATITLE").and(wrapper -> wrapper.eq("status", "cj").or().eq("status", "").or().isNull("status")));
            if (catalogueList.size() > 0) {
                bool = true;
                cadreMapper.update(new Cadre(), new UpdateWrapper<Cadre>().set("status", "sh").set("ischange", "true").eq("idnumber", idnumber));
                catalogueMapper.update(new Catalogue(), new UpdateWrapper<Catalogue>().set("status", "sh").eq("idnumber", idnumber).isNotNull("CATATITLE").and(wrapper -> wrapper.eq("status", "cj").or().eq("status", "").or().isNull("status")));
                for (int i = 0; i < catalogueList.size(); i++) {
                    Catalogue catalogue = (Catalogue) catalogueList.get(i);
                    String sortcode = catalogue.getSortcode();
                    String sequence = catalogue.getSequence();
                    String catatitle = catalogue.getCatatitle();
                    //过程信息
                   /* DaProcess p=new DaProcess(Constant.getGuid(),logininfo.getRealname(),"提交审核了一条目录材料，目录编号<"+sortcode+"-"+sequence+">,目录题名<"+catatitle+">", DateTools.pdateToString(new Date(), "yyyy-MM-dd HH:mm:ss"),idnumber);
                    this.basedao.addPo(DaProcess.getTablenameofpo(), p);*/

                }
            }
        }

    }

    /**
     * @param mudule
     * @return
     * @description 标准数据包接收 标准数据包采集(1.解压zip；2.解析xml；3.匹配电子文件；4.插入目录表中形成关系；5.成功后插入接收表记录中。)
     * *     2019-05-05 加入模块判断，如果是档案管理模块批量采集，生成两套文件（采集库及永久库）
     * @author Fkw
     * @date 2019/10/4
     */
    @Override
    public String uploadStandardAdd(String mudule, MultipartFile file, Logininfo logininfo) {
        String result = "";
        try {
            String fileFileName = file.getOriginalFilename();
            // 获取存储位置
            Store store = storeMapper.selectOne(new QueryWrapper<Store>().eq("datatype", "SOLID"));
            String location = store.getLocation();
            // 移动文件到指定路径(保存原数据包)--start
            String nameAndid = fileFileName.substring(0, fileFileName.lastIndexOf("."));
            String fileMir = location + "/标准数据接收包/" + nameAndid + "/";
            File directory = new File(fileMir);
            if (!directory.exists())
                directory.mkdirs();
            String absolutePath = fileMir + fileFileName;
            long startMili = System.currentTimeMillis();
            InputStream is = file.getInputStream();
            OutputStream os = new FileOutputStream(absolutePath);
            byte[] bytefer = new byte[1024];
            int length = 0;
            while ((length = is.read(bytefer)) > 0) {
                os.write(bytefer, 0, length);
            }
            os.close();
            is.close();
            long endMili = System.currentTimeMillis();
            System.out.println("复制文件时间" + (endMili - startMili));
            // 移动文件到指定路径(保存原数据包)--end

            // 1、解压数据包--start
            ZipOption opt = new ZipOption();
            opt.extZipFileList(absolutePath, fileMir);
            // 1、解压数据包--end

            // 2.解析xml --start
            String path = fileMir + "/" + nameAndid + "/" + "" + nameAndid + ".xml";
            FileInputStream finput = new FileInputStream(path);
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(finput);
            Element root = doc.getRootElement();
            String extfilename = null;
            String formatname = null;
            List list = XPath.selectNodes(root, "//人员基本信息");
            if (!list.isEmpty()) {
                Element element = (Element) list.get(0);
                Element achild = element.getChild("公民身份号码");
                Element achildName = element.getChild("姓名");
                if (achild != null) {
                    formatname = achild.getText().trim();
                    System.out.println("=====身份证号：" + formatname);
                    // 判断身份证是否存在
                    List listCadre = cadreMapper.selectList(new QueryWrapper<Cadre>().eq("idnumber", formatname));
                    if (!listCadre.isEmpty()) {
                        boolean resultFlag = checkZipData(formatname);
                        if (resultFlag) {
                            List listCata = root.getChild("目录信息").getChildren();
                            Map map = getXMLinfo(listCata);
                            String message = map.get("message").toString();
                            if ("".equals(message)) {// 数据包没有报错信息再进行下一步操作
                                JSONArray jsonArray = (JSONArray) map.get("cataJsonArray");
                                System.out.println("=====目录条目信息：" + jsonArray);

                                // 3、插入目录信息以及挂接电子文件
                                Cadre cadre = (Cadre) listCadre.get(0);
                                String cadreCode = cadre.getCode();
                                String imagePath = fileMir + "/" + nameAndid + "/图像数据/原始图像数据/";
                                String isSuccess = this.addCadreCataInfo(jsonArray, imagePath, formatname, cadreCode);

                                //如果是档案管理模块批量采集，形成永久库文件
                                if ("gl".equals(mudule)) {
                                    mediaService.strucEepFile("", formatname, logininfo);
                                }

                                if (!"success".equals(isSuccess)) {
                                    // 删除解压的文件
                                    String zipPath = fileMir + "/" + "" + nameAndid + ".zip";
                                    String imageName = "" + nameAndid + ".zip";
                                    FileTool.delDir(fileMir + "/" + nameAndid);

                                    // 5.成功后插入接收表记录中
                                    mediaService.addZipfile(formatname, imageName, zipPath, "");
                                }
                            } else {
                                result = message;
                                String zipPath = fileMir + "/" + "" + nameAndid + ".zip";
                                String imageName = "" + nameAndid + ".zip";
                                mediaService.addZipfile(formatname, imageName, zipPath, result);
                                // 删除上传的数据包以及解压的数据包
                                FileTool.delDir(fileMir + "/" + nameAndid);
                                FileTool.deleteFile(fileMir + "/" + "" + nameAndid + ".zip");
                            }
                            // writeProcess(logininfo, "通过数据的挂接的方式导入数据",formatname);

                            //记录入职信息
                            //记录系统日志
                          /*  Date dt=new Date();
                            String dotime= DateTools.pdateToString(dt, "yyyy-MM-dd HH:mm:ss");
                            String cadreName = "";
                            if(achildName!=null){
                                cadreName = achildName.getText().trim();
                            }

                            Logdata logdata = new Logdata(Constant.getGuid(), logininfo.getUsername(), logininfo.getRealname(),
                                    logininfo.getUnitname(), logininfo.getUnitid(), logininfo.getIpaddress(), dotime,
                                    "通过'批量采集-标准数据包'方式导入干部'"+cadreName+"'的人事档案信息",
                                    "批量采集", "正常","人事档案采集-批量采集");
                            this.addPo(logdata, "t_s_datalog", "id");*/

                        } else {
                            result = "该干部数据包已经接收过，无需重复接收!";
                            FileTool.delDir(fileMir + "/" + nameAndid);
                            /* deleteFile(fileMir + "/" + ""+nameAndid+".zip"); */

                            //记录系统日志
                            Date dt = new Date();
                            String dotime = DateTools.pdateToString(dt, "yyyy-MM-dd HH:mm:ss");
                            String cadreName = "";
                            if (achildName != null) {
                                cadreName = achildName.getText().trim();
                            }

                           /* Logdata logdata = new Logdata(Constant.getGuid(), logininfo.getUsername(), logininfo.getRealname(),
                                    logininfo.getUnitname(), logininfo.getUnitid(), logininfo.getIpaddress(), dotime,
                                    "通过'批量采集-标准数据包'方式导入干部'"+cadreName+"'的人事档案信息异常，异常信息：该干部数据包已经接收过。",
                                    "批量采集", "异常","人事档案采集-批量采集");
                            this.addPo(logdata, "t_s_datalog", "id");*/

                        }
                    } else {
                        // 删除上传的数据包以及解压的数据包
                        result = "该干部在系统中不存在，请先登记干部信息后再采集目录信息!";
                        String zipPath = fileMir + "/" + "" + nameAndid + ".zip";
                        String imageName = "" + nameAndid + ".zip";
                        mediaService.addZipfile(formatname, imageName, zipPath, result);

                        FileTool.delDir(fileMir + "/" + nameAndid);
                        FileTool.deleteFile(fileMir + "/" + "" + nameAndid + ".zip");

                        //记录系统日志
                        Date dt = new Date();
                        String dotime = DateTools.pdateToString(dt, "yyyy-MM-dd HH:mm:ss");
                        String cadreName = "";
                        if (achildName != null) {
                            cadreName = achildName.getText().trim();
                        }
/*
                        Logdata logdata = new Logdata(Constant.getGuid(), logininfo.getUsername(), logininfo.getRealname(),
                                logininfo.getUnitname(), logininfo.getUnitid(), logininfo.getIpaddress(), dotime,
                                "通过'批量采集-标准数据包'方式导入干部'"+cadreName+"'的人事档案信息异常，异常信息：该干部在系统中不存在。",
                                "批量采集", "异常","人事档案采集-批量采集");
                        this.addPo(logdata, "t_s_datalog", "id");*/
                    }
                }
            }

            // 2.解析xml --end

        } catch (Exception e) {
            result = e.getMessage();
            e.printStackTrace();
        } finally {

        }
        return result;
    }

    /**
     * @param
     * @return
     * @description 多人增量采集(1.解压zip ； 2.解析excel ； 3.校验数据 ； 4.匹配电子文件 ； 5.插入目录表中形成关系 ； 6.成功后插入接收表记录中 。)
     * @author Fkw
     * @date 2019/10/28
     */
    @Override
    public String uploadMultipleAdd(Map<String, String> params, MultipartFile file, Logininfo logininfo) {
        String result = "";
        try {
            // 获取存储位置
            Store store = storeMapper.selectOne(new QueryWrapper<Store>().eq("datatype", "SOLDID"));
            //获取上传的文件名
            String fileFileName = file.getOriginalFilename();
            // 移动文件到指定路径(保存原数据包)--start
            String zipName = fileFileName.substring(0, fileFileName.lastIndexOf("."));
            String timeStamp = DateUtil.getDateFormatStr("yyyyMMddHHmmss", new Date());
            String fileMir = store.getLocation() + "/增量数据包/" + timeStamp + "/";
            File directory = new File(fileMir);
            if (!directory.exists())
                directory.mkdirs();
            String absolutePath = fileMir + fileFileName;
            long startMili = System.currentTimeMillis();
            InputStream is = file.getInputStream();
            OutputStream os = new FileOutputStream(absolutePath);
            byte[] bytefer = new byte[1024];
            int length = 0;
            while ((length = is.read(bytefer)) > 0) {
                os.write(bytefer, 0, length);
            }
            os.close();
            is.close();
            long endMili = System.currentTimeMillis();
            System.out.println("复制文件时间" + (endMili - startMili));
            // 移动文件到指定路径(保存原数据包)--end

            // 1、解压数据包--start
            ZipOption opt = new ZipOption();
            opt.extZipFileList(absolutePath, fileMir);
            // 1、解压数据包--end

            // 2.解析excel --start
            String dirExcelPath = fileMir + "/" + zipName;
            String excelName = traverseExcelFile(dirExcelPath);
            List<Map<String, String>> list = ExcelReader.readExcelDatas(dirExcelPath + "/" + excelName);
            File[] imageFiles = FileTool.getChildFiles(dirExcelPath);
            //2.解析excel --end

            //3.校验数据--start
            int imageNumber = imageFiles.length;
            if (imageNumber == 0) {
                result = "没有需要挂接的电子图片,请核对后再上传!";
                //删除上传的数据包以及解压的数据包
                FileTool.delDir(fileMir);
                FileTool.deleteFile(fileMir + "/" + fileFileName);
                return result;
            } else {
                String idNumbers = "";
                String pagenumber = params.get("pagenumber").toString();
                if (!"".equals(pagenumber)) {//采集界面有填写数量
                    if (Integer.parseInt(pagenumber) * (list.size()) != imageNumber) {
                        result = "电子图片的数量与填写的电子文件数量不符,请核对后再上传!";
                        mediaService.addZipfile("增量包", fileFileName, absolutePath, result);
                        //删除上传的数据包以及解压的数据包
                        FileTool.delDir(fileMir);
                        FileTool.deleteFile(fileMir + "/" + fileFileName);
                        return result;
                    }
                    int excelPageTotal = 0;
                    String isExist = "";
                    if (!list.isEmpty()) {
                        for (int i = 0; i < list.size(); i++) {
                            Map<String, String> map = list.get(i);
                            if (!"".equals(map.get("page"))) {
                                excelPageTotal += Integer.parseInt(map.get("page"));
                            }
                            String idNumber = map.get("code").toString();
                            String cadreName = map.get("name").toString();
                            List listCadre = cadreMapper.selectList(new QueryWrapper<Cadre>().eq("idnumber", idNumber));
                            if (listCadre.isEmpty()) {
                                if ("".equals(isExist)) {
                                    isExist = "干部'" + cadreName + "'不存在;";
                                } else {
                                    isExist += "干部'" + cadreName + "'不存在;";
                                }
                            } else {
                                if ("".equals(idNumbers)) {
                                    idNumbers = idNumber;
                                } else {
                                    idNumbers += "," + idNumber;
                                }
                            }
                        }
                        if (!"".equals(isExist)) {
                            result = isExist;
                            mediaService.addZipfile("增量包", fileFileName, absolutePath, result);
                            //删除上传的数据包以及解压的数据包
                            FileTool.delDir(fileMir);
                            FileTool.deleteFile(fileMir + "/" + fileFileName);
                            return result;
                        }
                    } else {
                        result = "excel模板中没有需要导入的数据!";
                        mediaService.addZipfile("增量包", fileFileName, absolutePath, result);
                        //删除上传的数据包以及解压的数据包
                        FileTool.delDir(fileMir);
                        FileTool.deleteFile(fileMir + "/" + fileFileName);
                        return result;
                    }
                    String returnResult = this.addCadreCataInfoMultiple(list, imageFiles, params.get("cataname").toString(), params.get("catacode").toString(), pagenumber);
                    //如果是档案管理模块批量采集，形成永久库文件
                    if ("gl".equals(params.get("mudule"))) {
                        if (!"".equals(idNumbers)) {
                            mediaService.strucEepFile("", idNumbers, logininfo);
                        }
                    }
                    if (!"".equals(returnResult)) {
                        //删除上传的数据包以及解压的数据包
                        result = returnResult;
                        mediaService.addZipfile("增量包", fileFileName, absolutePath, returnResult);
                        //删除上传的数据包以及解压的数据包
                        FileTool.delDir(fileMir);
                        FileTool.deleteFile(fileMir + "/" + fileFileName);
                        return result;
                    } else {
                        //上传增量包信息到表中
                        mediaService.addZipfile("增量包", fileFileName, absolutePath, "");
                        //删除解压后的文件夹
                        FileTool.delDir(dirExcelPath);
                    }
                } else {//采集界面没有填写数量，获取excel中'页数'字段的值
                    int excelPageTotal = 0;
                    String isExist = "";
                    if (!list.isEmpty()) {
                        for (int i = 0; i < list.size(); i++) {
                            Map<String, String> map = list.get(i);
                            if (!"".equals(map.get("page"))) {
                                excelPageTotal += Integer.parseInt(map.get("page"));
                            }
                            String idNumber = map.get("code").toString();
                            String cadreName = map.get("name").toString();
                            List listCadre = cadreMapper.selectList(new QueryWrapper<Cadre>().eq("idnumber", idNumber));
                            if (listCadre.isEmpty()) {
                                if ("".equals(isExist)) {
                                    isExist = "干部'" + cadreName + "'不存在;";
                                } else {
                                    isExist += "干部'" + cadreName + "'不存在;";
                                }
                            } else {
                                if ("".equals(idNumbers)) {
                                    idNumbers = idNumber;
                                } else {
                                    idNumbers += "," + idNumber;
                                }
                            }
                        }
                        if (!"".equals(isExist)) {
                            result = isExist;
                            mediaService.addZipfile("增量包", fileFileName, absolutePath, result);
                            //删除上传的数据包以及解压的数据包
                            FileTool.delDir(fileMir);
                            FileTool.deleteFile(fileMir + "/" + fileFileName);
                            return result;
                        }
                        if (excelPageTotal != imageNumber) {
                            result = "电子图片的数量与填写的电子文件数量不符,请核对后再上传!";
                            mediaService.addZipfile("增量包", fileFileName, absolutePath, result);
                            //删除上传的数据包以及解压的数据包
                            FileTool.delDir(fileMir);
                            FileTool.deleteFile(fileMir + "/" + fileFileName);
                            return result;
                        }

                        //4.匹配电子文件；5.插入目录表中形成关系；---start
                        String returnResult = this.addCadreCataInfoMultiple(list, imageFiles, params.get("cataname").toString(), params.get("catacode").toString(), pagenumber);
                        //如果是档案管理模块批量采集，形成永久库文件
                        if ("gl".equals(params.get("mudule"))) {
                            if (!"".equals(idNumbers)) {
                                mediaService.strucEepFile("", idNumbers, logininfo);
                            }
                        }
                        if (!"".equals(returnResult)) {
                            //删除上传的数据包以及解压的数据包
                            result = returnResult;
                            mediaService.addZipfile("增量包", fileFileName, absolutePath, returnResult);
                            FileTool.deleteFile(fileMir + "/" + fileFileName);
                            FileTool.delDir(fileMir);
                            return result;
                        } else {
                            //上传增量包信息到表中
                            mediaService.addZipfile("增量包", fileFileName, absolutePath, "");
                            //删除解压后的文件夹
                            FileTool.delDir(dirExcelPath);
                        }
                    } else {
                        result = "excel模板中没有需要导入的数据!";
                        mediaService.addZipfile("增量包", fileFileName, absolutePath, result);
                        //删除上传的数据包以及解压的数据包
                        FileTool.deleteFile(fileMir + "/" + fileFileName);
                        FileTool.delDir(fileMir);
                        return result;
                    }
                }
            }
            // 2.解析excel --end
        } catch (Exception e) {
            result = e.getMessage();
            e.printStackTrace();
        } finally {

        }
        return result;
    }


    /**
     * 查询指定文件夹下面的excel文件
     *
     * @param direPath
     * @return
     * @throws Exception
     */
    public String traverseExcelFile(String direPath) throws Exception {
        String excelName = "";
        try {
            File file = new File(direPath);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File fi : files) {
                    if (fi.getName().indexOf(".xls") > 0 || fi.getName().indexOf(".xlsx") > 0) {
                        excelName = fi.getName();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return excelName;
    }


    /**
     * 增量采集功能 -- 根据excel数据匹配电子文件插入到对应的干部中
     *
     * @param list
     * @param files
     * @return
     * @throws Exception
     */
    public String addCadreCataInfoMultiple(List<Map<String, String>> list, File[] files, String cataname, String catacode, String pagenumber) throws Exception {
        String result = "";
        try {
            int count = 0;
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> map = list.get(i);
                String cadreCode = map.get("code").toString();
                String cataTitle = cataname;
                String cataYear = map.get("year").toString();
                String cataMonth = map.get("month");
                String cataDay = map.get("day");
                String pageNumber = map.get("page");

                Catalogue catalogue = new Catalogue();
                catalogue.setId(CommonUtil.getGuid());
                catalogue.setSortcode(catacode);
                catalogue.setIdnumber(cadreCode);
                catalogue.setCatatitle(cataTitle);
                catalogue.setCatayear(cataYear);
                catalogue.setCatamonth(cataMonth);
                catalogue.setCataday(cataDay);
                catalogue.setPagenumber(pageNumber);

                if (!"".equals(pagenumber)) {
                    catalogue.setPagenumber(pagenumber);
                    String cataId = catalogueService.addCatalogueMultiple(catalogue);

                    //组装对应的电子图片
                    File[] cataImageFiles = this.getImageFiles(files, count, Integer.parseInt(pagenumber));
                    //挂接目录对应的电子图片
                    mediaService.addImageMultiple(cataImageFiles, cataId, cadreCode);
                    count += Integer.parseInt(pagenumber);
                } else {
                    String cataId = catalogueService.addCatalogueMultiple(catalogue);
                    //组装对应的电子图片
                    File[] cataImageFiles = this.getImageFiles(files, count, Integer.parseInt(pageNumber));
                    mediaService.addImageMultiple(cataImageFiles, cataId, cadreCode);
                    count += Integer.parseInt(pageNumber);
                }
            }
        } catch (Exception e) {
            result = e.getMessage();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 检查数据包是否上传过(标准数据包接收功能)
     *
     * @param
     * @return
     * @throws Exception
     */
    public boolean checkZipData(String idnumber) throws Exception {
        boolean result = true;
        try {
            List list = commonMapper.selectFilterSql("*", "t_er_stdziplist", "erid = '" + idnumber + "' and (message is null or message = '')");
            if (!list.isEmpty()) {
                result = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 截取每个目录
     *
     * @param files       电子图片总集合
     * @param startNumber 开始游标
     * @param count       数量
     * @return
     * @throws Exception
     */
    public File[] getImageFiles(File[] files, int startNumber, int count) throws Exception {
        File[] imageFile = new File[count];
        try {
            int j = 0;
            for (int i = startNumber; i < (startNumber + count); i++) {
                imageFile[j] = files[i];
                j++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    /**
     * 获取xml中目录条目信息
     *
     * @param nodes
     * @return
     * @throws Exception
     */
    public Map getXMLinfo(List nodes) throws Exception {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;
        String returnMessage = "";
        Map map = new HashMap();
        try {
            for (int i = 0; i < nodes.size(); i++) {
                Element element = (Element) nodes.get(i);
                String message = "";
                // 类号
                Map mapSort = this.getxmlNodeValue(element, "类号", i + 1);
                String sortCode = mapSort.get("value").toString().trim();
                message += mapSort.get("returnMessage").toString().trim();
                // 序号
                Map mapSeq = this.getxmlNodeValue(element, "序号", i + 1);
                String sequece = mapSeq.get("value").toString().trim();
                message += mapSeq.get("returnMessage").toString().trim();
                // 材料名称
                Map mapName = this.getxmlNodeValue(element, "材料名称", i + 1);
                String name = mapName.get("value").toString().trim();
                message += mapName.get("returnMessage").toString().trim();
                // 材料形成时间
                String year = "";
                String month = "";
                String day = "";
                Map mapDate = this.getxmlNodeValue(element, "材料形成时间", i + 1);
                String date = mapDate.get("value").toString();
                if ("".equals(mapDate.get("returnMessage").toString().trim()) && date.length() == 8) {
                    year = date.substring(0, 4);
                    month = date.substring(4, 6);
                    day = date.substring(6, 8);
                } else if ("".equals(mapDate.get("returnMessage").toString().trim()) && date.length() == 6) {
                    year = date.substring(0, 4);
                    month = date.substring(4, 6);
                }
                message += mapDate.get("returnMessage").toString().trim();
                // 页数
                Map mapPage = this.getxmlNodeValue(element, "页数", i + 1);
                String page = mapPage.get("value").toString().trim();
                message += mapPage.get("returnMessage").toString().trim();
                // 备注
                Map mapBak = this.getxmlNodeValue(element, "备注", i + 1);
                String bak = mapBak.get("value").toString().trim();
                message += mapBak.get("returnMessage").toString().trim();
                // 原始图片名称
                Map mapImage = this.getxmlNodeValue(element, "原始图像数据", i + 1);
                String image = mapImage.get("value").toString().trim();
                message += mapImage.get("returnMessage").toString().trim();

                //if ("".equals(message)) {
                jsonObject = new JSONObject();
                jsonObject.put("sortcode", sortCode);
                jsonObject.put("catatitle", name);
                jsonObject.put("catayear", year);
                jsonObject.put("catamonth", month);
                jsonObject.put("cataday", day);
                jsonObject.put("pagenumber", page);
                jsonObject.put("bak", bak);
                jsonObject.put("sequece", sequece);
                jsonObject.put("imageName", image);

                jsonArray.add(jsonObject);

                //returnMessage += message;
                //}
            }
            map.put("cataJsonArray", jsonArray);
            map.put("message", returnMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }


    /**
     * 根据节点名称获取节点值
     *
     * @param nodeName
     * @return
     * @throws Exception
     */
    public Map getxmlNodeValue(Element element, String nodeName, int count) throws Exception {
        Map map = new HashMap();
        String returnMessage = "";
        String notBlank = "类号,序号,材料名称,材料形成时间,页数";// 必填值
        try {
            List list = element.getChildren(nodeName);
            String value = "";
            if (!list.isEmpty()) {
                if (list.size() == 1) {
                    Element Child = element.getChild(nodeName);
                    if (Child == null) {
                        returnMessage += "第" + count + "个档案目录条目中'" + nodeName + "'不存在！";
                    } else {
                        value = Child.getText().trim();
                        if (notBlank.contains(nodeName)) {
                            if ("".equals(value)) {
                                returnMessage += "第" + count + "个档案目录条目中'" + nodeName + "'为空！";
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        Element Child = (Element) list.get(i);
                        if (Child == null) {
                            returnMessage += "第" + count + "个档案目录条目中'" + nodeName + "'不存在！";
                        } else {
                            if ("".equals(value)) {
                                value = Child.getText().trim();
                            } else {
                                value += "," + Child.getText().trim();
                            }

                            if (notBlank.contains(nodeName)) {
                                if ("".equals(value)) {
                                    returnMessage += "第" + count + "个档案目录条目中'" + nodeName + "'为空！";
                                }
                            }
                        }
                    }
                }
            }

            map.put("value", value);
            map.put("returnMessage", returnMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 批量挂接功能----插入目录条目信息以及挂接电子图片
     *
     * @param jsonArray 目录信息json数组
     * @param imagePath 电子原始图像数据路径文件夹
     * @return
     * @throws Exception
     */
    public String addCadreCataInfo(JSONArray jsonArray, String imagePath, String idNumber, String cadreCode)
            throws Exception {
        String result = "";
        try {
            Iterator<Object> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                JSONObject obj = (JSONObject) iterator.next();
                String sortCode = obj.getString("sortcode");
                if (!sortCode.contains("-")) {
                    SwitchNumberUtil switchN = new SwitchNumberUtil();
                    sortCode = String.valueOf(switchN.getIntegerByNumberStr(sortCode));
                }
                String cataTitle = obj.getString("catatitle");
                String cataYear = obj.getString("catayear");
                String cataMonth = obj.getString("catamonth");
                String cataDay = obj.getString("cataday");
                String pageNumber = obj.getString("pagenumber");
                String bak = obj.getString("bak");
                String sequece = obj.getString("sequece");
                Catalogue catalogue = new Catalogue();
                catalogue.setSortcode(sortCode);
                catalogue.setIdnumber(idNumber);
                catalogue.setCatatitle(cataTitle);
                catalogue.setCatayear(cataYear);
                catalogue.setCatamonth(cataMonth);
                catalogue.setCataday(cataDay);
                catalogue.setPagenumber(pageNumber);
                catalogue.setBak(bak);
                catalogue.setSequence(sequece);
                catalogue.setId(CommonUtil.getGuid());
                String cataId = catalogueService.addCatalogue(catalogue);

                // 挂接电子文件
                String imageNames = obj.getString("imageName");
                String[] imageNameArr = imageNames.split(",");
                for (String imageName : imageNameArr) {
                    String path = imagePath + imageName;// 原始文件路径
                    File imageFile = new File(path);
                    String imageFilePath = this.struFilePathBycadreId(idNumber, cataId);
                    String storeitemid = mediaService.saveFile(imageFilePath, imageName, imageFile, cataId, "SOURCE");
                    DES des = new DES();

                    MediaSource mediaSource = new MediaSource();
                    mediaSource.setFilename(imageName);
                    if ("".equals(imageName)) {
                        System.out.println("文件名为空");
                    }
                    mediaSource.setDatatype("SOURCE");
                    mediaSource.setFilesize((int) imageFile.length());
                    mediaSource.setStoreitemid(storeitemid);
                    mediaSource.setErid(cataId);
                    mediaSource.setDocumenttype("MAINFILE");
                    mediaSource.setHashcode(des.getEncryptedString(ReverseEncript.Encrypt(imageFile, "SHA-1")));
                    mediaService.addFile(mediaSource);
                }
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
    public String struFilePathBycadreId(String idNumber, String sortId) {
        String path = "";
        try {
            String cadreCode = "";// 干部编号
            String unitName = "";//
            String name = "";
            List listCadre = cadreMapper.selectList(new QueryWrapper<Cadre>().eq("idnumber", idNumber));
            if (!listCadre.isEmpty()) {
                Cadre cadre = (Cadre) listCadre.get(0);
                unitName = cadre.getUnitname();
                idNumber = cadre.getIdnumber();
                name = cadre.getName();
            }
            path = unitName + "/" + name + idNumber + "/" + sortId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

}
