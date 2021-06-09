package p50_project_v1_3_8;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

import p50_project_v1.J1_BeanBatch;
import p50_project_v1.J1_BeanCall;
import p50_project_v1.J1_BeanCallRelate;
import p50_project_v1.J1_BeanDb;
import p50_project_v1.J1_BeanMenu;
import p50_project_v1.J1_BeanTran;
import p50_project_v1.J1_BeanTranRelate;

/* 1.1 vue->json
 * 1.2 add config,
 */
public class J2_MainUnit {
	public static int LOG_LEVEL=5;
	//1 ERROR
	//3 SYSTEM
	static boolean LS = LOG_LEVEL>2;
    static String PROJECT_ID = 
//    		"26"	//26 ex
    		"27"	//27 gs
//    		"06"	//06 local
    		;  
	public static int M = 0000;
	public static boolean isRealTime = false;
	public static String CP = ((Map)J2_Config.CONFIG.get(J2_MainUnit.PROJECT_ID)).get(J2_Config.ICORE_CODE_PATH).toString();
	static String TRAN_JSON_PATH = ((Map)J2_Config.CONFIG.get(J2_MainUnit.PROJECT_ID)).get(J2_Config.TRAN_JSON_PATH).toString();
	static String JSON_PATH = ((Map)J2_Config.CONFIG.get(PROJECT_ID)).get(J2_Config.JSON_PATH).toString();
	static String VUE_JSON_PATH = ((Map)J2_Config.CONFIG.get(PROJECT_ID)).get(J2_Config.VUE_JSON_PATH).toString();
	static String MENU_JSON_PATH = ((Map)J2_Config.CONFIG.get(PROJECT_ID)).get(J2_Config.MENU_JSON_PATH).toString();
	static String ONL_JSON_PATH = ((Map)J2_Config.CONFIG.get(PROJECT_ID)).get(J2_Config.ONL_JSON_PATH).toString();
	static String BAT_JSON_PATH = ((Map)J2_Config.CONFIG.get(PROJECT_ID)).get(J2_Config.BAT_JSON_PATH).toString();
	
	public static String STR_READY = "准备过程";
	public static String STR_PRINT = "打印过程";

