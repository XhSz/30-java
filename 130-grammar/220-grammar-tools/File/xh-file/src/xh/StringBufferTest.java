package xh;

public class StringBufferTest {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("123456789\n");
        StringBuilder sb2 = new StringBuilder("123456789");
        System.out.println(sb);
        //System.out.println(sb.deleteCharAt(sb.length()-1));
        System.out.println(sb2);
    }
}
