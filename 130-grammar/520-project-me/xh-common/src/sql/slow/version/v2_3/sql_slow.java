package sql.slow.version.v2_3;

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
	public static String PATH_ROOT = "";	//the path class run
	public static String NAME_THIS = Thread.currentThread().getStackTrace()[1].getClassName(); //class name 
	
	public static String TABLE_NAME = "sql_slow";// table name , update if necessary
	
	public static String CUR_DATE = ""; // date format , yyyyMMdd
	public static Map<String,Map<String,Object>> MAP_ONL = new HashMap<String,Map<String,Object>>(); // onl map
	public static Map<String,Map<String,Object>> MAP_BAT = new HashMap<String,Map<String,Object>>(); // bat map
	
	public static String SPACE = " "; // space util 
	//head string
	public static String head_id_sql = lpad("id_sql",80,SPACE);
	public static int hil = head_id_sql.length();
	public static String head_count = "    count";
	public static int hcl = head_count.length();
	public static String head_avg_time = "  avg_time(ms)";
	public static int hal = head_avg_time.length();
	//map key
	public static String KEY_AVG = "avg_time_avg";
	public static String KEY_SUM = "time_sum";
	public static String KEY_COU = "count";
	
	public static void main(String[] args) throws Exception {
		System.out.println("begin...");
		// 1. get files
		List<String> file_names = getLogPaths();
		for(String file_name:file_names){
			// 2. init map
			intiMap(file_name);
		}
		// 3. output map
		outMap();
		System.out.println("end...");
	}
	public static void intiMap(String file_name) 	
	{
	    	// get file par : date、vm、ser
		    String[] file_keys = file_name.split("\\.");
		    String date_ = file_keys[2].substring(0, 10);
		    CUR_DATE = date_.substring(0,4)+date_.substring(5,7)+date_.substring(8,10);
		    String[] file_keys_pre = file_keys[0].split("_");
			try {								
				List<String> lines = Files.readAllLines(Paths.get(file_name.toString()), StandardCharsets.UTF_8);
				for (String line : lines) {
					//parse line
					String[] ls = line.split("]");
					if(ls.length<2)break;
					String name="";
					if(",[FRWDB".equals(ls[11]))
						name=ls[12];
					else
						name=ls[11];
					String id_sql = name.substring(4);	//id_sql
					String time = ls[9].substring(12);	//time
					//parse line for map
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
	public static void outMap(String vm,Map<String,Map<String,Object>> im) 	
	{
		//sort
		List<Map.Entry<String,Map<String,Object>>> list = new ArrayList<Map.Entry<String,Map<String,Object>>>(im.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String,Map<String,Object>>>() {
            public int compare(Entry<String, Map<String,Object>> o1,
                    Entry<String, Map<String,Object>> o2) {
            	Map<String,Object> sl1 = o1.getValue();
            	Map<String,Object> sl2 = o2.getValue();
            	getAvg(sl1);
            	getAvg(sl2);
                return (Integer)sl1.get(KEY_COU) < (Integer)sl2.get(KEY_COU)?1:-1;
            }
        });
        //print
		FileOutputStream outputStream = null;
		try {
		    File file = new File("sql_slow-"+CUR_DATE+"-"+vm+".txt");
		    file.createNewFile();
		    outputStream = new FileOutputStream(file);
		    outputStream.write((head_id_sql+head_count+head_avg_time+"\r\n").getBytes());
	        for(Map.Entry<String,Map<String,Object>> mapping:list){ 
	        	Map<String,Object> sl = mapping.getValue();
//	            System.out.println(mapping.getKey()+":"+sl.get(KEY_COU)+","+sl.get(KEY_AVG)); 
	   		    StringBuilder sb = new StringBuilder(lpad(mapping.getKey(),hil,SPACE));
	   		    sb.append(lpad(String.valueOf(sl.get(KEY_COU)),hcl,SPACE));
	   		    sb.append(lpad(String.valueOf(sl.get(KEY_AVG)),hcl,SPACE));
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
	public static void getAvg(Map<String,Object> im){
		Object avg_time_avg = im.get(KEY_AVG);
		if(null==avg_time_avg){
			BigDecimal time_sum = (BigDecimal)im.get(KEY_SUM);
			String avgStr = time_sum.divide(new BigDecimal(im.get(KEY_COU).toString()),0,BigDecimal.ROUND_HALF_UP).toString();
			int l = avgStr.length();
			if(l>3)avgStr = avgStr.substring(0, l-3)+","+avgStr.substring(l-3,l);
			im.put(KEY_AVG, avgStr);
		}
	}
	
	//parse line for map
	public static void readLine(String id_sql,String time,Map<String,Map<String,Object>> im){
		if(im.containsKey(id_sql)){
			Map<String,Object> sl = im.get(id_sql);
			//add count
			int count = (Integer)sl.get(KEY_COU);
			count++;
			sl.put(KEY_COU,count);
			//add sum
			BigDecimal time_sum = (BigDecimal)sl.get(KEY_SUM);
			time_sum = time_sum.add(new BigDecimal(time));
			sl.put(KEY_SUM,time_sum);
		}else{
			Map<String,Object> sl = new HashMap<String,Object>();
			sl.put(KEY_COU,1);
			sl.put(KEY_SUM,new BigDecimal(time));
			im.put(id_sql,sl);
		}
	}
	public static List<String> getLogPaths() throws Exception 	{
		List<String> rs = new ArrayList<String>();
		PATH_ROOT = System.getProperty("user.dir");
		for(String file :new File(PATH_ROOT).list()){
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
