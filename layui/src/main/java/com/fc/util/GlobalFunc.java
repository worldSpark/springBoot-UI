package com.fc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description 系统基本函数处理类
 * @author chencc
 * @Date 2016年04月22日 上午09:04:00
 * @version 1.0.0
 * */
public class GlobalFunc
{
  private static String configfile = "Config";
  private static String endline = "------分隔符------";



  public static String formateDate(String date){
     if(date==null||"".equals(date))return "";
	  	return date.substring(0,10);
  }


  public static int parseInt(String s)
  {
    try
    {
      if (s == null||"".equals(s))
        return 0;

      return Integer.parseInt(s.trim()); } catch (Exception e) {
    }
    return 0;
  }


  public static double parseDouble(Object s)
  {
    try
    {
      if (s == null) {
          return 0.0D;
      }
      return Double.parseDouble(toString(s));} catch (Exception e) {
    }
    return 0.0D;
  }

  public static double parseDouble(int i)
  {
    return parseDouble(String.valueOf(i));
  }

  public static double parseDouble(long i)
  {
    return parseDouble(String.valueOf(i));
  }

  public static String doubleToStr(double f)
  {
    return String.valueOf(parseDouble(Math.round(f * 100.0D)) / 100.0D);
  }

  public static int parseInt(Object s)
  {
    return parseInt(s, 0);
  }

  public static int parseInt(Object s, int def)
  {
    try {
      if (s == null)
        return def;

        String str = toString(s).trim();
        if(str.contains(".")){
            str = str.substring(0,str.indexOf("."));
        }
        return Integer.parseInt(str);
      } catch (Exception e) {

    }
    return def;
  }

  public static float parseFloat(Object s)
  {
    try {
      if ((s == null) || ("".equals(s)))
        return Float.parseFloat("0");

      return Float.parseFloat(s.toString().trim());
    } catch (Exception e) {
    }
    return Float.parseFloat("0");
  }

