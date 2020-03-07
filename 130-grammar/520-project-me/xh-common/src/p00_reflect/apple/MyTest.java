package p00_reflect.apple;

/**
 * Created by gmq on 2015/9/10.
 */
public class MyTest
{
 
    @MyAnnotation(hello = "Hello,Beijing",world = "Hello,world")
    public void output() {
        System.out.println("method output is running ");
    }

}
