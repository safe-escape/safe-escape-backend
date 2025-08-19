package team.safe.escape.exit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.safe.escape.exit.entity.EmergencyExit;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyExitDto {
    private long id;

    private double latitude;

    private double longitude;

    public static EmergencyExitDto ofExit(EmergencyExit exit) {
        return EmergencyExitDto.builder()
                .id(exit.getId())
                .latitude(exit.getLatitude())
                .longitude(exit.getLongitude())
                .build();
    }
}
