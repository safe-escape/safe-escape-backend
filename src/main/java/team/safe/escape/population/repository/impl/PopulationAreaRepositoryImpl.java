package team.safe.escape.population.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.safe.escape.population.repository.PopulationAreaRepositoryCustom;

import static team.safe.escape.population.entity.QPopulationArea.populationArea;

@Repository
@RequiredArgsConstructor
public class PopulationAreaRepositoryImpl implements PopulationAreaRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public long countPopulationArea() {
        Long count = queryFactory
                .select(populationArea.count())
                .from(populationArea)
                .fetchOne();
        return count != null ? count : 0L;
    }
}
