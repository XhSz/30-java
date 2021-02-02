package p21_file_size.release.v1_5;

import java.io.File;
import java.util.List;

public class J2_Bean extends File {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1192896344364572435L;
	public long s;	//File Size 文件/文件夹大小
	public long count;	//File Count 文件/文件夹个数
	public long time;	//Scan Time 文件夹扫描耗时时间
	public long time_begin;	//Scan Begin Time 文件夹扫描起始时间
	public long time_end;	//Scan End Time 文件夹扫描结束时间
    public int n;	//Current File Stage On Root 相对于根目录的文件层级，根目录为1
    public boolean isDulicate;	//judge whether the file is dulicate
    public String str_dulicate;	//dulicate string
    public List<J2_Bean> cl;	//Children List
    public J2_Bean(String path, int n) {
        super(path);
        if (this.isFile())
        	 this.s=length();
        else
        	this.s=0;
        this.n=n;
        this.isDulicate=false;
        this.count=1;
        this.time_begin=System.currentTimeMillis();
    }
}
