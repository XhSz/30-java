package p50_project_v1_2;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class J5_Sql {

	static boolean LS = J2_Main.LOG_LEVEL>2;
    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
 
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
        
            // 打开链接
            if(LS)System.out.println("连接数据库...");
//            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Map projectMap = (Map) J2_Config.CONFIG.get(J2_Main.PROJECT_ID);
            conn = DriverManager.getConnection(
	            		projectMap.get(J2_Config.DB_URL).toString(),
	            		projectMap.get(J2_Config.DB_USER).toString(),
	            		projectMap.get(J2_Config.DB_PW).toString()
            		);
        
            // 执行查询
            if(LS)System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "select menu_code,menu_name_zh from tree_menu limit 10";
            sql = "select menu_code,menu_desc from ctp_menu limit 10";
            ResultSet rs = stmt.executeQuery(sql);
        
            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
//                int id  = rs.getInt("id");
//                String name = rs.getString("name");
//                String url = rs.getString("url");
                String menu_code = rs.getString("menu_code");
                String menu_name_zh = rs.getString("menu_desc");//menu_desc menu_name_zh
    
                // 输出数据
//                System.out.print("ID: " + id);
//                System.out.print(", 站点名称: " + name);
//                System.out.print(", 站点 URL: " + url);
                System.out.print("menu_code: " + menu_code);
                System.out.print(", menu_name_zh: " + menu_name_zh);
                System.out.print("\n");
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

    public static J1_BeanMenu callSelf(){
        J1_BeanMenu beanMenu = new J1_BeanMenu();
        beanMenu.setLevel(0);
        beanMenu.setMenuId("%");
        beanMenu.setMenuNameZh("vue");
    	callSelf(beanMenu);
    	return beanMenu;
    }
    public static void callSelf(J1_BeanMenu self){
    	int level = self.getLevel();
        long t1=System.currentTimeMillis();
        if(LS)System.out.println("1."+level+" callSelf...begin..."+t1);
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(JDBC_DRIVER);
//            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            Map projectMap = (Map) J2_Config.CONFIG.get(J2_Main.PROJECT_ID);
            conn = DriverManager.getConnection(
	            		projectMap.get(J2_Config.DB_URL).toString(),
	            		projectMap.get(J2_Config.DB_USER).toString(),
	            		projectMap.get(J2_Config.DB_PW).toString()
            		);
	        stmt = conn.createStatement();
			String sql = " select * from tree_menu where menu_upper_id='"+self.getMenuId()+"' order by menu_code ";
	        long t1_1=System.currentTimeMillis();
	        if(LS)System.out.println("1."+level+" executeQuery...begin..."+t1_1);
	        System.out.println("1."+level+" executeQuery...begin..."+sql);
	        ResultSet rs = stmt.executeQuery(sql);
	        long t1_2=System.currentTimeMillis();
	        if(LS)System.out.println("1."+level+" executeQuery...end...耗时:"+(t1_2-t1_1));
	        if(LS)System.out.println("1."+level+" executeQuery...end...耗时:"+J3_Util.longToTime(t1_2-t1_1));
	        while(rs.next()){
	            J1_BeanMenu beanMenu = new J1_BeanMenu();
	            beanMenu.setLevel(level+1);
	            beanMenu.setMenuCode(rs.getString("menu_code"));
	            beanMenu.setMenuId(rs.getString("menu_id"));
	            beanMenu.setMenuNameZh(rs.getString("menu_name_zh"));
	            beanMenu.setAddJsonPath(rs.getString("add_json_path"));
	            beanMenu.setAddTrans(rs.getString("add_trans"));
	            beanMenu.setDelTrans(rs.getString("del_trans"));
	            beanMenu.setEditJsonPath(rs.getString("edit_json_path"));
	            beanMenu.setEditTrans(rs.getString("edit_trans"));
	            beanMenu.setLeaf("1".equals(rs.getString("is_leaf")));
//	            System.err.println("1".equals(rs.getString("is_leaf")));
	            beanMenu.setMenuJsonPath(rs.getString("menu_json_path"));
	            beanMenu.setMenuNameEn(rs.getString("menu_name_en"));
	            beanMenu.setQryTrans(rs.getString("qry_trans"));
	            beanMenu.setQryTransIcore(rs.getString("qry_trans_icore"));
	        	if("0".equals(rs.getString("is_leaf"))) {
	        		// 如果不是叶子节点，继续递归遍历获得子节点
	        		callSelf(beanMenu);
	        	}
		        long t1_3=System.currentTimeMillis();
		        if(LS)System.out.println("1."+level+" callSelf...end...耗时:"+(t1_3-t1_1));
		        if(LS)System.out.println("1."+level+" callSelf...end...耗时:"+J3_Util.longToTime(t1_3-t1_1));
		        System.err.println(self.getMenuNameZh()+"----add----"+beanMenu.getMenuNameZh());
		        self.getChildren().add(beanMenu);
	        }
	        rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
//        p50_project.J3_Util.printDebug(self);
    }
    public static void initTreeMenu(ArrayList<J1_BeanMenu> mainList) {
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(JDBC_DRIVER);
            Map projectMap = (Map) J2_Config.CONFIG.get(J2_Main.PROJECT_ID);
            conn = DriverManager.getConnection(
	            		projectMap.get(J2_Config.DB_URL).toString(),
	            		projectMap.get(J2_Config.DB_USER).toString(),
	            		projectMap.get(J2_Config.DB_PW).toString()
            		);
            stmt = conn.createStatement();
            String sql_1,sql_2,sql_3,sql_4;
            sql_1 = "select menu_code from tree_menu where menu_upper_id='%'";
            ResultSet rs1 = stmt.executeQuery(sql_1);
            while(rs1.next()){
                J1_BeanMenu beanMenu_1 = new J1_BeanMenu();
                beanMenu_1.setMenuCode(rs1.getString("menu_code"));
                beanMenu_1.setMenuNameZh(rs1.getString("menu_name_zh"));
                mainList.add(beanMenu_1);
                sql_2 = "select menu_code from tree_menu where menu_upper_id='"+beanMenu_1.getMenuCode()+"'";
                ResultSet rs2 = stmt.executeQuery(sql_1);
            }
            rs1.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }
    public static List<J1_BeanMenu> getVueJson(){
    	List<J1_BeanMenu>  vueList = new ArrayList<J1_BeanMenu>();
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(JDBC_DRIVER);
            Map projectMap = (Map) J2_Config.CONFIG.get(J2_Main.PROJECT_ID);
            conn = DriverManager.getConnection(
	            		projectMap.get(J2_Config.DB_URL).toString(),
	            		projectMap.get(J2_Config.DB_USER).toString(),
	            		projectMap.get(J2_Config.DB_PW).toString()
            		);
	        stmt = conn.createStatement();
			String sql = " select * from tree_menu where is_leaf='1' order by menu_code ";
	        ResultSet rs = stmt.executeQuery(sql);
	        while(rs.next()){
	            J1_BeanMenu beanMenu = new J1_BeanMenu();
//	            beanMenu.setMenuCode(rs.getString("menu_code"));
	            beanMenu.setMenuId(rs.getString("menu_id"));
	            beanMenu.setMenuNameZh(rs.getString("menu_name_zh"));
//	            beanMenu.setAddJsonPath(rs.getString("add_json_path"));
//	            beanMenu.setAddTrans(rs.getString("add_trans"));
//	            beanMenu.setDelTrans(rs.getString("del_trans"));
//	            beanMenu.setEditJsonPath(rs.getString("edit_json_path"));
//	            beanMenu.setEditTrans(rs.getString("edit_trans"));
//	            beanMenu.setLeaf("1".equals(rs.getString("is_leaf")));
////	        System.err.println("1".equals(rs.getString("is_leaf")));
	            beanMenu.setMenuJsonPath(rs.getString("menu_json_path"));
//	            beanMenu.setMenuNameEn(rs.getString("menu_name_en"));
//	            beanMenu.setQryTrans(rs.getString("qry_trans"));
		        System.err.println(beanMenu.getMenuNameZh()+"--------"+beanMenu.getMenuJsonPath());
		        vueList.add(beanMenu);
	        }
	        rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return vueList;
    }
    public static void createCtJson(){
    	JSONObject jo =  new JSONObject();
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(JDBC_DRIVER);
            Map projectMap = (Map) J2_Config.CONFIG.get(J2_Main.PROJECT_ID);
            conn = DriverManager.getConnection(
	            		projectMap.get(J2_Config.DB_URL).toString(),
	            		projectMap.get(J2_Config.DB_USER).toString(),
	            		projectMap.get(J2_Config.DB_PW).toString()
            		);
	        stmt = conn.createStatement();
			String sql = " select out_service_code,inner_service_code from tsp_service_in ";
          	ResultSet rs = stmt.executeQuery(sql);
	        while(rs.next()){
	            J1_BeanMenu beanMenu = new J1_BeanMenu();
	            beanMenu.setQryTrans(rs.getString("out_service_code"));
	            beanMenu.setQryTransIcore(rs.getString("inner_service_code"));
	        	jo.put(beanMenu.getQryTrans(), beanMenu.getQryTransIcore());
	        }
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    	try {
			J3_Util.pringToJson(new StringBuilder(jo.toJSONString()),J6_Vue.CT_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    public static void createIcoreJson(){
    	JSONObject jo =  new JSONObject();
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(JDBC_DRIVER);
            Map projectMap = (Map) J2_Config.CONFIG.get(J2_Main.PROJECT_ID);
            conn = DriverManager.getConnection(
	            		projectMap.get(J2_Config.DB_URL_ICORE).toString(),
	            		projectMap.get(J2_Config.DB_USER_ICORE).toString(),
	            		projectMap.get(J2_Config.DB_PW_ICORE).toString()
            		);
	        stmt = conn.createStatement();
			String sql = " select out_service_code,inner_service_code from tsp_service_in ";
          	ResultSet rs = stmt.executeQuery(sql);
	        while(rs.next()){
	            J1_BeanMenu beanMenu = new J1_BeanMenu();
	            beanMenu.setQryTrans(rs.getString("out_service_code"));
	            beanMenu.setQryTransIcore(rs.getString("inner_service_code"));
	        	jo.put(beanMenu.getQryTrans(), beanMenu.getQryTransIcore());
	        }
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    	try {
			J3_Util.pringToJson(new StringBuilder(jo.toJSONString()),J6_Vue.ICORE_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    public static void createIcoreJson(JSONObject vueJson){
    	JSONObject jo =  new JSONObject();
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(JDBC_DRIVER);
            Map projectMap = (Map) J2_Config.CONFIG.get(J2_Main.PROJECT_ID);
            conn = DriverManager.getConnection(
	            		projectMap.get(J2_Config.DB_URL_ICORE).toString(),
	            		projectMap.get(J2_Config.DB_USER_ICORE).toString(),
	            		projectMap.get(J2_Config.DB_PW_ICORE).toString()
            		);
	        stmt = conn.createStatement();
			String sql = " select inner_service_code from tsp_service_in where out_service_code='326104' ";
	        for(Map.Entry<String, Object> entry : vueJson.entrySet()) { 		
	        	sql = " select inner_service_code from tsp_service_in where out_service_code='"+entry.getValue()+"' ";
	          	ResultSet rs = stmt.executeQuery(sql);
		        while(rs.next()){
		            J1_BeanMenu beanMenu = new J1_BeanMenu();
		            beanMenu.setQryTrans(entry.getValue().toString());
		            beanMenu.setQryTransIcore(rs.getString("inner_service_code"));
		        	jo.put(beanMenu.getQryTrans(), beanMenu.getQryTransIcore());
		        }
		        rs.close();
	        }  
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    	try {
			J3_Util.pringToJson(new StringBuilder(jo.toJSONString()),J6_Vue.ICORE_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    public static int update(JSONObject vueJson){
    	int count = 0;
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(JDBC_DRIVER);
            Map projectMap = (Map) J2_Config.CONFIG.get(J2_Main.PROJECT_ID);
            conn = DriverManager.getConnection(
	            		projectMap.get(J2_Config.DB_URL).toString(),
	            		projectMap.get(J2_Config.DB_USER).toString(),
	            		projectMap.get(J2_Config.DB_PW).toString()
            		);
	        stmt = conn.createStatement();
			String sql = " select * from tree_menu where is_leaf='1' order by menu_code ";
	        for(Map.Entry<String, Object> entry : vueJson.entrySet()) { 		
//	            if (entry.getKey().equals("name")) {
//	         	    System.out.println(entry.getKey()+" "+entry.getValue());
//	             	//System.out.println(entry.getKey().getClass().toString());
//	         	}
	            sql = " update tree_menu set qry_trans = '"+entry.getValue()+"' where menu_id ='"+entry.getKey()+"' ";
				count += stmt.executeUpdate(sql);
	        }  
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return count;
    }
    public static int updateTranIcore(JSONObject icoreJson){
    	int count = 0;
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(JDBC_DRIVER);
            Map projectMap = (Map) J2_Config.CONFIG.get(J2_Main.PROJECT_ID);
            conn = DriverManager.getConnection(
	            		projectMap.get(J2_Config.DB_URL).toString(),
	            		projectMap.get(J2_Config.DB_USER).toString(),
	            		projectMap.get(J2_Config.DB_PW).toString()
            		);
	        stmt = conn.createStatement();
			String sql = " select * from tree_menu where is_leaf='1' order by menu_code ";
	        for(Map.Entry<String, Object> entry : icoreJson.entrySet()) { 		
//	            if (entry.getKey().equals("name")) {
//	         	    System.out.println(entry.getKey()+" "+entry.getValue());
//	             	//System.out.println(entry.getKey().getClass().toString());
//	         	}
	            sql = " update tree_menu set qry_trans_icore = '"+entry.getValue()+"' where qry_trans ='"+entry.getKey()+"' ";
				count += stmt.executeUpdate(sql);
	        }  
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return count;
    }
}
