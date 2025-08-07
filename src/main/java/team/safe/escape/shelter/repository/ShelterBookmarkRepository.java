package team.safe.escape.shelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.safe.escape.shelter.entity.ShelterBookmark;

public interface ShelterBookmarkRepository extends JpaRepository<ShelterBookmark, Long>, ShelterBookmarkRepositoryCustom {
}
