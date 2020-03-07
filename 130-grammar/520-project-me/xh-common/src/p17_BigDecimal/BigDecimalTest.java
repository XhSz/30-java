package p17_BigDecimal;

import java.math.BigDecimal;


public class BigDecimalTest {
	
	public static void main(String[] args) {
		divide();
		divide_bug();
	}

	public static void divide_bug(){
		// Non-terminating decimal expansion; no exact representable decimal result.
		BigDecimal d1 = new BigDecimal("100");
		BigDecimal d2 = new BigDecimal("3");
		BigDecimal d3 = d1.divide(d2);
	}
	public static void divide(){
		BigDecimal d1 = new BigDecimal("100");
		BigDecimal d2 = new BigDecimal("3");
		BigDecimal d3 = d1.divide(d2,3,BigDecimal.ROUND_DOWN);//指定三位小数,接近零的舍入模式。
		System.out.println(d3);
		/*
		 * 	1、ROUND_UP
				舍入远离零的舍入模式
				在丢弃非零部分之前始终增加数字并且始终对非零舍弃部分前面的数字+1
			2、ROUND_DOWN
				接近零的舍入模式。
				在丢弃某部分之前始终不增加数字(从不对舍弃部分前面的数字加1，即截短)
			3、ROUND_CEILING
				接近正无穷大的舍入模式。
				如果 BigDecimal 为正，则舍入行为与 ROUND_UP 相同;
				如果为负，则舍入行为与 ROUND_DOWN 相同。
			4、ROUND_FLOOR
				接近负无穷大的舍入模式。
				如果 BigDecimal 为正，则舍入行为与 ROUND_DOWN 相同;
				如果为负，则舍入行为与 ROUND_UP 相同。
		   *5、ROUND_HALF_UP
				四舍五入模式
			6、ROUND_HALF_DOWN
				五舍六入模式
			7、ROUND_HALF_EVEN
				向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则向相邻的偶数舍入。
				如果舍弃部分左边的数字为奇数，则舍入行为与 ROUND_HALF_UP 相同;
				如果为偶数，则舍入行为与 ROUND_HALF_DOWN 相同
			8、ROUND_UNNECESSARY
				断言请求的操作具有精确的结果，因此不需要舍入。
				如果对获得精确结果的操作指定此舍入模式，则抛出ArithmeticException。
		 */
	}
	
}
