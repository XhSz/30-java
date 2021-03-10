package p50_project_v1_3_7;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import p50_project_v1.J1_BeanMenu;

public class J6_Vue {

    static String VUE_CODE_PATH = ((Map)J2_Config.CONFIG.get(J2_Main.PROJECT_ID)).get(J2_Config.VUE_CODE_PATH).toString();
    static String VUE_JSON_PATH = ((Map)J2_Config.CONFIG.get(J2_Main.PROJECT_ID)).get(J2_Config.VUE_JSON_PATH).toString();
   
    public static void main(String[] args) {
    	scanVueRequestToJson();
    	parseJsonUpdateMenu();
//    	code(args);
	}
    public static void code(String[] args) {
    	String path = "/views/us/UserPages/UserQuery/user_list_query.json";
		JSONObject vueJson = JSONObject.parseObject(J3_Util.getJson(VUE_CODE_PATH+path));
		JSONObject result = (JSONObject)J3_Util.searchJson(vueJson,"doRequest");
		System.out.println(result.toJSONString());
	}
    public static void scanVueRequestToJson() {
    	JSONObject jo =  new JSONObject();
    	List<J1_BeanMenu> vueList = (List<J1_BeanMenu>)J5_Sql.doMain(339, J5_Sql.VUE_TREE);//J5_Sql.getVueJson();
    	for(J1_BeanMenu beanMenu : vueList) {
    		try {
	    		JSONObject vueJson = JSONObject.parseObject(J3_Util.getJson(VUE_CODE_PATH+beanMenu.getMenuJsonPath()));
	    		JSONObject result = null;
	    		if(null!=vueJson)
	    			result = (JSONObject)J3_Util.searchJson(vueJson,"doRequest");
	    		if(null!=result) {
		    		JSONObject params = (JSONObject)result.get("params");
		    		String servicecode = params.get("servicecode").toString();
		    		beanMenu.setQryTrans(servicecode);
		        	jo.put(beanMenu.getMenuId(), beanMenu.getQryTrans());
	    		}
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    	try {
			J3_Util.printToJson(new StringBuilder(jo.toJSONString()),VUE_JSON_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
    public static void parseJsonUpdateMenu() {
    	JSONObject jo =  JSONObject.parseObject(J3_Util.getJson(VUE_JSON_PATH));
    	J5_Sql.doMain(239, jo);
    }
}
