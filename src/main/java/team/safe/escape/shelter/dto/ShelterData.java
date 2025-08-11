package team.safe.escape.shelter.dto;

import lombok.Getter;

@Getter
public class ShelterData {
    private final double latitude;
    private final double longitude;
    private final String address;
    private final String facilityName;

    public ShelterData(String latitude, String longitude, String address, String facilityName) {
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
        this.address = address + " " + facilityName;
        this.facilityName = facilityName;
    }
}
