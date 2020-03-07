package p00_reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class VehicalInvacationHandler implements InvocationHandler {
	 
    private final IVehical vehical;
    public VehicalInvacationHandler(IVehical vehical){
        this.vehical = vehical;
    }
 
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //System.out.println("---------invoke begin-------,proxy="+proxy+",method="+method+",args="+args);
        System.out.println("---------invoke begin-------");
        Object invoke = method.invoke(vehical, args);
        //System.out.println("---------invoke end-------,invoke="+invoke);
        System.out.println("---------invoke end-------");
 
        return invoke;
    }
}
