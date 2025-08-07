package team.safe.escape.shelter.repository;

public interface ShelterBookmarkRepositoryCustom {

    boolean existsByMemberIdAndShelterId(Long memberId, Long shelterId);
}
