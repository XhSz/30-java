package p21_file_size.xh;

import java.io.*;

public class XhJavaFile {
    static String TARGET_PATH =
            "C:\\Users\\谢豪\\Downloads"
            //"C:\\Users\\谢豪\\下载\\code.html"
            //"C:\\Users\\谢豪\\Downloads\\Kafka"
            //"C:\\Users\\谢豪\\Downloads\\WAS8.5\\升级包+JDK8"
            //"C:\\Users\\谢豪\\Downloads\\WAS8.5\\升级包+JDK8\\8.5.5-WS-WAS-FP013-part3.zip"
            ;
    static String JSON_PATH =
            "D:\\xh-java\\220-grammar-tools\\File\\xh-file\\result.json"
            ;
    static StringBuilder sb_cache;
    static StringBuilder sb_final;
    static int RUN_FLAG=1;
    public static void main(String[] args) {
        System.out.println("开始遍历...");
        StringBuilder sb = new StringBuilder("{\n");
        if(1==RUN_FLAG){
            FileWithSizeShow rf = XhFileCommon.init(TARGET_PATH,0);
            sb_final = rf.sb;
        }else if(0==RUN_FLAG){
            XhFile cxf = new XhFile(TARGET_PATH,0);
            refreshFileList(cxf);
            cxf.finshInit();
            sb_final = cxf.getSb();
        }
        //sb_final.append("\n,\"").append(cxf.getF_name()).append("\"\n");
        sb.append(sb_final).append("\n}");
        System.out.println("开始结束...");
        try{
            XhFileCommon.pringToJson(sb,JSON_PATH);
        }catch (FileNotFoundException e){
            System.out.println("JSON不存在！");
            return ;
        }
        System.out.println("程序运行完毕...");
    }
    public static StringBuilder refreshFileList(XhFile xf)
    {
        sb_cache = XhFileCommon.space(xf.getStage());
        if(xf.getFile().isDirectory()){
            sb_cache = sb_cache.append("\"\":{\n");
        }else{
            xf.finshInit();
            return sb_cache.append(xf.getSb());
        }
        //遍历目录
        //File dir = new File(xf.getFile().getAbsolutePath());
        File[] files = xf.getFile().listFiles();
        if (files == null){
            //System.out.println("空文件夹！");
            //return new StringBuilder("{}");
            //xf.setSb(xf.getSb().append("}"));
            return xf.finshInit();
        }
        for (int i = 0; i < files.length; i++) {
            XhFile cxf = new XhFile(files[i].getAbsolutePath(),xf.getStage());
            if(files[i].isDirectory()){
                sb_cache = sb_cache.append(
                        refreshFileList(cxf)
                ).append(",\n");
            }else{
                sb_cache = sb_cache.append(XhFileCommon.space(cxf.getStage()));
                xf.setLength(xf.getLength()+cxf.getLength());
                cxf.finshInit();
                sb_cache = sb_cache.append(cxf.getSb().append(",\n"));//new StringBuilder("}");
            }
        }
        xf.setSb(xf.getSb().append(
                sb_cache.deleteCharAt(sb_cache.length()-2)
                        .append(XhFileCommon.space(xf.getStage())).append("}\n")
        ));
        return xf.getSb();
    }
}
