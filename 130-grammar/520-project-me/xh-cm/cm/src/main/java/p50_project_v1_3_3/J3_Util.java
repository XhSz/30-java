package p50_project_v1_3_3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.List;
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
    public static void main(String[] args) {
		char ch = 
//				'{'	//123
//				'}'	//125
//				'/n'//13
				' '	//32
//				'('	//40
//				'*'	//42
//				'.'	//46
//				'/'	//47
//				'0'	//48
//				'1'	//49
//				'9'	//57
//				':'	//58
//				';'	//59
//				'<'	//60
//				'@'	//64
//				'A'	//65
//				'Z'	//90
//				'['	//91 
//				'`'	//96 
//				'a'	//97
//				'z'	//122
//				'p'	//112
//				'c'	//99
//				'功'	//21151
//				'能'	//33021
//				'说'	//35828
//				'明'	//26126
				;
		System.out.println((int)ch);
		int i = 9;
//		System.out.println((char)i);
	}
    /**
     * 解析java,{大括号层级匹配法}
     * @param list
     */
    public static String parseJava(String path,Set<String> callKeySet,List<J1_BeanCallRelate> callRelateList,Set<J1_BeanCall> callSet) {
    	//解析用参数
    	int layer = 0; //当前大括号所属层级, 0为Java外层层级,1为java内层级, 2为method层级, >2为方法内部层级
    	int wordPre = 0;	//关键词前一个匹配字符
        StringBuffer wordSb = new StringBuffer();
        StringBuffer noteSb = new StringBuffer();
        StringBuffer noteMulSb = new StringBuffer();
        StringBuffer noteSinSb = new StringBuffer();
    	String noteStr = "";
        StringBuffer lineSb = new StringBuffer();
        boolean wordMatch = false;	//是否单词匹配
        boolean lineMatch = false;	//是否单行匹配
        boolean noteMatch = false;	//是否可能注释匹配
        boolean noteEndMatch = false;	//注释匹配-正式开启，后面开始记录注释
        boolean noteKeyMatch = false;	//是否关键字注释匹配-功能说明
        boolean noteMulMatch = false;	//是否多行注释匹配-/**
        boolean noteSinMatch = false;	//是否单行注释匹配-//
    	//获得文件名
        boolean isSev = false;
        String[] pathArry = path.split("\\\\");
        String fileName = pathArry[pathArry.length-1];
    	String fileKey = fileName.substring(0,fileName.length()-5);
    	if(fileKey.endsWith("Impl")) {
    		fileKey = fileKey.substring(0,fileKey.length()-4);
    		isSev=true;
    	}
        String methodKey = "";
		String callNameParent = "";
//    	System.out.println(fileKey);
		//方法内部解析用
		String before = "";
		boolean isCallRelate = false;
		String callNameChild = "";
        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            Reader reader = new InputStreamReader(new FileInputStream(file),"Utf-8");
            int ch = 0;
            while ((ch = reader.read()) != -1) {
            	isCallRelate = false;
            	String wordStr = "";
            	if(isNoteBegin(ch)) {
            		//1.0 /-》开始匹配注释
            		noteMatch = true;
            	}
    			if(isNotNote(ch)) {
            		//1.0 /或者*-》非注释字符
    				noteMatch =false;
            		noteSinMatch =false;
    			}
            	if(isKeyNoteEnd(ch)){
            		//1.0 <或者*-》关键词匹配关闭
            		noteKeyMatch =false;
            	}
    			if(noteMulMatch&&isNoteBegin(ch)) {
    				//1.0 多行匹配开启时，且/-》多行匹配关闭
            		noteMulMatch =false;
    			}
               //是否多行注释匹配-/**
            	if(noteMatch) {
            		noteMulSb.append((char) ch);
            	}else {
            		if(noteMulSb.length()!=0) {
            			String noteMulStr = noteMulSb.toString();
            			if("/**".equals(noteMulStr)) {
                    		if(1==layer) {
                    			if(isNotNote(ch)) {
                    				noteMulMatch = true;
                    			}
                    		}
            			}
            		}
            		noteMulSb = new StringBuffer();
            	}
            	if(noteMulMatch)
    				noteMatch =true;
            	//是否单行注释匹配-//
            	/*
             	if(noteMatch)
             		noteSinSb.append((char) ch);
             	else {
             		if(noteSinSb.length()!=0) {
             			String noteSinStr = noteSinSb.toString();
             			if("//".equals(noteSinStr)) {
             			}
             		}
             		noteSinSb = new StringBuffer();
             	}*/
            	noteEndMatch = false;
            	if(noteMulMatch&&isNoteEnd(ch)){
            		noteEndMatch = true;
            	}
            	//是否注释匹配开启
            	if(noteMatch&&(noteEndMatch||noteKeyMatch)) {
            		noteSb.append((char) ch);
            	}else {
            		if(!noteKeyMatch) {
                		if(noteSb.length()!=0) {
                			noteStr = noteSb.toString();
//                			System.out.println(noteStr);
                        	noteMatch = false;
                        	noteMulMatch = false;
                		}
                		noteSb = new StringBuffer();
            		}
            	}
            	//判断是否单词解析
            	wordMatch = isLetter(ch);
            	if(wordMatch)
            		wordSb.append((char) ch);
            	else {
            		if(wordSb.length()!=0) {
            			wordStr = wordSb.toString();
            			if("public".equals(wordStr)||"private".equals(wordStr))
                    		lineMatch = true;
            		}
            		wordSb = new StringBuffer();
            	}
            	if(noteKeyMatch) {
            		noteSb.append((char) ch);
            	}else {
            		if(noteSb.length()!=0) {
            			noteStr = noteSb.toString();
            		}
            		noteSb = new StringBuffer();
            	}
            	if("功能说明".equals(wordStr)) {
            		noteKeyMatch =true;
//            		noteMatch = false;
            		noteMulMatch = false;
            		noteSinMatch = false;
            	}
            	//判断是否单行解析
            	if(lineMatch)
            		lineSb.append((char) ch);
            	else {
            		if(lineSb.length()!=0) { 
            			//当前大括号所属层级, 0为Java外层层级,1为java内层级, 2为method层级, >2为方法内部层级
                		if(2==layer) {
//                			if(J2_Main.LS)System.out.println(lineSb);
                			String lineStr = lineSb.toString();
                			lineStr = lineStr.replace("    ", " ");
                			lineStr = lineStr.replace("   ", " ");
                			lineStr = lineStr.replace("  ", " ");
                			String[] lineArry = lineStr.split(" ");
                			String judgeKey = lineArry[1];
                			if("static".equals(judgeKey)) {
                				if("final".equals(lineArry[2])) 
                        			methodKey = lineArry[4];
                				else
                					methodKey = lineArry[3];
                			}
                			else
                				methodKey = lineArry[2];
                			if(methodKey.contains("("))
                				methodKey = methodKey.split("\\(")[0];
                			callNameParent = fileKey+"."+methodKey;
                        	J1_BeanCall bean = new J1_BeanCall();
                        	bean.setCall_name(callNameParent);
//                        	if(noteStr.length()>1)
//                        		noteStr = noteStr.substring(0,noteStr.length()-1);
                			bean.setCall_des(noteStr);
                        	if(isSev)
                        		bean.setTran_type("S");
                        	else
                        		bean.setTran_type("M");
                        	if(!callKeySet.contains(callNameParent)) {
                        		if(null==callRelateList) {
                                	if(J72_Tran_Main.isRealTime) {
                            			J5_Sql.doMain(113, bean);
                                	}else{
                                		callKeySet.add(callNameParent);
                                		callSet.add(bean);
                                	}
                        		}
                        	}
                        	if(J2_Main.LS)System.err.println(callNameParent+","+noteStr);
                    		noteStr = "";
                		}
            		}
            		lineSb = new StringBuffer();
            	}
            	if(123==ch) {	//{
            		layer++;
            		lineMatch = false;
            	}else if(125==ch) {	//}
            		layer--;
//            		break;
            	}else if(112==ch) {	//p
            	}else if(99==ch) {	//c
            	}else if(59==ch) {	//;
            		lineMatch = false;
            	}else if(46==ch) {	//.
            	}else if(32==ch) {	//空格
            	}else if(60==ch) {	//<
            	}
//            	System.err.print((char)ch);
//            	if("registerCustInfo".equals(wordStr))
//                	System.err.println("registerCustInfo's pre="+wordPre);
            	if(layer>1&&isMatch(ch)){
            		//匹配call
            		if(isSpace(wordPre)&&wordStr.length()>0){
                		String callNameChildTest = fileKey+"."+wordStr;
            			if(callKeySet.contains(callNameChildTest)) {
//                        	System.err.println();
//            				System.out.println(callNameParent+","+callNameChildTest);
                        	isCallRelate = true;
                        	callNameChild = callNameChildTest;
            			}
                	}
            		if(before.length()>0) {
            			String callNameChildTest = before+"."+wordStr;
            			if(callKeySet.contains(callNameChildTest)) {
//            				System.out.println(callNameParent+","+callNameChildTest);
                        	isCallRelate = true;
                        	callNameChild = callNameChildTest;
            			}
            		}
            		before = wordStr;
            		if(isCallRelate) {
                    	if(J72_Tran_Main.isRealTime) {
                    		J1_BeanCallRelate bean = new J1_BeanCallRelate();
                			bean.setCall_name_parent(callNameParent);
                			bean.setCall_name_child(callNameChild);
                			J5_Sql.doMain(114, bean);
                    	}else{
                			callKeySet.add(callNameParent);
                    	}
            		}
            	}
            	if(!wordMatch)
            		wordPre = ch;
            }
            fileReader.close();
            reader.close();
            return "";
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }
    public static boolean isLetter(int i) {
//		'0'	//48
//		'1'	//49
//		'9'	//57
//		':'	//58
//		'@'	//64
//		'A'	//65
//		'Z'	//90
//		'['	//91 
//		'`'	//96 
//		'a'	//97
//		'z'	//122
//		'功'	//21151
//		'能'	//33021
//		'说'	//35828
//		'明'	//26126
    	if((i>47&&i<58)||(i>64&&i<91)||(i>96&&i<123)
    			||21151==i||33021==i||35828==i||26126==i)
    		return true;
    	else
    		return false;
    }
    public static boolean isNotNote(int i) {
//		'/'	//47
//		'*'	//42
    	if(i==47||i==42)
    		return false;
    	else
    		return true;
    }
    public static boolean isNoteBegin(int i) {
//		'/'	//47
    	if(i==47)
    		return true;
    	else
    		return false;
    }
    public static boolean isKeyNoteEnd(int i) {
//    	'<'	//60
//		'*'	//42
    	if(i==60||i==42)
    		return true;
    	else
    		return false;
    }
    public static boolean isNoteEnd(int i) {
//    	'<'	//60
//		'*'	//42
//		'/n'//10
//		'/n'//13
//		' '	//32
    	if(i==60||i==42||i==13||i==32||i==10)
    		return false;
    	else
    		return true;
    }
    public static boolean isMatch(int i) {
//		'('	//40
//		'.'	//46
//		';'	//59
//		'['	//91 
    	if(i==40||i==46||i==59||i==91)
    		return true;
    	else
    		return false;
    }
    public static boolean isSpace(int i) {
//		'/n' 	//9		换行
//		' '		//32	空格
    	if(i==9||i==32)
    		return true;
    	else
    		return false;
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
    public static void searchJsonTest(String[] args) {
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
