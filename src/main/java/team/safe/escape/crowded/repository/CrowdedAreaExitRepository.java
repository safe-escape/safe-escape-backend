package team.safe.escape.crowded.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.safe.escape.crowded.entity.CrowdedAreaExit;
import team.safe.escape.crowded.entity.CrowdedAreaExitId;

public interface CrowdedAreaExitRepository extends JpaRepository<CrowdedAreaExit, CrowdedAreaExitId>, CrowdedAreaExitRepositoryCustom {
}
