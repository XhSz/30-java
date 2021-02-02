package p21_file_table_format;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main1_0 {
	
	public static String MODULE = "3.0-mk";
	public static String PATH_FILE_FORMAT = "D:\\03-sl\\105-key\\cbs\\s";	
	public static String PATH_FILE_SOURCE = "D:\\30-java\\130-grammar\\520-project-me\\xh-common\\src\\p21_file_table_format\\";
	public static String CHAR_ENTER = "\r\n";	
	public static String CHAR_TABLE_KEY = "table_name";
	public static String CHAR_PACKAGE = "-package\\";
	public static String CHAR_BLANK_LINE = ",,";
	public static String CHAR_SPLIT = ",";
	public static String CHAR_CONNECT = "-";
	public static String CHAR_NOTE = "-- ";
	public static String CHAR_FILE_SQL = ".sql";
	public static String MODULE_PACKAGE = PATH_FILE_SOURCE+MODULE+CHAR_PACKAGE;
	public static String PATH_FILE_MODULE = PATH_FILE_SOURCE+MODULE;
	
	public static void main(String[] args) {
		StringBuilder fileFormat = readFileFormat();
		String strFormat = fileFormat.toString();
		StringBuilder writer = new StringBuilder();
		List<String[]> tableList = getTable();
		for(String[] item : tableList){
			String fileName = item[0]+CHAR_CONNECT+item[2];
			String result = strFormat.replaceAll(CHAR_TABLE_KEY, item[0]);
			writer = new StringBuilder();
			writer.append(CHAR_NOTE);
			writer.append(fileName);
			writer.append(result);
			writeFile(MODULE_PACKAGE+fileName+CHAR_FILE_SQL,writer.toString());
		}
	}

    public static StringBuilder readFileFormat(){  
    	StringBuilder result = new StringBuilder(CHAR_ENTER);
		try {								
			List<String> lines = Files.readAllLines(Paths.get(PATH_FILE_FORMAT.toString()), StandardCharsets.UTF_8);
			for (int i=0;i<lines.size();i++) {
				result.append(lines.get(i));
				result.append(CHAR_ENTER);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
    }
    
    public static List<String[]> getTable(){  
    	List<String[]> result = new ArrayList<String[]>();
		try {								
			List<String> lines = Files.readAllLines(Paths.get(PATH_FILE_MODULE.toString()), StandardCharsets.UTF_8);
			for (int i=0;i<lines.size();i++) {
				if(!CHAR_BLANK_LINE.equals(lines.get(i))){
					String[] lineItem = lines.get(i).split(CHAR_SPLIT);
					result.add(lineItem);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
    }

    public static void writeFile(String filePath ,String content){ 
		//输出
		FileOutputStream outputStream = null;
		try {
		    File file_out = new File(filePath);
		    if(!file_out.exists())
		    	file_out.createNewFile();//创建文件
		    outputStream = new FileOutputStream(file_out);
		    outputStream.write(content.getBytes());
//			System.out.println(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
