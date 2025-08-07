package team.safe.escape.shelter.repository;

import team.safe.escape.shelter.entity.ShelterBookmark;

public interface ShelterBookmarkRepositoryCustom {

    boolean existsByMemberIdAndShelterId(Long memberId, Long shelterId);

    ShelterBookmark findByMemberIdAndShelterId(Long memberId, Long shelterId);
}
