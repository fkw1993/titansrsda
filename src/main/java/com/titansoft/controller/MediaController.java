package com.titansoft.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.titansoft.entity.Catalogue;
import com.titansoft.entity.cadre.Cadre;
import com.titansoft.entity.media.MediaSource;
import com.titansoft.entity.media.Store;
import com.titansoft.mapper.CadreMapper;
import com.titansoft.mapper.CatalogueMapper;
import com.titansoft.mapper.MediaSourceMapper;
import com.titansoft.mapper.StoreMapper;
import com.titansoft.service.MediaService;
import com.titansoft.utils.util.AESUtils;
import com.titansoft.utils.util.DES;
import com.titansoft.utils.util.ReverseEncript;
import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.crypto.CipherInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**电子文件处理Controller
 * @Author: Kevin
 * @Date: 2019/8/2 16:34
 */
@Controller
@RequestMapping("/media")
public class MediaController extends BaseController {
    @Autowired
    MediaService mediaService;
    @Autowired
    CatalogueMapper catalogueMapper;
    @Autowired
    CadreMapper cadreMapper;
    @Autowired
    MediaSourceMapper mediaSourceMapper;
    @Autowired
    StoreMapper storeMapper;


    static String key;

    static {
        try {
            key = AESUtils.getSecretKey("GZSGAT-GBRSDA");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param *                   @param file:
     * @param redirectAttributes:
     * @param request:
     * @param mediaSource:
     * @return
     * @description 上传目录的电子图片
     * @author Fkw
     * @date 2019/10/17
     */
    @RequestMapping("/uploadfile")
    public void uploadfile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, HttpServletRequest request, @ModelAttribute("mediaSource") MediaSource mediaSource) throws IOException {
        // MultipartFile是对当前上传的文件的封装，当要同时上传多个文件时，可以给定多个MultipartFile参数(数组)
        String datatype = mediaSource.getDatatype();
        if (mediaSource.getDocumenttype() == null) {
            mediaSource.setDocumenttype("MAINFILE");
        }
        boolean isReapt = mediaService.checkFileName(file.getOriginalFilename(), datatype, mediaSource.getErid());
        if (isReapt) {
            // throw new Exception("存在重复的原始文件,请检查！");
            super.toAjaxError("存在重复的原始文件,请检查！");
            return;
        }
        String filePath = createFilePath(mediaSource.getErid());
        String storeitemid = mediaService.saveFile(filePath, file.getOriginalFilename(), file, mediaSource.getErid(), datatype);
        DES des = new DES();
        mediaSource.setFilename(file.getOriginalFilename());
        mediaSource.setDatatype(datatype);
        mediaSource.setFilesize(Integer.valueOf((int) file.getSize()));
        mediaSource.setStoreitemid(storeitemid);
        mediaSource.setHashcode(des.getEncryptedString(ReverseEncript.Encrypt(file.getInputStream(), "SHA-1")));
        mediaService.addFile(mediaSource);


      /*  //过程信息
        sv.put("erid", mediaSource.getErid());
        sv.doService("erms.media.bs.MediaBS", "getCatalogueByErid");
        String catatitle=(String)sv.get("catatitle");
        String sortcode=(String)sv.get("sortcode");
        String sequence=(String)sv.get("sequence");
        writeProcess(logininfo, "目录编号<"+sortcode+"-"+sequence+">, 目录标题<"+catatitle+">,上传了电子图片："+fileFileName+"", idnumber);

        //记录系统日志
        Date dt=new Date();
        String dotime= DateTools.pdateToString(dt, "yyyy-MM-dd HH:mm:ss");
        Logdata logdata = new Logdata(Constant.getGuid(), logininfo.getUsername(), logininfo.getRealname(),
                logininfo.getUnitname(), logininfo.getUnitid(), logininfo.getIpaddress(), dotime,
                "目录编号<"+sortcode+"-"+sequence+">, 目录标题<"+catatitle+">,上传了电子图片："+fileFileName+"",
                "挂接扫描原件", "正常","人事档案采集-编辑-挂接扫描原件");
        this.addPo(logdata, "t_s_datalog", "id");*/

        result = "{\"success\":true}";
        super.toAjax(result);
    }

    /**
     * @param * @param sortId:
     * @return
     * @description 采集库路径
     * @author Fkw
     * @date 2019/8/3
     */
    public String createFilePath(String sortId) {
        String path = "";
        try {
            String cadreCode = "";// 干部编号
            String unitName = "";//
            String idNumber = "";
            String name = "";
            List list = catalogueMapper.selectList(new QueryWrapper<Catalogue>().eq("id", sortId));
            if (!list.isEmpty()) {
                Catalogue catalogue = (Catalogue) list.get(0);
                cadreCode = catalogue.getIdnumber();
            }
            if (cadreCode != null && !"".equals(cadreCode)) {
                List listCadre = cadreMapper.selectList(new QueryWrapper<Cadre>().eq("idnumber", cadreCode));
                if (!listCadre.isEmpty()) {
                    Cadre cadre = (Cadre) listCadre.get(0);
                    unitName = cadre.getUnitname();
                    idNumber = cadre.getIdnumber();
                    name = cadre.getName();
                }
            }
            path = unitName + "/" + name + idNumber + "/" + sortId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * @param * @param cataId:
     * @return
     * @description 判断数据库电子文件表是否有数据
     * @author Fkw
     * @date 2019/8/3
     */
    @RequestMapping(value = "/checkMediasourceIsExist", method = RequestMethod.POST)
    public void checkMediasourceIsExist(@RequestParam(required = false) String cataId) throws Exception {
        List mediaSourceList = mediaSourceMapper.selectList(new QueryWrapper<MediaSource>().eq("erid", cataId));
        boolean bool = false;
        if (mediaSourceList.size() > 0) {
            bool = true;
        } else {
            bool = false;
        }
        super.toAjax("{\"success\": " + bool + "}");
    }

    /**
     * @param *       @param sortId:
     * @param mudule:
     * @return
     * @description 查看图片
     * @author Fkw
     * @date 2019/8/3
     */
    @RequestMapping(value = "/browseImgfile", method = RequestMethod.POST)
    public void browseImgfile(@RequestParam(required = false) String sortId, @RequestParam(required = false) String mudule, @RequestParam(required = false) String sqcdid) throws Exception {
        //获取采集库跟路径
        Store store = storeMapper.selectOne(new QueryWrapper<Store>().eq("datatype", "SOURCE"));
        if ("ly".equals(mudule)) {//利用平台
            result = mediaService.getCataMediaPathLY(sortId, sqcdid, "SOURCE");
        } else {//管理平台
            // 获取文件具体存储位置
            result = mediaService.getCataMediaPath(sortId, mudule, "SOURCE");
        }
        toAjax(result);
    }

    /**
     * @param *          @param enFilePath:
     * @param mudule:
     * @param responses:
     * @return
     * @description 加载图片流
     * @author Fkw
     * @date 2019/10/17
     */
    @GetMapping(value = "/fileEcho")
    public void fileEcho(@RequestParam(required = false) String enFilePath, @RequestParam(required = false) String mudule, HttpServletResponse responses) {
        String enPath = enFilePath;
        enPath = enPath.substring(enPath.indexOf("-") + 1);
        String url = "";
        try {
            String dataType = "";
            if (mudule != null && "gl".equals(mudule)) {
                dataType = "EEP";
            } else {
                dataType = "OPTIMIZE";
            }
            Store store = storeMapper.selectOne(new QueryWrapper<Store>().eq("datatype", dataType));
            String location = store.getLocation();

            //解密
            byte[] inputData = AESUtils.parseHexStr2Byte(enPath);
            byte[] outputData = AESUtils.decrypt(inputData, key);
            String outputStr = new String(outputData);

            url = location + "/" + outputStr;

            //解密文件过程--start
            CipherInputStream cis = AESUtils.decryptedFileData(url);
            byte[] buffer = new byte[1024];
            int n = 0;
            responses.setContentType("multipart/form-data");
            responses.setContentType("image/*"); // 设置返回的文件类型
            OutputStream toClient = responses.getOutputStream(); // 得到向客户端输出二进制数据的对象

            while ((n = cis.read(buffer)) != -1) {
                toClient.write(buffer, 0, n);
            }
            cis.close();
            toClient.flush();
            toClient.close();
            //解密文件过程--end

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("图片不存在");
        }
    }


    @RequestMapping(value = "/getDownfile", method = RequestMethod.POST)
    public void getDownfile() throws Exception {
        String path = mediaService.getDownfile("EEP", "idnumber");
        String json = "{success: true,filepath:'" + path + "'}";
        super.toAjax(json);

    }


    /**
     * 批量采集的数据包下载
     */
    @RequestMapping(value = "/downDataZip", method = RequestMethod.GET)
    public void downDataZip(@RequestParam(required = false) String mudule) {
		/*String userdir = System.getProperty("user.dir").endsWith("bin") ? System.getProperty("user.dir")
				: System.getProperty("user.dir") + "/bin";*/
        String zippath = "";
        if ("standard".equals(mudule)) {//标准
            zippath = "/person/zip/标准数据包示例/李寻欢440400198710230123.zip";
        } else {//多人
            zippath = request.getSession().getServletContext().getRealPath("") + "person/zip/增量数据包示例/干部增量信息采集包.zip";
        }
        String downloadFilePath = request.getServletContext().getRealPath(zippath);
        String fileName = zippath.substring(zippath.lastIndexOf("/") + 1);// 被下载文件的名称
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            File file = new File(downloadFilePath);
            inputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            response.setContentType("application/x-download");
            // String fileName="shuiyin.rar";
            response.addHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(fileName, "UTF-8"));// 设置文件名
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载
     * del 1：代表下载完删除，0代表不删除  默认是0；
     *
     * @return
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(@RequestParam String filename, @RequestParam(required = false) String del) {
        try {
            String filenameweb = "";
            filename = java.net.URLDecoder.decode(filename, "UTF-8");
            filename = super.convercharajax(filename);
            filenameweb = super.convercharajax(filenameweb);
            String webPath = "";
            // 真正用户名
            String realFilename = "";
            String[] filenameArray = null;
           /* webPath = request.getRealPath("");
            filenameArray = filenameweb.split("[/]");
            realFilename = filenameArray[filenameArray.length - 1];
            filenameweb = webPath + "/" + filenameweb;
            filename = filenameweb;
*/
            int i = 65000;
            File file = new File(filename);
            FileInputStream fileinputstream = new FileInputStream(file);
            long l = file.length();
            if (l == 0) {
                l = 1;
            }
            int k = 0;
            byte abyte0[] = new byte[i];
            response.setContentType("application/x-msdownload");
            String m_contentDisposition;
            m_contentDisposition = new String();
            m_contentDisposition = null;
            response.setContentLength((int) l);
            m_contentDisposition = m_contentDisposition != null ? m_contentDisposition : "attachment;";
            try {
                // 取扩展名
                String extFilename = "";
                int a;
                a = realFilename.lastIndexOf(".");
                if (a > 0) {
                    extFilename = realFilename.substring(a + 1).toLowerCase().trim();
                }
                // 取主文件名
                String mainFilename = "";
                int b;
                b = realFilename.lastIndexOf(".");
                if (b > 0) {
                    mainFilename = realFilename.substring(0, b).toUpperCase().trim();
                }
                realFilename = mainFilename.substring(0, 16) + "." + extFilename;
                //realFilename = filename;
            } catch (Exception er) {

            }
            String agent = (String) request.getHeader("USER-AGENT");
            if (agent != null && agent.indexOf("MSIE") == -1) {
                realFilename = "=?UTF-8?B?" + (new String(Base64.encodeBase64(realFilename.getBytes("UTF-8")))) + "?=";
            } else {
                realFilename = new String(realFilename.getBytes("GB2312"), "ISO8859-1");
            }
            response.setHeader("Content-Disposition", m_contentDisposition + " filename=" + realFilename);//java.net.URLEncoder.encode(realFilename,"ISO8859-1"));
            while ((long) k < l) {
                int j = fileinputstream.read(abyte0, 0, i);
                k += j;
                if (j == -1) {
                    response.getOutputStream().write(abyte0, 0, 1);
                    k = 1;
                } else {
                    response.getOutputStream().write(abyte0, 0, j);
                }
            }
            fileinputstream.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if ("1".equals(del)) {
                File f = new File(filename);
                f.delete();
            }
        }
    }

}
