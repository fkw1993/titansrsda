package com.titansoft.utils.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 补“0”类处理公共类，包括档案业务中对类A1.01之类的进行补0处理
 * @author 珠海·lhh
 * @version 1.0 2016-01-12
 */
public class StringformatZreoUtil {
    
    /**
     * 档号字段实“0”的公共方法
     * @param str 传入档号组成字段值
	 * @param len 并转入补零后要求的长度
     * 返回 补零后的字符串值
     */
    public static String getDhFieldValue(String str,int len) {
    	
    	str = str.trim();
    	int length = str.length();

    	//只允数字   
        String regEx = "[^0-9]";   
        Pattern p = Pattern.compile(regEx);   
        Matcher m = p.matcher(str);   
        //替换与模式匹配的所有字符（即非数字的字符将被""替换）   
        String temp = m.replaceAll("@").trim();
        
        int min = temp.indexOf("@");        //第一个字母位置
        int max = temp.lastIndexOf("@");   //最后一个字母位置
        int frist = str.indexOf(".");	    // 第一个点
        int last = str.lastIndexOf(".");   //最后一个点
        
        //以下变量表示字母串变量  字符串 +　纯数字串　+　点　+  纯数字串 +　字母串
        String str1 = "";
        String num1 = "";
        String dot = ".";
        String num2 = "";
        String str2 = "";
        
        String temp1 = "";
        String temp2 = "";
        
        if(max<0)
        {
        	if(length<=len)
        	{
        		for(int i=0;i<len-length;i++)
            	{
            		str = "0" + str;
            	}
        	}
        	else
        	{
        		while(str.indexOf("0")==0&&str.length()>len)
        		{
        			str = str.substring(1);
        		}
        	}
        }
        if(frist>0&&frist==last&&last<=max&&last<(str.length()-1)&&!"@".equals(temp.charAt(last+1)+""))
        {
        	if(min==0&&min!=last&&last==max&&!"@".equals(temp.charAt(last-1)+""))
        	{
        		str1 = str.substring(0,frist);
	        	str2 = str.substring(frist+1,length);
	        	
	        	temp1 = temp.substring(0,frist);
	        	temp2 = temp.substring(frist+1,length);
	        	
	        	num1 = temp1.substring(temp1.lastIndexOf("@") + 1);
	        	str1 = str1.substring(0, temp1.lastIndexOf("@") + 1);
	        	if(temp2.indexOf("@")>0)
	        	{
	        		num2 = temp2.substring(0, temp2.indexOf("@"));
		        	str2 = str2.substring(temp2.indexOf("@"));
	        	}
	        	else
	        	{
	        		num2 = str2;
		        	str2 = "";
	        	}
	        	
	        	if(length<=len)
	        	{
	        		for(int i=0;i<len-str.length();i++)
    	        	{
	        			if(num1.length()>0)
	        			{
	        				if(num2.length()<=num1.length())
	    	        		{
	    	        			num2 = "0" + num2;
	    	        		}
	    	        		else
	    	        		{
	    	        			num1 = "0" + num1;
	    	        		}
	        			}
	        			else
	        				num2 = "0" + num2;
    	        	}
	        	}
	        	else
	        	{
	        		int i = str.length() - len;
		        	while((num1.indexOf("0")==0||num2.indexOf("0")==0)&&i>0&&(num2.length()>1||num1.length()>1))
	        		{
	        			if(num1.indexOf("0")==0&&num2.indexOf("0")==0)
	        			{
	        				if(num1.length()>=num2.length()&&num1.length()>1)
	        				{
	        					num1 = num1.substring(1);
	        				}
	        				else if(num2.length()>1)
	        				{
	        					num2 = num2.substring(1);
	        				}
	        			}
	        			else if(num2.indexOf("0")==0&&num2.length()>1)
	        			{
	        				num2 = num2.substring(1);
	        			}
	        			else if(num1.length()>1)
	        			{
	        				num1 = num1.substring(1);
	        			}
	        			i--;
	        		}
	        	}
	        	str = str1 + num1 + dot + num2 + str2;
        	}
        }
    	return str; 
    }
    
    /**
     * 档号字段实“0”的公共方法
     * @param str 传入档号组成字段值
	 * @param len 并转入补零后要求的长度
     * 返回 补零后的字符串值
     */
    public static String getNumFillZreo(String str,int len) {
    	try {
			str = String.format("%0"+len+"d", Integer.parseInt(str));
		} catch (Exception e) {
		}
		return str;
    }
    
    
    /**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args)  {
		String srcString = "A3.01";
		System.out.println(getDhFieldValue(srcString,10));
	}
}
