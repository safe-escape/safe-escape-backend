package team.safe.escape.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.safe.escape.user.entity.User;
import team.safe.escape.user.enumeration.UserRole;

import static team.safe.escape.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public User findUserByEmail(String email) {
        return queryFactory
                .selectFrom(user)
                .where(user.email.eq(email).and(user.role.eq(UserRole.USER)))
                .fetchFirst();
    }

    @Override
    public User findAdminByEmail(String email) {
        return queryFactory
                .selectFrom(user)
                .where(user.email.eq(email).and(user.role.eq(UserRole.ADMIN)))
                .fetchFirst();
    }

}
