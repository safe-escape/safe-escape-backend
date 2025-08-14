package team.safe.escape.population.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.safe.escape.population.repository.PopulationRepositoryCustom;

import java.time.LocalDateTime;

import static team.safe.escape.population.entity.QPopulation.population;

@Repository
@RequiredArgsConstructor
public class PopulationRepositoryImpl implements PopulationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return queryFactory
                .selectOne()
                .from(population)
                .where(population.dateTime.goe(startDate)
                        .and(population.dateTime.lt(endDate)))
                .fetchFirst() != null;
    }
}
