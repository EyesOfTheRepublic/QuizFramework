public class TransposeDecrypt {
     
          public static String cypherText = "jgabckgficcbfkclgjaiimgkbjdajigihggmcjjmkhahefhmbbce";
          public static int columns = 13;
     
     
          public static void main(String[] args) {
              System.out.println(answer());
          }
     
          public static String answer() {
              char[][] encryptArray = new char[columns][cypherText.length() / columns];
              String result = "";
        
              int charLoc = 0;
              for (int i = 0; i < columns ; i++) {
                for(int j = 0; j < cypherText.length() / columns; j++) {
                  encryptArray[i][j]= cypherText.charAt(charLoc);
                  charLoc++;
                }
              }
        
              for (int i = 0; i < cypherText.length() / columns; i++) {
                for (int j = 0; j < columns; j++) {
                  result += encryptArray[j][i];
                }
              }
            return result;
          }
     }