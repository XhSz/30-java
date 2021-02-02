package p14_Json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Main {
	public static void main(String[] args) {
		int i = 3;
		if(1==i) {
			String str = "{'@type':'com.dcf.platform.token.MessageHolder','forSend':'211554','generateTime':1490422777204,'id':'18701762172','lastCanSendTime':1490422777204,'message':'211554'}";
			Object obj = JSON.parse(str);
		}else if(2==i) {
			String s2 = "{\"cust_name\":\"xiehao_test_004\",\"gender\":\"L\",\"brithday\":\"553010\",\"operater_ind\":\"A\",\"servicecode\":\"553010\"}";
			JSON.parse(s2);
		}else if(3==i) {
			String s2 = "{\"cust_name\":\"xiehao_test_004\",\"gender\":\"L\",\"brithday\":\"553010\",\"operater_ind\":\"A\",\"servicecode\":\"553010\"}";
			JSONObject.parseObject(s2);
		}
			
	}
}
