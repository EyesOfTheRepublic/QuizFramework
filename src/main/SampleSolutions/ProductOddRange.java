public class ProductOddRange {
  
  public static int x = 4;
  public static int y = 8;
  
  
  public static void main(String[] args) {
    System.out.println(answer());
  }
  
  public static int answer() {
    int product = 1;
    for(int i = x; i <= y; i++) {
      if (i % 2 != 0) {
        product *= i;
      }
    }
    return product;
  }
}