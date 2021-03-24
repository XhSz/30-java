package p50_project_v1_4_6;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class J2_Config {

    static String JSON_PATH = "JSON_PATH";
    static String VUE_CODE_PATH = "VUE_CODE_PATH";
    static String VUE_JSON_PATH = "VUE_JSON_PATH";
    static String ICORE_JSON_PATH = "ICORE_JSON_PATH";
    static String TRAN_JSON_PATH = "TRAN_JSON_PATH"; 
    static String MENU_JSON_PATH = "MENU_JSON_PATH";
    static String ONL_JSON_PATH = "ONL_JSON_PATH";
    static String BAT_JSON_PATH = "BAT_JSON_PATH";
    static String DB_URL = "DB_URL";
    static String DB_USER = "DB_USER";
    static String DB_PW = "DB_PW";
    static String DB_URL_ICORE = "DB_URL_ICORE";
    static String DB_USER_ICORE = "DB_USER_ICORE";
    static String DB_PW_ICORE = "DB_PW_ICORE";
    static String DB_URL_PO = "DB_URL_PO";
    static String DB_USER_PO = "DB_USER_PO";
    static String DB_PW_PO = "DB_PW_PO";
    static String ICORE_CODE_PATH = "ICORE_CODE_PATH";
    static String TABLE_MODEL_PATH = "TABLE_MODEL_PATH";
    static String TABLE_PATH = "TABLE_PATH";
    
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
		ex.put(DB_URL_PO, "jdbc:mysql://10.22.61.3:3306/podb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
		ex.put(DB_USER_PO, "po");
		ex.put(DB_PW_PO, "po");
		ex.put(VUE_CODE_PATH, "D:\\03-sl-107-code\\27-ex\\latest\\sump-vue\\src");
		ex.put(VUE_JSON_PATH, "D:\\03-sl\\327-exim\\ex-vue.json");
		ex.put(ICORE_JSON_PATH, "D:\\03-sl\\327-exim\\ex-icore.json");
		ex.put(ICORE_CODE_PATH, "D:\\03-sl-107-code\\27-ex\\latest");
		ex.put(TRAN_JSON_PATH, "D:\\03-sl\\327-exim\\ex-tran.json");
		ex.put(MENU_JSON_PATH, "D:\\03-sl\\327-exim\\ex-menu.json");
		ex.put(ONL_JSON_PATH, "D:\\03-sl\\327-exim\\ex-onl.json");
		ex.put(BAT_JSON_PATH, "D:\\03-sl\\327-exim\\ex-bat.json");
		ex.put(TABLE_MODEL_PATH, "D:\\03-sl\\105-key\\cbs\\1.5-tables\\00-model-gs.xlsx");
		ex.put(TABLE_PATH, "D:\\30-java\\130-grammar\\520-project-me\\xh-cm\\key\\ex.xlsx");
		CONFIG.put(exId, ex);
		String gsId = "27";//27 gs
		Map gs = new HashMap<String,String>();
		gs.put(JSON_PATH, "D:\\30-java\\130-grammar\\520-project-me\\xh-cm\\key\\gs.json");
		gs.put(DB_URL, "jdbc:mysql://10.22.12.109:3323/bat_sump?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
		gs.put(DB_USER, "bat_sump");
		gs.put(DB_PW, "bat_sump");
		gs.put(DB_URL_ICORE, "jdbc:mysql://10.22.12.109:3323/bat_core?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
		gs.put(DB_USER_ICORE, "bat_core");
		gs.put(DB_PW_ICORE, "bat_core");
		gs.put(VUE_CODE_PATH, "D:\\03-sl-107-code\\26-gs\\201127-vue\\sump-vue\\src");
		gs.put(VUE_JSON_PATH, "D:\\03-sl\\326-gs\\gs-vue.json");
		gs.put(ICORE_JSON_PATH, "D:\\03-sl\\326-gs\\gs-icore.json");
		gs.put(ICORE_CODE_PATH, "D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable");
		gs.put(TRAN_JSON_PATH, "D:\\03-sl\\326-gs\\gs-tran.json");
		gs.put(MENU_JSON_PATH, "D:\\03-sl\\326-gs\\gs-menu.json");
		gs.put(ONL_JSON_PATH, "D:\\03-sl\\326-gs\\gs-onl.json");
		gs.put(BAT_JSON_PATH, "D:\\03-sl\\326-gs\\gs-bat.json");
		gs.put(TABLE_MODEL_PATH, "D:\\03-sl\\105-key\\cbs\\1.5-tables\\00-model-gs.xlsx");
		gs.put(TABLE_PATH, "D:\\30-java\\130-grammar\\520-project-me\\xh-cm\\key\\gs.xlsx");
		
		CONFIG.put(gsId, gs);
		String localId = "06";//06 local
		Map local = new HashMap<String,String>();
		local.put(JSON_PATH, "D:\\30-java\\130-grammar\\520-project-me\\xh-cm\\key\\gs.json");
		local.put(DB_URL, "jdbc:mysql://localhost:3306/sump?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
		local.put(DB_USER, "root");
		local.put(DB_PW, "hao6990807");
		local.put(DB_URL_ICORE, "jdbc:mysql://localhost:3306/sump?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
		local.put(DB_USER_ICORE, "root");
		local.put(DB_PW_ICORE, "hao6990807");
		local.put(VUE_CODE_PATH, "D:\\03-sl-107-code\\26-gs\\201127-vue\\sump-vue\\src");
		local.put(VUE_JSON_PATH, "D:\\03-sl\\326-gs\\gs-vue.json");
		local.put(ICORE_JSON_PATH, "D:\\03-sl\\326-gs\\gs-icore.json");
		local.put(ICORE_CODE_PATH, "D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable");
		local.put(TRAN_JSON_PATH, "D:\\03-sl\\326-gs\\gs-tran.json");
		CONFIG.put(localId, local);
	}
	public static String CODE_PATH = ((Map)CONFIG.get(J2_Main.PROJECT_ID)).get(ICORE_CODE_PATH).toString();
}
