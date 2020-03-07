package p12_String;


public class CommonString {
	
	public static void main(String[] args) {
		//String ts = "select a.*,b.chrg_cond_code,b.chrg_form_code from cmp_chrg_code a join cmp_chrg_code_form b on a.chrg_code=b.chrg_code and a.org_id=b.org_id where (a.org_id='2086') and b.trxn_event_id = 'DP003' and (b.expiry_date>='20200803'  and  b.effect_date<='20200803')";
		//System.out.println(delSqlValue(ts));
		

//		java.lang.String header_str = "9043674119287      1928700400     0000      000000000000000000CIMBPH11      001";
//		System.out.println(header_str.substring(2,8));
//		System.out.println(header_str.substring(8,13));
		split();


	}

	public static void split(){
		String ts = "select a.*,b.chrg_c"; // * 会出bug，无法识别，应改为 \\* {Dangling meta character '*' near index 0}
		split(ts);
	}
	public static void split(String in){
		String[] rs = in.split("\\*");
		System.out.println(rs.length);
	}
	public static String delSqlValue(String in){
		String[] inArray = in.split("'");
		StringBuilder outSB = new StringBuilder();
		for(int i=0;i<inArray.length;i++){
			if(0==i%2)
				outSB = outSB.append(inArray[i]);
		}
		return outSB.toString();
	}

	public static String transferDoubleQuotationMarks(String in){
		if(in.indexOf("\"")>0)
			return in.replace("\"", "\\\"");
		else
			return in;
	}
	public static String lpad(String s, int n, String replace) {
		while (s.length() < n) {
			s = replace+s;
		}
		return s;
	}
	private String rpad(String s, int n, String replace) {
		while (s.length() < n) {
			s = s+replace;
		}
		return s;
	}
}
