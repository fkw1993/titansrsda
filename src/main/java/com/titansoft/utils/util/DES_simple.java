package com.titansoft.utils.util;

import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * DES加密介绍 DES是一种对称加密算法，所谓对称加密算法即：加密和解密使用相同密钥的算法。DES加密算法出自IBM的研究，
 * 后来被美国政府正式采用，之后开始广泛流传，但是近些年使用越来越少，因为DES使用56位密钥，以现代计算能力，
 * 24小时内即可被破解。虽然如此，在某些简单应用中，我们还是可以使用DES加密算法，本文简单讲解DES的JAVA实现 。
 * 注意：DES加密和解密过程中，密钥长度都必须是8的倍数
 */
public class DES_simple {
	public DES_simple() {
	}

	private static final String password = "07563350118";
	
	// 测试
	public static void main(String args[]) {
		// 待加密内容
		String str = "前言：刚学h5没多久，感觉吧比android难多了啊，特别是适配，真尼玛苦逼啊，不过h5的大牛还是很多的，毕竟这么多年了，随便一搜就一大堆，正是因为这样，今天刚好后台需要用des对称加密传输数据，然后就上网一搜，真尼玛一大堆啊，最后找到了一个叫crypto-js的库，down下来的时候不会用，尼玛尴尬了，于是就当笔记记下来了，大牛勿喷～！";
		// 密码，长度要是8的倍数
		String password = "12345678";

		String result = DES_simple.encodeBase64Str(str);

		System.out.println("加密后：" + result);
		
		//base64str = "k8ZzxZI/GPOb4K0W+0+Sug==";
		// 直接将如上内容解密
		try {
			result = DES_simple.decryptBase64Str(result);
			System.out.println("解密后：" + result);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * 解密已经加密成base64的des加密字符串
	 * @param base64Str
	 * @return
	 */
	public static String decryptBase64Str(String base64Str)
	{
		String result = "";
		try {
			byte[] decryResult = DES_simple.decrypt(decoderBase64(base64Str),password);
			result = new String(decryResult, "utf-8");
		} catch (Exception e) {
		}
		return result;
	}
	
	/**
	 * 将字符串改成
	 * @param sourceStr
	 * @return
	 */
	public static String encodeBase64Str(String sourceStr)
	{
		String base64str ="";
		
		try {
			sourceStr = "datalength=" + sourceStr.length() + "|" + sourceStr;
			byte[] result = DES_simple.encrypt(sourceStr.getBytes(), password);
			base64str =  encodeBASE64String(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return base64str;
	}
	
	/**  

	    * <p>将base64字符解码保存文件</p>  

	    * @param base64Code  

	    * @param targetPath  

	    * @throws Exception  

	    */ 

	   public static byte[] decoderBase64(String base64Code) throws Exception {  

	       byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);  
	       return buffer;
	   }
	
	 public static String encodeBASE64String(byte[] bytes)  {
	       sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
	       return encoder.encodeBuffer(bytes).trim();
	   }
	
	/**
	 * 加密
	 * 
	 * @param datasource
	 *            byte[]
	 * @param password
	 *            String
	 * @return byte[]
	 */
	public static byte[] encrypt(byte[] datasource, String password) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password.getBytes());
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return cipher.doFinal(datasource);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param src
	 *            byte[]
	 * @param password
	 *            String
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, String password) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom random = new SecureRandom();
		// 创建一个DESKeySpec对象
		DESKeySpec desKey = new DESKeySpec(password.getBytes());
		// 创建一个密匙工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// 将DESKeySpec对象转换成SecretKey对象
		SecretKey securekey = keyFactory.generateSecret(desKey);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		// 真正开始解密操作
		return cipher.doFinal(src);
	}
}

