package com.titansoft.utils.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateTools {

	Date d = new Date();

	GregorianCalendar gc = new GregorianCalendar();

	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

	public String getYears() {
		gc.setTime(d);
		gc.add(1, +1);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc
				.get(Calendar.DATE));

		return sf.format(gc.getTime());
	}

	public String getHalfYear() {
		gc.setTime(d);
		gc.add(2, +6);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc
				.get(Calendar.DATE));

		return sf.format(gc.getTime());
	}

	public String getQuarters(int num) {
		gc.setTime(d);
		gc.add(5, +num);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc
				.get(Calendar.DATE));

		return sf.format(gc.getTime());
	}

	public String getLocalDate() {
		return sf.format(d);
	}

	/*
	 * 字符转换对象
	 * 
	 * @param 日期转换字符
	 * @return Date对象
	 */
	public static String pdateToString(Date dt, String strFormat) {
		// String strFormat = "yyyy-MM-dd HH:mm:ss";
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
	
	
	/*
	 * 日期加减运行，以分钟为单位
	 * 
	 * @param 日期转换字符
	 * @return Date对象
	 */
	public static java.util.Date   Dateaddminute( int minute) {
		Calendar cd = Calendar.getInstance();   
		cd.setLenient(true); //宽松处理，可以自动做转换   
		cd.add(Calendar.MINUTE, +minute);   
		java.util.Date  dt =cd.getTime();
		return dt;
	}
	
	/*
	 * 字符转换对象
	 * 
	 * @param 日期转换字符
	 * @return Date对象
	 */
	public static Date parseToDate(String str, String strFormat) {
		// String strFormat = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdFormat = new SimpleDateFormat(strFormat);
		Date dt;
		try {
			dt = sdFormat.parse(str);
		} catch (Exception e) {
			return null;
		}
		return dt;
	}

	/*
	 * 获取有关资质文件超时时间 天数
	 * 
	 * @param sdate
	 *            文件日期
	 * @return num 是前一天还是后一天
	 */
	public static String addDate(String sdate, int num) throws Exception {
		while (sdate.indexOf("/") != -1)
			sdate = sdate.replace('/', '-');
		Calendar cal = Calendar.getInstance();
		DateFormat df = DateFormat.getDateInstance();
		Date dt = df.parse(sdate);
		cal.setTime(dt);
		cal.add(Calendar.DATE, num);

		int iMonth = cal.get(Calendar.MONTH) + 1;
		String month1 = "" + iMonth;
		if (iMonth < 10)
			month1 = "0" + month1;

		String date1 = "" + cal.get(Calendar.DATE);
		if (date1.length() == 1)
			date1 = "0" + date1;

		Calendar calendar = Calendar.getInstance();
		int iHour = calendar.get(Calendar.HOUR_OF_DAY);
		String hour = "";
		if (iHour < 10) {
			hour = "0" + iHour;
		} else {
			hour = "" + iHour;
		}
		int iMin = calendar.get(Calendar.MINUTE);
		String min = "";
		if (iMin < 10) {
			min = "0" + iMin;
		} else {
			min = "" + iMin;
		}

		return "" + cal.get(Calendar.YEAR) + "-" + month1 + "-" + date1 + " "
				+ hour + ":" + min + ":" + calendar.get(Calendar.SECOND);

	}
	
	/**
	 * 获取有关资质文件超时时间 天数
	 * 
	 * @param sdate
	 *            文件日期
	 * @return num 是前一天还是后一天
	 */
	public static String addDate2(String sdate, int num) throws Exception {
		while (sdate.indexOf("/") != -1)
			sdate = sdate.replace('/', '-');
		Calendar cal = Calendar.getInstance();
		DateFormat df = DateFormat.getDateInstance();
		Date dt = df.parse(sdate);
		cal.setTime(dt);
		cal.add(Calendar.DATE, num);

		int iMonth = cal.get(Calendar.MONTH) + 1;
		String month1 = "" + iMonth;
		if (iMonth < 10)
			month1 = "0" + month1;

		String date1 = "" + cal.get(Calendar.DATE);
		if (date1.length() == 1)
			date1 = "0" + date1;

	

		return "" + cal.get(Calendar.YEAR) + "-" + month1 + "-" + date1 + " ";
				
	}
	
	
	/**
	 * 字符串转日期
	 * 如：s=20160203 转成 2016-02-03
	 */
	public static String getStringTodate(String str){
		String s = str;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat format1 = new SimpleDateFormat("yyyyMMdd");      
		Date date;
		try {
			date = format1.parse(str);
			String dueDateString = formatter.format(date);
			return dueDateString.trim();
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		
		return null ;
	}
	/*
	 * 传参数获取当前时间的前几个月
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String getdefaultStartDate(int i){
		Date date = new Date();   //当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(date);//把当前时间赋给日历
		calendar.add(calendar.MONTH, -i);  //传变量
		dBefore = calendar.getTime();   //得到前3月的时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
		String defaultStartDate = sdf.format(dBefore);    //格式化前3月的时间
		
		return defaultStartDate;
	}
	
	/*
	 * 传参数获取当前时间的前几个月
	 * @param date
	 * @return yyyymm
	 * @throws Exception
	 */
	public static String getdefaultStartDateMonth(int i){
		Date dNow = new Date();   //当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);//把当前时间赋给日历
		calendar.add(calendar.MONTH, -i);  //设置为前3月
		dBefore = calendar.getTime();   //得到前3月的时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM"); //设置时间格式
		String defaultStartDate = sdf.format(dBefore);    //格式化前3月的时间
		
		return defaultStartDate;
	}
	/*
	 * 传参数获取当前时间的前几个月
	 * @param date
	 * @return yyyymm
	 * @throws Exception
	 */
	public static String getdefaultStartDateMonth1(int i){
		Date dNow = new Date();   //当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);//把当前时间赋给日历
		calendar.add(calendar.MONTH, -i);  //设置为前3月
		dBefore = calendar.getTime();   //得到前3月的时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM"); //设置时间格式
		String defaultStartDate = sdf.format(dBefore);    //格式化前3月的时间
		
		return defaultStartDate;
	}
	
	
	
	/*
	 * 传参数获取当前时间的前几天
	 * @param date
	 * @return yyyymm
	 * @throws Exception
	 */
	public static String getdefaultStartDateday(int i){
		Date dNow = new Date();   //当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);//把当前时间赋给日历
		calendar.add(calendar.DATE, -i);  //设置为前3月
		dBefore = calendar.getTime();   //得到前3月的时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
		String defaultStartDate = sdf.format(dBefore);    //格式化前3月的时间
		
		return defaultStartDate;
	}
	/**
	 * 获得当前的日期 as:200705
	 * 
	 * @return
	 */
	/*public static String getNowDateOnlyNoDay() {
		Date now = new Date(System.currentTimeMillis());

		int year = DateTools.getYear(now);
		int month = DateUtil.getMonth(now);
		// 构造开始的时间
		StringBuffer sb = new StringBuffer("");
		sb.append(year);
		if (month <10) {
			sb.append("0").append(month);
		} else {
			sb.append("").append(month);
		}

		return sb.toString();
	}*/
	
	/**
	 * 获得当前年
	 * 
	 * @return
	 */
	/*public static String getNowyrar() {
		Date now = new Date(System.currentTimeMillis());

		int year = DateUtil.getYear(now);
		// 构造开始的时间
		StringBuffer sb = new StringBuffer("");
		sb.append(year);
		return sb.toString();
	}*/
	
	
	/*
	 * 获取当月的第一天
	 * @param date
	 * @return yyyymm
	 * @throws Exception
	 */
	public static String getMonth() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first);
		String s = str.toString();
		return s;
	}
	
	/*
	 * 传两个时间段算出它们之间的的所有日期 yyyy-mm
	 * @param date
	 * @return yyyy-mm
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {    
	    List lDate = new ArrayList();
	    lDate.add(beginDate);//把开始时间加入集合
	    Calendar cal = Calendar.getInstance();
	    //使用给定的 Date 设置此 Calendar 的时间
	    cal.setTime(beginDate);
	    boolean bContinue = true;
	    while (bContinue) {
	        //根据日历的规则，为给定的日历字段添加或减去指定的时间量
	       // cal.add(Calendar.DAY_OF_MONTH, 1);
	    	 cal.add(Calendar.MONDAY, 1);  
	        // 测试此日期是否在指定日期之后
	        if (endDate.after(cal.getTime())) {
	            lDate.add(cal.getTime());
	        } else {
	            break;
	        }
	    }
	    lDate.add(endDate);//把结束时间加入集合
	    return lDate;
	}
	
	/*
	 * 比较两个时间段的大小
	 * @param date
	 * @return yyyy-mm
	 * @throws Exception
	 */
	public static int compare_date(String DATE1, String DATE2) { 
	        DateFormat df = new SimpleDateFormat("yyyy-MM");
	        try {
	            Date dt1 = df.parse(DATE1);
	            Date dt2 = df.parse(DATE2);
	            if (dt1.getTime() > dt2.getTime()) {
	                System.out.println("dt1 在dt2前");
	                return 1;
	            } else if (dt1.getTime() < dt2.getTime()) {
	                System.out.println("dt1在dt2后");
	                return -1;
	            } else {
	                return 0;
	            }
	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
	        return 0;
	    }
	
	
	
	
	
	
	
	/*public static void main(String[] args) throws Exception {
		DateTools ud = new DateTools();
		//当前月的  6 - 1  =  5   如：2017 06  - 04
		System.out.println(ud.getdefaultStartDateMonth(5));
		System.out.println(ud.getdefaultStartDateMonth(4));
		System.out.println(ud.getdefaultStartDateMonth(3));
		System.out.println(ud.getdefaultStartDateMonth(2));
		System.out.println(ud.getdefaultStartDateMonth(1));	
	}*/
	
	/*public static void main(String[] args) throws ParseException {  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");  
        String str1 = "201507";  
        String str2 = "201605";  
        Calendar bef = Calendar.getInstance();  
        Calendar aft = Calendar.getInstance();  
        bef.setTime(sdf.parse(str1));  
        aft.setTime(sdf.parse(str2));  
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);  
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;  
        int a = Math.abs(month + result);
        System.out.println(Math.abs(month + result));     

        String months = str1.substring(str1.lastIndexOf("-")+1);
        int mon = Integer.parseInt(months);
        for (int i = 0; i <a; i++) {
        	System.out.println(str1+i);
        	if(mon+i<=12){
        		//年要变，变完之后 月从1开始
        		String strs = str2.substring(0,str2.lastIndexOf("-"));
        		int s = Integer.parseInt(strs);
        		int year = s+1;
        		mon = 1 ;
        		
        		
        		
        	}
        	
			
		}
        
    }  */
	
	
	
/*
		public static void main(String args[]) throws ParseException {
			 Calendar cal = Calendar.getInstance();  
		     String start = "2016-06"; 
		     String end = "2016-04"; 
		     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM"); 
		     Date dBegin =sdf.parse(start); 
		     Date dEnd = sdf.parse(end); 
		     List<Date> listDate = DateTools.getDatesBetweenTwoDate(dBegin, dEnd);    
		     for(Date date:listDate){    
		    	System.out.println(sdf.format(date));
			
		}
	}*/
	    
	    
	    
	public static void main(String[] args)throws Exception{
		 
	    /* System.out.println("jdk1.6测试");    
	     Calendar cal = Calendar.getInstance();  
	     String start = "2017-05"; 
	     String end = "2017-04"; 
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM"); 
	     Date dBegin =sdf.parse(start); 
	     Date dEnd = sdf.parse(end); 
	     List<Date> listDate = getDatesBetweenTwoDate(dBegin, dEnd);    
	     System.out.println(listDate.size());
	
	     for(Date date:listDate){    
	         System.out.println(sdf.format(date));   
	     }  
	     
	     System.out.println("jdk1.4测试");    
	     List lDate = getDatesBetweenTwoDate(dBegin, dEnd);    
	     for(int i=0;i<lDate.size();i++){   
	        Date date = (Date)lDate.get(i);
	            System.out.println(sdf.format(date));    
	     } 
		String values ="1=1 AND DOTIME like '2017-04'%";
		String strs = values.substring(values.indexOf("'")+1,values.lastIndexOf("'")-1); //2017-06%
		System.out.println(strs);
		*/
		
		//System.out.println(DateTools.getNowDateOnlyNoDay());
	     
	}
	
	
}