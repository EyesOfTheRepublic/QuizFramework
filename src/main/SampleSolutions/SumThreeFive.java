public class SumThreeFive {

      public static int sumRange = 28;


      public static void main(String[] args) {
          System.out.println(answer());
      }

      public static int answer() {
          int runningTotal = 0;
          for (int i = 1; i <= sumRange; i++) {
              if ((i % 3 == 0) ^ (i % 5 == 0)) {
                  runningTotal += i;
              }
          }
          return runningTotal;
      }
 }