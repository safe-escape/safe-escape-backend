package team.safe.escape.exit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.safe.escape.exit.entity.EmergencyExit;

public interface EmergencyExitRepository extends JpaRepository<EmergencyExit, Long> {
}
