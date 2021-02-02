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

public class JAR_PARSER {
	public final static String DAY = "2020/03/31";
	public final static String FIlE_DIR = "D:\\81-micro-dos\\31-tools-string\\";
	public final static String FIlE_SOURCE = FIlE_DIR + "sunline_jar.txt";
	public final static String FIlE_CREATE = FIlE_DIR + "sunline_jar_2.0.txt";
	
	public static void main(String[] args) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date_day = null;
		Date date_item = null;
		FileOutputStream outputStream = null;
		try {		
			date_day = sdf.parse(DAY);
		    File file = new File(FIlE_CREATE);
		    file.createNewFile();
		    outputStream = new FileOutputStream(file);
		    
			List<String> lines = Files.readAllLines(Paths.get(FIlE_SOURCE), StandardCharsets.UTF_8);
			StringBuilder sb = new StringBuilder();
			for (String line : lines) {
				String[] items =  line.split(" ");
				if(items.length==4){
					String[] all_path = items[3].split("\\\\");
					date_item = sdf.parse(items[0]);
					if(date_item.getTime() > date_day.getTime()){
						System.out.println(line);
						sb.append(line+" "+all_path[all_path.length-1]+"\r\n");
					}
				}else{
					System.err.println(line);
				}
			}
			String fromFile = sb.toString();
		    outputStream.write(fromFile.getBytes());
			System.out.println(fromFile);
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    try {
		    	outputStream.close();
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		}
		}
}
