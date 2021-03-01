package p50_project_v1_3_5;

import java.util.ArrayList;
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
 */
public class J73_Tran_Main {
	public static boolean isRealTime = false;
	public static int M = 4;
	public static String CP = ((Map)J2_Config.CONFIG.get(J2_Main.PROJECT_ID)).get(J2_Config.ICORE_CODE_PATH).toString();
	public static void main(String[] args) {
		if(M==4||M==6) {
			Set<String> dbKeySet = new HashSet<String>();
			Set<J1_BeanDb> dbSet = new HashSet<J1_BeanDb>();
	        long t1=System.currentTimeMillis();
	        if(J2_Main.LS)System.out.println("scanTable...begin..."+t1);
			J73_Tran_Util.scanFolder(CP, 2, dbKeySet, dbSet);// 2,tables
	        long t2=System.currentTimeMillis();
	        if(J2_Main.LS)System.out.println("scanTable...end...耗时:"+J3_Util.longToTime(t2-t1));
	        if(J2_Main.LS)System.out.println("insertDb...begin..."+t2);
			//清库
			J5_Sql.doMain(445, null);
        	if(!J72_Tran_Main.isRealTime) {
    			J5_Sql.doMain(135, dbSet);
        	}
	        long t3=System.currentTimeMillis();
	        if(J2_Main.LS)System.out.println("insertDb...end...耗时:"+J3_Util.longToTime(t3-t2));
		}
        
	}
}
