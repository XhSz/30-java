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
        
        /*System.out.println("file��һ���ļ���"+file.isFile());
        System.out.println("file��һ��Ŀ¼��"+file.isDirectory());
        System.out.println("file���ļ�����"+file.getName());
        File parentFile=file.getParentFile();
        System.out.println("parentFile��һ��Ŀ¼��"+parentFile.isDirectory());
        System.out.println("parentFile��һ���ļ���"+parentFile.isFile());
    
        */
        /*if(file.isDirectory()){
            System.out.println("File��һ��Ŀ¼��");
            if(file.exists()){
                System.out.println("�ļ����Ѵ��ڣ�");
            }else{
                System.out.println("�ļ��в�����");
                file.mkdirs();
                if(file.exists())
                    System.out.println("�ļ����Ѵ�����");
            }
        }
        
        if(file.isFile()){
            System.out.println("File��һ���ļ�!");
            if(file.exists()){
                System.out.println("�ļ��Ѵ��ڣ�");
            }else {
                System.out.println("�ļ�������");
                File file2=new File(file.getParent());
                if(file2.exists()){
                    System.out.println("���ļ���Ŀ¼�Ѵ��ڣ�");
                    file.createNewFile();
                    System.out.println("�ļ��Ѵ�����");
                }else {
                    file2.mkdirs();
                    System.out.println("�ļ�Ŀ¼�ѽ�����");
                    file.createNewFile();
                    System.out.println("�ļ��ѽ�����");
                }
            }
        }*/
        
        /*if(file.exists()){
            System.out.println("�ļ��д��ڣ�");
            if(file.isDirectory()){
                System.out.println("�ļ����ڣ�");
            }else{
                file.createNewFile();
                System.out.println("�ļ������ڣ��Ѵ����ļ���");
            }
        }else{
            System.out.println("�ļ��в����ڣ�");
            File file2=new File(file.getParent());//��ȡָ���ļ����ϲ�Ŀ¼�����������ļ���~
            System.out.println("file2��һ��Ŀ¼��"+file2.isDirectory()+" \nfile2��һ���ļ���"+file2.isFile());
            file2.mkdirs();
            file.createNewFile();//�ļ��в����ڣ���Ӧ�ļ��϶�Ҳ�����ڿ�������Ҫ�½���
        }*/
        
        //�������һ�䣬mkdirs()��mkdir()���������ڣ�ǰ�߿��Խ����༶�ļ��У�����ֻ�Ὠ��һ���ļ��С����磺
        //new File("c:/temp/user/hehe").mkdirs()�Ὠ������Ŀ¼��ֱ��hehe��
        //new File("c:/temp/user/hehe").mkdir()ֻ�Ὠ��һ��Ŀ¼��hehe�����ǰ������Ŀ¼�����ڣ��򷵻�false��
        //
        
        FileOutputStream fOutputStream=new FileOutputStream(file);
        OutputStreamWriter writer=new OutputStreamWriter(fOutputStream, "utf-8");
        writer.append("��������+English");
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