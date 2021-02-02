package p22_Thread;

class J4_Timer extends Thread
{
    private String name;
    long doer;
    Share share;
    
    J4_Timer(String name,long doer)
    {
        this.name = name;
        this.doer = doer;
    }
    
    J4_Timer(String name,Share share)
    {
        this.name = name;
        this.share = share;
    }

    public void run()
    {
        try {
        	while(true) {
            	System.out.println("当前count值="+share.count);
    			Thread.sleep(10l);
        	}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}
