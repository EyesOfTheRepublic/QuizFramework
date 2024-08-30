public class DistanceBetweenPoints {

      public static double latPoint1 = 57.153350;
      public static double lonPoint1 = -22.515097;
      public static double latPoint2 = 35.904863;
      public static double lonPoint2 = -71.440773;


      public static void main(String[] args) {
          System.out.println(answer());
      }

      public static double answer() {
          double latDistance = Math.toRadians(latPoint2 - latPoint1);
          double lonDistance = Math.toRadians(lonPoint2 - lonPoint1);
          double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(latPoint1)) * Math.cos(Math.toRadians(latPoint2))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
          double c = 2 * Math.asin(Math.sqrt(a));
          return 6371 * c;
    }
}