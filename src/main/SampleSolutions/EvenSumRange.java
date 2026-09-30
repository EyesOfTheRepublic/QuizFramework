public class EvenSumRange {
  
  public static int x = 16;
  public static int y = 40;
  
  
  public static void main(String[] args) {
    System.out.println(answer());
  }
  
  public static int answer() {
    int total = 0;
    for(int i = x; i <= y; i++) {
      if (i %2 == 0) {
        total += i;
      }
    }
    return total;
  }
}