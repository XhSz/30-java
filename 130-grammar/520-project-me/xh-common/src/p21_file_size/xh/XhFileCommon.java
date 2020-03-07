package p21_file_size.xh;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import p21_file_size.xh.function.F3;

public class XhFileCommon {
    //public static double COMPARE_SIZE= 0.1*1024*1024*1024;//0.1GB
    public static double COMPARE_SIZE= 10*1024*1024;//10
    public static void main(String[] args) {

    }
    static FileWithSizeShow init(String sPath,int in){
        FileWithSizeShow resultFile = new FileWithSizeShow(sPath,in);
        if(resultFile.isDirectory())
        {
            resultFile.sb.append(space(resultFile.stage)).append("\"\":{\n");
            File[] files = resultFile.listFiles();
            if (files != null){
                in++;
                for(int i=0;i<files.length;i++){
                    FileWithSizeShow item =
                            init(files[i].getAbsolutePath(),in);
                    resultFile.setSize(
                            resultFile.getSize()
                                    +item.getSize()
                    );
                    if(item.getSize()>COMPARE_SIZE) {
                        /*
                        int num = 0;
                        if (null != resultFile.childArray) {
                            num = resultFile.childArray.length;
                        }
                        resultFile.childArray[num] = item;
                        */
                        resultFile.sb.append(space(item.stage))
                                .append(item.sb).append("\n");
                    }
                }
            }
            resultFile.sb.append(space(resultFile.stage)).append("\n}");
        }else{
            resultFile.size+=resultFile.length();
            resultFile.sb.append(space(resultFile.stage))
                    .append(resultFile.getName())
                    .append("-------")
                    .append(longToSize(resultFile.getSize()))
                    .append(":\"\",\n");
        }
        return resultFile;
    }
    /*
        默认路径存在，每个路径判断一次影响效率
     */
    static FileWithSize init(String sPath){
        FileWithSize resultFile = new FileWithSize(sPath);
        if(resultFile.isDirectory())
        {
            File[] files = resultFile.listFiles();
            if (files != null){
                for(int i=0;i<files.length;i++){
                    FileWithSize item = init(files[i].getAbsolutePath());
                    resultFile.setSize(
                            resultFile.getSize()
                            +item.getSize()
                    );
                }
            }
        }
        return resultFile;
    }
    public static F3 init3(String sPath, int stage){
        F3 r = new F3(sPath,stage);
        if(r.isDirectory())
        {
            File[] files = r.listFiles();
            if (files != null){
                r.cl= new ArrayList<F3>();
                stage++;
                for(int i=0;i<files.length;i++){
                    F3 item = init3(files[i].getAbsolutePath(),stage);
                    r.s+=item.s;
                    if(item.s>100*1024*1024){
                        r.cl.add(item);
                    }
                }
            }
        }
        return r;
    }
    public static StringBuilder getSb3(F3 f3){
        StringBuilder sb = new StringBuilder();
        sb.append("{\n")
                .append(getItemSb3(f3));
        sb.deleteCharAt(sb.length()-2);
        sb.append("}");
        return sb;
    }
    public static StringBuilder getItemSb3(F3 f3){
        StringBuilder sb = new StringBuilder();
        sb.append(space(f3.n))
                .append("\"")
                .append(longToSize(f3.s))
                .append("------")
                .append(f3.getName())
                .append("\":");
        if(f3.isDirectory())
        {
            sb.append("{\n");//.append(space(f3.n));
            if(null!=f3.cl&&f3.cl.size()>0) {
                for (F3 cf3 : f3.cl) {
                    //for(int i=0;i<f3.cl.size();i++){
                    //xh.XhFileCommon.getSb3(f3.cl.get(i));
                    sb.append(getItemSb3(cf3));
                }
                sb.deleteCharAt(sb.length()-2);
                sb.append("\n")
                        .append(space(f3.n))
                        .append("},\n");
            }else{
                sb.deleteCharAt(sb.length()-1);
                sb.append("},\n");
            }
            //"filename------1G":{\n\n},\n
        }else
            sb.append("\"\",\n");
        //"filename------1G":"",\n
        return sb;//.deleteCharAt(sb.length()-2);
    }
    public static void sort3(F3 f3){
        if(null!=f3.cl&&f3.cl.size()>1){
            //List<F3> nl=new ArrayList<F3>();
            for(int j=0;j<f3.cl.size()-1;j++) {
                F3 c3 ;
                for (int i = j; i < f3.cl.size(); i++) {
                    //System.out.println("i.s="+f3.cl.get(i).s
                      //  +",i+1.s="+f3.cl.get(i+1).s);
                    if (f3.cl.get(i).s > f3.cl.get(j).s) {
                        c3=f3.cl.get(j);
                        f3.cl.set(j,f3.cl.get(i));
                        f3.cl.set(i,c3);
                    }
                }
            }
            for(F3 iF : f3.cl){
                sort3(iF);
            }
            //System.out.println(f3.cl);
        }
    }
    public void sort(FileWithSize filein){
        FileWithSize[] childArray = filein.childArray;
        if(null==childArray||childArray.length<2)return;
        FileWithSize[] resultList = new FileWithSize[childArray.length];
        for(int j=0;j<childArray.length-1;j++) {
            FileWithSize minFile = new FileWithSize("");
            for (int i = j; i < childArray.length-1; i++) {
                if (childArray[i].size > childArray[i + 1].size) {
                    resultList[j]=childArray[i];
                    minFile = childArray[i + 1];
                }else{
                    resultList[j]=childArray[i+1];
                    minFile = childArray[i];
                }
            }
            resultList[j+1]=minFile;
        }
        filein.childArray = resultList;
    }
    static String longToSize(long i) {
        String rs = "<0.1G";
        if(i==0)return "empty";
        if (i > 1024 * 1024 * 100)//100M
        {
            long m = i/(1024*1024);
            //System.out.println("m="+m);
            double d = m/(double)1024;
            //System.out.println("d="+d);
            rs = String.format("%.2f", d)+"G";
        }
        return  rs;
    }
    public static StringBuilder space(int n){
        StringBuilder s = new StringBuilder();
        for(int i=0;i<n;i++){
            s.append("\t");
        }
        return s;
    }
    public static void pringToJson(StringBuilder s,String jp) throws FileNotFoundException {
        File json = new File(jp);
        FileOutputStream fos = new FileOutputStream(json);
        PrintStream ps = new PrintStream(fos);
        ps.print(s);
    }
}
