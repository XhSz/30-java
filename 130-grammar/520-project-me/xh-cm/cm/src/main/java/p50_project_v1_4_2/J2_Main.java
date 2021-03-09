package p50_project_v1_4_2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

import p50_project_v1.J1_BeanCall;
import p50_project_v1.J1_BeanDb;
import p50_project_v1.J1_BeanMenu;
import p50_project_v1.J1_BeanTran;
import p50_project_v1.J1_BeanTranRelate;

/* 1.1 vue->json
 * 1.2 add config,
 */
public class J2_Main {
	public static int LOG_LEVEL=5;
	//1 ERROR
	//3 SYSTEM
	static boolean LS = LOG_LEVEL>2;
    static String PROJECT_ID = 
//    		"26"	//26 ex
//    		"27"	//27 gs
    		"06"	//06 local
    		;  
	public static int M = 0000;
	public static boolean isRealTime = false;
	public static String CP = ((Map)J2_Config.CONFIG.get(J2_Main.PROJECT_ID)).get(J2_Config.ICORE_CODE_PATH).toString();
	static String TRAN_JSON_PATH = ((Map)J2_Config.CONFIG.get(J2_Main.PROJECT_ID)).get(J2_Config.TRAN_JSON_PATH).toString();
	static String JSON_PATH = ((Map)J2_Config.CONFIG.get(PROJECT_ID)).get(J2_Config.JSON_PATH).toString();
	
	public static String STR_0 = "STR_0-全过程";
	public static String STR_9_MENU_1_UPDATE = "9_MENU_1_UPDATE-交易匹配更新";
	public static boolean DO_9_MENU_1_UPDATE = false;//交易匹配更新
	public static Map<String,String> MAP_9_MENU_1_UPDATE = new HashMap<String,String>();//交易匹配更新
	public static String STR_9_MENU_1_MATCH = "9_MENU_1_MATCH-交易匹配初始化";
	public static boolean DO_9_MENU_1_MATCH = false;//交易匹配
	public static Map<String,String> MAP_9_MENU_1_MATCH = new HashMap<String,String>();//交易匹配
	public static String STR_9_MENU_2_VUE = "9_MENU_2_VUE-扫描vue,更新到menu";
	public static boolean DO_9_MENU_2_VUE = false;//扫描vue,更新到menu
	public static String STR_9_MENU_3_TREE = "9_MENU_3_TREE-扫菜单树结构初始化";
	public static J1_BeanMenu TREE_9_MENU_3_TREE = null;
	public static boolean DO_9_MENU_3_TREE = false;//菜单树结构初始化
	public static String STR_9_MENU_9_PRINT = "9_MENU_9_PRINT-输出最终json";
	
    public static void main(String[] args) {

		J3_Util.logB(STR_0);
		List<J1_BeanTran> tranList = new ArrayList<J1_BeanTran>();
		List<J1_BeanTranRelate> tranRelateList = new ArrayList<J1_BeanTranRelate>();
		Set<String> callKeySet = new HashSet<String>();
		Set<J1_BeanCall> callSet = new HashSet<J1_BeanCall>();
		Set<String> dbKeySet = new HashSet<String>();
		Set<J1_BeanDb> dbSet = new HashSet<J1_BeanDb>();
		Map<String,String> dbMap = new HashMap();
		
		M = 1390;
		boolean isMenu = (M==1390||M==1391||M==1392||M==1300);//是否需初始化menu
		boolean isTran = (M==1310||M==1320||M==1301||M==1300);//是否需要初始化tran,tranRealte
		boolean isCallSet = (M==1330||M==1307||M==1300);//是否需要第一遍初始化方法call
		boolean isDbSet = (M==1352||M==1353||M==1307||M==1300);//是否需要初始化db
		boolean isCallRelate = (M==1340||M==1307||M==1300);//是否需要插入callRelate
		boolean isPrint = (M==1390||M==1392||M==1300);//是否需初始化menu
		THREAD_9_MENU_1_MATCH.start();
		THREAD_9_MENU_2_VUE.start();
		THREAD_9_MENU_1_UPDATE.start();
		THREAD_9_MENU_3_TREE.start();
		THREAD_9_MENU_9_PRINT.start();
	}

	public static Thread THREAD_9_MENU_1_UPDATE = new Thread(){
        public void run(){
    		try {
	        	while(!DO_9_MENU_1_MATCH) {
						Thread.sleep(1000);
	        	}
	    		J3_Util.logB(STR_9_MENU_1_UPDATE);
	    		J3_Util.createTranJson(MAP_9_MENU_1_MATCH);
	        	JSONObject tranJson =  JSONObject.parseObject(J3_Util.getJson(TRAN_JSON_PATH));
	        	while(!DO_9_MENU_2_VUE) {
						Thread.sleep(1000);
	        	}
	        	J5_Sql.doMain(229, tranJson);
	    		J3_Util.logE(STR_9_MENU_1_UPDATE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
    };
	public static Thread THREAD_9_MENU_1_MATCH = new Thread(){
        public void run(){
    		J3_Util.logB(STR_9_MENU_1_MATCH);
        	J5_Sql.doMain(348, MAP_9_MENU_1_MATCH, 3);
        	J5_Sql.doMain(348, MAP_9_MENU_1_MATCH);
    		J3_Util.logE(STR_9_MENU_1_MATCH);
    		DO_9_MENU_1_MATCH = true;
        }
    };
	public static Thread THREAD_9_MENU_2_VUE = new Thread(){
        public void run(){
    		J3_Util.logB(STR_9_MENU_2_VUE);
        	J6_Vue.scanVueRequestToJson();
        	J6_Vue.parseJsonUpdateMenu();
    		J3_Util.logE(STR_9_MENU_2_VUE);
    		DO_9_MENU_2_VUE = true;
        }
    };
	public static Thread THREAD_9_MENU_3_TREE = new Thread(){
        public void run(){
        	while(!DO_9_MENU_2_VUE) {
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
    		J3_Util.logB(STR_9_MENU_3_TREE);
        	J6_Vue.scanVueRequestToJson();
        	J6_Vue.parseJsonUpdateMenu();
    		J3_Util.logE(STR_9_MENU_3_TREE);
    		DO_9_MENU_3_TREE = true;
        }
    };
	public static Thread THREAD_9_MENU_9_PRINT = new Thread(){
        public void run(){
    		try {
        		Thread.sleep(10*1000);
        		boolean start = false;
            	while(!start) {
            		Thread.sleep(1000);
            		start = DO_9_MENU_1_MATCH&&DO_9_MENU_3_TREE;
            	}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		J3_Util.logB(STR_9_MENU_9_PRINT);
    		TREE_9_MENU_3_TREE = (J1_BeanMenu)J5_Sql.doMain(339, J5_Sql.ROOT_TREE);//339,select if,tree_menu
			try {
				J3_Util.printToJson(J3_Util.getBranchSb(TREE_9_MENU_3_TREE,MAP_9_MENU_1_MATCH), JSON_PATH);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
    		J3_Util.logE(STR_9_MENU_9_PRINT);
    		J3_Util.logE(STR_0);
        }
    };
}
