package p50_project_v1_3_5;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import p50_project_v1_3.J1_BeanTran;
import p50_project_v1_3.J1_BeanTranRelate;

/* 1.1 vue->json
 * 1.2 add config,
 * 1.3.1 J71 
 * 		scan tran
 * 后续计划:
 * 1.3.2 J72
 * 		scan java
 */
public class J71_Tran_Main {
	public static boolean isRealTime = true;
	public static String CP = ((Map)J2_Config.CONFIG.get(J2_Main.PROJECT_ID)).get(J2_Config.ICORE_CODE_PATH).toString();
	public static void main(String[] args) {
		
		List<J1_BeanTran> tranList = new ArrayList<J1_BeanTran>();
		List<J1_BeanTranRelate> tranRelateList = new ArrayList<J1_BeanTranRelate>();
		//清库
		J5_Sql.doMain(441, null);
		J5_Sql.doMain(442, null);
        long t1=System.currentTimeMillis();
        if(J2_Main.LS)System.out.println("scanTran...begin..."+t1);
		//扫描代码根目录，获得所有flowtrans,并入库
		J71_Tran_Util.scanTran(CP,tranList,tranRelateList);
        long t2=System.currentTimeMillis();
        if(J2_Main.LS)System.out.println("scanTran...end...耗时:"+(t2-t1));
        if(J2_Main.LS)System.out.println("scanTran...end...耗时:"+J3_Util.longToTime(t2-t1));
//		for(J1_BeanTran bean:tranList) {
//			System.out.println(bean.getTran_name());
//		}
		//tree_tran入库
    	//J5_Sql.doMain(131,tranList);
	}
}
