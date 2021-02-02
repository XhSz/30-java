import java.util.concurrent.ThreadLocalRandom;



public class Main {
	public static void main(String[] args) {
//		System.out.println(lpad("", 10, "9"));
		System.out.println(ThreadLocalRandom.current().nextLong(0l,2l));
	}

/*     */   public static String lpad(String s, int i, String s1)
/*     */   {
/* 236 */     if (s == null)
/* 237 */       return null;
/* 238 */     if ((s1 == null) || (s1.length() <= 0))
/*     */     {
/* 240 */       throw new IllegalArgumentException(); 
				}
/* 241 */     if (i <= 0)
/*     */     {
/* 243 */       throw new IllegalArgumentException(); 
				}
/* 244 */     if (i <= s.length()) {
/* 245 */       return s.substring(0, i);
/*     */     }
/* 247 */     StringBuffer sb = new StringBuffer(s);
/* 248 */     char[] c1 = s1.toCharArray();
/* 249 */     int index = 0;
/* 250 */     for (; sb.length() < i;)
/*     */     {
/* 252 */       index = 0;
/* 253 */       if ((sb.length() < i) && (index < c1.length))
/*     */       {
/* 255 */         sb.insert(0, c1[(index++)]);
/*     */       }
/*     */     }
/* 258 */     return sb.toString();
/*     */   }
/*     */   

}