package p21_file_slow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class db_1_0 {

	public static void main(String[] args) 	{
		String date = "20200204";
		String sers[] = {"25","27"};
		String vms[] = {"onl","onl2"};
		for(String ser:sers){
			for(String vm:vms){
				fun(date,ser,vm);
			}
		}
	}
	public static void fun(String date,String ser,String vm) 	{
		String date_format = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8);
		int mode = 0; // time6+","+name
			mode = 1; //
		String path_root = "D:\\03-sl\\201-PH-CIMB-DAP-Production-CPUDAC\\41-pro-confirm\\20191210-slowSql\\";
		StringBuilder file_name = new StringBuilder(path_root);
		if("onl".equals(vm)||"onl2".equals(vm)){
			file_name = file_name.append("11-sql_slow_onl\\");
		}else if("bat".equals(vm)){
			
		}
		file_name = file_name.append(date).append("\\").append(ser).append("_")
				.append(vm).append("_").append("ltts_slowsql.log-R15.").append(date_format).append("-1");
		//File directory = new File("D:\03-sl\201-PH-CIMB-DAP-Production-CPUDAC\41-pro-confirm\20191210-slowSql");
		//directory.mkdir();//创建文件夹
		FileOutputStream outputStream = null;
		try {
		    File file = new File(file_name+"-insert.sql");
		    file.createNewFile();//创建文件
		    outputStream = new FileOutputStream(file);//形参里面可追加true参数，表示在原有文件末尾追加信息
		    //String data = "Hello javaFile";

			//如果是文本文件也可以这么读  调用readAllLines 方法
			try {								
				//JDK1.8以后可以省略第二个参数，默认是UTF-8编码
				List<String> lines = Files.readAllLines(Paths.get(file_name.toString()), StandardCharsets.UTF_8);
				StringBuilder sb = new StringBuilder();
				for (String line : lines) {
					String[] ls = line.split("]");
					//ls[9].length()
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
						//String time=String.format("%0"+6+"d",Integer.parseInt(ls[9].substring(12)));
						sb.append("insert into sql_slow_onl values ('"
								+ls[1].substring(2,ls[1].length()) 	//id_time
								+"','"
								+name.substring(4)		//id_sql
								+"',"
								+ls[9].substring(12)	//time
								+",'"
								+ls[1].substring(2,12)	//date
								+"','"+ser+"','"+(3==vm.length()?"1":vm.substring(3,4))+"');\r\n");	//ser+vm
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
