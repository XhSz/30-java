package p21_file_size.release.v1_5;

public class J {

    public static String H = "-";
    public static String FILE_JSON = ".json";
	public static String FAST_KEY = "00"; 
	public static String FAST_KEY_FILE = FAST_KEY+FILE_JSON;
	public static String ABSO_KEY = "01"; 
	public static String ABSO_KEY_FILE = ABSO_KEY+FILE_JSON;
    public static String[] STORE_FONT_DISK = {"2","3"};	//存储前缀-硬盘类型
    
    enum STORE_FONT{
    	LAPTOP("电脑", 1), DISK("硬盘", 2), PHONE("手机", 3); // 构造方法  
        // 成员变量  
        private String des;  
        private int index; 
        private STORE_FONT(String des, int index) {  
            this.des = des;  
            this.index = index;  

        }
    }
}
