package p12_String;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LowerToUpperFirstLetterV2 {
	
	public static String FILE_PATH = "D:\\03-sl\\201-PH-CIMB-DAP-Production-CPUDAC\\14-log\\data-migration\\"
			+ "13-coding\\CASA_CARD_RB-V1.2\\CASA_CARD_RB-V1.2-7-IFS_RB_TRAN_HIST 金融交易流水表-javaSet.txt";
	public static String BEAN = "ifs_rb_tran_hist.set";
	public static String BEAN_1 = "null";
	public static String ENTER = "\r\n";
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
	
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		String line_value = "default";
		String[] array = new String[3];
		String[] array_1 = new String[2];
		//读取、转换、拼接
		try {
			List<String> lines = Files.readAllLines(Paths.get(FILE_PATH.toString()), StandardCharsets.UTF_8);
			for(String line :lines){
				line_value = line;
				sb.append(BEAN);	//append, xx.set
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
				sb.append(ENTER);			//append, xx.setValue(xx.getValue());	//Content\r\n 
			}
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
}
