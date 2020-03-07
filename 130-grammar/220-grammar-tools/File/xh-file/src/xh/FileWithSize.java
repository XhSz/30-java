package xh;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileWithSize extends File {
    public FileWithSize(String pathname) {
        super(pathname);
        size=0;
        //childList = new FileWithSize[];
        childList= new ArrayList<FileWithSize>();
    }
    public long size;
    public String sizeShow;
    public FileWithSize[] childArray;
    public List<FileWithSize> childList;

    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }
    public String getSizeShow() {
        return sizeShow;
    }
    public void setSizeShow(String sizeShow) {
        this.sizeShow = sizeShow;
    }
}
