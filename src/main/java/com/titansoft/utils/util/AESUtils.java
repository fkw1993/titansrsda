package com.titansoft.utils.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.awt.*;
import java.io.*;
import java.security.*;
import java.util.Random;

/**
 * AES加密解密工具包
 *
 * @author jiaogan
 */
public class AESUtils {
    private static final Logger logger = LoggerFactory.getLogger(AESUtils.class);

    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 128;

    public static final String KEY = "GZSGAT-GBRSDA";

    /**
     * <p>
     * 生成随机密钥
     * </p>
     *
     * @return
     * @throws Exception
     */
    public static String getSecretKey() throws Exception {
        return getSecretKey(null);
    }

    /**
     * <p>
     * 生成密钥
     * </p>
     *
     * @param seed 密钥种子
     * @return
     * @throws Exception
     */
    public static String getSecretKey(String seed) {
        try {
            KeyGenerator keyGenerator = null;

            keyGenerator = KeyGenerator.getInstance(ALGORITHM);

            SecureRandom secureRandom;
            if (seed != null && !"".equals(seed)) {
                secureRandom = new SecureRandom(seed.getBytes());
            } else {
                secureRandom = new SecureRandom();
            }
            keyGenerator.init(KEY_SIZE, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            return Base64Utils.encode(secretKey.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>
     * 加密
     * </p>
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, String key) {
        try {
            Key k = toKey(Base64Utils.decode(key));
            byte[] raw = k.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error("加密失败：" + e.getMessage());
        }
        return null;
    }


    /**
     * 加密文件
     *
     * @param fileName          源文件路径
     * @param encryptedFileName 加密后文件
     */
    public static void encryptFile(String fileName, String encryptedFileName) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            FileOutputStream fos = new FileOutputStream(encryptedFileName);

            // 秘钥自动生成
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            Key key = keyGenerator.generateKey();

            byte[] keyValue = key.getEncoded();

            fos.write(keyValue);// 记录输入的加密密码的消息摘要

            SecretKeySpec encryKey = new SecretKeySpec(keyValue, "AES");// 加密秘钥

            byte[] ivValue = new byte[16];
            Random random = new Random(System.currentTimeMillis());
            random.nextBytes(ivValue);
            IvParameterSpec iv = new IvParameterSpec(ivValue);// 获取系统时间作为IV

            fos.write("MyFileEncryptor".getBytes());// 文件标识符

            fos.write(ivValue); // 记录IV
            Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5Padding");
            cipher.init(cipher.ENCRYPT_MODE, encryKey, iv);

            CipherInputStream cis = new CipherInputStream(fis, cipher);

            byte[] buffer = new byte[1024];
            int n = 0;
            while ((n = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, n);
            }
            cis.close();
            fos.close();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 文件加密
     *
     * @param fileSource
     * @param encryptedFileName
     */
    public static void encryptFile(File fileSource, String encryptedFileName) {
        try {
            FileInputStream fis = new FileInputStream(fileSource);
            FileOutputStream fos = new FileOutputStream(encryptedFileName);

            // 秘钥自动生成
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            Key key = keyGenerator.generateKey();

            byte[] keyValue = key.getEncoded();

            fos.write(keyValue);// 记录输入的加密密码的消息摘要

            SecretKeySpec encryKey = new SecretKeySpec(keyValue, "AES");// 加密秘钥

            byte[] ivValue = new byte[16];
            Random random = new Random(System.currentTimeMillis());
            random.nextBytes(ivValue);
            IvParameterSpec iv = new IvParameterSpec(ivValue);// 获取系统时间作为IV

            fos.write("MyFileEncryptor".getBytes());// 文件标识符

            fos.write(ivValue); // 记录IV
            Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5Padding");
            cipher.init(cipher.ENCRYPT_MODE, encryKey, iv);

            CipherInputStream cis = new CipherInputStream(fis, cipher);

            byte[] buffer = new byte[1024];
            int n = 0;
            while ((n = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, n);
            }
            cis.close();
            fos.close();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 文件加密
     *
     * @param fileSource
     * @param encryptedFileName
     */
    public static void encryptFile(MultipartFile fileSource, String encryptedFileName) {
        try {
            logger.info("开始进行对文件进行加密");
            InputStream fis = fileSource.getInputStream();
            FileOutputStream fos = new FileOutputStream(encryptedFileName);

            // 秘钥自动生成
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            Key key = keyGenerator.generateKey();

            byte[] keyValue = key.getEncoded();

            fos.write(keyValue);// 记录输入的加密密码的消息摘要

            SecretKeySpec encryKey = new SecretKeySpec(keyValue, "AES");// 加密秘钥

            byte[] ivValue = new byte[16];
            Random random = new Random(System.currentTimeMillis());
            random.nextBytes(ivValue);
            IvParameterSpec iv = new IvParameterSpec(ivValue);// 获取系统时间作为IV

            fos.write("MyFileEncryptor".getBytes());// 文件标识符

            fos.write(ivValue); // 记录IV
            Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5Padding");
            cipher.init(cipher.ENCRYPT_MODE, encryKey, iv);

            CipherInputStream cis = new CipherInputStream(fis, cipher);

            byte[] buffer = new byte[1024];
            int n = 0;
            while ((n = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, n);
            }
            cis.close();
            fos.close();
            logger.info("完成对文件进行加密");
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    /**
     * 解密文件
     *
     * @param encryptedFileName 加密后文件
     * @param decryptedFileName 解密后文件
     */
    public static void decryptedFile(String encryptedFileName, String decryptedFileName) {

        try {
            FileInputStream fis = new FileInputStream(encryptedFileName);
            FileOutputStream fos = new FileOutputStream(decryptedFileName);

            byte[] fileIdentifier = new byte[15];

            byte[] keyValue = new byte[16];
            fis.read(keyValue);// 读记录的文件加密密码的消息摘要
            fis.read(fileIdentifier);
            if (new String(fileIdentifier).equals("MyFileEncryptor")) {
                SecretKeySpec key = new SecretKeySpec(keyValue, "AES");
                byte[] ivValue = new byte[16];
                fis.read(ivValue);// 获取IV值
                IvParameterSpec iv = new IvParameterSpec(ivValue);
                Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5Padding");
                cipher.init(cipher.DECRYPT_MODE, key, iv);
                CipherInputStream cis = new CipherInputStream(fis, cipher);
                byte[] buffer = new byte[1024];
                int n = 0;
                int i = 0;
                while ((n = cis.read(buffer)) != -1) {
                    //System.out.println("=====i:"+i);
                    fos.write(buffer, 0, n);
                    i++;
                }
                cis.close();
                fos.close();
            }
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (HeadlessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 返回解密后的文件流字节
     *
     * @param encryptedFileName
     * @return
     */
    public static CipherInputStream decryptedFileData(String encryptedFileName) {

        CipherInputStream cis = null;

        try {
            FileInputStream fis = new FileInputStream(encryptedFileName);

            byte[] fileIdentifier = new byte[15];

            byte[] keyValue = new byte[16];
            fis.read(keyValue);// 读记录的文件加密密码的消息摘要
            fis.read(fileIdentifier);
            if (new String(fileIdentifier).equals("MyFileEncryptor")) {
                SecretKeySpec key = new SecretKeySpec(keyValue, "AES");
                byte[] ivValue = new byte[16];
                fis.read(ivValue);// 获取IV值
                IvParameterSpec iv = new IvParameterSpec(ivValue);
                Cipher cipher = Cipher.getInstance("AES/CFB/PKCS5Padding");
                cipher.init(cipher.DECRYPT_MODE, key, iv);
                cis = new CipherInputStream(fis, cipher);
                //cis.close();
            }
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (HeadlessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return cis;
    }

    /**
     * <p>
     * 解密
     * </p>
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */

    public static byte[] decrypt(byte[] data, String key) {
        try {
            Key k = null;
            k = toKey(Base64Utils.decode(key));
            byte[] raw = k.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(raw, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * <p>
     * 转换密钥
     * </p>
     *
     * @param key
     * @return
     * @throws Exception
     */
    private static Key toKey(byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
        return secretKey;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}
