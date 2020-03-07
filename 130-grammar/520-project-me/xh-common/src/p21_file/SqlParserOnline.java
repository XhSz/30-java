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


public class SqlParserOnline {

	public final static String DAY = "2019-12-17";
	public final static String FIlE_DIR = "D:\\03-sl\\105-key\\cbs\\1.5.6\\15-cbs-lttran\\4020\\log-pro\\";
	public final static String FIlE_CREATE = FIlE_DIR + "4020-item-sql-insert.log";
	public final static String FIlE_SOURCE = FIlE_DIR + "4020-item.log";
	public final static String TABLE_NAME = "sql_online_4020_pro";
	
	
	public static void main(String[] args) 	{
		int mode = 0; // time6+","+name
			mode = 1; //
			mode = 2; // online default
			
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
					if(2==mode){
						if("[SQL  ".equals(ls[0])){
							String time_format = ls[1].substring(1);
							col_value[0]=time_format;
							col_value[1]=ls[7];
							col_value[2]=CommonString.transferDoubleQuotationMarks(ls[8].substring(5));
							col_value[3]=time_format;
							col_value[4]=CommonString.delSqlValue(ls[8].substring(5));
						}else if("[SQLIF".equals(ls[0])){
							// {day, hour, min, sec, misec}
							long[] disr = DateDistance.getDistanceTimesPattern(DAY+" "+col_value[3],DAY+" "+ls[1].substring(1),"yyyy-MM-dd HH:mm:ss,SSS");
							//col_value[2]=disr[4];
							sb.append("insert into "+TABLE_NAME+" values ('"
									+col_value[0]
									+"','"
									+col_value[4]
									+"','"
									+col_value[1]
									+"',\""
									+col_value[2]
									+"\","
									+disr[4]
									+");\r\n");
						}else continue;
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
