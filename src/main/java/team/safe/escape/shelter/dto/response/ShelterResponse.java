package team.safe.escape.shelter.dto.response;

import lombok.Getter;
import team.safe.escape.shelter.entity.Shelter;

@Getter
public class ShelterResponse {
    protected final Long id;
    protected final String name;
    protected final String address;
    protected final double latitude;
    protected final double longitude;

    protected ShelterResponse(Long id, String name, String address, double latitude, double longitude) {
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
