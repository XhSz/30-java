package p50_project_v1_3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* 1.1 vue->json
 * 1.2 add config,
 * 1.3.1 J71 
 * 		scan tran
 */
public class J71_Tran_Main {
	public static String CP = ((Map)J2_Config.CONFIG.get(J2_Main.PROJECT_ID)).get(J2_Config.ICORE_CODE_PATH).toString();
	public static void main(String[] args) {
		List<J1_BeanTran> tranList = new ArrayList<J1_BeanTran>();
		J71_Tran_Util.scanTran(CP,tranList);
		for(J1_BeanTran bean:tranList) {
			System.out.println(bean.getTran_name());
		}
	}
}
