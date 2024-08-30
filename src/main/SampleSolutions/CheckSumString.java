public class CheckSumString {
  public static String[] possStrings = {
            "afcytqdkuyhckdkrhxhwswkdlbqbdojqtntbhbukbypgxbtickqjxgfysob", "afcytqdkuyhckdkrhxutswkdwbqbdojqtnhblbhkbypgxbtickqjxgfysob", 
            "afcytqdkuyhckdkrhxhwswkdtbqbdojqtnhblbukbypgxbtickqjxgfysob", "afcytqdkuyhckdkrhxhwswkdtbqbdotqjnublbhkbypgxbtickqjxgfysob", 
            "afcytqdkuyhckdkrhxhwswkdtbqbdojqtbhblnukbypgxbtickqjxgfysob", "afcytqdkuyhckdkrhxhhsbkdtbqbdojqtnwwlbukbypgxbtickqjxgfysob"
        };
        public static long checkSum = 492094943L;


  public static void main(String[] args) {
      System.out.println(answer());
  }

  public static String answer() {
      for (int i = 0; i < possStrings.length; i++) {
        System.out.println(i);
        if (simpleCheckSum(possStrings[i]) == checkSum) {
          System.out.println(i);
          return possStrings[i];
        }
      }
      return null;
  }
  
  public static long simpleCheckSum(String str) {
    long k = 7;//7
    for (int i = 0; i < str.length(); i++) {
      k *= 23;//23
      k += str.charAt(i);
      k *= 13;//13
      k %= 1000000009;
    }
    return k;
  }
  
}