public class Progression {
  
  public static int threshold = 198;
  
  
  public static void main(String[] args) {
    System.out.println(answer());
  }
  
  public static int answer() {
    int runningTotal = 0;
    int count = 2;
    while (runningTotal <= threshold) {
      runningTotal += count * 3;
      count++;
    }
    return count - 1;
  }
}