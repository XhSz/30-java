package p22_Thread;

public class ThreadDemo {
    public static void main(String[] args)
    {
        Demo d1=new Demo("zhangsan");

        Demo d2=new Demo("lisi");
        d1.start();
        d2.start();

        System.out.println("当前执行run方法的线程名称"+Thread.currentThread().getName());
    }
}
