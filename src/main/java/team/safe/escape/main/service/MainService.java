package team.safe.escape.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.safe.escape.common.converter.LocationConverter;
import team.safe.escape.crowded.dto.CrowdedAreaDto;
import team.safe.escape.crowded.dto.request.LocationRequest;
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


    public MainAdminResponse getMainAdmin(List<LocationRequest> locationList) {
        double[][] doubleArray = LocationConverter.toDoubleArray(locationList);
        List<CrowdedAreaDto> crowdedAreaList = crowdedService.getCrowdedAreaList(doubleArray);
        List<ShelterResponse> shelterList = shelterService.getShelterResponseByLocation(doubleArray);
        List<PopulationDto> populationList = populationService.getPopulationResponseByLocation(doubleArray);
        return MainAdminResponse.builder()
                .crowdedAreaList(crowdedAreaList)
                .shelterList(shelterList)
                .populationList(populationList)
                .build();
    }

    public MainApiResponse getMainApi(List<LocationRequest> locationList, Long memberId) {
        double[][] doubleArray = LocationConverter.toDoubleArray(locationList);
        List<CrowdedAreaDto> crowdedAreaList = crowdedService.getCrowdedAreaList(doubleArray);
        List<ShelterWithBookmarkResponse> shelterList = shelterService.getShelterResponseWithBookmarkByLocation(doubleArray, memberId);
        List<PopulationDto> populationList = populationService.getPopulationResponseByLocation(doubleArray);
        String info = populationService.getPopulationNearby(doubleArray, 1).stream().map(PopulationNearbyDto::getName).findFirst().orElse(null);
        return MainApiResponse.builder()
                .crowdedAreaList(crowdedAreaList)
                .shelterList(shelterList)
                .populationList(populationList)
                .info("🔥 근처에서 " + info + "이 가장 혼잡해요")
                .build();
    }

}
