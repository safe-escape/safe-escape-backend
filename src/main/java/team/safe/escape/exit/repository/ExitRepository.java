package team.safe.escape.exit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.safe.escape.exit.entity.Exit;

public interface ExitRepository extends JpaRepository<Exit, Long>, ExitRepositoryCustom {
}
