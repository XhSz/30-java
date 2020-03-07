package runoob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileDemo {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        String separator=File.separator;
        String dir="temp01"+separator+"temp02";
        String fileName="hello.txt";
        File file=new File(dir, fileName);
        if(file.exists()){
            System.out.println(file.getAbsolutePath());
            System.out.println(file.getName());
            System.out.println(file.length());
        }else{
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        
        /*String dirname="c:/mysql";
        File file=new File(dirname);
        if(file.isDirectory()){
            System.out.println("Directory of "+dirname);
            String[] strings=file.list();
            for(int i=0;i<strings.length;i++){
                File f=new File(dirname+"/"+strings[i]);
                if(f.isDirectory()){
                    System.out.println(strings[i]+" is a directory");
                }else{
                    System.out.println(strings[i]+" is a file");
                }
            }
        }else{
            System.out.println(dirname+" is not a directory!");
        }*/
        
        /*System.out.println("file是一个文件："+file.isFile());
        System.out.println("file是一个目录："+file.isDirectory());
        System.out.println("file的文件名："+file.getName());
        File parentFile=file.getParentFile();
        System.out.println("parentFile是一个目录："+parentFile.isDirectory());
        System.out.println("parentFile是一个文件："+parentFile.isFile());
    
        */
        /*if(file.isDirectory()){
            System.out.println("File是一个目录！");
            if(file.exists()){
                System.out.println("文件夹已存在！");
            }else{
                System.out.println("文件夹不存在");
                file.mkdirs();
                if(file.exists())
                    System.out.println("文件夹已创建！");
            }
        }
        
        if(file.isFile()){
            System.out.println("File是一个文件!");
            if(file.exists()){
                System.out.println("文件已存在！");
            }else {
                System.out.println("文件不存在");
                File file2=new File(file.getParent());
                if(file2.exists()){
                    System.out.println("该文件的目录已存在！");
                    file.createNewFile();
                    System.out.println("文件已创建！");
                }else {
                    file2.mkdirs();
                    System.out.println("文件目录已建立！");
                    file.createNewFile();
                    System.out.println("文件已建立！");
                }
            }
        }*/
        
        /*if(file.exists()){
            System.out.println("文件夹存在！");
            if(file.isDirectory()){
                System.out.println("文件存在！");
            }else{
                file.createNewFile();
                System.out.println("文件不存在，已创建文件！");
            }
        }else{
            System.out.println("文件夹不存在！");
            File file2=new File(file.getParent());//获取指定文件的上层目录，用来创建文件夹~
            System.out.println("file2是一个目录："+file2.isDirectory()+" \nfile2是一个文件："+file2.isFile());
            file2.mkdirs();
            file.createNewFile();//文件夹不存在，对应文件肯定也不存在咯，所以要新建！
        }*/
        
        //这里插入一句，mkdirs()和mkdir()的区别在于，前者可以建立多级文件夹，后者只会建立一级文件夹。比如：
        //new File("c:/temp/user/hehe").mkdirs()会建立三级目录，直到hehe。
        //new File("c:/temp/user/hehe").mkdir()只会建立一级目录，hehe，如果前面两级目录不存在，则返回false！
        //
        
        FileOutputStream fOutputStream=new FileOutputStream(file);
        OutputStreamWriter writer=new OutputStreamWriter(fOutputStream, "utf-8");
        writer.append("中文输入+English");
        writer.append("\r\n");
        writer.append("English");
        writer.close();
        fOutputStream.close();
        
        FileInputStream fInputStream=new FileInputStream(file);
        InputStreamReader reader=new InputStreamReader(fInputStream,"UTF-8");
        StringBuilder sBuilder=new StringBuilder();
        while(reader.ready()){
            sBuilder.append((char)reader.read());
        }
        System.out.println(sBuilder.toString());
        reader.close();
        fInputStream.close();

    }

}