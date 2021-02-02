package p06_point;

import java.math.BigDecimal;

public class NullPointer {
	public static void main(String[] args) {
		String string = null;
		BigDecimal bigdecimal = null;
		System.out.println(setDefault(string)); // no = null , compile error , The local variable string may not have been initialized
		System.out.println(setDefault(bigdecimal)); // no = null , compile error , The local variable bigdecimal may not have been initialized
		string = "ABC";
		bigdecimal = new BigDecimal("0");
		System.out.println(setDefault(string));
		System.out.println(setDefault(bigdecimal));
	}
	public static String setDefault(Object v){
		String r = null;
		if(v instanceof String){
			if(null!=v) r = v.toString();
		}else if(v instanceof BigDecimal){
			if(null==v)r = "0.00";
			else r = v.toString();
		}
		return r;
	}
}
