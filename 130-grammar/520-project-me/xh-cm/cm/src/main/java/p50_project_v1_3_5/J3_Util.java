package p50_project_v1_3_5;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.alibaba.fastjson.JSONObject;


public class J3_Util {
	public static String DAO = "Dao";
	public static String ID = "id";
	public static String NAME = "name";
	public static String LONG_NAME = "longname";
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
     * 解析java,行读取法
     * @param list
     */
    public static String parseJava(String path,Set<String> callKeySet,Map<String,String> dbMap,Set<J1_BeanCall> callSet,List<J1_BeanCallRelate> callRelateList) {
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
		//方法内解析用参数
		int seq_no = 0;
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
                			seq_no=0;
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
									noteStr = strCache;//.substring(1, strCache.length());
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
				//是否table操作
				if(null!=callRelateList) {
					if(str.contains("Dao")) {
						String[] strArray = str.split("\\.");
						int dao = 0;
						for(int i=0;i<strArray.length;i++) {
							if(strArray[i].endsWith("Dao")) {
								dao = i;
								break;
							}
						}
						String daoStr = clear(strArray[dao]);
						String[] daoStrSpace = daoStr.split(" ");
						daoStr = daoStrSpace[daoStrSpace.length-1];
						String[] daoStrEqual = daoStr.split("=");
						daoStr = daoStrEqual[daoStrEqual.length-1];
						String[] daoStrBrackets = daoStr.split("\\(");
						daoStr = daoStrBrackets[daoStrBrackets.length-1];
						J1_BeanCallRelate bean = new J1_BeanCallRelate();
						bean.setCall_name_child("");
						bean.setCall_des("");
						bean.setIs_return(false);
						bean.setIs_simple(false);
						//判断操作类型
						String operKey = strArray[dao+1].split("\\(")[0];
						boolean isOper = false;
						if(operKey.startsWith("insert")) {
							bean.setTable_oper("I");
							isOper = true;
						}else if(operKey.startsWith("update")) {
							bean.setTable_oper("U");
							isOper = true;
						}else if(operKey.startsWith("select")) {
							bean.setTable_oper("S");
							isOper = true;
						}else if(operKey.startsWith("delete")) {
							bean.setTable_oper("D");
							isOper = true;
						}else if(operKey.startsWith("namedsql_")) {
							seq_no++;
							bean.setCall_name_parent(callNameParent);
							operKey = operKey.split(",")[0];
							bean.setTable_name(daoStr.substring(0,daoStr.length()-3)+"."+operKey.substring(9));
							bean.setCall_type("N");
							bean.setSeq_no(seq_no);
							callRelateList.add(bean);
						}else{
							if(str.contains("import ")) {
								
							}else{
								System.err.println("无法匹配Dao的行:"+str);
							}
						}
						if(isOper) {
							seq_no++;
							bean.setCall_name_parent(callNameParent);
							bean.setCall_type("T");
							bean.setSeq_no(seq_no);
							if(dbMap.containsKey(daoStr)) {
								bean.setTable_name(dbMap.get(daoStr));
							}else{
								if(!(daoStr.startsWith("import")||daoStr.equals("cn"))) {
									bean.setTable_name(daoStr);
//									System.err.println("不存在的Dao:"+daoStr);
								}
							}
							callRelateList.add(bean);
//							System.out.println(dbMap.get(daoStr));
						}
					}
					if(str.contains("DaoUtil.insertBatch")) {
					}
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
    	s = s.replace("/t", "");
    	s = s.trim();
    	return s;
    }
    /**
     * 解析tables,xml解析
     */
    public static void parseTables(String path,Set<String> keySet,Set<J1_BeanDb> dbSet) {
//    	path = "D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\dp-base\\dp-base\\src\\main\\resources\\tables\\TabDpAccountBase.tables.xml";
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    try {
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document d = builder.parse(path);
	        NodeList list = d.getElementsByTagName("table");
	        String idStr = "";
	        String nameStr = "";
	        for (int i = 0; i <list.getLength() ; i++) {
	            Element dbNode = (Element) list.item(i);
                NamedNodeMap attributes = dbNode.getAttributes();
                idStr = attributes.getNamedItem(ID).getNodeValue();
            	if(!keySet.contains(idStr)) {
            		keySet.add(idStr);
            		J1_BeanDb dbBean = new J1_BeanDb();
            		dbBean.setTable_dao(idStr+DAO);
            		nameStr = attributes.getNamedItem(NAME).getNodeValue();
            		dbBean.setTable_name(nameStr);
            		dbBean.setTable_des(attributes.getNamedItem(LONG_NAME).getNodeValue());
            		dbSet.add(dbBean);
                	System.out.println(nameStr);
            	}
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
    }
}
