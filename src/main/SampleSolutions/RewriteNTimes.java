public class RewriteNTimes {
      public static String[] possResults = {
          "XYabYaaaXXXXXbYaabaXbaY", "XYaYYaaaXXXXbbbaabaXXaY", "XYabYbaaXXXXXbYaabaaXaY", 
          "XYbbYaaaXXXabaYaabXXXaY", "XYabaaXaXXXXabYaabYXbaY", "XYaXXbaaXXXYbbbaXYaaaaY"
      };
      public static int numTimes = 7;

      public static String sourceString = "bbbbcacbaabcbaacaccabcaaabbcbbabbacbbabbcabbbaccabcaababbccbac";
      
      public static final String[][] REWRITE_MAP
          = {{"bYb", "Y"},
          {"c", "Y"},
          {"XXbYaX", "X"},
          {"XXba", "X"},
          {"Xa", "X"},
          {"XY", "X"},
          {"bb", "X"}};


      public static void main(String[] args) {
          System.out.println(answer());
      }

      public static String answer() {
        String temp = sourceString;
        for(int i = 0; i < numTimes; i++) {
          for(int j = 0; j < REWRITE_MAP.length; j++) {
            temp = temp.replaceAll(REWRITE_MAP[j][0], REWRITE_MAP[j][1]);
          }
        }
        return temp;
    }
}