public class SumNumbers {

      public static int sumNumbers = 7;


      public static void main(String[] args) {
          System.out.println(answer());
      }

      public static int answer() {
          int sum = 0;
          for(int i = 1; i <= sumNumbers; i++) {
            sum += 2*i - 1;
          }
        return sum;
      }
 }