package p11_date;

import java.util.Calendar;
import java.util.Date;

public class Ap_DateTimeUtil {
	
	public static String dateAdd(String type, String date, int num) {
		int amount;
		int precision;
		Date d1 = new Date();//Convert.toDate(date, DateConvertUtil.DEFAULT_DATE8_PATTERN);
		if (("day".equals(type)) || ("dd".equals(type))) {
			precision = 6;
			amount = num;
		} else {
			
			if (("year".equals(type)) || ("yy".equals(type))) {
				precision = 1;
				amount = num;
			} else {
				
				if (("month".equals(type)) || ("mm".equals(type))) {
					precision = 2;
					amount = num;
				} else {
					
					if (("quarter".equals(type)) || ("qq".equals(type))) {
						precision = 2;
						amount = 3 * num;
					} else {
						
						if (("week".equals(type)) || ("ww".equals(type))) {
							precision = 6;
							amount = 7 * num;
						} else {
							throw new IllegalArgumentException("传入参数" + type
									+ "不合法!");
						}
					}
				}
			}
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(d1);
		cal.add(precision, amount);
		return new String();//DateConvertUtil.dateToString8(cal.getTime());
	}
}
