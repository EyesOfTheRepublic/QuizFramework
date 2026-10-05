public class SumFour {
  
  public static int sumRange = 33;
  
  
  public static void main(String[] args) {
    System.out.println(answer());
  }
  
  public static int answer() {
    int total = 0;
    for (int i = 1; i <= sumRange; i++) {
      if (i % 4 == 0) {
        total += i;
      }
    }
    return total;
  }
}