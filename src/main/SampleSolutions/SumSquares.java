public class SumSquares {

      public static int numSquares = 7;


      public static void main(String[] args) {
          System.out.println(answer());
      }

      public static int answer() {
          int sum = 0;
          for(int i = 1; i <= numSquares; i++) {
            sum += i*i;
          }
          return sum;
      }
 }