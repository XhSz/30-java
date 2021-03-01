package p50_project_v1_3_5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * 		scan table
 * 1.3.5 J72
 * 		scan java, 识别表
 */
public class J72_Tran_Main {
	public static boolean isRealTime = false;
	public static int M = 66;
	public static String CP = ((Map)J2_Config.CONFIG.get(J2_Main.PROJECT_ID)).get(J2_Config.ICORE_CODE_PATH).toString();
	public static void main(String[] args) {
		Set<String> callKeySet = new HashSet<String>();
		Map<String,String> dbMap = new HashMap();
		Set<J1_BeanCall> callSet = new HashSet<J1_BeanCall>();
		boolean isCallSet = (M==33||M==63||M==64||M==67);
		if(isCallSet) {
			//第一遍初始化call
			J5_Sql.doMain(443, null);//443,del all,tree_call
	        long t1=System.currentTimeMillis();
	        if(J2_Main.LS)System.out.println("scanJava...begin..."+t1);
			//扫描代码根目录，获得所有java,method并入库
	        J2_Main.LS = false;
			J72_Tran_Util.scanJavaFolder(CP, callKeySet,dbMap,callSet, null);
	        J2_Main.LS = true;
	        long t2=System.currentTimeMillis();
	        if(J2_Main.LS)System.out.println("scanJava...end...耗时:"+J3_Util.longToTime(t2-t1));
	        if(J2_Main.LS)System.out.println("insertCall...begin..."+t2);
        	if(!J72_Tran_Main.isRealTime) {
    			J5_Sql.doMain(133, callSet);//133,batch insert,tree_call
        	}
	        long t3=System.currentTimeMillis();
	        if(J2_Main.LS)System.out.println("insertCall...end...耗时:"+J3_Util.longToTime(t3-t2));
		}
		if(M==35||M==65||M==66||M==67) {
			J5_Sql.doMain(345, dbMap);//345,select all,tree_call_db
		}
		if(M==34||M==64||M==66||M==67) {
			if(!isCallSet) {
				J5_Sql.doMain(343, callKeySet);//343,select all,call_name
			}
			//遍历构建调用关系
			J5_Sql.doMain(444, null);//444,del all,tree_call_relate
			//初始化call-relate
			List<J1_BeanCallRelate> callRelateList = new ArrayList<J1_BeanCallRelate>();
	        long t1=System.currentTimeMillis();
	        if(J2_Main.LS)System.out.println("initCallRelate...begin..."+t1);
			J72_Tran_Util.scanJavaFolder(CP, callKeySet,dbMap,null,callRelateList);
	        long t2=System.currentTimeMillis();
	        if(J2_Main.LS)System.out.println("initCallRelate...end...耗时:"+J3_Util.longToTime(t2-t1));
        	if(!J72_Tran_Main.isRealTime) {
    	        if(J2_Main.LS)System.out.println("insertCallRelate...begin..."+t2);
    			J5_Sql.doMain(134, callRelateList);//134,batch insert,tree_call_relate
    	        long t3=System.currentTimeMillis();
    	        if(J2_Main.LS)System.out.println("insertCall...end...耗时:"+J3_Util.longToTime(t3-t2));
        	}
		}
        
	}
}
