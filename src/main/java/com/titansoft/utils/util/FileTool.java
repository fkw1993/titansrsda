package com.titansoft.utils.util;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class FileTool {

    /**
     * Method 递规创建文件夹
     *
     * @param folderPath
     * @param targetpath
     * @return String
     */
    public static String createFolders(String folderPath, String targetpath) {
        String txts = targetpath;
        try {
            String txt = "";
            StringTokenizer st = new StringTokenizer(folderPath, "/");
            for (int i = 0; st.hasMoreTokens(); i++) {
                txt = st.nextToken().trim();
                txts = createFolder(txts + "/" + txt);
            }
        } catch (Exception e) {

        }
        return txts;
    }

    /**
     * Method 递规创建文件夹
     *
     * @param folderPath
     * @return String
     */
    private static String createFolder(String folderPath) {
        String txt = folderPath;
        try {
            File myFilePath = new File(txt);
            txt = folderPath;
            if (!myFilePath.exists()) {
                myFilePath.mkdir();
            }
            myFilePath = null;
        } catch (Exception e) {

        }
        return txt;
    }




    /***************************************************
     *功能:日期转换字符串
     ***************************************************/

    public String pdateToString(Date dt, String strFormat) {
        SimpleDateFormat sdFormat = new SimpleDateFormat(strFormat);
        String str = "";
        try {
            str = sdFormat.format(dt);
        } catch (Exception e) {
            String s = "";
            return s;
        }

        return str;
    }

    /**
     * 获取文件扩展名
     *
     * @param ss 文件名，例如“new1.txt”
     * @return txt, 没有返回""
     */
    public static String getExts(String ss) {
        int i;
        i = ss.lastIndexOf(".");
        String extstr = "";
        if (i > 0) {
            extstr = ss.substring(i + 1).trim();
        }
        return extstr;
    }

    //      取主文件名
    public String getMainFile(String ss) {
        int i;
        i = ss.lastIndexOf(".");
        String fileName = "";
        if (i > 0) {
            fileName = ss.substring(0, i).trim();
        }
        return fileName;
    }

    //取文件名
    public String getMainFiles(String ss) {
        return ss.substring(ss.lastIndexOf("/") + 1);
    }

    //取文件名
    public String getMainFilesmlzx(String ss) {
        return ss.substring(ss.lastIndexOf("\\") + 1);
    }

    /*********************************************************************
     *过程说明：复制文件
     *参数说明：sourFile源文件；descFile生成的文件
     *作    者：A-XIONG
     *日    期：200504
     *********************************************************************/
    public static boolean copyFile(String sourFile, String descFile) {
        File vfileold = new File(sourFile);
        File vfilenew = new File(descFile);
        if (vfileold.exists()) {
            if (vfilenew.exists())
                vfilenew.delete();
            FileInputStream in1 = null;
            FileOutputStream out1 = null;
            try {
                in1 = new FileInputStream(vfileold);
                out1 = new FileOutputStream(vfilenew);

                byte[] bytes = new byte[1024];
                int c;
                while ((c = in1.read(bytes)) != -1) {
                    out1.write(bytes, 0, c);
                }
                in1.close();
                out1.close();
            } catch (Exception ex) {
                try {
                    in1.close();
                    out1.close();
                } catch (Exception aa) {
                    in1 = null;
                    out1 = null;
                    vfileold = null;
                    vfilenew = null;
                }
                vfileold = null;
                vfilenew = null;
                return false;
            }
            vfileold = null;
            vfilenew = null;
        }
        return true;
    }

    /**
     * 重画库房平面图根据条件输出库房超标高亮显示图
     *
     * @param list    构造对象
     * @param list    构造对象楼层数
     * @param webPath 多煤体path
     */
    public void houseDisplay(List list, String layer, String webPath) {
        String x = "";
        String y = "";
        String title = "";
        try {
            String file = webPath + "/house/source/kfpicture-" + layer + ".jpg";
            BufferedImage image = createImage(file);
            //画圈
            Graphics2D g = (Graphics2D) image.getGraphics();
            g.setColor(Color.green);// 设置背景色
            //绘制指定矩形的边框。
         /*for(int i =0;i<2;i++){
             g.drawRect(x, y--, w, h);
             w +=2;
             h +=2;
         }*/
            for (Iterator it = list.iterator(); it.hasNext(); ) {
                Map hash = (HashMap) it.next();
                x = (hash.get("x") == null) ? "0" : hash.get("x").toString().trim();
                y = (hash.get("y") == null) ? "0" : hash.get("y").toString().trim();
                title = (hash.get("title") == null) ? "" : hash.get("title").toString().trim();
                g.drawString(title, Integer.parseInt(x), Integer.parseInt(y) + 30);
                //g.draw3DRect(x, y, w, h,true);
            }
            OutputStream os = new FileOutputStream(webPath + "/house/target/kfpicture-" + layer + ".jpg");
            writerImage(os, "jpg", image);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ImageFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getFileBytes(InputStream fis) {
        byte[] buffer = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getFileBytes(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }


    /**
     * 输出格式
     *
     * @param os
     * @param type
     * @param bi
     * @throws IOException
     */
    private static void writerImage(OutputStream os, String type, BufferedImage bi) throws IOException {
        ImageOutputStream ios = null;
        try {

            if (type == null || type.length() == 0 || type.equalsIgnoreCase("jpg")) {
                Iterator writers = ImageIO.getImageWritersByFormatName(type);//这代码也可以输出jpg格式
                ImageWriter writer = (ImageWriter) writers.next();
                ios = ImageIO.createImageOutputStream(os);
                writer.setOutput(ios);
                writer.write(bi);

            } else {
                //jpg
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
                encoder.encode(bi);
            }
        } finally {
            if (ios != null)
                ios.close();
        }
    }

    private BufferedImage createImage(String imageFile) throws Exception {
        InputStream is = null;
        is = new FileInputStream(imageFile);
        BufferedImage image = ImageIO.read(is);
        is.close();
        is = null;
        return image;
    }


    /***************************************************************************
     * 获取指定文件最后修改时间
     **************************************************************************/
    public static String getFileLastModifyTime(String filepath) {
        File f = new File(filepath);
        long time = f.lastModified();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        String datestr = cal.getTime().toLocaleString();
        return datestr;
    }

    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                File sourceFile = file[i];
                if (!new File(targetDir).isDirectory())
                    new File(targetDir).mkdirs();
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                String dir1 = sourceDir + "/" + file[i].getName();
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }

    //获取文件夹大小
    public static long getDirectiorySize(String sourceDir) throws IOException {
        File[] file = (new File(sourceDir)).listFiles();
        long size = 0;
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                File sourceFile = file[i];
                size += sourceFile.length();
            }
            if (file[i].isDirectory()) {
                String dir1 = sourceDir + "/" + file[i].getName();
                size += getDirectiorySize(dir1);
            }
        }
        return size;
    }


    /**
     * 获取文件夹里面子文件
     *
     * @param direPath
     * @return
     * @throws Exception
     */
    public static File[] getChildFiles(String direPath) throws Exception {
        File[] imageFiles = null;
        try {
            File file = new File(direPath);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File fi : files) {
                    if (fi.isDirectory()) {
                        imageFiles = fi.listFiles();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageFiles;
    }

    public static boolean copyFile(File src, File dst) {
        InputStream in = null;
        OutputStream out = null;
        int BUFFER_SIZE = 100 * 1024;
        try {
            in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(dst),
                    BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 从文件绝对路径字符串中拆分父目录及文件名
     *
     * @param pathFile 路径及文件名字符串
     * @return 返回数组｛父目录，文件名有｝
     */
    public static String[] getFileName(String pathFile) {
        String fileName, path;
        String file = pathFile.replace('\\', '/');
        int index = file.lastIndexOf('/');
        if (index >= 0) {
            fileName = file.substring(index + 1);
            path = file.substring(0, index);
        } else {
            path = "";
            fileName = file;
        }
        String[] rs = {path, fileName};
        return rs;
    }

    /**
     * 递归获取文件目中下的所有文件
     * 查询每一层目录下面的所有文件或文件夹，把它们的总和相加，只要遇见文件夹
     * 就把这个数量减一，如果不是文件夹，就说明这层已经检索完毕，文件的数量就是
     * 这层目录文件和文件夹数量总和减去文件夹的数量。
     *
     * @param f 是一个目录
     * @return 返回文件个数，初始值0
     */
    public static long getList(File f) {//递归求取目录文件个数
        long size = 0;
        File flist[] = f.listFiles();//返回的目录下的所有文件和文件夹，指的是一层。
        size = flist.length;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getList(flist[i]);
                size--;
            }
        }
        return size;
    }


    /**
     * 剪切条目到新地址
     *
     * @param oldpath 源地址
     * @param newpath 目标地址
     * @return boolean
     * @throws Exception
     */
    public static boolean cutDir(String oldpath, String newpath) throws Exception {
        File[] oldfile = (new File(oldpath)).listFiles();
        String dir1 = null;
        String newsname = null;
        boolean bl = false;
        long size = 0;
        for (int i = 0; i < oldfile.length; i++) {
            dir1 = oldpath + "/" + oldfile[i].getName();

            if (oldfile[i].isDirectory()) {
                newsname = newpath + "/" + oldfile[i].getName();
                new File(newsname).mkdirs();
                cutDir(dir1, newsname);

            } else {
                newsname = newpath + "/" + oldfile[i].getName();
                bl = oldfile[i].renameTo(new File(newsname));


            }
        }

        return bl;
    }

    /**
     * 删除路径下的所有文件,删除根目录
     *
     * @param filepath
     * @throws IOException
     */
    public static void delDir(String filepath) throws IOException {
        File f = new File(filepath);// 定义文件路径
        if (f.exists() && f.isDirectory()) {// 判断是文件还是目录
            if (f.listFiles().length == 0) {// 若目录下没有文件则直接删除
                f.delete();
            } else {// 若有则把文件放进数组，并判断是否有下级目录
                File delFile[] = f.listFiles();
                int i = f.listFiles().length;
                for (int j = 0; j < i; j++) {
                    if (delFile[j].isDirectory()) {
                        delDir(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
                    }
                    delFile[j].delete();// 删除文件
                }
                delFile = null;
                f.delete();
            }
        } else if (f.exists() && !f.isDirectory()) {
            f.delete();
        }
        f = null;
    }


    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 判断一个文件的最后一行是否为某字符串
     *
     * @param src 文件路径
     * @param key 判断是否最后一行的字符串
     *            是 则 返回 true 不是 则 返回 false
     */
    public static boolean checkLastLine(String src, String key) {
        int c;
        String line; // 读取新文件
        RandomAccessFile rf = null;
        boolean isEnd = false;
        try {
            rf = new RandomAccessFile(src, "r");
            long len = rf.length();
            long start = rf.getFilePointer();
            long nextend = start + len - 1;
            rf.seek(start + len - 1);
            while (nextend > start) {
                c = rf.read();
                line = rf.readLine();
                //判断读到的最后一行
                if ((c == '\n' || c == '\r') && line != null && !line.equals("")) {
                    if (line.equals(key)) {
                        isEnd = true;
                    }
                    break;
                }
                nextend--;
                rf.seek(nextend);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                rf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isEnd;
    }

    /**
     * 获取电子文件的基本属性
     * 计算机文件名、文件大小（字节）、文件格式、创建时间（精确到时分秒）、修改时间（默认就是上一次修改时间）
     *
     * @param file
     * @return
     */
    public static Map<String, String> getElecBaseAttr(File file) {
        Map<String, String> hasnmap = new HashMap<String, String>();
        String filename = file.getName();
        String size = file.length() + "";
        String exts = getExts(filename);
        String creatTime = null;
        String editTime = null;
        try {
            Path p = Paths.get(file.getAbsolutePath());
            BasicFileAttributes att = Files.readAttributes(p, BasicFileAttributes.class);
            //FileTime转日期，先把它变成long再变成date
            creatTime = DateUtil.getTime(new Date(att.creationTime().toMillis()));//lastAccessTime()和lastModifiedTime()
            editTime = DateUtil.getTime(new Date(att.lastModifiedTime().toMillis()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        hasnmap.put("计算机文件名", filename);
        hasnmap.put("文件大小", size);
        hasnmap.put("文件格式", exts);
        hasnmap.put("创建时间", creatTime);
        hasnmap.put("修改时间", editTime);
        return hasnmap;
    }


}







