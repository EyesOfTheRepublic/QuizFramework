import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeSince {
  public static String[] possDates = {
      "2650/09/19 03:15:14", "2010/05/12 00:12:21", "2772/07/15 07:34:58", "2202/05/15 10:17:26", 
      "2183/09/01 08:52:48", "2013/08/05 16:36:48"
  };
  public static long hours = 353783L;

  public static void main(String[] args) {
      System.out.println(answer());
  }

  public static String answer() {
      long millis = hours * 60 * 60 * 1000;
      long diff = millis;
      String closest = possDates[0];
      DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
      for(String date : possDates) {
        long possMillis = LocalDateTime.parse(date, df).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        if (Math.abs(possMillis - millis) < diff) {
          diff = Math.abs(possMillis - millis);
          closest = date;
        }
      }
    return closest;
    }
}