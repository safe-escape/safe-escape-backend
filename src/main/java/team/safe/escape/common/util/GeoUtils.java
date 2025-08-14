package team.safe.escape.common.util;


import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;

public abstract class GeoUtils {
    private GeoUtils() {
    }

    private static final GeodeticCalculator geoCalc = new GeodeticCalculator();

    public static double distanceInMeters(double lat1, double lon1, double lat2, double lon2) {
        GlobalCoordinates source = new GlobalCoordinates(lat1, lon1);
        GlobalCoordinates target = new GlobalCoordinates(lat2, lon2);

        return geoCalc
                .calculateGeodeticCurve(Ellipsoid.WGS84, source, target)
                .getEllipsoidalDistance();
    }

    public static boolean isValidLocation(double latitude, double longitude) {
        return isValidLatitude(latitude) && isValidLongitude(longitude);
    }

    public static boolean isValidLatitude(double latitude) {
        return latitude >= -90 && latitude <= 90;
    }

    public static boolean isValidLongitude(double longitude) {
        return longitude >= -180 && longitude <= 180;
    }

    public static boolean isPointInsidePolygon(double x, double y, double[][] polygon) {
        int n = polygon.length;
        boolean inside = false;

        for (int i = 0, j = n - 1; i < n; j = i++) {
            double xi = polygon[i][1]; // longitude
            double yi = polygon[i][0]; // latitude
            double xj = polygon[j][1]; // longitude
            double yj = polygon[j][0]; // latitude

            boolean intersect = ((yi > y) != (yj > y)) &&
                    (x < (xj - xi) * (y - yi) / (yj - yi) + xi);

            if (intersect) inside = !inside;
        }

        return inside;
    }

    public static double[] getPolygonCenter(double[][] polygon) {
        double sumLat = 0;
        double sumLon = 0;
        int n = polygon.length;

        for (double[] point : polygon) {
            sumLat += point[0]; // latitude
            sumLon += point[1]; // longitude
        }

        double centerLat = sumLat / n;
        double centerLon = sumLon / n;

        return new double[]{centerLat, centerLon};
    }
}
