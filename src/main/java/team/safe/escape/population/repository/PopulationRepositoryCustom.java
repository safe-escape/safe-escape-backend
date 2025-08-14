package team.safe.escape.population.repository;

import java.time.LocalDateTime;

public interface PopulationRepositoryCustom {
    boolean existsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}
