package p03_init_compile.innerclass;

public class AnonymousTest
{
   public void test(Product p)
   {
      try {
		System.out.println("购买了一个" + p.getName()
		     + "，花掉了" + p.getPrice());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   public static void main(String[] args)
   {
      AnonymousTest ta = new AnonymousTest();
      // 调用test()方法时，需要传入一个Product参数，
      // 此处传入其匿名内部类的实例
      ta.test(new Product()
      {
         public double getPrice()
         {
            return 567.8;
         }
         public String getName()
         {
            return "AGP显卡";
         }
      });
   }
}