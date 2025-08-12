package team.safe.escape.crowded.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.safe.escape.crowded.entity.CrowdedArea;

public interface CrowdedAreaRepository extends JpaRepository<CrowdedArea, Long> {
}
