package team.safe.escape.crowded.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.safe.escape.crowded.entity.CrowdedAreaLoc;

public interface CrowdedAreaLocRepository extends JpaRepository<CrowdedAreaLoc, Long> {
}
