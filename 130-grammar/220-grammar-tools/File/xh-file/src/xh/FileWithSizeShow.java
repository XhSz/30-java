package xh;

public class FileWithSizeShow extends FileWithSize {
    String f_name;
    StringBuilder sb;
    int stage;
    public FileWithSizeShow(String pathname,int parent_stage) {
        super(pathname);
        stage = parent_stage+1;
        sb = new StringBuilder();
    }
    public StringBuilder finshInit(){
        setSizeShow(XhFileCommon.longToSize(size));
        if(isDirectory())sb.deleteCharAt(sb.length()-1).append(",");
        sb.append("\"").append(getName()+"------"+getSizeShow()).append("\":\"\"");
        return sb;
    }
}
