package p13_Map;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MapSortSqlLine {
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
	    public static void main(String[] args) {
	        Map<String, SqlLine> map = new HashMap<String, SqlLine>();
	        map.put("c", new SqlLine(6,"8506"));
	        map.put("a", new SqlLine(10,"1057"));
	        map.put("b", new SqlLine(3,"4201"));
	        map.put("d", new SqlLine(18,"502"));

	        List<Map.Entry<String,SqlLine>> list = new ArrayList<Map.Entry<String,SqlLine>>(map.entrySet());
	        Collections.sort(list,new Comparator<Map.Entry<String,SqlLine>>() {
	            //升序排序
	            public int compare(Entry<String, SqlLine> o1,
	                    Entry<String, SqlLine> o2) {
	            	SqlLine sl1 = o1.getValue();
	            	SqlLine sl2 = o2.getValue();
	            	sl1.getAvg();
	            	sl2.getAvg();
	                return sl1.count < o2.getValue().count?1:-1;
	            }

	        });

	        for(Map.Entry<String,SqlLine> mapping:list){ 
	        		SqlLine sl = mapping.getValue();
	               System.out.println(mapping.getKey()+":"+sl.count+","+sl.avg_time_avg); 
	          } 
	     }
}
