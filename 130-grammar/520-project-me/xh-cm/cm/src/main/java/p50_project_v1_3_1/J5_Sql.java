package p50_project_v1_3_1;

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
            // 1 insert, 2 update , 3 select , 4 delete
            // 11, 单一插入 , 12 select 插入, 13 批量插入
            // 44 清库删除
            if(111==oper) {
	        	//111,单一插入,trans
            	J1_BeanTran bean = (J1_BeanTran)input;
				String sql = " insert into tree_tran (tran_name) values (?) ";
	    		ps = conn.prepareStatement(sql);
	        	count = insertTran(ps,bean);
	        }else if(112==oper) {
	        	//112,单一插入,tree_tran_relate
	        	J1_BeanTranRelate bean = (J1_BeanTranRelate)input;
				String sql = " insert into tree_tran_relate (tran_name,seq_no,block_test,call_name,tran_type,is_simple) values (?,?,?,?,?,?) ";
	    		ps = conn.prepareStatement(sql);
	        	count = insertTranRelate(ps,bean);
	        }else if(131==oper) {
	        	//131,批量插入,trans
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
	        }else if(221==oper) {
	        	//131,插入,trans
	        	List<J1_BeanTran> list = (List<J1_BeanTran>)input;
				String sql = " select * from tree_menu where is_leaf='1' order by menu_code ";
		        stmt = conn.createStatement();
	        	stmt.executeUpdate(sql);
	        }else if(441==oper) {
	        	//442,清库删除,tree_tran
				String sql = " delete from tree_tran ";
	    		ps = conn.prepareStatement(sql);
	    		count += ps.executeUpdate();
	        }else if(442==oper) {
	        	//442,清库删除,tree_tran_relate
				String sql = " delete from tree_tran_relate ";
	    		ps = conn.prepareStatement(sql);
	    		count += ps.executeUpdate();
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
			count += insertTran(ps,bean);
    	}
    	return count;
    }
    public static int insertTran(PreparedStatement ps,J1_BeanTran bean) throws SQLException{
    	int count = 0;
		ps.setString(1, bean.getTran_name());
		count += ps.executeUpdate();
		System.out.println("insert:"+bean.getTran_name());
    	return count;
    }
    public static int insertTranRelate(PreparedStatement ps,J1_BeanTranRelate bean) throws SQLException{
    	/*  112
    	 * insert into tree_tran_relate 
    	 * (tran_name,seq_no,block_test,call_name,is_simple) values 
    	 * (?,?,?,?,?) 
    	*/
    	int count = 0;
		ps.setString(1, bean.getTran_name());
		ps.setInt(2, bean.getSeq_no());
		ps.setString(3, bean.getBlock_test());
		ps.setString(4, bean.getCall_name());
		ps.setString(5, bean.getTran_type());
		ps.setString(6, bean.isIs_simple()?"Y":"N");
		count += ps.executeUpdate();
		System.out.println("insert:"+bean.getTran_name()+","+bean.getCall_name());
    	return count;
    }
}
