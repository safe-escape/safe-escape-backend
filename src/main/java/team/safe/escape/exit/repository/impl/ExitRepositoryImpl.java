package team.safe.escape.exit.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.safe.escape.exit.repository.ExitRepositoryCustom;

@Repository
@RequiredArgsConstructor
public class ExitRepositoryImpl implements ExitRepositoryCustom {

    private final JPAQueryFactory queryFactory;
}
