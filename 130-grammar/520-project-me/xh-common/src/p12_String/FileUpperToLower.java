package p12_String;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileUpperToLower {
	public static String FILE_PATH = "D:\\03-sl\\201-PH-CIMB-DAP-Production-CPUDAC\\14-log\\data-migration\\"
			+ "13-coding\\IFS_RB_ACCT-upper.sql";
	public static String ENTER = "\r\n";
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		//读取、转换、拼接
		try {
			List<String> lines = Files.readAllLines(Paths.get(FILE_PATH.toString()), StandardCharsets.UTF_8);
			for(String line :lines){
				line = line.toLowerCase();
				sb.append(line);
				sb.append(ENTER);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//输出
		FileOutputStream outputStream = null;
		try {
		    File file_out = new File(FILE_PATH+".output");
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
