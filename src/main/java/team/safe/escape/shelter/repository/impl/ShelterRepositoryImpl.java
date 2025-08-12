package team.safe.escape.shelter.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.safe.escape.shelter.entity.Shelter;
import team.safe.escape.shelter.repository.ShelterRepositoryCustom;

import java.util.List;

import static team.safe.escape.shelter.entity.QShelter.shelter;

@Repository
@RequiredArgsConstructor
public class ShelterRepositoryImpl implements ShelterRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Shelter> findByShelterIds(List<Long> shelterIds) {
        return queryFactory.selectFrom(shelter)
                .where(shelter.id.in(shelterIds))
                .fetch();
    }

    @Override
    public long countShelters() {
        Long count = queryFactory
                .select(shelter.count())
                .from(shelter)
                .fetchOne();
        return count != null ? count : 0L;
    }
}
