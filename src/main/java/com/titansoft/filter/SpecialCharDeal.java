package com.titansoft.filter;

public class SpecialCharDeal {

	public final static String xssattChars = "$,@,',&quot;,\\',\\&quot;,&lt;,>,(,),+,CR,LF,\\&quot;,&quot;,\\,;,[,],{,},|,\"";
	
	public final static String specialChars = "•";
	
	public final static String sqlattChars = "'";
	
	/**
	 * 跨站注入字符解码
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String xssattCharsDecode(String str) throws Exception
	{
		if(str.indexOf("ttxssatt")>=0) 
		{
			for(String specialChar : xssattChars.split(","))
			{
				if(str.indexOf("ttxssatt" + bytesToHexString(specialChar.getBytes("UTF-8")))>=0)
				{
					str = str.replace("ttxssatt" + bytesToHexString(specialChar.getBytes("UTF-8")),specialChar);
				}
			}
		}
		return str;
	}
	
	/**
	 * sql注入字符转码
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String xssattCharsEncode(String str) throws Exception
	{
		String abc = "";
		for(String specialChar : str.split(","))
		{
			abc +=  ",ttxssatt:" + bytesToHexString(specialChar.getBytes("UTF-8"));
		}
		return abc;
	}
	
	/**
	 * sql注入字符转码
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String sqlattCharsEncode(String str) throws Exception
	{
		for(String specialChar : sqlattChars.split(","))
		{
			if(str.indexOf(specialChar)>=0)
			{
				str = str.replace(specialChar, "ttsqlatt:" + bytesToHexString(specialChar.getBytes("UTF-8")));
			}
		}
		return str;
	}
	
	/**
	 * sql注入字符解码
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String sqlattCharsDecode(String str) throws Exception
	{
		if(str.indexOf("ttsqlatt:")>=0) 
		{
			for(String specialChar : sqlattChars.split(","))
			{
				if(str.indexOf("ttsqlatt:" + bytesToHexString(specialChar.getBytes("UTF-8")))>=0)
				{
					str = str.replace("ttsqlatt:" + bytesToHexString(specialChar.getBytes("UTF-8")),specialChar);
				}
			}
		}
		return str;
	}
	
	/**
	 * 异类字符转码
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String specialCharEncode(String str) throws Exception
	{
		for(String specialChar : specialChars.split(","))
		{
			if(str.indexOf(specialChar)>=0)
			{
				str = str.replace(specialChar, "ttssc:" + bytesToHexString(specialChar.getBytes("UTF-8")));
			}
		}
		return str;
	}
	
	/**
	 * 异类字符解码
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public String specialCharDecode(String str) throws Exception
	{
		if(str.indexOf("ttssc:")>=0) 
		{
			for(String specialChar : specialChars.split(","))
			{
				if(str.indexOf("ttssc:" + bytesToHexString(specialChar.getBytes("UTF-8")))>=0)
				{
					str = str.replace("ttssc:" + bytesToHexString(specialChar.getBytes("UTF-8")),specialChar);
				}
			}
		}
		return str;
	}
	
	
	
	/**
     * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
     * @param src byte[] data
     * @return hex string
     */   
	private  String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    /**
     * Convert hex string to byte[]
     * @param hexString the hex string
     * @return byte[]
     */
    private  byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    
    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
     private byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
     
     /*
      * Demonstraton and self test of class
      */
     public static void main(String args[]) throws Exception {
    	 SpecialCharDeal scd = new SpecialCharDeal();
    	 String sql = "http://";
    	 
    	 System.out.println(scd.xssattCharsEncode(sql));
     }
}
