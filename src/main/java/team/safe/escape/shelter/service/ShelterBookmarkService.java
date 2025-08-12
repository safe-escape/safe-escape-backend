package team.safe.escape.shelter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.safe.escape.shelter.dto.response.ShelterResponse;
import team.safe.escape.shelter.entity.Shelter;
import team.safe.escape.shelter.entity.ShelterBookmark;
import team.safe.escape.shelter.repository.ShelterBookmarkRepository;
import team.safe.escape.shelter.repository.ShelterRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShelterBookmarkService {

    private final ShelterBookmarkRepository shelterBookmarkRepository;
    private final ShelterRepository shelterRepository;

    public List<ShelterResponse> getBookmarkShelters(Long memberId) {
        List<ShelterBookmark> memberShelterBookmark = shelterBookmarkRepository.findByMemberId(memberId);
        List<Long> shelterIdList = memberShelterBookmark.stream().map(a -> a.getId().getShelterId()).toList();
        List<Shelter> shelterList = shelterRepository.findByShelterIds(shelterIdList);
        return shelterList.stream().map(ShelterResponse::ofShelter).toList();
    }
}
