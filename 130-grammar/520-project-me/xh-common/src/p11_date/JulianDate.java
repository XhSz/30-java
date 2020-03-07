package p11_date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class JulianDate {
	public static void main(String[] args) {
		try {
			System.out.println(juLianToDate(9287));
			System.out.println(juLianToDate(19287));
			System.out.println(juLianToDate(119287));
			System.out.println(dateToJuLian(getDateFromJulian7("20191013")));
			System.out.println(dateToJuLian(getDateFromJulian7("20191014")));
			System.out.println(dateToJuLian(getDateFromJulian7("20191015")));
			System.out.println(getDateFromJulian5("19287"));
//			System.out.println(dateToJuLian(getDateFromJulian5("20191014")));
//			System.out.println(dateToJuLian(getDateFromJulian5("20191015")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

    public static Date getDateFromJulian7(String julianDate) throws ParseException {
        return new SimpleDateFormat("yyyyMMdd").parse(julianDate);
    }
    public static Date getDateFromJulian5(String julianDate) throws ParseException {
        return new SimpleDateFormat("yyyyD").parse(julianDate);
    }
    
	//将当前日期转换成JuLian
	 public static int dateToJuLian(java.util.Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    int year = calendar.get(Calendar.YEAR) - 1900;
	    int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
	    return year * 1000 + dayOfYear;
	  }
	 
	    //juLian 转换成正常日期
	  public static java.util.Date juLianToDate(int date) {
	    int year = (date / 1000) + 1900;
	    int dayOfYear = date % 1000;
	 
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.YEAR, year);
	    calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
	 
	    return calendar.getTime();
	  }
}
