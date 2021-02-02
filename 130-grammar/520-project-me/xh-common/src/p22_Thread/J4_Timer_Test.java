package p22_Thread;

class J4_Timer_Test extends Thread
{
	public static long RESULT = 10*10000*10000l;
			
    public static void main(String[] args)
    {
    	long doer = 1l;
    	Share share = new Share(doer);
    	
        J4_Timer d1=new J4_Timer("zhangsan",share);
        d1.start();
        
        /**
         * 累加1万次左右线程启动；
         * 0.01s累加1000万次；
         * 
         * 	0.001s=1ms 
         	当前count值=13097
			当前count值=31464011		3千万/ms
			当前count值=75538064
         */
    	for(;share.count<RESULT;share.count++) {
    		
    	}
    	System.err.println(share.count);
        System.out.println("当前执行run方法的线程名称"+Thread.currentThread().getName());
    }
}
