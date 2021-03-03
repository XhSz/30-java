package p50_project_v1_4_3;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import p50_project_v1.J1_BeanCall;
import p50_project_v1.J1_BeanCallRelate;
import p50_project_v1.J1_BeanDb;
import p50_project_v1.J1_BeanMenu;
import p50_project_v1.J1_BeanTran;
import p50_project_v1.J1_BeanTranRelate;

/* 1.1 vue->json
 * 1.2 add config,
 */
public class J2_MainDo {
	public static int LOG_LEVEL=5;
	//1 ERROR
	//3 SYSTEM
	static boolean LS = LOG_LEVEL>2;
    static String PROJECT_ID = 
//    		"26"	//26 ex
    		"27"	//27 gs
//    		"06"	//06 local
    		;  
	public static boolean isRealTime = false;
	public static String CP = ((Map)J2_Config.CONFIG.get(J2_MainDo.PROJECT_ID)).get(J2_Config.ICORE_CODE_PATH).toString();

	public static int M = 0000;
	
    public static void main(String[] args) {
    	
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
		J3_Util.logB();
		
		//初始化tran,tranRealte
		if(isTran) {
			J71_Tran_Util.scanTran(CP,tranList,tranRelateList);//扫描代码根目录，获得所有flowtrans,并入库
			J3_Util.logE("131-init tran、tranRelate");
	    	if(!isRealTime) {
				J5_Sql.doMain(441, null);//441,del all,tree_tran
				J5_Sql.doMain(442, null);//442,del all,tree_tran_relate
		    	J5_Sql.doMain(131,tranList);//131,batch insert,tree_call
		    	J5_Sql.doMain(132,tranRelateList);//132,batch insert,tree_tran_relate
        	}
		}
		//初始化callMethod
		if(isCallSet) {
			J3_Util.logB();
	    	J72_Tran_Util.scanJavaFolder(CP, callKeySet,dbMap,callSet, null);//扫描代码根目录，获得所有java,method并入库
	        J3_Util.logE("133-init call");
	    	if(!isRealTime) {
				J5_Sql.doMain(443, null);//443,del all,tree_call
    			J5_Sql.doMain(133, callSet);//133,batch insert,tree_call
        	}
		}
		//初始化dbBean-tables
		if(isDbSet) {
			Set<J1_BeanDb> dbTablesSet = new HashSet<J1_BeanDb>();
			J73_Tran_Util.scanFolder(CP, 2, dbKeySet, dbTablesSet);// tables
			J3_Util.logE("135-tables-init db");
        	if(!isRealTime) {
    			J5_Sql.doMain(435, "table_type='T'");//435,del if,tree_db_bean 
    			J5_Sql.doMain(135, dbTablesSet);//135,batch insert,tree_db_bean
        	}
			dbSet.addAll(dbTablesSet);
		}
		//初始化dbBean-nsql
		if(isDbSet) {
			Set<J1_BeanDb> dbNsqlSet = new HashSet<J1_BeanDb>();
			J73_Tran_Util.scanFolder(CP, 3, dbKeySet, dbNsqlSet);// 3,nsql
			J3_Util.logE("135-nsql-init db");
        	if(!isRealTime) {
    			J5_Sql.doMain(435, "table_type='N'");//435,del if,tree_db_bean 
    			J5_Sql.doMain(135, dbNsqlSet);//135,batch insert,tree_db_bean
        	}
			dbSet.addAll(dbNsqlSet);
		}
		//扫描java
		if(isCallRelate) {
			if(!isCallSet) {
				J5_Sql.doMain(343, callKeySet);//343,select all,call_name
			}
			if(true) {
				J5_Sql.doMain(345, dbMap);//345,select all,tree_call_db
			}
			List<J1_BeanCallRelate> callRelateList = new ArrayList<J1_BeanCallRelate>();
			J72_Tran_Util.scanJavaFolder(CP, callKeySet,dbMap,null,callRelateList);//遍历构建调用关系
			J3_Util.logE("134-init callRelate");
        	if(!isRealTime) {
    			J5_Sql.doMain(444, null);//444,del all,tree_call_relate
    			J5_Sql.doMain(134, callRelateList);//134,batch insert,tree_call_relate
        	}
		}
		//初始化menu
		if(isMenu) {
			J1_BeanMenu rootTree = (J1_BeanMenu)J5_Sql.doMain(339, J5_Sql.ROOT_TREE);//339,select if,tree_menu
			J3_Util.logE("1391-init menu");
//			J3_Util.printDebug(rootTree);
		}
        
	}
}
