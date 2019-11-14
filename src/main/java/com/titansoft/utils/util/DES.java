package com.titansoft.utils.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class DES {
	private static String Algorithm = "DESede";// 加密算法的名称
	private static Cipher c;// 密码器
	private static byte[] cipherByte;
	private static SecretKey deskey;// 密钥
	private static String keyString = "T5S2AXwL3y/OaNUs6VTvrgFhj53+vwJ2";// 获得密钥的参数

	// 对base64编码的string解码成byte数组
	public byte[] deBase64(String parm) throws IOException {
		BASE64Decoder dec = new BASE64Decoder();
		byte[] dnParm = dec.decodeBuffer(parm);
		return dnParm;
	}

	// 把密钥参数转为byte数组
	public byte[] dBase64(String parm) throws IOException {
		BASE64Decoder dec = new BASE64Decoder();
		byte[] dnParm = dec.decodeBuffer(parm);
		return dnParm;
	}

	/**
	 * 对 Byte 数组进行解密
	 * 
	 * @param buff
	 *            要解密的数据
	 * @return 返回加密后的 String
	 */
	public static String createDecryptor(byte[] buff)
			throws NoSuchPaddingException, NoSuchAlgorithmException,
			UnsupportedEncodingException {
		try {
			c.init(Cipher.DECRYPT_MODE, deskey);// 初始化密码器，用密钥deskey,进入解密模式
			cipherByte = c.doFinal(buff);
		} catch (Exception ex) {
			//ex.printStackTrace();
			return null;
		}
		return (new String(cipherByte, "UTF-8"));
	}

	public void getKey(String key) throws IOException, InvalidKeyException,
			InvalidKeySpecException {
		byte[] dKey = dBase64(key);
		try {
			deskey = new javax.crypto.spec.SecretKeySpec(dKey, Algorithm);
			c = Cipher.getInstance(Algorithm);
		} catch (NoSuchPaddingException ex) {
		} catch (NoSuchAlgorithmException ex) {
		}
	}

	public static void main(String args[]) throws IOException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeySpecException, InvalidKeyException, IOException {
		DES des = new DES();
		String dStr = des.getEncryptedString("中华人民共和国");
		System.out.println("生成密钥串:" + dStr);
		System.out.println("解:" + dStr);
		System.out.println("解:" + des.decryptor(dStr));

	}

	/**
	 * 根据对称私钥解密数据
	 * 
	 * @param input
	 * @return
	 */
	public String decryptor(String input) {
		String result = "";
		try {
			this.getKey(keyString);
			byte[] dBy = this.deBase64(input);
			String dStr = this.createDecryptor(dBy);
			result = dStr;
		} catch (Exception er) {
			result = "bad";
		}
		return result;
	}

	/**
	 * 加密
	 * 
	 * @param src
	 *            要加密的字符串
	 * @return 加密后的字符串
	 */
	public String getEncryptedString(String src) {
		String base64 = "";
		try {
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, genKey(keyString));
			byte[] inputBytes = src.getBytes("UTF-8");
			byte[] outputBytes = cipher.doFinal(inputBytes);
			BASE64Encoder encoder = new BASE64Encoder();
			base64 = encoder.encode(outputBytes);
		} catch (Exception e) {
			base64 = e.getMessage();
		}
		return base64;
	}

	/**
	 * 生成密钥
	 * 
	 * @return 密钥
	 * @throws Exception
	 */
	private SecretKey genKey(String s) throws Exception {
		byte[] dKey = dBase64(s);
		SecretKey kd = new javax.crypto.spec.SecretKeySpec(dKey, "DESede");
		;
		return kd;
	}

}