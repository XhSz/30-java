package p11_date;

public class CommonDate {
	public static void main(String[] args) {
		//yyyyMMdd 
		//yyyy-MM-dd HH:mm:ss,SSS
		//{day, hour, min, sec}
		long[] r = DateDistance.getDistanceTimesPattern("2019-12-17 09:34:18,991","2019-12-17 09:34:18,993","yyyy-MM-dd HH:mm:ss,SSS");
		System.out.println(r[4]);
	}
}
