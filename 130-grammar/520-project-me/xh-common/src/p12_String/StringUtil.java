package p12_String;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
	
	public static String SOURCE = "12345678901234567890";
	
	public static void main(String[] args) {
		System.out.println(SOURCE.substring(5));
//		System.out.println(SOURCE.substring(5,2));  //java.lang.StringIndexOutOfBoundsException: String index out of range: -3
		System.out.println(SOURCE.substring(SOURCE.length()-5,SOURCE.length())); 
//		System.out.println(SOURCE.substring(-5));	//java.lang.StringIndexOutOfBoundsException: String index out of range: -5
		System.out.println(StringUtils.capitalize("person"));
	}
}
