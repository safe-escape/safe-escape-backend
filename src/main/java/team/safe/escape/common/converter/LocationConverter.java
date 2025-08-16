package team.safe.escape.common.converter;

import java.util.List;

public abstract class LocationConverter {
    private LocationConverter() {
    }

    public static double[][] toDoubleArray(List<Double> latitudes, List<Double> longitudes) {
        double[][] locationArr = new double[latitudes.size()][2];
        for (int i = 0; i < latitudes.size(); i++) {
            locationArr[i][0] = latitudes.get(i);
            locationArr[i][1] = longitudes.get(i);
        }
        return locationArr;
    }

}
