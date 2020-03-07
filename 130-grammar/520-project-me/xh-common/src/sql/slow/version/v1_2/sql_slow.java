package sql.slow.version.v1_2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class sql_slow {
	
	public static String PATH_DEFAULT = "";
	public static String NAME_THIS = Thread.currentThread().getStackTrace()[1].getClassName();
	
	public static String TABLE_NAME = "sql_slow";// table name , update if necessary
	
	public static void main(String[] args) throws Exception {
		List<String> file_names = getLogPaths();
		for(String file_name:file_names){
			fun(file_name);
		}
	}
	public static void fun(String file_name) 	{
		FileOutputStream outputStream = null;
		try {
		    File file = new File(file_name+"-insert.sql");
		    String[] file_keys = file_name.split("\\.");
		    String[] file_keys_pre = file_keys[0].split("_");
		    file.createNewFile();
		    outputStream = new FileOutputStream(file);
			try {								
				List<String> lines = Files.readAllLines(Paths.get(file_name.toString()), StandardCharsets.UTF_8);
				StringBuilder sb = new StringBuilder();
				for (String line : lines) {
					String[] ls = line.split("]");
					if(ls.length<2)break;
					String name="";
					if(",[FRWDB".equals(ls[11]))
						name=ls[12];
					else
						name=ls[11];
					sb.append("insert into "+TABLE_NAME+" values ('"
							+ls[1].substring(2,ls[1].length()) 	//id_time
							+"','"
							+name.substring(4)		//id_sql
							+"',"
							+ls[9].substring(12)	//time
							+",'"+file_keys[2].substring(0, 9)	//date
							+"','"+file_keys_pre[0]	//ser
							+"','"+file_keys_pre[1]+"');\r\n");	//vm
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
	public static List<String> getLogPaths() throws Exception 	{
		List<String> rs = new ArrayList<String>();
		PATH_DEFAULT = System.getProperty("user.dir");
		for(String file :new File(PATH_DEFAULT).list()){
			if(!file.startsWith(NAME_THIS)&&file.contains(".log."))
				rs.add(file);
		}
		if(rs.isEmpty())throw new Exception("There is no log in current path!");
		return rs;
	}
}
