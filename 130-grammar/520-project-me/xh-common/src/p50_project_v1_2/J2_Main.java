package p50_project_v1_2;

import java.io.FileNotFoundException;
import java.util.Map;

/* 1.1 vue->json
 * 1.2 add config,
 */
public class J2_Main {
	public static int LOG_LEVEL=1;
	//1 ERROR
	//3 SYSTEM
	static boolean LS = LOG_LEVEL>2;
    static String PROJECT_ID = 
    		"26"	//26 ex
//    		"27"	//27 gs
    		;  
	public static void main(String[] args) {
        long t1=System.currentTimeMillis();
        if(LS)System.out.println("1. callSelf...begin..."+t1);
		J1_BeanMenu rootTree = J5_Sql.callSelf();
//		J3_Util.printDebug(rootTree);
//		if(true)return;
        long t2=System.currentTimeMillis();
        if(LS)System.out.println("1. callSelf...end...耗时:"+(t2-t1));
        if(LS)System.out.println("1. callSelf...end...耗时:"+J3_Util.longToTime(t2-t1));
		try {
	        if(LS)System.out.println("2. pringToJson...begin..."+t2);
			J3_Util.pringToJson(J3_Util.getBranchSb(rootTree), ((Map)J2_Config.CONFIG.get(PROJECT_ID)).get(J2_Config.JSON_PATH).toString());
	        long t3=System.currentTimeMillis();
	        if(LS)System.out.println("2. pringToJson...end...耗时:"+(t3-t2));
	        if(LS)System.out.println("2. pringToJson...end...耗时:"+J3_Util.longToTime(t3-t2));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} ;
	}
}
