package p00_reflect;

public @interface CherryAnnotation {
	public String name();
	int age() default 18;
	int[] array();
	public int[] score();
}

