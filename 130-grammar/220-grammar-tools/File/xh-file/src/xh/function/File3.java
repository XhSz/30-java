package xh.function;


import xh.FileWithSize;
import xh.XhFileCommon;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class File3 {
    static String TARGET_PATH =
            //"C:\\Users\\谢豪\\Downloads"
            //"C:\\Users\\谢豪\\下载\\code.html"
            //"C:\\Users\\谢豪\\Downloads\\Kafka"
            //"D:\\"
            "C:"
            //"C:\\Users\\谢豪"
            //"C:\\Users\\谢豪\\Downloads"
//            "E:\\video\\xiaorouroubin"
            //"C:\\Users\\谢豪\\Downloads\\WAS8.5"
            //"C:\\Users\\谢豪\\Downloads\\WAS8.5\\升级包+JDK8"
            //"C:\\Users\\谢豪\\Downloads\\WAS8.5\\升级包+JDK8\\8.5.5-WS-WAS-FP013-part3.zip"
//            "E:\\video\\GXP01"
            ;
    static String JSON_PATH =
            "D:\\xh-java\\220-grammar-tools\\File\\xh-file\\result\\C-20190813.json"
//            "E:\\video\\GXP01\\20190317-20190411\\log2.json"
            ;
    public static void main(String[] args) {
        /*1.init*/
        long t1=System.currentTimeMillis();
        System.out.println("begin...1.init3..."+t1);
        F3 f3 = XhFileCommon.init3(TARGET_PATH,1);
        long t2=System.currentTimeMillis();
        System.out.println("end...1.init3...耗时:"+(t2-t1));
        System.out.println("end...1.init3...耗时:"+longToTime(t2-t1));
        //System.out.println("1------"+f3.cl);
        /*2.sort*/
        System.out.println("begin...2.sort3...");
        XhFileCommon.sort3(f3);
        //System.out.println("2------"+f3.cl);
        /*3.output*/
        System.out.println("begin...3.getSb3...");
        StringBuilder sb = XhFileCommon.getSb3(f3);
        try{
            System.out.println("print...");
            String rs = TARGET_PATH.replace("\\","-");
            rs=JSON_PATH+rs+".json";
            //XhFileCommon.pringToJson(sb,rs);
            XhFileCommon.pringToJson(sb,JSON_PATH);
        }catch (FileNotFoundException e){
            System.out.println("JSON不存在！");
            return ;
        }
        System.out.println("ok...");
        long t3=System.currentTimeMillis();
        System.out.println("end...2.sort3..3.print.耗时:"+(t3-t2));
        System.out.println("end...总耗时:"+(t3-t1));
        System.out.println("end...总耗时:"+longToTime(t3-t1));

    }
    public static String longToDate(long t){
        SimpleDateFormat sd = new SimpleDateFormat("mm:ss");
        //System.out.println(sd.format(new Date(t)));
        return sd.format(new Date(t));
    }
    public static String longToTime(long t){
        /*
        long t1=System.currentTimeMillis();
        System.out.println("begin...1.init3..."+t1);
        F3 f3 = XhFileCommon.init3(TARGET_PATH,1);
        long t2=System.currentTimeMillis();
        System.out.println("end...1.init3...耗时:"+(t2-t1));
        System.out.println("end...1.init3...耗时:"+longToTime(t2-t1));
         */
        StringBuilder sb = new StringBuilder();
        if(t>60*60*1000){
            sb.append(t/60*60*1000).append("h");
            t=t%60*60*1000;
        }
        if(t>60*1000){
            sb.append(t/60*1000).append("m");
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
