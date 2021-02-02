package p21_file_size.release.v1_3;

class J4_Timer extends Thread
{
	public static String END = "END";
    long hold;
    J4_Share j4_share;
    
    J4_Timer(J4_Share j4_share)
    {
        this.j4_share = j4_share;
        this.hold = 10l;
    }
    
    J4_Timer(J4_Share j4_share,long hold)
    {
        this.j4_share = j4_share;
        this.hold = hold;
    }

    public void run()
    {
        try {
        	while(!END.equals(j4_share.cur_file)) {
            	if(100l>hold)
            		System.out.println("路径="+j4_share.cur_file);
            	else
            		System.out.println(j4_share.cur_file);
    			Thread.sleep(hold);
        	}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}
