package team.safe.escape.shelter.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.safe.escape.shelter.entity.ShelterBookmark;
import team.safe.escape.shelter.repository.ShelterBookmarkRepositoryCustom;

import static team.safe.escape.shelter.entity.QShelterBookmark.shelterBookmark;

@Repository
@RequiredArgsConstructor
public class ShelterBookmarkRepositoryImpl implements ShelterBookmarkRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean existsByMemberIdAndShelterId(Long memberId, Long shelterId) {
        return queryFactory
                .selectOne()
                .from(shelterBookmark)
                .where(shelterBookmark.id.memberId.eq(memberId)
                        .and(shelterBookmark.id.shelterId.eq(shelterId)))
                .fetchFirst() != null;
    }

    @Override
    public ShelterBookmark findByMemberIdAndShelterId(Long memberId, Long shelterId) {
        return queryFactory
                .selectFrom(shelterBookmark)
                .where(shelterBookmark.id.memberId.eq(memberId)
                        .and(shelterBookmark.id.shelterId.eq(shelterId)))
                .fetchFirst();
    }
}
