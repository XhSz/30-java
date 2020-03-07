package p04_static;

public class StaticTest {
	public static void main(String args[]){
		//http://www.2cto.com/kf/201502/375549.html
		
		//Cannot make a static reference to the non-static method method() from the type StaticTest
        //method(); //会出错，提示你讲method方法改成静态的
        method2(); //调用方法正确
        /*
         * No enclosing instance of type StaticTest is accessible. 
         * Must qualify the allocation with an enclosing instance of type StaticTest 
         * (e.g. x.new A() where x is an instance of StaticTest).
         */
        //new Test2().method(); //出错
//        (new Test2()).method();
//        Test2 t2 = new Test2();
//        t2.method();
	      Test3 t3 = new Test3();
	      t3.method();
	      new Test3().method();
    }
    public void method(){
        System.out.println("HelloWorld");
    }
    public static void method2(){
        System.out.println("HelloWorld");
    }
	public class Test2{
		public Test2(){
			
		}
	    public void method(){
	        System.out.println("HelloWorld");
	    }
	}
	public static class Test3{
	    public void method(){
	        System.out.println("HelloWorld");
	    }
	}
}