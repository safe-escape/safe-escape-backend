package team.safe.escape.shelter.dto.response;

import lombok.Getter;
import team.safe.escape.shelter.entity.Shelter;

@Getter
public class ShelterWithBookmarkResponse extends ShelterResponse {
    private final boolean bookmark;

    protected ShelterWithBookmarkResponse(Long id, String name, String address, double latitude, double longitude, boolean bookmark) {
        super(id, name, address, latitude, longitude);
        this.bookmark = bookmark;
    }

    public static ShelterWithBookmarkResponse ofShelter(Shelter shelter, boolean bookmark) {
        return new ShelterWithBookmarkResponse(
                shelter.getId(),
                shelter.getName(),
                shelter.getAddress(),
                shelter.getLatitude(),
                shelter.getLongitude(),
                bookmark
        );
    }
}
