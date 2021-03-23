package p50_project_v1_4_7;

import java.io.File;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import p50_project_v1.J1_BeanBatch;
import p50_project_v1.J1_BeanCall;
import p50_project_v1.J1_BeanCallRelate;
import p50_project_v1.J1_BeanDb;
import p50_project_v1.J1_BeanTran;
import p50_project_v1.J1_BeanTranRelate;

public class J71_Tran_Util {
	private static boolean DE = false;
	/**
	 * 扫描代码根目录，获得tran工程路径
	 * @param path
	 * @param tranList
	 */
    public static void scanTran(String path,List<J1_BeanTran> tranList,List<J1_BeanTranRelate> tranRelateList) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
//                System.err.println(file.getAbsolutePath()+":空的!");
                return;	
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                    	if(file2.getAbsolutePath().endsWith("-tran")) {
                            System.out.println(file2.getAbsolutePath()+":ok!");
                            scanFlows(file2.getAbsolutePath()+"\\src\\main\\resources\\trans",tranList,tranRelateList);
                    	}else{
                    		scanTran(file2.getAbsolutePath(),tranList,tranRelateList);
                    	}
                    }
                }
            }
        } else {
            System.err.println(file.getAbsolutePath()+":不存在!");
        }
    }
    /**
     * 扫描tran工程，获得flowtrans
     * @param path
     * @param tranList
     */
    public static void scanFlows(String path,List<J1_BeanTran> tranList,List<J1_BeanTranRelate> tranRelateList) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
//                System.err.println(file.getAbsolutePath()+":空的!");
                return;	
            } else {
                for (File file2 : files) {
                    if (!file2.isDirectory()) {
                    	if(file2.getAbsolutePath().endsWith(".flowtrans.xml")) {
                    		String fn = file2.getName();
                    		fn = file2.getName().substring(0, fn.length()-14);
                    		J1_BeanTran bean = new J1_BeanTran();
                    		bean.setTran_name(fn);
                    		bean.setFile_path(file2.getAbsolutePath());
                    		//解析flowtrans.xml
                    		parseFlowTrans(file2.getAbsolutePath(), tranRelateList , bean);
                    		if(J2_Main.isRealTime) {
                        		//tree_tran入库
                    			J5_Sql.doMain(111, bean);
                    		}else{
                        		tranList.add(bean);
                    		}
                            if(J3_Util.DE)System.out.println(fn+":add!");
                    	}
                    }else{
                		scanFlows(file2.getAbsolutePath(),tranList,tranRelateList);
                    }
                }
            }
        } else {
            System.err.println(file.getAbsolutePath()+":不存在!");
        }
    }
	/**
	 * 扫描代码根目录，获得batch工程路径
	 * @param path
	 * @param tranList
	 */
    public static void scanBatch(String path,Set<J1_BeanBatch> batchSet) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
//                System.err.println(file.getAbsolutePath()+":空的!");
                return;	
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                    	if(file2.getAbsolutePath().endsWith("-batch")) {
                            System.out.println(file2.getAbsolutePath()+":ok!");
                            scanBatchs(file2.getAbsolutePath()+"\\src\\main\\resources\\batchtran",batchSet);
                    	}else{
                    		scanBatch(file2.getAbsolutePath(),batchSet);
                    	}
                    }
                }
            }
        } else {
            System.err.println(file.getAbsolutePath()+":不存在!");
        }
    }
    /**
     * 扫描batch工程，获得batchtran
     * @param path
     * @param tranList
     */
    public static boolean scanBatchs(String path,Set<J1_BeanBatch> batchSet) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
