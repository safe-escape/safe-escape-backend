package team.safe.escape.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.safe.escape.user.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>, RefreshTokenRepositoryCustom {
}
