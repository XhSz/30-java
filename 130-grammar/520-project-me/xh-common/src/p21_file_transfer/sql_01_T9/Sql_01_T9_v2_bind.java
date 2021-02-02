package p21_file_transfer.sql_01_T9;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Sql_01_T9_v2_bind {

	public static String ENV_PRD = "PRD";
	public static String ENV_UAT = "UAT";
	public static String ENV = ENV_UAT;
	public static String PATH_NAME_IN = "in.txt";
	public static String PATH_NAME_BIND = "bind.txt";
	public static String PATH_FILE = "D:\\30-java\\130-grammar\\520-project-me\\xh-common\\src\\p21_file_transfer\\sql_01_T9\\";
	//case workspace run, modify file path ,such as "D:\\\\slowSql\\\\20200204\\\\ltts_slowsql.log.2020-02-04-1";
	public static String INSERT_PRE_1 = "INSERT INTO `dpb_adjustment_temp`(`acct_no`, `prod_id`, `payment_amt`, `handle_status`, `error_desc`, `sub_acct_no`, `new_prod_id`, `adjust_type`) VALUES ('";
	public static String INSERT_PRE_2 = "','";
	public static String INSERT_PRE_3 = "', 0.00, '0', 'WAIT', '";
	public static String INSERT_PRE_4 = "','";
	public static String INSERT_PRE_5 = "', '2');\r\n";
	public static String PROD_FA01 = "FA01";
	public static String PROD_FA02 = "FA02";
	public static String PROD_GS01 = "GS01";
	public static String PROD_GS02 = "GS02";
	public static Set<String> SET_BIND = new HashSet<String>();
	public static Set<String> SET_NOUPGRADE = new HashSet<String>(); 
	public static String STEP_0_PRE = "D:\\30-java\\130-grammar\\520-project-me\\xh-common\\src\\p21_file_transfer\\sql_01_T9\\";
	public static String STEP_1_STR = STEP_0_PRE+"1-before-batch.sql";
	public static String STEP_3_STR = STEP_0_PRE+"3-after-batch.sql";
	public static String STEP_4_STR = STEP_0_PRE+"4-app-pre.txt";
	public static String STEP_5_STR = STEP_0_PRE+"5-unbind.txt";
	
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
	    if(ENV.equals(ENV_UAT)){
			createItem(STEP_3_STR);
	    }else if(ENV.equals(ENV_PRD)){
			for(String table:TABLES){
				createItem(table+".sql");
			}
	    }
		//0.3.1-begin-read_in
		StringBuilder file_name = new StringBuilder(PATH_FILE).append(PATH_NAME_IN);
		try {								
			List<String> lines = Files.readAllLines(Paths.get(file_name.toString()), StandardCharsets.UTF_8);
			T9 t9 = new T9();
			int j = 0;
			for (int i=0;i<lines.size();i++) {
				//parse line
				String line = lines.get(i);
				String[] ls = line.split(",");
				if(ls[2].endsWith(PROD_GS01)||ls[2].endsWith(PROD_GS02)){
					if(ls[2].endsWith(PROD_GS02)){
						System.err.println("GS02:"+ls[1]);
						SET_NOUPGRADE.add(ls[1]);
					}
					t9.setCf02_cust_no(ls[0]);
					t9.setCf02_acct_no(ls[1]);
					t9.setCf02_prod_id(ls[2]);
					t9.setCf02_cust_name(ls[3]);
					t9.setCf02_sub_acct_no(ls[4]);
				}else if(ls[2].endsWith(PROD_FA02)){
					t9.setCf01_cust_no(ls[0]);
					t9.setCf01_acct_no(ls[1]);
					t9.setCf01_prod_id(ls[2]);
					t9.setCf01_cust_name(ls[3]);
					t9.setCf01_sub_acct_no(ls[4]);
				}else{
					System.err.println("error prod id:"+line);
					continue;
				}
//				System.out.println("i="+i);
				if(i%2==1){
					if(isBind(t9)){
						if(null!=t9.getCf01_cust_name() && t9.getCf01_cust_name().equals(t9.getCf02_cust_name())){
							j++;
							write(t9);
						}else{
							System.err.println("not match:"+t9.getCf01_cust_no()+","+t9.getCf02_cust_no());
						}
					}else{
						//5-write-5-un_bind_file
						write_unbind(t9);
					}
					t9 = new T9();
				}
			}
			System.out.println("j="+j);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	writeEnd(STEP_1_STR);
	    if(ENV.equals(ENV_UAT)){
	    	writeEnd(STEP_3_STR);
	    }else if(ENV.equals(ENV_PRD)){
			for(String table:TABLES){
				writeEnd(table+".sql");
			}
	    }
		args = new String[2];
		args[0] = ENV;
		args[1] = STEP_0_PRE;
		Sql_02_dpt02.main(args);
		System.out.println("9-finish");
	}
	
	public static boolean isBind(T9 t9){
		return SET_BIND.contains(t9.getCf02_cust_no());
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
	public static void write(T9 t9){
		FileWriter fileWriter1 = null;
		FileWriter fileWriter2 = null;
		try {
			fileWriter1 =new FileWriter(STEP_1_STR, true);
			if(!SET_NOUPGRADE.contains(t9.getCf02_acct_no())){
				fileWriter1.write(
			    		INSERT_PRE_1
			    		+t9.getCf02_acct_no()
			    		+INSERT_PRE_2
			    		+t9.getCf02_prod_id()
			    		+INSERT_PRE_3
			    		+t9.getCf02_sub_acct_no()
			    		+INSERT_PRE_4
			    		+PROD_GS02
					    +INSERT_PRE_5
			    	);
			}
		    fileWriter2 =new FileWriter(STEP_4_STR, true);
		    fileWriter2.write(t9.getCf01_cust_no()+"\r\n");
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
			writeItem(table,t9);
		}
		for(String table:TABLES_SPEC){
			writeItemSpec(table,t9);
		}
	}
	public static void write_unbind(T9 t9){
		//5-write-5-un_bind_file
		FileWriter fileWriter2 = null;
		try {
		    fileWriter2 =new FileWriter(STEP_5_STR, true);
		    fileWriter2.write(t9.getCf01_cust_no()+","+t9.getCf01_acct_no()+","+t9.getCf01_prod_id()+","+t9.getCf01_cust_name()+","+t9.getCf01_sub_acct_no()+"\r\n");
		    fileWriter2.write(t9.getCf02_cust_no()+","+t9.getCf02_acct_no()+","+t9.getCf02_prod_id()+","+t9.getCf02_cust_name()+","+t9.getCf02_sub_acct_no()+"\r\n");
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
	public static void writeItem(String table ,T9 t9){
		writeItemColumns(table,t9,"cust_no","acct_no");
	}
	public static void writeItemColumns(String table ,T9 t9,String col1,String col2){
        FileWriter fileWriter = null;
        String file_name = table+".sql";
		try {
			if(ENV.equals(ENV_UAT))
		        file_name = STEP_3_STR;
		    fileWriter =new FileWriter(file_name, true);
		    fileWriter.write(
		    		"update "+table+" set "+col1+"='"
		    		+t9.getCf02_cust_no()
		    		+"' where "+col2+"='"
		    		+t9.getCf01_acct_no()
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
		    fileWriter =new FileWriter(table, true);
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
	public static void writeItemSpec(String table ,T9 t9){
		FileWriter fileWriter = null;
        String file_name = table+".sql";
		try {
			if(ENV.equals(ENV_UAT))
		        file_name = STEP_3_STR;
		    fileWriter =new FileWriter(file_name, true);
		    fileWriter.write(
		    		"update "+table+" set cust_no='"
		    		+t9.getCf02_cust_no()
		    		+"' where cust_no='"
		    		+t9.getCf01_cust_no()
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
		    File file = new File(table);
		    if(file.exists()) {
                file.delete();
            }
		    file.createNewFile();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	
	
	
	
	public static List<String> TABLES = new ArrayList<String>();
	static {
		TABLES.add("aps_fraud");
		TABLES.add("cfb_cust_list");
		TABLES.add("cmb_chrg_acct");
		TABLES.add("dca_card");
		TABLES.add("dcb_atfraud_trigger");
		TABLES.add("dcs_trxn_finance");
		TABLES.add("dpa_account");
		TABLES.add("dpa_card");
		TABLES.add("dpa_form_move");
		TABLES.add("dpa_sub_account");
		TABLES.add("dpb_agree_transfers");
		TABLES.add("dpb_bill_ctr_temp");
		TABLES.add("dpb_bulk_froze");
		TABLES.add("dpb_file_open");
		TABLES.add("dpb_froze");
		TABLES.add("dpb_joint_account");
		TABLES.add("dpb_satd_temp");
		TABLES.add("dpb_trans_temp");
		TABLES.add("dps_bill");
		TABLES.add("fxb_agree_price");
		TABLES.add("saa_acct");
		if(ENV.equals(ENV_UAT)){
		}else if(ENV.equals(ENV_PRD)){
			TABLES.add("dpt_gcash_acct");
		}
	}
	public static String[] TABLES_SPEC = {
		"lns_bill",
		"dcb_emboss"
	};
}
