package p50_project_v1_3_4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


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
            stmt = conn.createStatement();
            // 1 insert, 2 update , 3 select , 4 delete
            // 11, 单一插入 , 12 select 插入, 13 批量插入
            // 34 全量查询
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
	        }else if(113==oper) {
	        	//113,单一插入,tree_call
	        	J1_BeanCall bean = (J1_BeanCall)input;
				String sql = " insert into tree_call (call_name,call_des,tran_type) values (?,?,?) ";
	    		ps = conn.prepareStatement(sql);
	        	count = insertCall(ps,bean);
	        }else if(114==oper) {
	        	//114,单一插入,tree_tran_relate
	        	J1_BeanCallRelate bean = (J1_BeanCallRelate)input;
				String sql = " insert into tree_call_relate (call_name_parent,call_name_child) values (?,?) ";
	    		ps = conn.prepareStatement(sql);
	        	count = insertCallRelate(ps,bean);
	        }else if(131==oper) {
	        	//131,批量插入,trans
	        	List<J1_BeanTran> list = (List<J1_BeanTran>)input;
				String sql = " insert into tree_tran (tran_name) values (?) ";
	    		ps = conn.prepareStatement(sql);
	        	count = insertTran(ps,list);
	        }else if(133==oper) {
	        	//133,批量插入,call
	        	Set<J1_BeanCall> set = (Set<J1_BeanCall>)input;
				String sql = " insert into tree_call (call_name,call_des,tran_type) values (?,?,?) ";
	    		ps = conn.prepareStatement(sql);
	        	count = insertCall(ps,set);
	        }else if(221==oper) {
	        	//131,插入,trans
	        	List<J1_BeanTran> list = (List<J1_BeanTran>)input;
				String sql = " select * from tree_menu where is_leaf='1' order by menu_code ";
	        	stmt.executeUpdate(sql);
	        }else if(221==oper) {
	        	//131,插入,trans
	        	List<J1_BeanTran> list = (List<J1_BeanTran>)input;
				String sql = " select * from tree_menu where is_leaf='1' order by menu_code ";
	        	stmt.executeUpdate(sql);
	        }
            if(oper>340&&oper<350) {
            	if(343==oper)
            		selectCall(stmt,(Set<String>)input);
            	System.out.println();
            }
            if(oper>440&&oper<450) {
            	String tableName = "";
            	if(441==oper)
            		tableName = "tree_tran";
            	else if(442==oper)
            		tableName = "tree_tran_relate";
            	else if(443==oper)
            		tableName = "tree_call";
            	else if(444==oper)
            		tableName = "tree_call_relate";
				String sql = " delete from "+tableName+" ";
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
    public static void selectCall(Statement stmt,Set<String> callSet) throws SQLException {
        String sql = "select call_name from tree_call ";
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
        	callSet.add(rs.getString("call_name"));
        }
    	System.out.println();
    }
    public static int insertTran(PreparedStatement ps,List<J1_BeanTran> list) throws SQLException{
    	int count = 0;
    	for(J1_BeanTran bean:list) {
			count += insertTran(ps,bean);
    	}
    	return count;
    }
    public static int insertCall(PreparedStatement ps,Set<J1_BeanCall> set) throws SQLException{
    	int count = 0;
    	for(J1_BeanCall bean:set) {
			count += insertCall(ps,bean);
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
    public static int insertCall(PreparedStatement ps,J1_BeanCall bean) throws SQLException{
    	int count = 0;
		ps.setString(1, bean.getCall_name());
		ps.setString(2, bean.getCall_des());
		ps.setString(3, bean.getTran_type());
    	try {
    		count += ps.executeUpdate();
    	}catch(Exception e){
    		System.err.println();
    	}
//		System.out.println("insert:"+bean.getCall_name());
//		System.out.println(bean.getCall_name());
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
    public static int insertCallRelate(PreparedStatement ps,J1_BeanCallRelate bean) throws SQLException{
    	/*  114
    	 * insert into tree_call_relate 
    	 * (call_name_parent,call_name_child) values 
    	 * (?,?) 
    	*/
    	int count = 0;
		ps.setString(1, bean.getCall_name_parent());
		ps.setString(2, bean.getCall_name_child());
		count += ps.executeUpdate();
		System.out.println(bean.getCall_name_parent()+","+bean.getCall_name_child());
    	return count;
    }
}
