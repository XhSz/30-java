package p02_paradigm;

public class T2 implements I2{
/*
 * I1 is a raw type. References to generic type I1<I,P,D> should be parameterized
 * 		  未经加工的
 * I1是原始类型。对泛型类型i1<i，p，d>的引用应该参数化
 * (non-Javadoc)
 * @see p02_paradigm.I1#beforeTranProcess(java.lang.String, java.lang.Object, java.lang.Object)
 */
	public void beforeTranProcess(String paramString, Object paramI,
			Object paramP) {
		// TODO Auto-generated method stub
		
	}

	public void beforeTranProcess(java.lang.String paramString, Object paramI,
			Object paramP,Object f) {
		// TODO Auto-generated method stub
		System.out.println();
	}

	public static void main(String[] args) {
		T2 t1 = new T2();
		t1.beforeTranProcess("1", 2, 3,4);
	}
}
