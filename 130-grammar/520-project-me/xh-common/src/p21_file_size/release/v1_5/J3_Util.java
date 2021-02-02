package p21_file_size.release.v1_5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;



public class J3_Util {
	
	public static Set<Long> SET_SIZE = new HashSet<Long>(); //文件大小的存储Set集合
	
	public static Map<Long,String> MAP_LONG = new HashMap<Long,String>(); //以文件大小为key，绝对路径为value的Map集合

	/**
     * 1.	递归调用以添加文件夹中所有文件
     * 2.	将大于阈值大小的文件添加到显示列表
     * @参数	target_path : 扫描检测的目标路径
     * @参数	stage : 文件当前的文件目录级别，当前版本暂无实质性作用
     * @参数	size : 文件显示的大小阈值
     */
    public static J2_Bean init(String target_path, int stage , long size, J4_Share j4_share){
    	
        J2_Bean r = new J2_Bean(target_path,stage); // 根据路径初始化对象
        j4_share.cur_file = r.getAbsolutePath();
        boolean noFast = true;
        
        if(r.isDirectory())
        {
        	//如果为文件夹需要遍历文件夹里的内容
            File[] files = r.listFiles();
            if (files != null){
            	if(1==J1_MainExecute.NEED_FAST) {
                	//如果目录中包括快捷key则快捷统计
	                String fastFilePath = r.getAbsolutePath().replace(
	                		J1_MainExecute.TARGET_PATH, J1_MainExecute.TARGET_PATH_00
	                	);
            		//遍历目标目录，如果存在快捷文件移动到快捷目录
	                for(File f:files){
	                	if(J.FAST_KEY_FILE.equals(f.getName())){
	                		moveFile(r.getAbsolutePath(),fastFilePath,J.FAST_KEY_FILE);
	                	}
	                }
	                //遍历快捷目录，如果存在进行快捷统计
	                fastFilePath = fastFilePath+File.separator+J.FAST_KEY_FILE;
	                File fastFile = new File(fastFilePath);
	                if(fastFile.exists()) {
                		noFast = false;
                        JSONObject json = JSONObject.parseObject(getJson(fastFile.getAbsolutePath()));
                        long s = sizeToLongParse(json.get("s").toString());
                		r.s+=s; //把子目录内的文件/文件夹的大小加到父级目录
                        if(null!=json.get("count"))
                        	r.count+=(Long.parseLong(json.get("count").toString())-1);
	                }
            	}
                if(noFast) {
	                //如果目录中不包括快捷key则常规遍历递归统计
	                r.cl= new ArrayList<J2_Bean>();
	                stage++;//文件目录级别自增
	                for(int i=0;i<files.length;i++){
	                    J2_Bean item = init(files[i].getAbsolutePath(),stage,size,j4_share);//递归调用初始化子目录
	                    r.s+=item.s; //把子目录内的文件/文件夹的大小加到父级目录
	                    r.count+=item.count;
	                    if(item.s>size){
	                        r.cl.add(item); //在显示列表中添加元素
	                    }
	                }
                }
            }
        }
        
        //记录扫描耗时
        r.time_end=System.currentTimeMillis();
        r.time=r.time_end-r.time_begin;
        
        //判断是否执行写入快捷文件
        if(noFast&&r.isDirectory()&&(
        		r.s>J1_MainExecute.FAST_SIZE||r.count>J1_MainExecute.FAST_COUNT||r.time>J1_MainExecute.FAST_TAKE
        	)) {
        	writeFastJson(r);
        }
        return r;	//返回初始化完毕的对象
    }
    
