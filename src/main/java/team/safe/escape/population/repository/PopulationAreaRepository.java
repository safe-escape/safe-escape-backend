package team.safe.escape.population.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.safe.escape.population.entity.PopulationArea;

public interface PopulationAreaRepository extends JpaRepository<PopulationArea, Long>, PopulationAreaRepositoryCustom {
}
