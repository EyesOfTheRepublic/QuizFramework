public class Collatz {
  
  public static int startingVal = 27;
  public static int steps = 31;
  
  
  public static void main(String[] args) {
    System.out.println(answer());
  }
  
  public static int answer() {
    int val = startingVal;
    for(int i = 0; i < steps; i++) {
      if (val % 2 == 0) {
        val /= 2;
      } else {
        val = val * 3 + 1;
      }
    }
    return val;
  }
}