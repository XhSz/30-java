package p21_file_size.release.v1_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class J3_Util {

	public static Set<Long> SET_SIZE = new HashSet<Long>(); 
	public static Map<Long,String> MAP_LONG = new HashMap<Long,String>();
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
     * get item result string
     */
    public static StringBuilder getItemSb(J2_Bean f3){
        StringBuilder sb = new StringBuilder();
        sb.append(space(f3.n))
                .append("\"")
                .append(longToSize(f3.s))
                .append("------")
                .append(f3.getName());
        if(f3.isDulicate)
            sb.append("------").append("dulicate").append("------").append(f3.str_dulicate);
        sb.append("\":");
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
    static String longToSize(long i) {
    	return longToSize(i,  J1_MainExecute.THRESHOLD_PRINT);
    }
    static String longToSize(long i,long t) {
        String rs = "<0.1G";
        if(i==0)return "empty";
        if (i > t)
        {
            long m = i/(1024*1024);
            double d = m/(double)1024;
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
    public static void setDulicate(J2_Bean root) {
    	setSet(root);
    }
    public static void setSet(J2_Bean root) {
    	SET_SIZE.add(root.s);
    	MAP_LONG.put(root.s, root.getAbsolutePath());
    	if(null!=root.cl&&root.cl.size()>0)
    		for(J2_Bean item:root.cl) {
    			if(SET_SIZE.contains(item.s)&&(!root.getAbsolutePath().startsWith(MAP_LONG.get(item.s)))) {
    				item.isDulicate = true;
    				item.str_dulicate = MAP_LONG.get(item.s);
    			}else setSet(item);
    		}
    }
}
