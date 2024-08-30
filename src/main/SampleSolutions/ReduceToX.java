public class ReduceToX {
          public static String[] possAnswers = {
            "cbbcbacacbccaababcabbababcbcababaacccccacabcaacacbccaccaacbbaabab", "bbaaacbbabbbbcaacbacacabcabbcaaccaaacacbbabacaaccaccbacacacbcb", 
            "ccbcabacaaacbaaacabbcabccbbcccaabaccbaaacbbacbbcacbccbccbccaccacacbabaabc", 
            "cbcbbbcacacacbcaaaaacabbbbcbcaababcccccaacbccaababccabcbcabbacaacccbaccacbbaac", 
            "acbccaacccbccacaabacaabaabbcbbacaaccbcbcbbcaabbbbbaacbacacacabcccacbacccaabb", 
            "cbbcbaccbbabbbbaccbaacccaccbacbcbccccbccbbbbaabacabccbbaaaabcacaaaaabcb"
          };
  
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
              for(int i = 0; i < possAnswers.length; i++) {
                boolean done = false;
                String working = possAnswers[i];
                while(!done) {
                  String temp = working;
                  for(int j = 0; j < REWRITE_MAP.length; j++) {
                    temp = temp.replaceAll(REWRITE_MAP[j][0], REWRITE_MAP[j][1]);
                  }
                  if(temp.equals(working)) {
                    done = true;
                  }
                  working = temp;
              }
              if(working.equals("X")) {
                return possAnswers[i];
              }
          }
        return "Not found";
     }
}