package com.titansoft.utils.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Date Utility Class This is used to convert Strings to Dates and Timestamps
 * 
 * <p>
 * <a href="DateUtil.java.html"> <i>View Source </i> </a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible </a>
 * @version $Revision: 1.2 $ $Date: 2010/10/28 02:14:06 $
 */
public class DateUtil {
	// ~ Static fields/initializers
	// =============================================

	private static Log log = LogFactory.getLog(DateUtil.class);

	private static String datePattern = "yyyy-MM-dd";

	private static String timePattern = datePattern + " HH:mm:ss";

	// ~ Methods
	// ================================================================

	/**
	 * Return default datePattern (MM/dd/yyyy)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static String getDatePattern() {
		return datePattern;
	}

	/**
	 * This method attempts to convert an Oracle-formatted date in the form
	 * dd-MMM-yyyy to mm/dd/yyyy.
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(datePattern);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date/time in the
	 * format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate)
			throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		if (strDate == null || strDate.equals(""))
			return null;

		df = new SimpleDateFormat(aMask);

		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '"
					+ aMask + "'");
		}

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// log.error("ParseException: " + pe);
			//throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
	 * a
	 *
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {

		return getDateFormatStr(timePattern, theTime);
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 *
	 * @return the current date
	 * @throws ParseException
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(datePattern);

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));

		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 *
	 * @param format
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 *
	 * @see SimpleDateFormat
	 */
	public static final String getDateFormatStr(String format, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(format);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 *
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateFormatStr(datePattern, aDate);
	}

	/**
	 * This method converts a String to a date using the datePattern
	 *
	 * @param strDate
	 *            the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 *
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate) {
		Date aDate = null;

		try {
			if (log.isDebugEnabled()) {
				log.debug("converting date with pattern: " + datePattern);
			}

			aDate = convertStringToDate(datePattern, strDate);
		} catch (ParseException pe) {
			log.error("Could not convert '" + strDate
					+ "' to a date, throwing exception");
			pe.printStackTrace();

		}

		return aDate;
	}

	// 取年
	public static int getYear(Date d) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(d);
		return cl.get(Calendar.YEAR);
	}

	// 取月
	public static int getMonth(Date d) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(d);
		return cl.get(Calendar.MONTH) + 1;
	}

	// 取日
	public static int getDay(Date d) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(d);
		return cl.get(Calendar.DAY_OF_MONTH);
	}

	// 取秒
	public static int getSecond(Date d) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(d);
		return cl.get(Calendar.SECOND);
	}

	// 取小时
	public static int getHour(Date d) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(d);
		return cl.get(Calendar.HOUR_OF_DAY);
	}

	// 取分钟
	public static int getMinute(Date d) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(d);
		return cl.get(Calendar.MINUTE);
	}

	// 取周
	public static int getWeek(Date d) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(d);
		return cl.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 获得当前的日期 as:2007-05-04
	 *
	 * @return
	 */
	public static String getNowDateOnly() {
		Date now = new Date(System.currentTimeMillis());

		int year = DateUtil.getYear(now);
		int month = DateUtil.getMonth(now);
		int day = DateUtil.getDay(now);

		// 构造开始的时间
		StringBuffer sb = new StringBuffer("");
		sb.append(year);
		if (month < 10) {
			sb.append("-0").append(month);
		} else {
			sb.append("-").append(month);
		}

		if (day < 10) {
			sb.append("-0").append(day);
		} else {
			sb.append("-").append(day);
		}
		return sb.toString();
	}

	/**
	 * 获得当前的日期 as:200705
	 *
	 * @return
	 */
	public static String getNowDate() {
		Date now = new Date(System.currentTimeMillis());

		int year = DateUtil.getYear(now);
		int month = DateUtil.getMonth(now);
		int day = DateUtil.getDay(now);

		// 构造开始的时间
		StringBuffer sb = new StringBuffer("");
		sb.append(year);
		if (month < 10) {
			sb.append("0").append(month);
		} else {
			sb.append("-").append(month);
		}
		return sb.toString();
	}


	/**
	 * This method attempts to convert an Oracle-formatted date in the form
	 * dd-MMM-yyyy to mm/dd/yyyy.
	 *
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static final String getTime(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(timePattern);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/*
	  *
	  *  获取的当前的时间。格式为  yyyy-mm-dd HH:mm:ss
	  *  SQH
	  *
	  */
	  public static String getCurrentDateString()
	  {
	      Calendar calendar = Calendar.getInstance();
	      TimeZone timezone = calendar.getTimeZone();
	      int l = timezone.getRawOffset() / 0x36ee80;
	      calendar.add(10, -1 * l);
	      int iMonth = calendar.get(Calendar.MONTH)+1;
	      String month="";
	      if(iMonth<10){
	        month = "0" + iMonth;
	      }else{
	        month=""+iMonth;
	      }
	      int iDate = calendar.get(Calendar.DATE);
	      String date="";
	      if(iDate<10){
	        date = "0" + iDate;
	      }else{
	        date=""+iDate;
	      }
	      Calendar calendar2 = Calendar.getInstance();
	      int iHour =calendar2.get(Calendar.HOUR_OF_DAY);
	      String hour="";
	      if(iHour<10){
	        hour="0"+iHour;
	      }else{
	        hour=""+iHour;
	      }
	      int iMin=calendar2.get(Calendar.MINUTE);
	      String min="";
	      if(iMin<10){
	        min="0"+iMin;
	      }else{
	        min=""+iMin;
	      }


	      return ""+calendar.get(Calendar.YEAR)+"-"+month+"-"+date+" "
	          +hour+":"+min+":"
	          +calendar2.get(Calendar.SECOND);

	  }


