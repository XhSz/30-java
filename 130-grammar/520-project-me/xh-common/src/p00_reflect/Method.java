package p00_reflect;

public class Method {
	public static void main(String[] args) {
		String s= (new long[1][2][3]).getClass().getName();
		//[[[J
		System.out.println(s);
		Long[] l1 = {1l};
		System.out.println(l1.getClass().getName());
		Long[][] l2 = {{1l}};
		System.out.println(l2.getClass().getName());
		long[] l3 = {1l};
		System.out.println(l3.getClass().getName());
		System.out.println(long.class.getName());
	}
}
