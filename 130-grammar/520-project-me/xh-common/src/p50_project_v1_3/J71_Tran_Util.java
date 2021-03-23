package p50_project_v1_3;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class J71_Tran_Util {
    public static void scanTran(String path,List<J1_BeanTran> tranList) {
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
                            scanFlows(file2.getAbsolutePath()+"\\src\\main\\resources\\trans",tranList);
                    	}else{
                    		scanTran(file2.getAbsolutePath(),tranList);
                    	}
                    }
                }
            }
        } else {
            System.err.println(file.getAbsolutePath()+":不存在!");
        }
    }
    public static void scanFlows(String path,List<J1_BeanTran> tranList) {
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
                    		tranList.add(bean);
                            System.out.println(fn+":add!");
                    	}
                    }else{
                		scanFlows(file2.getAbsolutePath(),tranList);
                    }
                }
            }
        } else {
            System.err.println(file.getAbsolutePath()+":不存在!");
        }
    }
    public static int insertTran(PreparedStatement ps,List<J1_BeanTran> list){
    	int count = 0;
//    	System.out.println(doMain(131,list));
    	return count;
    }
}
