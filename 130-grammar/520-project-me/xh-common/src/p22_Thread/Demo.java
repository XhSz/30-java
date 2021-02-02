package p22_Thread;

class Demo extends Thread
{
    private String name;
    Demo(String name)
    {
        this.name = name;
    }

    public void run()
    {
        for(int x=0;x<9;x++)
        {
            System.out.println(name+"----"+x+"当前执行run方法的线程名称"+Thread.currentThread().getName());
        }
    }
}
