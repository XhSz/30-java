package sql.slow.version.v2_2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class sql_slow {

	public static class SqlLine 
	{
		public String avg_time_avg;
		public BigDecimal time_sum;
		public int count;
		
		public SqlLine(){
			
		}
		public SqlLine(int count,String time){
			this.count = count;
			this.time_sum = new BigDecimal(time);
		}
		public void getAvg(){
			if(null==avg_time_avg||"".equals(avg_time_avg)){
				String avgStr = time_sum.divide(new BigDecimal(count),0,BigDecimal.ROUND_HALF_UP).toString();
				int l = avgStr.length();
				avg_time_avg = l>3?avgStr.substring(0, l-3)+","+avgStr.substring(l-3,l):avgStr;
			}
		}
	}
	public static String PATH_DEFAULT = "";
	public static String NAME_THIS = Thread.currentThread().getStackTrace()[1].getClassName();
	
	public static String TABLE_NAME = "sql_slow";// table name , update if necessary
	
	public static String CUR_DATE = "";
	public static Map<String,SqlLine> MAP_ONL = new HashMap<String,SqlLine>();
	public static Map<String,SqlLine> MAP_BAT = new HashMap<String,SqlLine>();
	

	public static String SPACE = " ";
	public static String head_id_sql = lpad("id_sql",80,SPACE);
	public static int hil = head_id_sql.length();
	public static String head_count = "    count";
	public static int hcl = head_count.length();
	public static String head_avg_time = "  avg_time(ms)";
	public static int hal = head_avg_time.length();
	
	public static void main(String[] args) throws Exception {
		System.out.println("begin...");
		List<String> file_names = getLogPaths();
		for(String file_name:file_names){
			intiMap(file_name);
		}
		outMap();
		System.out.println("end...");
	}
	public static void intiMap(String file_name) 	
	{
		    String[] file_keys = file_name.split("\\.");
		    String date_ = file_keys[2].substring(0, 10);
		    CUR_DATE = date_.substring(0,4)+date_.substring(5,7)+date_.substring(8,10);
		    String[] file_keys_pre = file_keys[0].split("_");
			try {								
				List<String> lines = Files.readAllLines(Paths.get(file_name.toString()), StandardCharsets.UTF_8);
				for (String line : lines) {
					String[] ls = line.split("]");
					if(ls.length<2)break;
					String name="";
					if(",[FRWDB".equals(ls[11]))
						name=ls[12];
					else
						name=ls[11];
					String id_sql = name.substring(4);	//id_sql
					String time = ls[9].substring(12);	//time
					if(file_keys_pre[1].startsWith("onl")){
						readLine(id_sql,time,MAP_ONL);
					}else if(file_keys_pre[1].startsWith("bat")){
						readLine(id_sql,time,MAP_BAT);
					}
				};
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	public static void outMap() 	
	{
		outMap("onl",MAP_ONL);
		outMap("bat",MAP_BAT);
	}
	public static void outMap(String vm,Map<String,SqlLine> im) 	
	{
		//sort
		List<Map.Entry<String,SqlLine>> list = new ArrayList<Map.Entry<String,SqlLine>>(im.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String,SqlLine>>() {
            public int compare(Entry<String, SqlLine> o1,
                    Entry<String, SqlLine> o2) {
            	SqlLine sl1 = o1.getValue();
            	SqlLine sl2 = o2.getValue();
            	sl1.getAvg();
            	sl2.getAvg();
                return sl1.count < o2.getValue().count?1:-1;
            }
        });
        //print
		FileOutputStream outputStream = null;
		try {
		    File file = new File("sql_slow-"+CUR_DATE+"-"+vm+".txt");
		    file.createNewFile();
		    outputStream = new FileOutputStream(file);
		    outputStream.write((head_id_sql+head_count+head_avg_time+"\r\n").getBytes());
	        for(Map.Entry<String,SqlLine> mapping:list){ 
	        	SqlLine sl = mapping.getValue();
//	            System.out.println(mapping.getKey()+":"+sl.count+","+sl.avg_time_avg); 
	   		    StringBuilder sb = new StringBuilder(lpad(mapping.getKey(),hil,SPACE));
	   		    sb.append(lpad(String.valueOf(sl.count),hcl,SPACE));
	   		    sb.append(lpad(sl.avg_time_avg,hcl,SPACE));
	   		    sb.append("\r\n");
				String fromFile = sb.toString();
			    outputStream.write(fromFile.getBytes());
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
	public static void readLine(String id_sql,String time,Map<String,SqlLine> im){
		if(im.containsKey(id_sql)){
			SqlLine sl = im.get(id_sql);
			sl.count++;
			sl.time_sum = sl.time_sum.add(new BigDecimal(time));
		}else{
			SqlLine sl = new SqlLine(1,time);
			im.put(id_sql,sl);
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
	public static String lpad(String s, int n, String replace) {
		while (s.length() < n) {
			s = replace+s;
		}
		return s;
	}
}
