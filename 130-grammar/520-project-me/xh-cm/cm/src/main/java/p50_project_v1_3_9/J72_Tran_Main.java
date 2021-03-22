package p50_project_v1_3_9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import p50_project_v1.J1_BeanCall;
import p50_project_v1.J1_BeanCallRelate;
import p50_project_v1.J1_BeanDb;

/* 1.1 vue->json
 * 1.2 add config,
 * 1.3.1 J71 
 * 		scan tran
 * 后续计划:
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
 */
public class J72_Tran_Main {
	public static boolean isRealTime = false;
	public static int M = 1353;
	public static String CP = ((Map)J2_Config.CONFIG.get(J2_Main.PROJECT_ID)).get(J2_Config.ICORE_CODE_PATH).toString();
	public static void main(String[] args) {
		Set<String> callKeySet = new HashSet<String>();
		Set<J1_BeanCall> callSet = new HashSet<J1_BeanCall>();
		Set<String> dbKeySet = new HashSet<String>();
		Set<J1_BeanDb> dbSet = new HashSet<J1_BeanDb>();
		Map<String,String> dbMap = new HashMap();
		boolean isCallSet = (M==1330);//是否需要第一遍初始化方法call
		boolean isDbSet = (M==1350||M==1307);//是否需要初始化db
		J3_Util.DE = true;
		//初始化，准备工作
		if(isCallSet) {
			J3_Util.logB();
	    	J72_Tran_Util.scanJavaFolder(CP, callKeySet,dbMap,callSet, null);//扫描代码根目录，获得所有java,method并入库
	        J3_Util.logE("133-init call");
	    	if(!J72_Tran_Main.isRealTime) {
				J5_Sql.doMain(443, null);//443,del all,tree_call
    			J5_Sql.doMain(133, callSet);//133,batch insert,tree_call
        	}
		}
		if(isDbSet||M==1352) {
			Set<J1_BeanDb> dbTablesSet = new HashSet<J1_BeanDb>();
			J73_Tran_Util.scanFolder(CP, 2, dbKeySet, dbTablesSet);// tables
			J3_Util.logE("135-tables-init db");
        	if(!J72_Tran_Main.isRealTime) {
    			J5_Sql.doMain(435, "table_type='T'");//435,del if,tree_db_bean 
    			J5_Sql.doMain(135, dbTablesSet);//135,batch insert,tree_db_bean
        	}
			dbSet.addAll(dbTablesSet);
		}
		if(isDbSet||M==1353) {
			Set<J1_BeanDb> dbNsqlSet = new HashSet<J1_BeanDb>();
			J73_Tran_Util.scanFolder(CP, 3, dbKeySet, dbNsqlSet);// 3,nsql
			J3_Util.logE("135-nsql-init db");
//        	if(!J72_Tran_Main.isRealTime) {
//    			J5_Sql.doMain(435, "table_type='N'");//435,del if,tree_db_bean 
//    			J5_Sql.doMain(135, dbNsqlSet);//135,batch insert,tree_db_bean
//        	}
			dbSet.addAll(dbNsqlSet);
			J3_Util.print(dbNsqlSet);
		}
		if(M==1340||M==1307) {
			if(!isCallSet) {
				J5_Sql.doMain(343, callKeySet);//343,select all,call_name
			}
			if(true) {
				J5_Sql.doMain(345, dbMap);//345,select all,tree_call_db
			}
			List<J1_BeanCallRelate> callRelateList = new ArrayList<J1_BeanCallRelate>();
			J72_Tran_Util.scanJavaFolder(CP, callKeySet,dbMap,null,callRelateList);//遍历构建调用关系
			J3_Util.logE("134-init callRelate");
        	if(!J72_Tran_Main.isRealTime) {
    			J5_Sql.doMain(444, null);//444,del all,tree_call_relate
    			J5_Sql.doMain(134, callRelateList);//134,batch insert,tree_call_relate
        	}
		}
        
	}
}
