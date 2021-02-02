package p21_file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class JAR_PARSER_V2 {
	public final static String DAY = "2020/03/20";
	public final static String[] DAY_2_1 = {"2020/04/01","2020/04/07"};
	public final static String FIlE_DIR = "D:\\81-micro-dos\\31-tools-string\\";
	public final static String FIlE_SOURCE = FIlE_DIR + "sunline_jar.txt";
	public final static String FIlE_CREATE_2_1 = FIlE_DIR + "sunline_jar_2.1.txt";
	public final static String FIlE_CREATE_3_1 = FIlE_DIR + "sunline_jar_3.1.txt";
	
	public static void main(String[] args) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date_day = null;
		Date[] date_2_1 =new Date[2];
		Date date_item = null;
		FileOutputStream outputStream_2_1 = null;
		FileOutputStream outputStream_3_1 = null;
		try {		
			//1-init
			date_day = sdf.parse(DAY);
			date_2_1[0] = sdf.parse(DAY_2_1[0]);
			date_2_1[1] = sdf.parse(DAY_2_1[1]);
		    File file_2_1 = new File(FIlE_CREATE_2_1);
		    file_2_1.createNewFile();
		    outputStream_2_1 = new FileOutputStream(file_2_1);
		    File file_3_1 = new File(FIlE_CREATE_3_1);
		    file_3_1.createNewFile();
		    outputStream_3_1 = new FileOutputStream(file_3_1);
		    //2-read
			List<String> lines = Files.readAllLines(Paths.get(FIlE_SOURCE), StandardCharsets.UTF_8);
			StringBuilder sb_2_1 = new StringBuilder();
			StringBuilder sb_3_1 = new StringBuilder();
			for (String line : lines) {
				String[] items =  line.split(" ");
				if(items.length==4){
					String[] all_path = items[3].split("\\\\");
					date_item = sdf.parse(items[0]);
					if(date_item.getTime() > date_day.getTime()){
						System.out.println(line);
						if(date_item.getTime() == date_2_1[0].getTime()
								||date_item.getTime() == date_2_1[1].getTime()){
							sb_2_1.append(line+" "+all_path[all_path.length-1]+"\r\n");
						}else{
							sb_3_1.append(line+" "+all_path[all_path.length-1]+"\r\n");
						}
					}
				}else{
					System.err.println(line);
				}
			}
			//3-write
			String fromFile_2_1 = sb_2_1.toString();
		    outputStream_2_1.write(fromFile_2_1.getBytes());
			String fromFile_3_1 = sb_3_1.toString();
		    outputStream_3_1.write(fromFile_3_1.getBytes());
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
			//4-finish
		    try {
		    	outputStream_2_1.close();
		    	outputStream_3_1.close();
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		}
	}
}
