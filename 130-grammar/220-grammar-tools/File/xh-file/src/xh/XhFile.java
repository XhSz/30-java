package xh;

import java.io.File;

public class XhFile {
    File file;
    long length;
    String size;
    String f_name;
    StringBuilder sb;
    int stage;

    public XhFile(String s){
        file = new File(s);
        length = file.length();
        sb = new StringBuilder();
    }
    public XhFile(String s,int parent_stage){
        file = new File(s);
        stage = parent_stage+1;
        length = file.length();
        sb = new StringBuilder();
    }
    public StringBuilder finshInit(){
        setSize(longToSize(length));
        setF_name(file.getName()+"------"+getSize());
        if(file.isDirectory())sb.deleteCharAt(sb.length()-1).append(",");
        sb.append("\"").append(getF_name()).append("\":\"\"");
        return sb;
    }
    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }
    public String getF_name() {
        return f_name;
    }
    public void setF_name(String f_name) {
        this.f_name = f_name;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public long getLength() {
        return length;
    }
    public void setLength(long length) {
        this.length = length;
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
    public StringBuilder getSb() {
        return sb;
    }
    public void setSb(StringBuilder sb) {
        this.sb = sb;
    }
    public int getStage() {
        return stage;
    }
    public void setStage(int stage) {
        this.stage = stage;
    }
}