//                System.err.println(file.getAbsolutePath()+":空的!");
                return false;	
            } else {
                for (File file2 : files) {
                    if (!file2.isDirectory()) {
                    	if(file2.getAbsolutePath().endsWith(".batch_tran.xml")) {
                    		String fn = file2.getName();
                    		fn = file2.getName().substring(0, fn.length()-15);
                    		J1_BeanBatch bean = new J1_BeanBatch();
                    		bean.setTran_name(fn);
                    		bean.setFile_path(file2.getAbsolutePath());
                    		parseBatchs(file2.getAbsolutePath(),bean);
                    		if(J2_Main.isRealTime) {
                        		//tree_batch入库
                    			J5_Sql.doMain(116, bean);
                    		}else{
                    			batchSet.add(bean);
                    		}
                            if(J3_Util.DE)System.out.println(fn+":add!");
                    	}
                    }else{
                    	scanBatchs(file2.getAbsolutePath(),batchSet);
                    }
                }
            }
        } else {
            System.err.println(file.getAbsolutePath()+":不存在!");
            return false;
        }
        return true;
    }
    public static int insertTran(PreparedStatement ps,List<J1_BeanTran> list){
    	int count = 0;
//    	System.out.println(doMain(131,list));
    	return count;
    }
    public static void main(String[] args) {
    	J3_Util.DE = true;
    	DE = true;
//    	J1_BeanTran tranBean = new J1_BeanTran();
//    	parseFlowTrans("",new ArrayList<J1_BeanTranRelate>(),tranBean);
//     	J3_Util.print(tranBean);
    	J1_BeanBatch batchBean = new J1_BeanBatch();
    	parseBatchs("", batchBean);
     	J3_Util.print(batchBean);
	}
    /**
     * 解析flowtrans.xml，获得tree_tran_relate列表
     * @param list
     */
    public static void parseFlowTrans(String path,List<J1_BeanTranRelate> tranRelateList,J1_BeanTran tranBean){
    	if(DE&&J3_Util.DE) {
    	path = 
//    			"src/main/resources/p21_file_xml/f2_trans/dp1070.flowtrans.xml"
    	    	"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\us-busi\\us-tran\\src\\main\\resources\\trans\\base\\us2220.flowtrans.xml"
    			;
    	}
    	String flowtransName = path.substring(path.length()-20);
    	String flowtransKey = flowtransName.substring(0,6);
    	if(J3_Util.DE)System.out.println(flowtransKey);
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    try {
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document d = builder.parse(path);
	        NodeList list = d.getElementsByTagName("flow");
	        Node desNode = d.getElementsByTagName("flowtran").item(0).getAttributes().getNamedItem("longname");
	        if(null!=desNode)
	        	tranBean.setTran_des(desNode.getNodeValue());
	        for (int i = 0; i <list.getLength() ; i++) {
	            Element element = (Element) list.item(i);
	            int seq_no = 0;
	            addTranRelateList(flowtransKey,seq_no,true,element,tranRelateList,false);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
    }
    /**
     * 解析batch_tran.xml，初始化tree_batch集合
     * @param list
     */
    public static void parseBatchs(String path,J1_BeanBatch batchBean){
    	if(DE&&J3_Util.DE) {
    	path = 
    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-batch\\src\\main\\resources\\batchtran\\cf01.batch_tran.xml"
    			;
    	}
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    try {
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document d = builder.parse(path);
	        Node desNode = d.getElementsByTagName("batch_transaction").item(0).getAttributes().getNamedItem("longname");
	        if(null!=desNode)
	        	batchBean.setTran_des(desNode.getNodeValue());
	    } catch (Exception e) {
	        e.printStackTrace();
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
                	bean.setBlock_test(element.getAttributes().getNamedItem("test").getNodeValue());
                }
                bean.setTran_name(flowtransKey);
                String callName = attr.getNodeValue();
                bean.setCall_name(callName);
                bean.setTran_type(tranType);
                bean.setSeq_no(seq_no);
                bean.setIs_simple(is_simple);
        		if(J2_Main.isRealTime) {
            		//tree_tran入库
        			J5_Sql.doMain(112, bean);
        		}else{
                    tranRelateList.add(bean);
        		}
            	if(J3_Util.DE)System.out.println(flowtransKey+","+seq_no+","+callName+","+is_simple);
            }
        }
        return seq_no;
    }
}
