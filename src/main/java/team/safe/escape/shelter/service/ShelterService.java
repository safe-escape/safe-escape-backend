package team.safe.escape.shelter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.safe.escape.exception.ErrorCode;
import team.safe.escape.exception.EscapeException;
import team.safe.escape.shelter.dto.response.ShelterResponse;
import team.safe.escape.shelter.entity.Shelter;
import team.safe.escape.shelter.entity.ShelterBookmark;
import team.safe.escape.shelter.entity.ShelterBookmarkId;
import team.safe.escape.shelter.repository.ShelterBookmarkRepository;
import team.safe.escape.shelter.repository.ShelterRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ShelterService {

    private final ShelterRepository shelterRepository;
    private final ShelterBookmarkRepository shelterBookmarkRepository;

    public ShelterResponse bookmarkShelter(Long memberId, Long shelterId) {
        Shelter shelter = getShelterById(shelterId);
        if (isAlreadyBookmarked(memberId, shelterId)) {
            throw new EscapeException(ErrorCode.EXIST_SHELTER_BOOKMARK, shelterId);
        }
        saveShelterBookmark(memberId, shelterId);
        return ShelterResponse.ofShelter(shelter);
    }

    private Shelter getShelterById(Long shelterId) {
        return shelterRepository.findById(shelterId).orElseThrow(() -> new EscapeException(ErrorCode.SHELTER_NOT_FOUND, shelterId));
    }

    private boolean isAlreadyBookmarked(Long memberId, Long shelterId) {
        return shelterBookmarkRepository.existsByMemberIdAndShelterId(memberId, shelterId);
    }

    private void saveShelterBookmark(Long memberId, Long shelterId) {
        ShelterBookmark shelterBookMark = ShelterBookmark.builder().id(ShelterBookmarkId.of(memberId, shelterId)).build();
        shelterBookmarkRepository.save(shelterBookMark);
    }
}
