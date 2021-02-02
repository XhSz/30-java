package p21_file_size.release.v1_5;


import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * update-200105-1458:
 * 	快捷文件，绝对文件分开记录
 */
public class J1_MainExecute {
	//1.0.0 store绝对标识
    static String STORE = 
//    		"09"	//09-lenovo2
    		"25"	//25-x3
//    		"28"	//28-wd_red
    		;
	//1.0.2 运算标识
    static String FLAG = "01";
    static String TARGET_PATH ="F:\\";//1.1.1 检测目标路径
    static String TARGET_PATH_00 = J3_Util.getFolder(TARGET_PATH, J.FAST_KEY); //1.1.2 检测目标路径生成对应快捷文件路径
    static String TARGET_PATH_01 = J3_Util.getFolder(TARGET_PATH, J.ABSO_KEY); //1.1.2 检测目标路径生成绝对文件路径
    //in.substring(0, 2)+"\\"+J1_MainExecute.STORE+in.substring(0, 1)+J.H+key+in.substring(2);
    static long THRESHOLD_INIT = 100*1024*1024; 	//10M //1.2 会被初始化的容量大小阈值
    static long THRESHOLD_PRINT = 10*1024*1024; //10M //1.3 会以G形式被输出的容量大小阈值
    static String JSON_PATH = TARGET_PATH_00;	//1.4 最终结果json的输出路径	"D:\\01-cache\\";
    static long FAST_SIZE = 1024*1024*1024; 	//1G //1.5.1 触发自动写入快捷json打阈值，大小
    static long FAST_COUNT = 10*1000; 	//1W //1.5.2 触发自动写入快捷json打阈值，个数
    static long FAST_TAKE = 600*1000; 	//1m //1.5.3 触发自动写入快捷json打阈值，扫描耗时
    static int NEED_FAST = 0; 	//1，读取快捷文件； 其他值不会读快捷文件 { 0，覆盖写入；非01其他值，追加写入 } ；
    //2.1 打印当前文件时间间隔
    static long HOLDER = 
//			  	     1l	//每毫秒
//    			  1000l	//每秒
    			  5000l	//每秒
//    		   10*1000l	//每10秒
//    		60*60*1000l	//每30秒
//    		60*60*1000l	//每分钟
    		;
  //2.2 是否需要快捷执行判断  	 
    
    public static void main(String[] args) {
    	if(null!=args&&args.length>=7) {
    		STORE = args[0];
    		FLAG = args[1];
    		TARGET_PATH = args[2];
    		THRESHOLD_INIT = J3_Util.strToLong(args[3]);
    		THRESHOLD_PRINT = J3_Util.strToLong(args[4]);
    		HOLDER = Long.parseLong(args[5]);
    		NEED_FAST = Integer.parseInt(args[6]);
    		System.out.println("顺利传参！");
    	    TARGET_PATH_00 = J3_Util.getFolder(TARGET_PATH, J.FAST_KEY); //1.1.2 检测目标路径生成对应快捷文件路径
    	    TARGET_PATH_01 = J3_Util.getFolder(TARGET_PATH, J.ABSO_KEY); //1.1.2 检测目标路径生成绝对文件路径
    	    JSON_PATH = TARGET_PATH_00;	//1.4 最终结果json的输出路径	
    		System.out.println("顺利初始化！");
    	}
    	
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
        System.out.println("end...1.init...耗时:"+J3_Util.longToTime(t1_5-t1));
        j4_share.cur_file = J4_Timer.END;
        
        /*1.5.setDulicate 设置文件大小的Set集合*/
        System.out.println("begin...1.5.setDulicate...");
        J3_Util.setDulicate(f3);
        
        long t2=System.currentTimeMillis();
        System.out.println("end...1.5.setDulicate...耗时:"+(t2-t1_5));
        System.out.println("end...1.5.setDulicate...耗时:"+J3_Util.longToTime(t2-t1_5));
        
        /*2.sort 排序*/
        System.out.println("begin...2.sort3...");
        
        J3_Util.sort(f3);
        
        /*3.output 输出*/
        System.out.println("begin...3.getSb3...");
        
        StringBuilder sb = J3_Util.getSb(f3);// get result string
        String filename = "";
        try{
        	// print to file 输出到结果文件
            System.out.println("print...");
            filename = TARGET_PATH.replace("\\",".").replace(":",".");
            J3_Util.pringToJson(sb,JSON_PATH+filename+J.FILE_JSON);
        }catch (FileNotFoundException e){
            System.out.println("JSON不存在！");
            return ;
        }
        
        System.out.println("ok...");
        long t3=System.currentTimeMillis();
        System.out.println("end...2.sort..3.print.耗时:"+(t3-t2));
        System.out.println("end...2.sort..3.print.耗时:"+J3_Util.longToTime(t3-t2));
        System.out.println("end...总耗时:"+(t3-t1));
        System.out.println("end...总耗时:"+J3_Util.longToTime(t3-t1));

        /*4.给文件重命名*/
        SimpleDateFormat fmdate = new SimpleDateFormat("yyMMdd");
        String pre = STORE+J.H+fmdate.format(new Date())+J.H+FLAG+"-";
        J3_Util.renameFile(JSON_PATH, pre, filename, J3_Util.longToTime(t3-t1));
    }
    
}
