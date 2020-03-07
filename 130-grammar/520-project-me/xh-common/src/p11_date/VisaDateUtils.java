package p11_date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class VisaDateUtils {
	public static void main(String[] args) throws Exception {
		//System.out.println(getDateFromJulian7("19287"));
		//System.out.println(getDateFromJulian7("9287"));
		System.out.println(getJulianYYDDDFromString("19287"));
		System.out.println(getJulianYYDDDFromString("9287"));
	}
	
    public static Date getDateFromJulian7(String julianDate) throws ParseException {
        return new SimpleDateFormat("yyyyD").parse(julianDate);
    }

    public static String getJulian7FromDate(Date date) {
        StringBuilder sb = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return sb.append(cal.get(Calendar.YEAR)).append(String.format("%03d", cal.get(Calendar.DAY_OF_YEAR))).toString();
    }

    public static String getJulianYYDDDFromString(String yyyymmdd) {
        // String trDate="20120106";
        try {
            Date tradeDate = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(yyyymmdd);

            StringBuilder sb = new StringBuilder();
            Calendar cal = Calendar.getInstance();
            cal.setTime(tradeDate);

            return sb.append(String.valueOf(cal.get(Calendar.YEAR)).substring(2, 4)).append(String.format("%03d", cal.get(Calendar.DAY_OF_YEAR))).toString();
        } catch (Exception e) {
        	System.out.println(e);
            return "";
        }
    }
}