  public static String toString(Object s)
  {
    try
    {
      if (s == null) return "";

      //return String.valueOf(s).replace("null", "");' //若使用该方法.则表单中的就有错误
      return String.valueOf(s);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }






  public static boolean isNumeric(String s)
  {
    try
    {
      Integer.parseInt(s);
      return true;
    }
    catch (Exception e) {
    }
    return false;
  }


  public static Calendar getCal(String s)
  {
    Calendar cal = Calendar.getInstance();
    cal.set(getYear(s), getMonth(s) - 1, getDay(s), getHour(s), getMinute(s), getSecond(s));
    return cal;
  }
  public static int getHour(String s)
  {
    try
    {
      return Integer.parseInt(s.substring(11, 13));
    }
    catch (Exception e) {
    }
    return 0;
  }

  public static int getMinute(String s)
  {
    try
    {
      return Integer.parseInt(s.substring(14, 16));
    }
    catch (Exception e) {
    }
    return 0;
  }

  public static int getSecond(String s)
  {
    try
    {
      return Integer.parseInt(s.substring(17, 19));
    }
    catch (Exception e) {
    }
    return 0;
  }



  public static long getTime(Object time)
  {
    return getCal(toString(time)).getTime().getTime();
  }





  public static String getYear(Calendar cal)
  {
    return String.valueOf(cal.get(1));
  }
  public static boolean isNullStr(String s)
  {
    return ((s == null) || (s.trim().length() <= 0));
  }

  public static String strLen(String s, int len)
  {
    if (isNullStr(s))
      s = "";
    for (int i = 0; i < len - s.length(); ++i)
    {
      s = "0" + s;
    }
    return s;
  }

  public static String getMonth(Calendar cal)
  {
    return strLen(String.valueOf(cal.get(2) + 1), 2);
  }

  public static String getDay(Calendar cal)
  {
    return strLen(String.valueOf(cal.get(5)), 2);
  }

  public static String getHour(Calendar cal)
  {
    return strLen(String.valueOf(cal.get(11)), 2);
  }

  public static String getMinute(Calendar cal)
  {
    return strLen(String.valueOf(cal.get(12)), 2);
  }

  public static String getSecond(Calendar cal)
  {
    return strLen(String.valueOf(cal.get(13)), 2);
  }

  public static String getDateStr(Calendar cal)
  {
    return getYear(cal) + "/" + getMonth(cal) + "/" + getDay(cal);
  }


  public static String getDateStr(String date)
  {
	  if("1970/01/01".equals(getDateStr(getCal(date)))) return "";
	  if("1900/01/01".equals(getDateStr(getCal(date)))) return "";
    return getDateStr(getCal(date));
  }

  public static String getTimeStr(Calendar cal)
  {
    return getHour(cal) + ":" + getMinute(cal) + ":" + getSecond(cal);
  }

  public static String getTimeStr(String date)
  {
    return getTimeStr(getCal(date));
  }

  public static String getDate(Calendar cal)
  {
    return getDateStr(cal) + " " + getTimeStr(cal);
  }

  public static String getDate(Date dat)
  {
    if (dat == null) return "";
    Calendar cal = Calendar.getInstance();
    cal.setTime(dat);
    return getDateStr(cal) + " " + getTimeStr(cal);
  }
  /**
   *  获取年份 params string,  返回int类型
   */
  public static int getYear(String s)
  {
    try
    {
      return Integer.parseInt(s.substring(0, 4));
    }
    catch (Exception e) {
    }
    return 1970;
  }
  /**
   * 获取月份 params string,  返回int类型
   */
  public static int getMonth(String s)
  {
    try
    {
      return Integer.parseInt(s.substring(5, 7));
    }
    catch (Exception e) {
    }
    return 1;
  }
  /**
   *  获取天数 params string 返回int类型
   */
  public static int getDay(String s)
  {
    try
    {
      return Integer.parseInt(s.substring(8, 10));
    }
    catch (Exception e) {
    }
    return 1;
  }

   public static String getYearMonth(String s)
   {
    try
    {
      return   s.substring(0, 7) ;
    }
    catch (Exception e) {
    }
    return "";
  }




  /**
   *  显示的格式如 1900/01/01 00:00:00
   */
  public static String getNow()
  {
    Calendar now = Calendar.getInstance();
    return getDateStr(now) + " " + getTimeStr(now);
  }

/**
 *  显示的格式如 1900/01/01
 */
  public static String getNowDate()
  {
    Calendar now = Calendar.getInstance();
    return getDateStr(now);
  }


  public static String getNowTime()
  {
    Calendar now = Calendar.getInstance();
    return getTimeStr(now);
  }



  public static String getThisWeek()
  {
    Calendar now = Calendar.getInstance();
    now.set(5, now.get(5) - now.get(7) + 1);
    return getDateStr(now) + " " + getTimeStr(now);
  }

  public static String getThisWeekend()
  {
    Calendar now = Calendar.getInstance();
    now.set(5, now.get(5) - now.get(7) + 8);
    return getDateStr(now) + " " + getTimeStr(now);
  }



  static String getThisWeek(String date)
  {
    Calendar now = getCal(date);
    now.set(5, now.get(5) - now.get(7) + 1);
    return getDateStr(now) + " " + getTimeStr(now);
  }

  public static String getNextWeek()
  {
    Calendar now = Calendar.getInstance();
    now.set(5, now.get(5) - now.get(7) + 1 + 7);
    return getDateStr(now) + " " + getTimeStr(now);
  }

  public static String getNextWeek(String date)
  {
    Calendar now = getCal(date);
    now.set(5, now.get(5) - now.get(7) + 1 + 7);
    return getDateStr(now) + " " + getTimeStr(now);
  }

  public static String getNextMonth()
  {
    Calendar now = Calendar.getInstance();
    now.set(2, now.get(2) + 1);
    return getDateStr(now) + " " + getTimeStr(now);
  }

  public static String getNextMonth(String date)
  {
    Calendar now = getCal(date);
    now.set(2, now.get(2) + 1);
    return getDateStr(now) + " " + getTimeStr(now);
  }

  public static String getChnDate(String computerDate)
  {
    String out = String.valueOf(getYear(computerDate)) + "年" + String.valueOf(getMonth(computerDate)) + "月" + String.valueOf(getDay(computerDate)) + "日";
    return out;
  }


  	public static String getMonthLastDay(Calendar cal){
  	        cal.add(cal.MONTH, 1);
  	        cal.set(cal.DATE, 1);
  	        cal.add(cal.DATE, -1);
  	        return  getDateStr(cal);
  	}

 	public static String getMonthFristDay(Calendar cal){
	        cal.set(cal.DATE, 1);
	        return  getDateStr(cal);
	}



 	public static String getYearAndMonth(String dateStr){
         if(dateStr==null)return "";
        return  dateStr.replace("年", "/").replace("月", "") ;
    }

 	/**获取上一个月*/
 	public static String getLastMonth(String dateStr){
 		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM");
        Calendar   calendar=Calendar.getInstance();
        try {
			calendar.setTime(sdf.parse(dateStr));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       //取得上一个月时间
        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-1);
        String lastMonth= sdf.format(calendar.getTime());
        return lastMonth;
 	}
 	/**获取上一个月*/
 	public static String getFirstMonth(String dateStr){
 		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM");
        Calendar   calendar=Calendar.getInstance();
        try {
			calendar.setTime(sdf.parse(dateStr));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       //取得上一个月时间
        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)+1);
        String lastMonth= sdf.format(calendar.getTime());
        return lastMonth;
 	}


