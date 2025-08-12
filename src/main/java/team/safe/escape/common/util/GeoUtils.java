package team.safe.escape.common.util;


import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;

public abstract class GeoUtils {
    private GeoUtils() { }

    private static final GeodeticCalculator geoCalc = new GeodeticCalculator();

    public static double distanceInMeters(double lat1, double lon1, double lat2, double lon2) {
        GlobalCoordinates source = new GlobalCoordinates(lat1, lon1);
        GlobalCoordinates target = new GlobalCoordinates(lat2, lon2);

        return geoCalc
                .calculateGeodeticCurve(Ellipsoid.WGS84, source, target)
                .getEllipsoidalDistance();
    }
}
