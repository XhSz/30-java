package p13_Map;

import java.util.HashMap;
import java.util.Map;

public class MapQuery {
	public static String VALUE = "value";
	public static void main(String[] args) {
		
		Map<Integer,String> cache = new HashMap<Integer,String>(); 

        Long time1 = Long.valueOf(System.nanoTime());	//1纳秒=0.000001 毫秒
        
        System.out.println("time1="+time1);
        
		for(int i=0;i<30300000;i++){
			cache.put(i, VALUE+i);
		}

        Long time2 = Long.valueOf(System.nanoTime());

        System.out.println("time2="+time2);
        System.out.println("time2-time1="+(time2-time1)/1000000);
        
		System.out.println(cache.get(15608188));
		
        Long time3 = Long.valueOf(System.nanoTime());
        
        System.out.println("time3="+time3);
        System.out.println("time3-time2="+(time3-time2)/1000000);
        /*
         * 300w
         *  time1=90215226061900
			time2=90217397996200
			time2-time1=2171
			value56888/value1560888
			time3=90217398137400O_
			time3-time2=0
         */
        /*
         * 3000w	2G
         * time1=90588026600400
			Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
				at java.lang.AbstractStringBuilder.<init>(Unknown Source)
				at java.lang.StringBuilder.<init>(Unknown Source)
				at p13_Map.MapQuery.main(MapQuery.java:17)
         */
	}
}
