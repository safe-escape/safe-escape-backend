package team.safe.escape.shelter.service;

import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.safe.escape.common.util.GeoUtils;
import team.safe.escape.exception.ErrorCode;
import team.safe.escape.exception.EscapeException;
import team.safe.escape.shelter.dto.ShelterData;
import team.safe.escape.shelter.dto.response.ShelterResponse;
import team.safe.escape.shelter.dto.response.ShelterWithBookmarkResponse;
import team.safe.escape.shelter.entity.Shelter;
import team.safe.escape.shelter.entity.ShelterBookmark;
import team.safe.escape.shelter.entity.ShelterBookmarkId;
import team.safe.escape.shelter.repository.ShelterBookmarkRepository;
import team.safe.escape.shelter.repository.ShelterRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ShelterService {

    private final ShelterRepository shelterRepository;
    private final ShelterBookmarkRepository shelterBookmarkRepository;
    private final ShelterDataLoader shelterDataLoader;

    public ShelterResponse bookmarkShelter(Long memberId, Long shelterId) {
        Shelter shelter = getShelterById(shelterId);
        if (isAlreadyBookmarked(memberId, shelterId)) {
            throw new EscapeException(ErrorCode.EXIST_SHELTER_BOOKMARK, shelterId);
        }
        saveShelterBookmark(memberId, shelterId);
        return ShelterResponse.ofShelter(shelter);
    }

    public void deleteBookmark(Long memberId, Long shelterId) {
        ShelterBookmark shelterBookmark = getShelterBookmarkOrThrow(memberId, shelterId);
        shelterBookmarkRepository.delete(shelterBookmark);
    }

    public void saveShelter() {
        long countShelters = shelterRepository.countShelters();
        if (countShelters > 0) {
            throw new EscapeException(ErrorCode.ALREADY_SAVE_SHELTER);
        }

        List<ShelterData> shelterDataList = shelterDataLoader.loadShelterData();
        if (Collections.isEmpty(shelterDataList)) {
            return;
        }
        List<Shelter> shelterList = shelterDataList.stream()
                .map(Shelter::ofCreateByShelterData)
                .toList();
        shelterRepository.saveAll(shelterList);
    }

    public List<ShelterResponse> getShelterResponseByLocation(double[][] locations) {
        return shelterRepository.findAll()
                .stream()
                .filter(c -> GeoUtils.isPointInsidePolygon(c.getLongitude(), c.getLatitude(), locations))
                .map(ShelterResponse::ofShelter)
                .toList();
    }

    public List<ShelterWithBookmarkResponse> getShelterResponseWithBookmarkByLocation(double[][] locations, Long memberId) {
        Map<Long, ShelterBookmark> shelterBookmarkMap = shelterBookmarkRepository.findByMemberId(memberId).stream()
                .collect(Collectors.toMap(b -> b.getId().getShelterId(), Function.identity()));
        return shelterRepository.findAll()
                .stream()
                .filter(c -> GeoUtils.isPointInsidePolygon(c.getLongitude(), c.getLatitude(), locations))
                .map(c2 -> ShelterWithBookmarkResponse.ofShelter(c2, shelterBookmarkMap.containsKey(c2.getId())))
                .toList();
    }


    private Shelter getShelterById(Long shelterId) {
        return shelterRepository.findById(shelterId)
                .orElseThrow(() -> new EscapeException(ErrorCode.SHELTER_NOT_FOUND, shelterId));
    }

    private boolean isAlreadyBookmarked(Long memberId, Long shelterId) {
        return shelterBookmarkRepository.existsByMemberIdAndShelterId(memberId, shelterId);
    }

    private ShelterBookmark getShelterBookmarkOrThrow(Long memberId, Long shelterId) {
        return Optional.ofNullable(shelterBookmarkRepository.findByMemberIdAndShelterId(memberId, shelterId))
                .orElseThrow(() -> new EscapeException(
                        ErrorCode.SHELTER_BOOKMARK_NOT_FOUND,
                        String.format("memberId: %d, shelterId: %d", memberId, shelterId)));
    }

    private void saveShelterBookmark(Long memberId, Long shelterId) {
        ShelterBookmark shelterBookMark = ShelterBookmark.builder().id(ShelterBookmarkId.of(memberId, shelterId)).build();
        shelterBookmarkRepository.save(shelterBookMark);
    }
}
