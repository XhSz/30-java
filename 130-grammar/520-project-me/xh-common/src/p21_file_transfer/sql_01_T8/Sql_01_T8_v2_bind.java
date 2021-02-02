package p21_file_transfer.sql_01_T8;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Sql_01_T8_v2_bind {

	public static String PATH_NAME_IN = "in.txt";
	public static String PATH_NAME_BIND = "bind.txt";
//	public static String PATH_FILE = "D:\\03-sl\\201-PH-CIMB-DAP-Production-CPUDAC\\43-pro-bug-date\\20200311-Multiple CIF - Round 2\\0323-Test Scenarios\\T-04-cif_change\\pro\\tools\\";
	public static String PATH_FILE = "D:\\03-sl\\201-PH-CIMB-DAP-Production-CPUDAC\\43-pro-bug-date\\20200311-Multiple CIF - Round 2\\0323-Test Scenarios\\T_04-cif_change\\pro\\tools\\";
	//case workspace run, modify file path ,such as "D:\\\\slowSql\\\\20200204\\\\ltts_slowsql.log.2020-02-04-1";
	public static String INSERT_PRE_1 = "INSERT INTO `dpb_adjustment_temp`(`acct_no`, `prod_id`, `payment_amt`, `handle_status`, `error_desc`, `sub_acct_no`, `new_prod_id`, `adjust_type`) VALUES ('";
	public static String INSERT_PRE_2 = "','";
	public static String INSERT_PRE_3 = "', 0.00, '0', 'WAIT', '";
	public static String INSERT_PRE_4 = "','";
	public static String INSERT_PRE_5 = "', '2');\r\n";
	public static String PROD_FA01 = "FA01";
	public static String PROD_FA02 = "FA02";
	public static String PROD_GS02 = "GS02";
	public static Set<String> SET_BIND = new HashSet<String>();
	public static Set<String> SET_NOUPGRADE = new HashSet<String>();
	public static String STEP_1_STR = "1-before-batch.sql";
	public static String STEP_4_STR = "4-app-pre.txt";
	public static String STEP_5_STR = "5-unbind.txt";
	
	public static void main(String[] args) {
		System.out.println("1-begin");
		//0.1.1-filter-unbind 
		initSet();
		//0.2.1-pre-create-step_file 
	    try {
		    File file = new File(STEP_1_STR);
		    if(file.exists()) {
	            file.delete();
	        }
			file.createNewFile();
		    file = new File(STEP_4_STR);
		    if(file.exists()) {
	            file.delete();
	        }
			file.createNewFile();
		    file = new File(STEP_5_STR);
		    if(file.exists()) {
	            file.delete();
	        }
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//0.2.1-pre-create-3-cif_unchange_file 
		for(String table:TABLES){
			createItem(table);
		}
		//0.3.1-begin-read_in
		StringBuilder file_name = new StringBuilder(PATH_FILE).append(PATH_NAME_IN);
		try {								
			List<String> lines = Files.readAllLines(Paths.get(file_name.toString()), StandardCharsets.UTF_8);
			T8 t8 = new T8();
			int j = 0;
			for (int i=0;i<lines.size();i++) {
				//parse line
				String line = lines.get(i);
				String[] ls = line.split(",");
				if(ls[2].endsWith(PROD_FA01)||ls[2].endsWith(PROD_FA02)){
					if(ls[2].endsWith(PROD_FA02)){
						System.err.println("FA02:"+ls[1]);
						SET_NOUPGRADE.add(ls[1]);
					}
					t8.setCf01_cust_no(ls[0]);
					t8.setCf01_acct_no(ls[1]);
					t8.setCf01_prod_id(ls[2]);
					t8.setCf01_cust_name(ls[3]);
					t8.setCf01_sub_acct_no(ls[4]);
				}else if(ls[2].endsWith(PROD_GS02)){
					t8.setCf02_cust_no(ls[0]);
					t8.setCf02_acct_no(ls[1]);
					t8.setCf02_prod_id(ls[2]);
					t8.setCf02_cust_name(ls[3]);
					t8.setCf02_sub_acct_no(ls[4]);
				}else{
					System.err.println("error prod id:"+ls);
					continue;
				}
//				System.out.println("i="+i);
				if(i%2==1){
					if(isBind(t8)){
						if(null!=t8.getCf01_cust_name() && t8.getCf01_cust_name().equals(t8.getCf02_cust_name())){
							j++;
							write(t8);
						}else{
							System.err.println(t8.getCf01_cust_no()+","+t8.getCf02_cust_no());
						}
					}else{
						//5-write-5-un_bind_file
						write_unbind(t8);
					}
					t8 = new T8();
				}
			}
			System.out.println("j="+j);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String table:TABLES){
			writeEnd(table);
		}
		Sql_02_dpt02.main(args);
		System.out.println("9-finish");
	}
	
	public static boolean isBind(T8 t8){
		return SET_BIND.contains(t8.getCf02_cust_no());
	}
	public static boolean initSet(){
		boolean r = false;
		StringBuilder file_name = new StringBuilder(PATH_FILE).append(PATH_NAME_BIND);
		try {								
			List<String> lines = Files.readAllLines(Paths.get(file_name.toString()), StandardCharsets.UTF_8);
			for(String line:lines){
				SET_BIND.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return r;
	}
	public static void write(T8 t8){
		FileWriter fileWriter1 = null;
		FileWriter fileWriter2 = null;
		try {
			fileWriter1 =new FileWriter(STEP_1_STR, true);
			if(!SET_NOUPGRADE.contains(t8.getCf01_acct_no())){
				fileWriter1.write(
			    		INSERT_PRE_1
			    		+t8.getCf01_acct_no()
			    		+INSERT_PRE_2
			    		+t8.getCf01_prod_id()
			    		+INSERT_PRE_3
			    		+t8.getCf01_sub_acct_no()
			    		+INSERT_PRE_4
			    		+"FA02"
					    +INSERT_PRE_5
			    	);
			}
		    fileWriter2 =new FileWriter(STEP_4_STR, true);
		    fileWriter2.write(t8.getCf01_cust_no()+"\r\n");
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    try {
		            fileWriter1.flush();
		            fileWriter1.close();
		            fileWriter2.flush();
		            fileWriter2.close();
			    } catch (IOException e) {
			    	e.printStackTrace();
		    }
		}
		//3-write-3-cif_change_file
		for(String table:TABLES){
			writeItem(table,t8);
		}
		for(String table:TABLES_SPEC){
			writeItemSpec(table,t8);
		}
	}
	public static void write_unbind(T8 t8){
		//5-write-5-un_bind_file
		FileWriter fileWriter2 = null;
		try {
		    fileWriter2 =new FileWriter(STEP_5_STR, true);
		    fileWriter2.write(t8.getCf01_cust_no()+","+t8.getCf01_acct_no()+","+t8.getCf01_prod_id()+","+t8.getCf01_cust_name()+","+t8.getCf01_sub_acct_no()+"\r\n");
		    fileWriter2.write(t8.getCf02_cust_no()+","+t8.getCf02_acct_no()+","+t8.getCf02_prod_id()+","+t8.getCf02_cust_name()+","+t8.getCf02_sub_acct_no()+"\r\n");
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    try {
		            fileWriter2.flush();
		            fileWriter2.close();
			    } catch (IOException e) {
			    	e.printStackTrace();
		    }
		}
	}
	public static void writeItem(String table ,T8 t8){
		writeItemColumns(table,t8,"cust_no","acct_no");
	}
	public static void writeItemColumns(String table ,T8 t8,String col1,String col2){
        FileWriter fileWriter = null;
		try {
		    fileWriter =new FileWriter(table+".sql", true);
		    fileWriter.write(
		    		"update "+table+" set "+col1+"='"
		    		+t8.getCf02_cust_no()
		    		+"' where "+col2+"='"
		    		+t8.getCf01_acct_no()
		    		+ "';\r\n"
		    	);
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    try {
		            fileWriter.flush();
		            fileWriter.close();
			    } catch (IOException e) {
			    	e.printStackTrace();
		    }
		}
	}

	public static void writeEnd(String table){
        FileWriter fileWriter = null;
		try {
		    fileWriter =new FileWriter(table+".sql", true);
		    fileWriter.write(
		    		"commit;"
		    	);
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    try {
		            fileWriter.flush();
		            fileWriter.close();
			    } catch (IOException e) {
			    	e.printStackTrace();
		    }
		}
	}
	public static void writeItemSpec(String table ,T8 t8){
		FileWriter fileWriter = null;
		try {
		    fileWriter =new FileWriter(table+".sql", true);
		    fileWriter.write(
		    		"update "+table+" set cust_no='"
		    		+t8.getCf02_cust_no()
		    		+"' where cust_no='"
		    		+t8.getCf01_cust_no()
		    		+ "';\r\n"
		    	);
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    try {
		            fileWriter.flush();
		            fileWriter.close();
			    } catch (IOException e) {
			    	e.printStackTrace();
		    }
		}
	}
	public static void createItem(String table){
		try {
		    File file = new File(table+".sql");
		    if(file.exists()) {
                file.delete();
            }
		    file.createNewFile();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	public static String[] TABLES = {
		"aps_fraud",
		"cfb_cust_list",
		"cmb_chrg_acct",
		"dca_card",
		"dcb_atfraud_trigger",
		"dcs_trxn_finance",
		"dpa_account",
		"dpa_card",
		"dpa_form_move",
		"dpa_sub_account",
		"dpb_agree_transfers",
		"dpb_bill_ctr_temp",
		"dpb_bulk_froze",
		"dpb_file_open",
		"dpb_froze",
		"dpb_joint_account",
		"dpb_satd_temp",
		"dpb_trans_temp",
		"dps_bill",
		"dpt_gcash_acct",
		"fxb_agree_price",
		"saa_acct"
	};
	public static String[] TABLES_SPEC = {
		"lns_bill",
		"dcb_emboss"
	};
}
