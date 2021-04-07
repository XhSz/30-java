package p50_project_v1_4_6;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

import p50_project_v1.J1_BeanCall;
import p50_project_v1.J1_BeanCallRelate;
import p50_project_v1.J1_BeanDb;
import p50_project_v1.J1_BeanMenu;
import p50_project_v1.J1_BeanTran;
import p50_project_v1.J1_BeanTranRelate;

public class J2_Main {
	public static int LOG_LEVEL=5;
	//1 ERROR
	//3 SYSTEM
	static boolean LS = LOG_LEVEL>2;
    static String PROJECT_ID = 
//    		"26"	//26 ex
//    		"27"	//27 gs
//    		"06"	//06 local
    		"12"	//12 tz
    		;  
	public static int M = 0000;
	public static boolean isRealTime = false;
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

	/* 1.1 vue->json
	 * 1.2 add config,
	 * 1.3.1 J71 
	 * 		scan tran
	 * 1.3.2 J72
	 * 		scan java , add note "功能说明"
	 * 1.3.3 J72
	 * 		scan java , add note /**	失败版本
	 * 1.3.4 J72
	 * 		sacn java , 改成每行读取
	 * 1.3.5 J72
	 * 		scan java , 识别单行注释/多行注释
	 * 1.3.5 J73
	 * 		scan table,nsql
	 * 1.3.5 J72
	 * 		scan java, 识别表
	 * 1.4.1 J72_Tran_Main
	 * 		变量控制集成版本
	 * 1.4.2 J2_Main
	 * 		多线程运行前端部分集成版本
	 * 1.4.3 J2_Main
	 * 		多线程运行全功能集成版本
	 * 1.4.4 J2_Main
	 * 		多线程运行集成简易发布版本
	 * 1.4.5 J2_Main
	 * 		优化版本
	 * 1.3.6  增加nsql备注
	 * 1.3.7 menu以外的call输出,跑批程序逻辑输出
	 * 1.3.8  整合成单一json，注释输出
	 * 1.3.9 tables.xlsx生成
	 * 1.3.10 tables.xlsx 枚举值注释
	 * 后续计划:
	 * 		vue精准识别，table、nsql、服务调用逻辑，jar级别扫描，跑批程序逻辑输出
	 */
	
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
        		Thread.sleep(60*1000);
        		boolean start = false;
            	while(!start) {
            		Thread.sleep(1000);
            		start = DO_9_MENU_1_MATCH&&DO_9_MENU_3_TREE&&DO_4_RELATE_1_INIT;
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

    public static List<J1_BeanTran> tranList = new ArrayList<J1_BeanTran>();
    public static List<J1_BeanTranRelate> tranRelateList = new ArrayList<J1_BeanTranRelate>();
	public static String STR_1_TRAN_1_INIT = "1_TRAN_INIT-交易初始化";
	public static boolean DO_1_TRAN_1_INIT = false;//交易初始化
	public static String STR_1_TRAN_2_INSERT = "1_TRAN_INSERT-交易入库";
	public static boolean DO_1_TRAN_2_INSERT = false;//交易入库
	public static Set<String> callKeySet = new HashSet<String>();
	public static Set<J1_BeanCall> callSet = new HashSet<J1_BeanCall>();
	public static String STR_3_CALL_1_INIT = "3_CALL_1_INIT-方法首次初始化";
	public static boolean DO_3_CALL_1_INIT = false;//方法首次初始化
	public static String STR_3_CALL_2_INSERT = "3_CALL_2_INSERT-方法首次初始化入库";
	public static boolean DO_3_CALL_2_INSERT = false;//方法首次初始化入库
	public static Set<String> dbKeySet = new HashSet<String>();
	public static Set<J1_BeanDb> dbSet = new HashSet<J1_BeanDb>();
	public static Set<J1_BeanDb> dbTablesSet = new HashSet<J1_BeanDb>();
	public static String STR_5_DB_2_TABLES_1_INIT = "5_DB_2_TABLES_1_INIT-表初始化";
	public static boolean DO_5_DB_2_TABLES_1_INIT = false;//表初始化
	public static String STR_5_DB_2_TABLES_2_INSERT = "5_DB_2_TABLES_2_INSERT-表初始化入库";
	public static boolean DO_5_DB_2_TABLES_2_INSERT = false;//表初始化入库
	public static Set<J1_BeanDb> dbNsqlSet = new HashSet<J1_BeanDb>();
	public static String STR_5_DB_3_NSQL_1_INIT = "5_DB_3_NSQL_1_INIT-命名SQL初始化";
	public static boolean DO_5_DB_3_NSQL_1_INIT = false;//命名SQL初始化
	public static String STR_5_DB_3_NSQL_2_INSERT = "5_DB_3_NSQL_2_INSERT-命名SQL初始化入库";
	public static boolean DO_5_DB_3_NSQL_2_INSERT = false;//命名SQL初始化入库
	public static Map<String,String> dbMap = new HashMap();
	public static List<J1_BeanCallRelate> callRelateList = new ArrayList<J1_BeanCallRelate>();
	public static String STR_4_RELATE_1_INIT = "4_RELATE_1_INIT-调用关系初始化";
	public static boolean DO_4_RELATE_1_INIT = false;//调用关系初始化
	public static String STR_4_RELATE_2_INSERT = "4_RELATE_2_INSERT-调用关系入库";
	public static boolean DO_4_RELATE_2_INSERT = false;//调用关系入库
	
	public static Thread THREAD_1_TRAN_1_INIT = new Thread(){
        public void run(){
    		J3_Util.logB(STR_1_TRAN_1_INIT);
			J71_Tran_Util.scanTran(J2_MainUnit.CP,tranList,tranRelateList);//扫描代码根目录，获得所有flowtrans
    		J3_Util.logE(STR_1_TRAN_1_INIT);
    		DO_1_TRAN_1_INIT = true;
        }
    };
	public static Thread THREAD_1_TRAN_2_INSERT = new Thread(){
        public void run(){
        	while(!DO_1_TRAN_1_INIT) {
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
    		J3_Util.logB(STR_1_TRAN_2_INSERT);
	    	if(!isRealTime) {
				J5_Sql.doMain(441, null);//441,del all,tree_tran
				J5_Sql.doMain(442, null);//442,del all,tree_tran_relate
		    	J5_Sql.doMain(131,tranList);//131,batch insert,tree_call
		    	J5_Sql.doMain(132,tranRelateList);//132,batch insert,tree_tran_relate
        	}
    		J3_Util.logE(STR_1_TRAN_2_INSERT);
    		DO_1_TRAN_2_INSERT = true;
        }
    };
	public static Thread THREAD_3_CALL_1_INIT = new Thread(){
        public void run(){
    		J3_Util.logB(STR_3_CALL_1_INIT);
	    	J72_Tran_Util.scanJavaFolder(J2_MainUnit.CODE_PATH_JAVE, callKeySet,null,callSet, null);//扫描代码根目录，获得所有java,method并入库
    		J3_Util.logE(STR_3_CALL_1_INIT);
    		DO_3_CALL_1_INIT = true;
        }
    };
	public static Thread THREAD_3_CALL_2_INSERT = new Thread(){
        public void run(){
        	while(!DO_3_CALL_1_INIT) {
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
    		J3_Util.logB(STR_3_CALL_2_INSERT);
	    	if(!isRealTime) {
				J5_Sql.doMain(443, null);//443,del all,tree_call
    			J5_Sql.doMain(133, callSet);//133,batch insert,tree_call
        	}
    		J3_Util.logE(STR_3_CALL_2_INSERT);
    		DO_3_CALL_2_INSERT = true;
        }
    };
	public static Thread THREAD_5_DB_2_TABLES_1_INIT = new Thread(){
        public void run(){
    		J3_Util.logB(STR_5_DB_2_TABLES_1_INIT);
			J73_Tran_Util.scanFolder(J2_MainUnit.CP, 2, dbKeySet, dbTablesSet);// tables
			dbSet.addAll(dbTablesSet);
    		J3_Util.logE(STR_5_DB_2_TABLES_1_INIT);
    		DO_5_DB_2_TABLES_1_INIT = true;
        }
    };
	public static Thread THREAD_5_DB_2_TABLES_2_INSERT = new Thread(){
        public void run(){
        	while(!DO_5_DB_2_TABLES_1_INIT) {
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
    		J3_Util.logB(STR_5_DB_2_TABLES_2_INSERT);
	    	if(!isRealTime) {
    			J5_Sql.doMain(435, "table_type='T'");//435,del if,tree_db_bean 
    			J5_Sql.doMain(135, dbTablesSet);//135,batch insert,tree_db_bean
        	}
    		J3_Util.logE(STR_5_DB_2_TABLES_2_INSERT);
    		DO_5_DB_2_TABLES_2_INSERT = true;
        }
    };
	public static Thread THREAD_5_DB_3_NSQL_1_INIT = new Thread(){
        public void run(){
    		J3_Util.logB(STR_5_DB_3_NSQL_1_INIT);
			J73_Tran_Util.scanFolder(J2_MainUnit.CP, 3, dbKeySet, dbNsqlSet);// 3,nsql
			dbSet.addAll(dbNsqlSet);
    		J3_Util.logE(STR_5_DB_3_NSQL_1_INIT);
    		DO_5_DB_3_NSQL_1_INIT = true;
			J3_Util.print(dbNsqlSet);
        }
    };
	public static Thread THREAD_5_DB_3_NSQL_2_INSERT = new Thread(){
        public void run(){
        	while(!DO_5_DB_3_NSQL_1_INIT) {
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
    		J3_Util.logB(STR_5_DB_3_NSQL_2_INSERT);
	    	if(!isRealTime) {
    			J5_Sql.doMain(435, "table_type='N'");//435,del if,tree_db_bean 
    			J5_Sql.doMain(135, dbNsqlSet);//135,batch insert,tree_db_bean
        	}
    		J3_Util.logE(STR_5_DB_3_NSQL_2_INSERT);
    		DO_5_DB_3_NSQL_2_INSERT = true;
        }
    };
	public static Thread THREAD_4_RELATE_1_INIT = new Thread(){
        public void run(){
    		J3_Util.logB(STR_4_RELATE_1_INIT);
        	boolean start = false;
        	while(!start) {
        		try {
					Thread.sleep(1000);
					start = DO_5_DB_3_NSQL_2_INSERT&&DO_5_DB_2_TABLES_2_INSERT;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
			J5_Sql.doMain(345, dbMap);//345,select all,tree_call_db
        	while(!DO_3_CALL_1_INIT) {
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
			J72_Tran_Util.scanJavaFolder(J2_MainUnit.CODE_PATH_JAVE, callKeySet,dbMap,null,callRelateList);//遍历构建调用关系
    		J3_Util.logE(STR_4_RELATE_1_INIT);
    		DO_4_RELATE_1_INIT = true;
        }
    };
	public static Thread THREAD_4_RELATE_2_INSERT = new Thread(){
        public void run(){
    		try {
        		Thread.sleep(30*1000);
            	while(!DO_4_RELATE_1_INIT) {
            		Thread.sleep(1000);
            	}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		J3_Util.logB(STR_4_RELATE_2_INSERT);
	    	if(!isRealTime) {
    			J5_Sql.doMain(444, null);//444,del all,tree_call_relate
    			J5_Sql.doMain(134, callRelateList);//134,batch insert,tree_call_relate
        	}
    		J3_Util.logE(STR_4_RELATE_2_INSERT);
    		DO_4_RELATE_2_INSERT = true;
        }
    };
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
		boolean isPrint = (M==1390||M==1392||M==1300);//是否需初始化menu吧、
		boolean isVersion3 = true;
    	Object projectVersion = ((Map)J2_Config.CONFIG.get(J2_MainUnit.PROJECT_ID)).get(J2_Config.PROJECT_VERSION);
    	if(J2_Config.PROJECT_VERSION_1.equals(projectVersion))
    		isVersion3 = false;
		if((!isVersion3)&&true) {
			//1.0
//			THREAD_9_MENU_1_MATCH.start();
//			THREAD_9_MENU_2_VUE.start();
//			THREAD_9_MENU_1_UPDATE.start();
			
//			THREAD_1_TRAN_1_INIT.start();
//			THREAD_1_TRAN_2_INSERT.start();
			THREAD_3_CALL_1_INIT.start();
//			THREAD_3_CALL_2_INSERT.start();
//			THREAD_5_DB_2_TABLES_1_INIT.start();
//			THREAD_5_DB_2_TABLES_2_INSERT.start();
//			THREAD_5_DB_3_NSQL_1_INIT.start();
//			THREAD_5_DB_3_NSQL_2_INSERT.start();
			DO_5_DB_3_NSQL_2_INSERT = true;
			DO_5_DB_2_TABLES_2_INSERT = true;
			THREAD_4_RELATE_1_INIT.start();
//			THREAD_4_RELATE_2_INSERT.start();
//			THREAD_9_MENU_3_TREE.start();
    		DO_9_MENU_3_TREE = true;
    		DO_9_MENU_1_MATCH = true;
			THREAD_9_MENU_9_PRINT.start();
		}
		if(isVersion3&&false) {
			THREAD_9_MENU_1_MATCH.start();
			THREAD_9_MENU_2_VUE.start();
			THREAD_9_MENU_1_UPDATE.start();
			
			THREAD_1_TRAN_1_INIT.start();
			THREAD_1_TRAN_2_INSERT.start();
			THREAD_3_CALL_1_INIT.start();
			THREAD_3_CALL_2_INSERT.start();
			THREAD_5_DB_2_TABLES_1_INIT.start();
			THREAD_5_DB_2_TABLES_2_INSERT.start();
			THREAD_5_DB_3_NSQL_1_INIT.start();
			THREAD_5_DB_3_NSQL_2_INSERT.start();
			
			THREAD_4_RELATE_1_INIT.start();
			THREAD_4_RELATE_2_INSERT.start();
			THREAD_9_MENU_3_TREE.start();
			THREAD_9_MENU_9_PRINT.start();
		}
		if(false) {
			J3_Util.logB(STR_5_DB_3_NSQL_1_INIT);
			THREAD_5_DB_3_NSQL_1_INIT.start();
			THREAD_5_DB_3_NSQL_2_INSERT.start();
		}
		if(false) {
			J2_MainUnit.THREAD_7_ENUM_1_INIT.start();
			J2_MainUnit.THREAD_7_ENUM_2_INSERT.start();
		}
		if(false) {
	    	J2_MainUnit.IS_PRINT_EXCEL = true;
			J2_MainUnit.THREAD_7_ENUM_1_INIT.start();
			J2_MainUnit.THREAD_7_ENUM_4_INIT.start();
			J2_MainUnit.THREAD_5_DB_2_TABLES_1_INIT.start();
			J2_MainUnit.THREAD_7_ENUM_PRINT.start();
		}
		if(false) {
			THREAD_5_DB_2_TABLES_1_INIT.start();
			THREAD_5_DB_2_TABLES_2_INSERT.start();
			THREAD_5_DB_3_NSQL_1_INIT.start();
			THREAD_5_DB_3_NSQL_2_INSERT.start();
		}
	}

}
