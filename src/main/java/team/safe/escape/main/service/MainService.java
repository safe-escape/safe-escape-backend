package team.safe.escape.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.safe.escape.crowded.dto.CrowdedAreaDto;
import team.safe.escape.crowded.service.CrowdedService;
import team.safe.escape.main.dto.response.MainAdminResponse;
import team.safe.escape.main.dto.response.MainApiResponse;
import team.safe.escape.population.dto.PopulationDto;
import team.safe.escape.population.dto.response.PopulationNearbyDto;
import team.safe.escape.population.service.PopulationService;
import team.safe.escape.shelter.dto.response.ShelterResponse;
import team.safe.escape.shelter.dto.response.ShelterWithBookmarkResponse;
import team.safe.escape.shelter.service.ShelterService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final CrowdedService crowdedService;
    private final ShelterService shelterService;
    private final PopulationService populationService;


    public MainAdminResponse getMainAdmin(double[][] locationArray) {
        List<CrowdedAreaDto> crowdedAreaList = crowdedService.getCrowdedAreaList(locationArray);
        List<ShelterResponse> shelterList = shelterService.getShelterResponseByLocation(locationArray);
        List<PopulationDto> populationList = populationService.getPopulationResponseByLocation(locationArray);
        return MainAdminResponse.builder()
                .crowdedAreaList(crowdedAreaList)
                .shelterList(shelterList)
                .populationList(populationList)
                .build();
    }

    public MainApiResponse getMainApi(double[][] locationArray, Long memberId) {
        List<CrowdedAreaDto> crowdedAreaList = crowdedService.getCrowdedAreaList(locationArray);
        List<ShelterWithBookmarkResponse> shelterList = shelterService.getShelterResponseWithBookmarkByLocation(locationArray, memberId);
        List<PopulationDto> populationList = populationService.getPopulationResponseByLocation(locationArray);
        PopulationNearbyDto nearByPopulation = populationService.getPopulationNearby(locationArray, 1).stream().findFirst().orElse(null);
        return MainApiResponse.builder()
                .crowdedAreaList(crowdedAreaList)
                .shelterList(shelterList)
                .populationList(populationList)
                .nearbyPopulation(nearByPopulation)
                .build();
    }

}
