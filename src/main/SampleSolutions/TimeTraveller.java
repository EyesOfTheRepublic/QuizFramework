import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeTraveller {
         public static String dateList[] = {
               "2492/03/08 12:12:32", "2301/06/20 18:04:54", "2858/09/09 09:54:52", "2652/05/10 02:52:27", 
               "2487/03/30 18:17:05", "2104/04/14 07:39:16", "2328/02/21 02:58:25", "2583/07/08 05:19:50", 
               "2383/07/07 21:33:26", "2689/08/26 02:09:05", "2575/06/22 20:24:21", "2952/08/21 21:22:55", 
               "2779/05/08 10:55:29", "2114/11/26 07:25:00", "2398/04/28 23:43:14", "2806/11/24 14:40:55", 
               "2348/02/18 08:40:58", "2280/11/22 10:52:16", "2062/09/16 14:58:19", "2453/08/07 04:27:20", 
               "2510/06/09 07:43:27", "2463/08/06 10:13:56", "2046/02/16 15:32:10", "2797/06/01 13:37:13", 
               "2360/02/15 06:26:18", "2132/03/18 20:33:46", "2471/11/15 04:29:35", "2846/06/23 17:26:23", 
               "2828/07/07 17:04:20", "2559/02/23 13:44:51", "2138/03/25 11:29:11", "2951/11/27 04:06:11", 
               "2249/05/30 12:00:10", "2941/08/28 04:51:25", "2940/04/21 00:19:09", "2947/11/07 12:30:02", 
               "2882/08/03 15:32:08", "2629/10/13 20:31:55", "2526/06/10 11:54:48", "2545/07/22 01:54:12", 
               "2932/04/23 18:13:36", "2463/05/30 01:24:51", "2867/05/18 05:02:55", "2665/08/08 14:57:26", 
               "2781/08/18 22:48:03", "2483/10/06 17:49:52", "2072/04/11 01:32:41", "2585/03/24 12:01:08", 
               "2794/04/04 10:18:24", "2190/10/09 13:54:42", "2645/11/17 14:17:15", "2232/06/07 19:28:37", 
               "2233/07/17 16:38:10"
            };
          public static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
   

          public static void main(String[] args) {
              System.out.println(answer());
          }

          public static long answer() {
              long total = 0;
              long baseDateMillis = Math.abs(LocalDateTime.parse(dateList[0],df).atZone(ZoneId.systemDefault())
               .toInstant().toEpochMilli());
               for(int i = 1; i < dateList.length; i++) {
                  long nextDateMillis = Math.abs(LocalDateTime.parse(dateList[i],df).atZone(ZoneId.systemDefault())
                  .toInstant().toEpochMilli());
                  total += Math.abs(nextDateMillis - baseDateMillis);
                  baseDateMillis = nextDateMillis;
               }
            return total;
         }
     }