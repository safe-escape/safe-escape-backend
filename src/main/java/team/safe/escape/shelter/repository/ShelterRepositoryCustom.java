package team.safe.escape.shelter.repository;

import team.safe.escape.shelter.entity.Shelter;

import java.util.List;

public interface ShelterRepositoryCustom {
    List<Shelter> findByShelterIds(List<Long> shelterIds);
}
