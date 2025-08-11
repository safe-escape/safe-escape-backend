package team.safe.escape.shelter.repository;

import team.safe.escape.shelter.entity.ShelterBookmark;

import java.util.List;

public interface ShelterBookmarkRepositoryCustom {

    boolean existsByMemberIdAndShelterId(Long memberId, Long shelterId);

    ShelterBookmark findByMemberIdAndShelterId(Long memberId, Long shelterId);

    List<ShelterBookmark> findByMemberId(Long memberId);
}
