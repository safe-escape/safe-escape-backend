package team.safe.escape.population.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.safe.escape.population.entity.Population;

public interface PopulationRepository extends JpaRepository<Population, Long>, PopulationRepositoryCustom {
}
