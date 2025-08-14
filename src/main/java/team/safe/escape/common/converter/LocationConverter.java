package team.safe.escape.common.converter;

import team.safe.escape.crowded.dto.request.LocationRequest;

import java.util.List;

public abstract class LocationConverter {
    private LocationConverter() {}

    public static double[][] toDoubleArray(List<LocationRequest> locations) {
        double[][] result = new double[locations.size()][2];
        for (int i = 0; i < locations.size(); i++) {
            result[i][0] = locations.get(i).getLatitude();
            result[i][1] = locations.get(i).getLongitude();
        }
        return result;
    }

}
