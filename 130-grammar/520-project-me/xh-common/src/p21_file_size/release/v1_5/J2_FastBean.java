package p21_file_size.release.v1_5;

import java.text.SimpleDateFormat;
import java.util.Date;

public class J2_FastBean{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1192826344368572435L;
	public String date;	
	public String time;	
	public long take;	//文件夹扫描耗时时间
	public long s;		//文件夹大小
	public long count;	//文件/文件夹数量
	public String take_read;	//文件夹扫描耗时时间
	public String size_read;	//文件夹大小
	public String count_read;	//文件/文件夹数量
	public J2_FastBean(J2_Bean bean) {
        SimpleDateFormat fmdate = new SimpleDateFormat("yyMMdd");
        this.date = fmdate.format(new Date());
        fmdate = new SimpleDateFormat("HHmm");
        this.time = fmdate.format(new Date());
        this.take = bean.time;
        this.take_read = J3_Util.longToTime(bean.time);
        this.s = bean.s;
        this.size_read = J3_Util.longToSizeParse(bean.s);
        this.count = bean.count;
        this.count_read = J3_Util.longToRead(bean.count);
	}
}
