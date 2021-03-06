package p21_file_updatetime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class J3_Util {
    /**
     * 1.	Recursively call to add up the volume of all files in the folder
     * 2.	add files larger than the size to the showlist
     * @param	target_path : target_path
     * @param	stage : file content stage , there is no use current version
     * @param	size : File display size condition
     */
    public static J2_Bean init(String target_path, int stage , long size){
        J2_Bean r = new J2_Bean(target_path,stage); // roor file
        if(r.isDirectory())
        {
            File[] files = r.listFiles();
            if (files != null){
                r.cl= new ArrayList<J2_Bean>();
                stage++;
                for(int i=0;i<files.length;i++){
                    J2_Bean item = init(files[i].getAbsolutePath(),stage,size);
                    r.s+=item.s; //add size
                    if(item.s>size){
                        r.cl.add(item); //add show list
                    }
                }
            }
        }
        return r;
    }
    /**
     * 1.	Recursively call to update directory with his child's most update time
     * 2.	add files larger than the size to the showlist
     * @param	target_path : target_path
     * @param	stage : file content stage , there is no use current version
     * @param	time : File display update time condition
     */
    public static J2_Bean initByUpdatetime(String target_path, int stage , long time){
        J2_Bean r = new J2_Bean(target_path,stage); // roor file
        if(r.isDirectory())
        {
            File[] files = r.listFiles();
            if (files != null){
                r.cl= new ArrayList<J2_Bean>();
                stage++;
                for(int i=0;i<files.length;i++){
                    J2_Bean item = initByUpdatetime(files[i].getAbsolutePath(),stage,time);
                    if(item.ut>r.ut)
                    	r.ut=item.ut; //update update time
                    if(item.ut>time&&(!item.getName().endsWith(".class"))){
                        r.cl.add(item); //add show list
                    }
                }
            }
        }
        return r;
    }
    public static void sort(J2_Bean f3){
        if(null!=f3.cl&&f3.cl.size()>1){
        	//bubble sort the current directory
            for(int j=0;j<f3.cl.size()-1;j++) {
                J2_Bean c3 ; // cache bean
                for (int i = j; i < f3.cl.size(); i++) {
                    if (f3.cl.get(i).s > f3.cl.get(j).s) {
                        c3=f3.cl.get(j);
                        f3.cl.set(j,f3.cl.get(i));
                        f3.cl.set(i,c3);
                    }
                }
            }
            //bubble sort subdirectories
            for(J2_Bean iF : f3.cl){
                sort(iF);
            }
        }
    }
    public static void sortByUpdatetime(J2_Bean f3){
        if(null!=f3.cl&&f3.cl.size()>1){
        	//bubble sort the current directory
            for(int j=0;j<f3.cl.size()-1;j++) {
                J2_Bean c3 ; // cache bean
                for (int i = j; i < f3.cl.size(); i++) {
                    if (f3.cl.get(i).ut > f3.cl.get(j).ut) {
                        c3=f3.cl.get(j);
                        f3.cl.set(j,f3.cl.get(i));
                        f3.cl.set(i,c3);
                    }
                }
            }
            //bubble sort subdirectories
            for(J2_Bean iF : f3.cl){
            	sortByUpdatetime(iF);
            }
        }
    }
    /**
     * get result string
     */
    public static StringBuilder getSb(J2_Bean f3){
        StringBuilder sb = new StringBuilder();
        sb.append("{\n")
                .append(getItemSb(f3));
        sb.deleteCharAt(sb.length()-2);
        sb.append("}");
        return sb;
    }
    /**
     * get result string ByUpdatetime
     */
    public static StringBuilder getSbByUpdatetime(J2_Bean f3){
        StringBuilder sb = new StringBuilder();
        sb.append("{\n")
                .append(getItemSbByUpdatetime(f3));
        sb.deleteCharAt(sb.length()-2);
        sb.append("}");
        return sb;
    }
    /**
     * get item result string 
     */
    public static StringBuilder getItemSb(J2_Bean f3){
        StringBuilder sb = new StringBuilder();
        sb.append(space(f3.n))
                .append("\"")
                .append(longToSize(f3.s))
                .append("------")
                .append(f3.getName())
                .append("\":");
        if(f3.isDirectory())
        {
            sb.append("{\n");
            if(null!=f3.cl&&f3.cl.size()>0) {
                for (J2_Bean cf3 : f3.cl) {
                    sb.append(getItemSb(cf3));
                }
                sb.deleteCharAt(sb.length()-2);
                sb.append("\n")
                        .append(space(f3.n))
                        .append("},\n");
            }else{
                sb.deleteCharAt(sb.length()-1);
                sb.append("},\n");
            }
        }else
            sb.append("\"\",\n");
        return sb;
    }
    /**
     * get item result string ByUpdatetime
     */
    public static StringBuilder getItemSbByUpdatetime(J2_Bean f3){
        StringBuilder sb = new StringBuilder();
        sb.append(space(f3.n))
                .append("\"")
                .append(longToTime(f3.ut))
                .append("------")
                .append(f3.getName())
                .append("\":");
        if(f3.isDirectory())
        {
            sb.append("{\n");
            if(null!=f3.cl&&f3.cl.size()>0) {
                for (J2_Bean cf3 : f3.cl) {
                    sb.append(getItemSbByUpdatetime(cf3));
                }
                sb.deleteCharAt(sb.length()-2);
                sb.append("\n")
                        .append(space(f3.n))
                        .append("},\n");
            }else{
                sb.deleteCharAt(sb.length()-1);
                sb.append("},\n");
            }
        }else
            sb.append("\"\",\n");
        return sb;
    }
    static String longToSize(long i) {
        String rs = "<0.1G";
        if(i==0)return "empty";
        if (i > 1024 * 1024 * 100)//100M
        {
            long m = i/(1024*1024);
            double d = m/(double)1024;
            rs = String.format("%.2f", d)+"G";
        }
        return  rs;
    }
    static String longToTime(long i) {
    	Calendar cal = Calendar.getInstance(); 
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");         
        cal.setTimeInMillis(i);    
        return  formatter.format(cal.getTime());
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
        if(!json.exists())    
        {    
            try {    
            	json.createNewFile();    
            } catch (IOException e) {    
                e.printStackTrace();    
            }    
        } 
        FileOutputStream fos = new FileOutputStream(json);
        PrintStream ps = new PrintStream(fos);
        ps.print(s);
        ps.close();
    }
}
