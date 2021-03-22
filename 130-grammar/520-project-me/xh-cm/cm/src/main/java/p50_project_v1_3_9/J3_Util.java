package p50_project_v1_3_9;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.alibaba.fastjson.JSONObject;

import p50_project_v1.J1_BeanBatch;
import p50_project_v1.J1_BeanCall;
import p50_project_v1.J1_BeanCallRelate;
import p50_project_v1.J1_BeanCol;
import p50_project_v1.J1_BeanDb;
import p50_project_v1.J1_BeanMenu;
import p50_project_v1.J1_BeanTran;
import p50_project_v1.J1_BeanTranRelate;


public class J3_Util {
	public static boolean DE = false;
	public static String DAO = "Dao";
	public static String ID = "id";
	public static String TYPE = "type";
	public static String NAME = "name";
	public static String METHOD = "method";
	public static String LONG_NAME = "longname";
	public static String BATCH_JAVA = "DataProcessor.";
	public static String BREAK = "break";
	public static String SPACE = " ";
	public static String SHORT_MIDDLE_LINE = "-";
	public static String SML = SHORT_MIDDLE_LINE;
	public static String COMMA = ",";
	public static String SINGLE_QUOTATION_MARK = "'";
	public static String SQM = SINGLE_QUOTATION_MARK;
	public static String POINT = ".";
	public static String MAX_TABLESPACE_DBOOK = "MAX_TABLESPACE_DBOOK";
	public static String POS_TABLESPACE_DBOOK = "POS_TABLESPACE_DBOOK";
	public static String MAX_TABLE_DBOOK = "MAX_TABLE_DBOOK";
	public static long TIMESTAMP_BEGIN = System.currentTimeMillis();
	public static long TIMESTAMP_END = System.currentTimeMillis();
	public static Map<String,Long> TIMESTAMP_MAP = new HashMap<String,Long>(); 
	public static boolean LAST_ERROR = false;
	public static String LAST_LINE = "";
	//S,select;I,insert;U,update;D,delete;B,batchInsert
	public static Map<String,String> OPER_MAP = new HashMap<String,String>();
	//R,remote service;S,local service;M,method;T,tables;N,namesql;E,error
	public static Map<String,String> CALL_MAP = new HashMap<String,String>();
	//dynamicSelect,S;select,S;update,U;delete,D
	public static Map<String,String> NSQL_XML_TYPE_MAP = new HashMap<String,String>();
	//dynamicSelect,dynamicSql;select,sql;update,sql;delete,sql
	public static Map<String,String> NSQL_XML_STR_MAP = new HashMap<String,String>();
	public static Map<String,StringBuilder> MODULE_PRINT_ONL_MAP = new HashMap<String,StringBuilder>();
	public static Map<String,StringBuilder> MODULE_PRINT_BAT_MAP = new HashMap<String,StringBuilder>();
	public static String[] BATCH_METHOD_ARRAY = {"getBatchDataWalker","getJobBatchDataWalker","process"};
	public static CreationHelper dbBookHelper = null;
	public static XSSFHyperlink dbBookLink = null;
	public static CellStyle linkColStyle = null;
	public static CellStyle commonColStyle = null;
	public static CellStyle desColStyle = null;
	
	static {
		OPER_MAP.put("S", "select");
		OPER_MAP.put("I", "insert");
		OPER_MAP.put("U", "update");
		OPER_MAP.put("D", "delete");
		OPER_MAP.put("B", "batchInsert");
		CALL_MAP.put("R", "remoteSer");
		CALL_MAP.put("S", "localSer");
		CALL_MAP.put("M", "method");
		CALL_MAP.put("T", "table");
		CALL_MAP.put("N", "nsql");
		CALL_MAP.put("E", "error");
		NSQL_XML_TYPE_MAP.put("dynamicSelect", "S");
		NSQL_XML_TYPE_MAP.put("select", "S");
		NSQL_XML_TYPE_MAP.put("update", "U");
		NSQL_XML_TYPE_MAP.put("delete", "D");
		NSQL_XML_STR_MAP.put("dynamicSelect", "dynamicSql");
		NSQL_XML_STR_MAP.put("select", "sql");
		NSQL_XML_STR_MAP.put("update", "sql");
		NSQL_XML_STR_MAP.put("delete", "sql");
	}

