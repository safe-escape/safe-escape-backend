package team.safe.escape.crowded.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LocationDto {
    private double latitude;
    private double longitude;

    public static LocationDto ofCreate(double latitude, double longitude) {
        return LocationDto.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
