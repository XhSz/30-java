package p03_init_compile.innerclass;

public class ProductFoodTest
{
   public void test(ProductFood p) throws Exception
   {
      System.out.println("购买了一个" + p.getName()
         + "，花掉了" + p.getPrice()+","+p.eat());
   }
   public static void main(String[] args) throws Exception
   {
      ProductFoodTest ta = new ProductFoodTest();
      // 调用test()方法时，需要传入一个Product参数，
      // 此处传入其匿名内部类的实例
      ta.test(new ProductFood()
      {
         public double getPrice() throws Exception
         {
        	 if(true)throw new Exception("eeeee");
            return 567.8;
         }
         public String getName()
         {
            return "AGP显卡";
         }
         {
       	  if(true)throw new Exception("iiiiii");
         }
      });
   }
}