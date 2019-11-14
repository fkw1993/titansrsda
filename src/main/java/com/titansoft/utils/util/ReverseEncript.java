package com.titansoft.utils.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * 不可逆加密算法，包括MD5,SAH-1
 * @author Administrator
 */
public class ReverseEncript {
	private final static char hexDigits[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	/**
	 * 获取字符串的hash
	 * @param s
	 * @param hashtype
	 * @return
	 */
	public final static String Encrypt(String s, String hashtype) {
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance(hashtype);
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取流文件的hash
	 * @param file 文件
	 * @param hashtype 算法MD5,SHA-1
	 * @return
	 */
	public final static String Encrypt(File file, String hashtype) {

		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			MessageDigest digest = MessageDigest.getInstance(hashtype);

			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();

			byte[] b = digest.digest();
			return byteToHexString(b);

		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取流文件的hash
	 * @param 文件输入流
	 * @param hashtype 算法MD5,SAH-1
	 * @return
	 */
	public final static String Encrypt(InputStream in, String hashtype) {

		byte buffer[] = new byte[1024];
		int len;
		try {
			MessageDigest digest = MessageDigest.getInstance(hashtype);

			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();

			byte[] b = digest.digest();
			return byteToHexString(b);

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 把byte[]数组转换成十六进制字符串表示形式
	 * 
	 * @param tmp
	 *            要转换的byte[]
	 * @return 十六进制字符串表示形式
	 */
	private static String byteToHexString(byte[] tmp) {
		String s;
		// 用字节表示就是 16 个字节
		char str[] = new char[tmp.length * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
		// 所以表示成 16 进制需要 32 个字符
		int k = 0; // 表示转换结果中对应的字符位置
		for (int i = 0; i < tmp.length; i++) { // 从第一个字节开始，对 MD5 的每一个字节
			// 转换成 16 进制字符的转换
			byte byte0 = tmp[i]; // 取第 i 个字节
			str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
			// >>> 为逻辑右移，将符号位一起右移
			str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
		}
		s = new String(str); // 换后的结果转换为字符串
		return s;
	}

	public static String getMD5(String str)
	{
		return Encrypt(str,"MD5");
	}
	
	public static void main(String args[]){
		System.out.println(Encrypt(new File("G:/adberdr_zh_cn.exe"),"SHA-1"));
	}
}
