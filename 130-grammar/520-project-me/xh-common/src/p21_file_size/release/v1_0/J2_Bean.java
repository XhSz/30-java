package p21_file_size.release.v1_0;

import java.io.File;
import java.util.List;

public class J2_Bean extends File {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1192896344364572435L;
	public long s;	//File Size
    public int n;	//Current File Stage On Root
    public List<J2_Bean> cl;	//Children List
    public J2_Bean(String s, int n) {
        super(s);
        this.s=length();
        this.n=n;
    }
}
