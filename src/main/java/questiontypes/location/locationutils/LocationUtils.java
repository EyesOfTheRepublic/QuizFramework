package questiontypes.location.locationutils;

import quizframework.utils.Utils;

public class LocationUtils {

    public record Point(double lat, double lon) {
        @Override
        public String toString() {
            return lat + ", " + lon;
        }
    }

    public static final double MIN_GEO = -90.0;
    public static final double MAX_GEO = 90.0;
    public static final double MAX_DIST_VARIATION = 1000.0;
    public static final double EARTH_RAD = 6371;
    public static final double EARTH_CIRC = 40075;

    public static final int MIN_STEPS = 13;
    public static final int MAX_STEPS = 23;

    /*
    Create a new random point
     */
    public static Point randomPoint() {
        return new LocationUtils.Point(Utils.getRandomDouble(LocationUtils.MIN_GEO, LocationUtils.MAX_GEO, 0),
                Utils.getRandomDouble(LocationUtils.MIN_GEO, LocationUtils.MAX_GEO, 0));
    }

    /*
    Convert a latitude and longitude represented as doubles into a degrees, minutes, seconds string
     */
    public static String toMinSec(final Point point) {
        final char ns = point.lat() < 0 ? 'S' : 'N';
        final double lat = Math.abs(point.lat());
        double latSec = Math.round(lat * 3600);
        final int latDeg = (int) latSec / 3600;
        latSec = Math.abs(latSec % 3600);
        final int latMin = (int) latSec / 60;
        latSec %= 60;
        String latStr = String.format("%d\u00B0%d'%.2f\"%c",
                latDeg, latMin, latSec, ns);

        final char ew = point.lon() < 0 ? 'W' : 'E';
        final double lon = Math.abs(point.lon());
        double lonSec = Math.round(lon * 3600);
        final int lonDeg = (int) lonSec / 3600;
        lonSec = Math.abs(lonSec % 3600);
        final int lonMin = (int) lonSec / 60;
        lonSec %= 60;
        String lonStr = String.format("%d\u00B0%d'%.2f\"%c",
                lonDeg, lonMin, lonSec, ew);

        return latStr + ", " + lonStr;
    }

    /*
    Convert a degrees, minutes, seconds string (representing either longitude or latitude) into a decimal
     */
    public static double convertHourToDecimal(final String degree) {

        boolean isNeg = false;
        final String[] strArray = degree.split("[\"'\u00B0]");
        if (degree.charAt(degree.length() - 1) == 'S' || degree.charAt(degree.length() - 1) == 'W') {
            isNeg = true;
        }
        final double rawVal = Double.parseDouble(strArray[0]) + Double.parseDouble(strArray[1]) / 60
                + Double.parseDouble(strArray[2]) / 3600;
        return isNeg ? -rawVal : rawVal;
    }

    /*
    Get distance between two points represented as lat/long double pairs using Haversine method - distance in Km
     */
    public static double getDistance(final Point point1, final Point point2) {
        final double latDistance = Math.toRadians(point2.lat() - point1.lat());
        final double lonDistance = Math.toRadians(point2.lon() - point1.lon());
        final double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(point1.lat()))
                * Math.cos(Math.toRadians(point2.lat()))* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        final double c = 2 * Math.asin(Math.sqrt(a));
        return EARTH_RAD * c;
    }

}
