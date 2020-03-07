package p31_cmd;

import java.util.HashMap;
import java.util.Map;



public class InOutTest {

	public static void main(String[] args) {
		System.out.println("begin...");
		System.out.println("current path:"+System.getProperty("user.dir"));
//		printIn(args);
		Map<String, String> inMap = initMap(args);
//		printMap(inMap);
		System.out.println(Thread.currentThread().getStackTrace()[1].getClassName());
		System.out.println("end...");
	}

	public static void printIn(String[] args) 	{
		for(String arg:args){
			System.out.println(arg);
		}
	}
	public static Map<String, String> initMap(String[] args) 	{
		Map<String, String> resultMap = new HashMap<String, String>();
		for(String arg: args){
			String[] kv = arg.split(":");
			resultMap.put(kv[0], kv[1]);
		}
		return resultMap;
	}
	public static void printMap(Map<String, String> inMap) 	{
		for(Map.Entry<String, String> item: inMap.entrySet()){
			System.out.println(item.getKey()+":"+item.getValue());
		}
	}
}
