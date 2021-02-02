package p50_project_v1_1;

import java.sql.*;
import java.util.ArrayList;

public class J5_Sql {

	static boolean LS = J2_Main.LOG_LEVEL>2;
    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/a?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";

    static final String DB_URL_EX_DIT = "jdbc:mysql://10.22.61.3:3306/ctdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    //static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    //static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
 
 
    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "hao6990807";
    static final String USER_EX_DIT = "ct";
    static final String PASS_EX_DIT = "ct";
 
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
        
            // 打开链接
            if(LS)System.out.println("连接数据库...");
//            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            conn = DriverManager.getConnection(DB_URL_EX_DIT,USER_EX_DIT,PASS_EX_DIT);
        
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
        beanMenu.setMenuCode("%");
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
            conn = DriverManager.getConnection(DB_URL_EX_DIT,USER_EX_DIT,PASS_EX_DIT);
	        stmt = conn.createStatement();
			String sql = " select * from tree_menu where menu_upper_id='"+self.getMenuCode()+"' order by menu_code ";
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
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
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
}
