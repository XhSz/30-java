package p21_file_size.release.v1_1;


import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class J1_MainExecute {
    static String TARGET_PATH =
//    		"E:\\"
//    		"D:\\23-log\\31-hard_disk"
//    		"E:\\backup\\05-lenovo-pro-2\\20191020-2228\\D\\sunline"
//            "D:\\"
    		"E:\\09D"
//    		"D:\\01-cache"
            ;
    static long THRESHOLD_INIT =
            10*1024*1024
//            1
            ;
    static long THRESHOLD_PRINT =
    		THRESHOLD_INIT
//            10*1024*1024
//            1
            ;
    static String JSON_PATH =
            "D:\\01-cache\\"
            ;
    public static void main(String[] args) {
    	
        /*1.init*/
        long t1=System.currentTimeMillis();
        System.out.println("begin...1.init..."+t1);
        
        J2_Bean f3 = J3_Util.init(TARGET_PATH,1,THRESHOLD_INIT);

        long t1_5=System.currentTimeMillis();
        System.out.println("end...1.init...耗时:"+(t1_5-t1));
        System.out.println("end...1.init...耗时:"+longToTime(t1_5-t1));
        
        /*1.5.setDulicate*/
        System.out.println("begin...1.5.setDulicate...");
        J3_Util.setDulicate(f3);
        
        long t2=System.currentTimeMillis();
        System.out.println("end...1.5.setDulicate...耗时:"+(t2-t1_5));
        System.out.println("end...1.5.setDulicate...耗时:"+longToTime(t2-t1_5));
        
        /*2.sort*/
        System.out.println("begin...2.sort3...");
        
        J3_Util.sort(f3);
        
        /*3.output*/
        System.out.println("begin...3.getSb3...");
        
        StringBuilder sb = J3_Util.getSb(f3);// get result string
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