    //排序
    public static void sort(J2_Bean f3){
        if(null!=f3.cl&&f3.cl.size()>1){
        	//bubble sort the current directory 对当前目录进行气泡排序
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
            //bubble sort subdirectories 对子目录进行气泡排序
            for(J2_Bean iF : f3.cl){
                sort(iF);
            }
        }
    }
    /**
     * get result string
     * 得到结果字符串
     */
    public static StringBuilder getSb(J2_Bean f3){
    	StringBuilder child = getItemSb(f3);
        return getJsonParent(child);
    }
    public static StringBuilder getJsonParent(StringBuilder child){
        StringBuilder sb = new StringBuilder();
        sb.append("{\n")
                .append(child);
        sb.deleteCharAt(sb.length()-2);
        sb.append("}");
        return sb;
    }
    /**
     * get item result string
     * 得到元素结果字符串
     */
    public static StringBuilder getItemSb(J2_Bean f3){
        StringBuilder sb = new StringBuilder();
        sb.append(space(f3.n))
                .append("\"")
                .append(longToSize(f3.s))
                .append("---")
                .append(f3.s)
                .append("---")
                .append(f3.getName())
                .append("---")
                .append(longToRead(f3.count))
                .append("---")
                .append(f3.count)
                .append("---")
                .append(longToTime(f3.time))
                .append("---")
                .append(f3.time);
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
        String rs = "<"+longToSizeParse(t);
        if(i==0)return "empty";
        if (i > t)
        {
            long m = i/(1024*1024);
            double d = m/(double)1024;
            rs = String.format("%.2f", d)+"G";
        }
        return  rs;
    }
    public static String longToSizeParse(long t){
        StringBuilder sb = new StringBuilder();
        if(t>1024*1024*1024){
            sb.append(t/(1024*1024*1024)).append("G");
            t=t%(1024*1024*1024);
        }
        if(t>1024*1024){
            sb.append(t/(1024*1024)).append("M");
            t=t%(1024*1024);
        }
        if(t>1024){
            sb.append(t/1024).append("K");
            t=t%1024;
        }
        sb.append(t).append("B");
        return sb.toString();
    }
    public static long sizeToLongParse(String str){
    	long result = 0l;
    	long unit = 1l;
    	if(str.endsWith("G")) {
    		unit = 1024*1024*1024;
    	}else if(str.endsWith("M")) {
        		unit = 1024*1024;
        }else if(str.endsWith("K")) {
    		unit = 1024;
        }else {
        	return Long.parseLong(str);
        }
    	str = String.valueOf((Float.parseFloat(str.substring(0, str.length()-1))*100));
		result = Long.parseLong(str.substring(0, str.length()-2));
		result = result*unit/100;
        return result;
    }
    public static long strToLong(String str) {
    	long re = 1l;
    	String[] strA = str.split("\\*");
    	for(String stra: strA) {
    		re*= Long.parseLong(stra);
    	}
    	return re;
    }
    //把绝对时间转化为时分秒显示
    public static String longToTime(long t){
        StringBuilder sb = new StringBuilder();
        if(t>60*60*1000){
            sb.append(t/(60*60*1000)).append("h");
            t=t%(60*60*1000);
        }
        if(t>60*1000){
            sb.append(t/(60*1000)).append("m");
            t=t%(60*1000);
        }
        if(t>1000){
            sb.append(t/1000).append("s");
            t=t%1000;
        }
        sb.append(t).append("ms");
        return sb.toString();
    }
    public static void main(String[] args) {
		System.out.println(longToRead(18149l));
		System.out.println(longToRead(18150l));
	}
    //把绝对数字转化为易读数字
    public static String longToRead(long t){
        StringBuilder sb = new StringBuilder();
        if(t>10000*10*1000){
            sb.append(t/(10000*10*1000)).append("y");
            t=t%(10000*10*1000);
        }
        if(t>10*1000){
            sb.append(t/(10*1000)).append("w");
            t=t%(10*1000);
        }
        if(t>1000){
            sb.append(t/1000).append("q");
            t=t%1000;
        }
        sb.append(t);
        return sb.toString();
    }
    /*设置文件大小的Set集合*/
    public static void setDulicate(J2_Bean root) {
    	setSet(root);
    }
    public static void setSet(J2_Bean root) {
    	SET_SIZE.add(root.s);
    	MAP_LONG.put(root.s, root.getAbsolutePath());
    	if(null!=root.cl&&root.cl.size()>0)
    		for(J2_Bean item:root.cl) {
    			//如果该大小文件曾经出现，且不存在父子目录关系，则标记该元素为重复，并把与之相同文件的绝对路径存入元素
    			if(SET_SIZE.contains(item.s)&&(!root.getAbsolutePath().startsWith(MAP_LONG.get(item.s)))) {
    				item.isDulicate = true;
    				item.str_dulicate = MAP_LONG.get(item.s);
    			}else setSet(item);
    		}
    }
    public static String getJson(String path) {
        String jsonStr = "";
        try {
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            Reader reader = new InputStreamReader(new FileInputStream(file),"Utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (Exception e) {
            return null;
        }
    }
    public static void renameFile(String filePath, String pre, String fileName, String take) {
        String oldFileName = filePath+"/"+fileName+J.FILE_JSON;
        File oldFile = new File(oldFileName);
        String newFileName = filePath+"/"+pre+fileName+J.H+take+J.FILE_JSON;
        File newFile = new File(newFileName);
        if (oldFile.exists() && oldFile.isFile()) {
            oldFile.renameTo(newFile);
        }
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
    
    //执行写快捷文件
    public static void writeFastJson(J2_Bean bean){
    	
        StringBuilder sb = new StringBuilder();
    	J2_FastBean fb = new J2_FastBean(bean);
//    	System.out.println("bean路径="+bean.getAbsolutePath());
//    	System.out.println("TARGET_PATH路径="+J1_MainExecute.TARGET_PATH);
//    	System.out.println("TARGET_PATH_00路径="+J1_MainExecute.TARGET_PATH_00);
    	String fastFileFolder = bean.getAbsolutePath().replace(
        		J1_MainExecute.TARGET_PATH, J1_MainExecute.TARGET_PATH_00
        	);
    	//1. 如果目标目录存在存量快捷文件删除
    	File targetFile = new File(bean.getAbsolutePath()+File.separator+J.FAST_KEY_FILE);
    	if(targetFile.exists())targetFile.delete();
    	//2.1 快捷文件目录不存在，则先创建目录
        if (!new File(fastFileFolder).exists()){
//        	System.out.println("快捷目录文件夹，开始创建！"+fastFileFolder);
            if(!new File(fastFileFolder).mkdirs()){
                System.err.println("快捷目录文件夹，创建失败！");
            }
//        	System.out.println("快捷目录文件夹，创建成功！"+fastFileFolder);
        }
        //2.2 写入文件
    	String key_json = fastFileFolder+File.separator+J.FAST_KEY_FILE;
        File json_file = new File(key_json);
    	if(0!=J1_MainExecute.NEED_FAST&&json_file.exists()) {
            JSONObject json = JSONObject.parseObject(getJson(json_file.getAbsolutePath()));
            long s = sizeToLongParse(json.get("s").toString());
	        sb = getJsonParent(getFastJsonItem(1,fb,json.getJSONObject("version")));
    	}else {
	        sb = getJsonParent(getFastJsonItem(0,fb,null));
    	}
    	try {
			pringToJson(sb,key_json);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        //3. 同时写入绝对目录 
    	String absoFileFolder = bean.getAbsolutePath().replace(
        		J1_MainExecute.TARGET_PATH, J1_MainExecute.TARGET_PATH_01
        	);
    	//3.1 绝对文件目录不存在，则先创建目录
        if (!new File(absoFileFolder).exists()){
            if(!new File(absoFileFolder).mkdirs()){
                System.err.println("绝对目录文件夹，创建失败！");
            }
        }
    	String abso_json = absoFileFolder+File.separator+J.ABSO_KEY_FILE;
    	//3.2 写入绝对目录
	    sb = getJsonParent(getAbsoJsonItem(fb));
    	try {
			pringToJson(sb,abso_json);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    public static StringBuilder getFastJsonItem(int space_width,J2_FastBean fb,JSONObject jsonObj){
    	
        StringBuilder sb = new StringBuilder();
        if(0==space_width||1==space_width) {
          sb.append(space(1)).append("\"date\":\"").append(fb.date).append("\",\n")
            .append(space(1)).append("\"time\":\"").append(fb.time).append("\",\n")
            .append(space(1)).append("\"take\":\"").append(fb.take).append("\",\n")
            .append(space(1)).append("\"s\":\"").append(fb.s).append("\",\n")
            .append(space(1)).append("\"count\":\"").append(fb.count).append("\",\n")
            .append(space(1)).append("\"take_read\":\"").append(fb.take_read).append("\",\n")
            .append(space(1)).append("\"size_read\":\"").append(fb.size_read).append("\",\n")
            .append(space(1)).append("\"count_read\":\"").append(fb.count_read).append("\",\n")
            .append(space(1)).append("\"version\":{\n")
            .append(getFastJsonItem(2,fb,jsonObj));
          if(1==space_width) {
        	  for(String str:jsonObj.keySet()){
        		  sb.append(space(2)).append("\"").append(str).append("\"")
        		  .append(":").append(jsonObj.get(str)).append(",\n");
        	  }
//        	  String version = jsonObj.toString();
//        	  version = version.substring(1);
//        	  sb.append(version);
        	  sb.deleteCharAt(sb.length()-2);
          }else {
        	  sb.deleteCharAt(sb.length()-2);
          }
          sb.append(space(1))
          	.append("},\n");
        }else if(2==space_width){
            sb.append(space(space_width))
            .append("\"").append(fb.date).append(".").append(fb.time)
            .append("\":{\"take\":\"").append(fb.take)
            .append("\",\"s\":\"").append(fb.s)
            .append("\",\"count\":\"").append(fb.count)
            .append("\",\"take_read\":\"").append(fb.take_read)
            .append("\",\"size_read\":\"").append(fb.size_read)
            .append("\",\"count_read\":\"").append(fb.count_read)
            .append("\"},\n");
        }
        return sb;
    }
    public static StringBuilder getAbsoJsonItem(J2_FastBean fb){
    	
        StringBuilder sb = new StringBuilder();
        {
          sb.append(space(1)).append("\"s\":\"").append(fb.s).append("\",\n")
            .append(space(1)).append("\"count\":\"").append(fb.count).append("\",\n")
            .append(space(1)).append("\"size_read\":\"").append(fb.size_read).append("\",\n")
            .append(space(1)).append("\"count_read\":\"").append(fb.count_read).append("\",\n")
            ;
        }
        return sb;
    }
    //移动文件夹
    public static boolean moveFiles(String oldPath, String newPath){
        boolean result = true;
        String[] filePaths = new File(oldPath).list();
 
        if (filePaths != null && filePaths.length > 0){
            if (!new File(newPath).exists()){
                if(!new File(newPath).mkdirs()){
                    System.out.println("文件夹创建失败");
                    return false;
                }
            }
 
            try{
                for (String filePath : filePaths) {
                    if (new File(oldPath + File.separator + filePath).isDirectory()){
                        moveFiles(oldPath + File.separator + filePath, newPath + File.separator + filePath);
                    }else if (new File(oldPath + File.separator + filePath).isFile()){
                        //复制文件到另一个目录
                        copyFile(oldPath + File.separator + filePath, newPath + File.separator + filePath);
                        //移动文件至另一个目录
                        if(!new File(oldPath + File.separator + filePath).renameTo(new File(newPath + File.separator + filePath))){
                            System.out.println(oldPath + File.separator + filePath +"文件复制失败");
                            result = false;
                            break;
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
 
        return result;
    }
    //移动文件
    public static boolean moveFile(String oldPath, String newPath, String fileName){
        boolean result = true;
        
        if(!new File(newPath).exists()){
            if(!new File(newPath).mkdirs()){
                System.out.println("文件夹创建失败");
                    return false;
                }
        }
        File newFile = new File(newPath+File.separator+fileName);
        if(newFile.exists())newFile.delete();
        try{
//        	copyFile(oldPath+File.separator+fileName, newPath+File.separator+fileName);//复制文件到另一个目录
            if(!new File(oldPath+File.separator+fileName).renameTo(newFile)){//移动文件至另一个目录
                System.out.println(oldPath +"文件移动失败");
                result = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public static void copyFile(String oldPath, String newPath) throws IOException {
        File oldFile = new File(oldPath);
        File file = new File(newPath);
        FileInputStream in = new FileInputStream(oldFile);
        FileOutputStream out = new FileOutputStream(file);;
 
        byte[] buffer=new byte[2097152];
 
        while((in.read(buffer)) != -1){
            out.write(buffer);
        }
    }
    public static String getFolder(String in,String key) {
    	J.STORE_FONT curStoreType = J.STORE_FONT.LAPTOP;
    	if(J1_MainExecute.STORE.startsWith("0")||J1_MainExecute.STORE.startsWith("1"))
    		curStoreType  = J.STORE_FONT.LAPTOP;
    	if(J1_MainExecute.STORE.startsWith("2")||J1_MainExecute.STORE.startsWith("3"))
    		curStoreType  = J.STORE_FONT.DISK;
    	if(J.STORE_FONT.LAPTOP.equals(curStoreType))
    		return in.substring(0, 2)+"\\"+J1_MainExecute.STORE+in.substring(0, 1)+J.H+key+in.substring(2);
    	else if(J.STORE_FONT.DISK.equals(curStoreType))
    		return in.substring(0, 2)+"\\"+J1_MainExecute.STORE+J.H+key+in.substring(2);
    	else
    		return in.substring(0, 2)+"\\"+J1_MainExecute.STORE+in.substring(0, 1)+J.H+key+in.substring(2);
    }
}
