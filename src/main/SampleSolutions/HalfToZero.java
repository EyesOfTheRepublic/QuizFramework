public class HalfToZero {
  
  public static int n = 1362281099;
  
  
  public static void main(String[] args) {
    System.out.println(answer());
  }
  
  public static int answer() {
    int counter = 0;
    int runningVal = n;
    while(runningVal > 0) {
      runningVal = runningVal/2;
      counter++;
    }
    return counter;
  }
}