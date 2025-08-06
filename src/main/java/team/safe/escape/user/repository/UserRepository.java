package team.safe.escape.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.safe.escape.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    User findByEmail(String email);

    boolean existsUserByEmail(String email);
}
