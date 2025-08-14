package team.safe.escape.crowded.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import team.safe.escape.crowded.entity.CrowdedAreaExit;
import team.safe.escape.crowded.repository.CrowdedAreaExitRepositoryCustom;

import java.util.List;

import static team.safe.escape.crowded.entity.QCrowdedAreaExit.crowdedAreaExit;
import static team.safe.escape.exit.entity.QEmergencyExit.emergencyExit;

@Repository
public class CrowdedAreaExitRepositoryImpl implements CrowdedAreaExitRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CrowdedAreaExitRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }


    @Override
    public List<CrowdedAreaExit> findByCrowdedAreaId(long crowdedAreaId) {
        return queryFactory.selectFrom(crowdedAreaExit)
                .join(crowdedAreaExit.exit, emergencyExit)
                .where(crowdedAreaExit.crowdedArea.id.eq(crowdedAreaId))
                .fetch();
    }
}
