package p00_reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        IVehical car = new Car();
 
        IVehical vehical = (IVehical)Proxy.newProxyInstance(car.getClass().getClassLoader(), Car.class.getInterfaces(), new VehicalInvacationHandler(car));

		System.out.println("vehical2,init,begin;");
		/*
        IVehical vehical2 = (IVehical)Proxy.newProxyInstance(car.getClass().getClassLoader(), Car.class.getInterfaces(), 
        			new InvocationHandler(){
						@Override
						public Object invoke(Object proxy, Method method,
								Object[] args) throws Throwable {
							System.out.println("proxy="+proxy);
							System.out.println("method="+method);
							System.out.println("args="+args);
							
							return null;
						}
			
					}
        		);
        
		System.out.println("vehical2,init,end;");
		*/
        vehical.run();
        int res = vehical.get(6);
        System.out.println("res="+res);
    }
}
