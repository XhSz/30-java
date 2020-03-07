package xh;

import java.io.File;

import javax.swing.JTextArea;

public class CountFile extends Thread{
    private static long totalNum=0;
    private static long totalSize=0;
    private static int	stage=0; 
    private static JTextArea jta=new JTextArea();
    private static String path;
    
    @SuppressWarnings("static-access")
	public CountFile(JTextArea jta,String path){
    	this.jta=jta;
    	this.jta.setText(null);
    	this.path=path;		
    }
       
    @Override
	public void run() {
        long a = System.currentTimeMillis();
        refreshFileList(stage,path);
        if(totalNum!=0)
        	  jta.append("\n总文件数:"+totalNum+"   总平均长度:"+totalSize/totalNum+" Bytes\n");	
        else
        	  jta.append("\n总文件数:"+totalNum+"   总平均长度:"+0+" Bytes\n");	
        jta.append("耗时 :"+(System.currentTimeMillis() - a)*0.001+"秒\n");
        jta.setCaretPosition(jta.getText().length());
	}

	public static void refreshFileList(int stage,String strPath) {
    	long count=0;
    	long length=0;
    	File dir = new File(strPath); 
        File[] files = dir.listFiles(); 
               
        if (files == null) 
            return; 
        for (int i = 0; i < files.length; i++) { 
        	if(files[i].isDirectory()){
        		refreshFileList(stage+1,files[i].getAbsolutePath()); 
            }
        	else{                
            	length+=files[i].length();
        		totalSize+=files[i].length();
            	totalNum++;
            	count++;  
            }
        }
        if(count!=0)
        	jta.append("第"+stage+"级目录:"+strPath+"   文件数:"+count+"   平均长度:"+length/count+" Bytes\n");
        else
        	jta.append("第"+stage+"级目录:"+strPath+"   文件数:"+count+"   平均长度:"+0+" Bytes\n");
        jta.setCaretPosition(jta.getText().length());
    }
}