package team.safe.escape.shelter.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.safe.escape.shelter.repository.ShelterRepositoryCustom;

@Repository
@RequiredArgsConstructor
public class ShelterRepositoryImpl implements ShelterRepositoryCustom {

    private final JPAQueryFactory queryFactory;

}
