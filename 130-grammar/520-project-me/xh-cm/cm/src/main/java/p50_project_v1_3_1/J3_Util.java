package p50_project_v1_3_1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

public class J3_Util {
	
    public static StringBuilder space(int n){
        StringBuilder s = new StringBuilder();
        for(int i=0;i<n;i++){
            s.append("\t");
        }
        return s;
    }
    public static void printDebug(J1_BeanMenu f3){
        for(J1_BeanMenu childMenu : f3.getChildren()) {
            System.err.println("print:"+f3.getMenuNameZh()+"'s child is "+childMenu.getMenuNameZh());
            printDebug(childMenu);
        }
    }
    /**
     * get branch result string
     * 得到枝干节点字符串
     */
    public static StringBuilder getBranchSb(J1_BeanMenu f3){
        StringBuilder sb = new StringBuilder();
        sb.append(space(f3.getLevel()))
                .append(f3.getMenuNameZh()).append(":{\n");
        for(int i=0;i<f3.getChildren().size();i++) {
        	J1_BeanMenu childMenu = f3.getChildren().get(i);
//            System.err.println("print:"+f3.getMenuNameZh()+"'s child is "+childMenu.getMenuNameZh()
//            	+",its level is "+childMenu.getLevel());
        	if(childMenu.isLeaf()) {
    			if(i!=0)sb.append("\n");
        		sb.append(getLeafSb(childMenu));
        	}
        	else
        		sb.append(getBranchSb(childMenu));
        }
		sb.append("\n");
        sb.append(space(f3.getLevel())).append("},");
		sb.append("\n");
        return sb;
    }
    /**
     * get leaf result string
     * 得到叶子节点字符串
     */
    public static StringBuilder getLeafSb(J1_BeanMenu f3){
        StringBuilder sb = new StringBuilder();
        sb.append(space(f3.getLevel()))
                .append("[").append(f3.getMenuCode()).append("]").append(f3.getMenuNameZh()).append(":{\n")
                .append(space(f3.getLevel()+1)).append("menu").append(space(1))
                .append(f3.getQryTransIcore()).append("-").append(f3.getQryTrans()).append(space(1))
                .append(f3.getMenuJsonPath()).append("\n")
                .append(space(f3.getLevel())).append("},");
        return sb;
    }
    public static void pringToJson(StringBuilder s,String jp) throws FileNotFoundException {
        File json = new File(jp);
        if(!json.exists())    
        {    
            try {    
            	json.createNewFile();    
            } catch (IOException e) {    
                e.printStackTrace();    
            }    
        } 
        FileOutputStream fos = new FileOutputStream(json);
        PrintStream ps = new PrintStream(fos);
        ps.print(s);
        ps.close();
    }
    //把绝对时间转化为时分秒显示
    public static String longToTime(long t){
        StringBuilder sb = new StringBuilder();
        if(t>60*60*1000){
            sb.append(t/(60*60*1000)).append("h");
            t=t%(60*60*1000);
        }
        if(t>60*1000){
            sb.append(t/(60*1000)).append("m");
            t=t%(60*1000);
        }
        if(t>1000){
            sb.append(t/1000).append("s");
            t=t%1000;
        }
        sb.append(t).append("ms");
        return sb.toString();
    }

    public static String getJson(String path) {
        String jsonStr = "";
        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            Reader reader = new InputStreamReader(new FileInputStream(file),"Utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (Exception e) {
            return null;
        }
    }
    public void lookJson(JSONObject obj,String searchKey) {
        //没有对象直接解析JSON对象
    	String str  = "{\"name\":\"李明\",\"age\":19}";
        JSONObject jsonObject = JSONObject.parseObject(str);
        for(Map.Entry<String, Object> entry : jsonObject.entrySet()) { 		
            if (entry.getKey().equals("name")) {
         	    System.out.println(entry.getKey()+" "+entry.getValue());
             		//System.out.println(entry.getKey().getClass().toString());
         	}
        }  
    }
    public static Object searchJson(JSONObject jsonObject,String searchKey) {
        for(Map.Entry<String, Object> entry : jsonObject.entrySet()) { 
        	Object value = entry.getValue();		
//            if (entry.getKey().equals("layout")) {
//            	System.out.println("layout");
//         	}		
//            if (entry.getKey().equals("datagrid")) {
//            	System.out.println("datagrid");
//         	}		
//            if (entry.getKey().equals("doRequest")) {
//            	System.out.println("doRequest");
//         	}	
//          if (entry.getKey().equals("form")) {
//        	System.out.println("form");
//          }
            if (entry.getKey().equals(searchKey)) {
            	return value;
         	}else{
         	    if(value.getClass().toString().endsWith("com.alibaba.fastjson.JSONObject")) {
         	    	JSONObject jsonValue = (JSONObject)value;
         	    	if(!jsonValue.isEmpty()) {
         	    		System.err.println(entry.getKey());
         	    		Object childResult = searchJson((JSONObject)value,searchKey);
         	    		if(null==childResult)
         	    			continue;
         	    		else return childResult;
         	    	}
         	    }
         	}
        }  
        return null;
    }
    public static void main(String[] args) {
        //没有对象直接解析JSON对象
    	String str  = "{\"name\":"+"{\"name2\":\"李明2\",\"age\":19}"+",\"age\":19}";
        JSONObject jsonObject = JSONObject.parseObject(str);
        System.out.println(searchJson(jsonObject,"name2"));
    }
    public static void code(String[] args) {
        //没有对象直接解析JSON对象
    	String str  = "{\"name\":"+"{\"name\":\"李明\",\"age\":19}"+",\"age\":19}";
        JSONObject jsonObject = JSONObject.parseObject(str);
        for(Map.Entry<String, Object> entry : jsonObject.entrySet()) { 		
            if (entry.getKey().equals("name")) {
         	    System.out.println(entry.getKey()+" "+entry.getValue());
         	    System.out.println(entry.getValue().getClass().toString());
         	    System.out.println(entry.getValue().getClass().toString().endsWith("com.alibaba.fastjson.JSONObject"));
             		//System.out.println(entry.getKey().getClass().toString());
         	}
        }  
    }
}
