public class ReduceToCompletion {

    public static String sourceString = "cbcbbbcbcaabccaccbbcbaaabacbbbbbaccccbbcabaabbcabccbbcbcbbbcccccbacbcaccc";
  
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

    public static int answer() {
        boolean done = false;
        int count = 0;
        while(!done) {
          String temp = sourceString;
          for(int i = 0; i < REWRITE_MAP.length; i++) {
            temp = temp.replaceAll(REWRITE_MAP[i][0], REWRITE_MAP[i][1]);
          }
          if (temp.equals(sourceString)) {
            done = true;
          } else {
            count++;
            sourceString = temp;
          }
        }
      return count;
    }
}
