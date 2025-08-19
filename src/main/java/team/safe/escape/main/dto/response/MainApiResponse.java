package team.safe.escape.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.safe.escape.crowded.dto.CrowdedAreaDto;
import team.safe.escape.population.dto.PopulationDto;
import team.safe.escape.population.dto.response.PopulationNearbyDto;
import team.safe.escape.shelter.dto.response.ShelterWithBookmarkResponse;

import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainApiResponse {
    private List<PopulationDto> populationList;
    private List<CrowdedAreaDto> crowdedAreaList;
    private List<ShelterWithBookmarkResponse> shelterList;
    private PopulationNearbyDto nearbyPopulation;
}
