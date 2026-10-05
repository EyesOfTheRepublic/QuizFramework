public class NextBiggestEven {
  
  public static int number = 5;
  
  
  public static void main(String[] args) {
    System.out.println(answer());
  }
  
  public static int answer() {
    if (number % 2 == 1) {
      return number + 1;
    } else {
      return number + 2;
    }
  }
}