package p21_file_updatetime;


import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class J1_MainExecute {
    static String TARGET_PATH =
//    		"D:\\"
//    		"E:\\backup\\05-lenovo-pro-2\\20191020-2228\\D\\sunline"
//    		"C:\\30-java\\130-grammar\\520-project-me\\xh-common\\src"
            "C:\\"
            ;
    static long THRESHOLD = 
            100*1024*1024
            ;
    static long THRESHOLD_UPDATETIME = System.currentTimeMillis()-
            (3*60*1000)
            ;
    static String JSON_PATH =
            "C:\\01-cache\\"
            ;
    public static void main(String[] args) {
    	
        /*1.init*/
        long t1=System.currentTimeMillis();
        System.out.println("begin...1.init..."+t1);
        
        J2_Bean f3 = J3_Util.initByUpdatetime(TARGET_PATH,1,THRESHOLD_UPDATETIME);
        
        long t2=System.currentTimeMillis();
        System.out.println("end...1.init...耗时:"+(t2-t1));
        System.out.println("end...1.init...耗时:"+longToTime(t2-t1));
        
        /*2.sort*/
        System.out.println("begin...2.sort3...");
        
        J3_Util.sortByUpdatetime(f3);
        
        /*3.output*/
        System.out.println("begin...3.getSb3...");
        
        StringBuilder sb = J3_Util.getSbByUpdatetime(f3);// get result string
        try{
        	// print to file
            System.out.println("print...");
            String rs = TARGET_PATH.replace("\\",".").replace(":",".");
            rs=JSON_PATH+rs+".json";
            J3_Util.pringToJson(sb,rs);
        }catch (FileNotFoundException e){
            System.out.println("JSON不存在！");
            return ;
        }
        
        System.out.println("ok...");
        long t3=System.currentTimeMillis();
        System.out.println("end...2.sort..3.print.耗时:"+(t3-t2));
        System.out.println("end...总耗时:"+(t3-t1));
        System.out.println("end...总耗时:"+longToTime(t3-t1));

    }
    public static String longToDate(long t){
        SimpleDateFormat sd = new SimpleDateFormat("mm:ss");
        return sd.format(new Date(t));
    }
    public static String longToTime(long t){
        StringBuilder sb = new StringBuilder();
        if(t>60*60*1000){
            sb.append(t/(60*60*1000)).append("h");
            t=t%60*60*1000;
        }
        if(t>60*1000){
            sb.append(t/(60*1000)).append("m");
            t=t%60*1000;
        }
        if(t>1000){
            sb.append(t/1000).append("s");
            t=t%1000;
        }
        sb.append(t).append("ms");
        return sb.toString();
    }
}
