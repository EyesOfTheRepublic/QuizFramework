public class AlternatingRewriting {
  
          public static final String[][] REWRITE_MAP
              = {{"bYb", "Y"},
              {"c", "Y"},
              {"XXbYaX", "X"},
              {"XXba", "X"},
              {"Xa", "X"},
              {"XY", "X"},
              {"bb", "X"}};
  
          public static final String[][] ALT_REWRITE_MAP
            = {{"aYb", "Y"},
            {"cY", "Y"},
            {"XabYa", "X"},
            {"XXX", "X"},
            {"Xa", "X"}};
  
  
          public static String[] possResults = {
                    "YbaYXaaaYaYYaYbYaYaYaXabXbYabYaYYbYXbaaXbYYYYbabaaYaa", "YbaYXYbaaaYbYabaaYaYYXbaXbaaaYaYabYXbYaXYYYYYbYbaaYaa", 
                    "YbaYXbYaaaYYaabYaYaYYXbaXbYaaYaYYbYXbaaXbYYYYbabaaYaa", "YbaYXYbaaaYYaabYYYaYbXbaXbYaaYaYYbYXaaaXbYYYYbabaaYaa", 
                    "YbaYXbYaaaYaaYbYaYaaYXbYXbYaaYaYYbYXbaaabYYYYbXbaaYaa", "YbaYXbYaXaaYaabYaYaYYXbabbYYaYaYYbYXbaaXaYYYYbabaaYaa"
                  };
          public static String sourceString = "cbacbbbcaaacacbaabcacabcbcbbbbabababbcbacbaacaccbcbbcbaabbbccbcbcbabaacaa";

          public static void main(String[] args) {
              System.out.println(answer());
          }

          public static String answer() {
              boolean done = false;
              boolean isSet1 = true;
              String sStr = sourceString;
              while(!done) {
                String temp = sStr;
                if (isSet1) {
                  for(int i = 0; i < REWRITE_MAP.length; i++) {
                    temp = temp.replaceAll(REWRITE_MAP[i][0], REWRITE_MAP[i][1]);
                  }
                } else {
                  for(int i = 0; i < ALT_REWRITE_MAP.length; i++) {
                    temp = temp.replaceAll(ALT_REWRITE_MAP[i][0], ALT_REWRITE_MAP[i][1]);
                  }
                }
                isSet1 = !isSet1;
                if (temp.equals(sStr)) {
                  done = true;
                } 
                sStr = temp;
              }          
            return sStr;
          }
     }