package p00_reflect.proxy;


public class Car implements IVehical {
    public void run() {
        System.out.println("Car会跑");
    }
    public int get(int in) {
        System.out.println("get,"+in);
        return in;
    }
}
