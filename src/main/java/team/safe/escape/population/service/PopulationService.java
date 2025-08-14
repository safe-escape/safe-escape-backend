package team.safe.escape.population.service;

import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.safe.escape.exception.ErrorCode;
import team.safe.escape.exception.EscapeException;
import team.safe.escape.population.dto.PopulationAreaData;
import team.safe.escape.population.entity.PopulationArea;
import team.safe.escape.population.repository.PopulationAreaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PopulationService {

    private final PopulationAreaDataLoader populationAreaDataLoader;
    private final PopulationAreaRepository populationAreaRepository;

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
                .stream().map(PopulationArea::ofCreateByDate).toList();
        populationAreaRepository.saveAll(populationAreaList);
    }
}