    public static StringBuilder space(int n){
        StringBuilder s = new StringBuilder();
        for(int i=0;i<n;i++){
            s.append("\t");
        }
        return s;
    }
    /**
     * get branch result string
     * 得到枝干节点字符串
     */
    public static StringBuilder getBranchSb(J1_BeanMenu f3,Map<String,String> map){
        StringBuilder sb = new StringBuilder();
        sb.append(space(f3.getLevel()))
                .append(f3.getMenuNameZh()).append(":{\n");
        for(int i=0;i<f3.getChildren().size();i++) {
        	J1_BeanMenu childMenu = f3.getChildren().get(i);
//            System.err.println("print:"+f3.getMenuNameZh()+"'s child is "+childMenu.getMenuNameZh()
//            	+",its level is "+childMenu.getLevel());
        	if(childMenu.isLeaf()) {
    			if(i!=0)sb.append("\n");
        		sb.append(getLeafSb(childMenu,map));
        	}
        	else
        		sb.append(getBranchSb(childMenu,map));
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
    public static StringBuilder getLeafSb(J1_BeanMenu f3,Map<String,String> map){
        StringBuilder sb = new StringBuilder();
        if(null!=map)
	        sb.append(space(f3.getLevel()))
	                .append("[").append(f3.getMenuCode()).append("]").append(f3.getMenuNameZh()).append(":{\n")
	                .append(space(f3.getLevel()+1)).append("menu").append(space(1))
	                .append(map.get(f3.getQryTrans())).append(SML).append(f3.getQryTrans()).append(space(1))
	                .append(f3.getMenuJsonPath()).append("\n")
	                .append(space(f3.getLevel())).append("},");
        else {
	        sb.append(space(f3.getLevel()))
	                .append("[").append(f3.getMenuCode()).append("]").append(f3.getMenuNameZh()).append(":{\n")
	                .append(space(f3.getLevel()+1)).append("menu").append(space(1))
	                .append(f3.getQryTransIcore()).append(SML).append(f3.getQryTrans()).append(space(1))
	                .append(f3.getMenuJsonPath());
	        if(J2_MainUnit.tranRelateKeySet.contains(f3.getQryTransIcore())) {
	        	sb.append(":{\n");
	        	getTranSb(sb,J2_MainUnit.tranRelateKeyMap.get(f3.getQryTransIcore()),f3);
	        	sb.append("\n").append(space(f3.getLevel()+1)).append("}\n");
	        }else{
	        	sb.append("\n");
	        }
	        sb.append(space(f3.getLevel())).append("},");
        }
        return sb;
    }
    public static StringBuilder getTranSb(StringBuilder sb,int begin,J1_BeanMenu f3){
    	for(int i=begin;;i++) {
    		if(i>=J2_MainUnit.tranRelateList.size())break;
    		J1_BeanTranRelate bean = J2_MainUnit.tranRelateList.get(i);
    		if(!f3.getQryTransIcore().equals(bean.getTran_name())) {
    			sb.delete(sb.length()-2, sb.length());
    			break;
    		}
    		sb.append(space(f3.getLevel()+2));
    		if(bean.isIs_simple())sb.append("1-");
    		sb.append(bean.getTran_type()).append(SML).append(bean.getCall_name())
    			.append(SML).append(SQM).append(J2_MainUnit.callMap.get(bean.getCall_name())).append(SQM);
    		if(J2_MainUnit.callRelateKeySet.contains(bean.getCall_name())) {
	        	sb.append(":{\n");
	        	getCallSb(sb,J2_MainUnit.callRelateKeyMap.get(bean.getCall_name()),f3.getLevel(),bean.getCall_name());
	        	sb.append("\n").append(space(f3.getLevel()+2)).append("},\n");
//    			J2_MainUnit.callRelateKeySet.remove(bean.getCall_name());
    		}else
    			sb.append(",\n");
    	}
    	return sb;
    }
    public static StringBuilder getOnlSb() {
        StringBuilder sb = new StringBuilder();
        sb.append("onl:{\n");
        final List<String> list = new ArrayList<String>();  
        for(final String value : J2_MainUnit.tranRelateKeySet){  
        	list.add(value);  
        }  
        Collections.sort(list);  
        for(String tranKey:list) {
        	String moduleKey = tranKey.substring(0, 2);
        	if(!MODULE_PRINT_ONL_MAP.containsKey(moduleKey)) {
        		StringBuilder moSb = initModuleMap(moduleKey);
        		MODULE_PRINT_ONL_MAP.put(moduleKey, moSb);
        	}
        	addTran(MODULE_PRINT_ONL_MAP.get(moduleKey),tranKey);
        }
        return endModuleMap(sb,MODULE_PRINT_ONL_MAP);
    }
    public static StringBuilder getBatSb() {
        StringBuilder sb = new StringBuilder();
        sb.append("bat:{\n");
        final List<String> list = new ArrayList<String>();  
        for(final String value : J2_MainUnit.batchMap.keySet()){  
        	list.add(value);  
        }  
        Collections.sort(list);  
        for(String tranKey:list) {
        	String moduleKey = tranKey.substring(0, 2);
        	if(!MODULE_PRINT_BAT_MAP.containsKey(moduleKey)) {
        		StringBuilder moSb = initModuleMap(moduleKey);
        		MODULE_PRINT_BAT_MAP.put(moduleKey, moSb);
        	}
        	addBatch(MODULE_PRINT_BAT_MAP.get(moduleKey),tranKey);
        }
        return endModuleMap(sb,MODULE_PRINT_BAT_MAP);
    }
    public static StringBuilder initModuleMap(String tranKey){
        StringBuilder sb = new StringBuilder();
        sb.append(space(1)).append(tranKey).append(":{\n");
        return sb;
    }
    public static void addBatch(StringBuilder sb,String tranKey){
    	sb.append(space(2)).append(tranKey).append(SML).append(SQM).append(J2_MainUnit.batchMap.get(tranKey)).append(SQM).append(":{\n");
    	for(String method:BATCH_METHOD_ARRAY) {
    		StringBuilder batchJava =  new StringBuilder(tranKey).append(BATCH_JAVA).append(method);
    		String batchJavaStr = batchJava.toString();
			if(J2_MainUnit.callRelateKeySet.contains(batchJavaStr)) {
	        	sb.append(space(3)).append(method).append(":{\n");
	        	getCallSb(sb,J2_MainUnit.callRelateKeyMap.get(batchJavaStr),2,batchJavaStr);
	        	sb.append("\n").append(space(3)).append("},\n");
			}
    	}
    	sb.append(space(2)).append("},\n");
    }
    public static void addTran(StringBuilder sb,String tranKey){
    	sb.append(space(2)).append(tranKey).append(SML).append(SQM).append(J2_MainUnit.tranMap.get(tranKey).replaceAll(SQM, "`")).append(SQM);
		J1_BeanMenu f3 = new J1_BeanMenu();
		f3.setLevel(2);
		f3.setQryTransIcore(tranKey);
        if(J2_MainUnit.tranRelateKeySet.contains(tranKey)) {
        	sb.append(":{\n");
        	getTranSb(sb,J2_MainUnit.tranRelateKeyMap.get(tranKey),f3);
        	sb.append("\n");
        }
        sb.append(space(f3.getLevel())).append("},\n");
    }
    public static StringBuilder endModuleMap(StringBuilder sb,Map<String,StringBuilder> map){
    	Set<String> ks = map.keySet();
    	for(String key : ks) {
    		sb.append(map.get(key).append("\n").append(space(1)).append("},\n"));
    	}
		sb.append("\n}");
        return sb;
    }
    public static StringBuilder getCallSb(StringBuilder sb,int begin,int space,String callNameParent){
    	for(int i=begin;;i++) {
    		if(i>=J2_MainUnit.callRelateList.size())break;
    		J1_BeanCallRelate bean = J2_MainUnit.callRelateList.get(i);
    		if(!callNameParent.equals(bean.getCall_name_parent())) {
    			sb.delete(sb.length()-2, sb.length());
    			break;
    		}
    		sb.append(space(space+3));
    		//R,remote service;S,local service;M,method;T,tables;N,namesql;E,error
    		if(bean.isIs_return())
    			sb.append("return ");
//    		if(bean.isIs_simple())
//    			sb.append("simple").append(SML);
    		String c = bean.getCall_type();
    		sb.append(CALL_MAP.get(c)).append(SML);
    		if("R".equals(c)||"S".equals(c)||"M".equals(c)) {
        		sb.append(bean.getCall_name_child())
    			.append(SML).append(SQM).append(J2_MainUnit.callMap.get(bean.getCall_name_child())).append(SQM);
        		if(space<8&&J2_MainUnit.callRelateKeySet.contains(bean.getCall_name_child())) {
    	        	sb.append(":{\n");
    	        	getCallSb(sb,J2_MainUnit.callRelateKeyMap.get(bean.getCall_name_child()),space+1,bean.getCall_name_child());
    	        	sb.append("\n").append(space(space+3)).append("},\n");
        		}else
        			sb.append(",\n");
    		}else if("E".equals(c)) {
        		sb.append(bean.getCall_name_child())
    			.append(SML).append(SQM).append(J2_MainUnit.callMap.get(bean.getCall_name_child())).append(SQM).append(",\n");
    		}else if("T".equals(c)) {
        		sb.append(bean.getTable_name()).append(SML).append(OPER_MAP.get(bean.getTable_oper()));
    			if(J2_MainUnit.dbBeanMap.containsKey(bean.getTable_name())) {
    				J1_BeanDb dbbean = J2_MainUnit.dbBeanMap.get(bean.getTable_name());
    				sb.append(SML).append(SQM).append(dbbean.getTable_des()).append(SQM);
    			}
				sb.append(",\n");
    		}else if("N".equals(c)) {
    			if(J2_MainUnit.dbBeanMap.containsKey(bean.getTable_name())) {
    				J1_BeanDb dbbean = J2_MainUnit.dbBeanMap.get(bean.getTable_name());
    				StringBuilder tnnsb = new StringBuilder();
    				String[] tnnArray = dbbean.getTable_name_nsql().split(COMMA);
    				for(int m=0;m<tnnArray.length;m++){
    					if(m!=0)tnnsb.append(COMMA);
    					tnnsb.append(tnnArray[m]).append(SML).append(OPER_MAP.get(dbbean.getTable_oper()));
    				}
    				sb.append(dbbean.getTable_name()).append(SML).append(dbbean.getTable_oper()).append(SML)
    					.append(tnnsb).append(SML).append(SQM).append(dbbean.getTable_des()).append(SQM).append(",\n");
    			}else{
    				sb.append(bean.getTable_name()).append(",\n");
    			}
    		}
    	}
    	return sb;
    }
    public static void printToJson(StringBuilder s,String jp) throws FileNotFoundException {
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
    	DE = true;
    	int M = 1352;
		Set<String> callKeySet = new HashSet<String>();
		Set<J1_BeanCall> callSet = new HashSet<J1_BeanCall>();
		Set<String> dbKeySet = new HashSet<String>();
		Set<J1_BeanDb> dbSet = new HashSet<J1_BeanDb>();
		Map<String,String> dbMap = new HashMap();
		List<J1_BeanCallRelate> callRelateList = new ArrayList<J1_BeanCallRelate>();
    	if(M==1352) {
    		parseTables("", new HashSet<String>(), new HashSet<J1_BeanDb>());
			try {
				OutputStream fileOut = new FileOutputStream(J2_MainUnit.TABLE_PATH);
		        J2_MainUnit.dbBook.write(fileOut);J2_MainUnit.dbBook.getSheetAt(3).getRow(4);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}else if(M==1353) {
    		parseNSQL("", new HashSet<String>(), dbSet); 
    		print(dbSet);
    	}else if(M==1343) {
			J5_Sql.doMain(343, callKeySet);//343,select all,call_name
			J5_Sql.doMain(345, dbMap);//345,select all,tree_call_db
    		parseJava("",callKeySet,dbMap,null,callRelateList);
    		print(callRelateList);
    	}else if(M==1354) {
			J5_Sql.doMain(343, callKeySet);//343,select all,call_name
			J5_Sql.doMain(345, dbMap);//345,select all,tree_call_db

			J5_Sql.doMain(344, J2_MainUnit.callRelateList);//344,select all,tree_call_db
    		for(int i=0;i<J2_MainUnit.callRelateList.size();i++) {
    			J1_BeanCallRelate bean =  J2_MainUnit.callRelateList.get(i);
    			if(!J2_MainUnit.callRelateKeySet.contains(bean.getCall_name_parent())) {
    				J2_MainUnit.callRelateKeySet.add(bean.getCall_name_parent());
    				J2_MainUnit.callRelateKeyMap.put(bean.getCall_name_parent(), i);
    			}
    		}
    		parseJava("",callKeySet,dbMap,null,callRelateList);
    		print(callRelateList);
    	}
    }
    /**
     * 解析java,行读取法
     * @param list
     */
    public static String parseJava(String path,Set<String> callKeySet,Map<String,String> dbMap,Set<J1_BeanCall> callSet,List<J1_BeanCallRelate> callRelateList) {
    	if(DE) {
    		path = 
//        			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\dp-base\\dp-base\\src\\main\\java\\cn\\sunline\\icore\\dp\\base\\interest\\afresh\\DpSlipAfreshAdvance.java"
//        			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\us-busi\\us-serv\\src\\main\\java\\cn\\sunline\\icore\\us\\serv\\verify\\UsVerify.java"
//    				"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\us-busi\\us-serv\\src\\main\\java\\cn\\sunline\\icore\\us\\serv\\base\\UsLakuPandaiQuery.java"
//    				"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\dp-busi\\dp-serv\\src\\main\\java\\cn\\sunline\\icore\\dp\\serv\\settle\\DpSettleVoucherMaintain.java"
//    				"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\dp-busi\\dp-serv\\src\\main\\java\\cn\\sunline\\icore\\dp\\serv\\maintain\\DpCardAccountRelate.java"
//    				"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\dp-base\\dp-base\\src\\main\\java\\cn\\sunline\\icore\\dp\\base\\interest\\account\\DpHistInterestRate.java"
//    				"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\dp-busi\\dp-batch\\src\\main\\java\\cn\\sunline\\icore\\dp\\batch\\dayend\\dp32DataProcessor.java"
//    				"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\us-busi\\us-serv\\src\\main\\java\\cn\\sunline\\icore\\us\\serv\\base\\UsBatchAccounOpen.java"
//    				"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-serv\\src\\main\\java\\cn\\sunline\\icore\\cf\\serv\\base\\CfCustInfoQuery.java"
//    				"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-serv\\src\\main\\java\\cn\\sunline\\icore\\cf\\serv\\base\\CfAgreeSignMnt.java"
//    				"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\us-busi\\us-serv\\src\\main\\java\\cn\\sunline\\icore\\us\\serv\\util\\UsUtils.java"
//    				"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\us-busi\\us-tran\\src\\main\\java\\cn\\sunline\\icore\\us\\tran\\base\\us3080.java"
//        			"D:\\03-sl-107-code\\26-gs\\201106\\cm-busi\\cm-serv\\src\\main\\java\\cn\\sunline\\icore\\cm\\serv\\serviceimpl\\chrg\\SrvIoCmChrgImpl.java"
//    				"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-batch\\src\\main\\java\\cn\\sunline\\icore\\cf\\batch\\cf02DataProcessor.java"
//    				"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-batch\\src\\main\\java\\cn\\sunline\\icore\\cf\\batch\\cf07DataProcessor.java"
//    				"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\dp-busi\\dp-batch\\src\\main\\java\\cn\\sunline\\icore\\dp\\batch\\dayend\\dp01DataProcessor.java"
    				"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\dp-busi\\dp-serv\\src\\main\\java\\cn\\sunline\\icore\\dp\\serv\\account\\draw\\DpDemandDrawCheck.java"
    				;
        	System.out.println(path);
    	}
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
        boolean isReturn = false;
		List<J1_BeanCallRelate> serRelateList = new ArrayList<J1_BeanCallRelate>();
        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            BufferedReader bf = new BufferedReader(fileReader);
            String str;
			while ((str = bf.readLine()) != null) {
		        try {
		    		if(LAST_ERROR) {
		    			str = LAST_LINE.trim()+str.trim();
		    			LAST_ERROR = false;
		    		}else{
			        	isReturn = false;
		    		}
					//类,方法,层级解析
					if(!noteMulMatch&&(str.contains("public ")||str.contains("private "))) {
						if(str.contains("(")&&!str.contains(";")) {
	            			//当前大括号所属层级, 0为Java外层层级,1为java内层级, 2为method层级, >2为方法内部层级
							layer = 2;
	                		if(2==layer) {
	//                			if(J2_Main.J2_Main.LS)System.out.println(lineSb);
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
	                        	if(DE)System.err.println(callNameParent+COMMA+noteStr);
	                    		noteStr = "";
	                    		serRelateList = new ArrayList<J1_BeanCallRelate>();
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
										noteStr = strCache.replace("@description", "");//.substring(1, strCache.length());
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
					if(null!=callRelateList) {
						if(str.contains("return ")) {
							isReturn = true;
						}
						seq_no = isMethod(fileKey,callNameParent,seq_no,str,callKeySet,callRelateList,isReturn);
//						if(str.contains(".")) {
//						}
//						seq_no = isSelfMethod(fileKey,callNameParent,seq_no,str,callKeySet,callRelateList,isReturn);
						if(serRelateList.size()>0) {
							for(J1_BeanCallRelate bean:serRelateList) {
								if(str.contains(bean.getTable_name()+".")) {
									String ser = bean.getCall_name_child()+"."+str.split(bean.getTable_name()+".")[1].split("\\(")[0];
									if(callKeySet.contains(ser)) {
										seq_no++;
										bean.setCall_name_child(ser);
										bean.setSeq_no(seq_no);
										bean.setTable_name(null);
										callRelateList.add(bean);
									}else{
										if("R".equals(bean.getCall_type())) {
											seq_no++;
											bean.setCall_type("E");
											bean.setCall_name_child(ser);
											bean.setSeq_no(seq_no);
											bean.setTable_name(null);
											callRelateList.add(bean);
											System.err.println("无法匹配的多行R服务："+ser);
										}
									}
								}
							}
						}
						if(str.contains("getRemoteInstance")||str.contains("getInstance")) {
							String[] insArray = null;
							J1_BeanCallRelate bean = new J1_BeanCallRelate();
							bean.setCall_name_parent(callNameParent);
							if(str.contains("getRemoteInstance")) {
								bean.setCall_type("R");
								insArray = str.split("getRemoteInstance");
							}else if(str.contains("getInstance")) {
								bean.setCall_type("S");
								insArray = str.split("getInstance");
							}
//							ComIoUsUser.qryPushDemandInfoOut qryPushDemandInfoOut = SysUtil.getRemoteInstance(SrvIoUsUser.class).qryPushDemandInfo(qryPushDemandInfoIn);
							String[] classArray = insArray[1].split(".class");
							String servName = classArray[0].substring(1);
							String serMethod = ""; 
							boolean isOne = false;
							if(classArray.length>1) {
								if(classArray[1].startsWith(");")){
	//SrvIoCfCustomerInfo customerInfo = SysUtil.getRemoteInstance(SrvIoCfCustomerInfo.class);
									//分步处理
									String[] equalArry = insArray[0].split("=")[0].split(" ");
									for(int i=equalArry.length-1;i>0;i--) {
										if(equalArry[i].length()>0) {
											bean.setCall_name_child(servName);
											bean.setTable_name(equalArry[i]);
											serRelateList.add(bean);
											break;
										}
									}
								}else if(classArray[1].startsWith(").")){
									//一步处理
									serMethod = classArray[1].split("\\(")[0].substring(2);
									isOne = true;
								}
								if(isOne) {
									String ser = classArray[0].substring(1)+"."+serMethod;
									if(callKeySet.contains(ser)) {
										seq_no++;
										bean.setCall_name_child(ser);
										bean.setSeq_no(seq_no);
										callRelateList.add(bean);
									}else{
										System.err.println("无法匹配的单行服务："+ser);
										if(0==serMethod.length()) {
											LAST_LINE = str;
											LAST_ERROR = true;
										}else{
											seq_no++;
											bean.setCall_type("E");
											bean.setCall_name_child(ser);
											bean.setSeq_no(seq_no);
											bean.setTable_name(null);
											callRelateList.add(bean);
										}
									}
								}
							}
						}
						if(str.contains(DAO)) {
							//判断是否数据库操作
							seq_no = isDao(path,callNameParent,seq_no,str,dbMap,callRelateList);
						}
					}
				} catch (Exception e) {
					if(!BREAK.equals(e.getMessage()))
						e.printStackTrace();
					continue;
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
    //判断是否注释信息
    public static String isNote() throws Exception {
    	return "";
    }
    //判断是否调用其他方法
    public static int isMethod(String fileKey,String callNameParent,int seq_no,String str
    		,Set<String> callKeySet,List<J1_BeanCallRelate> callRelateList,boolean isReturn){
		List<String> callNameChildList = new ArrayList<String>();
		String callKey = "";
		//
		List<String> inList = split(str);//拆分获得所有关键字
		for(int i=0;i<inList.size();i++) {
			if(inList.get(i).equals("checkDrawCtrl"))
				System.err.println();
			if(i>0) {
				callKey = inList.get(i-1)+"."+inList.get(i);
				if(callKeySet.contains(callKey)) {
					callNameChildList.add(callKey);
				}
			}
			callKey = fileKey+"."+inList.get(i);
			if(callKeySet.contains(callKey)&&str.contains(inList.get(i)+"(")
					&&(!callNameParent.equals(callKey))) {
				callNameChildList.add(callKey);
			}
		}
		if(callNameChildList.size()>0) {
//			if(DE)print(callNameChildList);
			for(String callNameChild:callNameChildList) {
				seq_no++;
				J1_BeanCallRelate bean = new J1_BeanCallRelate();
				bean.setCall_name_parent(callNameParent);
				bean.setCall_name_child(callNameChild);
				bean.setCall_type("M");
				bean.setSeq_no(seq_no);
				bean.setIs_return(isReturn);
				if(isReturn&&1==seq_no)
					bean.setIs_simple(true);
				callRelateList.add(bean);
			}
		}
		return seq_no;
    }
    //判断是否数据库操作
    public static int isDao(String path,String callNameParent,int seq_no,String str
    		,Map<String,String> dbMap,List<J1_BeanCallRelate> callRelateList) throws Exception {
    	String[] strArray = str.split("\\.");
		int dao = -1;
		for(int i=0;i<strArray.length;i++) {
			if(strArray[i].endsWith("Dao")||strArray[i].endsWith("DaoUtil")) {
				dao = i;
				break;
			}
		}
		if(-1==dao)
			throw new Exception(BREAK);
		String daoStr = clear(strArray[dao]);
		String[] daoStrTab = daoStr.split("	");//tab
		daoStr = daoStrTab[daoStrTab.length-1];
		String[] daoStrSpace = daoStr.split(" ");
		daoStr = daoStrSpace[daoStrSpace.length-1];
		String[] daoStrEqual = daoStr.split("=");
		daoStr = daoStrEqual[daoStrEqual.length-1];
		String[] daoStrBrackets = daoStr.split("\\(");
		daoStr = daoStrBrackets[daoStrBrackets.length-1];
		String[] daoStrComma = daoStr.split(COMMA);
		daoStr = daoStrComma[daoStrComma.length-1];
		J1_BeanCallRelate bean = new J1_BeanCallRelate();
		bean.setCall_name_child("");
		bean.setCall_des("");
		bean.setIs_return(false);
		bean.setIs_simple(false);
		//判断操作类型
		String operKey = "";
		try{
			if(strArray.length>1)
				operKey = splitClearBegin(strArray[dao+1]);
			else
				throw new Exception(BREAK);
		}catch(Exception e){
			System.err.println(e);
			System.err.println("出错文件："+path);
			System.err.println("出错行："+str);
			LAST_ERROR = true;
			LAST_LINE = str;
			throw new Exception(BREAK);
		}
		boolean isOper = false;
		boolean isNsql = false;
		if(operKey.startsWith("insertBatch")) {
			bean.setTable_oper("B");
			isOper = true;
			String[] classStr = str.split(".class")[0].split("\\(");
			daoStr = classStr[classStr.length-1]+DAO;
		}else if(operKey.startsWith("insert")) {
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
			daoStr = daoStr.substring(0,daoStr.length()-3)+"."+operKey.substring(9);
			isNsql = true;
		}else{
			if(str.contains("import ")) {
				
			}else{
				String nsqlMethod = strArray[dao+1];
				nsqlMethod = nsqlMethod.split("\\(")[0];
				if("DaoUtil".equals(daoStr)) {
					if(!"insertBatch".equals(nsqlMethod)) {
						throw new Exception(BREAK);
					}
				}else{
					daoStr = daoStr.substring(0,daoStr.length()-3)+"."+nsqlMethod;
					if(dbMap.containsKey(daoStr)) {
						isNsql = true;
					}else{
						System.err.println("无法匹配Dao的行:"+str);
						throw new Exception(BREAK);
					}
				}
			}
		}
		if(isNsql) {
			seq_no++;
			bean.setCall_name_parent(callNameParent);
			operKey = operKey.split(COMMA)[0];
			bean.setTable_name(daoStr);
			bean.setCall_type("N");
			bean.setSeq_no(seq_no);
			callRelateList.add(bean);
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
//					System.err.println("不存在的Dao:"+daoStr);
				}
			}
			callRelateList.add(bean);
//			System.out.println(dbMap.get(daoStr));
		}
		return seq_no;
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
    public static String splitClear(String s) {
		String daoStr = clear(s);
		String[] daoStrSpace = daoStr.split(" ");
		daoStr = daoStrSpace[daoStrSpace.length-1];
		String[] daoStrEqual = daoStr.split("=");
		daoStr = daoStrEqual[daoStrEqual.length-1];
		String[] daoStrBrackets = daoStr.split("\\(");
		daoStr = daoStrBrackets[daoStrBrackets.length-1];
		String[] daoStrComma = daoStr.split(COMMA);
		daoStr = daoStrComma[daoStrComma.length-1];
		return daoStr;
    }
    public static String splitClearBegin(String s) {
		String daoStr = clear(s);
		String[] daoStrSpace = daoStr.split(" ");
		daoStr = daoStrSpace[0];
		String[] daoStrEqual = daoStr.split("=");
		daoStr = daoStrEqual[0];
		String[] daoStrBrackets = daoStr.split("\\(");
		daoStr = daoStrBrackets[0];
		String[] daoStrComma = daoStr.split(COMMA);
		daoStr = daoStrComma[0];
		return daoStr;
    }
    public static List<String> split(String s) {
    	List<String> i = new ArrayList<String>();
    	i.add(quotClear(s));
    	List<String> r = split(i,"\\.");
    	r = split(r," ");
    	r = split(r,"=");
    	r = split(r,"\\(");
    	r = split(r,"\\)");
    	return r;
    }
    public static List<String> split(List<String> s,String regex) {
    	List<String> r = new ArrayList<String>();
    	for(String str:s) {
	    	for(String childStr:str.split(regex)) {
	    		r.add(clear(childStr));
	    	}
    	}
    	return r;
    }
    public static String quotClear(String s) {
    	while(s.contains("\"")){
    		int b = s.indexOf("\"");
    		String cacheStr = s.substring(b+1);
    		int e = cacheStr.indexOf("\"");
    		s = s.substring(0, b)+s.substring(e+b+2, s.length());
    	}
    	return s;
    }
    /**
     * 解析tables,xml解析
     */
    public static void parseTables(String path,Set<String> keySet,Set<J1_BeanDb> dbSet) {
    	if(DE) {
    		path = 
//    				"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\dp-base\\dp-base\\src\\main\\resources\\tables\\TabDpAccountBase.tables.xml"
    				"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-serv\\src\\main\\resources\\tables\\TabCfCustOther.tables.xml"
    				;
    		System.out.println(path);
    		J3_Util.initDBBook();
    	}
    	//获得文件名
        String[] pathArry = path.split("\\\\");
        String fileName = pathArry[pathArry.length-1];
    	String fileKey = fileName.substring(0,fileName.length()-11);
    	addDBBookTableSpace(fileKey);
    	//解析xml
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
            		if(idStr.contains("_"))
            			idStr = idStr.substring(0, 1).toUpperCase()+idStr.substring(1);
            		idStr = idStr+DAO;
            		dbBean.setTable_dao(idStr);
            		nameStr = attributes.getNamedItem(NAME).getNodeValue();
            		dbBean.setTable_name(nameStr);
            		dbBean.setTable_des(attributes.getNamedItem(LONG_NAME).getNodeValue());
            		dbBean.setTable_type("T");
            		dbSet.add(dbBean);
            		if(J3_Util.DE)System.out.println(nameStr+COMMA+idStr);
            		addDBBookTable(nameStr,dbNode);
            	}
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
    }
    /**
     * 解析nsql,xml解析
     */
    public static void parseNSQL(String path,Set<String> keySet,Set<J1_BeanDb> dbSet) {
    	if(DE) {
    		path = 
//    		    	"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-serv\\src\\main\\resources\\namedsql\\CfCustomerQuerySing.nsql.xml"
//    		    	"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\dp-busi\\dp-serv\\src\\main\\resources\\namedsql\\batch\\SqlDpDayEnd.nsql.xml"
//    				"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-serv\\src\\main\\resources\\namedsql\\CfCustomerBatch.nsql.xml"
//    		"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\us-busi\\us-serv\\src\\main\\resources\\namedsql\\UsUserBase.nsql2.xml"
//    				"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-serv\\src\\main\\resources\\namedsql\\CfCustomerBatch.nsql2.xml"
    				"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-serv\\src\\main\\resources\\namedsql\\CfCustomerBase.nsql2.xml"
    				;
    		System.out.println(path);
    	}
    	//获得文件名
        String[] pathArry = path.split("\\\\");
        String fileName = pathArry[pathArry.length-1];
    	String fileKey = fileName.substring(0,fileName.length()-9);
		//解析文件
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    try {
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document d = builder.parse(path);
	        addDB(fileKey,d,"dynamicSelect",keySet,dbSet);
	        addDB(fileKey,d,"select",keySet,dbSet);
	        addDB(fileKey,d,"update",keySet,dbSet);
	        addDB(fileKey,d,"delete",keySet,dbSet);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
    }
	public static void addDB(String fileKey,Document d,String xmlKey,Set<String> keySet,Set<J1_BeanDb> dbSet) {
    	NodeList list = d.getElementsByTagName(xmlKey);
        String idStr = "";
        for (int i = 0; i <list.getLength() ; i++) {
            Element dbNode = (Element) list.item(i);
            NamedNodeMap attributes = dbNode.getAttributes();
            idStr = attributes.getNamedItem(ID).getNodeValue();
            idStr = fileKey+"."+idStr;
        	if(!keySet.contains(idStr)) {
        		keySet.add(idStr);
        		J1_BeanDb dbBean = new J1_BeanDb();
        		dbBean.setTable_name(idStr);
        		dbBean.setTable_dao(idStr);
        		dbBean.setTable_type("N");
        		dbBean.setTable_oper(NSQL_XML_TYPE_MAP.get(xmlKey));
        		Node longName = attributes.getNamedItem(LONG_NAME);
        		if(null!=longName)
        			dbBean.setTable_des(longName.getNodeValue());
        		//获得sql语句，并解析获得表
        		initNsqlTable(dbNode,dbBean,xmlKey);
        		dbSet.add(dbBean);
            	if(DE)System.out.println(idStr);
        	}
        }
    }
    public static void initNsqlTable(Element dbNode,J1_BeanDb dbBean,String xmlKey) {
    	//获得sql语句
		String sqlStr = "";
		NodeList sqlList = dbNode.getElementsByTagName(NSQL_XML_STR_MAP.get(xmlKey));
		Element mySqlBean = null;
		if("dynamicSelect".equals(xmlKey)) {
	        for (int j = 0; j <sqlList.getLength() ; j++) {
	        	mySqlBean = (Element) sqlList.item(j);
		        Node typeNode = mySqlBean.getAttributes().getNamedItem("type");
	            if(typeNode!=null&&"mysql".equals(typeNode.getNodeValue())) {
	            	break;
	            }
	        }
	        NodeList strList = mySqlBean.getElementsByTagName("str").item(0).getChildNodes();
	        for (int j = 0; j <strList.getLength() ; j++) {
	        	Node strBean =  (Node)strList.item(j);
	        	sqlStr = strBean.getNodeValue();
	        }
		}else{
	        for (int j = 0; j <sqlList.getLength() ; j++) {
	        	mySqlBean = (Element) sqlList.item(j);
		        NodeList strList = mySqlBean.getChildNodes();
		        for (int k = 0; k <strList.getLength() ; k++) {
		        	Node strBean =  (Node)strList.item(k);
		        	try {
		        		sqlStr = strBean.getNodeValue();
		        	}catch(Exception e){
		        		e.printStackTrace();
		        	}
		        }
		        Node typeNode = mySqlBean.getAttributes().getNamedItem("type");
	            if(typeNode!=null&&"mysql".equals(typeNode.getNodeValue())) {
	            	break;
	            }
	        }
		}
		sqlStr = sqlStr.toLowerCase();
        if(DE)System.err.println(sqlStr); 
		try {
		}catch(Exception e) {
			e.printStackTrace();
		}
        //获得sql语句，并解析获得表
        List<String> tableList = new ArrayList<String>(); 
        if(dbBean.getTable_oper().equals("S")||dbBean.getTable_oper().equals("D")) {
        	String[] joinArray = sqlStr.split("join");
        	String[] fromArray = joinArray[0].split("from");
        	initTableList(fromArray[1],tableList);
        	if(joinArray.length>1) {
            	for(int l=1;l<joinArray.length;l++) {
                	initTableList(joinArray[l],tableList);
            	}
        	}
        }else if(dbBean.getTable_oper().equals("U")) {
        	String[] ddlArray = sqlStr.split(OPER_MAP.get(dbBean.getTable_oper()));
        	initTableList(ddlArray[1],tableList);
        }
        //拼接塞入table_name_nsql
        StringBuilder tableNameNsql = new StringBuilder();
    	for(int l=0;l<tableList.size();l++) {
    		if(l!=0)tableNameNsql.append(COMMA);
    		tableNameNsql.append(tableList.get(l));
    	}
    	dbBean.setTable_name_nsql(tableNameNsql.toString());
        print(tableList);
    }
    public static void initTableList(String str,List<String> tableList) {
    	String[] tableArray = str.split(SPACE);
    	for(String tableStr:tableArray) {
    		if(!"".equals(tableStr.trim())) {
            	tableList.add(tableStr.trim());
            	break;
    		}
    	}
    }
    public static void logB() {
        TIMESTAMP_BEGIN=System.currentTimeMillis();
    }
    public static void logB(String key) {
        TIMESTAMP_MAP.put(key, System.currentTimeMillis());
    }
    public static void logE(String key) {
    	logE(key,1);
    }
    public static void logE(String key,int level) {
    	String out = "";
    	if(TIMESTAMP_MAP.containsKey(key)) {
    		long now = System.currentTimeMillis();
    			out = key+"..耗时:"+J3_Util.longToTime(now-TIMESTAMP_MAP.get(key));
            TIMESTAMP_MAP.put(key, now);
    	}else{
	        TIMESTAMP_END=System.currentTimeMillis();
    		out = key+"..耗时:"+J3_Util.longToTime(TIMESTAMP_END-TIMESTAMP_BEGIN);
	        TIMESTAMP_BEGIN=TIMESTAMP_END;
    	}
		if(J2_Main.LS) {
			if(1==level)System.out.println(out);
			else if(2==level)System.err.println(out);
		}
    }
    public static void printDebug(J1_BeanMenu f3){
        for(J1_BeanMenu childMenu : f3.getChildren()) {
            System.err.println("print:"+f3.getMenuNameZh()+"'s child is "+childMenu.getMenuNameZh());
            printDebug(childMenu);
        }
    }
	public static void print(Object obj) {
    	if(!DE)return;
		if(obj instanceof J1_BeanTran) {
			J1_BeanTran b = (J1_BeanTran)obj;
    		System.out.println(
    			b.getTran_name()+COMMA+b.getTran_des()
    		);
    	}else if(obj instanceof J1_BeanCallRelate) {
    		J1_BeanCallRelate b = (J1_BeanCallRelate)obj;
    		System.out.println(
    			b.getCall_name_parent()+COMMA+b.getSeq_no()+COMMA+b.getCall_type()+COMMA+b.getCall_name_child()
    			+COMMA+b.getTable_name()+COMMA+b.getTable_oper()
    		);
    	}else if(obj instanceof J1_BeanDb) {
    		J1_BeanDb b = (J1_BeanDb)obj;
    		System.out.println(
    			b.getTable_name()+COMMA+b.getTable_type()+COMMA+b.getTable_dao()+COMMA+b.getTable_oper()
    			+COMMA+b.getTable_name_nsql()+COMMA+b.getTable_des()
    		);
    	}else if(obj instanceof J1_BeanBatch) {
    		J1_BeanBatch b = (J1_BeanBatch)obj;
    		System.out.println(
    			b.tran_name+COMMA+b.getTran_des()
    		);
    	}else if(obj instanceof String) {
    		System.out.println(obj);
    	}
    }
    @SuppressWarnings("rawtypes")
	public static void print(Collection co) {
    	if(!DE)return;
    	for(Object obj:co) {
    		print(obj);
    	}
    }
    public static void createTranJson(Map<String,String> map){
    	JSONObject jo =  new JSONObject();
    	Set<String> ks = map.keySet();
    	for(String k : ks) {
            J1_BeanMenu beanMenu = new J1_BeanMenu();
            beanMenu.setQryTrans(k);
            beanMenu.setQryTransIcore(map.get(k));
        	jo.put(beanMenu.getQryTrans(), beanMenu.getQryTransIcore());
    	}
    	try {
			J3_Util.printToJson(new StringBuilder(jo.toJSONString()),J2_Main.TRAN_JSON_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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

    public static void initDBBook() {
		try {
			J2_MainUnit.dbBook = WorkbookFactory.create(new FileInputStream(J2_MainUnit.TABLE_MODEL_PATH));
            dbBookHelper = J2_MainUnit.dbBook.getCreationHelper();
//            dbBookLink = (XSSFHyperlink) dbBookHelper.createHyperlink(HyperlinkType.DOCUMENT);
            linkColStyle = J2_MainUnit.dbBook.createCellStyle();
            Font font = J2_MainUnit.dbBook.createFont();
            font.setUnderline(XSSFFont.U_DOUBLE);
            font.setColor(IndexedColors.BLUE.getIndex());
            linkColStyle.setFont(font);
            commonColStyle = J2_MainUnit.dbBook.createCellStyle();
            commonColStyle.setAlignment(HorizontalAlignment.CENTER);//设置水平对齐的样式为居中对齐; 
            commonColStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置垂直对齐的样式为居中对齐; 
            desColStyle = J2_MainUnit.dbBook.createCellStyle();
            desColStyle.setAlignment(HorizontalAlignment.CENTER);//设置水平对齐的样式为居中对齐; 
            desColStyle.setVerticalAlignment(VerticalAlignment.CENTER);//设置垂直对齐的样式为居中对齐; 
            Font desFont = J2_MainUnit.dbBook.createFont();
            desFont.setColor(IndexedColors.BLUE_GREY.getIndex());
            desColStyle.setFont(desFont);
        	J2_MainUnit.dbBookMap.put(MAX_TABLESPACE_DBOOK, -1);
        	J2_MainUnit.dbBookMap.put(POS_TABLESPACE_DBOOK, -1);
	        J2_MainUnit.dbBookMap.put(MAX_TABLE_DBOOK,0);
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
		}
    }
    public static void setLink(Cell cell,String goal) {
    	cell.setCellStyle(linkColStyle);
    	XSSFHyperlink dbBookLink = (XSSFHyperlink) dbBookHelper.createHyperlink(HyperlinkType.DOCUMENT);
    	dbBookLink.setAddress(goal);
        cell.setHyperlink(dbBookLink);
    }
    public static void setDesCol(Cell cell) {
    	cell.setCellStyle(desColStyle);
    }
    public static void sc(Cell cell) {
    	setCommonCol(cell);
    }
    public static void setCommonCol(Cell cell) {
    	cell.setCellStyle(commonColStyle);
    }
    public static void addDBBookTableSpace(String tableSpace) {
		try {
			int posSpace = J2_MainUnit.dbBookMap.get(POS_TABLESPACE_DBOOK)+1;
			int maxSpace = J2_MainUnit.dbBookMap.get(MAX_TABLESPACE_DBOOK)+1;
			int maxTable = J2_MainUnit.dbBookMap.get(MAX_TABLE_DBOOK);
	        Sheet sheet = J2_MainUnit.dbBook.getSheetAt(0);//获取第一个工作表信息
	        //添加tablespace
	        Row row = sheet.getRow(posSpace);if (row == null)row = sheet.createRow(posSpace);
	        Cell cell0 = row.getCell(0);if (cell0 == null)cell0 = row.createCell(0);cell0.setCellValue(integerToString(maxSpace));
	        Cell cell1 = row.getCell(1);if (cell1 == null)cell1 = row.createCell(1);cell1.setCellValue(tableSpace);
			//更新map
	        J2_MainUnit.dbBookMap.put(POS_TABLESPACE_DBOOK,posSpace);
        	J2_MainUnit.dbBookMap.put(MAX_TABLESPACE_DBOOK, maxSpace);
	        J2_MainUnit.dbBookMap.put(MAX_TABLE_DBOOK,0);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public static void addDBBookTable(String tableName,Element dbNode) {
		try {
			//00增加注释
			int posSpace = J2_MainUnit.dbBookMap.get(POS_TABLESPACE_DBOOK);
			int maxSpace = J2_MainUnit.dbBookMap.get(MAX_TABLESPACE_DBOOK);
			int maxTable = J2_MainUnit.dbBookMap.get(MAX_TABLE_DBOOK);
	        Sheet sheet = J2_MainUnit.dbBook.getSheetAt(0);//获取第一个工作表信息
	        Row row = sheet.getRow(posSpace+1);if (row == null)row = sheet.createRow(posSpace+1);
	        Cell cell2 = row.getCell(2);if (cell2 == null)cell2 = row.createCell(2);cell2.setCellValue(integerToString(maxTable));
	        Cell cell3 = row.getCell(3);if (cell3 == null)cell3 = row.createCell(3);cell3.setCellValue(tableName);
	        String sheetName = integerToString(maxSpace)+integerToString(maxTable);
	        //添加超链接
	        setLink(cell3,sheetName+"!B4");
	        //增加sheet
	        J2_MainUnit.dbBook.cloneSheet(1);
	        J2_MainUnit.dbBook.setSheetName(posSpace+2, sheetName);
	        //参数递增
	        J2_MainUnit.dbBookMap.put(MAX_TABLE_DBOOK,maxTable+1);
	        J2_MainUnit.dbBookMap.put(POS_TABLESPACE_DBOOK,posSpace+1);
	        setDBBookTable(posSpace+2,sheetName,tableName,dbNode);
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public static void setDBBookTable(int posSpace,String sheetName,String tableName,Element dbNode) {
    	//初始化J2_MainUnit.colMapList
        NodeList list = dbNode.getElementsByTagName("field");
        String idStr = "";
        for (int i = 0; i <list.getLength() ; i++) {
            Element fieldNode = (Element) list.item(i);
            NamedNodeMap attributes = fieldNode.getAttributes();
            idStr = attributes.getNamedItem(ID).getNodeValue();
            J1_BeanCol colBean = new J1_BeanCol();
            int colType = 0;
            if(attributes.getNamedItem(TYPE).getNodeValue().endsWith("U_INTEGE"))colType=1;
            colBean.setCol_name(idStr);
            colBean.setCol_type(colType);
            Node desNode = attributes.getNamedItem(LONG_NAME);
            if(null!=desNode)colBean.setCol_des(desNode.getNodeValue());
            J2_MainUnit.colMapList.add(colBean);
        	if(J3_Util.DE)System.out.println(tableName+COMMA+idStr);
        }
    	//写入excel
        Sheet sheet = J2_MainUnit.dbBook.getSheet(sheetName);
        //写入excel - 返回
        Row row0 = sheet.getRow(0);if (row0 == null)row0 = sheet.createRow(0);
        Cell cell0 = row0.getCell(0);if (cell0 == null)cell0 = row0.createCell(0);cell0.setCellValue("00");
        setLink(cell0,"00"+"!D"+posSpace);
        //写入excel - head
        Row row1 = sheet.getRow(1);if (row1 == null)row1 = sheet.createRow(1);
        for(int j=0;j<J2_MainUnit.colMapList.size();j++) {
            Cell cell = row0.getCell(j+1);if (cell == null)cell = row0.createCell(j+1);
            sc(cell);
            cell.setCellValue(J2_MainUnit.colMapList.get(j).getCol_name());
            Cell cellDes = row1.getCell(j+1);if (cellDes == null)cellDes = row1.createCell(j+1);
            setDesCol(cellDes);
            cellDes.setCellValue(J2_MainUnit.colMapList.get(j).getCol_des());
        }
        //写入excel - body
        Object result = J5_Sql.doMain(340,tableName,3);
        if(result instanceof List) {
	        List<Map<String,String>> resultList = (List<Map<String,String>>)result;
	        for(int i=0;i<resultList.size();i++) {
	        	int rowInt = i+3;
	            Row row = sheet.getRow(rowInt);if (row == null)row = sheet.createRow(rowInt);
	            Map<String,String> map = resultList.get(i);
	            for(int j=0;j<J2_MainUnit.colMapList.size();j++) {
	                Cell cell = row.getCell(j+1);if (cell == null)cell = row.createCell(j+1);
	                sc(cell);
	                cell.setCellValue(map.get(J2_MainUnit.colMapList.get(j).getCol_name()));
	            }
	        }
	        setAutoWidth(sheet,J2_MainUnit.colMapList.size()+1);
        }
	    J2_MainUnit.colMapList = new ArrayList<J1_BeanCol>();
    }
    public static String integerToString(int i) {
    	StringBuilder sb = new StringBuilder();
    	if(i<10)sb.append("0");
    	sb.append(i);
    	return sb.toString();
    }
    public static void setAutoWidth(Sheet sheet,int maxColumn) {
    	//列宽自适应，只对英文和数字有效  
        for (int i = 0; i <= maxColumn; i++)  
        {  
            sheet.autoSizeColumn(i);  
        }  
        //获取当前列的宽度，然后对比本列的长度，取最大值  
        for (int columnNum = 0; columnNum <= maxColumn; columnNum++)  
        {  
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;  
            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++)  
            {  
				try {
	                Row currentRow;  
	                //当前行未被使用过  
	                if (sheet.getRow(rowNum) == null)  
	                {  
	                    currentRow = sheet.createRow(rowNum);  
	                }  
	                else   
	                {  
	                    currentRow = sheet.getRow(rowNum);  
	                }  
	
	                if(currentRow.getCell(columnNum) != null)  
	                {  
	                    Cell currentCell = currentRow.getCell(columnNum);  
	                    int length;
							length = currentCell.toString().getBytes("GBK").length;
	                    if (columnWidth < length + 1)  
	                    {  
	                        columnWidth = length + 1;  
	                    }  
	                }  
	            }catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}    
	            sheet.setColumnWidth(columnNum, columnWidth * 256);  
			} 
        } 
    }
}
