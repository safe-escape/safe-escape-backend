package team.safe.escape.shelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.safe.escape.shelter.entity.Shelter;

public interface ShelterRepository extends JpaRepository<Shelter, Long>, ShelterRepositoryCustom {
}
