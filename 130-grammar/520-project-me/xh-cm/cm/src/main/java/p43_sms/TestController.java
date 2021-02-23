package p43_sms;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {
	public static String APP_CODE = "fc01c793cf054f17a4d0598a41048695";
	public static String MOBILE = "15013632409";
	public static void main(String[] args) {
		t();
	}
    @RequestMapping("/t.do" )
    public static void t() {
         String host = "http://yzx.market.alicloudapi.com";
            String path = "/yzx/sendSms";
            String method = "POST";
            String appcode = APP_CODE;
            Map<String, String> headers = new HashMap<String, String>();
            //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
            headers.put("Authorization", "APPCODE " + appcode);
            Map<String, String> querys = new HashMap<String, String>();
            querys.put("mobile", "15013632409");
            //querys.put("param", "code:1234");
            //querys.put("param", "这里填写你和商家定义的变量名称和变量值填写格式看上一行代码");
            querys.put("tpl_id", "Hello, you have a to-do list to be approved");
            Map<String, String> bodys = new HashMap<String, String>();


            try {
                /**
                * 重要提示如下:
                * HttpUtils请从
                * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
                * 下载
                *
                * 相应的依赖请参照
                * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
                */
                HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
                System.err.println(response.toString());

                //获取response的body
                //System.out.println(EntityUtils.toString(response.getEntity()));
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
