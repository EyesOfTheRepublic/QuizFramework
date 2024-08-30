public class CheckSumValue {
     
    public static String checkStr = "rsflkduwaucypqbcjpndwshwfmczgjatljrujf";


    public static void main(String[] args) {
        System.out.println(answer());
    }

    public static long answer() {
        long k = 7;//7
        for (int i = 0; i < checkStr.length(); i++) {
          k *= 23;//23
          k += checkStr.charAt(i);
          k *= 13;//13
          k %= 1000000009;
        }
        return k;
      
    }
}