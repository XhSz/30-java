package p50_project_v1_3_4;

import java.io.BufferedReader;
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
    	String noteStr = "";
        boolean noteKeyMatch = false;	//是否关键字注释匹配-功能说明
        boolean noteMulMatch = false;	//是否多行注释匹配-/**
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
        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader bf = new BufferedReader(fileReader);
            String str;
			while ((str = bf.readLine()) != null) {
				//类,方法,层级解析
				if(!noteMulMatch&&(str.contains("public")||str.contains("private"))) {
					if(str.contains("(")&&!str.contains(";")) {
            			//当前大括号所属层级, 0为Java外层层级,1为java内层级, 2为method层级, >2为方法内部层级
						layer = 2;
                		if(2==layer) {
//                			if(J2_Main.LS)System.out.println(lineSb);
                			String lineStr = str;
                			lineStr = lineStr.replace("    ", " ");
                			lineStr = lineStr.replace("   ", " ");
                			lineStr = lineStr.replace("  ", " ");
                			String[] lineArry = lineStr.split(" ");
                			int judgeKey = 0;
                			for(String js:lineArry) {
                				js = clear(js);
                    			if(	"".equals(js)
                    					||"static".equals(js)
                    					||"public".equals(js)
                    					||"private".equals(js)
                    					||"final".equals(js)) {
                    				judgeKey++;
                    			}else{
                    				break;
                    			}
                			}
                			judgeKey+=1;
                			methodKey = lineArry[judgeKey];
                			if(methodKey.contains("("))
                				methodKey = methodKey.split("\\(")[0];
                			callNameParent = fileKey+"."+methodKey;
                        	J1_BeanCall bean = new J1_BeanCall();
                        	bean.setCall_name(callNameParent);
//                        	if(noteStr.length()>1)
//                        		noteStr = noteStr.substring(0,noteStr.length()-1);
                        	noteStr = noteStr.trim();
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
					if(str.contains("class")) {
						layer = 1;
                		noteStr = "";
					}
				}
				//是否非规范多行注释开启
				if(str.contains("/**")||str.contains("/*")) {
			        noteMulMatch = true;	//是否多行注释匹配-/**
				}
				if(str.contains("*/")) {
			        noteKeyMatch = false;	//是否关键字注释匹配-功能说明
			        noteMulMatch = false;	//是否多行注释匹配-/**
				}
				if(str.contains("}")) {
            		noteStr = "";
				}
				if(noteMulMatch) {
					//keyNote解析
					if(str.contains("功能说明")) {
				        noteKeyMatch = true;	//是否关键字注释匹配-功能说明
//						 *         <li>功能说明负债开户</li>
						 String[] strArry = null;
						 if(str.contains(":"))
							 strArry = str.split(":");
						 if(str.contains("："))
							 strArry = str.split("：");
						 noteStr = strArry[strArry.length-1].split("<")[0];
					}
					if(!noteKeyMatch) {
						String strCache = clear(str);
						if(!strCache.equals("*")) {
							if(!strCache.contains("@")||strCache.contains("@description")) {
								if(isNote(strCache)) {
									//包含方法注释的那一行
									noteStr = strCache.substring(1, strCache.length());
								}
							}
						}
					}
				}
				//是否单行注释开启
				if(str.contains("//")) {
					String strCache = str.trim();
					noteStr = strCache.substring(2, strCache.length());
				}
			}
			bf.close();
            fileReader.close();
            return "";
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }
    public static boolean isNote(String s) {
    	if(s.length()>0&&!s.contains("20"))
    		return true;
    	else
    		return false;
    }
    public static String clear(String s) {
    	/*
        *         <p>
        *         <li>2019年12月12日-下午4:41:11</li>
        *         <li>用户重复注册，更新邮件与名称</li>
        *         </p>
        */
    	s = s.replace("<p>", "");
    	s = s.replace("</p>", "");
    	s = s.replace("<li>", "");
    	s = s.replace("</li>", "");
    	s = s.replace("*", "");
    	s = s.replace("/", "");
    	s = s.trim();
    	return s;
    }
}
