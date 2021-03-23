package p50_project_v1_3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class J5_Sql {

	static boolean LS = J2_Main.LOG_LEVEL>2;
    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
 
    public static void main(String[] args) {
    	J1_BeanTran bean = new J1_BeanTran();
    	bean.setTran_name("us3160");
    	List list = new ArrayList<J1_BeanTran>();
    	list.add(bean);
    	System.out.println(doMain(131,list)); ;
    }
    public static int doMain(int oper ,Object input){
    	int count = 0;
        Connection conn = null;
        Statement stmt = null;	
    	PreparedStatement ps = null;
        try{
            Class.forName(JDBC_DRIVER);
            Map projectMap = (Map) J2_Config.CONFIG.get(J2_Main.PROJECT_ID);
            conn = DriverManager.getConnection(
	            		projectMap.get(J2_Config.DB_URL).toString(),
	            		projectMap.get(J2_Config.DB_USER).toString(),
	            		projectMap.get(J2_Config.DB_PW).toString()
            		);
	        if(131==oper) {
	        	//131,插入,trans
	        	List<J1_BeanTran> list = (List<J1_BeanTran>)input;
				String sql = " insert into tree_tran (tran_name) values (?) ";
	    		ps = conn.prepareStatement(sql);
	        	count = insertTran(ps,list);
	        }else if(221==oper) {
	        	//131,插入,trans
	        	List<J1_BeanTran> list = (List<J1_BeanTran>)input;
				String sql = " select * from tree_menu where is_leaf='1' order by menu_code ";
		        stmt = conn.createStatement();
	        	stmt.executeUpdate(sql);
	        }
	        if(ps!=null)ps.close();
	        if(stmt!=null)stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(ps!=null) ps.close();
            }catch(SQLException se3){
            }
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
    public static int insertTran(PreparedStatement ps,List<J1_BeanTran> list) throws SQLException{
    	int count = 0;
    	for(J1_BeanTran bean:list) {
			ps.setString(1, bean.getTran_name());
			count += ps.executeUpdate();
			System.out.println(bean.getTran_name());
    	}
    	return count;
    }
}
