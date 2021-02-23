package p50_project_v1_3_1;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class J2_Config {

    static String JSON_PATH = "JSON_PATH";
    static String VUE_PATH = "VUE_PATH";
    static String VUE_OUT_PATH = "VUE_OUT_PATH";
    static String ICORE_PATH = "ICORE_PATH";
    static String CT_PATH = "CT_PATH";
    static String DB_URL = "DB_URL";
    static String DB_USER = "DB_USER";
    static String DB_PW = "DB_PW";
    static String DB_URL_ICORE = "DB_URL_ICORE";
    static String DB_USER_ICORE = "DB_USER_ICORE";
    static String DB_PW_ICORE = "DB_PW_ICORE";
    static String ICORE_CODE_PATH = "ICORE_CODE_PATH";
	public static Map CONFIG = new HashMap<String,Map>();
	static {
		String exId = "26";//26 ex
		Map ex = new HashMap<String,String>();
		ex.put(JSON_PATH, "D:\\03-sl\\327-exim\\ex.json");
		ex.put(DB_URL, "jdbc:mysql://10.22.61.3:3306/ctdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
		ex.put(DB_USER, "ct");
		ex.put(DB_PW, "ct");
		ex.put(DB_URL_ICORE, "jdbc:mysql://10.22.61.3:3306/icoredb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
		ex.put(DB_USER_ICORE, "icore");
		ex.put(DB_PW_ICORE, "icore");
		ex.put(VUE_PATH, "D:\\03-sl-107-code\\27-ex\\vue-210126\\sump-vue\\src");
		ex.put(VUE_OUT_PATH, "D:\\03-sl\\327-exim\\ex-vue.json");
		ex.put(ICORE_PATH, "D:\\03-sl\\327-exim\\ex-icore.json");
		ex.put(CT_PATH, "D:\\03-sl\\327-exim\\ex-ct.json");
		CONFIG.put(exId, ex);
		String gsId = "27";//27 gs
		Map gs = new HashMap<String,String>();
		gs.put(JSON_PATH, "D:\\03-sl\\326-gs\\gs.json");
		gs.put(DB_URL, "jdbc:mysql://10.22.12.109:3323/bat_sump?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
		gs.put(DB_USER, "bat_sump");
		gs.put(DB_PW, "bat_sump");
		gs.put(VUE_PATH, "D:\\03-sl-107-code\\26-gs\\201127-vue\\sump-vue\\src");
		gs.put(VUE_OUT_PATH, "D:\\03-sl\\326-gs\\gs-vue.json");
		gs.put(ICORE_CODE_PATH, "D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable");
		CONFIG.put(gsId, gs);
	}
}