	public static boolean needReady = true;//是否需初始化前置数据
	/**
	 * 9	tree_menu初值、vue更新、交易匹配完毕
	 * 1,2	tree_tran,tree_tran_relate初始化完毕
	 * 3	tree_call初始化完毕
	 * 5	tree_db_bean（table，nsql）初始化完毕
	 * 4	tree_call_relate初始化完毕
	 */
	public static boolean isReadyPrint = true;	
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
	public static String STR_9_MENU_9_PRINT_2_CACHE = "9_MENU_9_PRINT_2_CACHE-遍历menu输出最终json";
	public static boolean DO_9_MENU_9_PRINT_2_CACHE = false;//vueSb初始化
	public static String STR_4_RELATE_5_PRINT_2_ONL = "4_RELATE_5_PRINT_2_ONL-遍历relate输出onl-json";
	public static boolean DO_4_RELATE_5_PRINT_2_ONL = false;//onlSb初始化
	public static String STR_4_RELATE_5_PRINT_2_BAT = "4_RELATE_5_PRINT_2_BAT-遍历relate输出bat-json";
	public static boolean DO_4_RELATE_5_PRINT_2_BAT = false;//batSb初始化
	public static boolean IS_PRINT_ALL = false;
	public static StringBuilder menuSb = new StringBuilder();
	public static StringBuilder onlSb = new StringBuilder();
	public static StringBuilder batSb = new StringBuilder();
	public static String STR_4_RELATE_5_PRINT = "4_RELATE_5_PRINT-输出核心json";
	
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
	        	J5_Sql.doMain(229, tranJson);//更新交易匹配
	    		J3_Util.logE(STR_9_MENU_1_UPDATE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
    };
	public static Thread THREAD_9_MENU_1_MATCH = new Thread(){
        public void run(){
			J3_Util.logB(STR_READY);
    		J3_Util.logB(STR_9_MENU_1_MATCH);
        	J5_Sql.doMain(348, MAP_9_MENU_1_MATCH, 3);//获得交易匹配-icore
        	J5_Sql.doMain(348, MAP_9_MENU_1_MATCH);//获得交易匹配-ct
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
			J3_Util.logB(STR_PRINT);
    		J3_Util.logB(STR_9_MENU_3_TREE);
    		if(needReady) {
	        	while(!DO_9_MENU_1_UPDATE) {
	        		try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	        	}
    		}
			TREE_9_MENU_3_TREE = (J1_BeanMenu)J5_Sql.doMain(339, J5_Sql.ROOT_TREE);//339,select if,tree_menu
    		J3_Util.logE(STR_9_MENU_3_TREE);
    		DO_9_MENU_3_TREE = true;
        }
    };
	public static Thread THREAD_9_MENU_9_PRINT_2_CACHE = new Thread(){
        public void run(){
    		J3_Util.logB(STR_9_MENU_9_PRINT_2_CACHE);
        	while(!DO_9_MENU_3_TREE) {
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
        	boolean start = false;
    		while(!start) {
        		try {
					Thread.sleep(1000);
					System.out.println(STR_9_MENU_9_PRINT_2_CACHE+":"+DO_1_TRAN_3_CACHE+J3_Util.COMMA+DO_4_RELATE_3_CACHE
							+J3_Util.COMMA+DO_5_DB_1_INIT+J3_Util.COMMA+DO_3_CALL_3_CACHE);
					start = DO_1_TRAN_3_CACHE&&DO_4_RELATE_3_CACHE&&DO_5_DB_1_INIT&&DO_3_CALL_3_CACHE;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
    		try {
    			System.out.println("输出menu内容，初始化程序，开始...");
    			menuSb = J3_Util.getBranchSb(TREE_9_MENU_3_TREE,null);
				if(!IS_PRINT_ALL)J3_Util.printToJson(menuSb, MENU_JSON_PATH);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
    		DO_9_MENU_9_PRINT_2_CACHE = true;
    		J3_Util.logE(STR_9_MENU_9_PRINT_2_CACHE);
        }
    };
	public static Thread THREAD_4_RELATE_5_PRINT_2_ONL = new Thread(){
        public void run(){
    		J3_Util.logB(STR_4_RELATE_5_PRINT_2_ONL);
        	boolean start = false;
    		while(!start) {
        		try {
					Thread.sleep(1000);
					System.out.println(STR_4_RELATE_5_PRINT_2_ONL+":"+DO_1_TRAN_3_CACHE+J3_Util.COMMA+DO_4_RELATE_3_CACHE
							+J3_Util.COMMA+DO_5_DB_1_INIT+J3_Util.COMMA+DO_1_TRAN_5_INIT+J3_Util.COMMA+DO_3_CALL_3_CACHE);
					start = DO_1_TRAN_3_CACHE&&DO_4_RELATE_3_CACHE&&DO_5_DB_1_INIT&&DO_1_TRAN_5_INIT&&DO_3_CALL_3_CACHE;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
    		try {
    			System.out.println("输出onl内容，初始化程序，开始...");
    			onlSb = J3_Util.getOnlSb();
				if(!IS_PRINT_ALL)J3_Util.printToJson(onlSb, ONL_JSON_PATH);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
    		DO_4_RELATE_5_PRINT_2_ONL = true;
    		J3_Util.logE(STR_4_RELATE_5_PRINT_2_ONL);
        }
    };
	public static Thread THREAD_4_RELATE_5_PRINT_2_BAT = new Thread(){
        public void run(){
    		J3_Util.logB(STR_4_RELATE_5_PRINT_2_BAT);
        	boolean start = false;
    		while(!start) {
        		try {
					Thread.sleep(1000);
					System.out.println(STR_4_RELATE_5_PRINT_2_BAT+":"+DO_6_BAT_4_INIT+J3_Util.COMMA
							+DO_5_DB_1_INIT+J3_Util.COMMA+DO_4_RELATE_3_CACHE+J3_Util.COMMA+DO_3_CALL_3_CACHE);
					start = DO_6_BAT_4_INIT&&DO_5_DB_1_INIT&&DO_4_RELATE_3_CACHE&&DO_3_CALL_3_CACHE;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
    		try {
    			System.out.println("输出bat内容，初始化程序，开始...");
    			batSb = J3_Util.getBatSb();
				if(!IS_PRINT_ALL)J3_Util.printToJson(batSb, BAT_JSON_PATH);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
    		DO_4_RELATE_5_PRINT_2_BAT = true;
    		J3_Util.logE(STR_4_RELATE_5_PRINT_2_BAT);
        }
    };

	public static Thread THREAD_4_RELATE_5_PRINT = new Thread(){
        public void run(){
    		J3_Util.logB(STR_4_RELATE_5_PRINT);
        	boolean start = false;
    		while(!start) {
        		try {
					Thread.sleep(1000);
					System.out.println(STR_4_RELATE_5_PRINT+":"+DO_9_MENU_9_PRINT_2_CACHE
							+J3_Util.COMMA+DO_4_RELATE_5_PRINT_2_ONL+J3_Util.COMMA+DO_4_RELATE_5_PRINT_2_BAT
							+J3_Util.COMMA+DO_3_CALL_3_CACHE);
					start = DO_9_MENU_9_PRINT_2_CACHE&&DO_4_RELATE_5_PRINT_2_ONL&&DO_4_RELATE_5_PRINT_2_BAT&&DO_3_CALL_3_CACHE;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
    		try {
    			J3_Util.printToJson(menuSb.append("\n").append(onlSb).append("\n").append(batSb) , JSON_PATH);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
    		J3_Util.logE(STR_4_RELATE_5_PRINT);
			J3_Util.logE(STR_PRINT);
        }
    };
    public static List<J1_BeanTran> tranList = new ArrayList<J1_BeanTran>();
	public static Map<String,String> tranMap = new HashMap<String,String>();
    public static List<J1_BeanTranRelate> tranRelateList = new ArrayList<J1_BeanTranRelate>();
    public static Set<String> tranRelateKeySet = new HashSet<String>();
    public static Map<String,Integer> tranRelateKeyMap =  new HashMap<String,Integer>();
	public static String STR_1_TRAN_1_INIT = "1_TRAN_1_INIT-交易初始化-扫描flowtrans"; 
	public static boolean DO_1_TRAN_1_INIT = false;//交易调用-初始化
	public static String STR_1_TRAN_2_INSERT = "1_TRAN_INSERT-交易入库";
	public static boolean DO_1_TRAN_2_INSERT = false;//交易调用-入库 
	public static String STR_1_TRAN_3_CACHE = "1_TRAN_3_CACHE-交易调用-内存化";
	public static boolean DO_1_TRAN_3_CACHE = false;//交易调用-内存化
	public static String STR_1_TRAN_4_INIT = "1_TRAN_4_INIT-交易调用-初始化-查询数据库"; 
	public static boolean DO_1_TRAN_5_INIT = false;//交易内存化
	public static String STR_1_TRAN_5_INIT = "1_TRAN_5_INIT-交易初始化-查询数据库"; 
	public static Set<String> callKeySet = new HashSet<String>();
	public static Set<J1_BeanCall> callSet = new HashSet<J1_BeanCall>();
	public static String STR_3_CALL_1_INIT = "3_CALL_1_INIT-方法首次初始化";
	public static boolean DO_3_CALL_1_INIT = false;//方法首次初始化
	public static String STR_3_CALL_2_INSERT = "3_CALL_2_INSERT-方法首次初始化入库";
	public static boolean DO_3_CALL_2_INSERT = false;//方法首次初始化入库 
	public static String STR_3_CALL_3_CACHE = "3_CALL_3_CACHE-方法内存化";
	public static boolean DO_3_CALL_3_CACHE = false;//方法内存化
	public static Map<String,String> callMap = new HashMap<String,String>();
	public static Set<String> dbKeySet = new HashSet<String>();
	public static Set<J1_BeanDb> dbSet = new HashSet<J1_BeanDb>();
	public static Set<J1_BeanDb> dbTablesSet = new HashSet<J1_BeanDb>();
	public static String STR_5_DB_4_INIT = "5_DB_4_INIT-db初始化";
	public static boolean DO_5_DB_1_INIT = false;//db初始化
	public static String STR_5_DB_2_TABLES_1_INIT = "5_DB_2_TABLES_1_INIT-表初始化";
	public static boolean DO_5_DB_2_TABLES_1_INIT = false;//表初始化
	public static String STR_5_DB_2_TABLES_2_INSERT = "5_DB_2_TABLES_2_INSERT-表初始化入库";
	public static boolean DO_5_DB_2_TABLES_2_INSERT = false;//表初始化入库
	public static Set<J1_BeanDb> dbNsqlSet = new HashSet<J1_BeanDb>();
	public static String STR_5_DB_3_NSQL_1_INIT = "5_DB_3_NSQL_1_INIT-命名SQL初始化-遍历nsql文件";
	public static boolean DO_5_DB_3_NSQL_1_INIT = false;//命名SQL初始化
	public static String STR_5_DB_3_NSQL_2_INSERT = "5_DB_3_NSQL_2_INSERT-命名SQL初始化入库";
	public static boolean DO_5_DB_3_NSQL_2_INSERT = false;//命名SQL初始化入库
	public static String STR_5_DB_3_NSQL_3_CACHE = "5_DB_3_NSQL_3_CACHE-命名SQL初始化-内存化";
	public static boolean DO_5_DB_3_NSQL_3_CACHE = false;//命名SQL内存化 
	public static String STR_5_DB_3_NSQL_4_INIT = "5_DB_3_NSQL_4_INIT-命名SQL初始化-查询数据库";
	public static Map<String,J1_BeanDb> dbBeanMap = new HashMap<String,J1_BeanDb>();
	public static Map<String,J1_BeanDb> dbNsqlMap = new HashMap<String,J1_BeanDb>();
	public static Map<String,String> dbMap = new HashMap<String,String>();
	public static List<J1_BeanCallRelate> callRelateList = new ArrayList<J1_BeanCallRelate>();
    public static Set<String> callRelateKeySet = new HashSet<String>();
    public static Map<String,Integer> callRelateKeyMap =  new HashMap<String,Integer>();
	public static String STR_4_RELATE_1_INIT = "4_RELATE_1_INIT-调用关系初始化-遍历java";
	public static boolean DO_4_RELATE_1_INIT = false;//调用关系初始化
	public static String STR_4_RELATE_4_INIT = "4_RELATE_4_INIT-调用关系初始化-查询数据库";
	public static String STR_4_RELATE_2_INSERT = "4_RELATE_2_INSERT-调用关系入库";
	public static boolean DO_4_RELATE_2_INSERT = false;//调用关系入库
	public static String STR_4_RELATE_3_CACHE = "4_RELATE_3_CACHE-调用关系初始化-内存化";
	public static boolean DO_4_RELATE_3_CACHE = false;//调用关系初始化 
	public static String STR_6_BAT_1_INIT = "6_BAT_1_INIT-批量初始化-遍历文件";
	public static boolean DO_6_BAT_1_INIT = false;//批量初始化-遍历文件
	public static String STR_6_BAT_2_INSERT = "6_BAT_2_INSERT-批量初始化-入库";
	public static boolean DO_6_BAT_2_INSERT = false;//批量入库  
	public static boolean DO_6_BAT_4_INIT = false;//批量初始化-查询数据库 
	public static String STR_6_BAT_4_INIT = "6_BAT_4_INIT-批量初始化-查询数据库";
	
	
	public static Set<J1_BeanBatch> batchSet = new HashSet<J1_BeanBatch>();
	public static Map<String,String> batchMap = new HashMap<String,String>();
	
	public static Thread THREAD_1_TRAN_1_INIT = new Thread(){
        public void run(){
    		J3_Util.logB(STR_1_TRAN_1_INIT);
			J71_Tran_Util.scanTran(CP,tranList,tranRelateList);//扫描代码根目录，获得所有flowtrans
    		J3_Util.logE(STR_1_TRAN_1_INIT);
    		DO_1_TRAN_1_INIT = true;
        }
    };
	public static Thread THREAD_1_TRAN_4_INIT = new Thread(){
        public void run(){
    		J3_Util.logB(STR_1_TRAN_4_INIT);
    		if(needReady) {
            	while(!DO_1_TRAN_2_INSERT) {
            		try {
    					Thread.sleep(1000);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
            	}
    		}
    		J5_Sql.doMain(342, tranRelateList);//342,select all,tree_tran_relate
    		J3_Util.logE(STR_1_TRAN_4_INIT);
    		DO_1_TRAN_1_INIT = true;
        }
    };
	public static Thread THREAD_1_TRAN_5_INIT = new Thread(){
        public void run(){
    		J3_Util.logB(STR_1_TRAN_5_INIT);
    		if(needReady) {
            	while(!DO_1_TRAN_2_INSERT) {
            		try {
    					Thread.sleep(1000);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
            	}
    		}
    		J5_Sql.doMain(341, tranMap);//341,select all,tree_tran
    		J3_Util.logE(STR_1_TRAN_5_INIT);
    		DO_1_TRAN_5_INIT = true;
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
	public static Thread THREAD_1_TRAN_3_CACHE = new Thread(){
        public void run(){
        	while(!DO_1_TRAN_1_INIT) {
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
    		J3_Util.logB(STR_1_TRAN_3_CACHE);
    		for(int i=0;i<tranRelateList.size();i++) {
    			J1_BeanTranRelate bean =  tranRelateList.get(i);
    			if(!tranRelateKeySet.contains(bean.getTran_name())) {
    				tranRelateKeySet.add(bean.getTran_name());
    				tranRelateKeyMap.put(bean.getTran_name(), i);
    			}
    		}
    		J3_Util.logE(STR_1_TRAN_3_CACHE);
    		DO_1_TRAN_3_CACHE = true;
        }
    };
	public static Thread THREAD_3_CALL_1_INIT = new Thread(){
        public void run(){
    		J3_Util.logB(STR_3_CALL_1_INIT);
	    	J72_Tran_Util.scanJavaFolder(CP, callKeySet,null,callSet, null);//扫描代码根目录，获得所有java,method并入库
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
	public static Thread THREAD_3_CALL_3_CACHE = new Thread(){
        public void run(){
    		J3_Util.logB(STR_3_CALL_3_CACHE);
    		if(needReady) {
            	while(!DO_3_CALL_2_INSERT) {
            		try {
    					Thread.sleep(1000);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
            	}
    		}
	    	if(!isRealTime) {
				J5_Sql.doMain(3432, callMap);//3432,select all,tree_call,map
        	}
    		J3_Util.logE(STR_3_CALL_3_CACHE);
    		DO_3_CALL_3_CACHE = true;
        }
    };
	public static Thread THREAD_5_DB_2_TABLES_1_INIT = new Thread(){
        public void run(){
    		J3_Util.logB(STR_5_DB_2_TABLES_1_INIT);
			J73_Tran_Util.scanFolder(CP, 2, dbKeySet, dbTablesSet);// tables
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
			J73_Tran_Util.scanFolder(CP, 3, dbKeySet, dbNsqlSet);// 3,nsql
			dbSet.addAll(dbNsqlSet);
    		J3_Util.logE(STR_5_DB_3_NSQL_1_INIT);
    		DO_5_DB_3_NSQL_1_INIT = true;
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
	public static Thread THREAD_5_DB_3_NSQL_3_CACHE = new Thread(){
        public void run(){
        	while(!DO_5_DB_3_NSQL_1_INIT) {
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
    		J3_Util.logB(STR_5_DB_3_NSQL_3_CACHE);
	    	if(!isRealTime) {
        	}
    		J3_Util.logE(STR_5_DB_3_NSQL_3_CACHE);
    		DO_5_DB_3_NSQL_3_CACHE = true;
        }
    };
    public static Thread THREAD_5_DB_3_NSQL_4_INIT = new Thread(){
        public void run(){
    		J3_Util.logB(STR_5_DB_3_NSQL_4_INIT);
    		if(needReady) {
            	while(!DO_5_DB_3_NSQL_2_INSERT) {
            		try {
    					Thread.sleep(1000);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
            	}
    		}
	    	if(!isRealTime) {
				J5_Sql.doMain(3453, dbNsqlMap);//3453,select all,tree_db_bean,nsql
        	}
    		J3_Util.logE(STR_5_DB_3_NSQL_4_INIT);
    		DO_5_DB_3_NSQL_1_INIT = true;
        }
    };
    public static Thread THREAD_5_DB_4_INIT = new Thread(){
        public void run(){
    		J3_Util.logB(STR_5_DB_4_INIT);
    		if(needReady) {
    			boolean start = false;
            	while(!start) {
            		try {
    					Thread.sleep(1000);
    					start = DO_5_DB_2_TABLES_2_INSERT&&DO_5_DB_3_NSQL_2_INSERT;
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
            	}
    		}
	    	if(!isRealTime) {
				J5_Sql.doMain(3450, dbBeanMap);//3450,select all,tree_db_bean
        	}
    		J3_Util.logE(STR_5_DB_4_INIT);
    		DO_5_DB_1_INIT = true;
        }
    };
	public static Thread THREAD_6_BAT_1_INIT = new Thread(){
        public void run(){
    		J3_Util.logB(STR_6_BAT_1_INIT);
			J71_Tran_Util.scanBatch(CP,batchSet);//扫描代码根目录，获得所有batch_tran.xml,并入库
    		J3_Util.logE(STR_6_BAT_1_INIT);
    		DO_6_BAT_1_INIT = true;
        }
    };
	public static Thread THREAD_6_BAT_2_INSERT = new Thread(){
        public void run(){
        	while(!DO_6_BAT_1_INIT) {
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
    		J3_Util.logB(STR_6_BAT_2_INSERT);
	    	if(!isRealTime) {
				J5_Sql.doMain(446, null);//446,del all,tree_batch
		    	J5_Sql.doMain(136,batchSet);//136,batch insert,tree_batch
        	}
    		J3_Util.logE(STR_6_BAT_2_INSERT);
    		DO_6_BAT_2_INSERT = true;
        }
    };
    public static Thread THREAD_6_BAT_4_INIT = new Thread(){
        public void run(){
    		J3_Util.logB(STR_6_BAT_4_INIT);
    		if(needReady) {
            	while(!DO_6_BAT_2_INSERT) {
            		try {
    					Thread.sleep(1000);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
            	}
    		}
	    	if(!isRealTime) {
				J5_Sql.doMain(346, batchMap);//346,select all,tree_batch
        	}
    		J3_Util.logE(STR_6_BAT_4_INIT);
    		DO_6_BAT_4_INIT = true;
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
			J72_Tran_Util.scanJavaFolder(CP, callKeySet,dbMap,null,callRelateList);//遍历构建调用关系
    		J3_Util.logE(STR_4_RELATE_1_INIT);
    		DO_4_RELATE_1_INIT = true;
        }
    };
	public static Thread THREAD_4_RELATE_4_INIT = new Thread(){
        public void run(){
    		J3_Util.logB(STR_4_RELATE_4_INIT);
    		if(needReady) {
            	while(!DO_4_RELATE_2_INSERT) {
            		try {
    					Thread.sleep(1000);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
            	}
    		}
	    	if(!isRealTime) {
				J5_Sql.doMain(344, callRelateList);//344,select all,tree_call_db
        	}
    		J3_Util.logE(STR_4_RELATE_4_INIT);
    		DO_4_RELATE_1_INIT = true;
        }
    };
	public static Thread THREAD_4_RELATE_3_CACHE = new Thread(){
        public void run(){
        	while(!DO_4_RELATE_1_INIT) {
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
    		J3_Util.logB(STR_4_RELATE_3_CACHE);
	    	if(!isRealTime) {
	    		for(int i=0;i<callRelateList.size();i++) {
	    			J1_BeanCallRelate bean =  callRelateList.get(i);
	    			if(!callRelateKeySet.contains(bean.getCall_name_parent())) {
	    				callRelateKeySet.add(bean.getCall_name_parent());
	    				callRelateKeyMap.put(bean.getCall_name_parent(), i);
	    			}
	    		}
        	}
    		J3_Util.logE(STR_4_RELATE_3_CACHE);
    		DO_4_RELATE_3_CACHE = true;
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
    		J3_Util.logE(STR_READY,2);
        }
    };
    //准备工作
    public static void ready() {
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
		
		THREAD_6_BAT_1_INIT.start();
		THREAD_6_BAT_2_INSERT.start();
    }
    //所有准备工作完成，查询数据，内存级别数据准备
    public static void printReady() {
		THREAD_9_MENU_3_TREE.start();
		THREAD_1_TRAN_4_INIT.start();
		THREAD_1_TRAN_3_CACHE.start();
		THREAD_4_RELATE_4_INIT.start();
		THREAD_4_RELATE_3_CACHE.start();
//		THREAD_5_DB_3_NSQL_4_INIT.start();
//		THREAD_5_DB_3_NSQL_3_CACHE.start();
		THREAD_5_DB_4_INIT.start();
		THREAD_3_CALL_3_CACHE.start();
    }
    //json-menu
    public static void printMenu() {
		THREAD_9_MENU_9_PRINT_2_CACHE.start();
    }
    //json-onl
    public static void printOnl() {
    	THREAD_1_TRAN_5_INIT.start();
		THREAD_4_RELATE_5_PRINT_2_ONL.start();
    }
    //json-bat
    public static void printBat() {
    	THREAD_6_BAT_4_INIT.start();
		THREAD_4_RELATE_5_PRINT_2_BAT.start();
    }
	/**
	 * 9	tree_menu初值、vue更新、交易匹配完毕
	 * 1,2	tree_tran,tree_tran_relate初始化完毕
	 * 3	tree_call初始化完毕
	 * 5	tree_db_bean（table，nsql）初始化完毕
	 * 4	tree_call_relate初始化完毕
	 */
    public static void main(String[] args) {
    	
    	IS_PRINT_ALL = false;
    	IS_PRINT_ALL = true;
    	
    	int m = 1;
    	needReady = (m==1||m==3?true:false);;//是否需初始化前置数据
    	isReadyPrint = (m==2||m==3?true:false);;
    	
		if(needReady) {
			ready();//准备过程..耗时:53s126ms
		}
		if(isReadyPrint) {
	    	printReady();
			printMenu();//打印过程..耗时:2s677ms
			printOnl();
	    	printBat();
		}
		if(isReadyPrint&&IS_PRINT_ALL) {
			THREAD_4_RELATE_5_PRINT.start();
		}
	}
}