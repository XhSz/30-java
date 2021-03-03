package p50_project_v1_4_2;

import java.io.File;
import java.util.Set;

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
}
