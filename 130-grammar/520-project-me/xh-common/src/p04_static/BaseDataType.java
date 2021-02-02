package p04_static;

import org.openjdk.jol.info.ClassLayout;

public class BaseDataType {
//	int a;
//	long b;
//	boolean c;
	public static void main(String[] args) {
		BaseDataType m  = new BaseDataType();
		System.out.println(ClassLayout.parseInstance(m).toPrintable());
	}
}
