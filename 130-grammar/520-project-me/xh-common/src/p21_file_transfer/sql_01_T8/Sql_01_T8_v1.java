package p21_file_transfer.sql_01_T8;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Sql_01_T8_v1 {

	public static String PATH_NAME = "in.txt";
	public static String PATH_FILE = "D:\\03-sl\\201-PH-CIMB-DAP-Production-CPUDAC\\43-pro-bug-date\\20200311-Multiple CIF - Round 2\\0323-Test Scenarios\\T-04-cif_change\\pro\\tools\\";
	//case workspace run, modify file path ,such as "D:\\\\slowSql\\\\20200204\\\\ltts_slowsql.log.2020-02-04-1";

	public static String PROD_1 = "FA01";
	public static String PROD_2 = "GS02";
	
	public static void main(String[] args) {
		System.out.println("1-begin");
		for(String table:TABLES){
			createItem(table);
		}
		StringBuilder file_name = new StringBuilder(PATH_FILE).append(PATH_NAME);
		try {								
			List<String> lines = Files.readAllLines(Paths.get(file_name.toString()), StandardCharsets.UTF_8);
			T8 t8 = new T8();
			int j = 0;
			for (int i=0;i<lines.size();i++) {
				//parse line
				String line = lines.get(i);
				String[] ls = line.split(",");
				if(ls[2].endsWith(PROD_1)){
					t8.setCf01_cust_no(ls[0]);
					t8.setCf01_acct_no(ls[1]);
					t8.setCf01_cust_name(ls[3]);
				}else if(ls[2].endsWith(PROD_2)){
					t8.setCf02_cust_no(ls[0]);
					t8.setCf02_acct_no(ls[1]);
					t8.setCf02_cust_name(ls[3]);
				}else{
					System.err.println("error prod id:"+ls[2]);
					continue;
				}
//				System.out.println("i="+i);
				if(i%2==1){
					if(null!=t8.getCf01_cust_name() && t8.getCf01_cust_name().equals(t8.getCf02_cust_name())){
						j++;
						write(t8);
					}else{
						System.err.println(t8.getCf01_cust_no()+","+t8.getCf02_cust_no());
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
		System.out.println("9-finish");
	}
	public static void write(T8 t8){
		for(String table:TABLES){
			writeItem(table,t8);
		}
		for(String table:TABLES_SPEC){
			writeItemSpec(table,t8);
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
		    if(!file.exists()) {
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
		"dca_card_test",
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
		"lns_bill"
	};
}
