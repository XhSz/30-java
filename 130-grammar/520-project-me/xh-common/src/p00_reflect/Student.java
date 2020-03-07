package p00_reflect;

import java.lang.annotation.Annotation;

@MyAnnotation
public class Student{

    public static void main(String[] args) {
        Student student = new Student();
        Class studentClass = student.getClass();
        //1-Class.isAnnotation()=本身是否是注解类
        boolean isAnnotation = studentClass.isAnnotation();
        log(studentClass.getSimpleName()+"是否是注解类:"+isAnnotation+"");
        //2-Class.isAnnotationPresent(Class)=是否使用某注解
        boolean isContainAnnotation = studentClass.isAnnotationPresent(MyAnnotation.class);
        log(studentClass.getSimpleName()+"是否包含MyAnnotation注解类:"+isContainAnnotation);
        //3-Class.getAnnotation(Class)=获取正在使用的注解
        Annotation annotation = studentClass.getAnnotation(MyAnnotation.class);
        if (annotation != null) {
            log("获取指定的MyAnnotation类:"+annotation.toString());
        }
        //4-Class.getAnnotations()=获取某类的所有注解
        Annotation[] annotations = studentClass.getAnnotations();
        if (annotations.length > 0){
            log("获取注解集合中的第一个元素:"+annotations[0].toString());
        }
    }

    private static void log(String value) {
        System.out.println(value);
    }
}