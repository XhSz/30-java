package p21_file_transfer.sql_02_dpt39;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import p21_file_transfer.Item_2;

public class Sql_01_v2_bind {

	public static String TID = "T13";
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
	public static String PROD_SA01 = "SA01";
	public static String INSERT_PRE_1 = "INSERT INTO `dpb_adjustment_temp`(`acct_no`, `prod_id`, `payment_amt`, `handle_status`, `error_desc`, `sub_acct_no`, `new_prod_id`, `adjust_type`) VALUES ('";
	public static String INSERT_PRE_2 = "','";
	public static String INSERT_PRE_3 = "', 0.00, '0', 'WAIT', '";
	public static String INSERT_PRE_4 = "','";
	public static String INSERT_PRE_5 = "', '4');\r\n";
	public static Set<String> SET_BIND = new HashSet<String>();
	public static Set<String> SET_NOUPGRADE = new HashSet<String>(); 
	public static String STEP_1_STR = PATH_FILE+"1-before-batch.sql";
	public static String STEP_4_STR = PATH_FILE+"4-app-pre.txt";
	public static String STEP_5_STR = PATH_FILE+"5-unbind.txt";
	
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
				}else if(ls[2].endsWith(PROD_GS01)){
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
    	writeEnd(STEP_1_STR);
		args = new String[2];
		args[0] = ENV;
		args[1] = PATH_FILE;
		Sql_02_dpt39.main(args);
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
			fileWriter1 =new FileWriter(STEP_1_STR, true);
			if(!SET_NOUPGRADE.contains(item_2.getCf02_acct_no())){
				fileWriter1.write(
			    		INSERT_PRE_1
			    		+item_2.getCf01_sub_acct_no()
			    		+INSERT_PRE_2
			    		+item_2.getCf01_prod_id()
			    		+INSERT_PRE_3
			    		+item_2.getCf02_sub_acct_no()
			    		+INSERT_PRE_4
			    		+item_2.getCf02_prod_id()
					    +INSERT_PRE_5
			    	);
			}
		    fileWriter2 =new FileWriter(STEP_4_STR, true);
		    fileWriter2.write(item_2.getCf01_cust_no()+"\r\n");
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
}
