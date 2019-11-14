package com.titansoft.utils.util;

/**
 * @author YLC
 */

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ZipOption {

    /**
     * 取得指定目录下的所有文件列表，包括子目录.
     *
     * @param baseDir File 指定的目录
     * @return 包含java.io.File的List
     */
    public List<File> getSubFiles(File baseDir) {
        List<File> ret = new ArrayList<File>();
        // 判断文件是否存在
        if (baseDir.exists()) {
            File[] tmp = baseDir.listFiles();
            for (int i = 0; i < tmp.length; i++) {
                if (tmp[i].isFile()) {
                    ret.add(tmp[i]);
                }
                if (tmp[i].isDirectory()) {
                    ret.addAll(getSubFiles(tmp[i]));
                }
            }
            return ret;
        } else {
            return null;
        }
    }

    /**
     * 取得指定目录下的所有包含指定后缀的文件,不包含子目录，
     *
     * @param baseDir File 指定的目录
     * @param endsStr 文件后缀名
     * @return 包含java.io.File的List
     */
    public List<File> getFiles(File baseDir, String endsStr) {
        List<File> ret = new ArrayList<File>();
        File[] tmp = baseDir.listFiles();
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i].isFile()) {
                String name = tmp[i].getName();
                if (name.toLowerCase().endsWith("." + endsStr)) {
                    ret.add(tmp[i]);
                }
            }
        }
        return ret;
    }

    /**
     * 递归调用 取得指定目录下的所有包含指定后缀的文件,含子目录
     *
     * @param baseDir File 指定的目录
     * @param endsStr 文件后缀名
     * @return 包含java.io.File的List
     */
    public List<File> getallFiles(File baseDir, String endsStr) {
        List<File> ret = new ArrayList<File>();
        File[] tmp = baseDir.listFiles();
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i].isFile()) {
                String name = tmp[i].getName();
                if (name.toLowerCase().endsWith("." + endsStr)) {
                    ret.add(tmp[i]);
                }
            } else if (tmp[i].isDirectory()) {
                ret.addAll(getallFiles(tmp[i], endsStr));
            }
        }
        return ret;
    }

    /**
     * 给定根目录，返回一个相对路径所对应的实际文件名.
     *
     * @param baseDir     指定根目录
     * @param absFileName 相对路径名，来自于ZipEntry中的name
     * @return java.io.File 实际的文件
     */
    private File getRealFileName(String baseDir, String absFileName) {
        String[] dirs = absFileName.split("/");
        File ret = new File(baseDir);
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                ret = new File(ret, dirs[i]);
            }
        }
        if (!ret.exists()) {
            ret.mkdirs();
        }
        ret = new File(ret, dirs[dirs.length - 1]);
        return ret;
    }

    /**
     * 复制单个文件 复制后文件夹目录必须已经存在
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static boolean copyFile(String oldPath, String newPath) {
        boolean bool = false;
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
            }
            File newFile = new File(newPath);
            bool = newFile.exists();
            newFile = null;
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }
        return bool;
    }

    /**
     * 删除路径下的所有文件,不删除根目录
     *
     * @param filepath 文件或目录
     * @throws IOException
     */
    public static void del1(String filepath) throws IOException {

        File f = new File(filepath);// 定义文件路径
        if (f.exists() && f.isDirectory()) {// 判断是文件还是目录
            if (f.listFiles().length == 0) {// 若目录下没有文件则直接删除
                f.delete();
            } else {// 若有则把文件放进数组，并判断是否有下级目录
                File delFile[] = f.listFiles();
                int i = f.listFiles().length;
                for (int j = 0; j < i; j++) {
                    if (delFile[j].isDirectory()) {
                        del1(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
                    }
                    delFile[j].delete();// 删除文件
                }
            }
        } else if (f.exists() && !f.isDirectory()) {
            f.delete();
        }
    }

    /**
     * 删除路径下的所有文件,删除根目录
     *
     * @param filepath
     * @throws IOException
     */
    public static void del(String filepath) {
        try {
            File f = new File(filepath);// 定义文件路径
            if (f.exists() && f.isDirectory()) {// 判断是文件还是目录
                if (f.listFiles().length == 0) {// 若目录下没有文件则直接删除
                    f.delete();
                } else {// 若有则把文件放进数组，并判断是否有下级目录
                    File delFile[] = f.listFiles();
                    int i = f.listFiles().length;
                    for (int j = 0; j < i; j++) {
                        if (delFile[j].isDirectory()) {
                            del(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
                        }
                        delFile[j].delete();// 删除文件
                    }
                }
                del(filepath);// 递归调用
            } else if (f.exists() && !f.isDirectory()) {
                f.delete();
            }
        } catch (Exception e) {

        }
    }

    /**
     * 解压ZIP文件
     *
     * @param zipFileName String 为需要解压的zip文件
     * @param extPlace    String 为解压后文件的存放路径
     */
    public static void extZipFileList(String zipFileName, String extPlace)
            throws Exception {
        // 验证ZIP压缩文件是否有效
        File fileZip = new File(zipFileName);
        boolean exists = fileZip.exists();// 判断文件/文件夹是否存在
        if (exists) {
            // 创建extPlace目录
            File dirs = new File(extPlace);
            dirs.mkdirs();
            // 请空目录
            // ZipOption.del(dirs.getAbsolutePath());

            ZipFile zipFile = new ZipFile(zipFileName, "gbk");
            Enumeration e = zipFile.getEntries();
            ZipEntry zipEntry = null;
            while (e.hasMoreElements()) {
                zipEntry = (ZipEntry) e.nextElement();
                String entryName = zipEntry.getName().replace(
                        File.separatorChar, '/');
                String names[] = entryName.split("/");
                int length = names.length;
                String path = extPlace;
                for (int v = 0; v < length; v++) {
                    if (v < length - 1) {
                        path += names[v] + "/";
                        new File(path).mkdir();
                    } else { // 最后一个
                        if (entryName.endsWith("/")) { // 为目录,则创建文件夹
                            new File(extPlace + entryName).mkdir();
                        } else {
                            InputStream in = zipFile.getInputStream(zipEntry);
                            OutputStream os = new FileOutputStream(new File(
                                    extPlace + entryName));
                            byte[] buf = new byte[1024];
                            int len;
                            while ((len = in.read(buf)) > 0) {
                                os.write(buf, 0, len);
                            }
                            in.close();
                            os.close();
                        }
                    }
                }
            }
            zipFile.close();
        } else {

        }
    }


    /**
     * 压缩电子文件为ZIP包
     *
     * @param directory   需要压缩的目录
     * @param zipFileName ZIP压缩文件完整路径名
     * @return
     */
    public static boolean expZip(String directory, String zipFileName) {
        boolean bool = false;
        File file = new File(directory);
        ZipOption zipOp = new ZipOption();
        boolean validate = zipFileName.toLowerCase().endsWith(".zip");
        if (!file.exists() || !validate || !file.isDirectory()) {
            return false;
        } else {

            int dirleng = directory.length();

            directory = directory.replace(File.separatorChar, '/');
            zipFileName = zipFileName.replace(File.separatorChar, '/');
            int x = zipFileName.lastIndexOf("/");
            if (x <= -1) {
                return false;
            } else {
                String zipdir = zipFileName.substring(0, x);
                File zipdirs = new File(zipdir);
                zipdirs.mkdirs();
            }

            try {
                OutputStream os = new FileOutputStream(zipFileName);
                BufferedOutputStream bs = new BufferedOutputStream(os);
                ZipOutputStream zo = new ZipOutputStream(bs);
                ZipOption.zip(directory, new File(directory), zo, true, true);
                zo.closeEntry();
                zo.close();

                File exit = new File(zipFileName);
                bool = exit.exists();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return bool;
    }

    /**
     * @param path          要压缩的路径, 可以是目录, 也可以是文件.
     * @param basePath      如果path是目录,它一般为new File(path), 作用是:使输出的zip文件以此目录为根目录,
     *                      如果为null它只压缩文件, 不解压目录.
     * @param zo            压缩输出流
     * @param isRecursive   是否递归
     * @param isOutBlankDir 是否输出空目录, 要使输出空目录为true,同时baseFile不为null.
     * @throws IOException
     */
    public static void zip(String path, File basePath, ZipOutputStream zo,
                           boolean isRecursive, boolean isOutBlankDir) throws IOException {

        File inFile = new File(path);

        File[] files = new File[0];
        if (inFile.isDirectory()) { // 是目录
            files = inFile.listFiles();
        } else if (inFile.isFile()) { // 是文件
            files = new File[1];
            files[0] = inFile;
        }
        byte[] buf = new byte[1024];
        int len;
        // System.out.println("baseFile: "+baseFile.getPath());
        for (int i = 0; i < files.length; i++) {
            String pathName = "";
            if (basePath != null) {
                if (basePath.isDirectory()) {
                    pathName = files[i].getPath().substring(
                            basePath.getPath().length() + 1);
                } else {// 文件
                    pathName = files[i].getPath().substring(
                            basePath.getParent().length() + 1);
                }
            } else {
                pathName = files[i].getName();
            }
            //System.out.println(pathName);
            if (files[i].isDirectory()) {
                if (isOutBlankDir && basePath != null) {
                    zo.putNextEntry(new ZipEntry(pathName + "/")); // 可以使空目录也放进去
                }
                if (isRecursive) { // 递归
                    zip(files[i].getPath(), basePath, zo, isRecursive,
                            isOutBlankDir);
                }
            } else {
                FileInputStream fin = new FileInputStream(files[i]);
                zo.putNextEntry(new ZipEntry(pathName));
                while ((len = fin.read(buf)) > 0) {
                    zo.write(buf, 0, len);
                }
                fin.close();
            }
        }
    }

    public static void main(String[] args) {


        try {
            extZipFileList("D:/高碧峰440111681202009.zip", "D:/高碧峰440111681202009/");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
