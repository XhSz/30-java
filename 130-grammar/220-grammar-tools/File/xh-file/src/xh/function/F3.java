package xh.function;

import java.io.File;
import java.util.List;

public class F3 extends File {
    public long s;
    public int n;
    public List<F3> cl;
    public F3(String s, int n) {
        super(s);
        this.s=length();
        this.n=n;
    }
}
