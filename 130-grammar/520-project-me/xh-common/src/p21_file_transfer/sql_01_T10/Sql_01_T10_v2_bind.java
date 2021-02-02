package p21_file_transfer.sql_01_T10;

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

import p21_file_transfer.Item_2;

public class Sql_01_T10_v2_bind {

	public static String TID = "T10";
	public static String ENV_PRD = "PRD";
	public static String ENV_UAT = "UAT";
	public static String ENV = ENV_UAT;
	public static String PATH_NAME_IN = "in.txt";
	public static String PATH_NAME_BIND = "bind.txt";
	public static String PATH_ROOT = "D:\\30-java\\130-grammar\\520-project-me\\xh-common\\src\\p21_file_transfer\\";
	public static String PATH_FILE = PATH_ROOT+"sql_01_"+TID+"\\";
	//case workspace run, modify file path ,such as "D:\\\\slowSql\\\\20200204\\\\ltts_slowsql.log.2020-02-04-1";
	public static String PROD_FA01 = "FA01";
	public static String PROD_FA02 = "FA02";
	public static String PROD_GS01 = "GS01";
	public static String PROD_GS02 = "GS02";
	public static Set<String> SET_BIND = new HashSet<String>();
	public static Set<String> SET_NOUPGRADE = new HashSet<String>(); 
	public static String STEP_3_STR = PATH_FILE+"3-after-batch.sql";
	public static String STEP_4_STR = PATH_FILE+"4-app-pre.txt";
	public static String STEP_5_STR = PATH_FILE+"5-unbind.txt";
	
	public static void main(String[] args) {
		System.out.println("1-begin");
		//0.1.1-filter-unbind 
		initSet();
		//0.2.1-pre-create-step_file 
	    try {
	    	File file = new File(STEP_4_STR);
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
			Item_2 item_2 = new Item_2();
			int j = 0;
			for (int i=0;i<lines.size();i++) {
				//parse line
				String line = lines.get(i);
				String[] ls = line.split(",");
				if(ls[2].endsWith(PROD_GS02)){
					item_2.setCf02_cust_no(ls[0]);
					item_2.setCf02_acct_no(ls[1]);
					item_2.setCf02_prod_id(ls[2]);
					item_2.setCf02_cust_name(ls[3]);
					item_2.setCf02_sub_acct_no(ls[4]);
				}else if(ls[2].endsWith(PROD_FA02)){
					item_2.setCf01_cust_no(ls[0]);
					item_2.setCf01_acct_no(ls[1]);
					item_2.setCf01_prod_id(ls[2]);
					item_2.setCf01_cust_name(ls[3]);
					item_2.setCf01_sub_acct_no(ls[4]);
				}else{
					System.err.println("error prod id:"+line);
					continue;
				}
//				System.out.println("i="+i);
				if(i%2==1){
					if(isBind(item_2)){
						if(null!=item_2.getCf01_cust_name() && item_2.getCf01_cust_name().equals(item_2.getCf02_cust_name())){
							j++;
							write(item_2);
						}else{
							System.err.println("not match:"+item_2.getCf01_cust_no()+","+item_2.getCf02_cust_no());
						}
					}else{
						//5-write-5-un_bind_file
						write_unbind(item_2);
					}
					item_2 = new Item_2();
				}
			}
			System.out.println("j="+j);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    if(ENV.equals(ENV_UAT)){
	    	writeEnd(STEP_3_STR);
	    }else if(ENV.equals(ENV_PRD)){
			for(String table:TABLES){
				writeEnd(table+".sql");
			}
	    }
		System.out.println("9-finish");
	}
	
	public static boolean isBind(Item_2 item_2){
		return SET_BIND.contains(item_2.getCf02_cust_no());
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
	public static void write(Item_2 item_2){
		FileWriter fileWriter1 = null;
		FileWriter fileWriter2 = null;
		try {
		    fileWriter2 =new FileWriter(STEP_4_STR, true);
		    fileWriter2.write(item_2.getCf01_cust_no()+"\r\n");
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
		//3-write-3-cif_change_file
		for(String table:TABLES){
			writeItem(table,item_2);
		}
		for(String table:TABLES_SPEC){
			writeItemSpec(table,item_2);
		}
	}
	public static void write_unbind(Item_2 item_2){
		//5-write-5-un_bind_file
		FileWriter fileWriter2 = null;
		try {
		    fileWriter2 =new FileWriter(STEP_5_STR, true);
		    fileWriter2.write(item_2.getCf01_cust_no()+","+item_2.getCf01_acct_no()+","+item_2.getCf01_prod_id()+","+item_2.getCf01_cust_name()+","+item_2.getCf01_sub_acct_no()+"\r\n");
		    fileWriter2.write(item_2.getCf02_cust_no()+","+item_2.getCf02_acct_no()+","+item_2.getCf02_prod_id()+","+item_2.getCf02_cust_name()+","+item_2.getCf02_sub_acct_no()+"\r\n");
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
	public static void writeItem(String table ,Item_2 item_2){
		writeItemColumns(table,item_2,"cust_no","acct_no");
	}
	public static void writeItemColumns(String table ,Item_2 item_2,String col1,String col2){
        FileWriter fileWriter = null;
        String file_name = table+".sql";
		try {
			if(ENV.equals(ENV_UAT))
		        file_name = STEP_3_STR;
		    fileWriter =new FileWriter(file_name, true);
		    fileWriter.write(
		    		"update "+table+" set "+col1+"='"
		    		+item_2.getCf02_cust_no()
		    		+"' where "+col2+"='"
		    		+item_2.getCf01_acct_no()
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
	public static void writeItemSpec(String table ,Item_2 item_2){
		FileWriter fileWriter = null;
        String file_name = table+".sql";
		try {
			if(ENV.equals(ENV_UAT))
		        file_name = STEP_3_STR;
		    fileWriter =new FileWriter(file_name, true);
		    fileWriter.write(
		    		"update "+table+" set cust_no='"
		    		+item_2.getCf02_cust_no()
		    		+"' where cust_no='"
		    		+item_2.getCf01_cust_no()
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
