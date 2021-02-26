package p50_project_v1_3_4;

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
 * 		改成每行读取
 */
public class J72_Tran_Main {
	public static boolean isRealTime = false;
	public static int M = 1;
	public static String CP = ((Map)J2_Config.CONFIG.get(J2_Main.PROJECT_ID)).get(J2_Config.ICORE_CODE_PATH).toString();
	public static void main(String[] args) {
		if(M==1||M==3) {
			//第一遍初始化call
			Set<String> callKeySet = new HashSet<String>();
			Set<J1_BeanCall> callSet = new HashSet<J1_BeanCall>();
			//清库
			J5_Sql.doMain(443, null);
	        long t1=System.currentTimeMillis();
	        if(J2_Main.LS)System.out.println("scanJava...begin..."+t1);
			//扫描代码根目录，获得所有java,method并入库
	        J2_Main.LS = false;
			J72_Tran_Util.scanJavaFolder(CP, callKeySet, null, callSet);
	        J2_Main.LS = true;
	        long t2=System.currentTimeMillis();
	        if(J2_Main.LS)System.out.println("scanJava...end...耗时:"+(t2-t1));
	        if(J2_Main.LS)System.out.println("scanJava...end...耗时:"+J3_Util.longToTime(t2-t1));
	        if(J2_Main.LS)System.out.println("insertCall...begin..."+t2);
        	if(!J72_Tran_Main.isRealTime) {
    			J5_Sql.doMain(133, callSet);
        	}
	        long t3=System.currentTimeMillis();
	        if(J2_Main.LS)System.out.println("insertCall...end...耗时:"+(t3-t2));
	        if(J2_Main.LS)System.out.println("insertCall...end...耗时:"+J3_Util.longToTime(t3-t2));
		}
		if(M==2||M==3) {
			//第二遍初始化call-relate
			Set<String> callKeySet = new HashSet<String>();
			List<J1_BeanCallRelate> callRelateList = new ArrayList<J1_BeanCallRelate>();
			//初始化set
	        long t1=System.currentTimeMillis();
	        if(J2_Main.LS)System.out.println("initCallRelate...begin..."+t1);
			J5_Sql.doMain(343, callKeySet);
			//遍历构建调用关系
			//扫描代码根目录，获得所有java,method并入库
	        J2_Main.LS = false;
			//清库
			J5_Sql.doMain(444, null);
			J72_Tran_Util.scanJavaFolder(CP, callKeySet, callRelateList,null);
	        J2_Main.LS = true;
	        long t2=System.currentTimeMillis();
	        if(J2_Main.LS)System.out.println("initCallRelate...end...耗时:"+(t2-t1));
	        if(J2_Main.LS)System.out.println("initCallRelate...end...耗时:"+J3_Util.longToTime(t2-t1));
			
		}
        
	}
}
