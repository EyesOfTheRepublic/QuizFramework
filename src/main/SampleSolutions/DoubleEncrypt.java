public class DoubleEncrypt {
          
      public static String plainText = "lafalalkcedaekjkifhgfmimaacfmliflbgfjdikbleigklfmilifeegeiga";
      public static int columns = 12;


      public static void main(String[] args) {
          System.out.println(answer());
      }

      public static String answer() {
        String encOnce = encode(plainText, columns);
        return encode(encOnce, columns);
      }
  
    public static String encode(final String str, final int cols) {
      char[][] codeArray = new char[str.length()/cols][cols];
      int count = 0;
      for(int i = 0; i < str.length()/cols; i++) {
        for(int j = 0; j < cols; j++) {
          codeArray[i][j] = plainText.charAt(count);
          count++;
        }
      }
      String retVal = "";
      for(int k = 0; k < cols; k++) {
        for(int l = 0; l < str.length()/cols; l++) {
          retVal += codeArray[l][k];
        }
      }
    return retVal;
    }
  
}