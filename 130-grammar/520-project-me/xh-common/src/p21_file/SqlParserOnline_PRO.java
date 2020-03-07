package p21_file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import p11_date.DateDistance;
import p12_String.CommonString;


public class SqlParserOnline_PRO {

	public final static String DAY = "2019-12-17";
	public final static String FIlE_DIR = "D:\\03-sl\\105-key\\cbs\\1.5.6\\15-cbs-lttran\\4020\\log-pro\\";
	public final static String FIlE_CREATE = FIlE_DIR + "4020-item-sql-insert.log";
	public final static String FIlE_SOURCE = FIlE_DIR + "4020-item.log";
	public final static String TABLE_NAME = "sql_online_4020_pro";
	
	
	public static void main(String[] args) 	{
		int mode = 0; // time6+","+name
			mode = 1; //
			mode = 2; // online default
			mode = 3; // online pro
			
		//File directory = new File(FIlE_DIR);
		//directory.mkdir();//创建文件夹
			
		FileOutputStream outputStream = null;
		try {
		    File file = new File(FIlE_CREATE);
		    file.createNewFile();
		    outputStream = new FileOutputStream(file);

			try {								
				List<String> lines = Files.readAllLines(Paths.get(FIlE_SOURCE), StandardCharsets.UTF_8);
				StringBuilder sb = new StringBuilder();
				String[] col_value = new String[5]; //timeid\sql_name\sql\time\sql_format
				for (String line : lines) {
					String[] ls = line.split("]");
					if(null!=ls&&ls.length>0){
						if(3==mode){
							if("[SQLIF".equals(ls[0])){
								String time_format = ls[1].substring(1);
								col_value[0]=time_format; //timeid
								col_value[1]=ls[10].startsWith("SQL")?ls[10]:ls[9]; //sql_name
								col_value[2]="";//sql_format
								col_value[3]=time_format; //time
								col_value[4]="";//sql
								sb.append("insert into "+TABLE_NAME+" values ('"
										+col_value[0] //timeid
										+"','"
										+col_value[4] //sql ""
										+"','"
										+col_value[1] //sql_name
										+"',\""
										+col_value[2] //sql_format ""
										+"\","
										+0 
										+");\r\n");
							}else continue;
						}
					}
				}
				sb.append("commit;");
				String fromFile = sb.toString();
			    outputStream.write(fromFile.getBytes());
				System.out.println(fromFile);
	 
			} catch (IOException e) {
				e.printStackTrace();
			}
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
