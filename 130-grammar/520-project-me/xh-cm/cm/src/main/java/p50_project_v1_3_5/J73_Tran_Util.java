package p50_project_v1_3_5;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import p50_project_v1_3.J1_BeanCall;
import p50_project_v1_3.J1_BeanCallRelate;
import p50_project_v1_3.J1_BeanDb;
import p50_project_v1_3.J1_BeanTran;
import p50_project_v1_3.J1_BeanTranRelate;

public class J73_Tran_Util {
	public static String[] KEY_FOLD = {		"-tran",	"java",	"tables"	 ,"namedsql"};
	public static String[] KEY_FILE = {".flowtrans.xml",".java",".tables.xml",".nsql.xml"};
	/**
	 * 扫描代码根目录，获得特定文件工程路径
	 * @param path
	 * @param tranList
	 */
    public static void scanFolder(String path,int key,Set<String> keySet,Set objSet) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                return;	
            } else {
                for (File childFile : files) {
                    if (childFile.isDirectory()) {
                    	if(childFile.getAbsolutePath().endsWith(KEY_FOLD[key])) {
                    		scanFolder(childFile.getAbsolutePath(),key,keySet,objSet);
                    	}else if(childFile.getAbsolutePath().endsWith("target")){
                    		
                    	}else{
                    		scanFolder(childFile.getAbsolutePath(),key,keySet,objSet);
                    	}
                    }else{
                    	if(childFile.getAbsolutePath().endsWith(KEY_FILE[key])) {
                    		if(J3_Util.DE)System.out.println(childFile.getAbsolutePath());
                            if(2==key) {
                            	J3_Util.parseTables(childFile.getAbsolutePath(),keySet,objSet);
                            }else if(3==key) {
                            	J3_Util.parseNSQL(childFile.getAbsolutePath(),keySet,objSet);
                            }
                    	}
                    }
                }
            }
        } else {
            System.err.println(file.getAbsolutePath()+":不存在!");
        }
    }
    public static void main(String[] args) {
//    	J3_Util.parseTables("", new HashSet<String>(), new HashSet<J1_BeanDb>());
//    	J3_Util.parseNSQL("", new HashSet<String>(), new HashSet<J1_BeanDb>());
//    	callTablseTest(args);
    	callNSQLTest(args);
	}
    public static void callTablseTest(String[] args) {
//		J5_Sql.doMain(443, null);
    	String path = 
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-serv\\src\\main\\java\\cn\\sunline\\icore\\cf\\serv\\base\\CfAgreeInfoCheck.java"
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\us-busi\\us-serv\\src\\main\\java\\cn\\sunline\\icore\\us\\serv\\util\\FileTools.java"
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-serv\\src\\main\\java\\cn\\sunline\\icore\\cf\\serv\\base\\CfCustInfoCheck.java"
    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-serv\\src\\main\\java\\cn\\sunline\\icore\\cf\\serv\\base\\CfCustInfoBuild.java"
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
        J3_Util.parseTables(path,new HashSet<String>(),new HashSet<J1_BeanDb>());
	}
    public static void callNSQLTest(String[] args) {
//		J5_Sql.doMain(443, null);
    	String path = 
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-serv\\src\\main\\java\\cn\\sunline\\icore\\cf\\serv\\base\\CfAgreeInfoCheck.java"
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\us-busi\\us-serv\\src\\main\\java\\cn\\sunline\\icore\\us\\serv\\util\\FileTools.java"
//    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-serv\\src\\main\\java\\cn\\sunline\\icore\\cf\\serv\\base\\CfCustInfoCheck.java"
    			"D:\\03-sl-107-code\\26-gs\\99-3.0.4-stable\\cf-busi\\cf-serv\\src\\main\\java\\cn\\sunline\\icore\\cf\\serv\\base\\CfCustInfoBuild.java"
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
        J3_Util.parseTables(path,new HashSet<String>(),new HashSet<J1_BeanDb>());
	}
}
