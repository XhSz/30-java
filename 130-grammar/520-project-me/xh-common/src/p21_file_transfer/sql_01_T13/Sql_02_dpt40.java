package p21_file_transfer.sql_01_T13;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Sql_02_dpt40 {
	public static String ENV_PRD = "PRD";
	public static String ENV_UAT = "UAT";
	public static String ENV = ENV_UAT;
	public static 	Date date=new Date();//取时间
	public static 	Calendar calendar = new GregorianCalendar();
	public static String day;
	public static String month;
	public static String CONTEXT = "";
	public static String TID = "";
	public static String PATH_FILE = "\\src\\p21_file_transfer\\sql_01_"+TID+"\\";
	public static String STEP_2_STR = "2-run-batch.sql";
	
	public static void init(String[] args){
		 calendar.setTime(date);
		 if(ENV_UAT.equals(args[0])){}
		 else if(ENV_PRD.equals(args[0])){
			 calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
		 }
		 date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		 String dateString = formatter.format(date);
		 
		 System.out.println(dateString);
		 int day_int = calendar.get(Calendar.DATE);
		 if(day_int<10) day = "0"+day_int; else day = ""+day_int; 
		 int month_int = calendar.get(Calendar.MONTH) + 1;
		 if(month_int<10) month = "0"+month_int; else month = ""+month_int; 
		 CONTEXT = 
				"-- dpt40"
				+"\r\nINSERT INTO `ksys_plrenw` ("
				 +"\r\n`xitongbs`,"
				 +"\r\n`farendma`,"
				 +"\r\n`pljypich`,"
				 +"\r\n`plrwzxpc`,"
				 +"\r\n`pljytjrq`,"
				 +"\r\n`jiaoyirq`,"
				 +"\r\n`dqjioyrq`,"
				 +"\r\n`pljylcbs`,"
				 +"\r\n`liucbuzh`,"
				 +"\r\n`pljyzbsh`,"
				 +"\r\n`piljybss`,"
				 +"\r\n`ljhaoshi`,"
				 +"\r\n`jiaoyzht`,"
				 +"\r\n`plzxmosh`,"
				 +"\r\n`plrwzdbz`,"
				 +"\r\n`plrwtjsj`,"
				 +"\r\n`plrwuyxj`,"
				 +"\r\n`jyksshij`,"
				 +"\r\n`kshishjc`,"
				 +"\r\n`jyjsshij`,"
				 +"\r\n`jshishjc`,"
				 +"\r\n`xunijibs`,"
				 +"\r\n`ipdizhii`,"
				 +"\r\n`zhujimic`,"
				 +"\r\n`shujuquu`,"
				 +"\r\n`qslcbzha`,"
				 +"\r\n`qszxxhao`,"
				 +"\r\n`qspljyzu`,"
				 +"\r\n`qsbuzhou`,"
				 +"\r\n`cuowxinx`,"
				 +"\r\n`cuowduiz`,"
				 +"\r\n`fuwbiaoz`,"
				 +"\r\n`zxtongbh`"
				+"\r\n)"
				+"\r\nVALUES"
				 +"\r\n("
				  +"\r\n'CBS',"
				  +"\r\n'2086',"
				  +"\r\n'onTimeTransferClose2020"+month+day+"514264',"
				  +"\r\n'1563096000103',"
				  +"\r\n'2020"+month+day+"',"
				  +"\r\n'2020-"+month+"-"+day+"',"
				  +"\r\n'2020"+month+day+"',"
				  +"\r\n'',"
				  +"\r\n0,"
				  +"\r\n'120',"
				  +"\r\n'dpt40',"
				  +"\r\n0,"
				  +"\r\n'onprocess',"
				  +"\r\n'4',"
				  +"\r\nNULL,"
				  +"\r\n'',"
				  +"\r\n5,"
				  +"\r\n'',"
				  +"\r\n0,"
				  +"\r\n'',"
				  +"\r\n0,"
				  +"\r\n'',"
				  +"\r\n'',"
				  +"\r\n'',"
				  +"\r\n'{\"input\":{},\"sys\":{\"prcscd\":\"onTimeUpgradeProd\",\"error_id\":null,\"groupId\":\"120\",\"pljypich\":\"onTimeTransferClose2020"+month+day+"514264\"},\"comm_req\":{\"busi_branch_id\":\"3480\",\"initiator_system\":\"999\",\"sponsor_system\":\"999\",\"busi_teller_id\":\"S####\",\"session_id\":null,\"timerName\":\"onTimeUpgradeProd\",\"jiaoyirq\":\"2020"+month+day+"\",\"busi_org_id\":\"2086\",\"ip_address\":null,\"channel_id\":\"998\",\"over_time\":null}}',"
				  +"\r\n0,"
				  +"\r\n0,"
				  +"\r\n'',"
				  +"\r\n0,"
				  +"\r\n'',"
				  +"\r\n'',"
				  +"\r\n'',"
				  +"\r\n'bat'"
				 +"\r\n);"
				 +"\r\nCOMMIT;";
	}
		public static void main(String[] args) {
			if(null==args||0==args.length){
				args = new String[2];
				args[0] = ENV;
				args[1] = PATH_FILE;
			}else{
				ENV = args[0];
				PATH_FILE = args[1];
			}
			init(args);
			FileWriter fileWriter = null;
			try {
			    File file = new File(PATH_FILE+STEP_2_STR);
			    if(file.exists()) {
	                file.delete();
	            }
			    file.createNewFile();
			    fileWriter =new FileWriter(PATH_FILE+STEP_2_STR, true);
			    fileWriter.write(CONTEXT);
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
}