	  /*
		 *  获取系统当前的时间
		 *  return  系统当前的时间
		 *  @author:sqh
		 *
		 */
		public static String  getSystemTime() throws Exception
		{

			String systime="";
			Calendar c = Calendar.getInstance();
			Date date = c.getTime();
			systime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			return systime;
		}
		 /*
		 *  获取系统当前的时间
		 *  return  系统当前的时间
		 *  @author:sqh
		 *
		 */
		public static String  getSystemTime1() throws Exception
		{

			String systime="";
			Calendar c = Calendar.getInstance();
			Date date = c.getTime();
			systime = new SimpleDateFormat("yyyy-MM").format(date);
			return systime;
		}
		/**
	     *将时间转换成中文形式
	     */
	    public static String DateToCN(Date date) {
	        if (null == date || "".equals(date)) {
	            return null;
	        }
	        String[] CN = { "○", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
	        String str = "十";

//	        String[] CN = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
//	        String str = "拾";

	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);

	        StringBuffer cn = new StringBuffer();

	        String year = String.valueOf(calendar.get(Calendar.YEAR));

	        for (int i = 0; i < year.length(); i++) {
	            cn.append(CN[year.charAt(i) - 48]);
	        }

	        cn.append("年");

	        int t1,t2;

	        int mon = calendar.get(Calendar.MONTH) + 1;

	        t1 = mon/10;
	        t2 = mon%10;

	        if(t1 < 10){
	            if(t1 != 0){
	                cn.append(CN[t1]);
	                cn.append(str);
	            }else{
	                //cn.append(CN[0]);
	            }
	        }

	        if(t2 < 10 && t2 != 0){
	            cn.append(CN[t2]);
	        }

	        cn.append("月");

	        int day = calendar.get(Calendar.DAY_OF_MONTH);

	        t1 = day/10;
	        t2 = day%10;

	        if(t1 < 10){
	            if(t1 != 0){
	                cn.append(CN[t1]);
	                cn.append(str);
	            }else{
	                //cn.append(CN[0]);
	            }
	        }

	        if(t2 < 10 && t2 != 0){
	            cn.append(CN[t2]);
	        }

	        cn.append("日");

	        return cn.toString();
	    }

	    /**
		 * 获取xml标准时间
		 * @param date
		 * @return
		 * @throws Exception
		 */
		public static javax.xml.datatype.XMLGregorianCalendar getXMLGregorianCalendar(Date date) throws Exception
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			javax.xml.datatype.DatatypeFactory dtf = javax.xml.datatype.DatatypeFactory.newInstance();
			return dtf.newXMLGregorianCalendar(
			calendar.get(calendar.YEAR),
			calendar.get(calendar.MONTH)+1,
			calendar.get(calendar.DAY_OF_MONTH),
			calendar.get(calendar.HOUR),
			calendar.get(calendar.MINUTE),
			calendar.get(calendar.SECOND),
			calendar.get(calendar.MILLISECOND),
			calendar.get(calendar.ZONE_OFFSET)/(1000*60));
		}
		
		/**
		 * 获取当前时间的月份
		 * @param date
		 * @return
		 * @throws Exception
		 */
		public static int getDateTime(){
			int month=0;
		    //获取当前时间
			Calendar cal = Calendar.getInstance(); 
			Date date=new Date();
			DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
				 
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
			String time=format.format(date);  //2017-06-30
				    
			Date da = null; 
			try { 
					    	    
				da = formatter.parse(time);
				cal.setTime(da);
					    	      
			} catch(Exception e){    
			}     	 
			 month = cal.get(Calendar.MONTH) + 1;   //6	    	
			 return month ;
		}
		
		
		
		/**
		 * This method returns the current date time in the format: MM/dd/yyyy HH:MM
		 * a
		 * 
		 * @param theTime
		 *            the current time
		 * @return the current date/time
		 */
		public static String getTimeStandardStr(Date theTime) {
			return getDateFormatStr(timePattern, theTime);
		}
		
		public static void main(String[] args) throws Exception {

			//System.out.println(getTimeStandardStr(new Date(1472566925323L)));
		   // System.out.println(getNowDateOnly());
		   /* System.out.print(getSystemTime());
		    System.out.print(getCurrentDateString());*/
			
			
		/*	
			
			 //获取当前时间  
	        Calendar cal = Calendar.getInstance();  
	        //下面可以设置月份，注：月份设置要减1，所以设置1月就是1-1，设置2月就是2-1，如此类推  
	        cal.set(Calendar.MONTH, 1-1);  
	        //调到上个月  
	        cal.add(Calendar.MONTH, -1);  
	        //得到一个月最最后一天日期(31/30/29/28)  
	        int MaxDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH);  
	        //按你的要求设置时间  
	        cal.set( cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), MaxDay, 23, 59, 59);  
	        //按格式输出  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");  
	        System.out.println(sdf.format(cal.getTime()));        
			
			*/
	
			
			System.out.println(getSystemTime1());
			System.out.println(getDateFormatStr("yyyyMMddHHmmss", new Date()));
			
		}
}