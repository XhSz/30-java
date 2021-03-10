package p50_project_v1_3_6;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import p50_project_v1.J1_BeanCall;
import p50_project_v1.J1_BeanCallRelate;
import p50_project_v1.J1_BeanDb;
import p50_project_v1.J1_BeanTran;
import p50_project_v1.J1_BeanTranRelate;

public class J72_Tran_Util {
	/**
	 * 扫描代码根目录，获得java工程路径
	 * 
	 * @param path
	 * @param callKeySet
	 * @param dbKeySet
	 * @param callSet
	 * @param callRelateList
	 */
    public static void scanJavaFolder(String path,Set<String> callKeySet,Map<String,String> dbMap,Set<J1_BeanCall> callSet,List<J1_BeanCallRelate> callRelateList) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
//                System.err.println(file.getAbsolutePath()+":空的!");
                return;	
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                    	if(file2.getAbsolutePath().endsWith("java")) {
//                            System.out.println(file2.getAbsolutePath()+":ok!");
                            scanJavaFolder(file2.getAbsolutePath(),callKeySet,dbMap,callSet,callRelateList);
                    	}else if(file2.getAbsolutePath().endsWith("target")){
                    		
                    	}else{
                            scanJavaFolder(file2.getAbsolutePath(),callKeySet,dbMap,callSet,callRelateList);
                    	}
                    }else{
                    	if(file2.getAbsolutePath().endsWith(".java")) {
//                            if(J3_Util.DE)
                            	System.out.println(file2.getAbsolutePath());
                            J3_Util.parseJava(file2.getAbsolutePath(),callKeySet,dbMap,callSet,callRelateList);
                    	}
                    }
                }
            }
        } else {
            System.err.println(file.getAbsolutePath()+":不存在!");
        }
    }
    public static void main(String[] args) {
    	J3_Util.DE=true;
    	callTest(args);
//    	callRelateTest(args);
	}
    public static void callTest(String[] args) {
//		J5_Sql.doMain(443, null);
//    	String path = 
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-batch\\src\\main\\java\\cn\\sunline\\icore\\cf\\batch\\cf05DataProcessor.java"
//    			"D:\\03-sl-107-code\\26-gs\\201106\\cm-busi\\cm-serv\\src\\main\\java\\cn\\sunline\\icore\\cm\\serv\\serviceimpl\\chrg\\SrvIoCmChrgImpl.java"
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-batch\\src\\main\\java\\cn\\sunline\\icore\\cf\\batch\\cf13DataProcessor.java"
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-serv\\src\\main\\java\\cn\\sunline\\icore\\cf\\serv\\base\\CfAgreeInfoCheck.java"
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\us-busi\\us-serv\\src\\main\\java\\cn\\sunline\\icore\\us\\serv\\util\\FileTools.java"
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-serv\\src\\main\\java\\cn\\sunline\\icore\\cf\\serv\\base\\CfCustInfoCheck.java"
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-serv\\src\\main\\java\\cn\\sunline\\icore\\cf\\serv\\base\\CfCustInfoBuild.java"
//				"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\dp-busi\\dp-serv\\src\\main\\java\\cn\\sunline\\icore\\dp\\serv\\account\\open\\DpOpenAccount.java"
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-serv\\src\\main\\java\\cn\\sunline\\icore\\cf\\serv\\base\\CfAgreeApplyListQuery.java"
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-serv\\src\\main\\java\\cn\\sunline\\icore\\cf\\serv\\base\\CfAgreeSignMnt.java"
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-batch\\src\\main\\java\\cn\\sunline\\icore\\cf\\batch\\cf02DataProcessor.java"
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-serv\\src\\main\\java\\cn\\sunline\\icore\\cf\\serv\\agent\\CfCustAgentInfoDeal.java"
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\us-busi\\us-tran\\src\\main\\java\\cn\\sunline\\icore\\us\\tran\\base\\us3080.java"
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\us-busi\\us-serv\\src\\main\\java\\cn\\sunline\\icore\\us\\serv\\verify\\ObmUtil.java"
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\us-busi\\us-serv\\src\\main\\java\\cn\\sunline\\icore\\us\\serv\\verify\\UsVerify.java"
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\us-busi\\us-serv\\src\\main\\java\\cn\\sunline\\icore\\us\\serv\\base\\UsUserInfoFile.java"
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\us-busi\\us-serv\\src\\main\\java\\cn\\sunline\\icore\\us\\serv\\base\\UsBatchAccounOpen.java"
    			;
        J2_Main.LS = true;
        J72_Tran_Main.isRealTime = false;
		J3_Util.parseJava("",new HashSet<String>(),null,new HashSet<J1_BeanCall>(),null);
	}
    public static void callRelateTest(String[] args) {
		Set<String> callKeySet = new HashSet<String>();
		J5_Sql.doMain(343, callKeySet);//343,select all,call_name
		Map<String,String> dbMap = new HashMap();
		J5_Sql.doMain(345, dbMap);//345,select all,tree_call_db
    	String path = 
    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-serv\\src\\main\\java\\cn\\sunline\\icore\\cf\\serv\\serviceimpl\\SrvCfCustomerQuerySign.java"
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-batch\\src\\main\\java\\cn\\sunline\\icore\\cf\\batch\\cf05DataProcessor.java"
//    	    	"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\dp-busi\\dp-serv\\src\\main\\java\\cn\\sunline\\icore\\dp\\serv\\account\\open\\DpOpenAccount.java"
    			;
		List<J1_BeanCallRelate> callRelateList = new ArrayList<J1_BeanCallRelate>();
		J3_Util.parseJava(path,callKeySet,dbMap,null,callRelateList);
    	if(!J72_Tran_Main.isRealTime) {
    		J5_Sql.doMain(444, null);//444,del all,tree_call_relate
			J5_Sql.doMain(134, callRelateList);//134,batch insert,tree_call_relate
    	}
	}
    public static int addTranRelateList(String flowtransKey,int seq_no,boolean is_simple,
    		Element element,List<J1_BeanTranRelate> tranRelateList,boolean isBlock){
        NodeList childNodes = element.getChildNodes();
        if(!isBlock)
        	is_simple = childNodes.getLength()==3?true:false;
        for (int j = 0; j <childNodes.getLength() ; j++) {
            if (childNodes.item(j).getNodeType()==Node.ELEMENT_NODE) {
            	seq_no++;
            	Node call = childNodes.item(j);
            	String type = call.getNodeName();
                String attrName = "";
                String tranType = "";
                if("method".equals(type)) {
                	attrName = "method";
                	tranType = "M";
                }else if("service".equals(type)) {
                	attrName = "serviceName";
                	tranType = "S";
                }else if("block".equals(type)) {
                	seq_no = addTranRelateList(flowtransKey,seq_no,is_simple,(Element)call,tranRelateList,true);
                	continue;
                }else if("report".equals(type)) {
                	attrName = "reportId";
                	tranType = "R";
                }else if("case".equals(type)) {
                	seq_no = addTranRelateList(flowtransKey,seq_no,is_simple,(Element)call,tranRelateList,true);
                	continue;
                }else if("when".equals(type)) {
                	seq_no = addTranRelateList(flowtransKey,seq_no,is_simple,(Element)call,tranRelateList,true);
                	continue;
                }
                NamedNodeMap attributes = call.getAttributes();
            	Node attr = attributes.getNamedItem(attrName);
                J1_BeanTranRelate bean = new J1_BeanTranRelate();
                if(isBlock) {
                	bean.setBlock_test(element.getAttributes().getNamedItem("test").getNodeName());
                }
                bean.setTran_name(flowtransKey);
                String callName = attr.getNodeValue();
                bean.setCall_name(callName);
                bean.setTran_type(tranType);
                bean.setSeq_no(seq_no);
                bean.setIs_simple(is_simple);
        		if(J71_Tran_Main.isRealTime) {
            		//tree_tran入库
        			J5_Sql.doMain(112, bean);
        		}else{
                    tranRelateList.add(bean);
        		}
            	System.out.println(flowtransKey+","+seq_no+","+callName+","+is_simple);
            }
        }
        return seq_no;
    }
}
