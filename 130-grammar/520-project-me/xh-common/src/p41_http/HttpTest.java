package p41_http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpTest {
    public static String uri = 
//    		"http://127.0.0.1:8080/simpleweb"
//    		"http://127.0.0.1:8181/sump-3.1.0.10-0819-1107-local-RELEASE/call/RPCCall"
    		"http://10.22.62.103:8080/SUMP/call/RPCCall"
//    		"http://10.22.62.102:9009/gateway"	
    ;
    public static String CURRENT_TRAN = 
    		Tran.ap1061
//    		Tran.dp1000
//    		Tran.cf3010
//    		Tran.cf3016
    		;
    
    /**
     * Get����
     */
    @Test
    public void test1() {
        try {
            CloseableHttpClient client = null;
            CloseableHttpResponse response = null;
            try {
                HttpGet httpGet = new HttpGet(uri);

                client = HttpClients.createDefault();
                response = client.execute(httpGet);
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            } finally {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Post����form������
     */
    @Test
    public void test2() {
        try {
            CloseableHttpClient client = null;
            CloseableHttpResponse response = null;
            try {
                // ����һ���ύ���ݵ�����
                List<BasicNameValuePair> parames = new ArrayList<>();
                parames.add(new BasicNameValuePair("code", "001"));
                parames.add(new BasicNameValuePair("name", "����"));

                HttpPost httpPost = new HttpPost(uri + "/test1");
                httpPost.setEntity(new UrlEncodedFormEntity(parames, "UTF-8"));

                client = HttpClients.createDefault();
                response = client.execute(httpPost);
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            } finally {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    	test3();
	}
    /**
     * Post����json����
     */
    @Test
    public static void test3() {
        try {
            CloseableHttpClient client = null;
            CloseableHttpResponse response = null;
            try {
//                ObjectMapper objectMapper = new ObjectMapper();
//                Map<String, Object> data = new HashMap<String, Object>();
//                data.put("code", "001");
//                data.put("name", "����");
                String in = CURRENT_TRAN;//initStr("cf3016");
                
                HttpPost httpPost = new HttpPost(uri);
                httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
                httpPost.setHeader("Cookie", "JSESSIONID=DC0AB8D3184FB00A37218922E3551FF2");
                httpPost.setHeader("Host", "10.22.62.103:8080");
                httpPost.setHeader("Origin", "http://10.22.62.103:8080");
                httpPost.setHeader("Referer", "http://10.22.62.103:8080/");
                httpPost.setHeader("x-edsp-language", "en_US");
                httpPost.setHeader("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiRThDQ0UyQUM5OEEwRjNGMUYxNUNGOEYzRDc4QUQ5M0EiLCJleHAiOjE1OTg4NzczMjMsIm5iZiI6MTU5ODg3MzcyM30.GLcioTy59UgYW9qN6Md2mYbKUgD_1TO1zrBA3tsZ3NU");
                
                //httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(data),ContentType.create("text/json", "UTF-8")));
                httpPost.setEntity(new StringEntity(in,ContentType.create("text/json", "UTF-8")));

                client = HttpClients.createDefault();
                response = client.execute(httpPost);
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                System.out.println(result);
            } finally {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String initStr(String s) {
    	String in = new String();
    	if(s.equals("cf3016")) {
    		in = ""
            ;
    	}else if(s.equals("cf3010")) {
    		in = ""
            ;
    	}
    	return in;
    }
}