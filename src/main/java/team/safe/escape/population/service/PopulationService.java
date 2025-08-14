package team.safe.escape.population.service;

import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.safe.escape.exception.ErrorCode;
import team.safe.escape.exception.EscapeException;
import team.safe.escape.population.dto.ForecastData;
import team.safe.escape.population.dto.PopulationAreaData;
import team.safe.escape.population.entity.Population;
import team.safe.escape.population.entity.PopulationArea;
import team.safe.escape.population.repository.PopulationAreaRepository;
import team.safe.escape.population.repository.PopulationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PopulationService {

    private final PopulationAreaDataLoader populationAreaDataLoader;
    private final PopulationAreaRepository populationAreaRepository;
    private final PopulationRepository populationRepository;
    private final PopulationFetcher populationFetcher;

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

    private boolean isExistTodayData() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfToday = now.withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfToday = now.withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);
        return populationRepository.existsByDateRange(startOfToday, endOfToday);
    }
}
