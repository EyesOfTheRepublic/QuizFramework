public class TransposeEncrypt {
     
          public static String plainText = "fgjjcgadjjgmekkgcaafafdjlbmiebhehmegmgggfghcdidjbcjflckfemjjhdjajhcjfbckfkkafmjl";
          public static int columns = 10;
     
     
          public static void main(String[] args) {
              System.out.println(answer());
          }
     
          public static String answer() {
              char[][] codeArray = new char[plainText.length()/columns][columns];
              int count = 0;
              for(int i = 0; i < plainText.length()/columns; i++) {
                for(int j = 0; j < columns; j++) {
                  codeArray[i][j] = plainText.charAt(count);
                  count++;
                }
              }
              String retVal = "";
              for(int k = 0; k < columns; k++) {
                for(int l = 0; l < plainText.length()/columns; l++) {
                  retVal += codeArray[l][k];
                }
              }
            return retVal;
          }
     }