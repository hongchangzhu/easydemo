package com.analysis.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

public class DateTool {
	/**
	 * 功能: 将日期对象按照某种格式进行转换，返回转换后的字符串
	 * 
	 * @param date
	 *            日期对象
	 * @param pattern
	 *            转换格式 例：yyyy-MM-dd
	 */
	public static String DateToString(Date date, String pattern) {
		String strDateTime = null;

		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		strDateTime = date == null ? null : formater.format(date);
		return strDateTime;
	}

	/**
	 * 功能: 将传入的日期对象按照yyyy-MM-dd格式转换成字符串返回
	 * 
	 * @param date
	 *            日期对象
	 * @return String
	 */
	public static String DateToString(Date date) {
		String _pattern = "yyyy-MM-dd";
		return date == null ? null : DateToString(date, _pattern);
	}

	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * 把一个字符串(一定要保证是正确日期)转化成Java的Date类 如果失败返回null 如果格式不支持请将格式增加到datePattern中
	 * 
	 * @param source
	 *            传入的日期字符串
	 * @return Date
	 */
	public static Date parseDate(String source) {
		if (source == null || source.length() == 0)
			return null;
		String[] datePattern = { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "HH:mm:ss", "yyyy-MM-dd HH:mm" };
		Date date = null;
		for (int i = 0; i < datePattern.length; i++) {
			date = parseDate(source, datePattern[i]);
			if (date != null)
				break;
		}
		datePattern = null;
		return date;
	}

	public static java.sql.Date parseSqlDate(String pattern) {
		Date date = parseDate(pattern);
		java.sql.Date returnV;
		returnV = new java.sql.Date(date.getTime());
		return returnV;
	}

	/**
	 * 把一个指定格式的日期字符串转化成Java的Date类 如果失败返回null
	 * 
	 * @param source
	 *            传入的日期字符串
	 * @param format
	 *            日期的格式
	 * @return Date
	 */
	public static Date parseDate(String dateStr, String format) {
		if (StringUtils.isBlank(dateStr))
			return null;

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = null;

		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			// do nothing.
		}

		return date;
	}

	/**
	 * 取得传入日期是多少周
	 * 
	 * @param date
	 * @return 第几周
	 */
	public static int getWeekOfYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);

		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 取得当前日期是多少周
	 * 
	 * @return 第几周
	 */
	public static int getWeekOfYear() {
		return getWeekOfYear(new Date());
	}

	/**
	 * 得到某一年周的总数
	 * 
	 * @param year
	 * @return 某一年周的总数
	 */
	public static int getMaxWeekNumOfYear(int year) {
		Calendar c = new GregorianCalendar();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

		return getWeekOfYear(c.getTime());
	}

	/**
	 * 得到某年某周的第一天
	 * 
	 * @param year
	 * @param week
	 * @return 某年某周的第一天
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getFirstDayOfWeek(cal.getTime());
	}

	/**
	 * 功能: 将插入的字符串按格式转换成对应的日期对象
	 * 
	 * @param str
	 *            字符串
	 * @param pattern
	 *            格式
	 * @return Date
	 */
	public static Date StringToDate(String str, String pattern) {
		Date dateTime = null;
		try {
			if (str != null && !str.equals("")) {
				SimpleDateFormat formater = new SimpleDateFormat(pattern);
				dateTime = formater.parse(str);
			}
		} catch (Exception ex) {
		}
		return dateTime;
	}

	/**
	 * 取得当前日期所在周的第一天
	 * 
	 * @param date
	 * @return 当前日期所在周的第一天
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	/**
	 * 
	 * @功能：获取指定某年某周的第一天的日期字符串
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static String getFirstDayOfYearWeek(int year, int week) {
		Date date = DateTool.getFirstDayOfWeek(year, week - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int curyear = cal.get(Calendar.YEAR);
		// 如果第一天是上一年的日期則返回1月1日
		if (curyear != year)
			return year + "-01-01";

		return DateToString(date, "yyyy-MM-dd");
	}

	/**
	 * 
	 * @功能：获取指定某年某周的最后一天的日期字符串
	 * 
	 * @param year
	 * @param week
	 * @return
	 */
	public static String getLastDayOfYearWeek(int year, int week) {
		Date date = DateTool.getFirstDayOfWeek(year, week);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		int curyear = cal.get(Calendar.YEAR);
		// 如果最后一天是下一年的日期則返回12月31日
		if (curyear != year)
			return year + "-12-31";

		return DateToString(cal.getTime(), "yyyy-MM-dd");
	}

}
