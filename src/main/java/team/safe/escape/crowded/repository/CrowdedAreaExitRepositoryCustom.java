package team.safe.escape.crowded.repository;

import team.safe.escape.crowded.entity.CrowdedAreaExit;

import java.util.List;

public interface CrowdedAreaExitRepositoryCustom {

    List<CrowdedAreaExit> findByCrowdedAreaId(long crowdedAreaId);

}
