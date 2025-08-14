package team.safe.escape.population.repository;

import team.safe.escape.population.entity.Population;

import java.time.LocalDateTime;
import java.util.List;

public interface PopulationRepositoryCustom {
    boolean existsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<Population> findByDateTime(LocalDateTime dateTime);
}
