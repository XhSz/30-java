package p03_init.constructor;

public class Main {
	public static void main(String[] args) {
		try {
			Employee e = (Employee)Class.forName("p03_init.constructor.Employee").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
