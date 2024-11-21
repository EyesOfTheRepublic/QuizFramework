import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MillisFrom {
    public static String[] possDates = {
        "2702/03/20 15:09:59", "1980/10/01 22:35:11", "2012/02/11 18:47:47", "1991/06/10 17:31:39", 
        "2043/06/07 15:32:29", "2291/06/20 11:11:26"
    };
    public static long millsDifferent = 1160168766000L;

    public static String baseDate = "2028/03/15 13:37:45";;


    public static void main(String[] args) {
        System.out.println(answer());
    }

    public static String answer() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        long baseDateMillis = 
              Math.abs(LocalDateTime.parse(baseDate,df).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        boolean done = false;
        for (int i = 0; i < possDates.length; i++) {
            long currentDateMillis = LocalDateTime.parse(possDates[i],df).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            if (currentDateMillis + millsDifferent == baseDateMillis
              ||  currentDateMillis - millsDifferent == baseDateMillis) {
              return possDates[i];
            }
        }
        return "not found";
    }
}