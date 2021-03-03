package p50_project_v1_4_1;

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

import p50_project_v1.J1_BeanCall;
import p50_project_v1.J1_BeanCallRelate;
import p50_project_v1.J1_BeanDb;
import p50_project_v1.J1_BeanMenu;
import p50_project_v1.J1_BeanTran;
import p50_project_v1.J1_BeanTranRelate;


public class J5_Sql {

    public static String ROOT_TREE = "rootTree"; 
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
    public static Object doMain(int oper ,Object input){
    	Object resultObj = null;
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
            // 33 条件查询
            // 34 全量查询
            // 43 条件删除
            // 44 清库删除
        	String tableName = "";
        	String exeSql = "";
        	int ti = oper%10;
        	if(1==ti)
        		tableName = "tree_tran";
        	else if(2==ti) {
        		tableName = "tree_tran_relate";
        		exeSql = " insert into tree_tran_relate (tran_name,seq_no,block_test,call_name,tran_type,is_simple) values (?,?,?,?,?,?) ";
        	}else if(3==ti)
        		tableName = "tree_call";
        	else if(4==ti)
        		tableName = "tree_call_relate";
        	else if(5==ti)
        		tableName = "tree_db_bean";
        	else if(9==ti) {
        		tableName = "tree_tran_relate";
        		exeSql = " insert into tree_tran_relate (tran_name,seq_no,block_test,call_name,tran_type,is_simple) values (?,?,?,?,?,?) ";
        	}
    		ps = conn.prepareStatement(exeSql);
            if(oper>130&&oper<140) {
				J3_Util.logB();
				if(131==oper) {
		        	//131,批量插入,trans
		        	List<J1_BeanTran> list = (List<J1_BeanTran>)input;
					String sql = " insert into tree_tran (tran_name) values (?) ";
		    		ps = conn.prepareStatement(sql);
		        	count = insertTran(ps,list);
		        }else if(132==oper) {
		        	//132,批量插入,tranRelate
		        	List<J1_BeanTranRelate> list = (List<J1_BeanTranRelate>)input;
		        	count = insertTranRelate(ps,list);
		        }else if(133==oper) {
		        	//133,批量插入,call
		        	Set<J1_BeanCall> set = (Set<J1_BeanCall>)input;
					String sql = " insert into tree_call (call_name,call_des,tran_type) values (?,?,?) ";
		    		ps = conn.prepareStatement(sql);
		        	count = insertCall(ps,set);
		        }else if(134==oper) {
		        	//134,批量插入,tree_call_relate
		        	List<J1_BeanCallRelate> set = (List<J1_BeanCallRelate>)input;
					String sql = " insert into tree_call_relate (call_name_parent,seq_no,call_type,call_name_child,table_name,table_oper,call_des,is_simple,is_return) "
							+ "values (?,?,?,?,?,?,?,?,?) ";
		    		ps = conn.prepareStatement(sql);
		        	count = insertCallRelate(ps,set);
		        }else if(135==oper) {
		        	//135,批量插入,call
		        	Set<J1_BeanDb> set = (Set<J1_BeanDb>)input;
					String sql = " insert into tree_db_bean (table_name,table_type,table_oper,table_dao,table_des) "
							+ "values (?,?,?,?,?) ";
		    		ps = conn.prepareStatement(sql);
		        	count = insertDb(ps,set);
		        }
				J3_Util.logE(oper+"-insert "+tableName+"-"+count);
            }
            if(111==oper) {
	        	//111,单一插入,trans
            	J1_BeanTran bean = (J1_BeanTran)input;
				String sql = " insert into tree_tran (tran_name) values (?) ";
	    		ps = conn.prepareStatement(sql);
	        	count = insertTran(ps,bean);
	        }else if(112==oper) {
	        	//112,单一插入,tree_tran_relate
	        	J1_BeanTranRelate bean = (J1_BeanTranRelate)input;
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
            if(oper>330&&oper<340) {
				String sql = " select from "+tableName+" where "+input;
            	if(339==oper) {
            		if(input instanceof String && ROOT_TREE.equals(input)) {
            			resultObj = selectMenu(conn);
            			System.out.println();
            		}else{
            			selectDb(stmt,(Map<String,String>)input);
            		}
            	}
            }
            if(oper>340&&oper<350) {
            	if(343==oper)
            		selectCall(stmt,(Set<String>)input);
            	if(345==oper)
            		selectDb(stmt,(Map<String,String>)input);
            }
            if(oper>430&&oper<440) {
				String sql = " delete from "+tableName+" where "+input;
	    		ps = conn.prepareStatement(sql);
	    		count += ps.executeUpdate();
            }
            if(oper>440&&oper<450) {
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
        if(null!=resultObj)
        	return resultObj;
        else 
        	return count;
    }
    public static J1_BeanMenu selectMenu(Connection conn) throws SQLException{
        J1_BeanMenu beanMenu = new J1_BeanMenu();
        beanMenu.setLevel(0);
        beanMenu.setMenuId("%");
        beanMenu.setMenuNameZh("vue");
        J5_Sql.selectMenu(conn,beanMenu);
    	return beanMenu;
    }
    public static void selectMenu(Connection conn,J1_BeanMenu self) throws SQLException{
    	int level = self.getLevel();
		String sql = " select * from tree_menu where menu_upper_id='"+self.getMenuId()+"' order by menu_code ";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		if(!rs.isClosed()) {
			System.out.println();
		}else{
			System.out.println();
		}
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
        		selectMenu(conn,beanMenu);
        	}
	        System.err.println(self.getMenuNameZh()+"----add----"+beanMenu.getMenuNameZh());
	        if(beanMenu.getMenuCode().equals("5401"))
	        	System.out.println();
	        self.getChildren().add(beanMenu);
        }
    }
    public static void selectCall(Statement stmt,Set<String> callSet) throws SQLException {
        String sql = "select call_name from tree_call ";
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
        	callSet.add(rs.getString("call_name"));
        }
    }
    public static void selectDb(Statement stmt,Set<String> dbSet) throws SQLException {
        String sql = "select table_dao from tree_db_bean ";
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
        	dbSet.add(rs.getString("table_name"));
        }
    }
    public static void selectDb(Statement stmt,Map<String,String> dbMap) throws SQLException {
        String sql = "select table_dao,table_name from tree_db_bean ";
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
        	dbMap.put(rs.getString("table_dao"),rs.getString("table_name"));
        }
    }
    public static int insertTran(PreparedStatement ps,List<J1_BeanTran> list) throws SQLException{
    	int count = 0;
    	for(J1_BeanTran bean:list) {
			count += insertTran(ps,bean);
    	}
    	return count;
    }
    public static int insertTranRelate(PreparedStatement ps,List<J1_BeanTranRelate> list) throws SQLException{
    	int count = 0;
    	for(J1_BeanTranRelate bean:list) {
			count += insertTranRelate(ps,bean);
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
    public static int insertCallRelate(PreparedStatement ps,List<J1_BeanCallRelate> list) throws SQLException{
    	int count = 0;
    	for(J1_BeanCallRelate bean:list) {
			count += insertCallRelate(ps,bean);
    	}
    	return count;
    }
    public static int insertDb(PreparedStatement ps,Set<J1_BeanDb> set) throws SQLException{
    	int count = 0;
    	for(J1_BeanDb bean:set) {
			count += insertDb(ps,bean);
    	}
    	return count;
    }
    public static int insertMenu(PreparedStatement ps,J1_BeanCall bean) throws SQLException{
    	int count = 0;
    	return count;
    }
    public static int insertTran(PreparedStatement ps,J1_BeanTran bean) throws SQLException{
    	int count = 0;
		ps.setString(1, bean.getTran_name());
		count += ps.executeUpdate();
		if(J3_Util.DE)System.out.println("insert:"+bean.getTran_name());
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
    		System.err.println(e);
    	}
//		System.out.println("insert:"+bean.getCall_name());
//		System.out.println(bean.getCall_name());
    	return count;
    }
    public static int insertDb(PreparedStatement ps,J1_BeanDb bean) throws SQLException{
    	int count = 0;
		ps.setString(1, bean.getTable_name());
		ps.setString(2, bean.getTable_type());
		ps.setString(3, bean.getTable_oper());
		ps.setString(4, bean.getTable_dao());
		ps.setString(5, bean.getTable_des());
    	try {
    		count += ps.executeUpdate();
    	}catch(Exception e){
    		System.err.println(e);
    	}
    	return count;
    }
    public static int insertTranRelate(PreparedStatement ps,J1_BeanTranRelate bean) throws SQLException{
    	int count = 0;
    	try {
			ps.setString(1, bean.getTran_name());
			ps.setInt(2, bean.getSeq_no());
			ps.setString(3, bean.getBlock_test());
			ps.setString(4, bean.getCall_name());
			ps.setString(5, bean.getTran_type());
			ps.setString(6, bean.isIs_simple()?"Y":"N");
			count += ps.executeUpdate();
			if(J3_Util.DE)System.out.println("insert:"+bean.getTran_name()+","+bean.getCall_name());
    	}catch(SQLException e) {
    		System.err.println();
    		throw e;
    	}
	    return count;
    }
    public static int insertCallRelate(PreparedStatement ps,J1_BeanCallRelate bean) throws SQLException{
    	/*  114,134
				String sql = " insert into tree_call_relate 
				(call_name_parent,seq_no,call_type,call_name_child,table_name,table_oper,call_des,is_simple,is_return) "
						+ "values (?,?,?,?,?,?,?,?,?) ";
    	*/
    	int count = 0;
		ps.setString(1, bean.getCall_name_parent());
		ps.setInt(2, bean.getSeq_no());
		ps.setString(3, bean.getCall_type());
		ps.setString(4, bean.getCall_name_child());
		ps.setString(5, bean.getTable_name());
		ps.setString(6, bean.getTable_oper());
		ps.setString(7, bean.getCall_des());
		ps.setString(8, bean.isIs_simple()?"Y":"N");
		ps.setString(9, bean.isIs_return()?"Y":"N");
		try {
			count += ps.executeUpdate();
		}catch(Exception e){
			System.err.println(e);
		}
		if(J3_Util.DE)System.out.println(bean.getCall_name_parent()+","+bean.getSeq_no()+","+bean.getCall_type()+","
					+bean.getCall_name_child()+","+bean.getTable_name()+","+bean.getTable_oper());
    	return count;
    }
}
