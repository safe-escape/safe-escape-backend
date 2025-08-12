package team.safe.escape.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.safe.escape.user.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Member findByEmail(String email);

    boolean existsUserByEmail(String email);
}
