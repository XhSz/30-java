package p21_file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class BufferAndChannel {
	
	public static void main(String[] args) 	{
		int mode = 0; // time6+","+name
			mode = 1; //
		//File directory = new File("D:\03-sl\201-PH-CIMB-DAP-Production-CPUDAC\41-pro-confirm\20191210-slowSql");
		//directory.mkdir();//创建文件夹
		FileOutputStream outputStream = null;
		try {
		    File file = new File("D:\\03-sl\\201-PH-CIMB-DAP-Production-CPUDAC\\41-pro-confirm\\20191210-slowSql\\1\\ltts_slowsql-simple-format-all-insert.log");
		    file.createNewFile();//创建文件
		    outputStream = new FileOutputStream(file);//形参里面可追加true参数，表示在原有文件末尾追加信息
		    //String data = "Hello javaFile";

			//如果是文本文件也可以这么读  调用readAllLines 方法
			try {								
				//JDK1.8以后可以省略第二个参数，默认是UTF-8编码
				List<String> lines = Files.readAllLines(Paths.get("D:\\03-sl\\201-PH-CIMB-DAP-Production-CPUDAC\\41-pro-confirm\\20191210-slowSql\\1\\ltts_slowsql-simple.log"), StandardCharsets.UTF_8);
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
						sb.append("insert into inser1 values ('"
								+ls[1]+"','"
								+name
								+"',"
								+ls[9].substring(12)
								+");\r\n");
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
