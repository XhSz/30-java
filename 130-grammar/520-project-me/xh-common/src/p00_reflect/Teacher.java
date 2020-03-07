package p00_reflect;

public class Teacher {
    @CherryAnnotation(name = "cherry-peng",age = 23,score = {99,66,77}, array = { 0 })
    public void study(int times){
    	//System.out.println(age);
        for(int i = 0; i < times; i++){
            System.out.println("Good Good Study, Day Day Up!");
        }
    }
}