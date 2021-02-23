package p50_project_v1_3_2;

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
 * 		scan java
 */
public class J72_Tran_Main {
	public static boolean isRealTime = true;
	public static int M = 1;
	public static String CP = ((Map)J2_Config.CONFIG.get(J2_Main.PROJECT_ID)).get(J2_Config.ICORE_CODE_PATH).toString();
	public static void main(String[] args) {
		if(M==1||M==3) {
			//第一遍初始化call
			Set<String> callSet = new HashSet<String>();
			//清库
			J5_Sql.doMain(443, null);
	        long t1=System.currentTimeMillis();
	        if(J2_Main.LS)System.out.println("scanJava...begin..."+t1);
			//扫描代码根目录，获得所有java,method并入库
	        J2_Main.LS = false;
			J72_Tran_Util.scanJavaFolder(CP, callSet, null);
	        J2_Main.LS = true;
	        long t2=System.currentTimeMillis();
	        if(J2_Main.LS)System.out.println("scanJava...end...耗时:"+(t2-t1));
	        if(J2_Main.LS)System.out.println("scanJava...end...耗时:"+J3_Util.longToTime(t2-t1));
		}
		if(M==2||M==3) {
			//第二遍初始化call-relate
			Set<String> callSet = new HashSet<String>();
			List<J1_BeanCallRelate> callRelateList = new ArrayList<J1_BeanCallRelate>();
			//初始化set
	        long t1=System.currentTimeMillis();
	        if(J2_Main.LS)System.out.println("initCallRelate...begin..."+t1);
			J5_Sql.doMain(343, callSet);
			//遍历构建调用关系
			//扫描代码根目录，获得所有java,method并入库
	        J2_Main.LS = false;
			//清库
			J5_Sql.doMain(444, null);
			J72_Tran_Util.scanJavaFolder(CP, callSet, callRelateList);
	        J2_Main.LS = true;
	        long t2=System.currentTimeMillis();
	        if(J2_Main.LS)System.out.println("initCallRelate...end...耗时:"+(t2-t1));
	        if(J2_Main.LS)System.out.println("initCallRelate...end...耗时:"+J3_Util.longToTime(t2-t1));
			
		}
        
	}
}
