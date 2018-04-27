package com.l9e.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理工具类
 * @author 肖有标
 *
 */
public class DateUtil {
	
	public static final String DATE_HM = "HH:mm";
	
	public static final String DATE_FMT1 = "yyyy-MM-dd";
	
	public static final String DATE_FMT2 = "yyyyMMddHHmmss";
	
	//刘毅
	public static final String DATE_FMT3 = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATE_FMT_YMD = "yyyyMMdd";
	
	//一天的毫秒数
	private static final long ND=1000*24*60*60;
	//一分钟的毫秒数
	private static final long MD=1000*60;

	/**
	 * 把时间转换成字符串
	 * @param date  时间
	 * @param formatStr  要转换的格式
	 * @return
	 */
	public static String dateToString(Date date,String formatStr){
		SimpleDateFormat sdf=new SimpleDateFormat(formatStr);
		return sdf.format(date);
	}
	
	public static Date stringToDate(String dateStr,String formatStr){
		SimpleDateFormat sdf=new SimpleDateFormat(formatStr);
		Date date;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return date;
	}
	
	public static Date dateAddDays(Date date,int addDays){
		if(null==date){
			return null;
		}
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, addDays);
		return cal.getTime();
	}
	/**
	 * @author zuoyx
	 * @param date	     时间
	 * @param addDays 要增加的天数
	 * @return
	 */
	public static String dateAddDays(String date,String addDays){
		if(null==date){
			return null;
		}
		Calendar cal=Calendar.getInstance();
		cal.setTime(stringToDate(date, DATE_FMT1));
		cal.add(Calendar.DAY_OF_YEAR, Integer.valueOf(addDays));
		return dateToString(cal.getTime(),DATE_FMT1);
	}
	
	/**
	 * @author zuoyx
	 * @param date	     时间
	 * @param addDays 要增加的天数
	 * @return "yyyy-MM-dd HH:mm:ss"
	 */
	public static String dateAddDaysFmt3(String date,String addDays){
		if(null==date){
			return null;
		}
		Calendar cal=Calendar.getInstance();
		cal.setTime(stringToDate(date, DATE_FMT3));
		cal.add(Calendar.DAY_OF_YEAR, Integer.valueOf(addDays));
		return dateToString(cal.getTime(),DATE_FMT3);
	}
	
	/**
	 * @author 刘毅
	 * @param date	     时间1
	 * @param date2        时间2
	 * @return long 相差几天
	 */
	public static int dateDiff(Date d1, Date d2) {		
		long n1 = d1.getTime()/ND;	
		long n2 = d2.getTime()/ND;
		long diff = Math.abs(n1 - n2);				
		return (int)diff+1;	
	}
	
	/**
	 * @author zuoyx
	 * @param date	     时间1
	 * @param date2        时间2
	 * @return long 相差分钟
	 */
	public static String minuteDiff(String min1, String min2) {	
		Date d1 = stringToDate(min1,DATE_HM);
		Date d2 = stringToDate(min2,DATE_HM);
		long n1 = d1.getTime()/MD;	
		long n2 = d2.getTime()/MD;
		return  String.valueOf(Math.abs(n1 - n2));				
	}
	
	/**
	 * @author zuoyx
	 * @param date	     时间1
	 * @param date2        时间2
	 * @return long 相差分钟
	 */
	public static long minuteDiff(Date min1, Date min2) {	
		long n1 = min1.getTime()/MD;	
		long n2 = min2.getTime()/MD;
		return  Math.abs(n1 - n2);				
	}
	
	/**分钟数值转换为HH:mm
	 * @author zuoyx
	 * @param  str   分钟
	 * @return HH:mm字符串
	 */
	public static String minuteFormat(String str) {	
		int minute = Integer.valueOf(str);
		String result = "";
		int hours = (int) (minute/60);
		int min = (int) (minute%60);
		if(hours>10){
			result += hours+"";
		}else if(hours>0){
			result +="0"+hours;
		}else{
			result +="00";
		}
		if(min>10){
			result +=":"+min;
		}else{
			result +=":0"+min;
		}
		return  result;	
	}
	
	public  static  String dateTimeToMinute(String str){
		
		String[] time = str.split(":");
		int h=Integer.valueOf(time[0]);
		int m=Integer.valueOf(time[1]);
		int z=h*60+m;
		String result = String.valueOf(z);
		return result;
	
	}
	
	/**
	 * 计算历时天数，12306
	 * @param start
	 * @param limit
	 * @param day
	 * @return
	 */
	public static  String dayDiffer(String start,String  limit,String  day){
		
		String fDateStr =day+start;
		String  oDateStr ="";
		int minDif = Integer.valueOf(dateTimeToMinute(limit));	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm");	
		Date dateStart = null;
		try {
			dateStart = sdf.parse(fDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Calendar cal = Calendar.getInstance();
        cal.setTime(dateStart);   
        cal.add(Calendar.MINUTE, minDif);// 24小时制 
        Date  dateEnd = cal.getTime();
		Calendar aCalendar = Calendar.getInstance();
		
		aCalendar.setTime(dateStart);
		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(dateEnd);
		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
		System.out.println(day2 - day1);

		long differ=day2 - day1;
		String  differDay =  String.valueOf(differ);
		return differDay;
	}
	
 
	public static void main(String[] args) throws ParseException {
	
		
	}
	
	
	
}
