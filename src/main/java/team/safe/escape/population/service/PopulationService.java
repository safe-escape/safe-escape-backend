package team.safe.escape.population.service;

import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.safe.escape.common.util.DateTimeUtils;
import team.safe.escape.common.util.GeoUtils;
import team.safe.escape.exception.ErrorCode;
import team.safe.escape.exception.EscapeException;
import team.safe.escape.population.dto.ForecastData;
import team.safe.escape.population.dto.PopulationAreaData;
import team.safe.escape.population.dto.PopulationDto;
import team.safe.escape.population.dto.response.PopulationNearbyDto;
import team.safe.escape.population.entity.Population;
import team.safe.escape.population.entity.PopulationArea;
import team.safe.escape.population.repository.PopulationAreaRepository;
import team.safe.escape.population.repository.PopulationRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PopulationService {

    private final PopulationAreaDataLoader populationAreaDataLoader;
    private final PopulationAreaRepository populationAreaRepository;
    private final PopulationRepository populationRepository;
    private final PopulationFetcher populationFetcher;

    public List<PopulationNearbyDto> getPopulationNearby(double latitude, double longitude, int size) {
        check();
        LocalDateTime dateTime = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0);
        List<Population> populationList = populationRepository.findByDateTime(dateTime);
        Map<String, PopulationArea> areaMap = populationAreaRepository.findAll().stream()
                .collect(Collectors.toMap(PopulationArea::getAreaCode, Function.identity()));

        List<Population> sortedPopulationList = populationList
                .stream()
                .sorted(Comparator.comparingDouble(p2 -> GeoUtils.distanceInMeters(areaMap.get(p2.getAreaCode()).getLatitude(), areaMap.get(p2.getAreaCode()).getLongitude(), latitude, longitude)))
                .toList();

        return sortedPopulationList.stream()
                .map(p -> {
                    PopulationArea populationArea = areaMap.get(p.getAreaCode());
                    return PopulationNearbyDto.builder()
                            .latitude(populationArea.getLatitude())
                            .longitude(populationArea.getLongitude())
                            .name(populationArea.getAreaName())
                            .level(p.getLevel())
                            .build();
                })
                .limit(size)
                .toList();
    }

    public void savePopulationArea() {
        long countPopulation = populationAreaRepository.countPopulationArea();
        if (countPopulation > 0) {
            throw new EscapeException(ErrorCode.ALREADY_SAVE_POPULATION_AREA);
        }

        List<PopulationAreaData> populationAreaDataList = populationAreaDataLoader.loadPopulationData();
        if (Collections.isEmpty(populationAreaDataList)) {
            return;
        }

        List<PopulationArea> populationAreaList = populationAreaDataList
                .stream()
                .map(PopulationArea::ofCreateByDate)
                .toList();
        populationAreaRepository.saveAll(populationAreaList);
    }

    @Transactional
    public List<Population> savePopulation() {
        if (isExistTodayData()) {
            throw new EscapeException(ErrorCode.ALREADY_SAVE_POPULATION);
        }

        List<PopulationArea> populationAreas = populationAreaRepository.findAll();
        List<Population> populationList = populationAreas.stream()
                .map(PopulationArea::getAreaCode)
                .map(populationFetcher::fetchAreaForecast)
                .filter(response -> response != null && response.getCityData() != null)
                .flatMap(response -> response.getCityData().stream())
                .flatMap(area -> area.getForecasts().stream()
                        .filter(ForecastData::isValidForToday)
                        .map(forecast -> Population.ofCreateByApiResponse(forecast, area)))
                .toList();

        populationRepository.saveAll(populationList);
        return populationList;
    }

    public List<PopulationDto> getPopulationResponseByLocation(double[][] locations) {
        check();
        Map<String, PopulationArea> areaMap = populationAreaRepository.findAll().stream()
                .filter(c -> GeoUtils.isPointInsidePolygon(c.getLongitude(), c.getLatitude(), locations))
                .collect(Collectors.toMap(PopulationArea::getAreaCode, Function.identity()));

        LocalDateTime dateTime = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0);
        return populationRepository.findByDateTime(dateTime).stream()
                .filter(p -> areaMap.containsKey(p.getAreaCode()))
                .map(a-> PopulationDto.builder()
                        .level(a.getLevel())
                        .longitude(areaMap.get(a.getAreaCode()).getLongitude())
                        .latitude(areaMap.get(a.getAreaCode()).getLatitude())
                        .build()
                ).toList();
    }

    public List<PopulationNearbyDto> getPopulationNearby(double[][] locations, int size) {
        double[] polygonCenter = GeoUtils.getPolygonCenter(locations);
        return getPopulationNearby(polygonCenter[0], polygonCenter[1], size);
    }

    private boolean isExistTodayData() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfToday = now.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfToday = now.withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);
        return populationRepository.existsByDateRange(startOfToday, endOfToday);
    }

    private void check() {
        LocalDateTime dateTime = LocalDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0);
        List<Population> populationList = populationRepository.findByDateTime(dateTime);
        if (Collections.isEmpty(populationList)) {
            List<PopulationArea> populationAreas = populationAreaRepository.findAll();
            populationRepository.saveAll(populationAreas.stream()
                    .map(PopulationArea::getAreaCode)
                    .map(populationFetcher::fetchAreaForecast)
                    .filter(response -> response != null && response.getCityData() != null)
                    .flatMap(response -> response.getCityData().stream())
                    .flatMap(area -> area.getForecasts().stream()
                            .filter(ForecastData::isValidForToday)
                            .filter(a -> {
                                LocalDateTime d = LocalDateTime.parse(a.getFcstTime(), DateTimeUtils.YYYY_MM_DD_HH_MM);
                                return d.equals(LocalDateTime.now()) || d.isAfter(LocalDateTime.now());
                            })
                            .map(forecast -> Population.ofCreateByApiResponse(forecast, area)))
                    .toList());
        }
    }
}
