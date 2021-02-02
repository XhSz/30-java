package p50_project_v1_1;

import java.io.FileNotFoundException;

/* 1.1 vue->json
 */
public class J2_Main {
	public static int LOG_LEVEL=1;
	//1 ERROR
	//3 SYSTEM
	static boolean LS = LOG_LEVEL>2;
    static String TARGET_PATH =
	"D:\\03-sl\\327-exim\\ex.json"; 
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
			J3_Util.pringToJson(J3_Util.getBranchSb(rootTree), TARGET_PATH);
	        long t3=System.currentTimeMillis();
	        if(LS)System.out.println("2. pringToJson...end...耗时:"+(t3-t2));
	        if(LS)System.out.println("2. pringToJson...end...耗时:"+J3_Util.longToTime(t3-t2));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} ;
	}
}
