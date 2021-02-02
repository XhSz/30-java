package p12_String;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LowerToUpperFirstLetter {
	
	public static String FILE_PATH = "D:\\03-sl\\201-PH-CIMB-DAP-Production-CPUDAC\\14-log\\data-migration\\"
			+ "13-coding\\CASA_CARD_RB-V1.2\\CASA_CARD_RB-V1.2-9-IFS_CD_POS_AUTH_REG_预授权登记表-javaSet.txt";
	public static String BEAN = "ifs_cd_pos_auth_reg.set";
	public static String ENTER = "\r\n";
	public static String VALUE = "(\"\");";
	
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		//读取、转换、拼接
		try {
			List<String> lines = Files.readAllLines(Paths.get(FILE_PATH.toString()), StandardCharsets.UTF_8);
			for(String line :lines){
				sb.append(BEAN);
				sb.append(line.substring(0, 1).toUpperCase());
				sb.append(line.substring(1, line.length()));
				sb.append(VALUE);
				sb.append(ENTER);
			}
		} catch (IOException e) {
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
