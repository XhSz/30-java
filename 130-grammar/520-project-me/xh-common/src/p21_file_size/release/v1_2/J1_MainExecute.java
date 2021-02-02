package p21_file_size.release.v1_2;


import java.io.FileNotFoundException;

/**
 * 
 * update-200104-1700:
 * 	1.添加中文注释；
 *  2.添加进度显示；
 *
 */
public class J1_MainExecute {
	//1.1 检测目标路径
    static String TARGET_PATH =
//    		"E:\\"
//    		"D:\\23-log\\31-hard_disk"
//    		"E:\\backup\\05-lenovo-pro-2\\20191020-2228\\D\\sunline"
//            "D:\\"
//    		"E:\\09D"
    		"E:\\lxe"
//    		"D:\\01-cache"
            ;
    //1.2 会被初始化的容量大小阈值
    static long THRESHOLD_INIT =
            10*1024*1024
//            1
            ;
    //1.3 会被输出的容量大小阈值
    static long THRESHOLD_PRINT =
    		THRESHOLD_INIT
//            10*1024*1024
//            1
            ;
    //1.4 最终结果json的输出路径
    static String JSON_PATH =
            "D:\\01-cache\\"
            ;
    //2.1 打印当前文件时间间隔
    static long HOLDER = 
			  	     1l	//每毫秒
//    			  1000l	//每秒
//    		   10*1000l	//每10秒
//    		60*60*1000l	//每30秒
//    		60*60*1000l	//每分钟
    		;
    
    public static void main(String[] args) {
    	
        /*1.init 初始化过程*/
        long t1=System.currentTimeMillis();
        System.out.println("begin...1.init..."+t1);
        
        //1.1 启动计数器线程
        J4_Share j4_share = new J4_Share();
        J4_Timer j4_timer = new J4_Timer(j4_share,HOLDER);
        j4_timer.start();
        
        //1.2 开始遍历目标路径初始化
        J2_Bean f3 = J3_Util.init(TARGET_PATH,1,THRESHOLD_INIT,j4_share);

        long t1_5=System.currentTimeMillis();
        System.out.println("end...1.init...耗时:"+(t1_5-t1));
        System.out.println("end...1.init...耗时:"+longToTime(t1_5-t1));
        j4_share.cur_file = J4_Timer.END;
        
        /*1.5.setDulicate 设置文件大小的Set集合*/
        System.out.println("begin...1.5.setDulicate...");
        J3_Util.setDulicate(f3);
        
        long t2=System.currentTimeMillis();
        System.out.println("end...1.5.setDulicate...耗时:"+(t2-t1_5));
        System.out.println("end...1.5.setDulicate...耗时:"+longToTime(t2-t1_5));
        
        /*2.sort 排序*/
        System.out.println("begin...2.sort3...");
        
        J3_Util.sort(f3);
        
        /*3.output 输出*/
        System.out.println("begin...3.getSb3...");
        
        StringBuilder sb = J3_Util.getSb(f3);// get result string
        try{
        	// print to file 输出到结果文件
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
        System.out.println("end...2.sort..3.print.耗时:"+longToTime(t3-t2));
        System.out.println("end...总耗时:"+(t3-t1));
        System.out.println("end...总耗时:"+longToTime(t3-t1));

    }
    
    //把绝对时间转化为时分秒显示
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