 	/**获取下一个月*/
 	public static String getNextMonthFormat(String dateStr){
 		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM");
        Calendar   calendar=Calendar.getInstance();
        try {
			calendar.setTime(sdf.parse(dateStr));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       //取得下一个月时间
        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)+1);
        String lastMonth= sdf.format(calendar.getTime());
        return lastMonth;
 	}

 	//获取上传文件的名字
	 public static String getFileName(String filePathName)
	  {
	    int pos = 0;
	    pos = filePathName.lastIndexOf(47);
	    if (pos != -1)
	      return filePathName.substring(pos + 1, filePathName.length());
	    pos = filePathName.lastIndexOf(92);
	    if (pos != -1)
	      return filePathName.substring(pos + 1, filePathName.length());

	    return filePathName;
	  }

		//整合乐山版本  加的
	 public static String getChinaDate(){
		 	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date=simpleDateFormat.format(new Date());

			return date;
	 }
		//获取当前时间 如(2018-05-19 10:37:xx)
	 public static String getChinaDateNow(){
		 	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date=simpleDateFormat.format(new Date());

			return date;
	 }
	 //当前日期加几天后的日期
	 public static String getAfterDate(Integer days){

		 Calendar c = Calendar.getInstance();
		 c.add(Calendar.DATE, days);
		 Date d = c.getTime();
		 String afterDates = new SimpleDateFormat("yyyy-MM-dd").format(d);

		 return afterDates;
	 }
    //当前日期加几分钟后的日期
    public static String getAfterMinuteDate(Integer minute){

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, minute);
        Date d = c.getTime();
        String afterDates = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);

        return afterDates;
    }
	 /**
	  * 获取全中文日期（如：2014-10-23 获取为： 二〇一四年十月二十三日）
	  * @param date 指定日期
	  * @return 日期的全中文格式
	  */
	 public static String getChineseDate(Date date){
		 StringBuffer sbDate = new StringBuffer();
		 String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
		 String[] dateArray = dateStr.split("-");
		 String year = dateArray[0];
		 for(int i=0; i<year.length(); i++){
			 char n = year.charAt(i);
			 switch (n) {
			 case '0': sbDate.append("〇");break;
			 case '1': sbDate.append("一");break;
			 case '2': sbDate.append("二");break;
			 case '3': sbDate.append("三");break;
			 case '4': sbDate.append("四");break;
			 case '5': sbDate.append("五");break;
			 case '6': sbDate.append("六");break;
			 case '7': sbDate.append("七");break;
			 case '8': sbDate.append("八");break;
			 case '9': sbDate.append("九");break;
			 }
		 }
		 sbDate.append("年");
		 String month = dateArray[1];
		 for(int i=0; i<month.length(); i++){
			 char n = month.charAt(i);
			 if(i==0 && n=='0'){

			 } else if(i==0 && n=='1'){
				 sbDate.append("十");
			 } else if(i==0){
				 switch (n) {
				 case '2': sbDate.append("二十");break;
				 case '3': sbDate.append("三十");break;
				 case '4': sbDate.append("四十");break;
				 case '5': sbDate.append("五十");break;
				 case '6': sbDate.append("六十");break;
				 case '7': sbDate.append("七十");break;
				 case '8': sbDate.append("八十");break;
				 case '9': sbDate.append("九十");break;
				 }
			 }
			 if(i==1 && n=='0'){

			 } else if(i==1){
				 switch (n) {
				 case '1': sbDate.append("一");break;
				 case '2': sbDate.append("二");break;
				 case '3': sbDate.append("三");break;
				 case '4': sbDate.append("四");break;
				 case '5': sbDate.append("五");break;
				 case '6': sbDate.append("六");break;
				 case '7': sbDate.append("七");break;
				 case '8': sbDate.append("八");break;
				 case '9': sbDate.append("九");break;
				 }
			 }
		 }
		 sbDate.append("月");
		 String dates = dateArray[2];
		 for(int i=0; i<dates.length(); i++){
			 char n = dates.charAt(i);
			 if(i==0 && n=='0'){

			 } else if(i==0 && n=='1'){
				 sbDate.append("十");
			 } else if(i==0){
				 switch (n) {
				 case '2': sbDate.append("二十");break;
				 case '3': sbDate.append("三十");break;
				 case '4': sbDate.append("四十");break;
				 case '5': sbDate.append("五十");break;
				 case '6': sbDate.append("六十");break;
				 case '7': sbDate.append("七十");break;
				 case '8': sbDate.append("八十");break;
				 case '9': sbDate.append("九十");break;
				 }
			 }
			 if(i==1 && n=='0'){

			 } else if(i==1){
				 switch (n) {
				 case '1': sbDate.append("一");break;
				 case '2': sbDate.append("二");break;
				 case '3': sbDate.append("三");break;
				 case '4': sbDate.append("四");break;
				 case '5': sbDate.append("五");break;
				 case '6': sbDate.append("六");break;
				 case '7': sbDate.append("七");break;
				 case '8': sbDate.append("八");break;
				 case '9': sbDate.append("九");break;
				 }
			 }
		 }
		 sbDate.append("日");
		 return sbDate.toString();

		 /*
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 String date1 = dateFormat.format(date);
		 String finalDate = "";
		 for(int i =0;i<4;i++){
			 switch(date1.charAt(i)){
			 case '0':finalDate+="〇";break;
			 case '1':finalDate+="一";break;
			 case '2':finalDate+="二";break;
			 case '3':finalDate+="三";break;
			 case '4':finalDate+="四";break;
			 case '5':finalDate+="五";break;
			 case '6':finalDate+="六";break;
			 case '7':finalDate+="七";break;
			 case '8':finalDate+="八";break;
			 case '9':finalDate+="九";break;
			}
		 }
		 finalDate+="年";
		 if(date1.charAt(5)=='1'){
			 finalDate+="十";
		 }
		 if(date1.charAt(6)!='0'){
			 switch(date1.charAt(6)){
				 case '1':finalDate+="一月";break;
				 case '2':finalDate+="二月";break;
				 case '3':finalDate+="三月";break;
				 case '4':finalDate+="四月";break;
				 case '5':finalDate+="五月";break;
				 case '6':finalDate+="六月";break;
				 case '7':finalDate+="七月";break;
				 case '8':finalDate+="八月";break;
				 case '9':finalDate+="九月";break;
			 }
		 }
		 if(date1.charAt(8)=='1'){
			 finalDate+="十日";
		 }else if(date1.charAt(8)=='2'){
			 finalDate+="二十日";
		 }else if(date1.charAt(8)=='3'){
			 finalDate+="三十日";
		 }
		switch(date1.charAt(9)){
		 case '1':finalDate+="一日";break;
		 case '2':finalDate+="二日";break;
		 case '3':finalDate+="三日";break;
		 case '4':finalDate+="四日";break;
		 case '5':finalDate+="五日";break;
		 case '6':finalDate+="六日";break;
		 case '7':finalDate+="七日";break;
		 case '8':finalDate+="八日";break;
		 case '9':finalDate+="九日";break;
		}

		 return finalDate;*/
	}
	/**
 	 * 显示上个月日期
 	 * @param dateStr
 	 * @return
 	 */
 	public static String getLastDate(String dateStr){
 		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
        Calendar  calendar=Calendar.getInstance();
        try {
			calendar.setTime(sdf.parse(dateStr.substring(0, 8)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       //取得上一个月时间
        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH)-1);
        String lastMonth= sdf.format(calendar.getTime());
        return lastMonth + "-"+dateStr.substring(8, 10);
 	}
 	 /**
	  * 根据输入的出生日期计算年龄
	  * @param strDate
	  * @return
	  * @throws ParseException
	  */
	 public static String getAgeByBirthDay(String strDate) throws ParseException{
		 String age = "0";
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 Date date = new Date();
		 date = sdf.parse(strDate);
		 //使用calendar进行计算
		 Calendar calendar = Calendar.getInstance();
		 //获取当前时间毫秒值
		 long now = (new Date()).getTime();
		 long Birthdate = date.getTime();
		 long time = now-Birthdate;
		 int count=0;
		 //时间换算
		 long days = time/1000/60/60/24;
		 //判断闰年
		 int birthYear = Integer.parseInt(( strDate.substring(0, 4)));
		 for(int i=calendar.get(Calendar.YEAR);i>=birthYear;i--){
			 if((i%4==0 && !(i%100==0)) ||     (i%400==0) ){   count++;  }
		 	//加入闰年因素进行整理换算
		 	age = (((int)days-count)/365)+"";
	     }
		 return age;
	 }



	 /**
	   * 得到几天后的时间
	   *
	   * @param d
	   * @param day
	   * @return
	   */
	  public static String getDateAfter(Date d, int day) {

	      Calendar now = Calendar.getInstance();
	      now.setTime(d);
	      now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
	      Date after = now.getTime();
	      String afterDates = new SimpleDateFormat("yyyy-MM-dd").format(after);

	      return afterDates;
	  }

	  /**
	   * 将集合转为字符串
	   */
	  public static String listToStr(List<String> list){
		  StringBuffer buf = new StringBuffer();
		  for(String str : list){
			  buf.append(str+",");
		  }
		  String str = buf.toString();
		  str = str.substring(0, str.length()-1);
		  return str;
	  }


    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
}
