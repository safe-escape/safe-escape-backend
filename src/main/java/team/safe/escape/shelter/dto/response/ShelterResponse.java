package team.safe.escape.shelter.dto.response;

import lombok.Getter;
import team.safe.escape.shelter.entity.Shelter;

@Getter
public class ShelterResponse {
    private final Long id;
    private final String name;
    private final String address;
    private final double latitude;
    private final double longitude;

    private ShelterResponse(Long id, String name, String address, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static ShelterResponse ofShelter(Shelter shelter) {
        return new ShelterResponse(
                shelter.getId(),
                shelter.getName(),
                shelter.getAddress(),
                shelter.getLatitude(),
                shelter.getLongitude()
        );
    }
}
