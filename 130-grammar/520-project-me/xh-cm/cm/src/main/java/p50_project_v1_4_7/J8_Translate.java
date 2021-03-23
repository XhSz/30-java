package p50_project_v1_4_7;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Http请求工具类 
 * @author snowfigure
 * @since 2014-8-24 13:30:56 
 * @version v1.0.1
 */
public class J8_Translate {
    static boolean proxySet = false;
    static String proxyHost = "127.0.0.1";
    static int proxyPort = 8087;


    public static String getTK(String word){
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try{
            engine.eval("/**\n" +
                    " * Created by lzc on 2018/2/11.\n" +
                    " */\n" +
                    "var b = function (a, b) {\n" +
                    "    for (var d = 0; d < b.length - 2; d += 3) {\n" +
                    "        var c = b.charAt(d + 2),\n" +
                    "            c = \"a\" <= c ? c.charCodeAt(0) - 87 : Number(c),\n" +
                    "            c = \"+\" == b.charAt(d + 1) ? a >>> c : a << c;\n" +
                    "        a = \"+\" == b.charAt(d) ? a + c & 4294967295 : a ^ c\n" +
                    "    }\n" +
                    "    return a\n" +
                    "}\n" +
                    "\n" +
                    "var tk =  function (a,TKK) {\n" +
                    "    for (var e = TKK.split(\".\"), h = Number(e[0]) || 0, g = [], d = 0, f = 0; f < a.length; f++) {\n" +
                    "        var c = a.charCodeAt(f);\n" +
                    "        128 > c ? g[d++] = c : (2048 > c ? g[d++] = c >> 6 | 192 : (55296 == (c & 64512) && f + 1 < a.length && 56320 == (a.charCodeAt(f + 1) & 64512) ? (c = 65536 + ((c & 1023) << 10) + (a.charCodeAt(++f) & 1023), g[d++] = c >> 18 | 240, g[d++] = c >> 12 & 63 | 128) : g[d++] = c >> 12 | 224, g[d++] = c >> 6 & 63 | 128), g[d++] = c & 63 | 128)\n" +
                    "    }\n" +
                    "    a = h;\n" +
                    "    for (d = 0; d < g.length; d++) a += g[d], a = b(a, \"+-a^+6\");\n" +
                    "    a = b(a, \"+-3^+b+-f\");\n" +
                    "    a ^= Number(e[1]) || 0;\n" +
                    "    0 > a && (a = (a & 2147483647) + 2147483648);\n" +
                    "    a %= 1E6;\n" +
                    "    return a.toString() + \".\" + (a ^ h)\n" +
                    "}\n" +
                    "var ftk = tk('"+word+"',eval('((function(){var a\\x3d1306210781;var b\\x3d-490510385;return 421753+\\x27.\\x27+(a+b)})())'));");

            // engine.eval("alert(\"js alert\");");    // 不能调用浏览器中定义的js函数 // 错误，会抛出alert引用不存在的异常
        }catch(ScriptException e){

            e.printStackTrace();
        }
        return engine.get("ftk").toString();
    }


    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");


            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("cookie","_ga=GA1.3.765674498.1518169422; NID=123=bB8U1jEHFBUgpugO_OUl6RN348hrEWqpyXYUkYU9OhyO9tRAy_wpump5zLPtzjmjz2SOJgh-SJHj7uTyJhwfR5ElBftMZNMlhhsUnbg9r5jKrLKUlD5nKRnNrc-C2Juy; _gid=GA1.3.1351352886.1518312479; 1P_JAR=2018-2-11-1");
            connection.setRequestProperty("referer","https://translate.google.cn/");
            connection.setRequestProperty("x-chrome-uma-enabled","1");
            connection.setRequestProperty("x-client-data","CIe2yQEIo7bJAQjEtskBCKmdygEIqKPKAQ==");
            //connection.setRequestProperty(":authority","");


            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                //System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }





    public static void main(String[] argso) {
    	String[] args = new String[2];
        args[0] = "blue";
        //demo:代理访问
        String url = "https://translate.google.cn/translate_a/single";
        String paraE2C = "client=t&sl=en&tl=zh-CN&hl=zh-CN&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&otf=1&ssel=0&tsel=0&kc=1&tk="+ J8_Translate.getTK(args[0])+"&q="+args[0];
        String paraC2E = "client=t&sl=zh-CN&tl=en&hl=en&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&otf=1&ssel=0&tsel=0&kc=1&tk="+ J8_Translate.getTK(args[0])+"&q="+args[0];
        String paraTest = "client=t&sl=en&tl=zh-CN&hl=zh-CN&dt=at&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&ie=UTF-8&oe=UTF-8&otf=1&ssel=0&tsel=0&kc=1&tk="+J8_Translate.getTK("book")+"&q="+"book";
        String urlPre = "https://clients1.google.com/complete/search";
        String paraPre = "q=head&client=translate-web&ds=translate&hl=en&requiredfields=tl%3Azh-CN&callback=_callbacks____0jdi420wp";

        String sr = J8_Translate.sendGet(url,paraE2C);

        String result = sr.substring(getStr(sr,"[",2)-2,getStr(sr,"\"en\"",1)-1);
        String result2 = result.substring(getStr(result,"[",4));
        String result3 = result2.replace("]","\n");
        String result4 = result3.replace("null","");
        String result5 = result4.replace(",","");
        String result6 = result5.replace("["," ");
        String result7 = result6.replace("\"\"",",");
        String[] result8Array = result7.split("\n");
        for(String s:result8Array){

            if(s.contains(".")){
                continue;
            }
            if(s.contains("\""+args[1]+"\"")){
                continue;
            }
            System.out.println(s.trim());
        }

    }

    private static int getStr(String str,String find, int n) {
        int i = 0;
        int s = 0;
        while (i++ < n) {
            s = str.indexOf(find, s + 1);
            if (s == -1) {
                return -1;
            }
        }
        return s;
    }

}