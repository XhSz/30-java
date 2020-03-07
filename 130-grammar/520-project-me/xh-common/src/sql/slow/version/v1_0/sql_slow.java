package sql.slow.version.v1_0;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class sql_slow {
	
	public static String PATH_FILE = "";
	//case workspace run, modify file path ,such as "D:\\slowSql\\20200204\\ltts_slowsql.log.2020-02-04-1";
	
	public static String PATH_DEFAULT = "";
	public static String NAME_THIS = Thread.currentThread().getStackTrace()[1].getClassName();
	
	public static String KEY_DATE = "date";
	public static String KEY_DATE_VALUE = "";//advise for input date ,such as 2020-02-04
	public static String KEY_SER = "ser";
	public static String KEY_SER_VALUE = "";//advise for input server ,such as 25
	public static String KEY_VM = "vm";
	public static String KEY_VM_VALUE = "";//advise for input vm ,such as onl2
	public static String TABLE_NAME = "sql_slow";// table name , update if necessary
	
	public static void main(String[] args) throws Exception {
		String file_name = "";
		if("".equals(PATH_FILE)){
			//cmd
			Map<String, String> inMap = initMap(args);
			file_name = getLogPath();
			if(inMap.containsKey(KEY_SER))
				KEY_SER_VALUE = inMap.get(KEY_SER);
			if(inMap.containsKey(KEY_VM))
				KEY_VM_VALUE = inMap.get(KEY_VM);
			if(inMap.containsKey(KEY_DATE))
				KEY_DATE_VALUE = inMap.get(KEY_DATE);
		}else{
			//workspace
			file_name = PATH_FILE;
		}
		fun(file_name);
	}
	public static void fun(String file_name) 	{
		int mode = 0; // time6+","+name
			mode = 1; //
		FileOutputStream outputStream = null;
		try {
		    File file = new File(file_name+"-insert.sql");
		    file.createNewFile();
		    outputStream = new FileOutputStream(file);
			try {								
				List<String> lines = Files.readAllLines(Paths.get(file_name.toString()), StandardCharsets.UTF_8);
				StringBuilder sb = new StringBuilder();
				for (String line : lines) {
					String[] ls = line.split("]");
					if(ls.length<2)break;
					String name="";
					if(0==mode){
						if(",[FRWDB".equals(ls[11]))
							name=ls[12];
						else
							name=ls[11];
						String time=String.format("%0"+6+"d",Integer.parseInt(ls[9].substring(12)));
						sb.append(time+","+name+"\r\n");
					}else if(1==mode){
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
								+",'"+KEY_DATE_VALUE	//date
								+"','"+KEY_SER_VALUE	//ser
								+"','"+KEY_VM_VALUE+"');\r\n");	//vm
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
	public static Map<String, String> initMap(String[] args) 	{
		Map<String, String> resultMap = new HashMap<String, String>();
		for(String arg: args){
			String[] kv = arg.split(":");
			if("P".equals(kv[0].toUpperCase())||"PATH".equals(kv[0].toUpperCase()))
				kv[0]="path";
			resultMap.put(kv[0], kv[1]);
		}
		return resultMap;
	}
	public static void printMap(Map<String, String> inMap) 	{
		for(Map.Entry<String, String> item: inMap.entrySet()){
			System.out.println(item.getKey()+":"+item.getValue());
		}
	}
	public static String getLogPath() throws Exception 	{
		PATH_DEFAULT = System.getProperty("user.dir");
		for(String file :new File(PATH_DEFAULT).list()){
			if(!file.startsWith(NAME_THIS))
				return PATH_DEFAULT+"\\"+file;
		}
		throw new Exception("There is no log in current path!");
	}
}
