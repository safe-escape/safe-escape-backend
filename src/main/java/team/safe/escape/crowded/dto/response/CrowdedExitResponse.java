package team.safe.escape.crowded.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import team.safe.escape.crowded.dto.LocationDto;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CrowdedExitResponse {
    private List<LocationDto> crowdedLocations;
    private List<LocationDto> exitLocations;

}
