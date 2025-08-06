package team.safe.escape.user.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.safe.escape.user.repository.RefreshTokenRepositoryCustom;

import static team.safe.escape.user.entity.QRefreshToken.refreshToken;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public void deleteByUserId(Long userId) {
        queryFactory
                .delete(refreshToken)
                .where(refreshToken.userId.eq(userId))
                .execute();
    }
}
