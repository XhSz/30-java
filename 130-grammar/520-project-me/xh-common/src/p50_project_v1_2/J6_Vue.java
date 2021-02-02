package p50_project_v1_2;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class J6_Vue {

    static String VUE_PATH = ((Map)J2_Config.CONFIG.get(J2_Main.PROJECT_ID)).get(J2_Config.VUE_PATH).toString();
    static String VUE_OUT_PATH = ((Map)J2_Config.CONFIG.get(J2_Main.PROJECT_ID)).get(J2_Config.VUE_OUT_PATH).toString();
    static String ICORE_PATH = ((Map)J2_Config.CONFIG.get(J2_Main.PROJECT_ID)).get(J2_Config.ICORE_PATH).toString();
    static String CT_PATH = ((Map)J2_Config.CONFIG.get(J2_Main.PROJECT_ID)).get(J2_Config.CT_PATH).toString();
    
    public static void code(String[] args) {
    	String path = "/views/us/UserPages/UserQuery/user_list_query.json";
		JSONObject vueJson = JSONObject.parseObject(J3_Util.getJson(VUE_PATH+path));
		JSONObject result = (JSONObject)J3_Util.searchJson(vueJson,"doRequest");
		System.out.println(result.toJSONString());
	}
    public static void main(String[] args) {
//    	searchToJson();
//    	parseJson();
    	//通过生成的vuejson查询外部交易码对应icore交易，并生成icorejson
//    	JSONObject vueJson =  JSONObject.parseObject(J3_Util.getJson(VUE_OUT_PATH));
//    	J5_Sql.createIcoreJson(vueJson);
//    	J5_Sql.createIcoreJson();
    	//通过生成的icorejson更新tree_menu
//    	JSONObject icoreJson =  JSONObject.parseObject(J3_Util.getJson(ICORE_PATH));
//    	System.out.println(J5_Sql.updateTranIcore(icoreJson));
    	//ct trans
    	J5_Sql.createCtJson();
    	JSONObject ctJson =  JSONObject.parseObject(J3_Util.getJson(CT_PATH));
    	System.out.println(J5_Sql.updateTranIcore(ctJson));
    }
    public static void parseJson() {
    	JSONObject jo =  JSONObject.parseObject(J3_Util.getJson(VUE_OUT_PATH));
    	System.out.println(J5_Sql.update(jo));
    }
    public static void searchToJson() {
    	JSONObject jo =  new JSONObject();
    	List<J1_BeanMenu> vueList = J5_Sql.getVueJson();
    	for(J1_BeanMenu beanMenu : vueList) {
    		try {
	    		JSONObject vueJson = JSONObject.parseObject(J3_Util.getJson(VUE_PATH+beanMenu.getMenuJsonPath()));
	    		JSONObject result = null;
	    		if(null!=vueJson)
	    			result = (JSONObject)J3_Util.searchJson(vueJson,"doRequest");
	    		if(null!=result) {
		    		JSONObject params = (JSONObject)result.get("params");
		    		String servicecode = params.get("servicecode").toString();
		    		beanMenu.setQryTrans(servicecode);
		//    		Map beanMap = new HashMap();
		//    		beanMap.put(beanMenu.getMenuCode(), beanMenu.getQryTrans());
		//        	JSONObject.toJSON(beanMap);
		        	jo.put(beanMenu.getMenuId(), beanMenu.getQryTrans());
	    		}
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    	try {
			J3_Util.pringToJson(new StringBuilder(jo.toJSONString()),VUE_OUT_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
