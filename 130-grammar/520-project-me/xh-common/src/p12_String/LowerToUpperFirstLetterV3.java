package p12_String;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LowerToUpperFirstLetterV3 {
	
	public static String FILE_PATH = "D:\\03-sl\\201-PH-CIMB-DAP-Production-CPUDAC\\14-log\\data-migration\\"
			+ "13-coding\\CASA_CARD_RB-V1.2\\CASA_CARD_RB-V1.2-5-IFS_RB_LIMIT 限额额度信息表-javaSet.txt";
	public static String TABLE = "ifs_rb_limit";
	public static String BATCH_KEY = "ape05";
	
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder(PROCESS_BEGIN);
		
		String line_value = "default";
		String[] array = new String[3];
		String[] array_1 = new String[2];
		//读取、转换、拼接
		try {
			List<String> lines = Files.readAllLines(Paths.get(FILE_PATH.toString()), StandardCharsets.UTF_8);
			for(String line :lines){
				line_value = line;
				sb.append(TAB);
				sb.append(TAB);
				sb.append(TAB);
				sb.append(TAB);
				sb.append(TABLE_SET);	//append, xx.set
				array = line.split(SPLIT_1);	//,
				sb.append(array[0].substring(0, 1).toUpperCase());	//append, xx.setV 
				sb.append(array[0].substring(1, array[0].length())); //append, xx.setValue 
				sb.append(VALUE_0);	//append, xx.setValue( 
				if(array[1].endsWith(BEAN_1)){	//null
					sb.append(BEAN_1);	//append, xx.setValue(null 
					sb.append(VALUE_22);	//append, xx.setValue(null); 
				}else if(array[1].contains(SPLIT_2)){	//.
					array_1 = (array[1]).split(SPLIT_3); //\\.
					sb.append(array_1[0]);	//append, xx.setValue(xx 
					sb.append(SPLIT_2);		//append, xx.setValue(xx. 
					sb.append(VALUE_11);		//append, xx.setValue(xx.get 
					sb.append(array_1[1].substring(0, 1).toUpperCase()); 	//append, xx.setValue(xx.getV 
					sb.append(array_1[1].substring(1, array_1[1].length()));	//append, xx.setValue(xx.getValue 
					sb.append(VALUE_12);		//append, xx.setValue(xx.getValue()); 
				}else{
					System.err.println(line_value); 
					sb.append(VALUE_31);	//append, xx.setValue(" 
					sb.append(array[1]);	//append, xx.setValue("value 
					sb.append(VALUE_32);	//append, xx.setValue("value"); 
				}
				sb.append(VALUE_3);			//append, xx.setValue(xx.getValue());	// 
				sb.append(array[2]);		//append, xx.setValue(xx.getValue());	//Content 
				sb.append(FORMAT_ENTER);	//append, xx.setValue(xx.getValue());	//Content\r\n 
			}
			sb.append(FORMAT_ENTER);
			sb.append(PROCESS_END);	
			
		} catch (Exception e) {
			System.err.println("错误行内容："+line_value);
			e.printStackTrace();
		}
		//输出
		FileOutputStream outputStream = null;
		try {
		    File file_out = new File(FILE_PATH+".setValue.output");
		    file_out.createNewFile();//创建文件
		    outputStream = new FileOutputStream(file_out);//形参里面可追加true参数，表示在原有文件末尾追加信息
			String fromFile = sb.toString();
		    outputStream.write(fromFile.getBytes());
			System.out.println(fromFile);
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static String BATCH_FIRST_UPPER = BATCH_KEY.substring(0, 1).toUpperCase()+BATCH_KEY.substring(1, BATCH_KEY.length());
	public static String BATCH = BATCH_KEY+"DataProcessor";
	
	public static String TAB = "	";
	public static String TABLE_UPDATE = TABLE+"_update";
	public static String TABLE_RESULT = TABLE+"_result";
	public static String TABLE_DAO = TABLE.substring(0, 1).toUpperCase()+TABLE.substring(1, TABLE.length())+"Dao";
	public static String TABLE_RESULT_DAO = TABLE_RESULT.substring(0, 1).toUpperCase()+TABLE_RESULT.substring(1, TABLE_RESULT.length())+"Dao";
	public static String TABLE_SET = TABLE+".set";
	public static String TABLE_PRI_SELECT = ".selectOne_odb1(sub_acct_no , false);\r\n";
	public static String LOG_1_1 = "bizlog.method(\""+BATCH+" 1.1 get result success>>>>>>>>>>>>>>>>\");\r\n";
	
	public static String BEAN_1 = "null";
	public static String FORMAT_ENTER = "\r\n";
	public static String FORMAT_SPACE = " ";
	public static String FORMAT_EQUAL = " = ";
	public static String VALUE = "(\"\");";
	public static String VALUE_0 = "(";
	public static String VALUE_11 = "get";
	public static String VALUE_12 = "());";
	public static String VALUE_22 = ");";
	public static String VALUE_31 = "\""; 
	public static String VALUE_32 = "\");";
	public static String VALUE_3 = "	//";
	public static String SPLIT_1 = ",";
	public static String SPLIT_2 = ".";
	public static String SPLIT_3 = "\\.";
	
	public static String PROCESS_BEGIN = "		private static final BizLog bizlog = BizLogUtil.getBizLog("+BATCH+".class);\r\n"+
			"	\r\n"+
			"		/**\r\n"+
			"	 	* 批次数据项处理逻辑。\r\n"+
			"	 	* \r\n"+
			"	 	* @param job 批次作业ID\r\n"+
			"	 	* @param index  批次作业第几笔数据(从1开始)\r\n"+
			"	 	* @param dataItem 批次数据项\r\n"+
			"	 	* @param input 批量交易输入接口\r\n"+
			"	 	* @param property 批量交易属性接口\r\n"+
			"	 	*/\r\n"+
			"		@Override\r\n"+
			"		public void process(String jobId, int index, java.lang.String sub_acct_no, cn.sunline.ltts.busi.lttran.batchtran.intf."+BATCH_FIRST_UPPER+".Input input, cn.sunline.ltts.busi.lttran.batchtran.intf."+BATCH_FIRST_UPPER+".Property property) {\r\n"+
			"	\r\n"+
			"			bizlog.method(\""+BATCH+" begin >>>>>>>>>>>>>>>>\"+sub_acct_no);\r\n"+
			"	\r\n"+
			"			// 1.首先插入result表\r\n"+
			"			int result_response = -1; //-1,default或failed;0,插入失败;1,成功插入;2,需要更新;\r\n"+
			"	\r\n"+"\r\n"+
			"			"+TABLE_RESULT+" "+TABLE_RESULT+" = "+TABLE_RESULT_DAO+".selectOne_odb1(sub_acct_no , false);\r\n"+
			"	\r\n"+
			"			bizlog.method(\""+BATCH+" 1.1 get result success>>>>>>>>>>>>>>>>\");\r\n"+
			"	\r\n"+		
			"			if(null=="+TABLE_RESULT+"){\r\n"+
			"	\r\n"+			
			"				"+TABLE_RESULT+" = BizUtil.getInstance("+TABLE_RESULT+".class);\r\n"+
			"				"+TABLE_RESULT+".setAcct_no(sub_acct_no);\r\n"+
			"				"+TABLE_RESULT+".setStatus(E_YESORNO.NO);\r\n"+
			"				"+TABLE_RESULT+".setDetails(\"init\");\r\n"+ 
			"	\r\n"+			
			"				bizlog.method(\""+BATCH+" 1.2.1 insert result >>>>>>>>>>>>>>>>\");\r\n"+
			"	\r\n"+			
			"				result_response = "+TABLE_RESULT_DAO+".insert("+TABLE_RESULT+");\r\n"+
			"	\r\n"+			
			"				if(1!=result_response){\r\n"+
			"	\r\n"+				
			"					bizlog.method(\""+BATCH+" 1.2.1 insert result failed>>>>>>>>>>>>>>>>\");\r\n"+
			"					return;\r\n"+
			"				}\r\n"+
			"				bizlog.method(\""+BATCH+" 1.2.1 insert result success>>>>>>>>>>>>>>>>\");\r\n"+
			"	\r\n"+			
			"			}else{\r\n"+
			"	\r\n"+			
			"				bizlog.method(\""+BATCH+" 1.2.2 result exist>>>>>>>>>>>>>>>>\");\r\n"+
			"				//不为空说明有插入记录\r\n"+
			"				if(EnumType.E_YESORNO.YES.equals("+TABLE_RESULT+".getStatus())){\r\n"+
			"	\r\n"+				
			"					bizlog.method(\""+BATCH+" 1.2.2 result already success>>>>>>>>>>>>>>>>\");\r\n"+
			"					return;\r\n"+
			"				}\r\n"+
			"				result_response = 2;\r\n"+
			"			}\r\n"+
			"			try{\r\n"+
			"				// 2.获取需要插入的信息\r\n"+
			"				bizlog.method(\""+BATCH+" 2.0 init bean>>>>>>>>>>>>>>>>\");\r\n"+
			"	\r\n"+
			"				// 3.初始化要插入的信息\r\n"+
			"				bizlog.method(\""+TABLE_DAO+" 3.0 set bean>>>>>>>>>>>>>>>>\");\r\n"+
			"	\r\n"+				
			"				final "+TABLE+" "+TABLE+" = BizUtil.getInstance("+TABLE+".class);\r\n"+
			"	\r\n";		
					

	public static String PROCESS_END = "				// 4.插入数据\r\n"+
			"				bizlog.method(\""+BATCH+" 4.0 insert or update bean>>>>>>>>>>>>>>>>\");\r\n"+
			"				\r\n"+
			"				int response = 0;\r\n"+
			"				\r\n"+
			"				"+TABLE+" "+TABLE_UPDATE+" = "+TABLE_DAO+".selectOne_odb1(sub_acct_no, false);\r\n"+
			"				\r\n"+
			"				if(null=="+TABLE_UPDATE+"){\r\n"+
			"					\r\n"+
			"					bizlog.method(\""+BATCH+" 4.2.1 insert bean begin>>>>>>>>>>>>>>>>\");\r\n"+
			"					response = "+TABLE_DAO+".insert("+TABLE+");\r\n"+
			"				}\r\n"+
			"				else{\r\n"+
			"				\r\n"+
			"					bizlog.method(\""+BATCH+" 4.2.2 update bean begin>>>>>>>>>>>>>>>>\");\r\n"+
			"					response = "+TABLE_DAO+".updateOne_odb1("+TABLE+");\r\n"+
			"				}\r\n"+
			"				if(1==response){\r\n"+
			"					\r\n"+
			"					bizlog.method(\""+BATCH+" 4.3.1 insert or update bean success>>>>>>>>>>>>>>>>\");\r\n"+
			"					"+TABLE_RESULT+".setStatus(E_YESORNO.YES);\r\n"+
			"					"+TABLE_RESULT+".setDetails(\"success\");\r\n"+
			"					\r\n"+
			"				}else{\r\n"+
			"					\r\n"+
			"					bizlog.method(\""+BATCH+" 4.3.2 insert or update bean failed>>>>>>>>>>>>>>>>\");\r\n"+
			"					"+TABLE_RESULT+".setDetails(\"failed\");\r\n"+
			"				}\r\n"+
			"				\r\n"+
			"				"+TABLE_RESULT_DAO+".updateOne_odb1("+TABLE_RESULT+");\r\n"+
			"				\r\n"+
			"			}catch (Exception e){\r\n"+
			"				\r\n"+
			"				 bizlog.method(\""+BATCH+" failed >>>>>>>>>>>>>>>>\");\r\n"+
			"				 \r\n"+
			"				// 5.失败更新错误信息\r\n"+
			"				String details = null;\r\n"+
			"				if(null==e.getCause()){\r\n"+
			"					for(StackTraceElement ste:e.getStackTrace()){\r\n"+
			"						if(ste.toString().contains(\"DataProcessor.process\")){\r\n"+
			"							details = ste.toString();\r\n"+
			"							break;\r\n"+
			"						}\r\n"+
			"					}\r\n"+
			"				}else{\r\n"+
			"					details = e.getCause().toString();\r\n"+
			"				}\r\n"+
			"				if(null==details)\r\n"+
			"					details = e.getStackTrace()[0].toString();\r\n"+
			"				if(details.length()>100)\r\n"+
			"					"+TABLE_RESULT+".setDetails(details.substring(100)); \r\n"+
			"				else\r\n"+
			"					"+TABLE_RESULT+".setDetails(details);\r\n"+ 
			"				\r\n"+ 
			"				"+TABLE_RESULT_DAO+".updateOne_odb1("+TABLE_RESULT+");\r\n"+
			"			}\r\n"+
			"			\r\n"+
			"			bizlog.method(\""+BATCH+" success >>>>>>>>>>>>>>>>\");\r\n"+
			"		}\r\n";
}
